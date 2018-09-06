package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.ElectricianEvaluateDao;
import com.edianniu.pscp.sps.domain.ElectricianEvaluate;
import com.edianniu.pscp.sps.service.ElectricianEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("electricianEvaluateService")
public class ElectricianEvaluateServiceImpl implements ElectricianEvaluateService {
    @Autowired
    private ElectricianEvaluateDao electricianEvaluateDao;

    @Override
    public ElectricianEvaluate queryObject(Long id) {
        return electricianEvaluateDao.queryObject(id);
    }

    @Override
    public List<ElectricianEvaluate> queryList(Map<String, Object> map) {
        return electricianEvaluateDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return electricianEvaluateDao.queryTotal(map);
    }

    @Override
    public void save(ElectricianEvaluate electricianEvaluate) {
        electricianEvaluateDao.save(electricianEvaluate);
    }

    @Override
    public void update(ElectricianEvaluate electricianEvaluate) {
        electricianEvaluateDao.update(electricianEvaluate);
    }

    @Override
    public void delete(Long id) {
        electricianEvaluateDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        electricianEvaluateDao.deleteBatch(ids);
    }

    @Override
    public ElectricianEvaluate queryEntityByElectricianWorkOrderId(Long electricianId, Long electricianWorkOrderId) {
        return electricianEvaluateDao.queryEntityByElectricianWorkOrderId(electricianId, electricianWorkOrderId);
    }

    @Override
    public Long getId() {
        return electricianEvaluateDao.getId();
    }

}
