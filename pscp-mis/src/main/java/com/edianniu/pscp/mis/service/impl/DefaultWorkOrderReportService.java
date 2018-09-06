package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.query.WorkOrderReportQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportDetailsVO;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.WorkOrderReportDao;
import com.edianniu.pscp.mis.domain.WorkOrderReport;
import com.edianniu.pscp.mis.service.WorkOrderReportService;
import com.edianniu.pscp.mis.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultWorkOrderReportService
 * Author: tandingbo
 * CreateTime: 2017-09-12 10:25
 */
@Service
@Repository("workOrderReportService")
public class DefaultWorkOrderReportService implements WorkOrderReportService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultWorkOrderReportService.class);

    @Autowired
    private WorkOrderReportDao workOrderReportDao;

    @Override
    public PageResult<ReportListVO> queryList(WorkOrderReportQuery reportQuery) {
        PageResult<ReportListVO> result = new PageResult<ReportListVO>();
        int total = workOrderReportDao.queryCount(reportQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<ReportListVO> list = workOrderReportDao.queryList(reportQuery);
            result.setData(list);
            nextOffset = reportQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }
        result.setOffset(reportQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    @Transactional
    @Override
    public void save(WorkOrderReport report, UserInfo userInfo) {
        workOrderReportDao.save(report);
    }

    @Override
    public WorkOrderReport getEntityById(Long id, int type) {
        return workOrderReportDao.getEntityById(id, type);
    }

    @Transactional
    @Override
    public void updateMapCondition(Map<String, Object> updateMap) {
        workOrderReportDao.updateMapCondition(updateMap);
    }

    @Override
    public ReportDetailsVO getReportDetailsVOById(Long id, int type) {
        return workOrderReportDao.getReportDetailsVOById(id, type);
    }

    @Override
    public List<ReportListVO> queryListReport(Map<String, Object> map) {
        return workOrderReportDao.queryListReport(map);
    }

    /**
     * 构建工单报告列表信息
     * @param workOrderReport
     * @return
     */
    @SuppressWarnings("unused")
	private ReportListVO getReportListVO(WorkOrderReport workOrderReport) {
        ReportListVO reportListVO = new ReportListVO();
        BeanUtils.copyProperties(workOrderReport, reportListVO);
        reportListVO.setWorkDate(DateUtil.getFormatDate(workOrderReport.getWorkDate(), DateUtil.YYYY_MM_DD_FORMAT));
        return reportListVO;
    }
}
