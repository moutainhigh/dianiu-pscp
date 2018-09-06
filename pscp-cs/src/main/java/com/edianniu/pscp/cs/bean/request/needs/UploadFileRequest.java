package com.edianniu.pscp.cs.bean.request.needs;

import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * 上传、编辑合作附件
 *
 * @author zhoujianjian
 * 2017年9月18日下午2:46:40
 */
@JSONMessage(messageCode = 1002129)
public class UploadFileRequest extends TerminalRequest {

    private String orderId;
    //删除的附件fId 多个用逗号分隔
    private String removedImgs;

    private List<Attachment> attachmentList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemovedImgs() {
        return removedImgs;
    }

    public void setRemovedImgs(String removedImgs) {
        this.removedImgs = removedImgs;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
