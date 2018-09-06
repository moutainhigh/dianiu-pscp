package com.edianniu.pscp.sps.bean.socialworkorder.liquidate;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateVO;

/**
 * ClassName: LiquidateEvaluateResult
 * Author: tandingbo
 * CreateTime: 2017-08-09 11:50
 */
public class LiquidateEvaluateResult extends Result {
    private static final long serialVersionUID = 1L;

    /* 单位费用.*/
    private String fee;
    /* 费用小计.*/
    private String totalFee;
    /* 实际工单时间.*/
    private String actualWorkTime;
    /* 评价信息.*/
    private EvaluateVO evaluateInfo;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getActualWorkTime() {
        return actualWorkTime;
    }

    public void setActualWorkTime(String actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public EvaluateVO getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(EvaluateVO evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }
}
