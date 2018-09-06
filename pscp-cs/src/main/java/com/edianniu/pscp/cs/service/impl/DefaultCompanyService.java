package com.edianniu.pscp.cs.service.impl;

import com.edianniu.pscp.cs.dao.CompanyDao;
import com.edianniu.pscp.cs.domain.Company;
import com.edianniu.pscp.cs.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: DefaultCompanyService
 * Author: tandingbo
 * CreateTime: 2017-08-25 15:24
 */
@Service
@Repository("companyService")
public class DefaultCompanyService implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public Company getCompanyByMemberId(Long memberId) {
        return companyDao.getCompanyByMemberId(memberId);
    }
    
    @Override
    public Company getCompanyById(Long companyId){
    	return companyDao.getCompanyById(companyId);
    }


}
