/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:17:03
 * @version V1.0
 */
package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.electrician.AuditReqData;
import com.edianniu.pscp.mis.bean.electrician.AuditResult;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;
import com.edianniu.pscp.mis.bean.electrician.ElectricianCertificateInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.user.ElectricianAuthReq;
import com.edianniu.pscp.mis.bean.user.ElectricianAuthResult;
import com.edianniu.pscp.mis.bean.user.GetElectricianReq;
import com.edianniu.pscp.mis.bean.user.GetElectricianResult;
import com.edianniu.pscp.mis.bean.user.QuerySysUserReq;
import com.edianniu.pscp.mis.bean.user.SysUserInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.commons.TrueStatus;
import com.edianniu.pscp.mis.domain.Electrician;
import com.edianniu.pscp.mis.domain.ElectricianCertificate;
import com.edianniu.pscp.mis.service.CompanyService;
import com.edianniu.pscp.mis.service.ElectricianService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.service.dubbo.ElectricianInfoService;
import com.edianniu.pscp.mis.service.dubbo.SysUserInfoService;
import com.edianniu.pscp.mis.util.IDCardUtils;
import com.edianniu.pscp.mis.util.PropertiesUtil;
import com.edianniu.pscp.mis.util.IDCardUtils.IDCardInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 电工信息服务
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:17:03
 */
