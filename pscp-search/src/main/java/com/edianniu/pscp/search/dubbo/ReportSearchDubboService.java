package com.edianniu.pscp.search.dubbo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.edianniu.pscp.search.support.meter.AvgListResult;
import com.edianniu.pscp.search.support.meter.AvgOfMetreReqData;
import com.edianniu.pscp.search.support.meter.DayAggregateReqData;
import com.edianniu.pscp.search.support.meter.DayElectricChargeReqData;
import com.edianniu.pscp.search.support.meter.DayElectricReqData;
import com.edianniu.pscp.search.support.meter.DayLoadDetailReqData;
import com.edianniu.pscp.search.support.meter.DayLogReqData;
import com.edianniu.pscp.search.support.meter.DayPowerFactorReqData;
import com.edianniu.pscp.search.support.meter.DayVoltageCurrentDetailReqData;
import com.edianniu.pscp.search.support.meter.DemandDetailReqData;
import com.edianniu.pscp.search.support.meter.MeterLogReqData;
import com.edianniu.pscp.search.support.meter.MonthElectricReqData;
import com.edianniu.pscp.search.support.meter.MonthLogReqData;
import com.edianniu.pscp.search.support.meter.ReportListResult;
import com.edianniu.pscp.search.support.meter.StatListResult;
import com.edianniu.pscp.search.support.meter.SubEnergyReq;
import com.edianniu.pscp.search.support.meter.list.CurrentListReqData;
import com.edianniu.pscp.search.support.meter.list.DayAggregateListReqData;
import com.edianniu.pscp.search.support.meter.list.DayDetailLogListReqData;
import com.edianniu.pscp.search.support.meter.list.DayElectricChargeListReqData;
import com.edianniu.pscp.search.support.meter.list.DayElectricListReqData;
import com.edianniu.pscp.search.support.meter.list.DayLoadDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DayPowerFactorDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DayVoltageCurrentDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DemandDetailListReqData;
import com.edianniu.pscp.search.support.meter.list.DistinctMeterLogListReqData;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.list.MonthElectricChargeListReqData;
import com.edianniu.pscp.search.support.meter.list.MonthElectricListReqData;
import com.edianniu.pscp.search.support.meter.list.MonthLogListReqData;
import com.edianniu.pscp.search.support.meter.list.VoltageListReqData;
import com.edianniu.pscp.search.support.meter.vo.AvgOfMeterStat;
import com.edianniu.pscp.search.support.meter.vo.CurrentVO;
import com.edianniu.pscp.search.support.meter.vo.DayAggregateVO;
import com.edianniu.pscp.search.support.meter.vo.DayDetailLogVO;
import com.edianniu.pscp.search.support.meter.vo.DayElectricChargeVO;
import com.edianniu.pscp.search.support.meter.vo.DayElectricVO;
import com.edianniu.pscp.search.support.meter.vo.DayLoadDetailVO;
import com.edianniu.pscp.search.support.meter.vo.DayLogVO;
import com.edianniu.pscp.search.support.meter.vo.DayPowerFactorDetailVO;
import com.edianniu.pscp.search.support.meter.vo.DayPowerFactorVO;
import com.edianniu.pscp.search.support.meter.vo.DayVoltageCurrentDetailVO;
import com.edianniu.pscp.search.support.meter.vo.DemandDetailVO;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;
import com.edianniu.pscp.search.support.meter.vo.MeterLogVO;
import com.edianniu.pscp.search.support.meter.vo.MonthElectricChargeVO;
import com.edianniu.pscp.search.support.meter.vo.MonthElectricVO;
import com.edianniu.pscp.search.support.meter.vo.MonthLogVO;
import com.edianniu.pscp.search.support.meter.vo.StatLog;
import com.edianniu.pscp.search.support.meter.vo.VoltageVO;

/**
 * ReportSearchDubboService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月6日 下午2:10:44 
 * @version V1.0
 */
public interface ReportSearchDubboService {
	public List<StatLog> getStatLogs(String fromDate,String toDate,String type,Set<String> meterIds,Long companyId);
	/**
     * 
     * 查询日用电以及电费数据
     * @param reqData
     * @return
     */
    ReportListResult<DayElectricChargeVO> search(DayElectricChargeListReqData reqData);
    /**
     * 查询日用电以及电费明细数据
     * @param reqData
     * @return
     */
    ReportListResult<DayDetailLogVO> search(DayDetailLogListReqData reqData);
    
    /**
     * 
     * 查询当天综合数据
     * @param reqData
     * @return
     */
    ReportListResult<DayAggregateVO> search(DayAggregateListReqData reqData);
    /**
     * 年月日电量电费数据统计
     * 查询的是仪表日日志信息表
     * @param electricChargeStatReqData
     * @return
     */
    StatListResult<ElectricChargeStat> getElectricChargeStats(ElectricChargeStatReqData electricChargeStatReqData);
    /**
     * 查询每日负荷明细数据
     * @param reqData
     * @return
     */
    ReportListResult<DayLoadDetailVO> search(DayLoadDetailListReqData reqData);
    /**
     * 查询每月电费数据
     * @param reqData
     * @return
     */
    ReportListResult<MonthElectricChargeVO> search(MonthElectricChargeListReqData reqData);
    /**
     * 查询每月电费数据
     * @param reqData
     * @return
     */
    ReportListResult<MonthLogVO> search(MonthLogListReqData reqData);
    /**
     * 查询月用电量明细数据
     * @param reqData
     * @return
     */
    ReportListResult<DayElectricVO> search(DayElectricListReqData reqData);
   
