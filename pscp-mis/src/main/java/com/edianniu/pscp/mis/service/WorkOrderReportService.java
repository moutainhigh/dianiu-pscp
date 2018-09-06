package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.query.WorkOrderReportQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportDetailsVO;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.WorkOrderReport;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface WorkOrderReportService {
    PageResult<ReportListVO> queryList(WorkOrderReportQuery reportQuery);

    void save(WorkOrderReport report, UserInfo userInfo);

    WorkOrderReport getEntityById(Long id, int type);

    void updateMapCondition(Map<String, Object> updateMap);

    ReportDetailsVO getReportDetailsVOById(Long id, int type);

    List<ReportListVO> queryListReport(Map<String, Object> map);
}
