package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.CompanyRenter;

/**
 * CompanyRenterDao
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2018年03月30日 上午10:49:00
 */
public interface CompanyRenterDao extends BaseDao<CompanyRenter> {

    CompanyRenter getById(Long id);

    Long getCompanyIdByUid(Long uid);

    CompanyRenter getCompanyRenterByUid(Long uid);
}
