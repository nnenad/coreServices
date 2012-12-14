package com.java.web.core.config.managers;

import com.java.web.core.Constants;
import com.java.web.core.broker.HibernateSessionFactoriesDictionary;
import com.java.web.core.broker.SSessionUtil;
import com.java.web.core.domain.access.Product;
import com.java.web.core.domain.access.Product2menuitem;
import com.java.web.core.lib.http.HttpContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Properties;
import javax.naming.ConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public abstract class AbstractMainPageContent {

    public abstract void printOpeningHeader(PrintWriter out);

    public abstract void printClosingHeader(PrintWriter out, JsCssConfig jsCssConfig);

    public abstract void printInitJson(PrintWriter out, JsCssConfig jsCssConfig, Properties urlInitProperties) throws Exception;

    public abstract boolean usesLocalStorage();

    public abstract void oppeningBodyTag(PrintWriter out);

    public void execute(PrintWriter out, Properties urlInitProperties) throws IOException, ParserConfigurationException, SAXException, ConfigurationException, Exception {
        Product currentProduct = (Product) HttpContext.getSessionVar(Constants.KEY_PRODUCT_INSTANCE_IN_SESSION);
        MainConfigManager.getBaseBroker().getSessionUtil().setSession(HibernateSessionFactoriesDictionary.getMasterFactory().openSession());
        try {

            out.append("<html>\n");
            out.append("<head>\n");
            printOpeningHeader(out);
            JsCssConfig jsCssConfig = JsCssConfig.create();
            printInitJson(out, jsCssConfig,urlInitProperties);
            for (int i = 0; i < jsCssConfig.getCssIncludes().size(); i++) {
                out.append(String.format("<link rel=\"stylesheet\" href=\"%s?%s\" type=\"text/css\">\n", jsCssConfig.getCssIncludes().get(i), MainConfigManager.getAppSpecificConstants().getAppVersion()));
            }

            out.append("</head>\n");
            oppeningBodyTag(out);
            if (usesLocalStorage()) {
                if (currentProduct.isIsproduction()) {
                    for (int i = 0; i < jsCssConfig.getJsIncludes().size(); i++) {
                        out.append(String.format("<script type=\"text/javascript\" >" +
                                "var js_file=\"%s?%s\";" +
                                "var key=\"%s\";" +
                                "var previousKey=\"%s\";" +
                                "if(window.localStorage && window.localStorage[key]){" +
                                "try{" +
                                "window.eval(window.localStorage[key]);" +
                                "}" +
                                "catch(e){" +
                                "}" +
                                "}else " +
                                "if(window.localStorage){" +
                                "window.localStorage.clear();" +
                                "var xhr = new XMLHttpRequest();" +
                                "xhr.onreadystatechange = function(){" +
                                "if(xhr.readyState == 4){" +
                                "try{" +
                                "window.eval(xhr.responseText);" +
                                "}" +
                                "catch(e){}" +
                                "try{" +
                                "window.localStorage[key] = xhr.responseText;" +
                                "}catch(e){}" +
                                "}else{" +
                                "return;" +
                                "}" +
                                "};" +
                                "xhr.open(\"GET\",js_file,true);" +
                                "xhr.send();" +
                                "}" +
                                "</script>\n", jsCssConfig.getJsIncludes().get(i), MainConfigManager.getAppSpecificConstants().getAppVersion(),
                                MainConfigManager.getAppSpecificConstants().getAppVersion(),MainConfigManager.getAppSpecificConstants().getPreviousAppVersion()));
                    }
                } else {
                    for (int i = 0; i < jsCssConfig.getJsIncludes().size(); i++) {
                        out.append(String.format("<script type=\"text/javascript\" src=\"%s?%s\"></script>\n", jsCssConfig.getJsIncludes().get(i), MainConfigManager.getAppSpecificConstants().getAppVersion()));
                    }
                }
            } else {
                for (int i = 0; i < jsCssConfig.getJsIncludes().size(); i++) {
                    out.append(String.format("<script type=\"text/javascript\" src=\"%s?%s\"></script>\n", jsCssConfig.getJsIncludes().get(i), MainConfigManager.getAppSpecificConstants().getAppVersion()));
                }
            }
            printClosingHeader(out, jsCssConfig);
            out.append("</body>\n");
            out.append("</html>");
        } finally {
            MainConfigManager.getBaseBroker().getSessionUtil().closeSession();
        }
    }
}
