
package com.java.web.core.config.managers;

import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.java.web.core.Constants;
import com.java.web.core.lib.http.HttpContext;

public class JNDIBasedParams extends HashMap<String, String> {

    private static volatile JNDIBasedParams _instance = null;
    private static final Object _lock = new Object();

    public static JNDIBasedParams current() throws NamingException {
        JNDIBasedParams result = _instance;
        if (result == null) {
            synchronized(_lock) {
                result = _instance;
                if (result == null) {
                    result = _instance = create();
                }
            }
        }
        return result;
    }
    public static JNDIBasedParams currentNoWebSession(String appId) throws NamingException {
        JNDIBasedParams result = _instance;
        if (result == null) {
            synchronized(_lock) {
                result = _instance;
                if (result == null) {
                    result = _instance = createNoWebSession(appId);
                }
            }
        }
        return result;
    }
    private static JNDIBasedParams create() throws NamingException {
        Context initialCntx = new InitialContext();
        /* Tomcat */
        HashMap map = (HashMap)initialCntx.lookup("java:comp/env/"+MainConfigManager.getAppSpecificConstants().getAppId());
        /* GlassFish */
        //HashMap map = (HashMap)initialCntx.lookup(""+((MainConfigManager)HttpContext.getSessionVar(Constants.MAIN_CONFIG_MANAGEER)).getAppSpecificConstants().getAppId());
        JNDIBasedParams result = new JNDIBasedParams(map);
        return result;
    }
    private static JNDIBasedParams createNoWebSession(String appId) throws NamingException {
        Context initialCntx = new InitialContext();
        /* Tomcat */
        HashMap map = (HashMap)initialCntx.lookup("java:comp/env/"+appId);
        /* GlassFish */
        //HashMap map = (HashMap)initialCntx.lookup(""+appId);
        JNDIBasedParams result = new JNDIBasedParams(map);
        return result;
    }
    public static void createEmpty() {
        _instance = new JNDIBasedParams();
    }

    protected JNDIBasedParams() {
        super();
    }
    protected JNDIBasedParams(Map<String, String> m) {
        super(m);
    }

    

    

}
