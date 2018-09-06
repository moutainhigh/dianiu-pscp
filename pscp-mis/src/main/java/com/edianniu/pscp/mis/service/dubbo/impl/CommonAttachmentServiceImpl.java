package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.attachment.common.*;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.CommonAttachment;
import com.edianniu.pscp.mis.service.CommonAttachmentService;
import com.edianniu.pscp.mis.service.dubbo.CommonAttachmentInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-19 11:04:25
 */
@Service
@Repository("commonAttachmentInfoService")
public class CommonAttachmentServiceImpl implements CommonAttachmentInfoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("commonAttachmentService")
    private CommonAttachmentService commonAttachmentService;

    /**
     * 根据ID获取附件信息
     *
     * @param reqData
     * @return
     */
    @Override
    public QueryOneResult getAttachmentById(QueryOneReqData reqData) {
        QueryOneResult result = new QueryOneResult();
        try {
            if (reqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("附件标识不能为空");
                return result;
            }

            Map<String, Object> attachmentMap = new HashMap<>();
            CommonAttachment attachment = commonAttachmentService.getAttachmentById(reqData.getId());
            if (attachment != null) {
                attachmentMap = structureMapAttachment(attachment);
            }
            result.setAttachment(attachmentMap);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 根据条件获取附件信息
     *
     * @param reqData
     * @return
     */
    @Override
    public QueryListResult queryListAttachment(QueryListReqData reqData) {
        QueryListResult result = new QueryListResult();
        try {
            if (reqData.getForeignKey() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("查询条件foreignKey不能为空");
                return result;
            }

            AttachmentQuery attachmentQuery = new AttachmentQuery();
            BeanUtils.copyProperties(reqData, attachmentQuery);
            List<CommonAttachment> attachmentList = commonAttachmentService.queryListAttachment(attachmentQuery);
            List<Map<String, Object>> attachmentListMap = new ArrayList<>();
            if (attachmentList != null) {
                for (CommonAttachment commonAttachment : attachmentList) {
                    attachmentListMap.add(structureMapAttachment(commonAttachment));
                }
            }
            result.setAttachmentList(attachmentListMap);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    private Map<String, Object> structureMapAttachment(CommonAttachment commonAttachment) {
        Map<String, Object> attachmentMap = new HashMap<>();
        attachmentMap.put("id", commonAttachment.getId() == null ? 0L : commonAttachment.getId());
        attachmentMap.put("fid", commonAttachment.getFid() == null ? "" : commonAttachment.getFid());
        attachmentMap.put("foreignKey", commonAttachment.getForeignKey() == null ? 0L : commonAttachment.getForeignKey());
        attachmentMap.put("companyId", commonAttachment.getCompanyId() == null ? 0L : commonAttachment.getCompanyId());
        attachmentMap.put("memberId", commonAttachment.getMemberId() == null ? 0L : commonAttachment.getMemberId());
        attachmentMap.put("businessType", commonAttachment.getBusinessType() == null ? 0 : commonAttachment.getBusinessType());
        attachmentMap.put("type", commonAttachment.getType() == null ? 0 : commonAttachment.getType());
        attachmentMap.put("orderNum", commonAttachment.getOrderNum() == null ? 0L : commonAttachment.getOrderNum());
        attachmentMap.put("isOpen", commonAttachment.getIsOpen() == null ? 0 : commonAttachment.getIsOpen());
        attachmentMap.put("deleted", commonAttachment.getDeleted());
        return attachmentMap;
    }

    /**
     * 根据ID获取附件信息
     *
     * @param reqData
     * @return
     */
    @Override
    public SaveResult saveBatchEntity(SaveReqData reqData) {
        SaveResult result = new SaveResult();
        try {
            if (reqData.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            // 当前登录用户
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            List<Map<String, Object>> attachmentListMap = reqData.getAttachmentList();
            if (CollectionUtils.isEmpty(attachmentListMap)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("附件信息不能为空");
                return result;
            }

            List<CommonAttachment> attachmentList = new ArrayList<>();
            for (Map<String, Object> map : attachmentListMap) {
                CommonAttachment attachment = new CommonAttachment();
                if (map.get("fid") == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("附件信息不能为空");
                    return result;
                }
                attachment.setFid(map.get("fid").toString());

                if (map.get("foreignKey") == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("关联信息不能为空");
                    return result;
                }
                attachment.setForeignKey(Long.valueOf(map.get("foreignKey").toString()));

                attachment.setBusinessType(0);
                if (map.get("businessType") != null) {
                    attachment.setBusinessType(Integer.valueOf(map.get("businessType").toString()));
                }

                attachment.setOrderNum(0L);
                if (map.get("orderNum") != null) {
                    attachment.setOrderNum(Long.valueOf(map.get("orderNum").toString()));
                }
                attachment.setIsOpen(0);
                if (map.get("isOpen") != null) {
                    attachment.setIsOpen(Integer.valueOf(map.get("isOpen").toString()));
                }
                if (map.get("companyId") != null) {
                    attachment.setCompanyId(Long.valueOf(map.get("companyId").toString()));
                }
                if (map.get("memberId") != null) {
                    attachment.setMemberId(Long.valueOf(map.get("memberId").toString()));
                }

                attachment.setType(1);
                attachment.setDeleted(0);
                attachment.setCreateUser(userInfo.getLoginName());
                attachmentList.add(attachment);
            }

            if (!result.isSuccess() || CollectionUtils.isEmpty(attachmentList)) {
                return result;
            }

            // 批量保存附件信息
            commonAttachmentService.saveBatchEntity(attachmentList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 删除附件信息
     *
     * @param reqData
     * @return
     */
    @Override
    public DeleteResult deleteBatchEntity(DeleteReqData reqData) {
        DeleteResult result = new DeleteResult();
        try {
            if (reqData.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            // 当前登录用户
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            if (CollectionUtils.isEmpty(reqData.getAttachmentIds())) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("附件信息不能为空");
                return result;
            }

            commonAttachmentService.deleteBatchEntity(reqData.getAttachmentIds(), userInfo.getLoginName());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 批量修改附件信息
     *
     * @param reqData
     * @return
     */
    @Override
    public UpdateResult updateBatchEntity(UpdateReqData reqData) {
        UpdateResult result = new UpdateResult();
        try {
            if (reqData.getUid() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }
            // 当前登录用户
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            List<Map<String, Object>> attachmentListMap = reqData.getAttachmentList();
            if (CollectionUtils.isEmpty(attachmentListMap)) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("附件信息不能为空");
                return result;
            }

            List<CommonAttachment> attachmentList = new ArrayList<>();
            for (Map<String, Object> map : attachmentListMap) {
                if (map.get("isOpen") == null && map.get("orderNum") == null) {
                    continue;
                }

                CommonAttachment attachment = new CommonAttachment();
                if (map.get("id") == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("附件ID不能为空");
                    return result;
                }
                String attachmentIdStr = map.get("id").toString();
                if(!attachmentIdStr.matches("\\d+")){
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("附件ID格式不正确");
                    return result;
                }
                attachment.setId(Long.valueOf(map.get("id").toString()));

                if (map.get("isOpen") != null) {
                    attachment.setIsOpen(Integer.valueOf(map.get("isOpen").toString()));
                }
                if (map.get("orderNum") != null) {
                    attachment.setOrderNum(Long.valueOf(map.get("orderNum").toString()));
                }

                attachment.setModifiedTime(new Date());
                attachment.setModifiedUser(userInfo.getLoginName());
                attachmentList.add(attachment);
            }

            if (!result.isSuccess() || CollectionUtils.isEmpty(attachmentList)) {
                return result;
            }

            commonAttachmentService.updateBatchEntity(attachmentList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }
}
