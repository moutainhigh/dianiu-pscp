package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.member.*;

/**
 * ClassName: MemberInformationService
 * Author: tandingbo
 * CreateTime: 2017-08-21 15:22
 */
public interface MemberInformationService {
    /**
     * 电工(企业、社会)会员登录信息
     *
     * @param reqData
     * @return
     */
    MemberElectricianResult getElectricianMemberInfo(MemberElectricianReqData reqData);

    /**
     * 客户会员登录信息
     *
     * @param reqData
     * @return
     */
    MemberCustomerResult getCustomerMemberInfo(MemberCustomerReqData reqData);

    /**
     * 服务商会员登录信息
     *
     * @param reqData
     * @return
     */
    MemberFacilitatorResult getFacilitatorMemberInfo(MemberFacilitatorReqData reqData);
}
