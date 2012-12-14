
package com.java.web.core.migration;

import java.sql.*;

public abstract class MigrationBase {

    private Connection _connection;

    public Connection getConnection() {
        return this._connection;
    }
    public void setConnection(Connection con) {
        this._connection = con;
    }


    public int update(String sql) throws SQLException {
        Statement stmt = _connection.createStatement();
        String queryArray[] = sql.split(";");
        for(int i = 0; i < queryArray.length; i++){
            stmt.addBatch(queryArray[i]);
        }
        stmt.executeBatch();
        //int result = stmt.executeUpdate(sql);
        return 0;
    }
    public int update(String sql,String delimiter) throws SQLException {
        Statement stmt = _connection.createStatement();
        String queryArray[] = null;
        if(delimiter != null && !delimiter.isEmpty())
            queryArray = sql.split(delimiter);
        else
           queryArray = sql.split(";"); 
        for(int i = 0; i < queryArray.length; i++){
            stmt.addBatch(queryArray[i]);
        }
        stmt.executeBatch();
        //int result = stmt.executeUpdate(sql);
        return 0;
    }
    public ResultSet query(String sql) throws SQLException {
        Statement stmt = _connection.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        return result;
    }

    public abstract void up() throws SQLException;
    public abstract void down() throws SQLException;
    
}
