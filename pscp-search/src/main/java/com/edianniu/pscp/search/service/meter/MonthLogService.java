package com.edianniu.pscp.search.service.meter;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.service.ProduceService;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;
import com.edianniu.pscp.search.support.meter.vo.MonthLogVO;
import com.edianniu.pscp.search.support.query.meter.MonthLogQuery;

/**
 * MonthLogService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:43:09 
 * @version V1.0
 */
public interface MonthLogService extends ProduceService{
	public Map<String, Double> getDividedElectric(Long companyId,
			String[] meterIds, String date);
    /**
     * 分页搜索
     *
     * @param monthLogQuery
     * @return
     */
    PageResult<MonthLogVO> queryByPage(MonthLogQuery monthLogQuery);
    /**
     * 年月统计
     * @param monthStatReqData
     * @return
     */
    List<ElectricChargeStat> getYearMonthStats(ElectricChargeStatReqData monthStatReqData);
    
}
