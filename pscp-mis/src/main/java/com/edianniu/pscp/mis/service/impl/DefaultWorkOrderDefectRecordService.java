package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.attachment.common.AttachmentQuery;
import com.edianniu.pscp.mis.bean.attachment.common.BusinessType;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordDetailsVO;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.query.DefectRecordQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.WorkOrderDefectRecordDao;
import com.edianniu.pscp.mis.domain.CommonAttachment;
import com.edianniu.pscp.mis.domain.WorkOrderDefectRecord;
import com.edianniu.pscp.mis.service.CommonAttachmentService;
import com.edianniu.pscp.mis.service.WorkOrderDefectRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultWorkOrderDefectRecordService
 * Author: tandingbo
 * CreateTime: 2017-09-12 10:35
 */
@Service
@Repository("workOrderDefectRecordService")
public class DefaultWorkOrderDefectRecordService implements WorkOrderDefectRecordService {

    @Autowired
    private WorkOrderDefectRecordDao workOrderDefectRecordDao;
    
    @Autowired
    private CommonAttachmentService commonAttachmentService;

    @Override
    public PageResult<DefectRecordVO> queryList(DefectRecordQuery defectRecordQuery) {
        PageResult<DefectRecordVO> result = new PageResult<DefectRecordVO>();
        int total = workOrderDefectRecordDao.queryDefectRecordVOCount(defectRecordQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<DefectRecordVO> list = workOrderDefectRecordDao.queryListDefectRecordVO(defectRecordQuery);

            result.setData(list);
            nextOffset = defectRecordQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }
        result.setOffset(defectRecordQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    @Transactional
    @Override
    public void save(WorkOrderDefectRecord defectRecord, UserInfo userInfo) {
    	// 保存缺陷记录信息
        workOrderDefectRecordDao.save(defectRecord);
        // 保存缺陷记录附件
        List<CommonAttachment> attachmentList = defectRecord.getAttachmentList();
        if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (CommonAttachment commonAttachment : attachmentList) {
				Long orderNum = commonAttachment.getOrderNum() == null ? 0L : commonAttachment.getOrderNum();
				commonAttachment.setOrderNum(orderNum);
				commonAttachment.setBusinessType(BusinessType.DEFECT_RECORD_ATTACHMENT.getValue());
				commonAttachment.setCompanyId(userInfo.getCompanyId());
				commonAttachment.setMemberId(userInfo.getUid());
				commonAttachment.setForeignKey(defectRecord.getId());
				commonAttachment.setIsOpen(Constants.TAG_YES);
				commonAttachment.setType(1);
			}
			commonAttachmentService.saveBatchEntity(attachmentList);
		}
    }

    @Override
    public WorkOrderDefectRecord getEntityById(Long id) {
        return workOrderDefectRecordDao.getEntityById(id);
    }

    @Transactional
    @Override
    public void updateSolveInfo(WorkOrderDefectRecord defectRecord, UserInfo userInfo) {
    	// 保存整改信息
        workOrderDefectRecordDao.updateSolveInfo(defectRecord);
        // 保存整改附件
        List<CommonAttachment> solveAttachmentList = defectRecord.getSolveAttachmentList();
        if (CollectionUtils.isNotEmpty(solveAttachmentList)) {
			for (CommonAttachment commonAttachment : solveAttachmentList) {
				Long orderNum = commonAttachment.getOrderNum() == null ? 0L : commonAttachment.getOrderNum();
				commonAttachment.setOrderNum(orderNum);
				commonAttachment.setBusinessType(BusinessType.SOLVE_DEFECT_RECORD_ATTACHMENT.getValue());
				commonAttachment.setCompanyId(userInfo.getCompanyId());
				commonAttachment.setMemberId(userInfo.getUid());
				commonAttachment.setForeignKey(defectRecord.getId());
				commonAttachment.setIsOpen(Constants.TAG_YES);
				commonAttachment.setType(1);
			}
			commonAttachmentService.saveBatchEntity(solveAttachmentList);
		}
    }

    @Transactional
    @Override
    public void updateMapCondition(Map<String, Object> updateMap) {
        workOrderDefectRecordDao.updateMapCondition(updateMap);
    }

    /**
     * 获取修复缺陷记录
     *
     * @param ids
     * @return
     */
    @Override
    public List<DefectRecordVO> getRepairDefectRecord(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids) || ids.size() < 1) {
            return new ArrayList<>();
        }

        List<DefectRecordVO> list = workOrderDefectRecordDao.getRepairDefectRecord(ids);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public DefectRecordDetailsVO getDefectRecordVOById(Long id) {
    	DefectRecordDetailsVO defectRecordVO = workOrderDefectRecordDao.getDefectRecordVOById(id);
    	
		// 缺陷记录附件
    	List<Map<String, Object>> attachments = getAttachments(defectRecordVO.getId(), BusinessType.DEFECT_RECORD_ATTACHMENT.getValue());
    	defectRecordVO.setAttachmentList(attachments);
    	
    	// 整改附件
    	List<Map<String, Object>> attachments2 = getAttachments(defectRecordVO.getId(), BusinessType.SOLVE_DEFECT_RECORD_ATTACHMENT.getValue());
    	defectRecordVO.setSolveAttachmentList(attachments2);;
    	
        return defectRecordVO;
    }
    
    /**
     * 查询附件
     * @param foreignKey
     * @param businessType
     * @return
     */
    public List<Map<String, Object>> getAttachments(Long foreignKey, Integer businessType){
    	List<Map<String, Object>> attachements = null;
    	AttachmentQuery attachmentQuery = new AttachmentQuery();
    	attachmentQuery.setForeignKey(foreignKey);
    	attachmentQuery.setBusinessType(businessType);
    	List<CommonAttachment> list = commonAttachmentService.queryListAttachment(attachmentQuery);
    	if (CollectionUtils.isNotEmpty(list)) {
    		attachements = new ArrayList<>();
            for (CommonAttachment attachment : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("fid", attachment.getFid());
                map.put("orderNum", attachment.getOrderNum());
                attachements.add(map);
            }
		}
    	return attachements;
    }

    @Override
    public List<DefectRecordVO> queryListDefectRecord(Map<String, Object> param) {
        return workOrderDefectRecordDao.queryListDefectRecord(param);
    }
}
