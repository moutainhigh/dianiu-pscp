
package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.bean.company.*;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.TrueStatus;
import com.edianniu.pscp.mis.dao.*;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.service.AreaService;
import com.edianniu.pscp.mis.service.CompanyService;
import com.edianniu.pscp.mis.service.ElectricianService;
import com.edianniu.pscp.mis.util.IDCardUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ClassName: DefaultCompanyService
 * Author: tandingbo
 * CreateTime: 2017-04-16 15:05
 */
@Service
@Repository("companyService")
public class DefaultCompanyService implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberRoleDao memberRoleDao;
    @Autowired
    private CompanyCertificateImageDao companyCertificateImageDao;
    @Autowired
    private AreaService areaService;
    @Autowired
    private CompanyElectricianDao companyElectricianDao;
    @Autowired
    private ElectricianService electricianService;

    /**
     * 根据主键ID获取企业信息
     *
     * @param id
     * @return
     */
    @Override
    public Company getById(Long id) {
        return companyDao.getById(id);
    }

    /**
     * 根据会员ID获取企业信息
     *
     * @param memberId
     * @return
     */
    @Override
    public Company getByMemberId(Long memberId) {
        return companyDao.getByMemberId(memberId);
    }

    private void saveCertificateImages(Long companyId, List<CertificateImgInfo> certificateImages) {
        //现获取所有认证图片
        if (certificateImages != null && !certificateImages.isEmpty()) {
            List<CompanyCertificateImage> imgs = companyCertificateImageDao.getCertificateImagesByCompanyId(companyId);
            Map<String, Long> map = new HashMap<String, Long>();
            for (CompanyCertificateImage img : imgs) {
                map.put(img.getFileId(), img.getId());
            }
            if (certificateImages != null && certificateImages.size() > 0) {
                for (int i = 0; i < certificateImages.size(); i++) {
                    CertificateImgInfo image = certificateImages.get(i);
                    CompanyCertificateImage companyCertificateImage = companyCertificateImageDao.getCertificateImageByFileId(image.getFileId());
                    //没有记录就新增
                    if (companyCertificateImage == null) {
                        companyCertificateImage = new CompanyCertificateImage();
                        companyCertificateImage.setFileId(image.getFileId());
                        companyCertificateImage.setCompanyId(companyId);
                        companyCertificateImage.setOrderNum(image.getOrderNum());
                        companyCertificateImage.setStatus(0);
                        companyCertificateImageDao.save(companyCertificateImage);
                    } else {
                        //有记录就移除
                        map.remove(companyCertificateImage.getFileId());
                    }
                }
                //余下全是被删除的认证图片
                for (Entry<String, Long> set : map.entrySet()) {
                    Long id = set.getValue();
                    companyCertificateImageDao.delete(id);
                }
            }
        }

    }

    @Override
    public boolean isExistByName(String name, int[] statuss) {
        int count = companyDao.getCountByName(name, statuss);
        return count > 0 ? true : false;
    }

    @Transactional
    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Transactional
    @Override
    public void save(Company company) {
        companyDao.save(company);
    }

    @Transactional
    @Override
    public void update(Company company,
                       List<CertificateImgInfo> certificateImages) {
        update(company);
        saveCertificateImages(company.getId(), certificateImages);

    }

    @Transactional
    @Override
    public void save(Company company, List<CertificateImgInfo> certificateImages) {
        save(company);
        saveCertificateImages(company.getId(), certificateImages);

    }

    @Transactional
    @Override
    public void auditFail(Company company) {
        update(company);
    }

    @Override
    @Transactional
    public void auditSuccess(Company company) {
        update(company);
        // 解析身份证信息
        IDCardUtils.IDCardInfo idCardInfo = null;
        if (StringUtils.isNotBlank(company.getIdCardNo())) {
            idCardInfo = IDCardUtils.getIDCardInfo(company.getIdCardNo());
        }
        if (company.getMemberType() == CompanyMemberType.FACILITATOR.getValue()) {//服务商
            Member member = new Member();
            member.setUid(company.getMemberId());
            member.setRealName(company.getName());
            member.setIsAdmin(TrueStatus.YES.getValue());
            member.setCompanyId(company.getId());
            member.setIsFacilitator(TrueStatus.YES.getValue());
            member.setIsElectrician(TrueStatus.YES.getValue());
            if (idCardInfo != null) {
                member.setSex(idCardInfo.getSex());
                member.setAge(idCardInfo.getAge());
            }
            memberDao.updateUser(member);
            //删除电工角色
            memberRoleDao.deleteByUidAndRoleId(company.getMemberId(), Constants.ELECTRICIAN_ROLE_ID);
            memberRoleDao.deleteByUidAndRoleId(company.getMemberId(), Constants.CUSTOMER_ROLE_ID);
            //企业 服务商/客户 处理对应的角色信息 TODO
            //服务商管理员帐号自动成为企业电工
            CompanyElectrician companyElectrician = new CompanyElectrician();
            companyElectrician.setCompanyId(company.getId());
            Date date = new Date();
            Member member1 = memberDao.getByUid(company.getMemberId());
            companyElectrician.setInvitationAgreeTime(date);
            companyElectrician.setInvitationTime(date);
            companyElectrician.setMemberId(company.getMemberId());
            companyElectrician.setMobile(member1.getMobile());
            companyElectrician.setName(company.getContacts());
            companyElectrician.setStatus(CompanyElectricianStatus.BOUND.getValue());
            companyElectricianDao.save(companyElectrician);
            Electrician electrician = new Electrician();
            electrician.setAuditTime(date);
            electrician.setAuditUser("系统");
            electrician.setCompanyId(company.getId());
            electrician.setIdCardBackImg(company.getIdCardBackImg());
            electrician.setIdCardFrontImg(company.getIdCardFrontImg());
            electrician.setIdCardNo(company.getIdCardNo());
            electrician.setMemberId(member1.getUid());
            electrician.setStatus(ElectricianAuthStatus.SUCCESS.getValue());
            electrician.setUserName(company.getContacts());
            electricianService.save(electrician);
        } else if (company.getMemberType() == CompanyMemberType.CUSTOMER.getValue()) {
            Member member = new Member();
            member.setUid(company.getMemberId());
            member.setRealName(company.getName());
            member.setIsAdmin(TrueStatus.YES.getValue());
            member.setCompanyId(company.getId());
            member.setIsCustomer(TrueStatus.YES.getValue());
            if (idCardInfo != null) {
                member.setSex(idCardInfo.getSex());
                member.setAge(idCardInfo.getAge());
            }
            memberDao.updateUser(member);
            //删除电工角色
            memberRoleDao.deleteByUidAndRoleId(company.getMemberId(), Constants.ELECTRICIAN_ROLE_ID);
            memberRoleDao.deleteByUidAndRoleId(company.getMemberId(), Constants.FACILITATOR_ROLE_ID);
        }

    }

    @Override
    public boolean isCompanyMember(Long memberId) {
        Company company = this.getByMemberId(memberId);
        if (company == null) {
            return false;
        }
        if (company.getStatus() == CompanyAuthStatus.AUTHING.getValue() ||
                company.getStatus() == CompanyAuthStatus.SUCCESS.getValue()) {
            return true;
        }
        return false;
    }

    @Override
    public CompanyInfo getInfoById(Long id) {
        return getCompanyInfo(getById(id));
    }


    @Override
    public CompanyInfo getInfoByMemberId(Long memberId) {
        return getCompanyInfo(getByMemberId(memberId));
    }

    /**
     * 获取companyInfo
     *
     * @param company
     * @return
     */
    private CompanyInfo getCompanyInfo(Company company) {
        if (company == null) {
            return new CompanyInfo();
        }
        CompanyInfo companyInfo = new CompanyInfo();
        BeanUtils.copyProperties(company, companyInfo);
        companyInfo.setAuthStatus(company.getStatus());
        AddressInfo addressInfo = new AddressInfo();
        ProvinceInfo provinceInfo = areaService.getProvinceInfo(company.getProvinceId());
        if (provinceInfo != null) {
            addressInfo.setProvinceId(provinceInfo.getId());
            addressInfo.setProvinceName(provinceInfo.getName());
        }
        CityInfo cityInfo = areaService.getCityInfo(company.getProvinceId(), company.getCityId());
        if (cityInfo != null) {
            addressInfo.setCityId(cityInfo.getId());
            addressInfo.setCityName(cityInfo.getName());
        }
        AreaInfo areaInfo = areaService.getAreaInfo(company.getCityId(), company.getAreaId());
        if (areaInfo != null) {
            addressInfo.setAreaId(areaInfo.getId());
            addressInfo.setAreaName(areaInfo.getName());
        }
        addressInfo.setAddress(company.getAddress());
        companyInfo.setAddressInfo(addressInfo);
        List<CertificateImgInfo> certificateImages = Lists.newArrayList();
        List<CompanyCertificateImage> images = companyCertificateImageDao.getCertificateImagesByCompanyId(company.getId());
        if (images != null && !images.isEmpty()) {
            for (CompanyCertificateImage image : images) {
                CertificateImgInfo certificateImgInfo = new CertificateImgInfo();
                BeanUtils.copyProperties(image, certificateImgInfo);
                certificateImages.add(certificateImgInfo);
            }
        }
        companyInfo.setCertificateImages(certificateImages);
        return companyInfo;
    }

    @Override
    public CompanyInfo getInfoByUserInfo(UserInfo userInfo) {
        if (userInfo.isCompanyUser()) {
            return this.getInfoById(userInfo.getCompanyId());
        }
        return this.getInfoByMemberId(userInfo.getUid());
    }
}

