package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.history.FacilitatorHistoryReqData;
import com.edianniu.pscp.mis.bean.history.FacilitatorHistoryResult;

/**
 * ClassName: CompanyElectricianInfoService
 * Author: tandingbo
 * CreateTime: 2017-10-30 11:12
 */
public interface CompanyElectricianInfoService {
    /**
     * 获取电工历史服务商
     *
     * @param reqData
     * @return
     */
    FacilitatorHistoryResult queryFacilitatorHistory(FacilitatorHistoryReqData reqData);
}
