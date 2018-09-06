package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.workexperience.DelWorkExperienceReqData;
import com.edianniu.pscp.mis.bean.workexperience.DelWorkExperienceResult;
import com.edianniu.pscp.mis.bean.workexperience.SaveOrUpdateReqData;
import com.edianniu.pscp.mis.bean.workexperience.SaveOrUpdateResult;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.ElectricianResume;
import com.edianniu.pscp.mis.domain.ElectricianWorkExperience;
import com.edianniu.pscp.mis.service.ElectricianResumeService;
import com.edianniu.pscp.mis.service.ElectricianWorkExperienceService;
import com.edianniu.pscp.mis.service.dubbo.ElectricianWorkExperienceInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * ClassName: ElectricianWorkExperienceInfoServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:38
 */
@Service
@Repository("electricianWorkExperienceInfoService")
public class ElectricianWorkExperienceInfoServiceImpl implements ElectricianWorkExperienceInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianWorkExperienceInfoServiceImpl.class);

    @Autowired
    @Qualifier("electricianResumeService")
    private ElectricianResumeService electricianResumeService;

    @Autowired
    @Qualifier("electricianWorkExperienceService")
    private ElectricianWorkExperienceService electricianWorkExperienceService;

    /**
     * 保存或修改工作经历
     *
     * @param reqData
     * @return
     */
    @Override
    public Result saveOrUpdate(SaveOrUpdateReqData reqData) {
        SaveOrUpdateResult result = new SaveOrUpdateResult();

        try {
            if (reqData.getResumeId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("resumeId不能为空");
                return result;
            }
            if (StringUtils.isBlank(reqData.getCompanyName())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("公司名称不能为空");
                return result;
            }
            if (StringUtils.isBlank(reqData.getWorkContent())) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("工作内容不能为空");
                return result;
            }
            if (!BizUtils.checkLength(reqData.getWorkContent(), 1000)) {
            	result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("工作内容不能超过1000字");
                return result;
			}
            Date startTime = DateUtil.formString(reqData.getStartTime(), DateUtil.YYYY_MM_FORMAT);
            if (startTime == null) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("startTime格式不正确");
                return result;
            }
            Date endTime = DateUtil.formString(reqData.getEndTime(), DateUtil.YYYY_MM_FORMAT);
            if (endTime == null) {
                result.setResultCode(ResultCode.ERROR_406);
                result.setResultMessage("endTime格式不正确");
                return result;
            }
            if (endTime.before(startTime)) {
                result.setResultCode(ResultCode.ERROR_407);
                result.setResultMessage("结束时间不能早于开始时间");
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

            // 公司名称重复检查
            int resultCount = electricianWorkExperienceService.selectCountByCompanyName(reqData.getExperienceId(), reqData.getResumeId(), reqData.getCompanyName());
            if (resultCount > 0) {
                result.setResultCode(ResultCode.ERROR_408);
                result.setResultMessage("公司名称已存在");
                return result;
            }

            // 根据工作经历ID区分添加修改操作
            if (reqData.getExperienceId() != null) {
                ElectricianWorkExperience workExperience = electricianWorkExperienceService.getEntityById(reqData.getExperienceId(), reqData.getResumeId());
                if (workExperience == null) {
                    result.setResultCode(ResultCode.ERROR_409);
                    result.setResultMessage("工作经历不存在");
                    return result;
                }

                ElectricianWorkExperience updateEntity = new ElectricianWorkExperience();
                updateEntity.setId(workExperience.getId());
                updateEntity.setEndTime(endTime);
                updateEntity.setStartTime(startTime);
                updateEntity.setCompanyName(reqData.getCompanyName());
                updateEntity.setWorkContent(reqData.getWorkContent());
                electricianWorkExperienceService.updateEntity(updateEntity);
            } else {
                ElectricianWorkExperience workExperience = new ElectricianWorkExperience();
                workExperience.setEndTime(endTime);
                workExperience.setStartTime(startTime);
                workExperience.setDeleted(Constants.TAG_NO);
                workExperience.setOrderNum(Constants.TAG_NO);
                workExperience.setResumeId(reqData.getResumeId());
                workExperience.setCompanyName(reqData.getCompanyName());
                workExperience.setWorkContent(reqData.getWorkContent());
                // TODO
                workExperience.setCreateUser(resume.getCreateUser());
                electricianWorkExperienceService.saveEntity(workExperience);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 删除工作经历
     *
     * @param reqData
     * @return
     */
    @Override
    public Result delWorkExperience(DelWorkExperienceReqData reqData) {
        DelWorkExperienceResult result = new DelWorkExperienceResult();

        try {
            if (reqData.getResumeId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("resumeId不能为空");
                return result;
            }
            if (reqData.getExperienceId() == null) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("experienceId不能为空");
                return result;
            }

            ElectricianResume resume = electricianResumeService.getEntityById(reqData.getResumeId());
            if (resume == null) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("工作经历不存在");
                return result;
            }
            if (!reqData.getUid().equals(resume.getElectricianId())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("工作经历不存在");
                return result;
            }

            ElectricianWorkExperience workExperience = electricianWorkExperienceService.getEntityById(reqData.getExperienceId(), reqData.getResumeId());
            if (workExperience == null) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("工作经历不存在");
                return result;
            }

            // 修改删除标识
            ElectricianWorkExperience deleteEntity = new ElectricianWorkExperience();
            deleteEntity.setDeleted(Constants.TAG_YES);
            deleteEntity.setId(workExperience.getId());
            deleteEntity.setResumeId(workExperience.getResumeId());
            electricianWorkExperienceService.updateEntity(deleteEntity);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }
}
