/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.web.core.migration;

/**
 *
 * @author Dell
 */
public class MigrationResult {

    private MigrationWrapper wrapper;
    private Exception error;

    public MigrationResult(MigrationWrapper w, Exception ex) {
        this.wrapper = w;
        this.error = ex;
    }

    public MigrationWrapper getMigration() {
        return this.wrapper;
    }
    public boolean isOK() {
        return this.error == null;
    }
    public Exception getError() {
        return this.error;
    }
    

}
