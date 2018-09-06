package com.edianniu.pscp.cs.bean.response.operationrecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.operationrecord.vo.OperationRecordVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房操作记录详情
 * @author zhoujianjian
 * @date 2017年10月29日 下午10:02:39
 */
@JSONMessage(messageCode = 2002150)
public class DetailsResponse extends BaseResponse{
	
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
