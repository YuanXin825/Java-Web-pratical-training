package com.util;

import java.sql.*;

public class DBUtil {
    // 数据库连接信息（改成你自己的）
    private static final String URL = "jdbc:mysql://localhost:3306/property?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    /**
     * 获取数据库连接
     * @return Connection
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("找不到数据库驱动！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭资源
     * @param conn  连接
     * @param pstmt PreparedStatement
     * @param rs    结果集
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}