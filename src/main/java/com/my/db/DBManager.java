package com.my.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    private DataSource ds;

    private DBManager(){
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env/");
            ds = (DataSource) envContext.lookup("jdbc/DBforProject");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        Connection con = null;

        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return con;
    }

    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Closes a connection.
     *
     * @param con Connection to be closed.
     */
    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * close Statment
     *
     * @param stmt
     */
    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * close ResultSet
     *
     * @param rs
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Close Connection,Statment,ResultSet
     *
     * @param con
     * @param stmt
     * @param rs
     */
    void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Rollbacks a connection.
     *
     * @param con Connection to be rollbacked.
     */
    public void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
