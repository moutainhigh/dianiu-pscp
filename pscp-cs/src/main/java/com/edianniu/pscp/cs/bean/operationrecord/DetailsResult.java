package com.edianniu.pscp.cs.bean.operationrecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.operationrecord.vo.OperationRecordVO;

/**
 * 配电房操作记录详情
 * @author zhoujianjian
 * @date 2017年10月29日 下午10:07:45
 */
public class DetailsResult extends Result {
	private static final long serialVersionUID = 1L;
	
	private OperationRecordVO operation;
	
	public OperationRecordVO getOperation() {
		return operation;
	}

	public void setOperation(OperationRecordVO operation) {
		this.operation = operation;
	}

	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
