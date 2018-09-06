package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.company.AuditReqData;
import com.edianniu.pscp.mis.bean.company.AuditResult;
import com.edianniu.pscp.mis.bean.company.CompanySaveOrAuthReqData;
import com.edianniu.pscp.mis.bean.company.CompanySaveOrAuthResult;
import com.edianniu.pscp.mis.bean.company.GetCompanyInfoReqData;
import com.edianniu.pscp.mis.bean.company.GetCompanyInfoResult;

public interface CompanyInfoService {
	/**
     * 企业信息认证
     *
     * @param CompanySaveOrAuthReqData
     * @return
     */
    public CompanySaveOrAuthResult saveOrAuth(CompanySaveOrAuthReqData req);
    /**
     * 企业审核
     * @param auditReqData
     * @return
     */
    public AuditResult audit(AuditReqData auditReqData);
    
    /**
     * 获取企业认证信息
     * @param GetCompanyInfoReqData req
     * @return
     */
    public GetCompanyInfoResult getComapnyInfo(GetCompanyInfoReqData req);
    
    /**
     * 获取企业信息   
     * @param id
     * @return
     */
    public GetCompanyInfoResult getCompanyInfo(Long id);
    
}
