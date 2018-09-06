package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.ElectricianWorkLogAttachment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * ClassName: ElectricianWorkLogAttachmentDao
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:50
 */
public interface ElectricianWorkLogAttachmentDao {
    /**
     * 批量保存日志附件信息
     *
     * @param listAttachment
     * @return
     */
    Integer saveBatch(List<ElectricianWorkLogAttachment> listAttachment);

    /**
     * 删除工作记录附件信息
     *
     * @param attachment
     * @return
     */
    Integer delete(ElectricianWorkLogAttachment attachment);

    /**
     * 根据主键ID获取工作记录附件信息
     *
     * @param id
     * @return
     */
    ElectricianWorkLogAttachment getEntityById(Long id);

    /**
     * 根据工作记录ID获取附件信息
     *
     * @param workLogId
     * @return
     */
    List<ElectricianWorkLogAttachment> getAllEntity(@Param("workLogId")Long workLogId,
    		@Param("offset")Integer offset, @Param("limit")Integer limit);
}
