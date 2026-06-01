package com.example.util;
import java.sql.*;
public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/property";
    private static final String USER = "root";
    private static final String PWD = "123456";

    // 获取连接
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // 关闭资源
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // 关闭代码
    }
}