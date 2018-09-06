package com.edianniu.pscp.mis.bean.defectrecord;

import java.io.Serializable;
import java.util.List;

import com.edianniu.pscp.mis.domain.CommonAttachment;

/**
 * ClassName: CorrectionReqData
 * Author: tandingbo
 * CreateTime: 2017-09-12 17:52
 */
public class CorrectionReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long id;
    private String orderId;
    private String solver;
    private String solveDate;
    private String solveRemark;
    private List<CommonAttachment> solveAttachmentList;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSolver() {
        return solver;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }

    public String getSolveDate() {
        return solveDate;
    }

    public void setSolveDate(String solveDate) {
        this.solveDate = solveDate;
    }

    public String getSolveRemark() {
        return solveRemark;
    }

    public void setSolveRemark(String solveRemark) {
        this.solveRemark = solveRemark;
    }

	public List<CommonAttachment> getSolveAttachmentList() {
		return solveAttachmentList;
	}

	public void setSolveAttachmentList(List<CommonAttachment> solveAttachmentList) {
		this.solveAttachmentList = solveAttachmentList;
	}
}
