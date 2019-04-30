package com.liuming.sorm.core;

import com.liuming.sorm.bean.Configuration;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 根据配置信息,维持连接对象的管理(增加连接池功能)
 */
public class DBManager {
    private static Configuration configuration;

    static {
        Properties pro = new Properties();
        try {
            pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration = new Configuration();
        configuration.setDriver(pro.getProperty("driver"));
        configuration.setUrl(pro.getProperty("url"));
        configuration.setUser(pro.getProperty("user"));
        configuration.setPwd(pro.getProperty("pwd"));
        configuration.setUsingDB(pro.getProperty("usingDB"));
        configuration.setSrcPath(pro.getProperty("srcPath"));
        configuration.setPoPackage(pro.getProperty("poPackage"));
    }

    /**
     * 直接建立连接,后续增加连接池处理,提高效率
     * @return 连接
     */
    public static Connection getConnection() {
        try {
            System.out.println(configuration);
//            Class.forName(configuration.getDriver());no move need
            return DriverManager.getConnection(
                    configuration.getUrl(),
                    configuration.getUser(),
                    configuration.getPwd());
        } catch ( SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(ps, conn);
    }

    public static void close(Statement ps, Connection conn) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(conn);
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Configuration getConf() {
        return configuration;
    }
}
