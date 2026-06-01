package com.dao;

import com.entity.Repair;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RepairDao extends BaseDao {

    // 新增报修/投诉记录
    public int addRepair(Repair repair) {
        String sql = "INSERT INTO repair(resident_id,title,content,type,status,create_time) VALUES(?,?,?,?,?,NOW())";
        Object[] params = {
                repair.getResidentId(),
                repair.getTitle(),
                repair.getContent(),
                repair.getType(),
                repair.getStatus()
        };
        return executeUpdate(sql, params);
    }

    // 根据ID查询报修记录
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

    // 查询所有报修记录
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
                r.setResidentId(rs.getInt("resident_id"));
                r.setTitle(rs.getString("title"));
                r.setContent(rs.getString("content"));
                r.setType(rs.getInt("type"));
                r.setStatus(rs.getInt("status"));
                r.setCreateTime(rs.getDate("create_time"));
                r.setDealTime(rs.getDate("deal_time"));
                r.setRemark(rs.getString("remark"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // 修改报修记录（处理状态、备注）
    public int updateRepair(Repair repair) {
        String sql = "UPDATE repair SET status=?,deal_time=NOW(),remark=? WHERE id=?";
        Object[] params = {repair.getStatus(), repair.getRemark(), repair.getId()};
        return executeUpdate(sql, params);
    }

    // 删除报修记录
    public int deleteRepair(Integer id) {
        String sql = "DELETE FROM repair WHERE id=?";
        return executeUpdate(sql, id);
    }
}