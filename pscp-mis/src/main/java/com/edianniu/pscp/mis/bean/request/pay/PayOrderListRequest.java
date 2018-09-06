package com.edianniu.pscp.mis.bean.request.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 支付訂單列表
 * @author zhoujianjian
 * @date 2018年4月20日 下午3:25:05
 */
@JSONMessage(messageCode = 1002202)
public class PayOrderListRequest extends TerminalRequest{
	
	//类别 1 充值，2社会订单支付,3 社会订单结算支付，4响应需求保证金，5项目结算，6电费结算
	private Integer type;
	//状态  0:未支付   1:支付确认中(客户端)   2:支付成功(服务端异步通知)    3:支付失败   4:用户取消
	private Integer status;
	
	private Integer offset;
	
	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
