/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;

/**
 * 仪表数据
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class MeterLogReqData extends AbstractReportReq implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long time;
	//遥测数据
	private Double abcActivePower;//三相总有功功率
	private Double aActivePower;//A相有功功率
	private Double bActivePower;//B相有功功率
	private Double cActivePower;//C相有功功率
	private Double abcReactivePower;//三相总无功功率
	private Double aReactivePower;//A相无功功率
	private Double bReactivePower;//B相无功功率
	private Double cReactivePower;//C相无功功率
	private Double abcApparentPower;//三相总视在功率
	private Double aApparentPower;//A相视在功率
	private Double bApparentPower;//B相视在功率
	private Double cApparentPower;//C相视在功率
	private Double abcPowerFactor;//三相总功率因数
	private Double aPowerFactor;//A相功率因数
	private Double bPowerFactor;//B相功率因数
	private Double cPowerFactor;//C相功率因数
	private Double positiveActivePowerMaxDemand;//正向有功最大需量
	private Double negativeActivePowerMaxDemand;//反向有功最大需量
	private Double positiveReactivePowerMaxDemand;//正向无功最大需量
	private Double negativeReactivePowerMaxDemand;//反向无功最大需量
	private Double ua;// A相电压
	private Double ub;// B相电压
	private Double uc;// C相电压
	private Integer uStatus;//0正常，1异常
	private Integer uaStatus;//0正常,1过高,-1偏低
	private Integer ubStatus;//0正常,1过高,-1偏低
	private Integer ucStatus;//0正常,1过高,-1偏低
	private Double ia;// A相电流
	private Double ib;// B相电流
	private Double ic;// c相电流
	private Double iUnbalanceDegree;//三相电流不平衡度
	private Double iaUnbalanceDegree;//A相电流不平衡度
	private Double ibUnbalanceDegree;//B相电流不平衡度
	private Double icUnbalanceDegree;//C相电流不平衡度
	

	//电度数据
	private Double positiveTotalActivePowerCharge;//正向总有功电度
	private Double positiveApexActivePowerCharge;//正向尖有功电度
	private Double positivePeakPowerCharge;//正向峰有功电度
	private Double positiveFlatPowerCharge;//正向平有功电度
	private Double positiveValleyPowerCharge;//正向谷有功电度
	private Double negativeTotalActivePowerCharge;//反向总有功电度
	private Double negativeApexActivePowerCharge;//反向尖有功电度
	private Double negativePeakPowerCharge;//反向峰有功电度
	private Double negativeFlatPowerCharge;//反向平有功电度
	private Double negativeValleyPowerCharge;//反向谷有功电度
	private Double positiveTotalReactivePowerCharge;//正向总无功电度
	private Double positiveApexReactivePowerCharge;//正向尖无功电度
	private Double positivePeakReactivePowerCharge;//正向峰无功电度
	private Double positiveFlatReactivePowerCharge;//正向平无功电度
	private Double positiveValleyReactivePowerCharge;//正向谷无功电度
	private Double negativeTotalReactivePowerCharge;//反向总无功电度
	private Double negativeApexReactivePowerCharge;//反向尖无功电度
	private Double negativePeakReactivePowerCharge;//反向峰无功电度
	private Double negativeFlatReactivePowerCharge;//反向平无功电度
	private Double negativeValleyReactivePowerCharge;//反向谷无功电度

	
	@Override
	public String getId() {
		return super.getCompanyId()+"#"+super.getMeterId()+"#"+getTime();
	}
	public Double getIa() {
		return ia;
	}

	public Double getIb() {
		return ib;
	}

	public Double getIc() {
		return ic;
	}

	public void setIa(Double ia) {
		this.ia = ia;
	}

	public void setIb(Double ib) {
		this.ib = ib;
	}

	public void setIc(Double ic) {
		this.ic = ic;
	}

	public Double getUa() {
		return ua;
	}

	public Double getUb() {
		return ub;
	}

	public Double getUc() {
		return uc;
	}

	public void setUa(Double ua) {
		this.ua = ua;
	}

	public void setUb(Double ub) {
		this.ub = ub;
	}

	public void setUc(Double uc) {
		this.uc = uc;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Double getAbcActivePower() {
		return abcActivePower;
	}
	public Double getaActivePower() {
		return aActivePower;
	}
	public Double getbActivePower() {
		return bActivePower;
	}
	public Double getcActivePower() {
		return cActivePower;
	}
	public Double getAbcReactivePower() {
		return abcReactivePower;
	}
	public Double getaReactivePower() {
		return aReactivePower;
	}
	public Double getbReactivePower() {
		return bReactivePower;
	}
	public Double getcReactivePower() {
		return cReactivePower;
	}
	public Double getAbcApparentPower() {
		return abcApparentPower;
	}
	public Double getaApparentPower() {
		return aApparentPower;
	}
	public Double getbApparentPower() {
		return bApparentPower;
	}
	public Double getcApparentPower() {
		return cApparentPower;
	}
	public Double getAbcPowerFactor() {
		return abcPowerFactor;
	}
	public Double getaPowerFactor() {
		return aPowerFactor;
	}
	public Double getbPowerFactor() {
		return bPowerFactor;
	}
	public Double getcPowerFactor() {
		return cPowerFactor;
	}
	public Double getPositiveActivePowerMaxDemand() {
		return positiveActivePowerMaxDemand;
	}
	public Double getNegativeActivePowerMaxDemand() {
		return negativeActivePowerMaxDemand;
	}
	public Double getPositiveReactivePowerMaxDemand() {
		return positiveReactivePowerMaxDemand;
	}
	public Double getNegativeReactivePowerMaxDemand() {
		return negativeReactivePowerMaxDemand;
	}
	public Double getPositiveTotalActivePowerCharge() {
		return positiveTotalActivePowerCharge;
	}
	public Double getPositiveApexActivePowerCharge() {
		return positiveApexActivePowerCharge;
	}
	public Double getPositivePeakPowerCharge() {
		return positivePeakPowerCharge;
	}
	public Double getPositiveFlatPowerCharge() {
		return positiveFlatPowerCharge;
	}
	public Double getPositiveValleyPowerCharge() {
		return positiveValleyPowerCharge;
	}
	public Double getNegativeTotalActivePowerCharge() {
		return negativeTotalActivePowerCharge;
	}
	public Double getNegativeApexActivePowerCharge() {
		return negativeApexActivePowerCharge;
	}
	public Double getNegativePeakPowerCharge() {
		return negativePeakPowerCharge;
	}
	public Double getNegativeFlatPowerCharge() {
		return negativeFlatPowerCharge;
	}
	public Double getNegativeValleyPowerCharge() {
		return negativeValleyPowerCharge;
	}
	public Double getPositiveTotalReactivePowerCharge() {
		return positiveTotalReactivePowerCharge;
	}
	public Double getPositiveApexReactivePowerCharge() {
		return positiveApexReactivePowerCharge;
	}
	public Double getPositivePeakReactivePowerCharge() {
		return positivePeakReactivePowerCharge;
	}
	public Double getPositiveFlatReactivePowerCharge() {
		return positiveFlatReactivePowerCharge;
	}
	public Double getPositiveValleyReactivePowerCharge() {
		return positiveValleyReactivePowerCharge;
	}
	public Double getNegativeTotalReactivePowerCharge() {
		return negativeTotalReactivePowerCharge;
	}
	public Double getNegativeApexReactivePowerCharge() {
		return negativeApexReactivePowerCharge;
	}
	public Double getNegativePeakReactivePowerCharge() {
		return negativePeakReactivePowerCharge;
	}
	public Double getNegativeFlatReactivePowerCharge() {
		return negativeFlatReactivePowerCharge;
	}
	public Double getNegativeValleyReactivePowerCharge() {
		return negativeValleyReactivePowerCharge;
	}
	public void setAbcActivePower(Double abcActivePower) {
		this.abcActivePower = abcActivePower;
	}
	public void setaActivePower(Double aActivePower) {
		this.aActivePower = aActivePower;
	}
	public void setbActivePower(Double bActivePower) {
		this.bActivePower = bActivePower;
	}
	public void setcActivePower(Double cActivePower) {
		this.cActivePower = cActivePower;
	}
	public void setAbcReactivePower(Double abcReactivePower) {
		this.abcReactivePower = abcReactivePower;
	}
	public void setaReactivePower(Double aReactivePower) {
		this.aReactivePower = aReactivePower;
	}
	public void setbReactivePower(Double bReactivePower) {
		this.bReactivePower = bReactivePower;
	}
	public void setcReactivePower(Double cReactivePower) {
		this.cReactivePower = cReactivePower;
	}
	public void setAbcApparentPower(Double abcApparentPower) {
		this.abcApparentPower = abcApparentPower;
	}
	public void setaApparentPower(Double aApparentPower) {
		this.aApparentPower = aApparentPower;
	}
	public void setbApparentPower(Double bApparentPower) {
		this.bApparentPower = bApparentPower;
	}
	public void setcApparentPower(Double cApparentPower) {
		this.cApparentPower = cApparentPower;
	}
	public void setAbcPowerFactor(Double abcPowerFactor) {
		this.abcPowerFactor = abcPowerFactor;
	}
	public void setaPowerFactor(Double aPowerFactor) {
		this.aPowerFactor = aPowerFactor;
	}
	public void setbPowerFactor(Double bPowerFactor) {
		this.bPowerFactor = bPowerFactor;
	}
	public void setcPowerFactor(Double cPowerFactor) {
		this.cPowerFactor = cPowerFactor;
	}
	public void setPositiveActivePowerMaxDemand(Double positiveActivePowerMaxDemand) {
		this.positiveActivePowerMaxDemand = positiveActivePowerMaxDemand;
	}
	public void setNegativeActivePowerMaxDemand(Double negativeActivePowerMaxDemand) {
		this.negativeActivePowerMaxDemand = negativeActivePowerMaxDemand;
	}
	public void setPositiveReactivePowerMaxDemand(
			Double positiveReactivePowerMaxDemand) {
		this.positiveReactivePowerMaxDemand = positiveReactivePowerMaxDemand;
	}
	public void setNegativeReactivePowerMaxDemand(
			Double negativeReactivePowerMaxDemand) {
		this.negativeReactivePowerMaxDemand = negativeReactivePowerMaxDemand;
	}
	public void setPositiveTotalActivePowerCharge(
			Double positiveTotalActivePowerCharge) {
		this.positiveTotalActivePowerCharge = positiveTotalActivePowerCharge;
	}
	public void setPositiveApexActivePowerCharge(
			Double positiveApexActivePowerCharge) {
		this.positiveApexActivePowerCharge = positiveApexActivePowerCharge;
	}
	public void setPositivePeakPowerCharge(Double positivePeakPowerCharge) {
		this.positivePeakPowerCharge = positivePeakPowerCharge;
	}
	public void setPositiveFlatPowerCharge(Double positiveFlatPowerCharge) {
		this.positiveFlatPowerCharge = positiveFlatPowerCharge;
	}
	public void setPositiveValleyPowerCharge(Double positiveValleyPowerCharge) {
		this.positiveValleyPowerCharge = positiveValleyPowerCharge;
	}
	public void setNegativeTotalActivePowerCharge(
			Double negativeTotalActivePowerCharge) {
		this.negativeTotalActivePowerCharge = negativeTotalActivePowerCharge;
	}
	public void setNegativeApexActivePowerCharge(
			Double negativeApexActivePowerCharge) {
		this.negativeApexActivePowerCharge = negativeApexActivePowerCharge;
	}
	public void setNegativePeakPowerCharge(Double negativePeakPowerCharge) {
		this.negativePeakPowerCharge = negativePeakPowerCharge;
	}
	public void setNegativeFlatPowerCharge(Double negativeFlatPowerCharge) {
		this.negativeFlatPowerCharge = negativeFlatPowerCharge;
	}
	public void setNegativeValleyPowerCharge(Double negativeValleyPowerCharge) {
		this.negativeValleyPowerCharge = negativeValleyPowerCharge;
	}
	public void setPositiveTotalReactivePowerCharge(
			Double positiveTotalReactivePowerCharge) {
		this.positiveTotalReactivePowerCharge = positiveTotalReactivePowerCharge;
	}
	public void setPositiveApexReactivePowerCharge(
			Double positiveApexReactivePowerCharge) {
		this.positiveApexReactivePowerCharge = positiveApexReactivePowerCharge;
	}
	public void setPositivePeakReactivePowerCharge(
			Double positivePeakReactivePowerCharge) {
		this.positivePeakReactivePowerCharge = positivePeakReactivePowerCharge;
	}
	public void setPositiveFlatReactivePowerCharge(
			Double positiveFlatReactivePowerCharge) {
		this.positiveFlatReactivePowerCharge = positiveFlatReactivePowerCharge;
	}
	public void setPositiveValleyReactivePowerCharge(
			Double positiveValleyReactivePowerCharge) {
		this.positiveValleyReactivePowerCharge = positiveValleyReactivePowerCharge;
	}
	public void setNegativeTotalReactivePowerCharge(
			Double negativeTotalReactivePowerCharge) {
		this.negativeTotalReactivePowerCharge = negativeTotalReactivePowerCharge;
	}
	public void setNegativeApexReactivePowerCharge(
			Double negativeApexReactivePowerCharge) {
		this.negativeApexReactivePowerCharge = negativeApexReactivePowerCharge;
	}
	public void setNegativePeakReactivePowerCharge(
			Double negativePeakReactivePowerCharge) {
		this.negativePeakReactivePowerCharge = negativePeakReactivePowerCharge;
	}
	public void setNegativeFlatReactivePowerCharge(
			Double negativeFlatReactivePowerCharge) {
		this.negativeFlatReactivePowerCharge = negativeFlatReactivePowerCharge;
	}
	public void setNegativeValleyReactivePowerCharge(
			Double negativeValleyReactivePowerCharge) {
		this.negativeValleyReactivePowerCharge = negativeValleyReactivePowerCharge;
	}
	public Integer getuStatus() {
		return uStatus;
	}
	public Integer getUaStatus() {
		return uaStatus;
	}
	public Integer getUbStatus() {
		return ubStatus;
	}
	public Integer getUcStatus() {
		return ucStatus;
	}
	public Double getiUnbalanceDegree() {
		return iUnbalanceDegree;
	}
	public Double getIaUnbalanceDegree() {
		return iaUnbalanceDegree;
	}
	public Double getIbUnbalanceDegree() {
		return ibUnbalanceDegree;
	}
	public Double getIcUnbalanceDegree() {
		return icUnbalanceDegree;
	}
	public void setuStatus(Integer uStatus) {
		this.uStatus = uStatus;
	}
	public void setUaStatus(Integer uaStatus) {
		this.uaStatus = uaStatus;
	}
	public void setUbStatus(Integer ubStatus) {
		this.ubStatus = ubStatus;
	}
	public void setUcStatus(Integer ucStatus) {
		this.ucStatus = ucStatus;
	}
	public void setiUnbalanceDegree(Double iUnbalanceDegree) {
		this.iUnbalanceDegree = iUnbalanceDegree;
	}
	public void setIaUnbalanceDegree(Double iaUnbalanceDegree) {
		this.iaUnbalanceDegree = iaUnbalanceDegree;
	}
	public void setIbUnbalanceDegree(Double ibUnbalanceDegree) {
		this.ibUnbalanceDegree = ibUnbalanceDegree;
	}
	public void setIcUnbalanceDegree(Double icUnbalanceDegree) {
		this.icUnbalanceDegree = icUnbalanceDegree;
	}
	
}
