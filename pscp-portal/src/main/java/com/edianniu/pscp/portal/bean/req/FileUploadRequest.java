/**
 *
 */
package com.edianniu.pscp.portal.bean.req;

import com.edianniu.pscp.portal.commons.Constants;

import java.io.Serializable;

/**
 * @author cyl
 */
public class FileUploadRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long uid;//用户ID
    private String token;//token
    private String file;//文件二进制流通过base64转换为字符串
    /**
     * 图片水印(默认0)
     * 0：不加水印
     * 1: 添加水印
     */
    private Integer watermark = Constants.TAG_NO;

    public Long getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public String getFile() {
        return file;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getWatermark() {
        return watermark;
    }

    public void setWatermark(Integer watermark) {
        this.watermark = watermark;
    }
}
