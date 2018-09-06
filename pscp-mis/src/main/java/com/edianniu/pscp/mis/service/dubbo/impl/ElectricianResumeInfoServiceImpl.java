package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.electrician.CertificateType;
import com.edianniu.pscp.mis.bean.electricianresume.*;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.Certificate;
import com.edianniu.pscp.mis.domain.Electrician;
import com.edianniu.pscp.mis.domain.ElectricianResume;
import com.edianniu.pscp.mis.domain.ElectricianWorkExperience;
import com.edianniu.pscp.mis.service.*;
import com.edianniu.pscp.mis.service.dubbo.ElectricianResumeInfoService;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtil;
import com.edianniu.pscp.mis.util.MoneyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName: ElectricianResumeInfoServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:16
 */
@Service
@Repository("electricianResumeInfoService")
public class ElectricianResumeInfoServiceImpl implements ElectricianResumeInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianResumeInfoServiceImpl.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("electricianService")
    private ElectricianService electricianService;

    @Autowired
    @Qualifier("electricianResumeService")
    private ElectricianResumeService electricianResumeService;

    @Autowired
    @Qualifier("electricianWorkExperienceService")
    private ElectricianWorkExperienceService electricianWorkExperienceService;

    @Autowired
    @Qualifier("fileUploadService")
    private FileUploadService fileUploadService;
    @Autowired
    @Qualifier("certificateService")
    private CertificateService certificateService;
    @Autowired
    @Qualifier("electricianCertificateService")
    private ElectricianCertificateService electricianCertificateService;
    @Autowired
    @Qualifier("electricianCertificateImageService")
    private ElectricianCertificateImageService electricianCertificateImageService;

    /**
     * 简历详情
     *
     * @param reqData
     * @return
     */
    @Override
    public DetailResult resumeDetail(DetailReqData reqData) {
        DetailResult result = new DetailResult();

        try {
            // 电工ID不为空，获取电工ID对应的简历信息
            if (reqData.getElectricianId() != null) {
                reqData.setUid(reqData.getElectricianId());
            }

            // 用户注册信息
            UserInfo userInfo = new UserInfo();
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (userInfoResult.getResultCode() == ResultCode.SUCCESS && userInfoResult.getMemberInfo() != null) {
                userInfo = userInfoResult.getMemberInfo();
            }

            ElectricianResume resume = electricianResumeService.getEntityByUid(reqData.getUid());
            if (resume == null) {
                resume = new ElectricianResume();
                resume.setElectricianId(reqData.getUid());
                resume.setCreateUser(userInfo.getLoginName());
                electricianResumeService.saveEntity(resume);
            }

            // 基本信息
            Map<String, Object> essentialInfo = new HashMap<>();
            essentialInfo.put("id", resume.getId());

            String city = resume.getCity() == null ? "" : resume.getCity();
            essentialInfo.put("city", city);

            Integer workingYears = resume.getWorkingYears() == null ? 0 : resume.getWorkingYears();
            essentialInfo.put("workingYears", workingYears);
            essentialInfo.put("diploma", resume.getDiploma());

            essentialInfo.put("sex", userInfo.getSex());// 性别
            essentialInfo.put("contactTel", userInfo.getMobile());// 联系电话

            essentialInfo.put("userName", "");
            essentialInfo.put("birthYear", "");

            // 电工信息
            Electrician electrician = electricianService.getByUid(reqData.getUid());
            if (electrician != null) {
                // 姓名
                essentialInfo.put("userName", electrician.getUserName());
                // 出生年份
                essentialInfo.put("birthYear", Long.valueOf(electrician.getIdCardNo().substring(6, 10)));
            }

            result.setEssentialInfo(essentialInfo);

            // 工作经历
            List<Map<String, Object>> experiences = new LinkedList<>();
            List<ElectricianWorkExperience> workExperienceList = electricianWorkExperienceService.getAllListByResumeId(resume.getId());
            if (CollectionUtils.isNotEmpty(workExperienceList)) {
                for (ElectricianWorkExperience workExperience : workExperienceList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", workExperience.getId());
                    map.put("companyName", workExperience.getCompanyName());
                    map.put("workContent", workExperience.getWorkContent());
                    map.put("startTime", DateUtil.getFormatDate(workExperience.getStartTime(), DateUtil.YYYY_MM_FORMAT));
                    map.put("endTime", DateUtil.getFormatDate(workExperience.getEndTime(), DateUtil.YYYY_MM_FORMAT));
                    experiences.add(map);
                }
            }
            result.setExperiences(experiences);

            // 期望费用
            result.setExpectedFee(MoneyUtils.format(resume.getExpectedFee()));
            // 个人简介
            result.setSynopsis(resume.getSynopsis());

            if (electrician != null) {
                // 证书信息
                List<Map<String, Object>> certificateList = electricianCertificateService.queryListMap(electrician.getId());
                // 电工资质信息
                result.setQualificationsList(structureQualifications(certificateList));

                // 证书图片信息
                List<Map<String, Object>> certificateImageList = electricianCertificateImageService.queryListMap(electrician.getId());
                if (CollectionUtils.isNotEmpty(certificateImageList)) {
                    for (Map<String, Object> map : certificateImageList) {
                        if (map.get("fileid") != null) {
                            map.put("fileid", map.get("fileid"));
                        }
                    }
                }
                result.setCertificateImageList(certificateImageList);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
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
        List<Certificate> certificateList = certificateService.selectAll();
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

    /**
     * 修改简历信息
     *
     * @param reqData
     * @return
     */
    @Override
    public UpdateResult updateResume(UpdateReqData reqData) {
        UpdateResult result = new UpdateResult();
        try {
            if (reqData.getResumeId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("resumeId不能为空");
                return result;
            }
            if (reqData.getDiploma() == null) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("最高学历不能为空");
                return result;
            }
            if (reqData.getWorkingYears() == null) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("workingYears不能为空");
                return result;
            }
            if (reqData.getCity() == null) {
                result.setResultCode(ResultCode.ERROR_407);
                result.setResultMessage("city不能为空");
                return result;
            }
            if (StringUtils.isNotBlank(reqData.getSynopsis())) {
				if (! BizUtils.checkLength(reqData.getSynopsis(), 1000)) {
					result.setResultCode(ResultCode.ERROR_407);
	                result.setResultMessage("个人简介不能超过1000字");
	                return result;
				}
			}

            ElectricianResume resume = electricianResumeService.getEntityById(reqData.getResumeId());
            if (resume == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("简历不存在");
                return result;
            }
            if (!reqData.getUid().equals(resume.getElectricianId())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("简历不存在");
                return result;
            }

            resume.setCity(reqData.getCity());
            resume.setDiploma(reqData.getDiploma());
            resume.setWorkingYears(reqData.getWorkingYears());
            resume.setSynopsis(reqData.getSynopsis());
            electricianResumeService.updateEntity(resume);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 设置期望费用
     *
     * @param reqData
     * @return
     */
    @Override
    public SetExpectedFeeResult setExpectedFee(SetExpectedFeeReqData reqData) {
        SetExpectedFeeResult result = new SetExpectedFeeResult();
        try {
            if (reqData.getResumeId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("resumeId不能为空");
                return result;
            }

            if (StringUtils.isBlank(reqData.getExpectedFee())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("expectedFee格式不合法");
                return result;
            }

            ElectricianResume resume = electricianResumeService.getEntityById(reqData.getResumeId());
            if (resume == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("简历不存在");
                return result;
            }

            if (!reqData.getUid().equals(resume.getElectricianId())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("简历不存在");
                return result;
            }

            try {
                Double expectedFee = MoneyUtils.formatToMoney(reqData.getExpectedFee());
                if (expectedFee > 999999.99D) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("期望值太大，请输入整数位为6位的数字！");
                    return result;
                }
                resume.setExpectedFee(expectedFee);
            } catch (Exception e) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("expectedFee格式不合法");
                return result;
            }

            // 只更新期望费用
            ElectricianResume entity = new ElectricianResume();
            entity.setId(resume.getId());
            entity.setExpectedFee(resume.getExpectedFee());
            electricianResumeService.updateEntity(entity);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }
}
