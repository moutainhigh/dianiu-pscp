package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.ElectricianWorkLogAttachmentDao;
import com.edianniu.pscp.sps.domain.ElectricianWorkLogAttachment;
import com.edianniu.pscp.sps.service.ElectricianWorkLogAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("electricianWorkLogAttachmentService")
public class DefaultElectricianWorkLogAttachmentService implements ElectricianWorkLogAttachmentService {

    @Autowired
    private ElectricianWorkLogAttachmentDao electricianWorkLogAttachmentDao;

    @Override
    public ElectricianWorkLogAttachment queryObject(Long id) {
        return electricianWorkLogAttachmentDao.queryObject(id);
    }

    @Override
    public List<ElectricianWorkLogAttachment> queryList(Map<String, Object> map) {
        return electricianWorkLogAttachmentDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return electricianWorkLogAttachmentDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(ElectricianWorkLogAttachment electricianWorkLogAttachment) {
        electricianWorkLogAttachmentDao.save(electricianWorkLogAttachment);
    }

    @Override
    @Transactional
    public void update(ElectricianWorkLogAttachment electricianWorkLogAttachment) {
        electricianWorkLogAttachmentDao.update(electricianWorkLogAttachment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        electricianWorkLogAttachmentDao.delete(id);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        electricianWorkLogAttachmentDao.deleteBatch(ids);
    }

}
