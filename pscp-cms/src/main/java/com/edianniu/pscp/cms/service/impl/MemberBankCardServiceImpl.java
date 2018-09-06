package com.edianniu.pscp.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cms.commons.Constants;
import com.edianniu.pscp.cms.dao.MemberBankCardDao;
import com.edianniu.pscp.cms.dao.MemberDao;
import com.edianniu.pscp.cms.entity.MemberBankCardEntity;
import com.edianniu.pscp.cms.entity.MemberEntity;
import com.edianniu.pscp.cms.service.MemberBankCardService;
import com.edianniu.pscp.cms.utils.BizUtils;
import com.edianniu.pscp.cms.utils.JsonResult;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.bean.wallet.BankType;
import com.edianniu.pscp.mis.service.dubbo.AreaInfoService;



@Service("memberBankCardService")
public class MemberBankCardServiceImpl implements MemberBankCardService {
	@Autowired
	private MemberBankCardDao memberBankCardDao;
	@Autowired
	AreaInfoService areaInfoService;
	
	
	@Autowired
	private MemberDao sysUserDao;
	
	@Override
	public MemberBankCardEntity queryObject(Long id){
		
		MemberBankCardEntity bean=memberBankCardDao.queryObject(id);
		
		return bean;
	}
	
	@Override
	public List<MemberBankCardEntity> queryList(Map<String, Object> map){
		return memberBankCardDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		
		return memberBankCardDao.queryTotal(map);
	}
	
	@Override
	public JsonResult save(MemberBankCardEntity memberBankCard){
		JsonResult json=new JsonResult();
		if(memberBankCard.getProvinceId()==null||memberBankCard.getCityId()==null){
			json.setSuccess(false);
			json.setMsg("所在地区不能为空");
			return json;
		}
		if(memberBankCard.getBankId()==null||memberBankCard.getBankId()==0){
			json.setSuccess(false);
			json.setMsg("所在地区不能为空");
			return json;
		}
		if(StringUtils.isBlank(memberBankCard.getBankBranchName())){
			json.setSuccess(false);
			json.setMsg("开户地址不能为空");
			return json;
		}
		if(!BizUtils.checkLength(memberBankCard.getBankBranchName(),32)){
			json.setSuccess(false);
			json.setMsg("开户地址超过32位");
			return json;
		}
		if(StringUtils.isBlank(memberBankCard.getAccount())){
			json.setSuccess(false);
			json.setMsg("开户地址不能为空");
			return json;
		}
		/*
		if(BizUtils.isBankCardValid(memberBankCard.getAccount())){
			json.setSuccess(false);
			json.setMsg("银行卡输入错误");
			return json;
		}*/
		memberBankCardDao.save(memberBankCard);
		json.setSuccess(true);
		json.setObject(memberBankCard);
		return json;
	}
	
	@Override
	public JsonResult update(MemberBankCardEntity memberBankCard){
		JsonResult json=new JsonResult();
		if(memberBankCard.getProvinceId()==null||memberBankCard.getCityId()==null){
			json.setSuccess(false);
			json.setMsg("所在地区不能为空");
			return json;
		}
		if(memberBankCard.getBankId()==null||memberBankCard.getBankId()==0){
			json.setSuccess(false);
			json.setMsg("所在地区不能为空");
			return json;
		}
		if(StringUtils.isBlank(memberBankCard.getBankBranchName())){
			json.setSuccess(false);
			json.setMsg("开户地址不能为空");
			return json;
		}
		if(!BizUtils.checkLength(memberBankCard.getBankBranchName(),32)){
			json.setSuccess(false);
			json.setMsg("开户地址超过32位");
			return json;
		}
		if(StringUtils.isBlank(memberBankCard.getAccount())){
			json.setSuccess(false);
			json.setMsg("开户地址不能为空");
			return json;
		}
		/*
		if(BizUtils.isBankCardValid(memberBankCard.getAccount())){
			json.setSuccess(false);
			json.setMsg("银行卡输入错误");
			return json;
		}
		*/
		memberBankCardDao.update(memberBankCard);
		json.setSuccess(true);
		json.setObject(memberBankCard);
		return json;
	}
	
	@Override
	public void delete(Long id){
		memberBankCardDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		memberBankCardDao.deleteBatch(ids);
	}

	@Override
	public MemberBankCardEntity getAdminBankCard() {
		MemberEntity entity=null;//TODO　ShiroUtils.getUserEntity();
		MemberBankCardEntity bankCard=new MemberBankCardEntity();
		if(!entity.isAdmin()){
			Map<String,MemberEntity>map=new HashMap<String,MemberEntity>();
			MemberEntity bean=new MemberEntity();
			bean.setCompanyId(entity.getCompanyId());
			bean.setIsAdmin(1);
			map.put("bean",  bean);
			List<MemberEntity>users=sysUserDao.queryList(map);
			if(users.size()>0){
				bean=users.get(0);			
				bankCard.setUid(bean.getUserId());	
			}		
		}else{
			bankCard.setUid(entity.getUserId());			
		}
		Map<String,MemberBankCardEntity>bankmap=new HashMap<String,MemberBankCardEntity>();
		bankmap.put("bean", bankCard);
		List<MemberBankCardEntity>bankCards=memberBankCardDao.queryList(bankmap);
		MemberBankCardEntity card=null;
		if(!bankCards.isEmpty()&&bankCards.size()>0){
			card=bankCards.get(0);
		}	
		
		if(card==null){
			card=new MemberBankCardEntity();			
			card.setBanks(Constants.bankTypes);
		}else{
			List<BankType>banks=Constants.bankTypes;
			for(BankType bank :banks){
				if(bank.getId().intValue()==card.getBankId().intValue()){
					card.setBankName(bank.getName());
				}
			}
			ProvinceInfo province=areaInfoService.getProvinceInfo(card.getProvinceId());
			if(province!=null)
				card.setProvinceName(province.getName());
			CityInfo city=areaInfoService.getCityInfo(card.getProvinceId(), card.getCityId());
			if(city!=null)
				card.setCityName(city.getName());
			card.setBanks(Constants.bankTypes);
		}
		return card;
	}
	
	
	
}
