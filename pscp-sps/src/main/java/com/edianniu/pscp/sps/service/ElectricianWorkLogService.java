package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.bean.workorder.worklog.ElectricianWorkLogInfo;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListQuery;
import com.edianniu.pscp.sps.commons.PageResult;

import java.util.List;

/**
 * ClassName: ElectricianWorkLogService
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:33
 */
public interface ElectricianWorkLogService {
    PageResult<ElectricianWorkLogInfo> selectPageWorkLogInfo(ListQuery listQuery);

    List<ElectricianWorkLogInfo> selectWorkLogByElectricianId(Long electricianId, Long electricianWorkOrderId);

	ElectricianWorkLogInfo getDetail(Long id, String source);
}
