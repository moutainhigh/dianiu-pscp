package com.edianniu.pscp.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.dao.SmsSendLogDao;
import com.edianniu.pscp.cms.entity.SmsSendLogEntity;
import com.edianniu.pscp.cms.service.SmsSendLogService;
import com.edianniu.pscp.message.commons.MessageId;



@Service("smsSendLogService")
public class SmsSendLogServiceImpl implements SmsSendLogService {
	@Autowired
	private SmsSendLogDao smsSendLogDao;
	
	@Override
	public SmsSendLogEntity queryObject(Long id){
		 SmsSendLogEntity bean=smsSendLogDao.queryObject(id);
		 bean.setMsgValue(getMsgValue(bean.getMsgId()));
		return bean;
	}
	
	@Override
	public List<SmsSendLogEntity> queryList(Map<String, Object> map){
		List<SmsSendLogEntity>list=smsSendLogDao.queryList(map);
		if(!list.isEmpty()&&list.size()>0){
			for(SmsSendLogEntity bean:list){
				bean.setMsgValue(getMsgValue(bean.getMsgId()));
			}
		}
		
		return list;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smsSendLogDao.queryTotal(map);
	}
	
	
	@Override
	public void update(SmsSendLogEntity smsSendLog){
		smsSendLogDao.update(smsSendLog);
	}
	
	@Override
	public void delete(Long id){
		smsSendLogDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		smsSendLogDao.deleteBatch(ids);
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
