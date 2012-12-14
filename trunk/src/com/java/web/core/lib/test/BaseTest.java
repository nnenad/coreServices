/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.lib.test;

import com.java.web.core.config.managers.MainConfigManager;
import org.junit.Rule;

/**
 *
 * @author Nenad
 */
public abstract class BaseTest {
    @Rule
    public InitTestRule initTestRule = new InitTestRule();
    public abstract String getMigrationPropertiesFileLocation();
    public abstract String getTestDbHibernateConfigurationLocation(); 

    public void initTest() {
        getWebSimulator();
    }

    public abstract AbstractWebSimulator getWebSimulator();
    public abstract MainConfigManager getMainConfigManager();

}
