package com.edianniu.pscp.search.service.meter;

import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.service.ProduceService;
import com.edianniu.pscp.search.support.meter.vo.MeterLogVO;
import com.edianniu.pscp.search.support.query.meter.MeterLogQuery;

/**
 * ReportDayLoadDetailService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:43:09 
 * @version V1.0
 */
public interface MeterLogService extends ProduceService{
    /**
     * 分页搜索
     *
     * @param meterLogQuery
     * @return
     */
    PageResult<MeterLogVO> queryByPage(MeterLogQuery meterLogQuery);
    /**
     * 分页搜索(按meterId去重)
     * @param meterLogQuery
     * @return
     */
    public PageResult<MeterLogVO> searchDistinctByMeterId(MeterLogQuery meterLogQuery); 
    /**
     * 查询区间内的平均功率值
     * @param companyId
     * @param meterId
     * @param startTime
     * @param endTime
     * @return
     */
    public Double getAvgPower(Long companyId,String meterId,Long startTime,Long endTime);
    /**
     * 查询一个周期内的电压合格率
     * @param companyId
     * @param meterId
     * @param startTime
     * @param endTime
     * @return
     */
    public Double getVoltageQualifiedRate(Long companyId, String meterId,
			Long startTime, Long endTime);
}
