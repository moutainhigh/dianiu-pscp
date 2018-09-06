package com.edianniu.pscp.mis.bean.worksheetreport;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: SelectDataResult
 * Author: tandingbo
 * CreateTime: 2017-10-18 14:46
 */
public class SelectDataResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 修试记录
     */
    private List<ReportListVO> repairTestRecordList;
    /**
     * 巡视记录
     */
    private List<ReportListVO> patrolRecordList;

    public List<ReportListVO> getRepairTestRecordList() {
        return repairTestRecordList;
    }

    public void setRepairTestRecordList(List<ReportListVO> repairTestRecordList) {
        this.repairTestRecordList = repairTestRecordList;
    }

    public List<ReportListVO> getPatrolRecordList() {
        return patrolRecordList;
    }

    public void setPatrolRecordList(List<ReportListVO> patrolRecordList) {
        this.patrolRecordList = patrolRecordList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
