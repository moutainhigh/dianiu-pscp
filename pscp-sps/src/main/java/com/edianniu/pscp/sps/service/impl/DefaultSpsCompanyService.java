package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.SpsCompanyDao;
import com.edianniu.pscp.sps.domain.Company;
import com.edianniu.pscp.sps.service.SpsCompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: DefaultSpsCompanyService
 * Author: tandingbo
 * CreateTime: 2017-05-25 11:38
 */
@Service
@Repository("spsCompanyService")
public class DefaultSpsCompanyService implements SpsCompanyService {

    @Autowired
    private SpsCompanyDao spsCompanyDao;

    @Override
    public Company getCompanyById(Long id) {
        return spsCompanyDao.getCompanyById(id);
    }

	@Override
	public Company getCompanyByMemberId(Long memberId) {
		return spsCompanyDao.getCompanyByUid(memberId);
	}
}
