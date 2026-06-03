package com.dao;

import com.bean.Repair;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RepairDao extends BaseDao {

    private Repair mapRepair(ResultSet rs) throws Exception {
        Repair r = new Repair();
        r.setId(rs.getInt("id"));
        r.setTitle(rs.getString("title"));
        r.setType(rs.getString("type"));
        r.setContent(rs.getString("content"));
        r.setCreateTime(rs.getTimestamp("create_time"));
        Timestamp updateTime = rs.getTimestamp("update_time");
        r.setUpdateTime(updateTime);
        r.setStatus(rs.getString("status"));
        r.setReply(rs.getString("reply"));
        int rating = rs.getInt("rating");
        r.setRating(rs.wasNull() ? null : rating);
        r.setEvaluation(rs.getString("evaluation"));
        r.setUserId(rs.getInt("user_id"));
        return r;
    }

    public int addRepair(Repair repair) {
        String sql = "INSERT INTO repair(title, type, content, create_time, update_time, status, user_id) "
                + "VALUES(?, ?, ?, NOW(), NOW(), ?, ?)";
        Object[] params = {
                repair.getTitle(),
                repair.getType(),
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
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRepair(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    public List<Repair> findAllRepair() {
        return queryList("SELECT * FROM repair ORDER BY create_time DESC");
    }

    public List<Repair> findRepairByUserId(Integer userId) {
        return queryList("SELECT * FROM repair WHERE user_id=? ORDER BY create_time DESC", userId);
    }

    private List<Repair> queryList(String sql, Object... params) {
        List<Repair> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRepair(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    public int updateRepair(Repair repair) {
        String sql = "UPDATE repair SET status=?, reply=?, update_time=NOW() WHERE id=?";
        Object[] params = {repair.getStatus(), repair.getReply(), repair.getId()};
        return executeUpdate(sql, params);
    }

    public int evaluateRepair(Integer id, Integer userId, Integer rating, String evaluation) {
        String sql = "UPDATE repair SET rating=?, evaluation=?, update_time=NOW() "
                + "WHERE id=? AND user_id=? AND status='已完成' AND rating IS NULL";
        return executeUpdate(sql, rating, evaluation, id, userId);
    }

    public int deleteRepair(Integer id) {
        return executeUpdate("DELETE FROM repair WHERE id=?", id);
    }

    public int deleteRepairByOwner(Integer id, Integer userId) {
        return executeUpdate("DELETE FROM repair WHERE id=? AND user_id=? AND status='待处理'", id, userId);
    }
}
