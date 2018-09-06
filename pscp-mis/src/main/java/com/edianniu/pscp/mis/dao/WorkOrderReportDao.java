package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.query.WorkOrderReportQuery;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportDetailsVO;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import com.edianniu.pscp.mis.domain.WorkOrderReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WorkOrderReportDao {

    List<ReportListVO> queryList(WorkOrderReportQuery reportQuery);

    int queryCount(WorkOrderReportQuery reportQuery);

    Long save(WorkOrderReport report);

    WorkOrderReport getEntityById(@Param("id") Long id, @Param("type") int type);

    ReportDetailsVO getReportDetailsVOById(@Param("id") Long id, @Param("type") int type);

    void updateMapCondition(Map<String, Object> updateMap);

    List<ReportListVO> queryListReport(Map<String, Object> map);
}
