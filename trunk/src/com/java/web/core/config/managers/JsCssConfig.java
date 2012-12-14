
package com.java.web.core.config.managers;

import com.java.web.core.lib.StringHelper;
import com.java.web.core.config.JSFile;
import com.java.web.core.domain.access.Product;
import com.java.web.core.domain.access.Product2theme;
import com.java.web.core.lib.http.HttpContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import com.java.web.core.lib.md.five.MD5Encrypter;
import javax.naming.ConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JsCssConfig {

    private boolean isDebug = false;
    private ArrayList<String> cssIncludes;
    private ArrayList<String>  cssThemeFiles = new ArrayList<String>();
    private ArrayList<String>  cssBaseFiles = new ArrayList<String>();
    private ArrayList<String> cssDefaultFiles = new ArrayList<String>();
    private ArrayList<JSFile> jsFiles;
    private ArrayList<String> jsIncludes;
    private String first;
    private String login;

    private JsCssConfig() { }

    public ArrayList<String> getCssIncludes() {
        return cssIncludes;
    }
    public ArrayList<JSFile> getJsFiles() {
        return jsFiles;
    }
    public ArrayList<String> getJsIncludes() {
        return jsIncludes;
    }
    public String getFirst() {
        return first;
    }
    public String getLogin() {
        return login;
    }
    public boolean getIsDebug() {
        return isDebug;
    }


    public int getCssHashBuilderCapacity(){
        int stringBuilderCapacity = 0;
        stringBuilderCapacity = (this.cssBaseFiles.size()
                            + this.cssDefaultFiles.size() + this.cssThemeFiles.size())*20;
        return stringBuilderCapacity;
    }
    public String getCssHash() throws Exception {
        StringBuilder sb = new StringBuilder(this.getCssHashBuilderCapacity());
        for (int i=0; i<this.cssBaseFiles.size(); i++) {
            sb.append(this.cssBaseFiles.get(i));
            sb.append(";");
        }
        for (int i=0; i<this.cssDefaultFiles.size(); i++) {
            sb.append(this.cssDefaultFiles.get(i));
            sb.append(";");
        }
        for (int i=0; i<this.cssThemeFiles.size(); i++) {
            sb.append(this.cssThemeFiles.get(i));
            sb.append(";");
        }
        String hash = MD5Encrypter.encryptPassword(sb.toString());
        return hash;
    }
    public String getJsHash() throws Exception {
        StringBuilder sb = new StringBuilder(this.jsFiles.size() * 20);
        for (int i=0; i<this.jsFiles.size(); i++) {
            sb.append(this.jsFiles.get(i).getName());
            sb.append(";");
        }
        String hash = MD5Encrypter.encryptPassword(sb.toString());
        return hash;
    }



    public static JsCssConfig create() throws ParserConfigurationException, SAXException, IOException, ConfigurationException, Exception {
        Product product = InstanceManager.getProductInstanceFromSession();
        JsCssConfig result = parseConfig(product.getUrl(),product);
        if (product.isIsproduction()) {
            setProductionJsIncludes(result);
            setProductionCssIncludes(result);
            result.isDebug = false;
        } else {
            setDebugJsIncludes(result);
            setDebugCssIncludes(result);
            result.isDebug = true;
        }
        return result;
    }

    private static synchronized void setProductionCssIncludes(JsCssConfig result) throws Exception {
        String path = "resources/tmp/" + result.getCssHash() + ".css";
        String filename = HttpContext.context().getRealPath(path);
        File file = new File(filename);
        if (!file.exists()) {
            createCssBundle(result, filename);
        }
        result.cssIncludes = new ArrayList<String>();
        result.cssIncludes.add(path);
    }
    private static void createCssBundle(JsCssConfig result, String filename) throws IOException {
        String root = HttpContext.context().getRealPath("/");
        ArrayList<String> files = new ArrayList<String>();
        for (int i=0; i<result.getCssBaseFiles().size(); i++) {
            String src = root + result.getCssBaseFiles().get(i) + ".min.css";
            files.add(src);
        }
        for (int i=0; i<result.getCssThemeFiles().size(); i++) {
            String src = root + result.getCssThemeFiles().get(i) + ".min.css";
            files.add(src);
        }
        for (int i=0; i<result.getCssDefaultFiles().size(); i++) {
            String src = root + result.getCssDefaultFiles().get(i) + ".min.css";
            files.add(src);
        }
        createBundle(files, filename);
    }

    private static synchronized void setProductionJsIncludes(JsCssConfig result) throws Exception {
        String path = "resources/tmp/" + result.getJsHash() + ".js";
        String filename = HttpContext.context().getRealPath(path);
        File file = new File(filename);
        if (!file.exists()) {
            createJsBundle(result, filename);
        }
        result.jsIncludes = new ArrayList<String>();
        result.jsIncludes.add(path);
    }
    private static void createJsBundle(JsCssConfig result, String filename) throws IOException {
        ArrayList<String> files = new ArrayList<String>();
        for (int i=0; i<result.jsFiles.size(); i++) {
            String src = "/js/build/" + result.jsFiles.get(i).getName() + ".min.js";
            src = HttpContext.context().getRealPath(src);
            files.add(src);
        }
        createBundle(files, filename);
    }

    private static void createBundle(ArrayList<String> files, String filename) throws IOException {
        FileOutputStream out = new FileOutputStream(filename);
        byte[] separator = { 13, 10, 13, 10 };
        byte[] buf = new byte[1024];
        try {
            for (int i=0; i<files.size(); i++) {
                String src = files.get(i);
                FileInputStream in = new FileInputStream(src);
                try {
                    int len;
                    while ((len = in.read(buf)) > 0){
                        out.write(buf, 0, len);
                    }
                } finally {
                    in.close();
                }
                out.write(separator);
            }
        } finally {
            out.close();
        }
    }

    private static void setDebugCssIncludes(JsCssConfig result) {
        result.cssIncludes = new ArrayList<String>();
        for (int i=0; i<result.getCssThemeFiles().size(); i++) {
            result.cssIncludes.add(result.getCssThemeFiles().get(i) + ".css");
        }
        for (int i=0; i<result.getCssBaseFiles().size(); i++) {
            result.cssIncludes.add(result.getCssBaseFiles().get(i) + ".css");
        }
        for (int i=0; i<result.getCssDefaultFiles().size(); i++) {
            result.cssIncludes.add(result.getCssDefaultFiles().get(i) + ".css");
        }
    }
    private static void setDebugJsIncludes(JsCssConfig result) {
        result.jsIncludes = new ArrayList<String>();
        for (int i=0; i<result.jsFiles.size(); i++) {
            result.jsIncludes.add("js/src/" + result.jsFiles.get(i).getName() + ".js");
        }
    }


    private static JsCssConfig parseConfig(String instanceName,Product product) throws ParserConfigurationException, SAXException, IOException, ConfigurationException {
        JsCssConfig result = new JsCssConfig();
        Document doc = getDocument();

        parseStdJavascript(result, doc);
        parseStdStyle(result, doc, product);

        parseProduct(result, doc, instanceName);
        
        Collections.sort(result.jsFiles);

        return result;
    }
    private static void parseStdJavascript(JsCssConfig result, Document doc) throws ConfigurationException {
        NodeList javascriptList = doc.getElementsByTagName("javascript");
        if (javascriptList == null)
            throw new ConfigurationException("Missing javascript in js.config");
        Node javascriptNode = javascriptList.item(0);
        Element javascriptElement = (Element)javascriptNode;

        result.jsFiles = new ArrayList<JSFile>();
        NodeList stdJsList = javascriptElement.getElementsByTagName("js");
        for (int i = 0; i < stdJsList.getLength(); i++) {
            JSFile file = getJSFileFromNode(stdJsList.item(i));
            file.setIsStandard(true);
            result.jsFiles.add(file);
            if (file.getIsFirst())
                result.first = file.getName();
            if (file.getIsLogin())
                result.login = file.getName();
        }
    }
    private static void parseStdStyle(JsCssConfig result, Document doc,Product product) throws ConfigurationException {
        NodeList styleList = doc.getElementsByTagName("style");
        if (styleList == null)
            throw new ConfigurationException("Missing style in js.config");
        Node styleNode = styleList.item(0);
        Element cssElement = (Element)styleNode;

        result.cssBaseFiles = new ArrayList<String>();
        NodeList stdCssBaseList = cssElement.getElementsByTagName("cssBase");
        for (int i = 0; i < stdCssBaseList.getLength(); i++) {
            String css = stdCssBaseList.item(i).getFirstChild().getNodeValue();
            result.cssBaseFiles.add(css);
        }
        result.cssDefaultFiles = new ArrayList<String>();
        NodeList stdCssDefaultList = cssElement.getElementsByTagName("cssDefault");
        for (int i = 0; i < stdCssDefaultList.getLength(); i++) {
            String css = stdCssDefaultList.item(i).getFirstChild().getNodeValue();
            result.cssDefaultFiles.add(css);
        }
        if(product.getProduct2themes()!= null){
            if(product.getProduct2themes().size() !=0){
               Iterator it = product.getProduct2themes().iterator();
               Product2theme product2Theme =  (Product2theme)it.next();
               if(product2Theme.isIsactive()){
                   result.cssThemeFiles.add(product2Theme.getTheme().getThemename());
                   result.cssDefaultFiles = new ArrayList<String>();
               }
            }
        }
    }
    private static void parseProduct(JsCssConfig result, Document doc, String instanceName) {
        Node productNode = getProductNode(doc, instanceName);
        if (productNode != null) {
            NodeList jsList = ((Element)productNode).getElementsByTagName("js");
            for (int i=0; i < jsList.getLength(); i++) {
                JSFile file = getJSFileFromNode(jsList.item(i));
                file.setIsStandard(false);
                result.jsFiles.add(file);
            }

            //NodeList cssList = ((Element)productNode).getElementsByTagName("css");
            //for (int i=0; i < cssList.getLength(); i++) {
            //    String css = cssList.item(i).getFirstChild().getNodeValue();
            //    result.cssDefaultFiles.add(css);
            //}

            Node attr = productNode.getAttributes().getNamedItem("login");
            if ( attr != null && !StringHelper.isNullOrEmpty(attr.getNodeValue()))
                result.login = attr.getNodeValue().trim();
            attr = productNode.getAttributes().getNamedItem("first");
            if ( attr != null && !StringHelper.isNullOrEmpty(attr.getNodeValue()))
                result.first = attr.getNodeValue().trim();
        }
    }
    private static Document getDocument() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(HttpContext.context().getRealPath("/WEB-INF/classes/js.config.xml"));
        doc.getDocumentElement().normalize();
        return doc;
    }
    private static Node getProductNode(Document doc, String instanceName) {
        Node productNode = null;
        NodeList productList = doc.getElementsByTagName("product");
        for (int i=0; i < productList.getLength(); i++) {
            Node node = productList.item(i);
            Node attrName = node.getAttributes().getNamedItem("name");
            if (attrName != null && instanceName.equals(attrName.getNodeValue())) {
                productNode = node;
                break;
            }
        }
        return productNode;
    }
    private static JSFile getJSFileFromNode(Node node) {
        JSFile result = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            result = new JSFile();
            String nodeValue = node.getFirstChild().getNodeValue();
            result.setName(nodeValue);

            Node attr = node.getAttributes().getNamedItem("isCard");
            result.setIsCard( attr != null && "1".equals(attr.getNodeValue()) );

            attr = node.getAttributes().getNamedItem("isLogin");
            result.setIsLogin( attr != null && "1".equals(attr.getNodeValue()) );

            attr = node.getAttributes().getNamedItem("isFirst");
            result.setIsFirst( attr != null && "1".equals(attr.getNodeValue()) );

            int rank = 900;
            attr = node.getAttributes().getNamedItem("r");
            if (attr != null) {
                try {
                    rank = Integer.parseInt(attr.getNodeValue());
                } catch (Exception ex) { }
            }
            result.setRank(rank);

        }
        return result;
    }

    /**
     * @return the cssBaseFiles
     */
    public ArrayList<String> getCssBaseFiles() {
        return cssBaseFiles;
    }

    /**
     * @param cssBaseFiles the cssBaseFiles to set
     */
    public void setCssBaseFiles(ArrayList<String> cssBaseFiles) {
        this.cssBaseFiles = cssBaseFiles;
    }

    /**
     * @return the cssDefaultFiles
     */
    public ArrayList<String> getCssDefaultFiles() {
        return cssDefaultFiles;
    }

    /**
     * @param cssDefaultFiles the cssDefaultFiles to set
     */
    public void setCssDefaultFiles(ArrayList<String> cssDefaultFiles) {
        this.cssDefaultFiles = cssDefaultFiles;
    }

    /**
     * @return the cssThemeFiles
     */
    public ArrayList<String> getCssThemeFiles() {
        return cssThemeFiles;
    }

    /**
     * @param cssThemeFiles the cssThemeFiles to set
     */
    public void setCssThemeFiles(ArrayList<String> cssThemeFiles) {
        this.cssThemeFiles = cssThemeFiles;
    }



}
