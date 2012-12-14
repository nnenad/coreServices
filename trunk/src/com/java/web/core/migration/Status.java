package com.java.web.core.migration;

import java.util.ArrayList;

public class Status {
    private MigrationWrapper latestMigration;
    private ArrayList<MigrationWrapper> allMigrations = new ArrayList<MigrationWrapper>();
    private ArrayList<MigrationWrapper> executedMigrations = new ArrayList<MigrationWrapper>();
    private ArrayList<MigrationWrapper> pendingMigrations = new ArrayList<MigrationWrapper>();

    public Status(MigrationWrapper latest, ArrayList<MigrationWrapper> all, ArrayList<MigrationWrapper> executed, ArrayList<MigrationWrapper> pending) {
        this.latestMigration = latest;
        this.allMigrations = all;
        this.executedMigrations = executed;
        this.pendingMigrations = pending;
    }

    public MigrationWrapper getLatestMigration() {
        return latestMigration;
    }

    public ArrayList<MigrationWrapper> getAllMigrations() {
        return allMigrations;
    }

    public ArrayList<MigrationWrapper> getPendingMigrations() {
        return pendingMigrations;
    }

    public ArrayList<MigrationWrapper> getExecutedMigrations() {
        return executedMigrations;
    }

}
