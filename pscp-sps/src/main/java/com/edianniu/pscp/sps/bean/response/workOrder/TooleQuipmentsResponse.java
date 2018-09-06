package com.edianniu.pscp.sps.bean.response.workOrder;

import com.edianniu.pscp.sps.bean.response.BaseResponse;
import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: TooleQuipmentsResponse
 * Author: tandingbo
 * CreateTime: 2017-08-04 09:42
 */
@JSONMessage(messageCode = 2002113)
public class TooleQuipmentsResponse extends BaseResponse {

    private List<ToolEquipmentVO> toolEquipments;

    public List<ToolEquipmentVO> getToolEquipments() {
        return toolEquipments;
    }

    public void setToolEquipments(List<ToolEquipmentVO> toolEquipments) {
        this.toolEquipments = toolEquipments;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
