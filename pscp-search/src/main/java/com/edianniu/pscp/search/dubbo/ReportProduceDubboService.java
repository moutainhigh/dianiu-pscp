package com.edianniu.pscp.search.dubbo;

import com.edianniu.pscp.search.support.meter.DayDetailLogReqData;
import com.edianniu.pscp.search.support.meter.DayLoadDetailReqData;
import com.edianniu.pscp.search.support.meter.DayLogReqData;
import com.edianniu.pscp.search.support.meter.DayVoltageCurrentDetailReqData;
import com.edianniu.pscp.search.support.meter.DemandDetailReqData;
import com.edianniu.pscp.search.support.meter.MeterLogReqData;
import com.edianniu.pscp.search.support.meter.MonthLogReqData;
import com.edianniu.pscp.search.support.meter.OpResult;

/**
 * ReportProduceDubboService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月6日 下午2:10:44 
 * @version V1.0
 */
public interface ReportProduceDubboService {
    
    /**
     * 添加每日负荷明细数据
     * @param reqData
     * @return
     */
    OpResult save(DayLoadDetailReqData reqData);
    /**
     * 增加月电费情况
     * @param reqData
     * @return
     */
    OpResult save(MonthLogReqData reqData);
    
    
    /**
     * 新增日明细数据
     * @param reqData
     * @return
     */
    OpResult save(DayDetailLogReqData reqData);
    /**
     * 新增日数据
     * @param reqData
     * @return
     */
    OpResult save(DayLogReqData reqData);
   
    
    /**
     * 新增仪表实时信息
     * @param reqData
     * @return
     */
    OpResult save(MeterLogReqData reqData);
    /**
     * 新增需量明细
     * @param reqData
     * @return
     */
    OpResult save(DemandDetailReqData reqData);
    /**
     * 新增日电压电流明细
     * @param reqData
     * @return
     */
    OpResult save(DayVoltageCurrentDetailReqData reqData);

    
    /**
     * 删除每日负荷明细数据
     * @param reqData
     * @return
     */
    OpResult deleteById(DayLoadDetailReqData reqData);
    /**
     * 删除月电费情况
     * @param reqData
     * @return
     */
    OpResult deleteById(MonthLogReqData reqData);
    /**
     * 删除日数据
     * @param reqData
     * @return
     */
    OpResult deleteById(DayLogReqData reqData);
    /**
     * 删除日明细数据
     * @param reqData
     * @return
     */
    OpResult deleteById(DayDetailLogReqData reqData);
    
    /**
     * 删除需量明细
     * @param reqData
     * @return
     */
    OpResult deleteById(DemandDetailReqData reqData);
    /**
     * 删除日电压电流明细
     * @param reqData
     * @return
     */
    OpResult deleteById(DayVoltageCurrentDetailReqData reqData);
}
