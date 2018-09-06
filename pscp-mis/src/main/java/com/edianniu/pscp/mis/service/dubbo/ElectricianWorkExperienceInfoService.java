package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.workexperience.DelWorkExperienceReqData;
import com.edianniu.pscp.mis.bean.workexperience.SaveOrUpdateReqData;

/**
 * ClassName: ElectricianWorkExperienceInfoService
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:38
 */
public interface ElectricianWorkExperienceInfoService {
    /**
     * 保存或修改工作经历
     *
     * @param reqData
     * @return
     */
    Result saveOrUpdate(SaveOrUpdateReqData reqData);

    /**
     * 删除工作经历
     *
     * @param reqData
     * @return
     */
    Result delWorkExperience(DelWorkExperienceReqData reqData);
}
