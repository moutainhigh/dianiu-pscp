package com.edianniu.pscp.renter.mis.bean.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: ConfigureParamResponse
 * Author: tandingbo
 * CreateTime: 2017-04-20 15:24
 */
@JSONMessage(messageCode = 2002313)
public class ConfigureParamResponse extends BaseResponse {

    /**
     * 配置参数
     */
    private String configureParam;

    public String getConfigureParam() {
        return configureParam;
    }

    public void setConfigureParam(String configureParam) {
        this.configureParam = configureParam;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
