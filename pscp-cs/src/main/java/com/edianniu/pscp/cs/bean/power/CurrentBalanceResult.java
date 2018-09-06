package com.edianniu.pscp.cs.bean.power;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 智能监控>监控列表>电流平衡
 * @author zhoujianjian
 * @date 2017年12月16日 上午9:55:45
 */
public class CurrentBalanceResult extends Result{

	private static final long serialVersionUID = 1L;
	
	// 线路ID
	private Long lineId;
	// 线路名称
	private String lineName;
	// A相
	private String aPhase;
	// B相
	private String bPhase;
	// C相
	private String cPhase;
	// 实时不平衡度
	private String unbalanceDegreeOfNow;
	// 不平衡度记录
	private List<UnbalanceDegree> unbalanceDegrees;
	// 当前电流是否平衡   0：不平衡     1：平衡
	private Integer isBalance;
	// A相失衡点
	private List<Map<String, Object>> aUnbalancePointList;
	// B相失衡点
	private List<Map<String, Object>> bUnbalancePointList;
	// C相失衡点
	private List<Map<String, Object>> cUnbalancePointList;
	
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getaPhase() {
		return aPhase;
	}
	public void setaPhase(String aPhase) {
		this.aPhase = aPhase;
	}
	public String getbPhase() {
		return bPhase;
	}
	public void setbPhase(String bPhase) {
		this.bPhase = bPhase;
	}
	public String getcPhase() {
		return cPhase;
	}
	public void setcPhase(String cPhase) {
		this.cPhase = cPhase;
	}
	public String getUnbalanceDegreeOfNow() {
		return unbalanceDegreeOfNow;
	}
	public void setUnbalanceDegreeOfNow(String unbalanceDegreeOfNow) {
		this.unbalanceDegreeOfNow = unbalanceDegreeOfNow;
	}
	public List<UnbalanceDegree> getUnbalanceDegrees() {
		return unbalanceDegrees;
	}
	public void setUnbalanceDegrees(List<UnbalanceDegree> unbalanceDegrees) {
		this.unbalanceDegrees = unbalanceDegrees;
	}
	public Integer getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(Integer isBalance) {
		this.isBalance = isBalance;
	}
	public List<Map<String, Object>> getaUnbalancePointList() {
		return aUnbalancePointList;
	}
	public void setaUnbalancePointList(List<Map<String, Object>> aUnbalancePointList) {
		this.aUnbalancePointList = aUnbalancePointList;
	}
	public List<Map<String, Object>> getbUnbalancePointList() {
		return bUnbalancePointList;
	}
	public void setbUnbalancePointList(List<Map<String, Object>> bUnbalancePointList) {
		this.bUnbalancePointList = bUnbalancePointList;
	}
	public List<Map<String, Object>> getcUnbalancePointList() {
		return cUnbalancePointList;
	}
	public void setcUnbalancePointList(List<Map<String, Object>> cUnbalancePointList) {
		this.cUnbalancePointList = cUnbalancePointList;
	}
	

}
