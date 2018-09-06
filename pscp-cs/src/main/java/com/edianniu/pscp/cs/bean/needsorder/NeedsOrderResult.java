package com.edianniu.pscp.cs.bean.needsorder;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;

/**
 * ClassName: NeedsOrderResult
 * Author: tandingbo
 * CreateTime: 2017-09-26 16:46
 */
public class NeedsOrderResult extends Result {
    private static final long serialVersionUID = 1L;

    private NeedsOrderInfo needsOrderInfo;

    public NeedsOrderInfo getNeedsOrderInfo() {
        return needsOrderInfo;
    }

    public void setNeedsOrderInfo(NeedsOrderInfo needsOrderInfo) {
        this.needsOrderInfo = needsOrderInfo;
    }
}
