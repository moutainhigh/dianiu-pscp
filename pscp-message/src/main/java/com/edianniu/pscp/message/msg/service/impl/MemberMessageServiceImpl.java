package com.edianniu.pscp.message.msg.service.impl;

import java.util.List;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.msg.dao.MemberMessageDao;
import com.edianniu.pscp.message.msg.domain.MemberMessage;
import com.edianniu.pscp.message.msg.service.MemberMessageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: MemberMessageServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-02 11:38
 */
@Service
@Repository("memberMessageService")
public class MemberMessageServiceImpl implements MemberMessageService {
    private static final Logger logger = LoggerFactory.getLogger(MemberMessageServiceImpl.class);

    @Autowired
    private MemberMessageDao memberMessageDao;

    @Override
    @Transactional
    public int save(MemberMessage entity) {
        return memberMessageDao.saveEntity(entity);
    }
	@Override
	public int update(MemberMessage entity) {
		
		return memberMessageDao.updateEntity(entity);
	}
	@Override
	public List<MemberMessage> queryElectricianInvitationMessage(Long uid,
			String invitationId) {
		return memberMessageDao.getUserMsgsByUidAndCategoryAndInvitationId(uid, MessageId.ELECTRICIAN_INVITATION.getValue(), invitationId);
	}
	@Override
	public List<MemberMessage> queryCompanyInvitationMessage(Long uid,
			String invitationId) {
		return memberMessageDao.getUserMsgsByUidAndCategoryAndInvitationId(uid, MessageId.COMPANY_INVITATION.getValue(), invitationId);
	}
	@Override
	public List<MemberMessage> queryUnBundInvitationMessage(Long uid,
			String invitationId) {
		return memberMessageDao.getUserMsgsByUidAndCategoryAndInvitationId(uid, MessageId.APPLY_ELECTRICIAN_UNBUND.getValue(), invitationId);
	}
}
