/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.web.core.lib.test;

import com.java.web.core.config.managers.JNDIBasedParams;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author Nenad
 */
public class MigrationParams {
    private static Properties runtimeProperties;
    public static String propertyFileLocation;
    public static synchronized Properties getMigrationParamsInstance() {
        if (runtimeProperties == null) {
            
            runtimeProperties = new Properties();
            try {
                FileInputStream in = new FileInputStream(propertyFileLocation);
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
    public static synchronized Properties addProperty(String key, String value){
        getMigrationParamsInstance().put(key, value);
        return getMigrationParamsInstance();
    }
    public static synchronized void reload()
    {
        runtimeProperties = null;
        
            runtimeProperties = new Properties();
            try {
                FileInputStream in = new FileInputStream(propertyFileLocation);
                runtimeProperties.load(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
