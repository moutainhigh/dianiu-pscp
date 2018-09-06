package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.CompanyCustomerDao;
import com.edianniu.pscp.mis.domain.CompanyCustomer;
import com.edianniu.pscp.mis.service.CompanyCustomerService;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: DefaultCompanyCustomerService
 * Author: tandingbo
 * CreateTime: 2017-04-16 15:01
 */
@Service
@Repository("companyCustomerService")
public class DefaultCompanyCustomerService implements CompanyCustomerService {

    @Autowired
    private CompanyCustomerDao companyCustomerDao;

    /**
     * 根据主键ID获取企业客户信息
     * @param id
     * @return
     */
    @Override
    public CompanyCustomer getById(Long id) {
        return companyCustomerDao.getById(id);
    }

	@Override
	public List<CompanyCustomer> getInfo(HashMap<String, Object> map) {
		return companyCustomerDao.queryList(map);
	}
}
