package com.dao;

import com.bean.NoticeCategory;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoticeCategoryDao extends BaseDao {

    public List<NoticeCategory> findAll() {
        List<NoticeCategory> list = new ArrayList<>();
        String sql = "SELECT id, name, sort_order, remark, create_time FROM notice_category ORDER BY sort_order, id";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapCategory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    public NoticeCategory findById(Integer id) {
        String sql = "SELECT id, name, sort_order, remark, create_time FROM notice_category WHERE id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapCategory(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    public int add(NoticeCategory category) {
        String sql = "INSERT INTO notice_category(name, sort_order, remark) VALUES(?, ?, ?)";
        return executeUpdate(sql, category.getName(), category.getSortOrder(), category.getRemark());
    }

    public int update(NoticeCategory category) {
        String sql = "UPDATE notice_category SET name=?, sort_order=?, remark=? WHERE id=?";
        return executeUpdate(sql, category.getName(), category.getSortOrder(), category.getRemark(), category.getId());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM notice_category WHERE id=?";
        return executeUpdate(sql, id);
    }

    private NoticeCategory mapCategory(ResultSet rs) throws Exception {
        NoticeCategory c = new NoticeCategory();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setSortOrder(rs.getInt("sort_order"));
        c.setRemark(rs.getString("remark"));
        c.setCreateTime(rs.getTimestamp("create_time"));
        return c;
    }
}
