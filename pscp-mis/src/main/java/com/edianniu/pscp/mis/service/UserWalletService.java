/**
 *
 */
package com.edianniu.pscp.mis.service;

import java.util.List;

import com.edianniu.pscp.mis.bean.DepositInfo;
import com.edianniu.pscp.mis.bean.wallet.WalletQuery;
import com.edianniu.pscp.mis.domain.UserBankCard;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.exception.BusinessException;

/**
 * 我的钱包
 *
 * @author cyl
 */
public interface UserWalletService {
	/**
	 * 获取总金额根据订单编号+账单类型
	 * @param uid
	 * @param orderId
	 * @param type
	 * @return
	 */
	public Double getUserWalletDetailTotalAmount(Long uid,String orderId,int type);

	/**
	 * 获取保证金
	 * 
	 * @param uid
	 * @param companyId
	 * @return
	 */
	public DepositInfo getDepositInfo(Long uid, Long companyId);

	/**
	 * 冻结保证金(保证金处理) 1.无保证金，余额转保证金 2.保证金不足，余额转保证金 3.保证金已足，不处理
	 * 
	 * @param uid
	 * @param companyId
	 */
	public void frozenDeposit(Long uid, Long companyId)
			throws BusinessException;

	/**
	 * 
	 * @param uid
	 * @return
	 */
	public UserWallet getByUid(Long uid);
	
	public int update(UserWallet userwallet);
	
	public int update(UserWallet fromWallet,UserWallet toWallet);

	
	/**
	 * 取用户账户流水数量
	 *
	 * @param query
	 * @return
	 */
	int queryDaybookCount(WalletQuery query);

	/**
	 * 获取用户账户流水
	 *
	 * @param query
	 * @return
	 */
	List<UserWalletDetail> queryDayBook(WalletQuery query);

	/**
	 * 提现审核
	 * @param userwalleDetail
	 * @return
	 */
	public boolean withdrawalscashAudit(UserWalletDetail userwalleDetail);
	/**
	 * 提现打款确认
	 *
	 * @param userwalleDetail
	 * @return
	 */
	public boolean withdrawalscashPayConfirm(UserWalletDetail userwalleDetail);

	public UserWalletDetail getUserWalleDetail(Long id);

	/**
	 * 根据用户id获取用户所有绑定的银行卡信息
	 *
	 * @param uid
	 * @return
	 */
	public List<UserBankCard> getCardsByUid(Long uid);

	/**
	 * 新增用户银行卡信息
	 *
	 * @param uid
	 * @return
	 */
	public void addUserBankCard(UserBankCard userBankCard);

	/**
	 * 修改用户银行卡信息
	 *
	 * @param uid
	 * @return
	 */
	public void updateUserBankCard(UserBankCard UserBankCard);

	/**
	 * 判断用户是否已经绑定银行卡号
	 *
	 * @param uid
	 * @return
	 */
	public boolean isBandAccountExit(String account, Long uid);
	/**
	 * 获取用户银行卡数量
	 * @param uid
	 * @return
	 */
	public int getUserBankCardCount(Long uid);
	
	/**
	 * 根据用户id是银行卡账号获取用户银行卡信息
	 *
	 * @param uid,account
	 * @return
	 */
	 public UserBankCard getUserBankCardByUidAndAccount(Long uid, String account );
	/**
	 * 根据id获取用户银行卡信息
	 * @param id
	 * @return
	 */
	 public UserBankCard getUserBankCardById(Long id);
	/**
	 * 添加提现/充值记录
	 * @param userwalletDetail
	 * @param userWallet
	 * @throws BusinessException
	 */
	 public void addWalletDetail(UserWalletDetail userwalletDetail,UserWallet userWallet) throws BusinessException;
	 /**
	  * 添加消费明细
	  * @param userWalletDetail
	  */
	 public void addUserWalletDetail(UserWalletDetail userWalletDetail);
	 
	 /**
	  * 添加提现/充值记录
	  * @param userwalletDetail
	  * @param userWallet
	  * @param UserBankCard
	  * @throws Exception
	  */
	 public void addWalletDetailAndBankCard(UserWalletDetail userwalletDetail,UserWallet userWallet,UserBankCard UserBankCard) throws BusinessException;
	 
	 /**
	  * 删除银行卡
	  * @param id
	  */
	 public void deleteBankCard(Long id,String loginName);
	/**
	 * 获取用户所有资金详情记录
	 * @param uid
	 * @return
	 */
	 public List<UserWalletDetail> getDetailsByUid(Long uid);
	 
	 public UserWalletDetail getUserwalleDetailByOrderId(String orderId);
}
