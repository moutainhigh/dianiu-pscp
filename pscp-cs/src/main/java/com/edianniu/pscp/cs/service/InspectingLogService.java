package com.edianniu.pscp.cs.service;

import com.edianniu.pscp.cs.bean.inspectinglog.ListReqData;
import com.edianniu.pscp.cs.bean.inspectinglog.SaveReqData;
import com.edianniu.pscp.cs.bean.inspectinglog.vo.InspectingLogVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;

/**
 * 客户设备巡检日志
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:51:33
 */
public interface InspectingLogService {

	void saveInspectingLog(SaveReqData saveReqData, UserInfo userInfo);

	PageResult<InspectingLogVO> getInspectingLogVOList(ListReqData listReqData, UserInfo userInfo);

}
