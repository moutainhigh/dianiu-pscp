package com.edianniu.pscp.renter.mis.service.dubbo;

import com.edianniu.pscp.renter.mis.bean.ConfigureParamResult;

/**
 * ClassName: ConfigureParamService
 * Author: tandingbo
 * CreateTime: 2017-04-20 16:02
 */
public interface RenterConfigureParamService {
    /**
     * 构建配置参数信息
     *
     * @return
     */
    ConfigureParamResult buildConfigureParam();
}
