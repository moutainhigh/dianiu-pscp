package com.edianniu.pscp.mis.bean.response.electricianworkorder;

import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;
import java.util.Map;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月21日 下午4:20:47
 */
@JSONMessage(messageCode = 2002046)
public final class ElectricianListResponse extends BaseResponse {
    private List<Map<String, Object>> electricianList;

    public List<Map<String, Object>> getElectricianList() {
        return electricianList;
    }

    public void setElectricianList(List<Map<String, Object>> electricianList) {
        this.electricianList = electricianList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }
}
