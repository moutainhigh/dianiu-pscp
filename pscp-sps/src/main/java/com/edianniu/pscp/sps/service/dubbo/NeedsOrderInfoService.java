package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.needsorder.InitDataReqData;
import com.edianniu.pscp.sps.bean.needsorder.InitDataResult;

/**
 * ClassName: NeedsOrderInfoService
 * Author: tandingbo
 * CreateTime: 2017-09-26 16:27
 */
public interface NeedsOrderInfoService {
    InitDataResult initData(InitDataReqData initDataReqData);
}
