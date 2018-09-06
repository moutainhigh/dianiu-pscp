package com.edianniu.pscp.sps.bean.request.needsorder;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import com.edianniu.pscp.sps.commons.Constants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ListRequest
 * Author: tandingbo
 * CreateTime: 2017-09-26 10:23
 */
@JSONMessage(messageCode = 1002170)
public final class ListRequest extends TerminalRequest {

    private String name;
    private int offset;
    private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
