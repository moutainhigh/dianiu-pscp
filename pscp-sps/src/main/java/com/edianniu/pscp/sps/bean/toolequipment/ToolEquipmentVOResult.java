package com.edianniu.pscp.sps.bean.toolequipment;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;

import java.util.List;

/**
 * ClassName: ToolEquipmentVOResult
 * Author: tandingbo
 * CreateTime: 2017-05-17 15:29
 */
public class ToolEquipmentVOResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<ToolEquipmentVO> toolEquipments;

    public List<ToolEquipmentVO> getToolEquipments() {
        return toolEquipments;
    }

    public void setToolEquipments(List<ToolEquipmentVO> toolEquipments) {
        this.toolEquipments = toolEquipments;
    }
}
