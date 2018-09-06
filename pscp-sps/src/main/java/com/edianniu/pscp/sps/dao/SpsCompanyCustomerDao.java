package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.CompanyCustomer;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 公司客户dao
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-08 17:08:44
 */
public interface SpsCompanyCustomerDao extends BaseDao<CompanyCustomer> {
    public List<CompanyCustomer> getListByCompanyId(@Param("companyId") Long companyId, @Param("statusList") List<Integer> statusList);

    public void deletes(@Param("ids") Long[] ids, @Param("modifiedUser") String loginName);

    CompanyCustomer getCompanyCustomerByMap(Map<String, Object> queryMap);

    CompanyCustomer getByCustomerId(Long customerId);

	List<CompanyCustomer> getAllCompanyCustomerList();
}
