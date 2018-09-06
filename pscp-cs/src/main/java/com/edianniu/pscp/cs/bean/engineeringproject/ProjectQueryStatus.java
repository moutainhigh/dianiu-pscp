package com.edianniu.pscp.cs.bean.engineeringproject;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询项目列表状态
 * 
 * @author zhoujianjian 2017年9月21日上午11:16:05
 */
public enum ProjectQueryStatus {

	PROGRESSING("progressing", "进行中") {
		@Override
		public List<Integer> getStatus() {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EngineeringProjectStatus.ONGOING.getValue());
			statusList.add(EngineeringProjectStatus.CONFIRM_COST.getValue());
			statusList.add(EngineeringProjectStatus.PAYFAILD.getValue());
			return statusList;
		}
		
		@Override
		public List<EngineeringProjectStatus> getProjectStatus(){
			List<EngineeringProjectStatus> statuss = new ArrayList<>();
			statuss.add(EngineeringProjectStatus.ONGOING);
			statuss.add(EngineeringProjectStatus.CONFIRM_COST);
			statuss.add(EngineeringProjectStatus.PAYFAILD);
			return statuss;
		}
	},
		
	FINISHED("finished", "已结束") {
		@Override
		public List<Integer> getStatus() {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EngineeringProjectStatus.SETTLED.getValue());
			return statusList;
		}
		@Override
		public List<EngineeringProjectStatus> getProjectStatus(){
			List<EngineeringProjectStatus> statuss = new ArrayList<>();
			statuss.add(EngineeringProjectStatus.SETTLED);
			return statuss;
		}
	};

	private final String value;
	private final String name;

	private ProjectQueryStatus(String value, String name) {
		this.value = value;
		this.name = name();
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public List<Integer> getStatus() {
		return new ArrayList<Integer>();
	}
	
	public List<EngineeringProjectStatus> getProjectStatus() {
		return new ArrayList<EngineeringProjectStatus>();
	}
	
	/**
	 * 判断某一分组是否存在某一EngineeringProjectStatus
	 * @param projectStatusDesc
	 * @return
	 */
	public boolean isProjectStatusExist(String projectStatusDesc){
		if (StringUtils.isBlank(projectStatusDesc)) {
			return false;
		}
		List<EngineeringProjectStatus> projectStatusList = getProjectStatus();
		for (EngineeringProjectStatus projectStatus : projectStatusList) {
			if (projectStatus.getDesc().equals(projectStatusDesc)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据值获取Enum对象
	 * @param value
	 * @return
	 */
	public static ProjectQueryStatus getByValue(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		ProjectQueryStatus[] values = ProjectQueryStatus.values();
		for (ProjectQueryStatus projectQueryStatus : values) {
			if (value.equals(projectQueryStatus.getValue())) {
				return projectQueryStatus;
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
		ProjectQueryStatus[] values = ProjectQueryStatus.values();
		for (ProjectQueryStatus projectQueryStatus : values) {
			if (value.equals(projectQueryStatus.getValue())) {
				return true;
			}
		}
		return false;
	}
}
