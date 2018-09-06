package com.edianniu.pscp.message.msg.service;

import java.util.List;

import com.edianniu.pscp.message.msg.domain.MemberMessage;

/**
 * ClassName: MemberMessageService
 * Author: tandingbo
 * CreateTime: 2017-05-02 11:38
 */
public interface MemberMessageService {
    int save(MemberMessage entity);
    
    int update(MemberMessage entity);
    
    List<MemberMessage> queryElectricianInvitationMessage(Long uid,String invitationId);
    
    List<MemberMessage> queryCompanyInvitationMessage(Long uid,String invitationId);
    
    List<MemberMessage> queryUnBundInvitationMessage(Long uid,String invitationId);
}
