package com.edianniu.pscp.mis.bean.response.message;

import com.edianniu.pscp.mis.bean.message.MessageInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListMessageResponse
 * Author: tandingbo
 * CreateTime: 2017-04-17 16:12
 */
@JSONMessage(messageCode = 2002040)
public final class ListMessageResponse extends BaseResponse {
    /**
     * 下一次请求的offset
     */
    private Integer nextOffset;
    /**
     * True:还有下一页。False:最后一页了。
     */
    private boolean hasNext;
    /**
     * 总记录数
     */
    private Integer totalCount;
    /**
     * 消息信息
     */
    private List<MessageInfo> messages;

    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<MessageInfo> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageInfo> messages) {
        this.messages = messages;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
