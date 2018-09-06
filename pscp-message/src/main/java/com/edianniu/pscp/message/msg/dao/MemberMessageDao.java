package com.edianniu.pscp.message.msg.dao;

import com.edianniu.pscp.message.msg.domain.MemberMessage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: MemberMessageDao
 * Author: tandingbo
 * CreateTime: 2017-05-02 11:40
 */
@Mapper
public interface MemberMessageDao {
    int saveEntity(MemberMessage entity);

    int updateEntity(MemberMessage entity);

    List<MemberMessage> getUserMsgsByUidAndCategoryAndExt(@Param("uid") Long uid, @Param("category") String category, @Param("ext") String ext);
    
    List<MemberMessage> getUserMsgsByUidAndCategoryAndInvitationId(@Param("uid") Long uid, @Param("category") String category, @Param("invitationId") String invitationId);

    List<MemberMessage> getUserMsgsByUidAndCategory(@Param("uid") Long uid, @Param("category") String category);
}
