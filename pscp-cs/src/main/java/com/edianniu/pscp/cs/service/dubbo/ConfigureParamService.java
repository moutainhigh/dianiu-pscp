package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.ConfigureParamResult;

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
