package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.inspectinglog.ListReqData;
import com.edianniu.pscp.cs.bean.inspectinglog.ListResult;
import com.edianniu.pscp.cs.bean.inspectinglog.SaveReqData;
import com.edianniu.pscp.cs.bean.inspectinglog.SaveResult;

/**
 * 客户设备巡检日志
 * @author zhoujianjian
 * @date 2017年11月1日 下午10:56:01
 */
public interface InspectingLogInfoService {

	SaveResult saveInspectingLog(SaveReqData saveReqData);

	ListResult getInspectingLogList(ListReqData listReqData);

}
