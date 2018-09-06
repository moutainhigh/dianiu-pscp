package com.edianniu.pscp.mis.service.impl;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.mis.bean.DepositInfo;
import com.edianniu.pscp.mis.bean.WalletDealType;
import com.edianniu.pscp.mis.bean.WalleFundType;
import com.edianniu.pscp.mis.bean.WalletType;
import com.edianniu.pscp.mis.bean.wallet.WalletDetailCheckStatus;
import com.edianniu.pscp.mis.bean.wallet.WalletQuery;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.dao.UserBankCardDao;
import com.edianniu.pscp.mis.dao.UserWalletDetailDao;
import com.edianniu.pscp.mis.dao.UserWalletDao;
import com.edianniu.pscp.mis.domain.UserBankCard;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.UserWalletService;
import com.edianniu.pscp.mis.util.MoneyUtils;

@Service
@Repository("userWalletService")
public class DefaultUserWalletService implements UserWalletService {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultUserWalletService.class);
	@Autowired
	private UserWalletDao userWalletDao;
	@Autowired
	private UserBankCardDao userBankCardDao ;
	@Autowired
	private UserWalletDetailDao  userWalletDetailDao ;
	
	@Override
	public Double getUserWalletDetailTotalAmount(Long uid,String orderId, int type) {
		return userWalletDetailDao.getSumAmount(uid, orderId, type);
	}
	
	@Override
	public UserWallet getByUid(Long uid) {
		return userWalletDao.getByUid(uid);
	}
	
    /**
     * 取用户账户流水数量
     *
     * @param query
     * @return
     */
    @Override
    public int queryDaybookCount(WalletQuery query) {
        return this.userWalletDao.queryDaybookCount(query);
    }

    /**
     * 获取用户账户流水
     *
     * @param query
     * @return
     */
    @Override
    public List<UserWalletDetail> queryDayBook(WalletQuery query) {
        return this.userWalletDao.queryDaybook(query);
    }
	@Override
	public DepositInfo getDepositInfo(Long uid,Long companyId) {
		DepositInfo  depositInfo=new DepositInfo();
		depositInfo.setNeedDeposit(Constants.TAG_UN_NEED_DEPOSIT);
		depositInfo.setDeposit("0.00");
		depositInfo.setRechargeAmount("0.00");
		UserWallet userwalle=getByUid(uid);
		if(userwalle==null){
			return depositInfo;
		}
		//支付额度
		double depositFee=0;
		depositInfo.setDeposit(MoneyUtils.format(depositFee));
		double amount=userwalle.getAmount()+userwalle.getFreezingAmount();//余额，保证金，欠款
		depositInfo.setAvailableAmount(MoneyUtils.format(amount));
		if(MoneyUtils.lessThan(amount, 0)){//余额少于保证金金额
			depositInfo.setNeedDeposit(Constants.TAG_NEED_DEPOSIT);
			depositInfo.setRechargeAmount(MoneyUtils.format(depositFee-amount));
		}
		else{
			depositInfo.setNeedDeposit(Constants.TAG_UN_NEED_DEPOSIT);
			depositInfo.setRechargeAmount("0.00");
		}
		return depositInfo;
	}
	@Override
	@Transactional
	public void frozenDeposit(Long uid, Long companyId) throws BusinessException {
		UserWallet userWalle=getByUid(uid);
		if(userWalle==null){
			throw new BusinessException("user("+uid+") walle not exist") ;
		}
		
		/**
		 * 1)保证金+欠款金<保证金金额时，需要扣除余额的金额
		 */
		if(MoneyUtils.lessThan((userWalle.getFreezingAmount()), 0)){
		
			
				/*
				 * 账户流水
				 */
				UserWalletDetail userwalleDetail = new UserWalletDetail();
				userwalleDetail.setUid(userWalle.getUid());
				
				// 账单类型 - 扣款
				userwalleDetail.setType(WalletType.WITHHOLD.getValue());
				// 交易类型 - 支出
				userwalleDetail.setDealType(WalletDealType.COSTS.getValue());								
				// 资金来源 - 余额
				userwalleDetail.setFundSource(new Long(WalleFundType.WALLE_BALANCE.getValue()));
				// 资金去向 - 冻结余额
				userwalleDetail.setFundTarget(new Long(WalleFundType.PLATFORM.getValue()));
				// 交易后 钱包可用余额
				userwalleDetail.setAvailableAmount(userWalle.getAmount());
				// 交易后 钱包冻结金额
				userwalleDetail.setAvailableFreezingAmount(userWalle.getFreezingAmount());
				// 微信/支付宝订单号
				userwalleDetail.setCreateUser("系统");
				// 备注
				userwalleDetail.setRemark("欠款扣费");
				userWalletDetailDao.insert(userwalleDetail);
			
			/*
			userWalle.setAmount(userWalle.getAmount()-userWalle.getArrearAmount()-(costSet.getDepositFee()-userWalle.getFreezingAmount()));
			userWalle.setFreezingAmount(costSet.getDepositFee());
			userWalle.setArrearAmount(0.00);//扣除欠款
			*/
			update(userWalle);
			
		}
	}
	
	@Override
	public UserWalletDetail getUserWalleDetail(Long id) {
		UserWalletDetail detail=userWalletDetailDao.getUserwalleDetail(id);
		
		return detail;
	}
	@Override
	public List<UserBankCard> getCardsByUid(Long uid) {
		List<UserBankCard>list=userBankCardDao.getBankCardsByUid(uid);
		return list;
	}
	@Override
	public void addUserBankCard(UserBankCard userBankCard) {
		userBankCardDao.addUserBankCard(userBankCard);
		
	}
	@Override
	public void updateUserBankCard(UserBankCard UserBankCard) {
		userBankCardDao.updateUserBankCard(UserBankCard);
		
	}
	@Override
	public boolean isBandAccountExit(String account, Long uid) {
		List<UserBankCard> cards=getCardsByUid(uid);
		boolean flag=false;
		if(!cards.isEmpty()){
			for(UserBankCard card:cards){
				if(card.getAccount().equals(account)){
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	
	@Override
	public UserBankCard getUserBankCardByUidAndAccount(Long uid, String account) {
		UserBankCard card=new UserBankCard();
		card.setAccount(account);
		card.setUid(uid);
		card=userBankCardDao.getUserBankCardByUidAndAccount(card);
		return card;
	}
	@Override
	public UserBankCard getUserBankCardById(Long id) {
		UserBankCard card=userBankCardDao.getUserBankCardById(id);
		return card;
	}
	@Override
	@Transactional
	public void addWalletDetail(UserWalletDetail userwalleDetail,UserWallet userWallet) throws BusinessException{
		userWalletDetailDao.insert(userwalleDetail);
		userWalletDao.update(userWallet);
		
	}
	@Override
	@Transactional
	public void addWalletDetailAndBankCard(UserWalletDetail userwalleDetail, UserWallet userWallet,UserBankCard UserBankCard) throws BusinessException {
		addUserBankCard(UserBankCard);
		userwalleDetail.setFundTarget(UserBankCard.getId());
		addWalletDetail(userwalleDetail, userWallet);
		
		
	}
	@Override
	public void deleteBankCard(Long id,String loginName) {
		this.userBankCardDao.deleteBankCard(id,loginName);
		
	}
	@Override
	public UserWalletDetail getUserwalleDetailByOrderId(String orderId) {
		UserWalletDetail detail=this.userWalletDetailDao.getUserwalleDetailByOrderId(orderId);
		return detail;
	}
	
	@Override
	public List<UserWalletDetail> getDetailsByUid(Long uid) {
		List<UserWalletDetail>list=userWalletDetailDao.getDetailsByUid(uid);
		
		
		return list;
	}

	@Override
	public int update(UserWallet userWallet) {
		return userWalletDao.update(userWallet);
	}
	@Override
	public int update(UserWallet fromWallet, UserWallet toWallet) {
		int rs=userWalletDao.update(fromWallet);
		if(toWallet.getId()!=null&&rs==1){
			rs=userWalletDao.update(toWallet);
		}
		return rs;
	}
	@Override
	public int getUserBankCardCount(Long uid) {
		
		return userBankCardDao.getBankCardCountByUid(uid);
	}

	@Override
	@Transactional
	public boolean withdrawalscashAudit(UserWalletDetail userwalleDetail) {
		if (userwalleDetail.getCheckStatus()== WalletDetailCheckStatus.FAIL.getValue()) {//审核失败时，提现资金回滚到余额
			UserWallet userWallet = getByUid(userwalleDetail.getUid());
			if(userWallet==null){
				return false;
			}
			double amount = userWallet.getAmount();
			double tatolAmount = amount + userwalleDetail.getAmount();
			userWallet.setAmount(tatolAmount);
			update(userWallet);
		}
		userWalletDetailDao.withdrawalscashAudit(userwalleDetail);
		return true;
	}
	@Override
	@Transactional
	public boolean withdrawalscashPayConfirm(UserWalletDetail userwalleDetail) {
		if (userwalleDetail == null) {
			return false;
		}
		userWalletDetailDao.withdrawalscashPayConfirm(userwalleDetail);
		Long cardId = userwalleDetail.getFundTarget();
		UserBankCard userBankCard = getUserBankCardById(cardId);
		if (userBankCard != null) {
			if (userBankCard.getStatus().intValue() == UserBankCard.NORMAL
					|| userBankCard.getStatus().intValue() == UserBankCard.FAIL) {
				userBankCard.setModifiedUser(userwalleDetail.getPayUser());
				userBankCard.setStatus(UserBankCard.SUCCESS);
				userBankCard.setRemark("银行卡绑定状态修改");
				updateUserBankCard(userBankCard);
			}
		}
		return true;
	}

	@Override
	public void addUserWalletDetail(UserWalletDetail userWalletDetail) {
		userWalletDetailDao.insert(userWalletDetail);
		
	}

	
}
