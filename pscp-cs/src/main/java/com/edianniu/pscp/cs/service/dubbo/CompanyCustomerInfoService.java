package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.company.BindingFacilitatorReqData;
import com.edianniu.pscp.cs.bean.company.BindingFacilitatorResult;

/**
 * ClassName: CompanyCustomerInfoService
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:13
 */
public interface CompanyCustomerInfoService {
   
	/**
     * 获取客户绑定过的服务商信息
     * @param listReqData
     * @return
     */
    BindingFacilitatorResult queryBindingFacilitator(BindingFacilitatorReqData listReqData);
}
