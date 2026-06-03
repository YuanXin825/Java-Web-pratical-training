package com.service.impl;

import com.bean.Repair;
import com.dao.RepairDao;
import com.dao.UserDao;
import com.service.RepairService;

import java.util.List;

public class RepairServiceImpl implements RepairService {

    private RepairDao repairDao = new RepairDao();
    private UserDao userDao = new UserDao();

    @Override
    public void addRepair(Repair repair) {
        if (repair.getUserId() == null || !userDao.existsById(repair.getUserId())) {
            throw new IllegalArgumentException("提交失败：用户不存在");
        }
        if (repair.getType() == null || repair.getType().trim().isEmpty()) {
            repair.setType("报修");
        }
        repair.setStatus("待处理");
        repairDao.addRepair(repair);
    }

    @Override
    public List<Repair> getAllRepair() {
        return repairDao.findAllRepair();
    }

    @Override
    public List<Repair> getRepairByUserId(Integer userId) {
        return repairDao.findRepairByUserId(userId);
    }

    @Override
    public Repair getRepairById(Integer id) {
        return repairDao.findRepairById(id);
    }

    @Override
    public void updateRepair(Repair repair) {
        repairDao.updateRepair(repair);
    }

    @Override
    public boolean evaluateRepair(Integer id, Integer userId, Integer rating, String evaluation) {
        return repairDao.evaluateRepair(id, userId, rating, evaluation) > 0;
    }

    @Override
    public void deleteRepair(Integer id) {
        repairDao.deleteRepair(id);
    }

    @Override
    public boolean deleteRepairByOwner(Integer id, Integer userId) {
        return repairDao.deleteRepairByOwner(id, userId) > 0;
    }
}
