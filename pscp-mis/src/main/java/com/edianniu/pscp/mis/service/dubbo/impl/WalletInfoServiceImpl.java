package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.DefaultResult;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.SocialWorkOrderRefundStatus;
import com.edianniu.pscp.mis.bean.WalletDealType;
import com.edianniu.pscp.mis.bean.WalletType;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.pay.PayList;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.bean.user.LoginInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.wallet.*;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderStatus;
import com.edianniu.pscp.mis.commons.CacheKey;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.commons.TrueStatus;
import com.edianniu.pscp.mis.domain.CompanyRenter;
import com.edianniu.pscp.mis.domain.CompanyRenterConfig;
import com.edianniu.pscp.mis.domain.NeedsOrder;
import com.edianniu.pscp.mis.domain.RenterChargeOrder;
import com.edianniu.pscp.mis.domain.SocialWorkOrder;
import com.edianniu.pscp.mis.domain.UserBankCard;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.*;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtil;
import com.edianniu.pscp.mis.util.MoneyUtils;
import com.edianniu.pscp.mis.util.PropertiesUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import java.util.*;

/**
 * 钱包接口
 *
 * @author AbnerElk
 */
@Service
@Repository("walletInfoService")
public class WalletInfoServiceImpl implements WalletInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(WalletInfoServiceImpl.class);
    @Autowired
    @Qualifier("userWalletService")
    private UserWalletService userWalletService;
    @Autowired
    @Qualifier("cachedService")
    private CachedService cachedService;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("electricianService")
    private ElectricianService electricianService;
    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;
    @Autowired
    @Qualifier("companyRenterService")
    private CompanyRenterService companyRenterService;
    
    @Autowired
    @Qualifier("areaService")
    private AreaService areaService;
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private PayService payService;
    @Autowired
    private BankService bankService;
    @Autowired
    private NeedsOrderService needsOrderService;
    @Autowired
    private SocialWorkOrderService socialWorkOrderService;
    @Autowired
    private RenterChargeOrderService renterChargeOrderService;

    /**
     * 取钱包首页
     *
     * @param uid 用户ID
     * @return
     */
    @Override
    public WalletHomeResult getHome(Long uid, boolean hasWalletDetails) {
        WalletHomeResult result = new WalletHomeResult();
        try {
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            Long userId = uid;
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                	userId = userService.getFacilitatorAdminUid(userInfo
                            .getCompanyId());
                }
            }
            UserWallet userwalle = this.userWalletService.getByUid(userId);
            if (userwalle == null) {
                throw new BusinessException(String.format("用户 [%s] 钱包信息不存在!",
                        userId));
            }
            result.setAmount(MoneyUtils.format(userwalle.getAmount()));
            result.setFreezingAmount(MoneyUtils.format(userwalle
                    .getFreezingAmount()));
            if (hasWalletDetails) {
                List<UserWalletDetail> list = userWalletService
                        .getDetailsByUid(userId);
                List<WalletDetailInfo> details = new ArrayList<WalletDetailInfo>();
                if (!list.isEmpty() && list.size() > 0) {
                    for (UserWalletDetail userwalleDetail : list) {
                        details.add(this.getWalletDetailInfo(userwalleDetail));
                    }
                }
                result.setWalletDetails(details);
            }

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getHome:{}", e);
        }
        return result;
    }

    @Override
    public WalletRechargeResult recharge(Long uid, String amount) {
        WalletRechargeResult result = new WalletRechargeResult();
        try {
            UserWallet userWalle = this.userWalletService.getByUid(uid);
            if (userWalle == null) {
                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage("系统异常");
            }
            if (StringUtils.isNotBlank(amount)) {
                if (!(BizUtils.isPositiveFloat(amount) || BizUtils
                        .isPositiveNumber(amount))) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("amount 必须为大于等于0的数字");
                    return result;
                }
                result.setAmount(MoneyUtils.format(amount));
            } else {
                result.setAmount("0.00");
            }

            List<PayList> payList = payService.getPayListDelWalletPay();
            result.setPayList(payList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getHome:{}", e);
        }
        return result;
    }

    /**
     * 获取资金明细
     *
     * @param data
     * @return
     */
    @Override
    public DaybookResult getDayBook(DaybookReqData data) {
        DaybookResult result = new DaybookResult();
        Long uid = data.getUid();
        try {
            int offset = 0;
            if (data.getOffset() < 0) {
                offset = 0;
            } else {
                offset = data.getOffset();
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                    uid = userService.getFacilitatorAdminUid(userInfo
                            .getCompanyId());
                }
            }
            WalletQuery query = new WalletQuery();
            query.setOffset(offset);
            query.setUid(uid);
            query.setPageSize(data.getLimit());
            // query.setPageSize(Constants.DEFAULT_PAGE_SIZE);
            int total = this.userWalletService.queryDaybookCount(query);
            int nextOffset = 0;

            if (total > 0) {
                List<UserWalletDetail> list = this.userWalletService
                        .queryDayBook(query);
                List<WalletDetailInfo> walletDetails = new ArrayList<WalletDetailInfo>();
                for (UserWalletDetail userwalleDetail : list) {
                    WalletDetailInfo detail = this
                            .getWalletDetailInfo(userwalleDetail);
                    walletDetails.add(detail);
                }
                nextOffset = query.getOffset() + list.size();
                result.setWalletDetails(walletDetails);
            }
            // 是否有下一页
            result.setHasNext(nextOffset > 0 && nextOffset < total);
            result.setNextOffset(nextOffset);
            result.setTotalCount(total);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage(ResultCode.ERROR_500_MESSAGE);
            logger.error("getDayBook:{}", e);
        }

        return result;
    }

    private WalletDetailInfo getWalletDetailInfo(
            UserWalletDetail userwalleDetail) {
        WalletDetailInfo detail = new WalletDetailInfo();
        Long uid = userwalleDetail.getUid();
        if (uid != null) {
            UserInfo userInfo = userService.getUserInfo(userwalleDetail.getUid());
            // 如果是服务商，username显示企业名称
            if (userInfo.isFacilitator()) {
				CompanyInfo companyInfo = companyService.getInfoByUserInfo(userInfo);
				if (null != companyInfo) {
					detail.setUsername(companyInfo.getName());
				}
			}
            // 如果是社会电工，username显示社会电工的user_name
            if (userInfo.isSocialElectrician()) {
            	ElectricianInfo electricianInfo = electricianService.getInfoByUid(userInfo.getUid());
            	if (null != electricianInfo) {
            		detail.setUsername(electricianInfo.getUserName());
				}
			}
        }
        // 账单类型
        Integer type = new Integer(userwalleDetail.getType());
        detail.setType(WalletType.parse(type).getDesc());
        // 交易类型
        detail.setDealType(userwalleDetail.getDealType());
        detail.setDealTypeName(WalletDealType.parse(new Integer(userwalleDetail.getDealType())).getDesc());
        // 账单类型为充值、提现时，不区分收入、支出
        if (type.equals(WalletType.RECHARGE.getValue()) || type.equals(WalletType.WITHDRAW_CASH.getValue())) {
        	detail.setDealType(WalletDealType.INTER.getValue());
			detail.setDealTypeName(WalletDealType.INTER.getDesc());
		}
        
        // 订单号
        detail.setOrderId(userwalleDetail.getOrderId());
        String amount=MoneyUtils.format(new Double(userwalleDetail
                .getAmount()));
        detail.setAmount(amount);
        detail.setDealAccount(userwalleDetail.getDealAccount());
        detail.setRemark(userwalleDetail.getTransactionId());
        detail.setTransactionId(userwalleDetail.getTransactionId());
        detail.setId(userwalleDetail.getId());
        detail.setUid(userwalleDetail.getUid());
        detail.setCheckStatus(userwalleDetail.getCheckStatus());
        if (userwalleDetail.getCheckTime() != null) {
            detail.setCheckTime(DateUtil.getFormatDate(userwalleDetail
                    .getCheckTime()));
        }
        if (userwalleDetail.getPayStatus() != null) {
            detail.setPayStatus(userwalleDetail.getPayStatus());
        }
        detail.setPayStatus(userwalleDetail.getPayStatus());
        if (userwalleDetail.getPayTime() != null) {
            detail.setPayTime(DateUtil.getFormatDate(userwalleDetail
                    .getPayTime()));
        }
        if (userwalleDetail.getDealTime() != null) {
            detail.setDealTime(DateUtil.getFormatDate(userwalleDetail
                    .getDealTime()));
        }
        // 资金来源
        detail.setFundSource(userwalleDetail.getFundSource());
        // 资金去向
        detail.setFundTarget(userwalleDetail.getFundTarget());
        return detail;
    }

    @Override
    public WithdrawalscashAuditResult withdrawalscashAudit(
            WithdrawalscashAuditReqData withdrawalscashAuditReqData) {
        logger.debug("WithdrawalscashAuditReqData recv:{}", withdrawalscashAuditReqData);
        WithdrawalscashAuditResult result = new WithdrawalscashAuditResult();
        Long id = withdrawalscashAuditReqData.getId();
        Integer status = withdrawalscashAuditReqData.getStatus();
        String opUser = withdrawalscashAuditReqData.getOpUser();
        String memo = withdrawalscashAuditReqData.getMemo();
        try {
            if (id == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("id不能为空");
                return result;
            }
            UserWalletDetail detail = userWalletService
                    .getUserWalleDetail(id);
            if (detail == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("提现信息不存在");
                return result;
            }
            if (StringUtils.isBlank(opUser)) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("审核人不能为空");
                return result;
            }
            if (opUser.length() > 20) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("审核人长度过长，20个字符以内");
                return result;
            }
            if (status == null) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("status不能为空");
                return result;
            }
            if (!(status == WalletDetailCheckStatus.SUCCESS.getValue() || status == WalletDetailCheckStatus.FAIL.getValue())) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("status无效，只支持1(成功),-1(失败)");
                return result;
            }
            if (detail.getCheckStatus() != null
                    && (detail.getCheckStatus().intValue() == WalletDetailCheckStatus.SUCCESS
                    .getValue() || detail.getCheckStatus().intValue() == WalletDetailCheckStatus.FAIL
                    .getValue())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("已审核过，无需再次审核");
                return result;
            }
            if (status == WalletDetailCheckStatus.FAIL.getValue()) {
                if (StringUtils.isBlank(memo)) {
                    result.setResultCode(ResultCode.ERROR_205);
                    result.setResultMessage("审核失败原因不能为空");
                    return result;
                }
            }
            if (StringUtils.isNoneBlank(memo)) {
                if (memo.length() > 255) {
                    result.setResultCode(ResultCode.ERROR_205);
                    result.setResultMessage("审核失败原因长度过长，255个字符以内");
                    return result;
                }
            }
            detail.setCheckUser(opUser);
            detail.setCheckStatus(status);
            detail.setCheckMemo(memo);
            detail.setModifiedUser(opUser);
            long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_WITH_DRAWALS_LOCK
                    + detail.getUid(), "" + detail.getUid(), 1);// 设置超时时间1s
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_408);
                result.setResultMessage("提现中，请勿重复操作");
                return result;
            }
            if (userWalletService.withdrawalscashAudit(detail)) {
                if (status == WalletDetailCheckStatus.FAIL
                        .getValue()) {
                    UserInfo userInfo = userService.getUserInfo(detail.getUid());
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("failure_cause", memo);
                    PropertiesUtil config = new PropertiesUtil("app.properties");
                    params.put("contact_number", config.getProperty("contact.number"));
                    messageInfoService
                            .sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(),
                                    MessageId.WITH_DRAWALS_FAIL, params);
                }
            } else {
                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage("系统异常");
            }

        } catch (Exception e) {
            logger.error("withdrawalscashAudit {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");

        }
        return result;
    }

    @Override
    public WithdrawalscashPayConfirmResult withdrawalscashPayConfirm(
            WithdrawalscashPayConfirmReqData withdrawalscashPayConfirmReqData) {
        logger.debug("WithdrawalscashPayConfirmReqData recv:{}", withdrawalscashPayConfirmReqData);
        WithdrawalscashPayConfirmResult result = new WithdrawalscashPayConfirmResult();
        Long id = withdrawalscashPayConfirmReqData.getId();
        Integer status = withdrawalscashPayConfirmReqData.getStatus();
        String opUser = withdrawalscashPayConfirmReqData.getOpUser();
        String memo = withdrawalscashPayConfirmReqData.getMemo();
        Date payTime = withdrawalscashPayConfirmReqData.getPayTime();
        String payTransactionId = withdrawalscashPayConfirmReqData.getPayTransactionId();
        try {
            if (id == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("id不能为空");
                return result;
            }
            UserWalletDetail detail = userWalletService.getUserWalleDetail(id);
            if (detail == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("提现信息不存在");
                return result;
            }
            if (status != WalleDetailPayStatus.SUCCESS.getValue()) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("status 无效，只支持1(打款成功)");
                return result;
            }

            if (StringUtils.isBlank(opUser)) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("操作人不能为空");
                return result;
            }
            if (opUser.length() > 20) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("操作人长度过长，20个字符以内");
                return result;
            }
            if (StringUtils.isBlank(payTransactionId)) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("交易号不能为空");
                return result;
            }
            if (payTransactionId.length() > 64) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("交易号长度过长，64个字符以内");
                return result;
            }
            if (StringUtils.isBlank(memo)) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("备注不能为空");
                return result;
            }
            if (memo.length() > 255) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("备注长度过长，255个字符以内");
                return result;
            }
            if (payTime == null) {
                result.setResultCode(ResultCode.ERROR_206);
                result.setResultMessage("payTime 不能为空");
                return result;
            }
            if (detail.getCheckStatus() == null || detail.getCheckStatus() == WalletDetailCheckStatus.NORMAL.getValue()) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("未审核过，无法打款确认");
                return result;
            }
            if (detail.getCheckStatus() == WalletDetailCheckStatus.FAIL.getValue()) {
                result.setResultCode(ResultCode.ERROR_207);
                result.setResultMessage("审核失败，无法打款确认");
                return result;
            }
            if (detail.getPayStatus() != null
                    && (detail.getPayStatus() == WalleDetailPayStatus.SUCCESS.getValue())) {
                result.setResultCode(ResultCode.ERROR_208);
                result.setResultMessage("打款已确认，无需再次确认");
                return result;
            }
            detail.setPayMemo(memo);
            detail.setPayStatus(status);
            detail.setPayUser(opUser);
            detail.setModifiedUser(opUser);
            detail.setPayTime(payTime);
            detail.setPayTransactionId(payTransactionId);
            long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_WITH_DRAWALS_LOCK
                    + detail.getUid(), "" + detail.getUid(), 1);// 设置超时时间1s
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_408);
                result.setResultMessage("提现中，请勿重复操作");
                return result;
            }
            if (userWalletService.withdrawalscashPayConfirm(detail)) {
                UserInfo userInfo = userService.getUserInfo(detail.getUid());
                if (userInfo != null) {
                    // 发送审核通过短信和PUSH
                    Map<String, String> params = new HashMap<String, String>();
                    if (status == UserWalletDetail.PAY_SUCCESS) {
                        messageInfoService
                                .sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(),
                                        MessageId.WITH_DRAWALS_SUCCESS, params);
                    } else if (status == UserWalletDetail.PAY_FALSE) {
                        params.put("failure_cause", memo);
                        PropertiesUtil config = new PropertiesUtil("app.properties");
                        params.put("contact_number", config.getProperty("contact.number"));
                        messageInfoService
                                .sendSmsAndPushMessage(userInfo.getUid(), userInfo.getMobile(),
                                        MessageId.WITH_DRAWALS_FAIL, params);
                    }
                }
            } else {
                result.setResultCode(ResultCode.ERROR_500);
                result.setResultMessage("系统异常");
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("payConfirm {}", e);
        }
        return result;
    }

    @Override
    public WalletDetailResult getDetail(WalletDetailReqData data) {
        WalletDetailResult result = new WalletDetailResult();
        try {
            if (data.getUserWalleDetailId() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("提现记录id不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(data.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            Long uid = data.getUid();
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                    uid = userService.getFacilitatorAdminUid(userInfo
                            .getCompanyId());
                }
            }
            UserWalletDetail userWalletDetail = userWalletService
                    .getUserWalleDetail(data.getUserWalleDetailId());
            if (userWalletDetail == null
                    || !userWalletDetail.getUid().equals(uid)) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("提现记录信息为空");
                return result;
            }
            boolean isAplipay = PayType.isAplipay(userWalletDetail
                    .getFundTarget().intValue());
            WalletDetailInfo walletDetail = getWalletDetailInfo(userWalletDetail);
            if (userWalletDetail
                    .getFundTarget()>1000) {
                UserBankCard card = userWalletService
                        .getUserBankCardById(userWalletDetail.getFundTarget());
                if (card == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("没有该银行卡记录");
                    return result;
                } else {
                    walletDetail.setAccount(card.getAccount());
                    walletDetail.setBankBranchName(card.getBankBranchName());
                    walletDetail.setBankName(card.getBankName());
                    walletDetail.setBankId(card.getBankId());
                }
            }
            if (isAplipay) {
                walletDetail.setAccount(userWalletDetail.getDealAccount());
            }
            result.setWalletDetail(walletDetail);
            // 少一个银行类型的属性赋值
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getDetail {}", e);
        }
        return result;
    }

    @Override
    public PreWithdrawalsResult getPreithdrawals(
            PreWithdrawalsReqData preWithdrawalsReqData) {
    	Long uid = preWithdrawalsReqData.getUid();
        PreWithdrawalsResult result = new PreWithdrawalsResult();
        try {
            if (preWithdrawalsReqData.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            LoginInfo loginInfo=cachedService.getLoginInfo(uid);
            if(!loginInfo.isRenterWapApp()){
            	if (userInfo.isNormalMember()) {
                    result.setResultCode(ResultCode.UNAUTHORIZED_ERROR);
                    result.setResultMessage("账号未通过认证，请先前去认证！");
                    return result;
                }
            }
            if (userInfo.isFacilitator()) {// 服务商子帐号处理
                if (userInfo.isNotFacilitatorAdmin()) {
                    uid = userService.getFacilitatorAdminUid(userInfo
                            .getCompanyId());
                }
            }
            UserWallet userWallet = userWalletService.getByUid(uid);
            if (userWallet != null) {
                result.setAmount(MoneyUtils.format(userWallet.getAmount()));
            } else {
                result.setAmount("0.00");
            }

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getPreithdrawals {}", e);
        }
        return result;
    }

    @Override
    public AckwithdrawalsResult addWalletDetail(AckwithdrawalsReqData data) {
        AckwithdrawalsResult result = new AckwithdrawalsResult();
        Long uid = data.getUid();
        try {
            if (data.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(data.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            LoginInfo loginInfo=cachedService.getLoginInfo(uid);
            if(!loginInfo.isRenterWapApp()){
            	if (userInfo.isNormalMember()) {
                    result.setResultCode(ResultCode.UNAUTHORIZED_ERROR);
                    result.setResultMessage("您还未通过认证，请去认证页面查看！");
                    return result;
                }
            }
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                    uid = userService.getFacilitatorAdminUid(userInfo.getCompanyId());
                }
            }
            if (StringUtils.isBlank(data.getAmount())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("金额不能为空");
                return result;
            }
            if (!BizUtils.isPositiveNumber(data.getAmount())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("金额必须为数字");
                return result;
            }
            if (!MoneyUtils.greaterThan(Double.parseDouble(data.getAmount()), 0.00D)) {//支付金额大于0.00
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("金额必须大于0.00");
                return result;
            }
            if (data.getType() == null) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("提现账户类型不能为空");
                return result;
            }
            if (data.getType() == 2) {//银行卡
                if (data.getBankCardId() == null) {
                    result.setResultCode(ResultCode.ERROR_203);
                    result.setResultMessage("银行卡id不能为空");
                    return result;
                }
                UserBankCard card = userWalletService.getUserBankCardById(data.getBankCardId());
                if (card == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("银行卡不存在");
                    return result;
                }

                if (card.getStatus().intValue() == WalletDetailCheckStatus.FAIL.getValue()) {
                    result.setResultCode(ResultCode.ERROR_402);
                    result.setResultMessage("银行卡未审核通过");
                    return result;
                }
            } else if (data.getType() == 1) {//支付宝
                if (StringUtils.isBlank(data.getAlipayAccount())) {
                    result.setResultCode(ResultCode.ERROR_403);
                    result.setResultMessage("支付宝账户号不能为空");
                    return result;
                }
                if (data.getAlipayAccount().contains("@")) {
                    if (!BizUtils.checkEmail(data.getAlipayAccount())) {
                        result.setResultCode(ResultCode.ERROR_206);
                        result.setResultMessage("支付宝账户号格式不正确(只支持手机号码和邮箱)");
                        return result;
                    }
                } else {
                    if (!BizUtils.checkMobile(data.getAlipayAccount())) {
                        result.setResultCode(ResultCode.ERROR_207);
                        result.setResultMessage("支付宝账户号格式不正确(只支持手机号码和邮箱)");
                        return result;
                    }
                }
            }
            if (!cachedService.checkMsgCode(data.getMsgcodeid(),
                    data.getMsgcode())) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("验证码不正确");
                return result;
            }

            UserWallet userWallet = userWalletService.getByUid(uid);
            if (userWallet == null) {
                result.setResultCode(ResultCode.ERROR_404);
                result.setResultMessage("钱包不存在");
                return result;
            }
            if (!MoneyUtils.greaterThanOrEqual(userWallet.getAmount(), Double.parseDouble(data.getAmount()))) {
                result.setResultCode(ResultCode.ERROR_405);
                result.setResultMessage("转出金额超限");
                return result;
            }
            long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_WITH_DRAWALS_LOCK
                    + uid, "" + uid, 1);// 设置超时时间1s
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_408);
                result.setResultMessage("提现中，请勿重复操作");
                return result;
            }
            userWallet.setAmount(userWallet.getAmount() - Double.parseDouble(data.getAmount()));
            UserWalletDetail detail = new UserWalletDetail();
            String orderId = BizUtils.getOrderId("T");
            detail.setUid(uid);
            detail.setAmount(Double.parseDouble(data.getAmount()));
            detail.setAvailableAmount(userWallet.getAmount());
            detail.setAvailableFreezingAmount(userWallet.getFreezingAmount());
            detail.setOrderId(orderId);
            // 余额
            detail.setFundSource(new Long(PayType.WALLET.getValue()));
            detail.setDealType(WalletDealType.INCOME.getValue());
            detail.setType(WalletType.WITHDRAW_CASH.getValue());
            detail.setRemark(userInfo.getNickName() + "提现");
            detail.setCheckStatus(WalletDetailCheckStatus.NORMAL.getValue());
            detail.setPayStatus(PayStatus.UNPAY.getValue());
            if (data.getType() == 2) {
                detail.setFundTarget(data.getBankCardId());
            } else {
                detail.setFundTarget(new Long(PayType.ALIPAY.getValue()));
                detail.setDealAccount(data.getAlipayAccount());
            }
            userWalletService.addWalletDetail(detail, userWallet);

        } catch (BusinessException e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            if (jedisUtil.exists(CacheKey.CACHE_KEY_WITH_DRAWALS_LOCK + uid)) {
                jedisUtil.del(CacheKey.CACHE_KEY_WITH_DRAWALS_LOCK + uid);
            }
            logger.error("addWalletDetail:{}", e);
        }

        return result;
    }

    @Override
    public DelBankResult delBankCard(DelBankReqData delBankReqData) {
        DelBankResult result = new DelBankResult();
        Long uid = delBankReqData.getUid();
        try {
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }

            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            if (delBankReqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("银行卡id不能为空");
                return result;
            }
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                    uid = userService.getFacilitatorAdminUid(userInfo
                            .getCompanyId());
                }
            }
            UserBankCard card = userWalletService
                    .getUserBankCardById(delBankReqData.getId());
            if (card == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("银行卡记录不存在");
                return result;
            }
            if (uid.longValue() != card.getUid()
                    .longValue()) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("此用户没有该银行卡记录");
                return result;
            }
            String msgcodeid = delBankReqData.getMsgcodeid();
            String msgcode = delBankReqData.getMsgcode();
            if (!cachedService.checkMsgCode(msgcodeid, msgcode)) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("验证码不正确");
                return result;
            }
            
            long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_BANK_CARD_LOCK
                    + uid, "" + uid, 1);// 设置超时时间1s
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_408);
                result.setResultMessage("正在操作中，请勿重复操作");
                return result;
            }
            userWalletService.deleteBankCard(delBankReqData.getId(), userInfo.getLoginName());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("delBankCard {}", e);
            if (jedisUtil.exists(CacheKey.CACHE_KEY_BANK_CARD_LOCK
                    + uid)) {
                jedisUtil.del(CacheKey.CACHE_KEY_BANK_CARD_LOCK
                        + uid);
            }
        }
        return result;
    }

    @Override
    public MyBankCardsResult myBankCards(Long uid) {
        MyBankCardsResult result = new MyBankCardsResult();
        try {
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                    uid = userService.getFacilitatorAdminUid(userInfo
                            .getCompanyId());
                }
            }

            // 获取银行信息
            Map<Long, String> mapBankIdToCardIcon = getMapBankIdToCardIcon();
            List<UserBankCard> cards = userWalletService.getCardsByUid(uid);
            List<UserBankCardInfo> myCards = new ArrayList<UserBankCardInfo>();
            for (UserBankCard card : cards) {
                UserBankCardInfo info = new UserBankCardInfo();
                BeanUtils.copyProperties(card, info);
                if (mapBankIdToCardIcon.containsKey(card.getBankId())) {
                    info.setIcon(mapBankIdToCardIcon.get(card.getBankId()));
                }
                // 获取银行卡开户地信息
                if (null != card.getProvinceId()) {
                	ProvinceInfo provinceInfo = areaService.getProvinceInfo(card.getProvinceId());
                	if (null != provinceInfo) {
                		info.setProvinceId(provinceInfo.getId());
						info.setProvinceName(provinceInfo.getName());
					}
                	if (null != card.getCityId()) {
    					CityInfo cityInfo = areaService.getCityInfo(card.getProvinceId(), card.getCityId());
    					if (null != cityInfo) {
							info.setCityId(cityInfo.getId());
							info.setCityName(cityInfo.getName());
						}
    				}
				}
                myCards.add(info);
            }
            
            result.setBankCards(myCards);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("myBankCards {}", e);
        }
        return result;
    }

    private Map<Long, String> getMapBankIdToCardIcon() {
        Map<Long, String> result = new HashMap<>();
        List<Map<String, Object>> mapListBankIdToCardIcon = bankService.getBankIdToCardIcon();
        if (CollectionUtils.isNotEmpty(mapListBankIdToCardIcon)) {
            for (Map<String, Object> map : mapListBankIdToCardIcon) {
                result.put(Long.valueOf(map.get("id").toString()), map.get("cardIcon").toString());
            }
        }
        return result;
    }

    @Override
    public GetBanksResult getBanks(Long uid) {
        GetBanksResult result = new GetBanksResult();
        if (uid == null) {
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("uid不能为空");
            return result;
        }

        List<BankType> bankTypeList = bankService.selectAllBankType();
        if (CollectionUtils.isEmpty(bankTypeList)) {
            bankTypeList = new ArrayList<>();
        }
        result.setBanks(bankTypeList);
        return result;
    }

    @Override
    public List<BankType> getBanks() {
        List<BankType> list = bankService.selectAllBankType();
        BankType apliy = new BankType();
        apliy.setId(1L);
        apliy.setName("支付宝");
        list.add(apliy);
        return list;
    }

    @Override
    public AddBankCardResult addBankCard(AddBankCardReq data) {
        /**
         * 电工用户可以添加多张银行卡
         * 服务商用户只能添加一个张银行卡
         * 企业电工无法添加银行卡
         */
        AddBankCardResult result = new AddBankCardResult();
        Long uid = data.getUid();
        try {
            if (data.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            /*if (!(userInfo.isFacilitator() || userInfo.isSocialElectrician() || userInfo.isCustomer())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("当前用户无法添加银行卡");
                return result;
            }*/
            if (userInfo.isFacilitator()) {// 服务商
                if (userInfo.isNotFacilitatorAdmin()) {
                    uid = userService.getFacilitatorAdminUid(userInfo
                            .getCompanyId());
                }
            }
            if (data.getBankId() == null) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("银行类型不能为空");
                return result;
            }

            // 获取银行信息
            Map<Long, String> mapBankIdToName = getMapBankIdToName();
            if (!mapBankIdToName.containsKey(data.getBankId())) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("银行类型不存在");
                return result;
            }
            if (StringUtils.isBlank(data.getBankBranchName())) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("支行名字不能为空");
                return result;
            }
            if (!BizUtils.checkLength(data.getBankBranchName().trim(), 64)) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("支行名字长度过长");
                return result;
            }
            if (StringUtils.isBlank(data.getAccount())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("银行账户号不能为空");
                return result;
            }
            if (!BizUtils.checkLength(data.getAccount().trim(), 19)) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("银行账户号长度过长");
                return result;
            }
            if (!BizUtils.isBankCardValid(data.getAccount())) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("银行账户号输入不正确");
                return result;
            }
            if (userWalletService.isBandAccountExit(data.getAccount(), uid)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("当前银行卡已经存在");
                return result;
            }
            // 如果是服务商或客户，只能添加一张银行卡
            if (userInfo.isFacilitator() || userInfo.isCustomer()) {
                if (userWalletService.getUserBankCardCount(uid) >= 1) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("只能添加一张银行卡");
                    return result;
                }
            }
            if (!cachedService.checkMsgCode(data.getMsgcodeid(),
                    data.getMsgcode())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("验证码不正确");
                return result;
            }
            // 如果是服务商，则必须填写省份和城市
            if (userInfo.isFacilitator() || userInfo.isCustomer()) {
            	if (null == data.getProvinceId()) {
    				result.set(ResultCode.ERROR_403, "请选择省份");
    				return result;
    			}
                if (null == data.getCityId()) {
    				result.set(ResultCode.ERROR_403, "请选择城市");
    				return result;
    			}
			}
            long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_BANK_CARD_LOCK
                    + uid, "" + uid, 1);// 设置超时时间1s
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_408);
                result.setResultMessage("正在操作中，请勿重复操作");
                return result;
            }
            UserBankCard card = new UserBankCard();
            card.setAccount(data.getAccount());
            card.setBankBranchName(data.getBankBranchName());
            card.setBankId(data.getBankId());
            card.setBankName(mapBankIdToName.get(card.getBankId()));
            card.setUid(uid);
            card.setStatus(WalletDetailCheckStatus.NORMAL.getValue());
            card.setRemark(userInfo.getLoginName() + "的银行卡");
            card.setCreateUser(userInfo.getLoginName());
            card.setProvinceId(data.getProvinceId());
            card.setCityId(data.getCityId());
            userWalletService.addUserBankCard(card);
        } catch (Exception e) {
            logger.error("addBankCard {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            if (jedisUtil.exists(CacheKey.CACHE_KEY_BANK_CARD_LOCK
                    + uid)) {
                jedisUtil.del(CacheKey.CACHE_KEY_BANK_CARD_LOCK
                        + uid);
            }
        }
        return result;
    }

    private Map<Long, String> getMapBankIdToName() {
        Map<Long, String> result = new HashMap<>();
        List<Map<String, Object>> mapListBankIdToName = bankService.getBankIdToName();
        if (CollectionUtils.isNotEmpty(mapListBankIdToName)) {
            for (Map<String, Object> map : mapListBankIdToName) {
                result.put(Long.valueOf(map.get("id").toString()), map.get("name").toString());
            }
        }
        return result;
    }

    @Override
    public Result refundNeedsOrderDeposit(String orderId) {
        Result result = new DefaultResult();
        try {
            if (StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_201, "orderId不能为空");
                return result;
            }
            NeedsOrder needsOrder = needsOrderService.getNeedsOrderByOrderId(orderId);
            if (needsOrder == null) {
                result.set(ResultCode.ERROR_202, "orderId不存在");
                return result;
            }
            long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_REFUND_NEEDS_ORDER_DEPOSIT
                    + orderId, orderId, 200L);// 设置超时时时间200ms
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("正在操作中，请勿重复操作");
                return result;
            }
            if (!needsOrderService.refundNeedsOrderDeposit(needsOrder, false)) {
                result.set(ResultCode.ERROR_402, "退款失败");
                return result;
            }

        } catch (Exception e) {
            logger.error("refundNeedsOrderDeposit {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
        }
        return result;
    }

    @Override
    public Result refundSocialWorkOrderDeposit(String orderId) {
        Result result = new DefaultResult();

        try {
            if (StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_201, "orderId不能为空");
                return result;
            }
            SocialWorkOrder socialWorkOrder = socialWorkOrderService.getBySimpleOrderId(orderId);
            if (socialWorkOrder == null) {
                result.set(ResultCode.ERROR_202, "orderId不存在");
                return result;
            }
            if (socialWorkOrder.getRefundStatus() == SocialWorkOrderRefundStatus.SUCCESS.getValue()) {//已退款
                result.set(ResultCode.ERROR_203, "订单已退款，无需重复退款");
                return result;
            }
            if (socialWorkOrder.getPayStatus() != PayStatus.SUCCESS.getValue()) {
                result.set(ResultCode.ERROR_204, "订单未支付，不能退款");
                return result;
            }
            if (!(socialWorkOrder.getStatus() == SocialWorkOrderStatus.CANCEL.getValue() ||
                    socialWorkOrder.getStatus() == SocialWorkOrderStatus.LIQUIDATED.getValue())) {
                result.set(ResultCode.ERROR_205, "订单不是取消或者已结算，不能退款");
                return result;
            }
            long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_REFUND_SOCIAL_WORK_ORDER_DEPOSIT
                    + orderId, orderId, 1000L);// 设置超时时时间1000ms
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("正在操作中，请勿重复操作");
                return result;
            }
            if (!socialWorkOrderService.refundDeposit(socialWorkOrder)) {
                result.set(ResultCode.ERROR_402, "退款失败");
                return result;
            }

        } catch (Exception e) {
            logger.error("refundSocialWorkOrderDeposit {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
        }
        return result;
    }

	@Override
	public Result settlementPrepayRenterChargeOrder(String orderId) {
		Result result = new DefaultResult();

        try {
            if(StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_201, "orderId不能为空");
                return result;
            }
            RenterChargeOrder renterChargeOrder = renterChargeOrderService.getByOrderId(orderId);
            if (renterChargeOrder == null) {
                result.set(ResultCode.ERROR_202, "orderId不存在");
                return result;
            }
           
            if(renterChargeOrder.getType()!=Constants.PRE_PAY){
            	result.set(ResultCode.ERROR_203, orderId+" 不是预付费账单");
                return result;
            }
            if(renterChargeOrder.getPayStatus()==PayStatus.SUCCESS.getValue()){
            	result.set(ResultCode.ERROR_203, orderId+" 已结清，请勿重复操作");
                return result;
            }
            CompanyRenter companyRenter=companyRenterService.getById(renterChargeOrder.getRenterId());
            if(companyRenter==null){
            	result.set(ResultCode.ERROR_204, "租客信息不存在");
                return result;
            }
            CompanyRenterConfig renterConfig=companyRenterService.getRenterConfig(renterChargeOrder.getRenterId());
            if(renterConfig==null){
            	result.set(ResultCode.ERROR_204, "租客配置不存在");
                return result;
            }
            UserWallet wallet=userWalletService.getByUid(companyRenter.getMemberId());
            if(wallet==null){
            	result.set(ResultCode.ERROR_205, "钱包信息不存在");
                return result;
            }
            if(MoneyUtils.lessThan(wallet.getAmount(),renterConfig.getAmountLimit())){
        		logger.warn("会员({}),当前余额({}),余额不足({})",companyRenter.getMemberId(),wallet.getAmount(),renterConfig.getAmountLimit());
        		renterChargeOrderService.walletNotify(wallet,renterConfig);
        	}
            if(!MoneyUtils.greaterThan(renterChargeOrder.getCharge(), renterChargeOrder.getPrepaidCharge())&&
            		renterChargeOrder.getStatus()==TrueStatus.NO.getValue()){
            	result.set(ResultCode.ERROR_404, "orderId 不需要结算");
                return result;
            }
            long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_SETTLEMENT_PREPAY_RENTER_CHARGE_ORDER
                    + orderId, orderId, 2000L);// 设置超时时时间1000ms
            if (rs == 0) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("正在操作中，请勿重复操作");
                return result;
            }
            if (!renterChargeOrderService.settlementPrepayRenterChargeOrder(orderId)) {
                result.set(ResultCode.ERROR_402, "结算款失败");
                return result;
            }
        } catch (Exception e) {
            logger.error("settlementPrepayRenterChargeOrder {}", e);
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
        }
        return result;
	}
	

}
