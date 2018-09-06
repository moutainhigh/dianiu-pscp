package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.history.vo.FacilitatorHistoryVO;
import com.edianniu.pscp.mis.bean.query.history.FacilitatorHistoryQuery;
import com.edianniu.pscp.mis.commons.PageResult;

/**
 * ClassName: CompanyElectricianService
 * Author: tandingbo
 * CreateTime: 2017-10-30 11:14
 */
public interface CompanyElectricianService {
    /**
     * 获取电工历史服务商
     *
     * @param listQuery
     * @return
     */
    PageResult<FacilitatorHistoryVO> queryFacilitatorHistory(FacilitatorHistoryQuery listQuery);
}
