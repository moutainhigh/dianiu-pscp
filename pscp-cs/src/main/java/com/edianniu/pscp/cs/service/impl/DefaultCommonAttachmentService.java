package com.edianniu.pscp.cs.service.impl;

import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.service.CommonAttachmentService;
import com.edianniu.pscp.mis.bean.attachment.common.DeleteReqData;
import com.edianniu.pscp.mis.bean.attachment.common.DeleteResult;
import com.edianniu.pscp.mis.bean.attachment.common.QueryListReqData;
import com.edianniu.pscp.mis.bean.attachment.common.QueryListResult;
import com.edianniu.pscp.mis.bean.attachment.common.UpdateReqData;
import com.edianniu.pscp.mis.bean.attachment.common.UpdateResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.CommonAttachmentInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultCommonAttachmentService
 * Author: tandingbo
 * CreateTime: 2017-09-25 15:11
 */
@Service
@Repository("commonAttachmentService")
public class DefaultCommonAttachmentService implements CommonAttachmentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    @Qualifier("commonAttachmentInfoService")
    private CommonAttachmentInfoService commonAttachmentInfoService;

    /**
     * 获取附件信息
     *
     * @param foreignKey
     * @param businessType
     * @return
     */
    @Override
    public List<Attachment> structureAttachmentList(Long foreignKey, Integer businessType) {
        List<Attachment> attachmentList = new ArrayList<Attachment>();
        QueryListReqData reqData = new QueryListReqData();
        reqData.setForeignKey(foreignKey);
        reqData.setBusinessType(businessType);
        QueryListResult queryListResult = commonAttachmentInfoService.queryListAttachment(reqData);
        if (queryListResult.isSuccess()) {
            List<Map<String, Object>> attachmentListMap = queryListResult.getAttachmentList();
            for (Map<String, Object> map : attachmentListMap) {
                Attachment attachment = new Attachment();
                attachment.setFid(map.get("fid").toString());
                attachment.setId(Long.parseLong(map.get("id").toString()));
                attachment.setIsOpen(Integer.valueOf(map.get("isOpen").toString()));
                attachment.setOrderNum(Integer.valueOf(map.get("orderNum").toString()));
                attachmentList.add(attachment);
            }
        }
        return attachmentList;
    }

    /**
     * 删除附件
     *
     * @param attachmentIdsStr
     * @param userInfo
     */
    @Override
    public void deleteAttachmentHelper(String attachmentIdsStr, UserInfo userInfo) {
        List<Long> attachmentIds = new ArrayList<>();
        String[] removedImg = attachmentIdsStr.split(",");
        for (String id : removedImg) {
            attachmentIds.add(Long.valueOf(id));
        }

        // 删除附件
        DeleteReqData reqData = new DeleteReqData();
        reqData.setUid(userInfo.getUid());
        reqData.setAttachmentIds(attachmentIds);
        DeleteResult deleteResult = commonAttachmentInfoService.deleteBatchEntity(reqData);
        if (!deleteResult.isSuccess()) {
            logger.error("删除附件错误:{}", deleteResult.getResultMessage());
            throw new RuntimeException("删除附件错误!");
        }
    }

    /**
     * 保存附件方法
     *
     * @param foreignKey
     * @param userInfo
     * @param attachmentList
     * @throws RuntimeException
     */
    @Override
    public void saveAttachmentHelper(Long foreignKey, UserInfo userInfo, List<Attachment> attachmentList,
                                     Integer businessType) throws RuntimeException {
        List<Map<String, Object>> attachmentListMap = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(attachmentList)) {
            for (Attachment att : attachmentList) {
                Map<String, Object> map = new HashMap<>();
                map.put("fid", att.getFid());
                map.put("foreignKey", foreignKey);
                map.put("memberId", userInfo.getUid());
                map.put("companyId", userInfo.getCompanyId());
                map.put("businessType", businessType);
                map.put("orderNum", (att.getOrderNum() == null) ? 0 : att.getOrderNum());
                attachmentListMap.add(map);
            }

            // 保存附件信息
            com.edianniu.pscp.mis.bean.attachment.common.SaveReqData reqData = new com.edianniu.pscp.mis.bean.attachment.common.SaveReqData();
            reqData.setUid(userInfo.getUid());
            reqData.setAttachmentList(attachmentListMap);
            com.edianniu.pscp.mis.bean.attachment.common.SaveResult saveResult = commonAttachmentInfoService
                    .saveBatchEntity(reqData);
            if (!saveResult.isSuccess()) {
                logger.error("附件保存错误：{}", saveResult.getResultMessage());
                throw new RuntimeException("附件保存错误!");
            }
        }
    }
    
    /**
     * 修改附件
     * @param foreignKey
     * @param userInfo
     * @param attachmentList
     * @param businessType
     */
    public void updateAttachmentHelper(List<Attachment> attachmentList, UserInfo userInfo){
    	List<Map<String, Object>> attachmentMapList = new ArrayList<>();
    	if (CollectionUtils.isNotEmpty(attachmentList)) {
    		for (Attachment att : attachmentList) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("id", att.getId());
				map.put("isOpen", att.getIsOpen());
				attachmentMapList.add(map);
			}
    		UpdateReqData updateReqData = new UpdateReqData();
    		updateReqData.setAttachmentList(attachmentMapList);
    		updateReqData.setUid(userInfo.getUid());
    		//修改附件
    		UpdateResult updateResult = commonAttachmentInfoService.updateBatchEntity(updateReqData);
    		if (!updateResult.isSuccess()) {
                logger.error("附件修改错误：{}", updateResult.getResultMessage());
                throw new RuntimeException("附件修改错误!");
            }
		}
    }

}
