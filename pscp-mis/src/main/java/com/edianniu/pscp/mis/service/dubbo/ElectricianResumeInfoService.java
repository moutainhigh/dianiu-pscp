package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.electricianresume.*;

/**
 * ClassName: ElectricianResumeInfoService
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:16
 */
public interface ElectricianResumeInfoService {
    /**
     * 简历详情
     *
     * @param reqData
     * @return
     */
    DetailResult resumeDetail(DetailReqData reqData);

    /**
     * 修改简历信息
     *
     * @param reqData
     * @return
     */
    UpdateResult updateResume(UpdateReqData reqData);

    /**
     * 设置期望费用
     *
     * @param reqData
     * @return
     */
    SetExpectedFeeResult setExpectedFee(SetExpectedFeeReqData reqData);
}
