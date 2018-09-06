package com.edianniu.pscp.cs.service;

import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.mis.bean.user.UserInfo;

import java.util.List;

/**
 * ClassName: CommonAttachmentService
 * Author: tandingbo
 * CreateTime: 2017-09-25 15:11
 */
public interface CommonAttachmentService {

    List<Attachment> structureAttachmentList(Long foreignKey, Integer businessType);

    void deleteAttachmentHelper(String attachmentIdsStr, UserInfo userInfo);

    void saveAttachmentHelper(Long foreignKey, UserInfo userInfo, List<Attachment> attachmentList,
                              Integer businessType) throws RuntimeException;

	void updateAttachmentHelper(List<Attachment> attachmentList, UserInfo userInfo);
}
