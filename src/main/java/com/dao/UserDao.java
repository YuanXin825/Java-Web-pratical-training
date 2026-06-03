package com.dao;

import com.bean.User;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * 根据 id 查用户，并关联 role 表取出 role_name 作为 role
     */
    public User findById(Integer id) throws SQLException {
        String sql = "SELECT u.id, u.username, u.name, u.phone, u.gender, u.role_id, r.role_name "
                + "FROM `user` u LEFT JOIN `role` r ON u.role_id = r.id WHERE u.id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                throw new SQLException("数据库连接失败，请检查 DBUtil 中的地址、库名、账号密码是否与 Navicat 一致");
            }
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setGender(rs.getString("gender"));
                user.setRoleId(rs.getInt("role_id"));
                String roleName = rs.getString("role_name");
                if (roleName == null || roleName.trim().isEmpty()) {
                    int roleId = rs.getInt("role_id");
                    if (roleId == 1) {
                        roleName = "admin";
                    } else if (roleId == 2) {
                        roleName = "owner";
                    }
                }
                user.setRole(roleName != null ? roleName.trim() : null);
                return user;
            }
            return null;
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }

    public boolean existsById(Integer id) {
        try {
            return findById(id) != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
