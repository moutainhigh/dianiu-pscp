package com.edianniu.pscp.cs.service;

import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.company.vo.BindingFacilitatorVO;
import com.edianniu.pscp.cs.bean.query.BindingFacilitatorQuery;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.domain.CompanyCustomer;

/**
 * ClassName: CompanyCustomerService
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:14
 */
public interface CompanyCustomerService {
   
	/**
     * 获取客户绑定过的服务商
     * @param listQuery
     * @return
     */
    PageResult<BindingFacilitatorVO> queryBindingFacilitator(BindingFacilitatorQuery listQuery);
    
    CompanyCustomer getByCustomerId(Long customerId);
    
    List<CompanyCustomer> getCompanyCustomers(HashMap<String, Object> map);
}
