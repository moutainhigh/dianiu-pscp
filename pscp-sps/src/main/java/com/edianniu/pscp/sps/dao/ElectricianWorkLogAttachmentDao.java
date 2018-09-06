package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianWorkLogAttachment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 11:16:43
 */
public interface ElectricianWorkLogAttachmentDao extends BaseDao<ElectricianWorkLogAttachment> {

    List<Map<String, Object>> selectMapByWorkLogId(@Param("workOrderId")Long workOrderId,
    		@Param("offset")Integer offset, @Param("limit")Integer limit);
}
