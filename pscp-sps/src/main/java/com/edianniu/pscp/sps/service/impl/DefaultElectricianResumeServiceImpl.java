package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.ElectricianResumeDao;
import com.edianniu.pscp.sps.domain.ElectricianResume;
import com.edianniu.pscp.sps.service.ElectricianResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("electricianResumeService")
public class DefaultElectricianResumeServiceImpl implements ElectricianResumeService {
    @Autowired
    private ElectricianResumeDao electricianResumeDao;

    @Override
    public ElectricianResume queryObject(Long id) {
        return electricianResumeDao.queryObject(id);
    }

    @Override
    public List<ElectricianResume> queryList(Map<String, Object> map) {
        return electricianResumeDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return electricianResumeDao.queryTotal(map);
    }

    @Override
    public void save(ElectricianResume electricianResume) {
        electricianResumeDao.save(electricianResume);
    }

    @Override
    public void update(ElectricianResume electricianResume) {
        electricianResumeDao.update(electricianResume);
    }

    @Override
    public void delete(Long id) {
        electricianResumeDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        electricianResumeDao.deleteBatch(ids);
    }

    @Override
    public ElectricianResume queryEntityByElectricianId(Long electricianId) {
        return electricianResumeDao.queryEntityByElectricianId(electricianId);
    }

}
