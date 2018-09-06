package com.edianniu.pscp.search.service.meter;

import java.util.List;

import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.service.ProduceService;
import com.edianniu.pscp.search.support.meter.AvgOfMetreReqData;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.vo.AvgOfMeterStat;
import com.edianniu.pscp.search.support.meter.vo.DayLogVO;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;
import com.edianniu.pscp.search.support.query.meter.DayLogQuery;

/**
 * DayLogService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:43:09 
 * @version V1.0
 */
public interface DayLogService extends ProduceService{
    /**
     * 分页搜索
     *
     * @param monthElectricDetailQuery
     * @return
     */
    PageResult<DayLogVO> queryByPage(DayLogQuery listQuery);
    
    List<ElectricChargeStat> getDayStats(ElectricChargeStatReqData electricChargeStatReqData);

	List<AvgOfMeterStat> avgOfMeter(AvgOfMetreReqData req);
}
