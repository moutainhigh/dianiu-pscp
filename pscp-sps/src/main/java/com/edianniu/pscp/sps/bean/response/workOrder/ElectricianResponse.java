package com.edianniu.pscp.sps.bean.response.workOrder;

import com.edianniu.pscp.sps.bean.electrician.vo.ElectricianVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ElectricianResponse
 * Author: tandingbo
 * CreateTime: 2017-06-29 10:39
 */
@JSONMessage(messageCode = 2002100)
public final class ElectricianResponse extends BaseResponse {

    private List<ElectricianVO> electricianList;

    public List<ElectricianVO> getElectricianList() {
        return electricianList;
    }

    public void setElectricianList(List<ElectricianVO> electricianList) {
        this.electricianList = electricianList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
