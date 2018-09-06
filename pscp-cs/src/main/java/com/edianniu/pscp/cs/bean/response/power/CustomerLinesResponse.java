package com.edianniu.pscp.cs.bean.response.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.power.vo.LineVO;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 客户线路列表
 * @author zhoujianjian
 * @date 2017年12月7日 下午2:50:16
 */
@JSONMessage(messageCode = 2002190)
public class CustomerLinesResponse extends BaseResponse{
	
	private List<LineVO> lines;

	public List<LineVO> getLines() {
		return lines;
	}

	public void setLines(List<LineVO> lines) {
		this.lines = lines;
	}
	
}
