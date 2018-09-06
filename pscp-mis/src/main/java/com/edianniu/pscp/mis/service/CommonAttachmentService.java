package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.attachment.common.AttachmentQuery;
import com.edianniu.pscp.mis.domain.CommonAttachment;

import java.util.List;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-19 11:04:25
 */
public interface CommonAttachmentService {
    /**
     * 根据ID获取附件信息
     *
     * @param id
     * @return
     */
    CommonAttachment getAttachmentById(Long id);

    /**
     * 根据条件获取附件信息
     *
     * @param attachmentQuery
     * @return
     */
    List<CommonAttachment> queryListAttachment(AttachmentQuery attachmentQuery);

    /**
     * 批量保存附件信息
     *
     * @param attachmentList
     */
    void saveBatchEntity(List<CommonAttachment> attachmentList);

    /**
     * 批量删除附件信息
     *
     * @param ids
     * @param modifiedUser
     */
    void deleteBatchEntity(List<Long> ids, String modifiedUser);

    /**
     * 批量修改附件信息
     *
     * @param attachmentList
     */
    void updateBatchEntity(List<CommonAttachment> attachmentList);
}
