package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.ElectricianWorkLogAttachmentDao;
import com.edianniu.pscp.mis.dao.ElectricianWorkLogDao;
import com.edianniu.pscp.mis.domain.ElectricianWorkLog;
import com.edianniu.pscp.mis.domain.ElectricianWorkLogAttachment;
import com.edianniu.pscp.mis.service.ElectricianWorkLogService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: DefaultElectricianWorkLogService
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:49
 */
@Service
@Repository("electricianWorkLogService")
public class DefaultElectricianWorkLogService implements ElectricianWorkLogService {

    @Autowired
    private ElectricianWorkLogDao electricianWorkLogDao;

    @Autowired
    private ElectricianWorkLogAttachmentDao electricianWorkLogAttachmentDao;

    /**
     * 保存日志及附件信息
     *
     * @param workLog
     * @param listAttachment
     * @return
     */
    @Override
    @Transactional
    public Integer saveEntity(ElectricianWorkLog workLog, List<ElectricianWorkLogAttachment> listAttachment) {
        Integer c = electricianWorkLogDao.save(workLog);
        if (c > 0 && CollectionUtils.isNotEmpty(listAttachment)) {
            for (ElectricianWorkLogAttachment attachment : listAttachment) {
                attachment.setWorkLogId(workLog.getId());
            }
            electricianWorkLogAttachmentDao.saveBatch(listAttachment);
        }
        return c;
    }

    /**
     * 根据主键ID获取工作记录信息
     *
     * @param id
     * @return
     */
    @Override
    public ElectricianWorkLog getEntityById(Long id) {
        return electricianWorkLogDao.getEntityById(id);
    }

    /**
     * 删除工作记录
     *
     * @param electricianWorkLog
     * @return
     */
    @Override
    @Transactional
    public Integer deleteEntity(ElectricianWorkLog electricianWorkLog) {
        Integer c = electricianWorkLogDao.delete(electricianWorkLog);
        if (c > 0) {
            ElectricianWorkLogAttachment attachment = new ElectricianWorkLogAttachment();
            attachment.setWorkLogId(electricianWorkLog.getId());
            attachment.setModifiedTime(electricianWorkLog.getModifiedTime());
            attachment.setModifiedUser(electricianWorkLog.getModifiedUser());
            electricianWorkLogAttachmentDao.delete(attachment);
        }
        return c;
    }

    /**
     * 获取用户所有工作记录
     *
     * @param searchEntity
     * @return
     */
    @Override
    public List<ElectricianWorkLog> getAllEntity(ElectricianWorkLog searchEntity) {
        return electricianWorkLogDao.getAllEntity(searchEntity);
    }
}
