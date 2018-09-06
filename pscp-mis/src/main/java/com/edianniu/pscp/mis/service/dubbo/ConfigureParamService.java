package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.ConfigureParamResult;

/**
 * ClassName: ConfigureParamService
 * Author: tandingbo
 * CreateTime: 2017-04-20 16:02
 */
public interface ConfigureParamService {
    /**
     * 构建配置参数信息
     *
     * @return
     */
    ConfigureParamResult buildConfigureParam();
}
