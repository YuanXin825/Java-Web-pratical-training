package com.dao;

import com.entity.Repair;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RepairDao extends BaseDao {

    public int addRepair(Repair repair) {
        String sql = "INSERT INTO repair(title,content,create_time,status,user_id) VALUES(?,?,NOW(),?,?)";
        Object[] params = {
                repair.getTitle(),
                repair.getContent(),
                repair.getStatus(),
                repair.getUserId()
        };
        return executeUpdate(sql, params);
    }

    public Repair findRepairById(Integer id) {
        String sql = "SELECT * FROM repair WHERE id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Repair r = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                r = new Repair();
                r.setId(rs.getInt("id"));
                r.setTitle(rs.getString("title"));
                r.setContent(rs.getString("content"));
                r.setCreateTime(rs.getDate("create_time"));
                r.setStatus(rs.getString("status"));
                r.setUserId(rs.getInt("user_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return r;
    }

    public List<Repair> findAllRepair() {
        List<Repair> list = new ArrayList<>();
        String sql = "SELECT * FROM repair ORDER BY create_time DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Repair r = new Repair();
                r.setId(rs.getInt("id"));
                r.setTitle(rs.getString("title"));
                r.setContent(rs.getString("content"));
                r.setCreateTime(rs.getDate("create_time"));
                r.setStatus(rs.getString("status"));
                r.setUserId(rs.getInt("user_id"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    public int updateRepair(Repair repair) {
        String sql = "UPDATE repair SET status=? WHERE id=?";
        Object[] params = {repair.getStatus(), repair.getId()};
        return executeUpdate(sql, params);
    }

    public int deleteRepair(Integer id) {
        String sql = "DELETE FROM repair WHERE id=?";
        return executeUpdate(sql, id);
    }
}
