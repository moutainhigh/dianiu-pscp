package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;

import java.util.List;

/**
 * ClassName: SpsToolEquipmentService
 * Author: tandingbo
 * CreateTime: 2017-05-17 15:25
 */
public interface SpsToolEquipmentService {
    List<ToolEquipmentVO> selectAllToolEquipmentVOByCompanyId(Long companyId);
}
