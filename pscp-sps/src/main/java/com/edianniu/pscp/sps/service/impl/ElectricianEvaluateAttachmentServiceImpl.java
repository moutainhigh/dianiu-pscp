package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.ElectricianEvaluateAttachmentDao;
import com.edianniu.pscp.sps.domain.ElectricianEvaluateAttachment;
import com.edianniu.pscp.sps.service.ElectricianEvaluateAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("electricianEvaluateAttachmentService")
public class ElectricianEvaluateAttachmentServiceImpl implements ElectricianEvaluateAttachmentService {
    @Autowired
    private ElectricianEvaluateAttachmentDao electricianEvaluateAttachmentDao;

    @Override
    public ElectricianEvaluateAttachment queryObject(Long id) {
        return electricianEvaluateAttachmentDao.queryObject(id);
    }

    @Override
    public List<ElectricianEvaluateAttachment> queryList(Map<String, Object> map) {
        return electricianEvaluateAttachmentDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return electricianEvaluateAttachmentDao.queryTotal(map);
    }

    @Override
    public void save(ElectricianEvaluateAttachment electricianEvaluateAttachment) {
        electricianEvaluateAttachmentDao.save(electricianEvaluateAttachment);
    }

    @Override
    public void update(ElectricianEvaluateAttachment electricianEvaluateAttachment) {
        electricianEvaluateAttachmentDao.update(electricianEvaluateAttachment);
    }

    @Override
    public void delete(Long id) {
        electricianEvaluateAttachmentDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        electricianEvaluateAttachmentDao.deleteBatch(ids);
    }

    @Override
    public List<ElectricianEvaluateAttachment> queryListByEvaluateId(Long evaluateId) {
        return electricianEvaluateAttachmentDao.queryListByEvaluateId(evaluateId);
    }

}
