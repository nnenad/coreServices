/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.web.core.migration;

import java.util.ArrayList;
import com.java.web.core.Constants;

/**
 *
 * @author Nenad
 */
public class MigrationExecutor {
    public static Result executeUp(String migrationType) throws Exception{
        MigrationManager mngr = new MigrationManager(MigrationManager.getDefaultConfiguration(migrationType));
            Result result = null;
            if(mngr.getConfig().getMigrationType().equals(Constants.MIGRATION_TYPE_DOWN))
            {
                result = mngr.masterDown();
            }
            else
            {
                result = mngr.masterUp();
            }
            return result;

    }
    public static Result executeTestMigUp(String[] migrationsToexecute , String propertyFileLocation) throws Exception{
        MigrationManager mngr = new MigrationManager(MigrationManager.getDefaultTestPropertiesConfiguration(propertyFileLocation));
            Result result = null;
                result = mngr.masterUpTest(migrationsToexecute);
                return result;

    }
}
