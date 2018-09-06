package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.Company;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 16:00:18
 */
public interface SpsCompanyDao extends BaseDao<Company> {

    public Company getCompanyByUid(Long uid);

    public Company getCompanyById(Long id);

    public Company getCompanyByName(String name);

}
