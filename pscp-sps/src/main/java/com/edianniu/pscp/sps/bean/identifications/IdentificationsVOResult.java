package com.edianniu.pscp.sps.bean.identifications;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;

import java.util.List;

/**
 * ClassName: IdentificationsVOResult
 * Author: tandingbo
 * CreateTime: 2017-05-17 18:18
 */
public class IdentificationsVOResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<IdentificationsVO> identificationsVOList;

    public List<IdentificationsVO> getIdentificationsVOList() {
        return identificationsVOList;
    }

    public void setIdentificationsVOList(List<IdentificationsVO> identificationsVOList) {
        this.identificationsVOList = identificationsVOList;
    }
}
