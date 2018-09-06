package com.edianniu.pscp.cs.service;

import com.edianniu.pscp.cs.domain.Company;

/**
 * ClassName: CompanyService
 * Author: tandingbo
 * CreateTime: 2017-08-25 15:24
 */
public interface CompanyService {
    Company getCompanyByMemberId(Long memberId);

	Company getCompanyById(Long companyId);
}
