package com.edianniu.pscp.mis.bean.message;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: ListMessageResult
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:46
 */
public class ListMessageResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 下一次请求的offset
     */
    private Integer nextOffset;
    /**
     * true:还有下一页
     * false:已经到了最后一页了。
     */
    private Boolean hasNext;
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

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
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
