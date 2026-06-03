package com.dao;

import com.bean.Notice;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NoticeDao extends BaseDao {

    private static final String BASE_SELECT =
            "SELECT n.id, n.title, n.content, n.category_id, c.name AS category_name, "
                    + "n.is_top, n.create_time, n.update_time, n.publisher, n.publisher_id ";

    private static final String BASE_FROM =
            "FROM notice n LEFT JOIN notice_category c ON n.category_id = c.id ";

    public int addNotice(Notice notice) {
        String sql = "INSERT INTO notice(title, content, category_id, is_top, create_time, update_time, publisher, publisher_id) "
                + "VALUES(?, ?, ?, ?, NOW(), NOW(), ?, ?)";
        Object[] params = {
                notice.getTitle(),
                notice.getContent(),
                notice.getCategoryId(),
                notice.getIsTop() != null ? notice.getIsTop() : 0,
                notice.getPublisher(),
                notice.getPublisherId()
        };
        return executeUpdate(sql, params);
    }

    public Notice findNoticeById(Integer id) {
        String sql = BASE_SELECT + BASE_FROM + "WHERE n.id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapNotice(rs, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    public List<Notice> findAllNotice() {
        String sql = BASE_SELECT + BASE_FROM + "ORDER BY n.is_top DESC, n.create_time DESC";
        return queryNoticeList(sql, null);
    }

    public List<Notice> findAllNoticeWithReadStatus(Integer userId) {
        String sql = BASE_SELECT
                + ", CASE WHEN r.id IS NULL THEN 1 ELSE 0 END AS unread "
                + BASE_FROM
                + "LEFT JOIN notice_read r ON n.id = r.notice_id AND r.user_id = ? "
                + "ORDER BY n.is_top DESC, n.create_time DESC";
        return queryNoticeList(sql, userId);
    }

    public int updateNotice(Notice notice) {
        String sql = "UPDATE notice SET title=?, content=?, category_id=?, is_top=?, "
                + "publisher=?, publisher_id=?, update_time=NOW() WHERE id=?";
        Object[] params = {
                notice.getTitle(),
                notice.getContent(),
                notice.getCategoryId(),
                notice.getIsTop() != null ? notice.getIsTop() : 0,
                notice.getPublisher(),
                notice.getPublisherId(),
                notice.getId()
        };
        return executeUpdate(sql, params);
    }

    public int updateTop(Integer id, Integer isTop) {
        String sql = "UPDATE notice SET is_top=?, update_time=NOW() WHERE id=?";
        return executeUpdate(sql, isTop, id);
    }

    public int deleteNotice(Integer id) {
        String sql = "DELETE FROM notice WHERE id=?";
        return executeUpdate(sql, id);
    }

    public int countByCategoryId(Integer categoryId) {
        String sql = "SELECT COUNT(*) FROM notice WHERE category_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, categoryId);
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

    private List<Notice> queryNoticeList(String sql, Integer userId) {
        List<Notice> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (userId != null) {
                pstmt.setInt(1, userId);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapNotice(rs, userId != null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    private Notice mapNotice(ResultSet rs, boolean withUnread) throws Exception {
        Notice n = new Notice();
        n.setId(rs.getInt("id"));
        n.setTitle(rs.getString("title"));
        n.setContent(rs.getString("content"));
        int categoryId = rs.getInt("category_id");
        n.setCategoryId(rs.wasNull() ? null : categoryId);
        n.setCategoryName(rs.getString("category_name"));
        n.setIsTop(rs.getInt("is_top"));
        n.setCreateTime(rs.getTimestamp("create_time"));
        Timestamp updateTime = rs.getTimestamp("update_time");
        n.setUpdateTime(updateTime);
        n.setPublisher(rs.getString("publisher"));
        int publisherId = rs.getInt("publisher_id");
        n.setPublisherId(rs.wasNull() ? null : publisherId);
        if (withUnread) {
            n.setUnread(rs.getInt("unread") == 1);
        }
        return n;
    }
}
