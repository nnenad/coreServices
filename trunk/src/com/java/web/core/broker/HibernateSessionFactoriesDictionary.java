/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.web.core.broker;

import com.java.web.core.config.managers.InstanceManager;
import com.java.web.core.config.managers.JNDIBasedParams;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.ConfigurationException;
import javax.naming.NamingException;
import org.hibernate.SessionFactory;
import com.java.web.core.domain.access.Product;
import com.java.web.core.lib.http.HttpContext;
/**
 *
 * @author Vuk
 */
public class HibernateSessionFactoriesDictionary {

    private static volatile SessionFactory masterSessionFactory = null;
    private static volatile HashMap<String, SessionFactory> slaveSessionFactories = new HashMap<String, SessionFactory>();

    
    public static synchronized SessionFactory getMasterFactory() throws NamingException {
        if (masterSessionFactory ==  null) {
            masterSessionFactory = createMasterFactory();
        }
        return masterSessionFactory;
    }
    public static synchronized SessionFactory getMasterFactoryNoWebSession(String appId) throws NamingException {
        if (masterSessionFactory ==  null) {
            masterSessionFactory = createMasterFactoryNoWebSession(appId);
        }
        return masterSessionFactory;
    }
    public static synchronized org.hibernate.SessionFactory getSlaveFactory() {
        Product product = InstanceManager.getProductInstanceFromSession();
        org.hibernate.SessionFactory result = getSlaveFactory(product);
        return result;
    }
    public static synchronized org.hibernate.SessionFactory getSlaveFactory(Product product) {
        String url = product.getUrl().toLowerCase();
        org.hibernate.SessionFactory result = slaveSessionFactories.get(url);
        if(result ==  null)
        {
            result = createSlaveSessionFactory(product);
            slaveSessionFactories.put(url, result);
        }
        return result;
    }

    private static org.hibernate.SessionFactory createMasterFactory() throws NamingException {
        SessionFactory sessionFactory = null;
        String connectionUrl = null;
        String username = null;
        String password = null;
        ArrayList<String> mappings = new ArrayList<String>();
        HashMap<String, String> globalParams = JNDIBasedParams.current();
        for (String key : globalParams.keySet()) {
            if (key.equals("db.url"))
                connectionUrl = globalParams.get(key);
            else if (key.equals("db.username"))
                username = globalParams.get(key);
            else if (key.equals("db.password"))
                password = globalParams.get(key);
            else if (key.startsWith("master.hbm")) {
                mappings.add(globalParams.get(key));
            }
        }
        if (connectionUrl != null && username != null && password != null) {
            sessionFactory = SessionFactoryCreator.createSessionFactory(connectionUrl, username, password, mappings);
        } else {
            throw new ConfigurationException("JNDI not set");
        }
        return sessionFactory;
    }
    private static org.hibernate.SessionFactory createMasterFactoryNoWebSession(String appId) throws NamingException {
        SessionFactory sessionFactory = null;
        String connectionUrl = null;
        String username = null;
        String password = null;
        ArrayList<String> mappings = new ArrayList<String>();
        HashMap<String, String> globalParams = JNDIBasedParams.currentNoWebSession(appId);
        for (String key : globalParams.keySet()) {
            if (key.equals("db.url"))
                connectionUrl = globalParams.get(key);
            else if (key.equals("db.username"))
                username = globalParams.get(key);
            else if (key.equals("db.password"))
                password = globalParams.get(key);
            else if (key.startsWith("master.hbm")) {
                mappings.add(globalParams.get(key));
            }
        }
        if (connectionUrl != null && username != null && password != null) {
            sessionFactory = SessionFactoryCreator.createSessionFactory(connectionUrl, username, password, mappings);
        } else {
            throw new ConfigurationException("JNDI not set");
        }
        return sessionFactory;
    }

    private static org.hibernate.SessionFactory createSlaveSessionFactory(Product product) {
        ArrayList<String> mappings = new ArrayList<String>();
        int i = 1;
        while (true) {
            if (!HttpContext.runtimePropertiesContainsKey("slave.hbm." + i)) {
                break;
            } else {
                String txt = HttpContext.runtimePropertiesGetProperty("slave.hbm." + i);
                mappings.add(txt);
            }
            i++;
        }
        return  SessionFactoryCreator.createSessionFactory(product.getConnstring(), product.getDbusername(), product.getDbpassword(), mappings);
    }

}
