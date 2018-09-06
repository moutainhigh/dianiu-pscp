package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.operationrecord.DeleteReqData;
import com.edianniu.pscp.cs.bean.operationrecord.DeleteResult;
import com.edianniu.pscp.cs.bean.operationrecord.DetailsReqData;
import com.edianniu.pscp.cs.bean.operationrecord.DetailsResult;
import com.edianniu.pscp.cs.bean.operationrecord.ListReqData;
import com.edianniu.pscp.cs.bean.operationrecord.ListResult;
import com.edianniu.pscp.cs.bean.operationrecord.SaveReqData;
import com.edianniu.pscp.cs.bean.operationrecord.SaveResult;

/**
 * 配电房客户操作记录
 * @author zhoujianjian
 * @date 2017年10月18日 下午5:25:54
 */
public interface OperationRecordInfoService {

	SaveResult saveOperationRecord(SaveReqData saveReqData);

	ListResult operationRecordList(ListReqData listReqData);

	DetailsResult getOperationRecordDetails(DetailsReqData detailsReqData);

	DeleteResult deleteOperationRecord(DeleteReqData deleteReqData);
}
