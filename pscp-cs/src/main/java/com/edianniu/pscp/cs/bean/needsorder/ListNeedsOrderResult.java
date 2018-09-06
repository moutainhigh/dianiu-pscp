package com.edianniu.pscp.cs.bean.needsorder;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderVO;

import java.util.List;

/**
 * ClassName: ListNeedsOrderResult
 * Author: tandingbo
 * CreateTime: 2017-10-09 10:16
 */
public class ListNeedsOrderResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<NeedsOrderVO> needsOrderList;

    public List<NeedsOrderVO> getNeedsOrderList() {
        return needsOrderList;
    }

    public void setNeedsOrderList(List<NeedsOrderVO> needsOrderList) {
        this.needsOrderList = needsOrderList;
    }
}
