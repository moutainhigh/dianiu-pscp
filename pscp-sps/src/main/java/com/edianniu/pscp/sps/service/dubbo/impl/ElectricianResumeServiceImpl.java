package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.certificate.CertificateType;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.DiplomaType;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.ResumeReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.ResumeResult;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.VO.ResumeVO;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.Certificate;
import com.edianniu.pscp.sps.domain.Electrician;
import com.edianniu.pscp.sps.domain.ElectricianResume;
import com.edianniu.pscp.sps.domain.ElectricianWorkExperience;
import com.edianniu.pscp.sps.service.*;
import com.edianniu.pscp.sps.service.dubbo.ElectricianResumeInfoService;
import com.edianniu.pscp.sps.util.DateUtils;
import com.edianniu.pscp.sps.util.MoneyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName: ElectricianResumeServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-24 14:20
 */
@Service
@Repository("electricianResumeInfoService")
public class ElectricianResumeServiceImpl implements ElectricianResumeInfoService {
    private static final Logger logger = LoggerFactory.getLogger(SpsElectricianServiceImpl.class);

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
    @Autowired
    @Qualifier("spsElectricianService")
    private SpsElectricianService spsElectricianService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("spsCertificateService")
    private SpsCertificateService spsCertificateService;

    @Override
    public ResumeResult details(ResumeReqData resumeReqData) {
        ResumeResult result = new ResumeResult();
        try {
            if (resumeReqData.getElectricianId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工标识不能为空");
                return result;
            }

            Electrician electrician = spsElectricianService.selectElectricianVOByUid(resumeReqData.getElectricianId());
            if (electrician == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("电工信息不完整");
                return result;
            }

            // 简历信息
            ResumeVO resumeVO = new ResumeVO();
            // 工作经历
            List<Map<String, Object>> experiences = new LinkedList<>();
            ElectricianResume resume = electricianResumeService.queryEntityByElectricianId(resumeReqData.getElectricianId());
            if (resume != null) {
                resumeVO.setCity(resume.getCity());
                resumeVO.setSynopsis(resume.getSynopsis());
                resumeVO.setWorkingYears(resume.getWorkingYears());
                resumeVO.setDiploma(DiplomaType.getName(resume.getDiploma()));
                resumeVO.setExpectedFee(MoneyUtils.format(resume.getExpectedFee()));

                // 工作经历
                List<ElectricianWorkExperience> workExperienceList = electricianWorkExperienceService.getAllListByResumeId(resume.getId());
                if (CollectionUtils.isNotEmpty(workExperienceList)) {
                    for (ElectricianWorkExperience workExperience : workExperienceList) {
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
            result.setResumeInfo(resumeVO);// 简历信息
            result.setExperiences(experiences);// 工作经历

            // 证书信息
            List<Map<String, Object>> certificateList = spsElectricianCertificateService.queryListMap(electrician.getId());
            result.setCertificateList(certificateList);
            // 电工资质信息
            result.setQualificationsList(structureQualifications(certificateList));

            // 证书图片信息
            List<Map<String, Object>> certificateImageList = spsElectricianCertificateImageService.queryListMap(electrician.getId());
            if (CollectionUtils.isNotEmpty(certificateImageList)) {
                for (Map<String, Object> map : certificateImageList) {
                    if (map.get("fileid") != null) {
                        map.put("fileid", String.format("%s%s", picUrl, map.get("fileid")));
                    }
                }
            }
            result.setCertificateImageList(certificateImageList);

            // 用户信息
            Map<String, Object> electricianInfo = new HashMap<>();
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(electrician.getMemberId());
            if (getUserInfoResult.isSuccess()) {
                UserInfo userInfo = getUserInfoResult.getMemberInfo();
                if (userInfo != null) {
                    electricianInfo.put("sex", userInfo.getSex());
                    electricianInfo.put("mobile", userInfo.getMobile());
                    electricianInfo.put("userName", electrician.getUserName());
                }
            }
            result.setElectricianInfo(electricianInfo);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
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
