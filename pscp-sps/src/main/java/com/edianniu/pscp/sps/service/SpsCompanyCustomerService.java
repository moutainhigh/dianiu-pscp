package com.edianniu.pscp.sps.service;

import java.util.List;

import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.sps.bean.customer.CustomerInfo;
import com.edianniu.pscp.sps.bean.customer.ListPageQuery;
import com.edianniu.pscp.sps.bean.customer.ResetPasswordResult;
import com.edianniu.pscp.sps.bean.customer.SaveResult;
import com.edianniu.pscp.sps.bean.customer.vo.CustomerVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.domain.CompanyCustomer;

/**
 * ClassName: SpsCompanyCustomerService
 * Author: tandingbo
 * CreateTime: 2017-05-17 09:52
 */
public interface SpsCompanyCustomerService {
    //List<CompanyCustomer> getListByCompanyId(Long companyId);
    
    //List<CompanyCustomer> getListByCompanyId(Long companyId, List<Integer> statusList);
    
    public CompanyCustomer getById(Long id);

    List<CustomerVO> selectCustomerVOByCompanyId(Long companyId, List<Integer> statusList);
    
    public void delete(Long[] ids,String loginName);

    SaveResult save(UserInfo userInfo, CompanyCustomer companyCustomer);

    SaveResult update(UserInfo userInfo, CompanyCustomer companyCustomer);

    ResetPasswordResult resetPassword(UserInfo userInfo, Long customerId);

    PageResult<CustomerInfo> search(ListPageQuery listPageQuery);

    CompanyCustomer getByCustomerId(Long customerId);

	/*PageResult<CustomerInfo> customerInfoList(HashMap<String, Object> map);*/

	CustomerInfo getCustomerInfoByCustomerId(Long customerId);

	List<CompanyCustomer> getAllCompanyCustomerList();

	

	
}
