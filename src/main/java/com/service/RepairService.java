package com.service;

import com.bean.Repair;

import java.util.List;

public interface RepairService {

    void addRepair(Repair repair);

    List<Repair> getAllRepair();

    List<Repair> getRepairByUserId(Integer userId);

    Repair getRepairById(Integer id);

    void updateRepair(Repair repair);

    boolean evaluateRepair(Integer id, Integer userId, Integer rating, String evaluation);

    void deleteRepair(Integer id);

    boolean deleteRepairByOwner(Integer id, Integer userId);
}
