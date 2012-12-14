/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.config.managers;

import com.java.web.core.AppSpecificConstants;
import com.java.web.core.Constants;
import com.java.web.core.Service;
import com.java.web.core.broker.AbstractBroker;
import com.java.web.core.lib.http.HttpContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marija
 */
public abstract class MainConfigManager {
    
    private static AbstractMainPageContent contentGenerator;
    private static AbstractBroker baseBroker;
    private static Service baseService;
    private static AppSpecificConstants appSpecificConstants;
  

    public static AbstractBroker getBaseBroker() {
      if(baseBroker == null){
          MainConfigManager mainConfigManager = (MainConfigManager)HttpContext.getApplicationVar(Constants.MAIN_CONFIG_MANAGEER);
                baseBroker = mainConfigManager.configureBroker();
          return baseBroker;
      }
      else{
          return baseBroker;
      }
    }

    public static Service getBaseService() {
       if(baseService == null){
          MainConfigManager mainConfigManager = (MainConfigManager)HttpContext.getApplicationVar(Constants.MAIN_CONFIG_MANAGEER);
          baseService = mainConfigManager.configureBaseService();
          return baseService;
      }
      else{
          return baseService;
      }
    }

    public static AbstractMainPageContent getContentGenerator() {
        if(contentGenerator == null){
          MainConfigManager mainConfigManager = (MainConfigManager)HttpContext.getApplicationVar(Constants.MAIN_CONFIG_MANAGEER);
          contentGenerator = mainConfigManager.configureContentGenerator();
          return contentGenerator;
      }
      else{
          return contentGenerator;
      }
    }
    public static AppSpecificConstants getAppSpecificConstants() {
        if(appSpecificConstants == null){
          MainConfigManager mainConfigManager = (MainConfigManager)HttpContext.getApplicationVar(Constants.MAIN_CONFIG_MANAGEER);
          appSpecificConstants = mainConfigManager.configureApplicationSpecificConstants();
          return appSpecificConstants;
      }
      else{
          return appSpecificConstants;
      }
    }
    
    public static Session getCurrentSession(){
        return MainConfigManager.getBaseBroker().getSessionUtil().getCurrentSession();
    }

    public static Query createQuery(String hql){
        return MainConfigManager.getBaseBroker().getSessionUtil().getCurrentSession().createQuery(hql);
    }
    
   public abstract AbstractBroker configureBroker();
   public abstract Service configureBaseService();
   public abstract AbstractMainPageContent configureContentGenerator();
   public abstract AppSpecificConstants configureApplicationSpecificConstants();
}
