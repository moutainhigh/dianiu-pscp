package com.edianniu.pscp.mis.service.dubbo;

import java.util.List;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.wallet.AckwithdrawalsReqData;
import com.edianniu.pscp.mis.bean.wallet.AckwithdrawalsResult;
import com.edianniu.pscp.mis.bean.wallet.AddBankCardReq;
import com.edianniu.pscp.mis.bean.wallet.AddBankCardResult;
import com.edianniu.pscp.mis.bean.wallet.BankType;
import com.edianniu.pscp.mis.bean.wallet.WithdrawalscashAuditReqData;
import com.edianniu.pscp.mis.bean.wallet.WithdrawalscashAuditResult;
import com.edianniu.pscp.mis.bean.wallet.DaybookReqData;
import com.edianniu.pscp.mis.bean.wallet.DaybookResult;
import com.edianniu.pscp.mis.bean.wallet.DelBankReqData;
import com.edianniu.pscp.mis.bean.wallet.DelBankResult;
import com.edianniu.pscp.mis.bean.wallet.GetBanksResult;
import com.edianniu.pscp.mis.bean.wallet.MyBankCardsResult;
import com.edianniu.pscp.mis.bean.wallet.WithdrawalscashPayConfirmReqData;
import com.edianniu.pscp.mis.bean.wallet.PreWithdrawalsReqData;
import com.edianniu.pscp.mis.bean.wallet.PreWithdrawalsResult;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailReqData;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailResult;
import com.edianniu.pscp.mis.bean.wallet.WalletHomeResult;
import com.edianniu.pscp.mis.bean.wallet.WalletRechargeResult;
import com.edianniu.pscp.mis.bean.wallet.WithdrawalscashPayConfirmResult;

/**
 * 钱包接口
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月9日 上午9:54:21 
 * @version V1.0
 */
public interface WalletInfoService{
    /**
     * 取钱包首页
     *
     * @param uid 用户ID
     *  @param hasWalletDetails true:下发资金明细,false:不下发资金明细
     * @return
     */
    WalletHomeResult getHome(Long uid,boolean hasWalletDetails);
    /**
     * 我的钱包-充值页面
     * @param uid
     * @param amount
     * @return
     */
    WalletRechargeResult recharge(Long uid,String amount);

    /**
     * 获取资金明细
     *
     * @param offset
     * @return
     */
   DaybookResult getDayBook(DaybookReqData data);
   /**
    * 提现审核
    * @param withdrawalscashAuditReqData
    * @return
    */
   public  WithdrawalscashAuditResult  withdrawalscashAudit(WithdrawalscashAuditReqData withdrawalscashAuditReqData);
	/**
	 * 提现打款确认
	 * @param withdrawalscashPayConfirmReqData
	 * @return
	 */
	public WithdrawalscashPayConfirmResult withdrawalscashPayConfirm(WithdrawalscashPayConfirmReqData withdrawalscashPayConfirmReqData);
   /**
    * 获取资金详情
    *
    * @param WalletDetailReqData
    * @return WalletDetailResult
    */
   public  WalletDetailResult getDetail( WalletDetailReqData data);
   
   /**
    * 预提现
    *
    * @param PreithdrawalsReqData
    * @return PreithdrawalsResult
    */
   public PreWithdrawalsResult getPreithdrawals(PreWithdrawalsReqData data);
   
   /**
    * 提现
    *
    * @param AckwithdrawalsReqData
    * @return AckwithdrawalsResult
    * @throws Exception 
    */
   public AckwithdrawalsResult  addWalletDetail(AckwithdrawalsReqData data) ;
   
   
   /**
    * 删除用户的银卡信息
    *
    * @param DelBankReqData
    * @return DelBankResult
    * 
    */
   public DelBankResult delBankCard(DelBankReqData delBankReqData);
   
   /**
    * 我的银行卡
    *
    * @param uid
    * @return MyBankCardsResult
    * 
    */
   public MyBankCardsResult myBankCards(Long uid);
   
   /**
    * 银行列表
    *
    * @param uid
    * @return GetBanksResult
    * 
    */
   public GetBanksResult getBanks(Long uid);
   /**
    * 银行列表
    * @return
    */
   public List<BankType>  getBanks();
   
   /**
    * 绑定银行卡
    *
    * @param uid
    * @return GetBanksResult
    * 
    */
   public AddBankCardResult addBankCard(AddBankCardReq data);
   /**需求订单保证金退款
    * @param needsOrderId
    * @return
    */
   public Result refundNeedsOrderDeposit(String needsOrderId);
   /**
    * 社会工单保证金退款
    * @param orderId
    * @return
    */
   public Result refundSocialWorkOrderDeposit(String orderId);
   /**
    * 预付费租客电费账单结算
    * @param orderId
    * @return
    */
   public Result settlementPrepayRenterChargeOrder(String orderId);
   
   
}