    /**
     * 查询每月用电量数据
     * @param reqData
     * @return
     */
    ReportListResult<MonthElectricVO> search(MonthElectricListReqData reqData);
    
    /**
     * 查询实时电压数据
     * @param reqData
     * @return
     */
    ReportListResult<VoltageVO> search(VoltageListReqData reqData);
    /**
     * 查询日电压电流明细数据
     * @param reqData
     * @return
     */
    ReportListResult<DayVoltageCurrentDetailVO> search(DayVoltageCurrentDetailListReqData reqData);
    /**
     * 查询所有仪表最新的记录
     * 1） 最新的m条记录
     * 2) 根据仪表去重，一个仪表多个记录，只列出最近的M条
     * 3) 具体看请求参数
     * @return
     */
    ReportListResult<MeterLogVO> searchDistinctByMeterId(DistinctMeterLogListReqData reqData);
    /**
     * 查询实时电流数据
     * @param reqData
     * @return
     */
    ReportListResult<CurrentVO> search(CurrentListReqData reqData);
    /**
     * 根据客户ID，仪表编号列表，日期查询月分项能耗
     * @param companyId
     * @param meterIds
     * @param date
     * @return
     */
    public Map<String,Double> getMonthDividedElectric(Long companyId, String[] meterIds, String date);
    /**
     * 查询区间平均功率值
     * @param companyId
     * @param meterId
     * @param startTime yyyyMMdd hh:MM:ss
     * @param endTime  yyyyMMdd hh:MM:ss
     * @return
     */
    Double  getAvgPower(Long companyId,String meterId,Long startTime,Long endTime);
    /**
     * 查询一个周期内最大功率值
     * @param companyId
     * @param meterId
     * @param startTime(yyyyMMHHmmss)
     * @param endTime(yyyyMMHHmmss)
     * @return
     */
    public Double getMaxPower(Long companyId, String meterId, Long startTime,Long endTime);
    /**
     * 查询一个周期内电压的合格率
     * @param companyId
     * @param meterId
     * @param startTime
     * @param endTime
     * @return
     */
    public Double getVoltageQualifiedRate(Long companyId,String meterId,Long startTime,Long endTime);
    /**
     * 查询日功率因数明细数据
     * @param reqData
     * @return
     */
    ReportListResult<DayPowerFactorDetailVO> search(DayPowerFactorDetailListReqData reqData);
    /**
     * 查询需量明细
     * @param reqData
     * @return
     */
    ReportListResult<DemandDetailVO> search(DemandDetailListReqData reqData);
    /**
     * 查询日数据
     * @param reqData
     * @return
     */
    DayLogVO  getById(DayLogReqData reqData);
    /**
     * 查询当天综合数据
     * @param id
     * @return
     */
    DayAggregateVO  getById(DayAggregateReqData reqData);
    /**
     * 日用电电费数据
     * @param reqData
     * @return
     */
    DayElectricChargeVO  getById(DayElectricChargeReqData reqData);
    
    /**
     * 查询每日负荷明细数据
     * @param reqData
     * @return
     */
    DayLoadDetailVO  getById(DayLoadDetailReqData reqData);
    /**
     * 查询当月电费数据
     * @param reqData
     * @return
     */
    MonthElectricChargeVO  getById(MonthLogReqData reqData);
    /**
     * 查询当月用电量数据
     * @param reqData
     * @return
     */
    MonthElectricVO  getById(MonthElectricReqData reqData);
    /**
     * 查询当月用电量数据(扩展查询)
     * @param reqData
     * @param subEnergyReq 分项能耗查询条件
     * @return
     */
    MonthElectricVO  getById(MonthElectricReqData reqData,SubEnergyReq subEnergyReq);
    /**
     * 查询每日用电量数据
     * @param reqData
     * @return
     */
    DayElectricVO  getById(DayElectricReqData reqData);
    /**
     * 查询仪表日志数据
     * @param reqData
     * @return
     */
    MeterLogVO  getById(MeterLogReqData reqData);
    /**
     * 查询日功率因数
     * @param reqData
     * @return
     */
    DayPowerFactorVO  getById(DayPowerFactorReqData reqData);
    /**
     * 查询需量明细
     * @param reqData
     * @return
     */
    DemandDetailVO  getById(DemandDetailReqData reqData);
    /**
     * 查询日电压电流明细
     * @param reqData
     * @return
     */
    DayVoltageCurrentDetailVO  getById(DayVoltageCurrentDetailReqData reqData);
    
    /**
     * 按星期查询平均用电量
     * @param req
     * @return
     */
	AvgListResult<AvgOfMeterStat> avgOfMeter(AvgOfMetreReqData req);
}
