package com.edianniu.pscp.portal.controller;

import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.bean.pay.PreparePayReq;
import com.edianniu.pscp.mis.bean.pay.PreparePayResult;
import com.edianniu.pscp.mis.bean.wallet.*;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import com.edianniu.pscp.portal.commons.ResultCode;
import com.edianniu.pscp.portal.entity.MemberBankCardEntity;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.MemberBankCardService;
import com.edianniu.pscp.portal.service.SysUserService;
import com.edianniu.pscp.portal.utils.IPUtil;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-19 11:23:06
 */
@Controller
@RequestMapping("walletDetail")
public class MemberWalletController extends AbstractController {
    @Autowired
    private WalletInfoService walletInfoService;

    @Autowired
    private MemberBankCardService companyBankCardService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private PayInfoService payService;

    private String platformDomain;

    @Value(value = "${platform.domain}")
    public void setPlatformDomain(String platformDomain) {
        this.platformDomain = platformDomain;
    }

    @RequestMapping("/wallet.html")
    public String list() {
        return "cp/walletDetail.html";
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("walletDetail:list")
    public R list(Integer page, Integer limit) {
    	boolean isFacilitator = ShiroUtils.getUserEntity().isFacilitator();
    	SysUserEntity entity = ShiroUtils.getUserEntity();
    	if (isFacilitator) {
    		entity = userService.getCompanyAdmin(entity.getCompanyId());
		}
        DaybookReqData daybookReqData = new DaybookReqData();
        daybookReqData.setLimit(limit);
        daybookReqData.setOffset((page - 1) * limit);
        daybookReqData.setUid(entity.getUserId());
        //查询列表数据
        DaybookResult result = walletInfoService.getDayBook(daybookReqData);

        PageUtils pageUtil = new PageUtils(result.getWalletDetails(), result.getTotalCount(), limit, page);
        R r = R.ok();
        r.put("page", pageUtil);
        return r;
    }

    @ResponseBody
    @RequestMapping("/preWithdrawals")
    @RequiresPermissions("walletDetail:preWithdrawals")
    public R preWithdrawals() {
    	boolean isFacilitator = ShiroUtils.getUserEntity().isFacilitator();
    	SysUserEntity entity = ShiroUtils.getUserEntity();
    	if (isFacilitator) {
    		entity = userService.getCompanyAdmin(entity.getCompanyId());
		}
    	
        WalletHomeResult preResult = walletInfoService.getHome(entity.getUserId(),false);
        if (preResult.isSuccess()) {
            R r = R.ok().put("result", preResult);
            r.put("mobile", ShiroUtils.getUserEntity().getMobile());
            return r;
        } else {
            return R.error(preResult.getResultCode(), preResult.getResultMessage());
        }

    }

    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("walletDetail:info")
    public R info(@PathVariable("id") Long id) {
        WalletDetailReqData walletDetailReqData = new WalletDetailReqData();
        walletDetailReqData.setUserWalleDetailId(id);
        walletDetailReqData.setUid(ShiroUtils.getUserId());
        WalletDetailResult memberWallet = walletInfoService.getDetail(walletDetailReqData);
        if (memberWallet.isSuccess()) {
            return R.ok().put("memberWallet", memberWallet.getWalletDetail());
        } else {
            return R.error(memberWallet.getResultCode(), memberWallet.getResultMessage());
        }

    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("walletDetail:save")
    public R save(@RequestBody AckwithdrawalsReqData data) {
    	boolean isFacilitator = ShiroUtils.getUserEntity().isFacilitator();
    	SysUserEntity entity = ShiroUtils.getUserEntity();
    	if (isFacilitator) {
    		entity = userService.getCompanyAdmin(entity.getCompanyId());
		}
        data.setUid(entity.getUserId());
        MemberBankCardEntity bankCard = companyBankCardService.getAdminBankCard();
        if (bankCard == null || bankCard.getId() == null) {
            return R.error(ResultCode.ERROR, "请绑定提现银行卡信息");
        }

        data.setBankCardId(bankCard.getId());
        //提现到银行卡
        data.setType(2);
        AckwithdrawalsResult result = walletInfoService.addWalletDetail(data);

        if (result.isSuccess()) {
            return R.ok();
        } else {
            return R.error(result.getResultCode(), result.getResultMessage());
        }

    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/recharge")
    @RequiresPermissions("walletDetail:recharge")
    public R recharge(@RequestBody PreparePayReq preparePayReq, HttpServletRequest request) {
        preparePayReq.setIp(IPUtil.getIpAddress(request));
        preparePayReq.setUid(this.getUserId());

        preparePayReq.setReturnUrl(String.format("%s%s", platformDomain, "cp/payment/alipay"));
        preparePayReq.setExtendParams("main.html#walletDetail/wallet.html");
        String callback = "";
        if (preparePayReq.getPayType().intValue() == PayType.ALIPAY.getValue()) {
            callback = String.format("%s%s", platformDomain, "cp/payment/alipay/callback");

        } else if (preparePayReq.getPayType().intValue() == PayType.UNIONPAY.getValue()) {
            callback = String.format("%s%s", platformDomain, "cp/payment/unionpay/callback");

        }
        preparePayReq.setReturnUrl(callback);
        PreparePayResult result = payService.preparePay(preparePayReq);


        if (result.isSuccess()) {
            R r = R.ok();
            r.put("payType", preparePayReq.getPayType());
            r.put("orderId", result.getOrderId());
            r.put("orderType", "充值订单");
            r.put("amount", preparePayReq.getAmount());

            if (preparePayReq.getPayType().intValue() == PayType.ALIPAY.getValue()) {
                String thirdPartyPaymentInfo = new String(Base64.encodeBase64(result.getAlipay().getParams().getBytes()));


                String url = String.format("%s%s", platformDomain, "cp/payment/alipay");
                r.put("returnUrl", url);


                r.put("params", thirdPartyPaymentInfo);
            } else if (preparePayReq.getPayType().intValue() == PayType.WEIXIN.getValue()) {


                String url = String.format("%s%s", platformDomain, "cp/payment/wxpay");
                r.put("returnUrl", url);
                r.put("redirectUrl", "index.html#walletDetail/wallet.html");

                r.put("params", result.getWeixinpay().getCodeUrl());
            }

            return r;
        } else {
            return R.error(result.getResultMessage());
        }

    }

    @ResponseBody
    @RequestMapping("/payCheck")
    @RequiresPermissions("walletDetail:payCheck")
    public R payCheck(@RequestBody AckwithdrawalsReqData recharge) {

        //memberWalletService.addWalletDetail(data);

        return R.ok();
    }

}
