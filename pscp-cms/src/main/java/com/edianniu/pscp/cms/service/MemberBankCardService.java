package com.edianniu.pscp.cms.service;

import com.edianniu.pscp.cms.entity.MemberBankCardEntity;
import com.edianniu.pscp.cms.utils.JsonResult;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-26 11:12:59
 */
public interface MemberBankCardService {
	
	MemberBankCardEntity queryObject(Long id);
	
	List<MemberBankCardEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	JsonResult save(MemberBankCardEntity memberBankCard);
	
	JsonResult update(MemberBankCardEntity memberBankCard);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	MemberBankCardEntity getAdminBankCard();
	
}
