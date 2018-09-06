package com.edianniu.pscp.cs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.cs.bean.dutylog.DutyLogInfo;
import com.edianniu.pscp.cs.bean.dutylog.ListReqData;
import com.edianniu.pscp.cs.bean.dutylog.SaveReqData;
import com.edianniu.pscp.cs.bean.dutylog.vo.DutyLogVO;
import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.DutyLogDao;
import com.edianniu.pscp.cs.dao.RoomDao;
import com.edianniu.pscp.cs.service.CommonAttachmentService;
import com.edianniu.pscp.cs.service.DutyLogService;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.attachment.common.BusinessType;
import com.edianniu.pscp.mis.bean.user.UserInfo;

@Service
@Repository("dutyLogService")
@Transactional
public class DefaultDutyLogService implements DutyLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultDutyLogService.class);
	
	@Autowired
	private DutyLogDao dutyLogDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private CommonAttachmentService commonAttachmentService;

	/**
	 * 配电房客户值班日志新增、编辑
	 */
	@Override
	public void save(SaveReqData saveReqData, UserInfo userInfo) {
		if (null == saveReqData || null == userInfo) {
			logger.error("参数不合法");
			return;
		}
		DutyLogInfo dutyLogInfo = new DutyLogInfo();
		dutyLogInfo.setRoomId(saveReqData.getRoomId());
		dutyLogInfo.setCompanyId(userInfo.getCompanyId());
		dutyLogInfo.setTitle(saveReqData.getTitle());
		dutyLogInfo.setContent(saveReqData.getContent());
		
		Date date = new Date();
		if (null == saveReqData.getId()) {  //新增值班日志
			dutyLogInfo.setCreateUser(userInfo.getLoginName());
			dutyLogInfo.setCreateTime(date);
			dutyLogDao.save(dutyLogInfo);
		} else {                           // 编辑值班日志
			dutyLogInfo.setId(saveReqData.getId());
			dutyLogInfo.setModifiedUser(userInfo.getLoginName());
			dutyLogInfo.setModifiedTime(date);
			dutyLogDao.update(dutyLogInfo);
			
			// 删除附件
			String removedImgs = saveReqData.getRemovedImgs();
			if (StringUtils.isNotBlank(removedImgs)) {
				commonAttachmentService.deleteAttachmentHelper(removedImgs, userInfo);
			}
		}
		
		// 保存附件
		List<Attachment> attachmentList = saveReqData.getAttachmentList();
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			commonAttachmentService.saveAttachmentHelper(dutyLogInfo.getId(), userInfo, attachmentList, BusinessType.DUTY_LOG_ATTACHMENT.getValue());
		}
		
	}

	/**
	 * 值班日志列表
	 */
	@Override
	public PageResult<DutyLogVO> getDutyLogVOList(ListReqData listReqData, UserInfo userInfo) {
		PageResult<DutyLogVO> result = new PageResult<>();
		
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
		
		int total = dutyLogDao.getDutyLogVOListCount(queryMap);
		int nextOffset = 0;
		
		if (total > 0) {
			List<DutyLogVO> dutyLogVOList = new ArrayList<>();
			List<DutyLogInfo> entityList = dutyLogDao.getDutyLogVOList(queryMap);
			if (CollectionUtils.isNotEmpty(entityList)) {
				for (DutyLogInfo dutyLogInfo : entityList) {
					DutyLogVO dutyLogVO = new DutyLogVO();
					dutyLogVO.setId(dutyLogInfo.getId());
					dutyLogVO.setTitle(dutyLogInfo.getTitle());
					dutyLogVO.setCreateTime(DateUtils.format(dutyLogInfo.getCreateTime(), DateUtils.DATE_PATTERN));
					Long roomId = dutyLogInfo.getRoomId();
					RoomVO roomVO = roomDao.queryRoomVO(roomId);
					if (null != roomVO) {
						dutyLogVO.setRoomName(roomVO.getName());
					}
					dutyLogVOList.add(dutyLogVO);
				}
			}
			result.setData(dutyLogVOList);
			nextOffset = listReqData.getOffset() + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total ) {
			result.setHasNext(true);
		}
		result.setOffset(listReqData.getOffset());
		result.setNextOffset(nextOffset);
		result.setTotal(total);
		
		return result;
	}

	/**
	 * 值班日志详情
	 */
	@Override
	public DutyLogVO getDutyLogVO(Long id) {
		if (null == id) {
			return null;
		}
		DutyLogVO dutyLogVO = new DutyLogVO();
		DutyLogInfo dutyLogInfo = dutyLogDao.getDutyLogInfo(id);
		if (null == dutyLogInfo) {
			return null;
		}
		dutyLogVO.setId(dutyLogInfo.getId());
		dutyLogVO.setTitle(dutyLogInfo.getTitle());
		dutyLogVO.setContent(dutyLogInfo.getContent());
		dutyLogVO.setCreateTime(DateUtils.format(dutyLogInfo.getCreateTime(), DateUtils.DATE_PATTERN));
		Long roomId = dutyLogInfo.getRoomId();
		dutyLogVO.setRoomId(roomId);
		RoomVO roomVO = roomDao.queryRoomVO(roomId);
		if (null != roomVO) {
			dutyLogVO.setRoomName(roomVO.getName());
		}
		//获取值班日志附件
		List<Attachment> attachmentList = commonAttachmentService.structureAttachmentList(dutyLogInfo.getId(), BusinessType.DUTY_LOG_ATTACHMENT.getValue());
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			dutyLogVO.setAttachmentList(attachmentList);
		}
		return dutyLogVO;
	}

	/**
	 * 值班日志删除
	 */
	@Override
	public void deleteDutyLog(Long id, UserInfo userInfo) {
		if (null != id) {
			DutyLogInfo dutyLogInfo = new DutyLogInfo();
			dutyLogInfo.setId(id);
			dutyLogInfo.setDeleted(-1);
			dutyLogInfo.setModifiedTime(new Date());
			dutyLogInfo.setModifiedUser(userInfo.getLoginName());
			dutyLogDao.update(dutyLogInfo);
		}
	}
	
	// 判断配电房是否存在值班日志
	@Override
	public Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId){
		HashMap<String, Object> map = new HashMap<>();
		map.put("roomId", roomId);
		map.put("companyId", companyId);
		int count = dutyLogDao.getDutyLogVOListCount(map);
		return (count > 0) ? true : false ;
	}
	

}
