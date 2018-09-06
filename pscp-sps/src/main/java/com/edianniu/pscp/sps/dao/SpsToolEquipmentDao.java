package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ToolEquipment;

import java.util.List;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-17 15:19:33
 */
public interface SpsToolEquipmentDao extends BaseDao<ToolEquipment> {

    List<ToolEquipment> queryListByCompanyId(Long companyId);
}
