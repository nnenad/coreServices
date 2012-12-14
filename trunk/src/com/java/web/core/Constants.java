/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core;

/**
 *
 * @author Marija
 */
public class Constants {


    public static final String KEY_CURRENT_LOCALE = "CurrentLocale";

    public static final String KEY_PRODUCT_INSTANCE_IN_SESSION = "CurrentProductInstance";
    public static final String KEY_USER_IN_SESSION = "CurrentUser";
    public static final String KEY_HIBERANTE_SESSION = "HibernateSlaveSession";
    public static final String KEY_REMEMBER_ME_COOKIE = "Token";
    public static final String MAIN_CONFIG_MANAGEER = "Main_Config_Manager";
    
    
    public static final String MIGRATION_TYPE_CORE = "Core";
    public static final String MIGRATION_TYPE_APP = "App";
    public static final String MIGRATION_TYPE_TEST = "Test";
    public static final String MIGRATION_TYPE_DOWN = "Down";

    


    public static final int REMEMBER_COOKIE_AGE = 7776000; // 3 months
    public static final String CURRENT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String CURRENT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String CURRENT_TIME_FORMAT = "HH:mm:ss";

    public static final String GLOBAL_FIELD_CREATEDON="createdon";
    public static final String GLOBAL_FIELD_MODIFIEDON="modifiedon";
    public static final String GLOBAL_FIELD_CREATEDBY="createdby";
    public static final String GLOBAL_FIELD_MODIFIEDBY="modifiedby";

}
