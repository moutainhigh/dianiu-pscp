package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.socialworkorder.resume.ResumeReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.ResumeResult;

/**
 * ClassName: ElectricianResumeInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-24 14:20
 */
public interface ElectricianResumeInfoService {
    ResumeResult details(ResumeReqData resumeReqData);
}
