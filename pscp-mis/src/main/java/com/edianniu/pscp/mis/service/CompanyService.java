

package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.domain.Company;

import java.util.List;


/**
 * ClassName: CompanyService
 * Author: tandingbo
 * CreateTime: 2017-04-16 15:05
 */
public interface CompanyService {
    /**
     * 根据主键ID获取企业信息
     *
     * @param id
     * @return
     */
    Company getById(Long id);

    /**
     * 根据用户信息获取企业信息
     *
     * @param userInfo
     * @return
     */
    CompanyInfo getInfoByUserInfo(UserInfo userInfo);

    /**
     * 根据主键ID获取企业信息
     *
     * @param id
     * @return
     */
    CompanyInfo getInfoById(Long id);

    /**
     * 根据会员ID获取企业信息
     *
     * @param memberId
     * @return
     */
    Company getByMemberId(Long memberId);

    /**
     * 根据会员ID获取企业信息
     *
     * @param memberId
     * @return
     */
    CompanyInfo getInfoByMemberId(Long memberId);

    /**
     * 是否企业会员
     * 1）正在申请中
     * 2）审核成功
     *
     * @param memberId
     * @return
     */
    public boolean isCompanyMember(Long memberId);

    /**
     * 是否存在企业名称
     *
     * @param name
     * @param statuss
     * @return
     */
    public boolean isExistByName(String name, int[] statuss);

    /**
     * save
     *
     * @param company
     */
    public void update(Company company);

    /**
     * 审核失败
     *
     * @param company
     */
    public void auditFail(Company company);

    /**
     * 审核成功
     *
     * @param company
     */
    public void auditSuccess(Company company);

    /**
     * update
     *
     * @param company
     * @param certificateImages
     */
    public void update(Company company, List<CertificateImgInfo> certificateImages);

    /**
     * save
     *
     * @param company
     */
    public void save(Company company);

    /**
     * save
     *
     * @param company
     * @param certificateImages
     */
    public void save(Company company, List<CertificateImgInfo> certificateImages);
}

