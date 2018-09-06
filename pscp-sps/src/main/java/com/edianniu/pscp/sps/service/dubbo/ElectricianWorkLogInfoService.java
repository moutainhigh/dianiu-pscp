package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.workorder.worklog.DetailReqData;
import com.edianniu.pscp.sps.bean.workorder.worklog.DetailResult;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListResult;

/**
 * ClassName: ElectricianWorkLogInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:31
 */
public interface ElectricianWorkLogInfoService {
    ListResult list(ListReqData listReqData);

	DetailResult detail(DetailReqData detailReqData);
}