@Service
@Repository("electricianInfoService")
public class ElectricianInfoServiceImpl implements ElectricianInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(ElectricianInfoServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ElectricianService electricianService;
    @Autowired
    private MessageInfoService messageInfoService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Override
    public GetElectricianResult get(GetElectricianReq req) {
        GetElectricianResult result = new GetElectricianResult();
        if (req.getUid() == null) {
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("uid不能为空");
            return result;
        }
        UserInfo userInfo = userService.getUserInfo(req.getUid());
        if (userInfo == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("用户信息不存在");
            return result;
        }
        ElectricianInfo electrician = electricianService.getInfoByUid(req.getUid());
        if (electrician == null) {
            result.setResultCode(ResultCode.ERROR_403);
            result.setResultMessage("电工信息不存在");
            return result;
        }
        result.setInfo(electrician);
        return result;
    }

    @Override
    public ElectricianAuthResult auth(ElectricianAuthReq req) {
        ElectricianAuthResult result = new ElectricianAuthResult();
        try {
            if (req.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(req.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            if (!userInfo.isNormalMember()) {//普通账号可以申请
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("该账号使用中");
                return result;
            }
            if (StringUtils.isBlank(req.getInfo().getIdCardNo())) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("身份证号码不能为空");
                return result;
            }
            if (!IDCardUtils.isValidatedAllIdcard(req.getInfo().getIdCardNo())) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("身份证号码错误");
                return result;
            }
            if (StringUtils.isBlank(req.getInfo().getUserName())) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("名字不能为空");
                return result;
            }
            if (StringUtils.isBlank(req.getInfo().getIdCardFrontImg())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("身份证正面照不能为空");
                return result;
            }
            if (StringUtils.isBlank(req.getInfo().getIdCardBackImg())) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("身份证反面照不能为空");
                return result;
            }
            List<CertificateImgInfo> imgs = req.getInfo().getCertificateImgs();
            if (imgs == null || imgs.isEmpty()) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("认证证件照不能为空");
                return result;
            }
            if (companyService.isCompanyMember(req.getUid())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("该账号使用中");
                return result;
            }
            Electrician electrician = electricianService.getByUid(req.getUid());
            if (electrician == null) {
                electrician = new Electrician();
                BeanUtils.copyProperties(req.getInfo(), electrician);
                IDCardInfo idCardInfo = IDCardUtils.getIDCardInfo(electrician.getIdCardNo());
                userInfo.setAge(idCardInfo.getAge());
                userInfo.setSex(idCardInfo.getSex());
                electrician.setMemberId(req.getUid());
                electrician.setCompanyId(0L);
                electrician.setStatus(ElectricianAuthStatus.AUTHING.getValue());
                electricianService.save(electrician, imgs);
                //提交认证成功 to 本人
                Map<String, String> params = new HashMap<>();
                params.put("mobile", userInfo.getMobile().substring(7));
                messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), MessageId.ELECTRICIAN_AUDIT_SUBMIT, params);
            } else {
                if (electrician.getStatus() == ElectricianAuthStatus.AUTHING.getValue()) {
                    result.setResultCode(ResultCode.ERROR_206);
                    result.setResultMessage("正在认证中，请勿重复提交认证");
                    return result;
                }
                if (electrician.getStatus() == ElectricianAuthStatus.SUCCESS.getValue()) {
                    result.setResultCode(ResultCode.ERROR_206);
                    result.setResultMessage("已认证，请勿重复提交认证");
                    return result;
                }
                req.getInfo().setCompanyId(electrician.getCompanyId());
                BeanUtils.copyProperties(req.getInfo(), electrician);
                electrician.setMemberId(req.getUid());
                electrician.setStatus(ElectricianAuthStatus.AUTHING.getValue());
                IDCardInfo idCardInfo = IDCardUtils.getIDCardInfo(electrician.getIdCardNo());
                userInfo.setAge(idCardInfo.getAge());
                userInfo.setSex(idCardInfo.getSex());
                electricianService.update(electrician, imgs);
                //提交认证成功短信和PUSH to 本人
                Map<String, String> params = new HashMap<>();
                params.put("mobile", userInfo.getMobile().substring(7));
                messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), MessageId.ELECTRICIAN_AUDIT_SUBMIT, params);
            }
            // 短信通知运维
            MessageId  messageId = MessageId.ELECTRICIAN_AUDIT;
            QuerySysUserReq re = new QuerySysUserReq();
            re.setMemberAuditNotice(Constants.TAG_YES);
            re.setStatus(Constants.TAG_YES);
			List<SysUserInfo> list = sysUserInfoService.getList(re);
			Map<String, String> params = new HashMap<>();
			params.put("electrician_name", req.getInfo().getUserName());
            for (SysUserInfo sysUserInfo : list) {
            	messageInfoService.sendSmsMessage(sysUserInfo.getUserId(), sysUserInfo.getMobile(), messageId, params);
			}
        } catch (Exception e) {
            logger.error("electricianAuth {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public AuditResult audit(AuditReqData auditReqData) {
        logger.debug("auditReqData:{}", auditReqData);
        AuditResult result = new AuditResult();
        Integer authStatus = auditReqData.getAuthStatus();
        List<ElectricianCertificateInfo> certificateInfos = auditReqData.getCertificates();
        Long electricianId = auditReqData.getElectricianId();
        String opUser = auditReqData.getOpUser();
        String remark = auditReqData.getRemark();
        try {
            if (electricianId == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("electricianId 不能为空");
                return result;
            }
            if (authStatus == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("authStatus 不能为空");
                return result;
            }
            if (!(authStatus == ElectricianAuthStatus.FAIL.getValue() ||
                    authStatus == ElectricianAuthStatus.SUCCESS.getValue())) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("authStatus 只能是:"
                        + ElectricianAuthStatus.FAIL.getValue() + " 或者 "
                        + ElectricianAuthStatus.SUCCESS.getValue());
                return result;
            }
            if (authStatus == ElectricianAuthStatus.FAIL.getValue() && StringUtils.isBlank(remark)) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("remark 不能为空");
                return result;
            }
            if (authStatus == ElectricianAuthStatus.SUCCESS.getValue()) {
                if (certificateInfos == null || certificateInfos.isEmpty()) {
                    result.setResultCode(ResultCode.ERROR_204);
                    result.setResultMessage("证书信息不能为空");
                    return result;
                } else {
                    for (ElectricianCertificateInfo electricianCertificateInfo : certificateInfos) {
                        if (!electricianService.existCertificate(electricianCertificateInfo.getCertificateId())) {
                            result.setResultCode(ResultCode.ERROR_204);
                            result.setResultMessage("证书[id=" + electricianCertificateInfo.getCertificateId() + "] 不存在");
                            return result;
                        }
                    }

                }
            }
            if (StringUtils.isBlank(opUser)) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("opUser 不能为空");
                return result;
            }
            Electrician electrician = electricianService.getById(electricianId);
            if (electrician == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("electricianId 不能存在");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(electrician.getMemberId());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("electricianId 不能存在");
                return result;
            }
            if (electrician.getStatus() == ElectricianAuthStatus.NOTAUTH.getValue()) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("未提交认证信息，无法审核");
                return result;
            }
            if (electrician.getStatus() == ElectricianAuthStatus.FAIL.getValue() ||
                    electrician.getStatus() == ElectricianAuthStatus.SUCCESS.getValue()) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("已审核，无需重复认证");
                return result;
            }

            List<ElectricianCertificate> electricianCertificates = new ArrayList<>();
            electrician.setModifiedUser(opUser);
            electrician.setStatus(authStatus);
            if (authStatus == ElectricianAuthStatus.FAIL.getValue()) {
                electrician.setRemark(remark);
                electrician.setAuditTime(new Date());
                electrician.setAuditUser(auditReqData.getOpUser());
                electricianService.auditFail(electrician);
                //短信和push
                Map<String, String> params = new HashMap<>();
                params.put("mobile", userInfo.getMobile().substring(7));
                params.put("failure_cause", remark);
                PropertiesUtil config = new PropertiesUtil("app.properties");
                params.put("contact_number", config.getProperty("contact.number"));
                messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), MessageId.ELECTRICIAN_AUDIT_FAIL, params);
            } else {
                for (ElectricianCertificateInfo electricianCertificateInfo : certificateInfos) {
                    ElectricianCertificate electricianCertificate = new ElectricianCertificate();
                    BeanUtils.copyProperties(electricianCertificateInfo, electricianCertificate);
                    electricianCertificate.setElectricianId(electricianId);
                    electricianCertificates.add(electricianCertificate);
                }
                IDCardInfo idCardInfo = IDCardUtils.getIDCardInfo(electrician.getIdCardNo());
                userInfo.setAge(idCardInfo.getAge());
                userInfo.setSex(idCardInfo.getSex());
                userInfo.setIsElectrician(TrueStatus.YES.getValue());
                electrician.setAuditTime(new Date());
                electrician.setAuditUser(auditReqData.getOpUser());
                electrician.setRemark("审核成功");
                electricianService.auditSuccess(electrician, userInfo, electricianCertificates);
                //短信和push
                Map<String, String> params = new HashMap<>();
                params.put("mobile", userInfo.getMobile().substring(7));
                messageInfoService.sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(), MessageId.ELECTRICIAN_AUDIT_SUCCESS, params);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("audit:{}", e);
        }
        return result;
    }

}
