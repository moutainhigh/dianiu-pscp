package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: EvaluateInfo
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:14
 */
public class EvaluateInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务质量(0(0星)，1（1星），2(2星)，3（3星），4（4星），5（5星）)
     */
    private String serviceQuality;

    /**
     * 服务速度(0(0星)，1（1星），2(2星)，3（3星），4（4星），5（5星）)
     */
    private String serviceSpeed;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 附件信息，多个以逗号分隔
     */
    private String attachment;

    public String getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(String serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public String getServiceSpeed() {
        return serviceSpeed;
    }

    public void setServiceSpeed(String serviceSpeed) {
        this.serviceSpeed = serviceSpeed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
