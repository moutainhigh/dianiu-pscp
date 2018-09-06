package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.attachment.common.AttachmentQuery;
import com.edianniu.pscp.mis.domain.CommonAttachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-19 11:04:25
 */
public interface CommonAttachmentDao {

    CommonAttachment getAttachmentById(Long id);

    List<CommonAttachment> queryListAttachment(AttachmentQuery attachmentQuery);

    void saveBatchEntity(List<CommonAttachment> attachmentList);

    void deleteBatchEntity(@Param("ids") List<Long> ids, @Param("modifiedUser") String modifiedUser);

    void updateBatchEntity(List<CommonAttachment> attachmentList);
}
