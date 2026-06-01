package com.dao;

import com.entity.Notice;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoticeDao extends BaseDao {

    public int addNotice(Notice notice) {
        String sql = "INSERT INTO notice(title,content,create_time,publisher) VALUES(?,?,NOW(),?)";
        Object[] params = {notice.getTitle(), notice.getContent(), notice.getPublisher()};
        return executeUpdate(sql, params);
    }

    public Notice findNoticeById(Integer id) {
        String sql = "SELECT * FROM notice WHERE id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Notice n = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                n = new Notice();
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreateTime(rs.getDate("create_time"));
                n.setPublisher(rs.getString("publisher"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return n;
    }

    public List<Notice> findAllNotice() {
        List<Notice> list = new ArrayList<>();
        String sql = "SELECT * FROM notice ORDER BY create_time DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Notice n = new Notice();
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreateTime(rs.getDate("create_time"));
                n.setPublisher(rs.getString("publisher"));
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    public int updateNotice(Notice notice) {
        String sql = "UPDATE notice SET title=?,content=?,publisher=? WHERE id=?";
        Object[] params = {notice.getTitle(), notice.getContent(), notice.getPublisher(), notice.getId()};
        return executeUpdate(sql, params);
    }

    public int deleteNotice(Integer id) {
        String sql = "DELETE FROM notice WHERE id=?";
        return executeUpdate(sql, id);
    }
}
