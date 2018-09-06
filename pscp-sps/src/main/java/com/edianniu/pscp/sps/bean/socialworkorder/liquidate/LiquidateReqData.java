package com.edianniu.pscp.sps.bean.socialworkorder.liquidate;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: LiquidateReqData
 * Author: tandingbo
 * CreateTime: 2017-05-25 11:24
 */
public class LiquidateReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long socialWorkOrderId;
    private List<LiquidateInfo> liquidateInfoList;

    public LiquidateReqData() {
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getSocialWorkOrderId() {
        return socialWorkOrderId;
    }

    public void setSocialWorkOrderId(Long socialWorkOrderId) {
        this.socialWorkOrderId = socialWorkOrderId;
    }

    public List<LiquidateInfo> getLiquidateInfoList() {
        return liquidateInfoList;
    }

    public void setLiquidateInfoList(List<LiquidateInfo> liquidateInfoList) {
        this.liquidateInfoList = liquidateInfoList;
    }

}
