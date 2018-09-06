package com.edianniu.pscp.sps.service.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.room.RoomListResult;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.mis.bean.attachment.common.DeleteReqData;
import com.edianniu.pscp.mis.bean.attachment.common.DeleteResult;
import com.edianniu.pscp.mis.bean.attachment.common.QueryListReqData;
import com.edianniu.pscp.mis.bean.attachment.common.QueryListResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.CommonAttachmentInfoService;
import com.edianniu.pscp.sps.bean.Attachment;
import com.edianniu.pscp.sps.bean.BusinessType;
import com.edianniu.pscp.sps.bean.ProjectStatus;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.dao.EngineeringProjectDao;
import com.edianniu.pscp.sps.dao.SpsCompanyCustomerDao;
import com.edianniu.pscp.sps.domain.CompanyCustomer;
import com.edianniu.pscp.sps.domain.EngineeringProject;
import com.edianniu.pscp.sps.service.EngineeringProjectService;
import com.edianniu.pscp.sps.util.DateUtils;
import com.edianniu.pscp.sps.util.MoneyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultEngineeringProjectService
 * Author: tandingbo
 * CreateTime: 2017-05-15 16:40
 */
@Service
@Repository("engineeringProjectService")
public class DefaultEngineeringProjectService implements EngineeringProjectService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EngineeringProjectDao engineeringProjectDao;

    @Autowired
    private SpsCompanyCustomerDao companyCustomerDao;

    @Autowired
    CommonAttachmentInfoService commonAttachmentInfoService;
    
    @Autowired
    RoomInfoService roomInfoService;

    @Value(value = "${file.download.url}")
    private String picUrl;

    @Override
    public List<EngineeringProject> getListByCompanyIdAndCustomerId(Long companyId, Long customerId, boolean includeExpire) {
        return getEngineeringProjectList(companyId, customerId, includeExpire, null);
    }

    @Override
    public List<ProjectVO> selectProjectVOByCompanyIdAndAndCustomerId(Long companyId, Long customerId, boolean includeExpire) {
        List<ProjectVO> projectVOList = new ArrayList<>();
        List<EngineeringProject> engineeringProjectList = getEngineeringProjectList(companyId, customerId, includeExpire, ProjectStatus.ONGOING.getValue());
        if (CollectionUtils.isNotEmpty(engineeringProjectList)) {
            for (EngineeringProject project : engineeringProjectList) {
                ProjectVO projectVO = new ProjectVO();
                projectVO.setId(project.getId());
                projectVO.setName(project.getName());
                projectVOList.add(projectVO);
            }
        }
        return projectVOList;
    }

    private List<EngineeringProject> getEngineeringProjectList(Long companyId, Long customerId, boolean includeExpire, Integer status) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("status", status);
        queryMap.put("companyId", companyId);
        queryMap.put("customerId", customerId);
        queryMap.put("includeExpire", includeExpire);
        return engineeringProjectDao.getListByCompanyIdAndCustomerId(queryMap);
    }

    @Override
    public ProjectInfo getById(Long id, boolean hasCustomer) {
        ProjectInfo info = this.getById(id);
        if (hasCustomer && info != null) {
            CompanyCustomer customer = companyCustomerDao.queryObject(info.getCustomerId());
            if (customer != null) {
                info.setName(customer.getCpName());
            }
        }
        return info;
    }

    @Override
    public ProjectInfo getById(Long id) {
        ProjectInfo info = engineeringProjectDao.queryObject(id);
        if (info == null) return null;
        
        //查询合作附件
        List<Attachment> attachmentList = structureAttachmentList(id, BusinessType.COOPERATION_ATTACHMENT.getValue());
        if (CollectionUtils.isNotEmpty(attachmentList)) {
            info.setCooperationAttachmentList(attachmentList);
        }
        //查询结算金额附件
        List<Attachment> actualPriceAttachments = structureAttachmentList(id, BusinessType.ACTUAL_PRICE_ATTACHMENT.getValue());
        if (CollectionUtils.isNotEmpty(actualPriceAttachments)) {
            info.setActualPriceAttachmentList(actualPriceAttachments);
        }

        info = getProjectStatusAndType(info);
        info.setStartTimeConvert(DateUtils.format(info.getStartTime(), DateUtils.DATE_PATTERN));
        info.setStartTime(null);
        info.setEndTimeConvert(DateUtils.format(info.getEndTime(), DateUtils.DATE_PATTERN));
        info.setEndTime(null);
        info.setContractFileIds(null);
        
        // 封装配电房信息
        String roomIds = info.getRoomIds();
        if (StringUtils.isNotBlank(roomIds)) {
        	List<Long> ids = new ArrayList<Long>();
			String[] split = roomIds.split(",");
			for (String idStr : split) {
				if (StringUtils.isBlank(idStr)) {
					continue;
				}
				ids.add(Long.valueOf(idStr.trim()));
			}
			RoomListResult listResult = roomInfoService.getRoomsByIds(ids);
			if (listResult.isSuccess()) {
				List<RoomVO> roomList = listResult.getRoomList();
				if (CollectionUtils.isNotEmpty(roomList)) {
					List<HashMap<String, Object>> mapList = new ArrayList<>();
					for (RoomVO roomVO : roomList) {
						HashMap<String, Object> roomMap = new HashMap<>();
						roomMap.put("id", roomVO.getId());
						roomMap.put("name", roomVO.getName());
						mapList.add(roomMap);
					}
					String rooms = JSON.toJSONString(mapList);
					info.setRooms(rooms);
				}
			}
		}
        return info;
    }

    @Override
    public List<ProjectInfo> queryList(Map<String, Object> map) {
        List<ProjectInfo> queryList = engineeringProjectDao.queryList(map);
        for (ProjectInfo projectInfo : queryList) {
            // 获取项目类型和状态
            projectInfo = getProjectStatusAndType(projectInfo);
            projectInfo.setStartTimeConvert(DateUtils.format(projectInfo.getStartTime(), DateUtils.DATE_PATTERN));
            projectInfo.setStartTime(null);
            projectInfo.setEndTimeConvert(DateUtils.format(projectInfo.getEndTime(), DateUtils.DATE_PATTERN));
            projectInfo.setEndTime(null);
            projectInfo.setContractFileIds(null);
        }
        return queryList;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return engineeringProjectDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(ProjectInfo projectInfo, UserInfo userInfo) {
        //保存项目信息
        engineeringProjectDao.save(projectInfo);
        //上传合作附件
        List<Attachment> cooperationAttachmentList = projectInfo.getCooperationAttachmentList();
        if (CollectionUtils.isNotEmpty(cooperationAttachmentList)) {
            Long projectId = projectInfo.getId();
            saveAttachmentHelper(projectId, userInfo, cooperationAttachmentList, BusinessType.COOPERATION_ATTACHMENT.getValue());
        }
    }


    @Override
    @Transactional
    public void update(ProjectInfo engineeringProject, UserInfo userInfo) {
        //更新项目详情
        engineeringProjectDao.update(engineeringProject);

        // 删除附件
        String removedImgs = engineeringProject.getRemovedImgs();
        if (StringUtils.isNotBlank(removedImgs)) {
            deleteAttachmentHelper(removedImgs, userInfo);
        }

        // 保存新附件
        List<Attachment> cooperationAttachmentList = engineeringProject.getCooperationAttachmentList();
        if (CollectionUtils.isNotEmpty(cooperationAttachmentList)) {
            Long projectId = engineeringProject.getId();
            saveAttachmentHelper(projectId, userInfo, cooperationAttachmentList, BusinessType.COOPERATION_ATTACHMENT.getValue());
        }
    }

    @Override
    @Transactional
    public void settle(ProjectInfo projectInfo, UserInfo userInfo) {
        // 更新项目详情
        Double actualSettlementAmount = projectInfo.getActualSettlementAmount();
        if (null != actualSettlementAmount) {
            actualSettlementAmount = MoneyUtils.formatToMoney(actualSettlementAmount);
            projectInfo.setActualSettlementAmount(actualSettlementAmount);
        }
        engineeringProjectDao.update(projectInfo);

        // 删除要删除的结算附件
        String removedImgs = projectInfo.getRemovedImgs();
        if (StringUtils.isNotBlank(removedImgs)) {
            deleteAttachmentHelper(removedImgs, userInfo);
        }
        // 保存新添加进来的结算附件
        List<Attachment> actualPriceAttachmentList = projectInfo.getActualPriceAttachmentList();
        if (CollectionUtils.isNotEmpty(actualPriceAttachmentList)) {
            Long projectId = projectInfo.getId();
            saveAttachmentHelper(projectId, userInfo, actualPriceAttachmentList, BusinessType.ACTUAL_PRICE_ATTACHMENT.getValue());
        }
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return engineeringProjectDao.delete(id);
    }

    @Override
    @Transactional
    public int deleteBatch(Long[] ids) {
        return engineeringProjectDao.deleteBatch(ids);
    }

    @Override
    public ProjectInfo getProjectInfoByNeedsId(Map<String, Object> map) {
        return engineeringProjectDao.getProjectInfoByNeedsId(map);
    }

    @Override
    public boolean existByCustomerId(Long customerId) {
        int count = engineeringProjectDao.getCountByCustomerId(customerId);
        return count > 0 ? true : false;
    }

    @Override
    public boolean existByCompanyIdAndName(Long companyId, String name) {
        return engineeringProjectDao.getCountByCompanyIdAndName(companyId, name) > 0 ? true : false;
    }

    @Override
    public boolean existByCompanyIdAndIdAndName(Long companyId, Long id, String name) {
        return engineeringProjectDao.getCountByCompanyIdAndIdAndName(companyId, id, name) > 0 ? true : false;
    }

    /**
     * 获取附件信息
     *
     * @param foreignKey
     * @param businessType
     * @return
     */
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
     * 保存附件方法
     *
     * @param foreignKey
     * @param userInfo
     * @param attachmentList
     * @throws RuntimeException
     */
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
     * 删除附件
     *
     * @param attachmentIdsStr
     * @param userInfo
     */
    public void deleteAttachmentHelper(String attachmentIdsStr, UserInfo userInfo) {
        if (StringUtils.isBlank(attachmentIdsStr) || null == userInfo) {
            return;
        }
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
     * 获取项目状态、判断项目类型
     *
     * @param projectInfo
     * @return
     */
    private ProjectInfo getProjectStatusAndType(ProjectInfo projectInfo) {
        if (projectInfo.getStatus().equals(ProjectStatus.CONFIRMING.getValue())) {
            projectInfo.setGoStatus("未启动");
        }
        if (projectInfo.getStatus().equals(ProjectStatus.ONGOING.getValue()) ||
                projectInfo.getStatus().equals(ProjectStatus.CONFIRM_COST.getValue())) {
            projectInfo.setGoStatus("进行中");
        }
        if (null == projectInfo.getNeedsId()) {
            //需求ID为空，则表示为服务商自己创建的项目
            projectInfo.setIsCreateBySelf(1);
            if (projectInfo.getStatus().equals(ProjectStatus.CANCLED.getValue()) ||
                    projectInfo.getStatus().equals(ProjectStatus.SETTLED.getValue())) {
                projectInfo.setGoStatus("已结束");
            }
        } else {
            //需求ID不为空，则表示需求转换的项目
            projectInfo.setIsCreateBySelf(2);
            if (projectInfo.getStatus().equals(ProjectStatus.CANCLED.getValue())) {
                projectInfo.setGoStatus("已取消");
            }
            if (projectInfo.getStatus().equals(ProjectStatus.SETTLED.getValue())) {
                projectInfo.setGoStatus("已结算");
            }
        }
        return projectInfo;
    }

	@Override
	@Transactional
	public void batchUpdatePayment(List<ProjectInfo> projectList) {
		engineeringProjectDao.batchUpdatePayment(projectList);
	}

	
}
