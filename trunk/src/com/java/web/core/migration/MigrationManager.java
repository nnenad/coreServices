package com.java.web.core.migration;

import com.java.web.core.Constants;
import com.java.web.core.config.managers.JNDIBasedParams;
import com.java.web.core.lib.test.MigrationParams;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import javax.naming.NamingException;

public class MigrationManager {

    private Configuration config;

    public MigrationManager(Configuration cfg) {
        this.config = cfg;
    }

    // Public Methods -------------------------------------------------------------------------------------
    public Status getMasterStatus() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> executedList = this.getExecutedList();
        ArrayList<MigrationWrapper> allmigrations = this.getMigrationClasses();
        MigrationWrapper latest = null;
        ArrayList<MigrationWrapper> executed = new ArrayList<MigrationWrapper>();
        ArrayList<MigrationWrapper> pending = new ArrayList<MigrationWrapper>();
        for (Iterator i = allmigrations.iterator(); i.hasNext();) {
            MigrationWrapper w = (MigrationWrapper) i.next();
            String ver = w.getVersion().toString();

            boolean isExecuted = false;
            for (Iterator j = executedList.iterator(); j.hasNext();) {
                String s = (String) j.next();
                if (s.equals(ver)) {
                    isExecuted = true;
                    break;
                }
            }

            if (isExecuted) {
                executed.add(w);
                latest = w;
            } else {
                pending.add(w);
            }
        }
        Status result = new Status(latest, allmigrations, executed, pending);
        return result;
    }

    public Result masterUp() throws ClassNotFoundException, IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Result result = new Result();
        Status status = this.getMasterStatus();
        for (Iterator i = status.getPendingMigrations().iterator(); i.hasNext();) {
            MigrationWrapper w = (MigrationWrapper) i.next();
            Connection con = this.getConnection();
            Exception error = null;
            try {
                w.up(con);
                this.writeVersion(con, w);
            } catch (Exception ex) {
                error = ex;
            } finally {
                if (!con.isClosed()) {
                    con.close();
                }
            }
            MigrationResult mr = new MigrationResult(w, error);
            result.add(mr);
            if (error != null) {
                break;
            }
        }
        return result;
    }

    public Result masterDown() throws ClassNotFoundException, IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Result result = new Result();
        Status status = this.getMasterStatus();
        for (Iterator i = status.getPendingMigrations().iterator(); i.hasNext();) {
            MigrationWrapper w = (MigrationWrapper) i.next();
            Connection con = this.getConnection();
            Exception error = null;
            try {
                w.down(con);
            } catch (Exception ex) {
                error = ex;
            } finally {
                if (!con.isClosed()) {
                    con.close();
                }
            }
            MigrationResult mr = new MigrationResult(w, error);
            result.add(mr);
            if (error != null) {
                break;
            }
        }
        return result;
    }

    public Result masterUpTest(String[] migrationsToExecute) throws ClassNotFoundException, IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Result result = new Result();
        Status status = this.getMasterStatus();
        for (Iterator i = status.getPendingMigrations().iterator(); i.hasNext();) {
            MigrationWrapper w = (MigrationWrapper) i.next();
            Connection con = this.getConnection();
            Exception error = null;
            try {
                for(String migrationToExecute: migrationsToExecute){
                    if(w.getName().equals(migrationToExecute)){
                        w.up(con);
                        this.writeVersion(con, w);
                    }
                }
            } catch (Exception ex) {
                error = ex;
            } finally {
                if (!con.isClosed()) {
                    con.close();
                }
            }
            MigrationResult mr = new MigrationResult(w, error);
            result.add(mr);
            if (error != null) {
                break;
            }
        }
        return result;
    }

    // Private Methods -------------------------------------------------------------------------------------
    private ArrayList<String> getExecutedList() throws SQLException, ClassNotFoundException {
        ArrayList<String> result = new ArrayList<String>();
        Connection con = this.getConnection();
        this.checkTable(con);
        Statement stmt = con.createStatement();
        //where 'type'='%s'"    , this.config.getMigrationType()
        ResultSet rs = stmt.executeQuery(String.format("select ver from %s where migType='%s'", this.getConfig().getVerstionTable(), this.getConfig().getMigrationType()));
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        rs.close();
        return result;
    }

    private void writeVersion(Connection con, MigrationWrapper w) throws SQLException {
        this.checkTable(con);
        Statement stmt = con.createStatement();
        String sql = String.format("insert into %s (ver,migType) values ('%s', '%s')", this.getConfig().getVerstionTable(), w.getVersion().toString(), this.getConfig().getMigrationType());
        stmt.executeUpdate(sql);
    }

    private void checkTable(Connection con) throws SQLException {
        String sql = "select * from "+this.getConfig().getVerstionTable()+" where 1=2";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(sql);
        } catch (Exception ex) {
            Statement stmt = con.createStatement();
            if (this.getConfig().getMigrationType().equals(Constants.MIGRATION_TYPE_TEST)) {
                sql = "CREATE TABLE "+this.getConfig().getVerstionTable()+" (ver varchar(20) NOT NULL, ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (ver), migType varchar(10) ) ";
            } else {
                sql = String.format("CREATE TABLE "+this.getConfig().getVerstionTable()+" (" +
                        "`ver` varchar(20) NOT NULL," +
                        "`ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "`migType` varchar(10) NOT NULL," +
                        "PRIMARY KEY (`ver` ,`migType`)" +
                        ") ENGINE=MyISAM DEFAULT CHARSET=latin1;",
                        this.getConfig().getVerstionTable());

            }
            stmt.executeUpdate(sql);
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        if (getConfig().getMigrationType().equals(Constants.MIGRATION_TYPE_TEST)) {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } else {
            Class.forName("com.mysql.jdbc.Driver");
        }
        String url = this.getConfig().getDbUrl();  // "jdbc:mysql://192.168.1.201:3306/webwiz_demo";
        String un = this.getConfig().getDbUsername();
        String pw = this.getConfig().getDbPassword();
        java.sql.Connection con = DriverManager.getConnection(url, un, pw);
        return con;

    }

    private ArrayList<MigrationWrapper> getMigrationClasses() throws ClassNotFoundException, IOException {
        ArrayList<Class> allClasses = getClasses(this.getConfig().getMigrationPackage());
        ArrayList<MigrationWrapper> result = new ArrayList<MigrationWrapper>();
        for (Iterator<Class> i = allClasses.iterator(); i.hasNext();) {
            Class cls = i.next();
            MigrationWrapper w = new MigrationWrapper(cls);
            if (w.isValid()) {
                result.add(w);
            }
        }
        java.util.Collections.sort(result); // ??? , new MigrationComparator());
        return result;
    }

    // Static Methods --------------------------------------------------------------------------------
    public static Configuration getDefaultConfiguration(String migrationType) throws NamingException {
        Configuration result = new Configuration();

        if (migrationType.equals(Constants.MIGRATION_TYPE_APP)) {
            result.setMigrationPackage(JNDIBasedParams.current().get("migration.master.package"));        // com.evolution.mobile.migration.master
        }
        if (migrationType.equals(Constants.MIGRATION_TYPE_DOWN)) {
            result.setMigrationPackage(JNDIBasedParams.current().get("migration.down.package"));        // com.evolution.mobile.migration.down
        }
        if (migrationType.equals(Constants.MIGRATION_TYPE_CORE)) {
            result.setMigrationPackage(JNDIBasedParams.current().get("migration.core.package"));         // com.java.web.core.migrations.executables
            result.setJwcLocation(JNDIBasedParams.current().get("jwc.jar.location"));
        }
        //Add specific parameters for test package
        if (migrationType.equals(Constants.MIGRATION_TYPE_TEST)) {
            result.setMigrationPackage(JNDIBasedParams.current().get("migration.test.package"));
            result.setVerstionTable(JNDIBasedParams.current().get("migration.dbVersion")); 
            result.setMigrationType(migrationType);
            result.setDbUrl(JNDIBasedParams.current().get("test.db.url"));                               //jdbc:mysql://localhost:3306/master_gips_mobile
            result.setDbUsername(JNDIBasedParams.current().get("test.db.username"));                     //test
            result.setDbPassword(JNDIBasedParams.current().get("test.db.password"));                     //test
            return result;
        }

        result.setVerstionTable(JNDIBasedParams.current().get("migration.dbVersion"));                  //dbVersion
        result.setMigrationType(migrationType);
        result.setDbUrl(JNDIBasedParams.current().get("db.url"));                                       //jdbc:mysql://localhost:3306/master_gips_mobile
        result.setDbUsername(JNDIBasedParams.current().get("db.username"));
        result.setDbPassword(JNDIBasedParams.current().get("db.password"));


        return result;
    }
    public static Configuration getDefaultTestPropertiesConfiguration(String propertyFileLocation){
        MigrationParams.propertyFileLocation = propertyFileLocation;
        Configuration result = new Configuration();
        result.setMigrationPackage(MigrationParams.getMigrationParamsInstance().getProperty("migration.test.package"));
            result.setVerstionTable(MigrationParams.getMigrationParamsInstance().getProperty("migration.dbVersion"));
            result.setMigrationType(Constants.MIGRATION_TYPE_TEST);
            result.setDbUrl(MigrationParams.getMigrationParamsInstance().getProperty("test.db.url"));                               //jdbc:mysql://localhost:3306/master_gips_mobile
            result.setDbUsername(MigrationParams.getMigrationParamsInstance().getProperty("test.db.username"));                     //test
            result.setDbPassword(MigrationParams.getMigrationParamsInstance().getProperty("test.db.password"));                     //test
            return result;
    }

    public static String getNameFromClass(Class cls) {
        String name = cls.getName();
        if (name.contains(".")) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }
        return name;
    }

    public static MigrationVersion getVersionFromClass(Class cls) {
        MigrationVersion result = null;
        try {
            String name = getNameFromClass(cls);
            result = new MigrationVersion(name);
        } catch (Exception ex) {
        }
        return result;
    }

    private ArrayList<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {

        String path = packageName.replace('.', '/');
        ArrayList<Class> classes = new ArrayList<Class>();
        if (this.getConfig().getMigrationType().equals(Constants.MIGRATION_TYPE_CORE)) {
            getJWCJarClasses(classes, packageName);
        } else {     
                getVMClasses(classes, packageName, path);
        }
        return classes;
    }

    private void getVMClasses(ArrayList<Class> classes, String packageName, String path) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
    }

    private void getJWCJarClasses(ArrayList<Class> classes, String packageName) throws ClassNotFoundException {
        ArrayList<String> result = (ArrayList<String>) getClasseNamesInPackage(this.getConfig().getJwcLocation(), packageName);
        for (String className : result) {
            Class resultClass = Class.forName(className);
            classes.add(resultClass);
        }
    }

    public List getClasseNamesInPackage(String jarName, String packageName) {
        ArrayList classes = new ArrayList();

        packageName = packageName.replaceAll("\\.", "/");
        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if ((jarEntry.getName().startsWith(packageName)) &&
                        (jarEntry.getName().endsWith(".class"))) {
                    String className = jarEntry.getName().replaceAll("/", "\\.");
                    className = className.replaceAll(".class", "");
                    classes.add(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory() && !file.getName().toUpperCase().contains(".svn".toUpperCase())) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * @return the config
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(Configuration config) {
        this.config = config;
    }
}
