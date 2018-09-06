package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.ElectricianWorkLogAttachmentDao;
import com.edianniu.pscp.mis.domain.ElectricianWorkLogAttachment;
import com.edianniu.pscp.mis.service.ElectricianWorkLogAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: DefaultElectricianWorkLogAttachmentService
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:51
 */
@Service
@Repository("electricianWorkLogAttachmentService")
public class DefaultElectricianWorkLogAttachmentService implements ElectricianWorkLogAttachmentService {

    @Autowired
    private ElectricianWorkLogAttachmentDao electricianWorkLogAttachmentDao;

    /**
     * 根据主键ID获取工作记录附件信息
     *
     * @param id
     * @return
     */
    @Override
    public ElectricianWorkLogAttachment getEntityById(Long id) {
        return electricianWorkLogAttachmentDao.getEntityById(id);
    }

    /**
     * 删除工作记录附件
     *
     * @param workLogAttachment
     * @return
     */
    @Override
    @Transactional
    public Integer delete(ElectricianWorkLogAttachment workLogAttachment) {
        return electricianWorkLogAttachmentDao.delete(workLogAttachment);
    }

    /**
     * 根据工作记录ID获取附件信息
     *
     * @param workLogId
     * @return
     */
    @Override
    public List<ElectricianWorkLogAttachment> getAllEntity(Long workLogId, Integer offset, Integer limit) {
        return electricianWorkLogAttachmentDao.getAllEntity(workLogId, offset, limit);
    }
}
