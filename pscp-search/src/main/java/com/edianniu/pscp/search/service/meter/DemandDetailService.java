package com.edianniu.pscp.search.service.meter;

import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.service.ProduceService;
import com.edianniu.pscp.search.support.meter.vo.DemandDetailVO;
import com.edianniu.pscp.search.support.query.meter.DemandDetailQuery;

/**
 * ReportDemandDetailService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:43:09 
 * @version V1.0
 */
public interface DemandDetailService extends ProduceService{
    /**
     * 分页搜索
     *
     * @param dayPowerFactorQuery
     * @return
     */
    PageResult<DemandDetailVO> queryByPage(DemandDetailQuery demandDetailQuery);
    /**
     * 获取最大需量
     * @param companyId
     * @param meterId
     * @param startTime
     * @param endTime
     * @return
     */
    public Double getMaxPower(Long companyId, String meterId,Long startTime,Long endTime);
}
