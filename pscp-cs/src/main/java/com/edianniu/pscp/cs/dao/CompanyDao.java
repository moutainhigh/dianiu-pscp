package com.edianniu.pscp.cs.dao;


import java.util.HashMap;
import java.util.List;

import com.edianniu.pscp.cs.domain.Company;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 16:00:18
 */
public interface CompanyDao extends BaseDao<Company> {

    Company getCompanyById(Long id);

    Company getCompanyByMemberId(Long memberId);
    
    List<Company> getCompanyList(HashMap<String, Object> queryCompanyMap);
    
    int getCompanyCount(HashMap<String, Object> queryCompanyMap);

	int getCompanyWithLineAndMeterCount(HashMap<String, Object> companyMap);

	List<Company> getCompanyWithLineAndMeterList(HashMap<String, Object> companyMap);
}
