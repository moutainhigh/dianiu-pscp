package com.edianniu.pscp.cs.dao;


import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.bean.company.vo.BindingFacilitatorVO;
import com.edianniu.pscp.cs.bean.query.BindingFacilitatorQuery;
import com.edianniu.pscp.cs.domain.CompanyCustomer;

/**
 * 公司客户dao
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-08 17:08:44
 */
public interface CompanyCustomerDao extends BaseDao<CompanyCustomer> {
    
	CompanyCustomer getByCustomerId(Long customerId);
	
	void insert(CompanyCustomer companyCustomer);

	CompanyCustomer getCompanyCustomer(HashMap<String, Object> queryMap);

	void updateCompanyCustomer(HashMap<String, Object> changeMap);
	
	List<CompanyCustomer> getList(HashMap<String, Object> map);

	int queryCountBindingFacilitatorVO(BindingFacilitatorQuery listQuery);

	List<BindingFacilitatorVO> queryListBindingFacilitatorVO(BindingFacilitatorQuery listQuery);
}
