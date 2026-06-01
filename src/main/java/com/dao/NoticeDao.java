package com.dao;

import com.entity.Notice;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoticeDao extends BaseDao {

    // 新增公告
    public int addNotice(Notice notice) {
        String sql = "INSERT INTO notice(admin_id,title,content,create_time,is_top) VALUES(?,?,?,NOW(),?)";
        Object[] params = {notice.getAdminId(), notice.getTitle(), notice.getContent(), notice.getIsTop()};
        return executeUpdate(sql, params);
    }

    // 根据ID查询公告
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
                n.setAdminId(rs.getInt("admin_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreateTime(rs.getDate("create_time"));
                n.setIsTop(rs.getInt("is_top"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return n;
    }

    // 查询所有公告（置顶优先）
    public List<Notice> findAllNotice() {
        List<Notice> list = new ArrayList<>();
        String sql = "SELECT * FROM notice ORDER BY is_top DESC,create_time DESC";
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
                n.setAdminId(rs.getInt("admin_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreateTime(rs.getDate("create_time"));
                n.setIsTop(rs.getInt("is_top"));
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // 修改公告
    public int updateNotice(Notice notice) {
        String sql = "UPDATE notice SET title=?,content=?,is_top=? WHERE id=?";
        Object[] params = {notice.getTitle(), notice.getContent(), notice.getIsTop(), notice.getId()};
        return executeUpdate(sql, params);
    }

    // 删除公告
    public int deleteNotice(Integer id) {
        String sql = "DELETE FROM notice WHERE id=?";
        return executeUpdate(sql, id);
    }
}