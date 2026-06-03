package com.dao;

import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NoticeReadDao extends BaseDao {

    public int markRead(Integer userId, Integer noticeId) {
        String sql = "INSERT IGNORE INTO notice_read(user_id, notice_id, read_time) VALUES(?, ?, NOW())";
        return executeUpdate(sql, userId, noticeId);
    }

    public int countUnread(Integer userId) {
        String sql = "SELECT COUNT(*) FROM notice n "
                + "WHERE NOT EXISTS (SELECT 1 FROM notice_read r WHERE r.notice_id = n.id AND r.user_id = ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return 0;
    }

    public void deleteByNoticeId(Integer noticeId) {
        String sql = "DELETE FROM notice_read WHERE notice_id=?";
        executeUpdate(sql, noticeId);
    }
}
