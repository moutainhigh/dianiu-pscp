package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianWorkLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ElectricianWorkLogDao
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:36
 */
public interface ElectricianWorkLogDao extends BaseDao<ElectricianWorkLog> {
    List<ElectricianWorkLog> queryListWorkLog(Map<String, Object> map);

    List<ElectricianWorkLog> selectWorkLogByElectricianId(@Param("electricianId") Long electricianId,
                                                          @Param("electricianWorkOrderId") Long electricianWorkOrderId);
}
