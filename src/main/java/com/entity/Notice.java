package com.entity;

import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * 小区公告通知实体类
 */
public class Notice {
    // 字段对应数据库
    private Integer id;         // 主键ID
    private Integer adminId;    // 发布人ID(外键，关联user表)
    private String title;       // 公告标题
    private String content;     // 公告内容
    private Date createTime;    // 发布时间
    private Integer isTop;      // 是否置顶：0-否 1-是

    // 无参构造
    public Notice() {}

    // 全参构造
    public Notice(Integer id, Integer adminId, String title, String content, Date createTime, Integer isTop) {
        this.id = id;
        this.adminId = adminId;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.isTop = isTop;
    }

    // getter & setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
    // 根据ID查询
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
}