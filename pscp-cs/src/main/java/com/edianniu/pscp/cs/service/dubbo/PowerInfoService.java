package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.power.AllWarningsReqData;
import com.edianniu.pscp.cs.bean.power.ChargeBillReqData;
import com.edianniu.pscp.cs.bean.power.ChargeBillResult;
import com.edianniu.pscp.cs.bean.power.ChargeDetailReqData;
import com.edianniu.pscp.cs.bean.power.ChargeDetailResult;
import com.edianniu.pscp.cs.bean.power.CollectorPointsReqData;
import com.edianniu.pscp.cs.bean.power.CollectorPointsResult;
import com.edianniu.pscp.cs.bean.power.ConsumeRankReqData;
import com.edianniu.pscp.cs.bean.power.CompanyLinesReqData;
import com.edianniu.pscp.cs.bean.power.CompanyLinesResult;
import com.edianniu.pscp.cs.bean.power.GeneralByTypeReqData;
import com.edianniu.pscp.cs.bean.power.GeneralByTypeResult;
import com.edianniu.pscp.cs.bean.power.HasDataResult;
import com.edianniu.pscp.cs.bean.power.MonitorListReqData;
import com.edianniu.pscp.cs.bean.power.MonitorListResult;
import com.edianniu.pscp.cs.bean.power.PowerDistributeReqData;
import com.edianniu.pscp.cs.bean.power.PowerDistributeResult;
import com.edianniu.pscp.cs.bean.power.PowerFactorReqData;
import com.edianniu.pscp.cs.bean.power.PowerFactorResult;
import com.edianniu.pscp.cs.bean.power.PowerLoadReqData;
import com.edianniu.pscp.cs.bean.power.PowerLoadResult;
import com.edianniu.pscp.cs.bean.power.PowerQuantityReqData;
import com.edianniu.pscp.cs.bean.power.PowerQuantityResult;
import com.edianniu.pscp.cs.bean.power.RealTimeLoadReqData;
import com.edianniu.pscp.cs.bean.power.RealTimeLoadResult;
import com.edianniu.pscp.cs.bean.power.SafetyVoltageReqData;
import com.edianniu.pscp.cs.bean.power.SafetyVoltageResult;
import com.edianniu.pscp.cs.bean.power.UseFengGUReqData;
import com.edianniu.pscp.cs.bean.power.UseFengGUResult;
import com.edianniu.pscp.cs.bean.power.WarningDetailReqData;
import com.edianniu.pscp.cs.bean.power.WarningDetailResult;
import com.edianniu.pscp.cs.bean.power.WarningListReqData;
import com.edianniu.pscp.cs.bean.power.WarningListResult;
import com.edianniu.pscp.cs.bean.power.WarningSaveReqData;
import com.edianniu.pscp.cs.bean.power.WarningSaveResult;
import com.edianniu.pscp.cs.bean.power.ConsumeRankResult;
import com.edianniu.pscp.cs.bean.power.CurrentBalanceReqData;
import com.edianniu.pscp.cs.bean.power.CurrentBalanceResult;

/**
 * 
 * @author zhoujianjian
 * @date 2017年12月7日 下午3:53:32
 */
public interface PowerInfoService {

	/**
	 * 客户线路列表
	 */
	CompanyLinesResult getCompanyLines(CompanyLinesReqData reqData);

	/**
	 * 用电负荷
	 */
	PowerLoadResult getPowerLoad(PowerLoadReqData reqData);

	/**
	 * 用电量
	 */
	PowerQuantityResult getPowerQuantity(PowerQuantityReqData reqData);

	/**
	 * 用电分布
	 */
	PowerDistributeResult getPowerDistribute(PowerDistributeReqData reqData);

	/**
	 * 功率因数
	 */
	PowerFactorResult getPowerFactor(PowerFactorReqData reqData);

	/**
	 * 电费单
	 */
	ChargeBillResult getChargeBill(ChargeBillReqData reqData);

	/**
	 * 电费明细
	 */
	ChargeDetailResult getChargeDetail(ChargeDetailReqData reqData);

	/**
	 * 报警列表
	 */
	WarningListResult getWarningList(WarningListReqData reqData);

	/**
	 * 报警详情
	 */
	WarningDetailResult getWarningDetail(WarningDetailReqData reqData);

	/**
	 * 门户：智能监控>监控列表
	 */
	MonitorListResult getmonitorList(MonitorListReqData reqData);

	/**
	 * 门户：智能监控>监控列表>总览>总览-实时负荷
	 */
	RealTimeLoadResult getRealTimeLoad(RealTimeLoadReqData reqData);

	/**
	 * 门户：智能监控>监控列表>总览>（综合、动力、照明、空调、特殊）
	 */
	GeneralByTypeResult getGeneralByType(GeneralByTypeReqData reqData);

	/**
	 * 门户：智能监控>告警
	 * 获取服务商所有客户的告警列表
	 */
	WarningListResult getAllWarnings(AllWarningsReqData reqData);

	/**
	 * 门户：智能监控>监控列表>用能排行
	 */
	ConsumeRankResult getConsumeRank(ConsumeRankReqData reqData);

	/**
	 * 门户：智能监控>监控列表>电压健康
	 * @param reqData
	 * @return
	 */
	SafetyVoltageResult getSafetyVoltage(SafetyVoltageReqData reqData);

	/**
	 * 门户：智能监控>监控列表>电流平衡
	 */
	CurrentBalanceResult getCurrentBalance(CurrentBalanceReqData reqData);

	/**
	 * 门户：智能监控>监控列表>峰谷利用
	 */
	UseFengGUResult useFengGU(UseFengGUReqData reqData);

	/**
	 * 分页获取监测点数据
	 * 门户：功率因数---获取监测点功率因数
	 */
	CollectorPointsResult getCollectorPointsForPowerFactor(CollectorPointsReqData reqData);
	
	/**
	 * 分页获取监测点数据
	 * 门户：峰谷利用---获取监测点在监测时段用电量、电费
	 */
	CollectorPointsResult getCollectorPointsForPowerQuantity(CollectorPointsReqData reqData);

	/**
	 * 客户设备告警新增(测试时使用)
	 * 废弃--20180307 by zjj
	 */
	WarningSaveResult saveWarning(WarningSaveReqData reqData);

	/**
	 * 判断采集点是否有数据
	 * @param meterId    采集点编号
	 * @param companyId  客户公司ID
	 * @return
	 */
	HasDataResult hasData(String meterId, Long companyId);

	

	

}
