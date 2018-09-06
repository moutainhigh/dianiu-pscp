package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.attachment.common.AttachmentQuery;
import com.edianniu.pscp.mis.dao.CommonAttachmentDao;
import com.edianniu.pscp.mis.domain.CommonAttachment;
import com.edianniu.pscp.mis.service.CommonAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Repository("commonAttachmentService")
public class DefaultCommonAttachmentService implements CommonAttachmentService {

    @Autowired
    private CommonAttachmentDao commonAttachmentDao;

    /**
     * 根据ID获取附件信息
     *
     * @param id
     * @return
     */
    @Override
    public CommonAttachment getAttachmentById(Long id) {
        return commonAttachmentDao.getAttachmentById(id);
    }

    /**
     * 根据条件获取附件信息
     *
     * @param attachmentQuery
     * @return
     */
    @Override
    public List<CommonAttachment> queryListAttachment(AttachmentQuery attachmentQuery) {
        return commonAttachmentDao.queryListAttachment(attachmentQuery);
    }

    /**
     * 保存附件信息
     *
     * @param attachmentList
     */
    @Transactional
    @Override
    public void saveBatchEntity(List<CommonAttachment> attachmentList) {
        commonAttachmentDao.saveBatchEntity(attachmentList);
    }

    /**
     * 批量删除附件信息
     *
     * @param ids
     * @param modifiedUser
     */
    @Transactional
    @Override
    public void deleteBatchEntity(List<Long> ids, String modifiedUser) {
        commonAttachmentDao.deleteBatchEntity(ids, modifiedUser);
    }

    /**
     * 批量修改附件信息
     *
     * @param attachmentList
     */
    @Transactional
    @Override
    public void updateBatchEntity(List<CommonAttachment> attachmentList) {
        commonAttachmentDao.updateBatchEntity(attachmentList);
    }

}
