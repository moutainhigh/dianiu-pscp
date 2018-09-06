package com.edianniu.pscp.cs.service;

import java.util.List;

import com.edianniu.pscp.cs.bean.power.ChargeBillResult;
import com.edianniu.pscp.cs.bean.power.ChargeDetailResult;
import com.edianniu.pscp.cs.bean.power.CurrentBalanceResult;
import com.edianniu.pscp.cs.bean.power.HasDataResult;
import com.edianniu.pscp.cs.bean.power.PowerDistributeResult;
import com.edianniu.pscp.cs.bean.power.PowerFactorResult;
import com.edianniu.pscp.cs.bean.power.PowerLoadResult;
import com.edianniu.pscp.cs.bean.power.PowerQuantityResult;
import com.edianniu.pscp.cs.bean.power.RealTimeLoadResult;
import com.edianniu.pscp.cs.bean.power.SafetyVoltageResult;
import com.edianniu.pscp.cs.bean.power.UseFengGUResult;
import com.edianniu.pscp.cs.bean.power.WarningSaveReqData;
import com.edianniu.pscp.cs.bean.power.vo.BuildingVO;
import com.edianniu.pscp.cs.bean.power.vo.CollectorPointVO;
import com.edianniu.pscp.cs.bean.power.vo.LineVO;
import com.edianniu.pscp.cs.bean.power.vo.MonitorVO;
import com.edianniu.pscp.cs.bean.power.vo.WarningVO;
import com.edianniu.pscp.cs.commons.PageResult;

/**
 * 智能终端
 * @author zhoujianjian
 * @date 2017年12月7日 下午3:49:02
 */
public interface PowerService {

	/**
	 * 客户线路列表
	 * @param companyId
	 * @return
	 */
	List<LineVO> getCompanyLines(Long companyId);

	/**
	 * 用电负荷
	 * @param companyId
	 * @param fcompanyId
	 * @return
	 */
	PowerLoadResult getPowerLoad(Long companyId);

	/**
	 * 用电量
	 * @param companyId
	 * @param fcompanyId
	 * @param date   支持到月份
	 * @return
	 */
	PowerQuantityResult getPowerQuantity(Long companyId, String date);

	/**
	 * 电量分布
	 * @param companyId
	 * @param fcompanyId 
	 * @param date    支持到月份
	 * @return    
	 */
	PowerDistributeResult getPowerDistribute(Long companyId, String date);
	
	/**
	 * 功率因数
	 * @param companyId
	 * @param fcompanyId
	 * @param lineId
	 * @return
	 */
	PowerFactorResult getPowerFactor(Long companyId, Long lineId);

	/**
	 * 电费单
	 * @param companyId
	 * @param fcompanyId
	 * @param date
	 * @return
	 */
	ChargeBillResult getChargeBill(Long companyId, String date);

	/**
	 * 电费明细
	 * @param companyId
	 * @param fcompanyId
	 * @param date
	 * @return
	 */
	ChargeDetailResult getChargeDetail(Long companyId, String date);
	
	/**
	 * 客户设备告警新增
	 * @param companyId  客户公司ID
	 */
	void saveWarning(WarningSaveReqData reqData, Long companyId);
	
	/**
	 * 告警列表
	 * @param companyId
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	PageResult<WarningVO> getWarningList(Long companyId, Integer offset, Integer pageSize);

	/**
	 * 告警详情
	 * @param id
	 * @return
	 */
	WarningVO getWarningDetail(Long id);

	/**
	 * 门户：智能监控>监控列表
	 * @param companyId  服务商的companyId
	 */
	PageResult<MonitorVO> getMonitorList(Long companyId, Integer limit, Integer offset);
	
	/**
	 * 实时负荷
	 * @param companyId  客户companyId
	 * @param date       精确到日
	 * @return
	 */
	RealTimeLoadResult getRealTimeLoad(Long companyId, String date);

	/**
	 * 门户：智能监控>监控列表>总览>（综合、动力、照明、空调、特殊）
	 * @param companyId  客户companyId
	 * @param type
	 * @param date
	 * @param limit
	 * @param offset
	 * @return
	 */
	PageResult<CollectorPointVO> getGeneralByType(Long companyId, Integer type, String date, Integer limit, Integer offset);

	/**
	 * 门户：智能监控>告警
	 * 获取服务商所有客户的告警列表 
	 * @param companyId  服务商companyId
	 * @param limit
	 * @param offset
	 * @return
	 */
	PageResult<WarningVO> getAllWarnings(Long companyId, Integer limit, Integer offset);

	/**
	 * 门户：智能监控>监控列表>用能排行
	 * @param companyId
	 * @param date
	 * @param limit
	 * @param offset
	 * @return
	 */
	PageResult<BuildingVO> getConsumeRank(Long companyId, String date, Integer limit, Integer offset);

	/**
	 * 门户：智能监控>监控列表>电压健康
	 * @param companyId  客户companyId
	 * @return
	 */
	SafetyVoltageResult getSafetyVoltage(Long companyId);

	Boolean isExistLine(Long companyId, Long lineId);

	/**
	 * 门户：智能监控>监控列表>电流平衡
	 * @param companyId   客户companyId
	 * @param lineId
	 * @return
	 */
	CurrentBalanceResult getCurrentBalance(Long companyId, Long lineId);

	/**
	 * 门户：智能监控>监控列表>峰谷利用
	 * @param companyId    客户companyId
	 * @return
	 */
	UseFengGUResult useFengGU(Long companyId, Long lineId);

	/**
	 * 分页获取监测点数据
	 * 门户：功率因数---获取监测点功率因数
	 */
	PageResult<CollectorPointVO> getCollectorPointsForPowerFactor(Long companyId, Integer limit, Integer offset);

	/**
	 * 分页获取监测点数据
	 * 门户：峰谷利用---获取监测点在监测时段用电量、电费
	 * @param startTime  HH:mm:ss
	 * @param endTime    HH:mm:ss
	 */
	PageResult<CollectorPointVO> getCollectorPointsForPowerQuantity(Long companyId, Integer limit, Integer offset, String startTime, String endTime);

	/**
	 * 判断仪表是否存在
	 * @param companyId   客户公司ID
	 * @param meterId	     仪表编号
	 * @return
	 */
	Boolean isExistMeter(Long companyId, String meterId);

	/**
	 * 判断仪表是否有数据
	 * @param meterId    客户公司ID
	 * @param companyId  仪表编号
	 * @return
	 */
	HasDataResult hasData(String meterId, Long companyId);

	



	

}
