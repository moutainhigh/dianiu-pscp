package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.certificate.CertificateType;
import com.edianniu.pscp.sps.bean.member.*;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.DiplomaType;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.VO.ResumeVO;
import com.edianniu.pscp.sps.commons.Constants;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.*;
import com.edianniu.pscp.sps.service.*;
import com.edianniu.pscp.sps.service.dubbo.MemberInformationService;
import com.edianniu.pscp.sps.util.DateUtils;
import com.edianniu.pscp.sps.util.MoneyUtils;
import com.edianniu.pscp.sps.util.SensitiveInfoUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName: MemberInformationServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-08-21 15:54
 */
@Service
@Repository("memberInformationService")
public class MemberInformationServiceImpl implements MemberInformationService {
    private static final Logger logger = LoggerFactory.getLogger(MemberInformationServiceImpl.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("spsCompanyService")
    private SpsCompanyService spsCompanyService;
    @Autowired
    @Qualifier("spsCertificateService")
    private SpsCertificateService spsCertificateService;
    @Autowired
    @Qualifier("spsElectricianService")
    private SpsElectricianService spsElectricianService;
    @Autowired
    @Qualifier("electricianResumeService")
    private ElectricianResumeService electricianResumeService;
    @Autowired
    @Qualifier("electricianWorkExperienceService")
    private ElectricianWorkExperienceService electricianWorkExperienceService;
    @Autowired
    @Qualifier("spsElectricianCertificateService")
    private SpsElectricianCertificateService spsElectricianCertificateService;
    @Autowired
    @Qualifier("spsElectricianCertificateImageService")
    private SpsElectricianCertificateImageService spsElectricianCertificateImageService;

    /**
     * 电工(企业、社会)会员登录信息
     *
     * @param reqData
     * @return
     */
    @Override
    public MemberElectricianResult getElectricianMemberInfo(MemberElectricianReqData reqData) {
        MemberElectricianResult result = new MemberElectricianResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.set(userInfoResult.getResultCode(), userInfoResult.getResultMessage());
                return result;
            }

            UserInfo userInfo = userInfoResult.getMemberInfo();

            result.setUserName(userInfo.getLoginName());
            result.setUserId("" + userInfo.getUid());

            Electrician electrician = spsElectricianService.selectElectricianVOByUid(userInfo.getUid());
            if (electrician != null) {
                String idCardNo = StringUtils.isBlank(electrician.getIdCardNo()) ? "" : SensitiveInfoUtils.idCardNum(electrician.getIdCardNo());
                result.setIdNumber(idCardNo);

                result.setElectricianName(electrician.getUserName());
                result.setCertifiedStatus("" + electrician.getStatus());

                List<String> idCardImages = new LinkedList<>();
                if (StringUtils.isNotBlank(electrician.getIdCardFrontImg())) {
                    idCardImages.add(String.format("%s%s", picUrl, electrician.getIdCardFrontImg()));
                }
                if (StringUtils.isNotBlank(electrician.getIdCardBackImg())) {
                    idCardImages.add(String.format("%s%s", picUrl, electrician.getIdCardBackImg()));
                }
                result.setIdImagesList(idCardImages);

                // 证书图片
                List<String> certificateList = new LinkedList<>();
                List<Map<String, Object>> mapList = spsElectricianCertificateImageService.queryListMap(electrician.getId());
                if (CollectionUtils.isNotEmpty(mapList)) {
                    for (Map<String, Object> map : mapList) {
                        if (map.get("fileid") != null) {
                            String certificateImg = String.format("%s%s", picUrl, map.get("fileid"));
                            certificateList.add(certificateImg);
                        }
                    }
                }
                result.setCertificateList(certificateList);

                // 证书信息
                List<Map<String, Object>> certificateMapList = spsElectricianCertificateService.queryListMap(electrician.getId());
                // 电工资质信息
                result.setQualificationList(structureQualifications(certificateMapList));

                // 简历信息
                ResumeVO resumeVO = new ResumeVO();
                // 工作经历
                List<Map<String, Object>> experiences = new LinkedList<>();
                ElectricianResume resume = electricianResumeService.queryEntityByElectricianId(userInfo.getUid());
                if (resume != null) {
                    resumeVO.setCity(resume.getCity());
                    resumeVO.setSynopsis(resume.getSynopsis());
                    resumeVO.setWorkingYears(resume.getWorkingYears());
                    resumeVO.setDiploma(DiplomaType.getName(resume.getDiploma()));
                    resumeVO.setExpectedFee(MoneyUtils.format(resume.getExpectedFee()));

                    // 工作经历
                    List<ElectricianWorkExperience> experienceList = electricianWorkExperienceService.getAllListByResumeId(resume.getId());
                    if (CollectionUtils.isNotEmpty(experienceList)) {
                        for (ElectricianWorkExperience workExperience : experienceList) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", workExperience.getId());
                            map.put("companyName", workExperience.getCompanyName());
                            map.put("workContent", workExperience.getWorkContent());
                            map.put("startTime", DateUtils.format(workExperience.getStartTime(), DateUtils.DATE_PATTERN));
                            map.put("endTime", DateUtils.format(workExperience.getEndTime(), DateUtils.DATE_PATTERN));
                            experiences.add(map);
                        }
                    }
                }
                result.setResume(resumeVO);
                result.setExperiences(experiences);// 工作经历
            }

