/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.servlet;


import com.java.web.core.Constants;
import com.java.web.core.config.managers.MainConfigManager;
import com.java.web.core.lib.http.HttpContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Marija
 */
public abstract class LoadSvlCtxLi implements ServletContextListener{
    Thread fileMonitor = null;
    Thread logMonitor = null;
    public abstract MainConfigManager getMainConfigManager();
    public void contextInitialized(ServletContextEvent sce) {
        HttpContext.setApplicationVar(Constants.MAIN_CONFIG_MANAGEER, getMainConfigManager());
        this.onContextInitialized();
    }
    public abstract void onContextInitialized();
    public abstract void oncontextDestroyed();
    public void contextDestroyed(ServletContextEvent arg0) {
        this.oncontextDestroyed();
    }
}
