package com.edianniu.pscp.mis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.UserBankCard;

public interface UserBankCardDao {
	public UserBankCard getUserBankCardById(Long id);
	
	public List<UserBankCard> getBankCardsByUid(Long uid);
	
	public int getBankCardCountByUid(Long uid);
	
	public void addUserBankCard(UserBankCard userBankCard);
	
	public void updateUserBankCard(UserBankCard UserBankCard);
	
	public UserBankCard getUserBankCardByUidAndAccount(UserBankCard UserBankCard);
	
	public void deleteBankCard (@Param("id")Long id,@Param("modifiedUser")String modifiedUser);
}
