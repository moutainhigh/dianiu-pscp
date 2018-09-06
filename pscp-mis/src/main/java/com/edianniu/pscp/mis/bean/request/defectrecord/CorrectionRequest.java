package com.edianniu.pscp.mis.bean.request.defectrecord;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import com.edianniu.pscp.mis.domain.CommonAttachment;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: CorrectionRequest
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:10
 */
@JSONMessage(messageCode = 1002158)
public final class CorrectionRequest extends TerminalRequest {

    private Long id;
    private String orderId;
    private String solver;
    private String solveDate;
    private String solveRemark;
    private List<CommonAttachment> solveAttachmentList;

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

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}

