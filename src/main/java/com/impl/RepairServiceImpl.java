package com.impl;

import com.dao.RepairDao;
import com.entity.Repair;
import com.service.RepairService;

import java.util.List;

public class RepairServiceImpl implements RepairService {

    private RepairDao repairDao = new RepairDao();

    @Override
    public void addRepair(Repair repair) {
        // 默认：待处理
        repair.setStatus(0);
        repairDao.addRepair(repair);
    }

    @Override
    public List<Repair> getAllRepair() {
        return repairDao.findAllRepair();
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
    public void deleteRepair(Integer id) {
        repairDao.deleteRepair(id);
    }
}