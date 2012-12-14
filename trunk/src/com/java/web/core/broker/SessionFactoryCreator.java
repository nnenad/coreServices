/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.broker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Vuk
 */
public class SessionFactoryCreator {

    public static SessionFactory createSessionFactory(String connectionUrl, String username, String password,
            ArrayList<String> mappings) {
        Properties prop = new Properties();
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        prop.put("hibernate.connection.url", connectionUrl);
        prop.put("hibernate.connection.username", username);
        prop.put("hibernate.connection.password", password);
        prop.put("hibernate.connection.characterEncoding", "utf8");

        prop.put("hibernate.connection.pool_size", "0");

        prop.put("transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
		prop.put("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
        Configuration config = new Configuration();
        config.addProperties(prop);
        Iterator<String> it = mappings.iterator();
        while(it.hasNext())
        {
            String txt = it.next();
            config.addResource(txt);
        }
        return config.buildSessionFactory();

    }
}
