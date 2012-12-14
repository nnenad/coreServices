/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.config.managers;

import java.io.FileInputStream;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 *
 * @author Vuk
 */
public class RuntimeProperties {

    private static Properties runtimeProperties;

    public static synchronized Properties getRuntimePropertiesInstance(ServletContext context) {
        if (runtimeProperties == null) {
            String path = context.getRealPath("/WEB-INF/classes/runtime.properties");
            runtimeProperties = new Properties();
            try {
                FileInputStream in = new FileInputStream(path);
                runtimeProperties.load(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return runtimeProperties;
        }
        else
        {
            return runtimeProperties;
        }
    }
    public static synchronized Properties addProperty(String key, String value,ServletContext context){
        getRuntimePropertiesInstance(context).put(key, value);
        return getRuntimePropertiesInstance(context);
    }
    public static synchronized void reload(ServletContext context)
    {
        runtimeProperties = null;
        String path = context.getRealPath("/WEB-INF/classes/runtime.properties");
            runtimeProperties = new Properties();
            try {
                FileInputStream in = new FileInputStream(path);
                runtimeProperties.load(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
