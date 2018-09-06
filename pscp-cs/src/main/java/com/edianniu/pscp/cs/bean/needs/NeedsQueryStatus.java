package com.edianniu.pscp.cs.bean.needs;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;


/**
 * 需求列表查询状态
 * @author zhoujianjian
 * 2017年9月21日下午1:30:57
 */
public enum NeedsQueryStatus {
	
	/**
	 * verifying, responding, quoting, finished 供客户端客户端使用
	 */
	VERIFYING("verifying","审核中"){
		@Override
		public List<Integer> getStatus(){
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(NeedsStatus.AUDITING.getValue());
			return statusList;
		}
	},
	RESPONDING("responding","响应中"){
		@Override
		public List<Integer> getStatus(){
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(NeedsStatus.RESPONDING.getValue());
			return statusList;
		}
	},
	QUOTING("quoting","报价中"){
		@Override
		public List<Integer> getStatus(){
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(NeedsStatus.QUOTING.getValue());
			statusList.add(NeedsStatus.QUOTED.getValue());
			return statusList;
		} 
	},
	FINISHED("finished","已结束"){
		@Override
		public List<Integer> getStatus(){
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(NeedsStatus.OVERTIME.getValue());
			statusList.add(NeedsStatus.CANCELED.getValue());
			statusList.add(NeedsStatus.FAIL_AUDIT.getValue());
			statusList.add(NeedsStatus.COOPETATED.getValue());
			return statusList;
		}
	},
	
	
	/**
	 * not_audit, audit_succeed,  audit_failed 供系统后台使用
	 */
	NOT_AUDIT("not_audit", "未审核"){
		@Override
		public List<Integer> getStatus(){
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(NeedsStatus.AUDITING.getValue());
			return statusList;
		}
	},
	AUDIT_SUCCEED("audit_succeed", "审核通过"){
		@Override
		public List<Integer> getStatus(){
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(NeedsStatus.OVERTIME.getValue());
			statusList.add(NeedsStatus.CANCELED.getValue());
			statusList.add(NeedsStatus.RESPONDING.getValue());
			statusList.add(NeedsStatus.QUOTING.getValue());
			statusList.add(NeedsStatus.QUOTED.getValue());
			statusList.add(NeedsStatus.COOPETATED.getValue());
			return statusList;
		}
	},
	AUDIT_FAILED("audit_failed", "审核未通过"){
		@Override
		public List<Integer> getStatus(){
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(NeedsStatus.FAIL_AUDIT.getValue());
			return statusList;
		}
	};
	
	private String value;
	private String name;
	
	private NeedsQueryStatus(String value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public String getValue(){
		return value;
	}
	public String getName(){
		return name;
	}
	public List<Integer> getStatus(){
		return new ArrayList<Integer>();
	}
	
	/**
	 * 根据value获取NeedsQueryStatus对象
	 * @param value
	 * @return
	 */
	public static NeedsQueryStatus getByValue(String value){
		if (StringUtils.isBlank(value)) {
			return null;
		}
		NeedsQueryStatus[] values = NeedsQueryStatus.values();
		for (NeedsQueryStatus needsQueryStatus : values) {
			if (value.equals(needsQueryStatus.getValue())) {
				return needsQueryStatus;
			}
		}
		return null;
	}
	
	/**
	 * 判断value是否存在
	 * @param value
	 * @return
	 */
	public static Boolean isExist(String value){
		if (StringUtils.isBlank(value)) {
			return false;
		}
		NeedsQueryStatus[] values = NeedsQueryStatus.values();
		for (NeedsQueryStatus needsQueryStatus : values) {
			if (value.equals(needsQueryStatus.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	
}
