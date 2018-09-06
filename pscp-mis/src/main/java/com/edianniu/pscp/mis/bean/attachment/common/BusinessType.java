package com.edianniu.pscp.mis.bean.attachment.common;
/**
 * 附件业务类型
 * @author zhoujianjian
 */
public enum BusinessType {
	
	NEEDS_ATTACHMENT(1, "需求附件业务类型"),
	COOPERATION_ATTACHMENT(2, "合作附件业务类型"),
	QUOTE_ATTACHMENT(3, "报价附件业务类型"),
	ACTUAL_PRICE_ATTACHMENT(4, "实际结算金额附件业务类型"),
	DUTY_LOG_ATTACHMENT(5, "配电房值班日志附件"),
	DEFECT_RECORD_ATTACHMENT(6, "缺陷记录附件"),
	SOLVE_DEFECT_RECORD_ATTACHMENT(7, "缺陷整改附件"),
	REPAIR_TEST_RECORD_ATTACHMENT(8, "修试报告附件"),
	PATROL_RECORD_ATTACHMENT(9, "巡视报告附件"),
	SURVEY_RECORD_ATTACHMENT(100, "勘察报告附件");
	
	
	private Integer value;
	private String desc;
	
	BusinessType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}
