package com.edianniu.pscp.cs.service.dubbo.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.ConfigureParamResult;
import com.edianniu.pscp.cs.commons.ConfigureParam;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.dubbo.ConfigureParamService;
import com.edianniu.pscp.cs.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ConfigureParamServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-04-20 16:03
 */
@Service
@Repository("configureParamService")
public class ConfigureParamServiceImpl implements ConfigureParamService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigureParamServiceImpl.class);

    /**
     * 构建配置参数信息
     *
     * @return
     */
    @Override
    public ConfigureParamResult buildConfigureParam() {
        ConfigureParamResult result = new ConfigureParamResult();
        try {
            Map<String, Object> configureParamMap = new HashMap<>();

            // 工作日志附件限制数量
            configureParamMap.put("workLogFileNum", ConfigureParam.WORK_LOG_FILE_NUM);
            PropertiesUtil config = new PropertiesUtil("app.properties");
            // api接口的baseurl
            configureParamMap.put("api_url", config.getProperty("server.baseurl"));
            // 访问图片的baseurl
            configureParamMap.put("image_show_url", config.getProperty("pic.server.baseurl"));
            // 用户指南页面的url
            configureParamMap.put("userguidePage_url", config.getProperty("user.guide"));
            // 联系我们页面的url
            configureParamMap.put("aboutUsPage_url", config.getProperty("contact.us"));
            // 需求订单响应保证金
            configureParamMap.put("deposit", config.getProperty("needs.order.deposit"));

            result.setConfigureParam(JSON.toJSONString(configureParamMap));
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }
}
