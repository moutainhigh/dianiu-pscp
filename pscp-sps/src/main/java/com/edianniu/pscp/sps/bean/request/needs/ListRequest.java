package com.edianniu.pscp.sps.bean.request.needs;

import com.edianniu.pscp.sps.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ListRequest
 * Author: tandingbo
 * CreateTime: 2017-09-25 15:57
 */
@JSONMessage(messageCode = 1002168)
public final class ListRequest extends TerminalRequest {

    private int offset;
    /**
     * 需求名称搜索
     */
    private String searchText;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
