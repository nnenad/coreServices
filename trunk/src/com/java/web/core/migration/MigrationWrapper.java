
package com.java.web.core.migration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;


public class MigrationWrapper implements Comparable<MigrationWrapper>  {

    private Class _class;
    private MigrationVersion _ver;
    private String _name;
    
    public MigrationWrapper(Class cls) {
        this._class = cls;
        this._ver = MigrationManager.getVersionFromClass(this._class);
    }

    public MigrationVersion getVersion() {
        if (this._ver == null)
            this._ver = MigrationManager.getVersionFromClass(this._class);
        return _ver;
    }

    public String getName() {
        if (_name == null)
            this._name = MigrationManager.getNameFromClass(this._class);
        return _name;
    }

    public boolean isValid() {
        boolean validType = false;
        try {
            //package changed
            validType = Class.forName("com.java.web.core.migration.MigrationBase").isAssignableFrom(this._class);
        } catch (ClassNotFoundException ex) { }
        if (!validType) return false;
        
        boolean validVersion = this.getVersion() != null;
        return validVersion;
    }

    protected MigrationBase createInstance(Connection con) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor ctor = this._class.getConstructor();
        MigrationBase migration = (MigrationBase) ctor.newInstance();
        migration.setConnection(con);
        return migration;
    }
    public void up(Connection con) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        MigrationBase migration = this.createInstance(con);
        migration.up();
    }
    public void down(Connection con) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        MigrationBase migration = this.createInstance(con);
        migration.down();
    }

    public int compareTo(MigrationWrapper o) {
        if (o == null) return 1;
        MigrationVersion a = this.getVersion();
        MigrationVersion b = o.getVersion();
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        int result = a.compareTo(b);
        return result;
    }







}
