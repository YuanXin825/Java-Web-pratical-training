package com.service;

import com.entity.Repair;
import java.util.List;

public interface RepairService {
    // 业主提交报修/投诉
    void addRepair(Repair repair);

    // 查询所有报修（管理员）
    List<Repair> getAllRepair();

    // 根据ID查报修
    Repair getRepairById(Integer id);

    // 物业处理报修（改状态、填备注）
    void updateRepair(Repair repair);

    // 删除报修
    void deleteRepair(Integer id);
}