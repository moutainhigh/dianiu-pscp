package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.ElectricianEvaluate;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: ElectricianEvaluateDao
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:22
 */
public interface ElectricianEvaluateDao extends BaseDao<ElectricianEvaluate> {
    ElectricianEvaluate getEntityByElectricianId(@Param("uid") Long uid, @Param("electricianWorkOrderId") Long electricianWorkOrderId);
}
