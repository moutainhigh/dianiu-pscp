package com.edianniu.pscp.sps.bean.request.electricianworkorder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ListRequest
 * Author: tandingbo
 * CreateTime: 2017-07-11 11:37
 */
@JSONMessage(messageCode = 1002110)
public final class ListRequest extends TerminalRequest {

    /**
     * 工单状态
     * 未确认：unconfirm
     * 进行中：ongoing
     * 待结算：unsettlement
     * 已完成：finished
     */
    private String status;
    /**
     * 分页页码(默认0)
     */
    private Integer offset;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 搜索字段-
     */
    private String name;
    /**
     * 电工名字搜索
     */
    private String electricianName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElectricianName() {
        return electricianName;
    }

    public void setElectricianName(String electricianName) {
        this.electricianName = electricianName;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
