package com.edianniu.pscp.mis.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.mis.domain.CompanyCustomer;

/**
 * ClassName: CompanyCustomerService
 * Author: tandingbo
 * CreateTime: 2017-04-16 15:00
 */
public interface CompanyCustomerService {
    /**
     * 根据主键ID获取企业客户信息
     * @param id
     * @return
     */
    CompanyCustomer getById(Long id);
    
    /**
     * 通过客户memberId或服务商companyId获取CompanyCustomer信息
     * @param map
     * @return
     */
    List<CompanyCustomer> getInfo(HashMap<String, Object> map);
}
