package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.dutylog.DeleteReqData;
import com.edianniu.pscp.cs.bean.dutylog.DeleteResult;
import com.edianniu.pscp.cs.bean.dutylog.DetailsReqData;
import com.edianniu.pscp.cs.bean.dutylog.DetailsResult;
import com.edianniu.pscp.cs.bean.dutylog.ListReqData;
import com.edianniu.pscp.cs.bean.dutylog.ListResult;
import com.edianniu.pscp.cs.bean.dutylog.SaveReqData;
import com.edianniu.pscp.cs.bean.dutylog.SaveResult;

/**
 * @author zhoujianjian
 * @date 2017年10月30日 下午1:47:26
 */
public interface DutyLogInfoService {

	SaveResult saveDutyLog(SaveReqData saveReqData);

	ListResult dutyLogList(ListReqData listReqData);

	DetailsResult getDutyLogDetails(DetailsReqData detailsReqData);

	DeleteResult deleteDutyLog(DeleteReqData deleteReqData);
	

}