            Long companyId = userInfo.getCompanyId();
            if (companyId == null || companyId.equals(0L)) {
                result.setRole("社会电工");
            } else {
                result.setRole("企业电工");
                Company company = spsCompanyService.getCompanyById(companyId);
                if (company != null) {
                    result.setCompanyName(company.getName());
                    result.setCompanyAddr(company.getAddress());
                }
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getElectricianMemberInfo :{}", e);
        }
        return result;
    }

    /**
     * 客户会员登录信息
     *
     * @param reqData
     * @return
     */
    @Override
    public MemberCustomerResult getCustomerMemberInfo(MemberCustomerReqData reqData) {
        MemberCustomerResult result = new MemberCustomerResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.set(userInfoResult.getResultCode(), userInfoResult.getResultMessage());
                return result;
            }

            UserInfo userInfo = userInfoResult.getMemberInfo();

            result.setUserName(userInfo.getLoginName());
            result.setUserId("" + userInfo.getUid());
            result.setRole("客户");

            Company company = spsCompanyService.getCompanyByMemberId(userInfo.getUid());
            if (company != null) {
                result.setCompanyName(company.getName());
                result.setCompanyAddr(company.getAddress());
                result.setContacts(company.getContacts());
                result.setContactNumber(company.getMobile());
                result.setCompanyPhone(company.getPhone());
                result.setCertifiedStatus("" + company.getStatus());
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getCustomerMemberInfo :{}", e);
        }
        return result;
    }

    /**
     * 服务商会员登录信息
     *
     * @param reqData
     * @return
     */
    @Override
    public MemberFacilitatorResult getFacilitatorMemberInfo(MemberFacilitatorReqData reqData) {
        MemberFacilitatorResult result = new MemberFacilitatorResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.set(userInfoResult.getResultCode(), userInfoResult.getResultMessage());
                return result;
            }

            UserInfo userInfo = userInfoResult.getMemberInfo();

            result.setUserName(userInfo.getLoginName());
            result.setUserId("" + userInfo.getUid());
            if (userInfo.isNormalMember()) {
                result.setRole("普通用户");
            } else {
                result.setRole("服务商");
            }

            Company company = null;
            if (userInfo.getIsAdmin().equals(Constants.TAG_YES)) {
                company = spsCompanyService.getCompanyByMemberId(userInfo.getUid());
            } else if (userInfo.getCompanyId() != null) {
                company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
            }

            if (company != null) {
                result.setCompanyName(company.getName());
                result.setCompanyAddr(company.getAddress());
                result.setContacts(company.getContacts());
                result.setContactNumber(company.getMobile());
                result.setCompanyPhone(company.getPhone());
                result.setEmail(company.getEmail());
                result.setCertifiedStatus("" + company.getStatus());
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getFacilitatorMemberInfo :{}", e);
        }
        return result;
    }

    /**
     * 构建电工资质信息
     *
     * @param electricianCertificateList
     * @return
     */
    private List<Map<String, Object>> structureQualifications(List<Map<String, Object>> electricianCertificateList) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Set<Long> certificateIdSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(electricianCertificateList)) {
            for (Map<String, Object> map : electricianCertificateList) {
                if (map.get("certificateId") != null) {
                    certificateIdSet.add(Long.valueOf(map.get("certificateId").toString()));
                }
            }
        }

        Map<Integer, List<Map<String, Object>>> mapCertificate = new HashMap<>();
        List<Certificate> certificateList = spsCertificateService.selectAll();
        if (CollectionUtils.isNotEmpty(certificateList)) {
            for (Certificate certificate : certificateList) {
                Integer type = certificate.getType();

                Map<String, Object> map = new HashMap<>();
                map.put("name", certificate.getName());
                map.put("checked", false);
                if (certificateIdSet.contains(certificate.getId())) {
                    map.put("checked", true);
                }

                List<Map<String, Object>> listMap = new ArrayList<>();
                if (mapCertificate.containsKey(type)) {
                    listMap = mapCertificate.get(type);
                }
                listMap.add(map);
                mapCertificate.put(type, listMap);
            }
        }

        if (MapUtils.isNotEmpty(mapCertificate)) {
            for (Map.Entry<Integer, List<Map<String, Object>>> entry : mapCertificate.entrySet()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", CertificateType.getNameByKey(entry.getKey()));
                map.put("certificateList", entry.getValue());
                mapList.add(map);
            }
        }
        return mapList;
    }

    private String picUrl;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
