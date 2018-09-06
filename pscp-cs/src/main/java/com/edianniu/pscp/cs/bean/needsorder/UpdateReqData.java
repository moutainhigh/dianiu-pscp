package com.edianniu.pscp.cs.bean.needsorder;

import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderVO;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: UpdateReqData
 * Author: tandingbo
 * CreateTime: 2017-10-09 13:45
 */
public class UpdateReqData implements Serializable {
    private static final long serialVersionUID = -4501405230783125608L;

    private List<NeedsOrderVO> needsOrderList;

    public List<NeedsOrderVO> getNeedsOrderList() {
        return needsOrderList;
    }

    public void setNeedsOrderList(List<NeedsOrderVO> needsOrderList) {
        this.needsOrderList = needsOrderList;
    }
}
