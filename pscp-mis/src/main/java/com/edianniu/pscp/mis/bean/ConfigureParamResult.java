package com.edianniu.pscp.mis.bean;

/**
 * ClassName: ConfigureParamResult
 * Author: tandingbo
 * CreateTime: 2017-04-20 15:26
 */
public class ConfigureParamResult extends Result {
    private static final long serialVersionUID = 1L;

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
}
