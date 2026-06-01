package com.dao;

import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {

    /**
     * 增删改通用方法（INSERT/UPDATE/DELETE）
     * @param sql  SQL语句，带?占位符
     * @param params ?对应的参数列表
     * @return 受影响的行数
     */
    public int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            // 设置参数
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            // 执行更新
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            DBUtil.close(conn, pstmt, null);
        }
        return result;
    }

    /**
     * 查询通用方法（SELECT）
     * @param sql  SQL语句，带?占位符
     * @param params ?对应的参数列表
     * @return ResultSet结果集（用完记得关闭）
     */
    public ResultSet executeQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            // 设置参数
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            // 执行查询
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 注意：这里不能在finally里关闭conn和pstmt，否则rs会失效
        // 所以需要在调用executeQuery的地方，用完rs后再手动关闭
        return rs;
    }
}