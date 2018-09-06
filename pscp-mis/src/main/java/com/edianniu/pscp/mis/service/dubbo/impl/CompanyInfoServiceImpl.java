package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.company.*;
import com.edianniu.pscp.mis.bean.user.QuerySysUserReq;
import com.edianniu.pscp.mis.bean.user.SysUserInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.service.AreaService;
import com.edianniu.pscp.mis.service.CompanyService;
import com.edianniu.pscp.mis.service.ElectricianService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.service.dubbo.CompanyInfoService;
import com.edianniu.pscp.mis.service.dubbo.SysUserInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.IDCardUtils;
import com.edianniu.pscp.mis.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Repository("companyInfoService")
public class CompanyInfoServiceImpl implements CompanyInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(CompanyInfoServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ElectricianService electricianService;
    @Autowired
    private MessageInfoService messageInfoService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private SysUserInfoService sysUserInfoService;

    /**
     * 提交认证
     *
     * @param req
     * @param userInfo
     * @return
     */
    private CompanySaveOrAuthResult auth(CompanySaveOrAuthReqData req, UserInfo userInfo) {
        CompanySaveOrAuthResult result = new CompanySaveOrAuthResult();
        result = validateStepOn(req);
        if (!result.isSuccess()) {
            return result;
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getContacts())) {
            result.setResultCode(ResultCode.ERROR_215);
            result.setResultMessage("联系人不能为空");
            return result;
        }
        if (!BizUtils.checkLength(req.getCompanyInfo().getContacts(), 20)) {
            result.setResultCode(ResultCode.ERROR_216);
            result.setResultMessage("联系人名字超过20位");
            return result;
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getMobile())) {
            result.setResultCode(ResultCode.ERROR_217);
            result.setResultMessage("手机号码不能为空");
            return result;
        }
        if (!BizUtils.checkMobile(req.getCompanyInfo().getMobile())) {
            result.setResultCode(ResultCode.ERROR_218);
            result.setResultMessage("手机号码输入错误");
            return result;
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getPhone())) {
            result.setResultCode(ResultCode.ERROR_219);
            result.setResultMessage("电话号码不能为空");
            return result;
        }
        if (!BizUtils.checkPhone(req.getCompanyInfo().getPhone())) {
            result.setResultCode(ResultCode.ERROR_219);
            result.setResultMessage("电话号码输入错误");
            return result;
        }
        if (req.getMemberType() == CompanyMemberType.FACILITATOR.getValue()) {
            if (req.getCompanyInfo().getApplicationLetterFid() == null) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("企业申请公函不能为空");
                return result;
            }
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getIdCardBackImg())) {
            result.setResultCode(ResultCode.ERROR_206);
            result.setResultMessage("身份证反面不能为空");
            return result;
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getIdCardFrontImg())) {
            result.setResultCode(ResultCode.ERROR_207);
            result.setResultMessage("身份证正面不能为空");
            return result;
        }
        Company company = companyService.getByMemberId(req.getUid());
        if (company == null) {
            int statuss[] = {CompanyAuthStatus.AUTHING.getValue(),
                    CompanyAuthStatus.SUCCESS.getValue()};
            if (companyService.isExistByName(req.getCompanyInfo().getName(),
                    statuss)) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("企业名称重复");
                return result;
            }
            company = new Company();
            BeanUtils.copyProperties(req.getCompanyInfo(), company);
            company.setMemberId(req.getUid());
            company.setStatus(CompanyAuthStatus.AUTHING.getValue());
            company.setProvinceId(req.getCompanyInfo().getAddressInfo().getProvinceId());
            company.setCityId(req.getCompanyInfo().getAddressInfo().getCityId());
            company.setAreaId(req.getCompanyInfo().getAddressInfo().getAreaId());
            company.setAddress(req.getCompanyInfo().getAddressInfo().getAddress());
            company.setMemberType(req.getMemberType());
            companyService.save(company, req.getCompanyInfo().getCertificateImages());
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", userInfo.getMobile().substring(7));
            messageInfoService.sendSmsAndPushMessage(userInfo.getUid(),
                    userInfo.getMobile(), MessageId.COMPANY_AUDIT_SUBMIT, params);
        } else {
            int statuss[] = {CompanyAuthStatus.AUTHING.getValue(),
                    CompanyAuthStatus.SUCCESS.getValue()};
            if (companyService.isExistByName(req.getCompanyInfo().getName(),
                    statuss) && !company.getName().equals(req.getCompanyInfo().getName())) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("企业名称重复");
                return result;
            }
            if (company.getStatus() == CompanyAuthStatus.AUTHING.getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("企业正在认证中，请勿重复提交");
                return result;
            }
            if (company.getStatus() == CompanyAuthStatus.SUCCESS.getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("企业认证成功，请勿重复提交");
                return result;
            }
            BeanUtils.copyProperties(req.getCompanyInfo(), company, new String[]{"memberId"});
            company.setStatus(CompanyAuthStatus.AUTHING.getValue());
            company.setProvinceId(req.getCompanyInfo().getAddressInfo().getProvinceId());
            company.setCityId(req.getCompanyInfo().getAddressInfo().getCityId());
            company.setAreaId(req.getCompanyInfo().getAddressInfo().getAreaId());
            company.setAddress(req.getCompanyInfo().getAddressInfo().getAddress());
            company.setMemberType(req.getMemberType());
            companyService.update(company, req.getCompanyInfo().getCertificateImages());
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", userInfo.getMobile().substring(7));
            messageInfoService.sendSmsAndPushMessage(userInfo.getUid(),
                    userInfo.getMobile(), MessageId.COMPANY_AUDIT_SUBMIT, params);
        }
        
        // 短信通知运维
        Map<String, String> params = new HashMap<>();
        params.put("company_name", req.getCompanyInfo().getName());
        MessageId messageId = null;
        if (CompanyMemberType.FACILITATOR.getValue() == req.getMemberType()) {
        	messageId = MessageId.FACILITATOR_AUDIT;
		} else {
    		messageId = MessageId.CUSTOMER_AUDIT;
		}
        QuerySysUserReq re = new QuerySysUserReq();
        re.setMemberAuditNotice(Constants.TAG_YES);
        re.setStatus(Constants.TAG_YES);
		List<SysUserInfo> list = sysUserInfoService.getList(re);
        for (SysUserInfo sysUserInfo : list) {
			messageInfoService.sendSmsMessage(sysUserInfo.getUserId(), sysUserInfo.getMobile(), messageId, params);
		}
        return result;
    }

    /**
     * 保存/修改公司信息 1）公司信息不存在： 保存时，信息可以留空； 2）公司信息已存在： 2.1）未认证 可以修改，也可以留空。 2.2）认证中
     * 不可修改 2.3）认证通过 只能修改部分模块。 2.4）认证失败 可以修改，不可以留空。
     *
     * @param req
     * @return
     */
    private CompanySaveOrAuthResult save(CompanySaveOrAuthReqData req) {
        CompanySaveOrAuthResult result = new CompanySaveOrAuthResult();
        Company company = companyService.getByMemberId(req.getUid());
        Integer authStatus = (company == null ? CompanyAuthStatus.NOTAUTH
                .getValue() : company.getStatus());
        if (authStatus == CompanyAuthStatus.NOTAUTH.getValue()
                || authStatus == CompanyAuthStatus.FAIL.getValue()) {// 未认证||认证失败
            result = validateStepOn(req);
            if (!result.isSuccess()) {
                return result;
            }
            if (StringUtils.isNotBlank(req.getCompanyInfo().getContacts())) {
                if (!BizUtils.checkLength(req.getCompanyInfo().getContacts(),
                        20)) {
                    result.setResultCode(ResultCode.ERROR_216);
                    result.setResultMessage("联系人名字超过20位");
                    return result;
                }
            }
            if (StringUtils.isNotBlank(req.getCompanyInfo().getMobile())) {
                if (!BizUtils.checkMobile(req.getCompanyInfo().getMobile())) {
                    result.setResultCode(ResultCode.ERROR_218);
                    result.setResultMessage("手机号码输入错误");
                    return result;
                }
            }

            if (StringUtils.isNotBlank(req.getCompanyInfo().getPhone())) {
                if (!BizUtils.checkPhone(req.getCompanyInfo().getPhone())) {
                    result.setResultCode(ResultCode.ERROR_219);
                    result.setResultMessage("座机号码输入错误");
                    return result;
                }
            }
            if (company == null) {
                int statuss[] = {CompanyAuthStatus.AUTHING.getValue(),
                        CompanyAuthStatus.SUCCESS.getValue()};
                if (companyService.isExistByName(req.getCompanyInfo().getName(),
                        statuss)) {
                    result.setResultCode(ResultCode.ERROR_203);
                    result.setResultMessage("企业名称重复");
                    return result;
                }
                company = new Company();
                company.setStatus(authStatus);
                BeanUtils.copyProperties(req.getCompanyInfo(), company);
                company.setMemberId(req.getUid());
                company.setProvinceId(req.getCompanyInfo().getAddressInfo().getProvinceId());
                company.setCityId(req.getCompanyInfo().getAddressInfo().getCityId());
                company.setAreaId(req.getCompanyInfo().getAddressInfo().getAreaId());
                company.setAddress(req.getCompanyInfo().getAddressInfo().getAddress());
                company.setMemberId(req.getUid());
                company.setMemberType(req.getMemberType());
                companyService.save(company, req.getCompanyInfo().getCertificateImages());
            } else {
                int statuss[] = {CompanyAuthStatus.AUTHING.getValue(),
                        CompanyAuthStatus.SUCCESS.getValue()};
                if (companyService.isExistByName(req.getCompanyInfo().getName(),
                        statuss) && !company.getName().equals(req.getCompanyInfo().getName())) {
                    result.setResultCode(ResultCode.ERROR_203);
                    result.setResultMessage("企业名称重复");
                    return result;
                }
                BeanUtils.copyProperties(req.getCompanyInfo(), company, new String[]{"memberId"});
                company.setMemberId(req.getUid());
                company.setProvinceId(req.getCompanyInfo().getAddressInfo().getProvinceId());
                company.setCityId(req.getCompanyInfo().getAddressInfo().getCityId());
                company.setAreaId(req.getCompanyInfo().getAddressInfo().getAreaId());
                company.setAddress(req.getCompanyInfo().getAddressInfo().getAddress());
                company.setMemberType(req.getMemberType());
                companyService.update(company, req.getCompanyInfo().getCertificateImages());
            }
        } else if (authStatus == CompanyAuthStatus.AUTHING.getValue()) {// 认证中
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("企业正在认证中，不能修改");
            return result;
        } else if (authStatus == CompanyAuthStatus.SUCCESS.getValue()) {// 认证通过，只能修改
            // 官方网址，联系人，手机号码，座机号码可以修改
            if (StringUtils.isBlank(req.getCompanyInfo().getContacts())) {
                result.setResultCode(ResultCode.ERROR_215);
                result.setResultMessage("联系人不能为空");
                return result;
            }
            if (!BizUtils.checkLength(req.getCompanyInfo().getContacts(), 20)) {
                result.setResultCode(ResultCode.ERROR_216);
                result.setResultMessage("联系人名字超过20位");
                return result;
            }
            if (StringUtils.isBlank(req.getCompanyInfo().getMobile())) {
                result.setResultCode(ResultCode.ERROR_217);
                result.setResultMessage("手机号码不能为空");
                return result;
            }
            if (!BizUtils.checkMobile(req.getCompanyInfo().getMobile())) {
                result.setResultCode(ResultCode.ERROR_218);
                result.setResultMessage("手机号码输入错误");
                return result;
            }
            if (StringUtils.isBlank(req.getCompanyInfo().getPhone())) {
                result.setResultCode(ResultCode.ERROR_219);
                result.setResultMessage("座机号码不能为空");
                return result;
            }
            if (!BizUtils.checkPhone(req.getCompanyInfo().getPhone())) {
                result.setResultCode(ResultCode.ERROR_219);
                result.setResultMessage("电话号码输入错误");
                return result;
            }
            if (StringUtils.isNotBlank(req.getCompanyInfo().getWebsite())) {
                if (!BizUtils.checkLength(req.getCompanyInfo().getWebsite(), 64)) {
                    result.setResultCode(ResultCode.ERROR_204);
                    result.setResultMessage("企业网站长度不能超过64个字符");
                    return result;
                }
            }
            company.setContacts(req.getCompanyInfo().getContacts());
            company.setMobile(req.getCompanyInfo().getMobile());
            company.setPhone(req.getCompanyInfo().getPhone());
            company.setWebsite(req.getCompanyInfo().getWebsite());
            companyService.update(company);
        }
        return result;
    }

    private CompanySaveOrAuthResult validateStepOn(CompanySaveOrAuthReqData req) {
        CompanySaveOrAuthResult result = new CompanySaveOrAuthResult();
        if (CompanyMemberType.parse(req.getMemberType()) == null) {
            result.setResultCode(ResultCode.ERROR_215);
            result.setResultMessage("memberType 不正确");
            return result;
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getName())) {
            result.setResultCode(ResultCode.ERROR_203);
            result.setResultMessage("企业名称 不能为空");
            return result;
        }
        if (!BizUtils.checkLength(req.getCompanyInfo().getName(), 64)) {
            result.setResultCode(ResultCode.ERROR_203);
            result.setResultMessage("企业名称长度不能超过64字符");
            return result;
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getBusinessLicence())) {
            result.setResultCode(ResultCode.ERROR_204);
            result.setResultMessage("营业执照注册号不能为空");
            return result;
        }
        if (!BizUtils.checkLength(req.getCompanyInfo().getBusinessLicence(), 20)) {
            result.setResultCode(ResultCode.ERROR_204);
            result.setResultMessage("营业执照注册号长度不能超过20个字符");
            return result;
        }
        AddressInfo addressInfo = req.getCompanyInfo().getAddressInfo();
        if (addressInfo == null) {
            result.setResultCode(ResultCode.ERROR_211);
            result.setResultMessage("公司地址信息不能为空");
            return result;
        }
        Long provinceId = addressInfo.getProvinceId();
        Long cityId = addressInfo.getCityId();
        Long areaId = addressInfo.getAreaId();
        String address = addressInfo.getAddress();
        if (provinceId == null) {
            result.setResultCode(ResultCode.ERROR_211);
            result.setResultMessage("provinceId不能为空");
            return result;
        }
        if (areaService.getProvinceInfo(provinceId) == null) {
            result.setResultCode(ResultCode.ERROR_211);
            result.setResultMessage("provinceId不正确");
            return result;
        }
        if (cityId == null) {
            result.setResultCode(ResultCode.ERROR_211);
            result.setResultMessage("cityId不能为空");
            return result;
        }
        if (areaService.getCityInfo(provinceId, cityId) == null) {
            result.setResultCode(ResultCode.ERROR_211);
            result.setResultMessage("cityId不正确");
            return result;
        }
        if (areaId == null) {
            result.setResultCode(ResultCode.ERROR_211);
            result.setResultMessage("areaId不能为空");
            return result;
        }
        if (areaService.getAreaInfo(cityId, areaId) == null) {
            result.setResultCode(ResultCode.ERROR_211);
            result.setResultMessage("areaId不正确");
            return result;
        }
        if (StringUtils.isBlank(address)) {
            result.setResultCode(ResultCode.ERROR_212);
            result.setResultMessage("公司详细地址不能为空");
            return result;
        }
        if (!BizUtils.checkLength(address, 64)) {
            result.setResultCode(ResultCode.ERROR_212);
            result.setResultMessage("公司详细地址不能超过64个字符");
            return result;
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getBusinessLicenceImg())) {
            result.setResultCode(ResultCode.ERROR_205);
            result.setResultMessage("营业执照不能为空");
            return result;
        }
        if (StringUtils.isBlank(req.getCompanyInfo().getOrganizationCodeImg())) {
            result.setResultCode(ResultCode.ERROR_208);
            result.setResultMessage("组织机构代码证不能为空");
            return result;
        }
        if (req.getMemberType() == CompanyMemberType.FACILITATOR.getValue()) {
            if (req.getCompanyInfo().getCertificateImages() == null
                    || req.getCompanyInfo().getCertificateImages().size() == 0) {
                result.setResultCode(ResultCode.ERROR_209);
                result.setResultMessage("企业承修资质信息证件不能为空");
                return result;
            }
        }

        if (StringUtils.isNotBlank(req.getCompanyInfo().getWebsite())) {
            if (!BizUtils.checkLength(req.getCompanyInfo().getWebsite(), 64)) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("企业网站长度不能超过64个字符");
                return result;
            }
        }
        return result;
    }

    /**
     * 普通用户可以调用(除了审核中的电工)
     * <p>
     * 服务商可以调用
     */
    @Override
    public CompanySaveOrAuthResult saveOrAuth(CompanySaveOrAuthReqData req) {
        CompanySaveOrAuthResult result = new CompanySaveOrAuthResult();
        try {
            if (req.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid 不能为空");
                return result;
            }
            if (req.getMemberType() == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("memberType 不能为空");
                return result;
            }
            if (CompanyMemberType.parse(req.getMemberType()) == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("memberType 不正确");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(req.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            if (userInfo.isNormalMember()) {
                if (electricianService.isElectricianMember(userInfo.getUid())) {
                    result.setResultCode(ResultCode.ERROR_201);
                    result.setResultMessage("该账号使用中");
                    return result;
                }
            }
            if (!(userInfo.isFacilitator() || userInfo.isCustomer() || userInfo.isNormalMember())) {//普通会员或者服务商可以进入
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("该账号使用中");
                return result;
            }
            if (userInfo.isFacilitator()) {// 服务商子账号
                if (userInfo.isNotFacilitatorAdmin()) {
                    UserInfo admin = userService.getFacilitatorAdmin(userInfo
                            .getCompanyId());
                    if (admin != null) {
                        req.setUid(admin.getUid());
                    }
                }
            }
            if (StringUtils.isBlank(req.getActionType())) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("actionType 不能为空");
                return result;
            }
            if (!(req.getActionType().equals("auth") || req.getActionType()
                    .equals("save"))) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("actionType 只支持auth|save");
                return result;
            }
            if (req.getCompanyInfo() == null) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("企业信息不全");
                return result;
            }
            if (req.getActionType().equals("auth")) {
                return auth(req, userInfo);
            } else if (req.getActionType().equals("save")) {
                return save(req);
            } else {
                // TODO
            }
        } catch (Exception e) {
            logger.error("saveOrAuth {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public GetCompanyInfoResult getComapnyInfo(GetCompanyInfoReqData req) {
        GetCompanyInfoResult result = new GetCompanyInfoResult();
        Long uid = req.getUid();
        try {
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                    UserInfo admin = userService.getFacilitatorAdmin(userInfo
                            .getCompanyId());
                    if (admin != null) {
                        uid = admin.getUid();
                    }
                }
            }
            CompanyInfo companyInfo = companyService.getInfoByMemberId(uid);
            if (companyInfo != null) {
                result.setCompanyInfo(companyInfo);
            }
        } catch (Exception e) {
            logger.error("getCompanyInfo {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public AuditResult audit(AuditReqData auditReqData) {
        AuditResult result = new AuditResult();
        Integer authStatus = auditReqData.getAuthStatus();
        Long companyId = auditReqData.getCompanyId();
        String opUser = auditReqData.getOpUser();
        String remark = auditReqData.getRemark();
        String idCardNo = auditReqData.getIdCardNo();
        try {
            if (companyId == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("companyId 不能为空");
                return result;
            }
            if (authStatus == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("authStatus 不能为空");
                return result;
            }
            if (authStatus == CompanyAuthStatus.SUCCESS.getValue()) {
            	if (StringUtils.isBlank(idCardNo) ) {
                    result.setResultCode(ResultCode.ERROR_202);
                    result.setResultMessage("idCardNo 不能为空");
                    return result;
                }
                if (!IDCardUtils.isValidatedAllIdcard(idCardNo)) {
                    result.setResultCode(ResultCode.ERROR_202);
                    result.setResultMessage("idCardNo 格式不正确");
                    return result;
                }
			}
            if (!(authStatus == CompanyAuthStatus.FAIL.getValue() ||
                    authStatus == CompanyAuthStatus.SUCCESS.getValue())) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("authStatus 只能是:"
                        + CompanyAuthStatus.FAIL.getValue() + " 或者 "
                        + CompanyAuthStatus.SUCCESS.getValue());
                return result;
            }
            if (authStatus == CompanyAuthStatus.FAIL.getValue() && StringUtils.isBlank(remark)) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("remark 不能为空");
                return result;
            }
            if (StringUtils.isBlank(opUser)) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("opUser 不能为空");
                return result;
            }
            Company company = companyService.getById(companyId);
            if (company == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("companyId 不能存在");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(company.getMemberId());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("electricianId 不能存在");
                return result;
            }
            if (company.getStatus() == CompanyAuthStatus.NOTAUTH.getValue()) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("未提交认证信息，无法审核");
                return result;
            }
            if (company.getStatus() == CompanyAuthStatus.FAIL.getValue() ||
                    company.getStatus() == CompanyAuthStatus.SUCCESS.getValue()) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("已审核，无需重复审核");
                return result;
            }
            if (authStatus == CompanyAuthStatus.FAIL.getValue()) {
                company.setStatus(authStatus);
                company.setAuditTime(new Date());
                company.setAuditUser(opUser);
                company.setIdCardNo(idCardNo);
                company.setRemark(remark);
                companyService.auditFail(company);
            } else {
                company.setStatus(authStatus);
                company.setAuditTime(new Date());
                company.setAuditUser(opUser);
                company.setRemark("审核成功");
                company.setIdCardNo(idCardNo);
                companyService.auditSuccess(company);
            }

            //短信和push
            MessageId messageId = (authStatus == CompanyAuthStatus.FAIL.getValue()) ?
                    MessageId.COMPANY_AUDIT_FAIL : MessageId.COMPANY_AUDIT_SUCCESS;
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", userInfo.getMobile().substring(7));
            if (messageId.equals(MessageId.COMPANY_AUDIT_FAIL)) {
                params.put("failure_cause", remark);
                PropertiesUtil config = new PropertiesUtil("app.properties");
                params.put("contact_number", config.getProperty("contact.number"));
            }
            messageInfoService.sendSmsAndPushMessage(userInfo.getUid(),
                    userInfo.getMobile(), messageId, params);

        } catch (Exception e) {
            logger.error("company audit {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

	@Override
	public GetCompanyInfoResult getCompanyInfo(Long id) {
		GetCompanyInfoResult result = new GetCompanyInfoResult();
		try {
			if (null == id) {
				result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("id不能为空");
                return result;
			}
			CompanyInfo companyInfo = companyService.getInfoById(id);
			if (null == companyInfo) {
				result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("id不合法");
                return result;
			}
            UserInfo userInfo = userService.getUserInfo(companyInfo.getMemberId());
            if (null == userInfo) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("id不合法");
                return result;
            }
            result.setCompanyInfo(companyInfo);
		} catch (Exception e) {
			logger.error("getCompanyInfo {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
		}
		return result;
	}


}
