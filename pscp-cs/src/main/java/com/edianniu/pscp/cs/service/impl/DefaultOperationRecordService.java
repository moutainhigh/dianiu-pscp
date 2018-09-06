package com.edianniu.pscp.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.cs.bean.OrderIdPrefix;
import com.edianniu.pscp.cs.bean.operationrecord.ListReqData;
import com.edianniu.pscp.cs.bean.operationrecord.OperationRecordInfo;
import com.edianniu.pscp.cs.bean.operationrecord.SaveReqData;
import com.edianniu.pscp.cs.bean.operationrecord.vo.OperationRecordVO;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.OperationRecordDao;
import com.edianniu.pscp.cs.dao.RoomDao;
import com.edianniu.pscp.cs.service.OperationRecordService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.user.UserInfo;

@Service
@Repository("operationRecordService")
@Transactional
public class DefaultOperationRecordService implements OperationRecordService {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultOperationRecordService.class);
	
	@Autowired
	private OperationRecordDao operationRecordDao;
	
	@Autowired
	private RoomDao roomDao;

	/**
	 * 保存和编辑配电房操作记录
	 */
	@Override
	public void save(SaveReqData saveReqData, UserInfo userInfo) {
		if (null == saveReqData || null == userInfo) {
			logger.error("参数不合法");
			return;
		}
		OperationRecordInfo operationRecordInfo = new OperationRecordInfo();
		operationRecordInfo.setRoomId(saveReqData.getRoomId());
		operationRecordInfo.setCompanyId(userInfo.getCompanyId());
		operationRecordInfo.setEquipmentTask(saveReqData.getEquipmentTask());
		operationRecordInfo.setGroundLeadNum(saveReqData.getGroundLeadNum());
		Date startTime = DateUtils.parse(saveReqData.getStartTime(), DateUtils.DATE_PATTERN);
		if (null != startTime) {
			operationRecordInfo.setStartTime(startTime);
		}
		Date endTime = DateUtils.parse(saveReqData.getEndTime(), DateUtils.DATE_PATTERN);
		endTime = DateUtils.getEndDate(endTime);
		if (null != endTime) {
			operationRecordInfo.setEndTime(endTime);
		}
		operationRecordInfo.setIssuingCommand(saveReqData.getIssuingCommand());
		operationRecordInfo.setReceiveCommand(saveReqData.getReceiveCommand());
		operationRecordInfo.setOperator(saveReqData.getOperator());
		operationRecordInfo.setGuardian(saveReqData.getGuardian());
		operationRecordInfo.setRemark(saveReqData.getRemark());
		
		Date date = new Date();
		if (null == saveReqData.getId()) {  // 为空则新增操作记录
			String recordNum = BizUtils.getOrderId(OrderIdPrefix.OPERATION_RECORD_PREFIX);
			operationRecordInfo.setRecordNum(recordNum);
			operationRecordInfo.setCreateUser(userInfo.getLoginName());
			operationRecordInfo.setCreateTime(date);
			operationRecordDao.save(operationRecordInfo);
		} else {                            // 不为空则编辑操作记录
			operationRecordInfo.setId(saveReqData.getId());
			operationRecordInfo.setModifiedUser(userInfo.getLoginName());
			operationRecordInfo.setModifiedTime(date);
			operationRecordDao.update(operationRecordInfo);
		}
	}

	/**
	 * 操作记录列表
	 */
	@Override
	public PageResult<OperationRecordVO> getOperationRecordVOList(ListReqData listReqData, UserInfo userInfo) {
		PageResult<OperationRecordVO> result = new PageResult<>();
		
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("pageSize", listReqData.getPageSize());
		queryMap.put("offset", listReqData.getOffset());
		if (null != userInfo) {
			queryMap.put("companyId", userInfo.getCompanyId());
		}
		if (listReqData.getRoomId() == null || listReqData.getRoomId() == -1) {
			queryMap.put("roomId", null);
		}else {
			queryMap.put("roomId", listReqData.getRoomId());
		}
		
		int total = operationRecordDao.getOperationRecordInfoListCount(queryMap);
		int nextOffset = 0;
		
		if (total > 0) {
			List<OperationRecordVO> operationRecordVOList = new ArrayList<OperationRecordVO>();
			List<OperationRecordInfo> entityList = operationRecordDao.getOperationRecordInfoList(queryMap);
			if (CollectionUtils.isNotEmpty(entityList)) {
				for (OperationRecordInfo operationRecordInfo : entityList) {
					OperationRecordVO operationRecordVO = new OperationRecordVO();
					
					operationRecordVO.setId(operationRecordInfo.getId());
					operationRecordVO.setEquipmentTask(operationRecordInfo.getEquipmentTask());
					operationRecordVO.setRecordNum(operationRecordInfo.getRecordNum());
					Long roomId = operationRecordInfo.getRoomId();
					RoomVO roomVO = roomDao.queryRoomVO(roomId);
					if (null != roomVO) {
						operationRecordVO.setRoomName(roomVO.getName());
					}
					String startTime = DateUtils.format(operationRecordInfo.getStartTime(), DateUtils.DATE_PATTERN);
					String endTime = DateUtils.format(operationRecordInfo.getEndTime(), DateUtils.DATE_PATTERN);
					operationRecordVO.setStartTime(startTime);
					operationRecordVO.setEndTime(endTime);
					operationRecordVOList.add(operationRecordVO);
				}
			}
			
			result.setData(operationRecordVOList);
			nextOffset = listReqData.getOffset() + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		
		result.setOffset(listReqData.getOffset());
		result.setNextOffset(nextOffset);
		result.setTotal(total);
		
		return result;
	}

	/**
	 * 操作记录详情
	 */
	@Override
	public OperationRecordVO getOperationRecordVO(Long id) {
		if (null == id) {
			return null;
		}
		OperationRecordVO operationRecordVO = new OperationRecordVO();
		
		OperationRecordInfo operationRecordInfo = operationRecordDao.getOperationRecordInfo(id);
		if (null == operationRecordInfo) {
			return null;
		}
		operationRecordVO.setId(operationRecordInfo.getId());
		Long roomId = operationRecordInfo.getRoomId();
		operationRecordVO.setRoomId(roomId);
		RoomVO roomVO = roomDao.queryRoomVO(roomId);
		if (null != roomVO) {
			operationRecordVO.setRoomName(roomVO.getName());
		}
		operationRecordVO.setRecordNum(operationRecordInfo.getRecordNum());
		operationRecordVO.setEquipmentTask(operationRecordInfo.getEquipmentTask());
		operationRecordVO.setGroundLeadNum(operationRecordInfo.getGroundLeadNum());
		String startTime = DateUtils.format(operationRecordInfo.getStartTime(), DateUtils.DATE_PATTERN);
		String endTime = DateUtils.format(operationRecordInfo.getEndTime(), DateUtils.DATE_PATTERN);
		operationRecordVO.setStartTime(startTime);
		operationRecordVO.setEndTime(endTime);
		operationRecordVO.setIssuingCommand(operationRecordInfo.getIssuingCommand());
		operationRecordVO.setReceiveCommand(operationRecordInfo.getReceiveCommand());
		operationRecordVO.setOperator(operationRecordInfo.getOperator());
		operationRecordVO.setGuardian(operationRecordInfo.getGuardian());
		operationRecordVO.setRemark(operationRecordInfo.getRemark());
		
		return operationRecordVO;
	}

	/**
	 * 操作记录删除
	 */
	@Override
	public void deleteOperationRecord(Long id, UserInfo userInfo) {
		if (null != id && null != userInfo) {
			OperationRecordInfo operationRecordInfo = new OperationRecordInfo();
			operationRecordInfo.setId(id);
			operationRecordInfo.setDeleted(-1);
			operationRecordInfo.setModifiedTime(new Date());
			operationRecordInfo.setModifiedUser(userInfo.getLoginName());
			operationRecordDao.update(operationRecordInfo);
		}
	}
	
	
	// 判断配电房是否存在操作记录
	@Override
	public Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId){
		HashMap<String, Object> map = new HashMap<>();
		map.put("roomId", roomId);
		map.put("companyId", companyId);
		int count = operationRecordDao.getOperationRecordInfoListCount(map);
		return (count > 0) ? true : false ;
	}

}
