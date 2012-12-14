
package com.java.web.core.config.managers;

import com.java.web.core.domain.access.Product;
import com.java.web.core.lib.http.HttpContext;
import javax.naming.NamingException;
import com.java.web.core.Constants;

public class InstanceManager {

    public static String getProductInstanceNameFromURL() {
        String result = HttpContext.request().getServerName();
        return result;
    }

    public static Product getProductInstanceFromSession() {
        Product result = HttpContext.getSessionVar(Constants.KEY_PRODUCT_INSTANCE_IN_SESSION);
        return result;
    }

    public static void clearProductInstanceFromSession() {
        HttpContext.setSessionVar(Constants.KEY_PRODUCT_INSTANCE_IN_SESSION, null);
    }

    public static boolean validateProductInstanceInSession() throws NamingException {
        Product fromSession = getProductInstanceFromSession();
        String fromUrl = getProductInstanceNameFromURL();
        if (fromSession == null || !fromSession.getUrl().equalsIgnoreCase(fromUrl)) {
            fromSession = getProductInstanceFromDB(fromUrl);
            if (fromSession == null) {
                return false;
            }
            HttpContext.setSessionVar(Constants.KEY_PRODUCT_INSTANCE_IN_SESSION, fromSession);
        }
        return true;
    }

    public static Product getProductInstanceFromDB(String name) throws NamingException {
        Product result = null;
        MainConfigManager.getBaseBroker().openMasterSessionIfNeeded();
        try {
            result = MainConfigManager.getBaseBroker().getProductInstanceFromDB(name);
        } finally {
            MainConfigManager.getBaseBroker().closeSession();
        }
        return result;
    }

}
