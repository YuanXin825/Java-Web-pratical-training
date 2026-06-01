package com.entity;

import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * 业主报修投诉实体类
 */
public class Repair {
    // 字段对应数据库
    private Integer id;         // 主键ID
    private Integer residentId; // 住户ID(外键)
    private String title;       // 标题
    private String content;     // 详细内容
    private Integer type;       // 类型：1-报修 2-投诉
    private Integer status;     // 状态：0-待处理 1-处理中 2-已完成
    private Date createTime;    // 提交时间
    private Date dealTime;      // 处理时间
    private String remark;      // 物业处理备注

    // 无参构造
    public Repair() {}

    // 全参构造
    public Repair(Integer id, Integer residentId, String title, String content, Integer type, Integer status, Date createTime, Date dealTime, String remark) {
        this.id = id;
        this.residentId = residentId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = status;
        this.createTime = createTime;
        this.dealTime = dealTime;
        this.remark = remark;
    }

    // getter & setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    // 根据ID查询
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
                r.setResidentId(rs.getInt("resident_id"));
                r.setTitle(rs.getString("title"));
                r.setContent(rs.getString("content"));
                r.setType(rs.getInt("type"));
                r.setStatus(rs.getInt("status"));
                r.setCreateTime(rs.getDate("create_time"));
                r.setDealTime(rs.getDate("deal_time"));
                r.setRemark(rs.getString("remark"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return r;
    }

}