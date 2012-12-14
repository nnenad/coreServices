/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.lib.test;

import com.java.web.core.lib.test.ExecutesMigrations;
import com.java.web.core.migration.MigrationExecutor;
import com.java.web.core.migration.MigrationResult;
import com.java.web.core.migration.Result;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.java.web.core.Constants;
import com.java.web.core.lib.http.HttpContext;

/**
 *
 * @author Nenad
 */
public final class InitTestRule implements MethodRule {

    public boolean migrationExecutionCheck = true;

    public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                BaseTest baseTestImpl = (BaseTest)o;
                ExecutesMigrations executesMigrations = frameworkMethod.getAnnotation(ExecutesMigrations.class);
                if (executesMigrations == null) {
                    statement.evaluate();
                } else {
                    baseTestImpl.getWebSimulator().initHttpContext("localhost");
                    HttpContext.setApplicationVar(Constants.MAIN_CONFIG_MANAGEER, baseTestImpl.getMainConfigManager());
                    baseTestImpl.getMainConfigManager().getBaseBroker().setTestHibernateConfigurationLocation(
                            baseTestImpl.getTestDbHibernateConfigurationLocation());
                    String[] migrationsToExecute = executesMigrations.migrationsToExecute();
                    Result result = MigrationExecutor.executeTestMigUp(migrationsToExecute,baseTestImpl.getMigrationPropertiesFileLocation());
                    migrationExecutionCheck = true;
                    for (Iterator<MigrationResult> i = result.iterator(); i.hasNext();) {
                        MigrationResult r = i.next();
                        boolean isOK = r.isOK();
                        if (isOK) {
                            Logger.getLogger(o.getClass().getName()).log(Level.INFO, "OK - ");
                            Logger.getLogger(o.getClass().getName()).log(Level.INFO, frameworkMethod.getName());                           
                            Logger.getLogger(o.getClass().getName()).log(Level.INFO, r.getMigration().getName());
                        } else {
                            migrationExecutionCheck = false;
                            Logger.getLogger(o.getClass().getName()).log(Level.SEVERE, "ERROR - ");
                            Logger.getLogger(o.getClass().getName()).log(Level.SEVERE, frameworkMethod.getName());                            

                        }
                        if (!isOK) {
                            Logger.getLogger(o.getClass().getName()).log(Level.SEVERE, r.getMigration().getName(), r.getError());
                        }
                    }
                    if(migrationExecutionCheck){
                        statement.evaluate();
                    }
                }
            }
        };
    }
}
