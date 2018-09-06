package com.edianniu.pscp.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.dao.SmsTemplateDao;
import com.edianniu.pscp.cms.entity.SmsTemplateEntity;
import com.edianniu.pscp.cms.service.SmsTemplateService;
import com.edianniu.pscp.message.commons.MessageId;



@Service("smsTemplateService")
public class SmsTemplateServiceImpl implements SmsTemplateService {
	@Autowired
	private SmsTemplateDao smsTemplateDao;
	
	@Override
	public SmsTemplateEntity queryObject(Long id){
		SmsTemplateEntity bean=smsTemplateDao.queryObject(id);
		bean.setMsgValue(getMsgValue(bean.getMsgId()));
		return bean;
	}
	
	@Override
	public List<SmsTemplateEntity> queryList(Map<String, Object> map){
		List<SmsTemplateEntity>list=smsTemplateDao.queryList(map);
		for(SmsTemplateEntity temp:list){
			temp.setMsgValue(getMsgValue(temp.getMsgId()));
		}
		return list;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smsTemplateDao.queryTotal(map);
	}
	
	@Override
	public void save(SmsTemplateEntity smsTemplate){
		smsTemplateDao.save(smsTemplate);
	}
	
	@Override
	public void update(SmsTemplateEntity smsTemplate){
		smsTemplateDao.update(smsTemplate);
	}
	
	@Override
	public void delete(Long id){
		smsTemplateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		smsTemplateDao.deleteBatch(ids);
	}
	
	private String getMsgValue(String msgId){
		String desc="";
		if(msgId!=null){
			for(MessageId msg:MessageId.values()){
				if(msg.getValue().equals(msgId)){
					desc=msg.getDesc();
					break;
				}
				
			}
		}
		return desc;
	}
}
