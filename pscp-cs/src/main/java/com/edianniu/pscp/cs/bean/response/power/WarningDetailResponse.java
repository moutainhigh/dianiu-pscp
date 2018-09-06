package com.edianniu.pscp.cs.bean.response.power;

import com.edianniu.pscp.cs.bean.power.vo.WarningVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 警报详情
 * @author zhoujianjian
 * @date 2017年12月11日 下午5:32:48
 */
@JSONMessage(messageCode = 2002189)
public class WarningDetailResponse extends BaseResponse{
	
	private WarningVO warning;

	public WarningVO getWarning() {
		return warning;
	}

	public void setWarning(WarningVO warning) {
		this.warning = warning;
	}
	
}
