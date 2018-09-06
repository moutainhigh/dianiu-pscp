package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.toolequipment.ToolEquipmentVOResult;

/**
 * ClassName: SpsToolEquipmentInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-17 15:26
 */
public interface SpsToolEquipmentInfoService {
    ToolEquipmentVOResult selectAllToolEquipmentVOByCompanyId(Long companyId);
}
