
package com.java.web.core.migration;

public class Configuration {

    private String migrationPackage;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private String verstionTable;
    private String migrationType;
    private String JwcLocation;

    public String getMigrationPackage() {
        return migrationPackage;
    }
    public void setMigrationPackage(String migrationPackage) {
        this.migrationPackage = migrationPackage;
    }


    public String getDbUrl() {
        return dbUrl;
    }
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }


    public String getDbUsername() {
        return dbUsername;
    }
    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }


    public String getDbPassword() {
        return dbPassword;
    }
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }


    public String getVerstionTable() {
        return verstionTable;
    }
    public void setVerstionTable(String verstionTable) {
        this.verstionTable = verstionTable;
    }

    public String getMigrationType() {
        return migrationType;
    }

    public void setMigrationType(String migrationType) {
        this.migrationType = migrationType;
    }

    /**
     * @return the JwcLocation
     */
    public String getJwcLocation() {
        return JwcLocation;
    }

    /**
     * @param JwcLocation the JwcLocation to set
     */
    public void setJwcLocation(String JwcLocation) {
        this.JwcLocation = JwcLocation;
    }

    
    

}
