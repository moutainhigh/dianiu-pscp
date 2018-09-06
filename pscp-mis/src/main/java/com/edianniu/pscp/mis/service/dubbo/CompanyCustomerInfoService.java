package com.edianniu.pscp.mis.service.dubbo;

import java.util.List;

import com.edianniu.pscp.mis.bean.company.CompanyCustomerInfo;

public interface CompanyCustomerInfoService {

    CompanyCustomerInfo getById(Long id);
    
    /**
     * 通过客户memberId或服务商companyId获取已绑定的CompanyCustomer信息
     * @param memberId
     * @param companyId
     * @return
     */
    List<CompanyCustomerInfo> getInfo(Long memberId, Long companyId);
    
}