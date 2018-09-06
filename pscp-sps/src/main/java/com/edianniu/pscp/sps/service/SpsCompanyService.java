package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.domain.Company;

/**
 * ClassName: SpsCompanyService
 * Author: tandingbo
 * CreateTime: 2017-05-25 11:38
 */
public interface SpsCompanyService {
    Company getCompanyById(Long id);
    
    Company getCompanyByMemberId(Long memberId);
}
