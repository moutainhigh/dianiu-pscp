package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.worksheetreport.*;

/**
 * ClassName: WorkOrderReportInfoService
 * Author: tandingbo
 * CreateTime: 2017-09-12 10:26
 */
public interface WorkOrderReportInfoService {
    ListResult pageListReport(ListReqData reqData);

    SelectDataResult getReportSelectData(SelectDataReqData reqData);

    SaveResult saveWorkOrderReport(SaveReqData reqData);

    DetailsResult getWorkOrderReportById(DetailsReqData reqData);

    RemoveResult removeWorkOrderReport(RemoveReqData reqData);

    SaveResult saveSurveyReport(SaveReqData reqData);
}
