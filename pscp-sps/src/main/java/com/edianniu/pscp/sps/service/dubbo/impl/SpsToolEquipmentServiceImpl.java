package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.sps.bean.toolequipment.ToolEquipmentVOResult;
import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.SpsToolEquipmentService;
import com.edianniu.pscp.sps.service.dubbo.SpsToolEquipmentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: SpsToolEquipmentServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-17 15:27
 */
@Service
@Repository("spsToolEquipmentInfoService")
public class SpsToolEquipmentServiceImpl implements SpsToolEquipmentInfoService {
    private static final Logger logger = LoggerFactory.getLogger(EngineeringProjectServiceImpl.class);

    @Autowired
    @Qualifier("spsToolEquipmentService")
    private SpsToolEquipmentService spsToolEquipmentService;

    @Override
    public ToolEquipmentVOResult selectAllToolEquipmentVOByCompanyId(Long companyId) {
        ToolEquipmentVOResult result = new ToolEquipmentVOResult();
        try {
            if (companyId == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("客户标识不能为空");
                return result;
            }
            List<ToolEquipmentVO> toolEquipmentVOList = spsToolEquipmentService.selectAllToolEquipmentVOByCompanyId(companyId);
            result.setToolEquipments(toolEquipmentVOList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }
}
