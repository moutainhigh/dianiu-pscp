package com.edianniu.pscp.cs.service.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cs.bean.dutylog.DeleteReqData;
import com.edianniu.pscp.cs.bean.dutylog.DeleteResult;
import com.edianniu.pscp.cs.bean.dutylog.DetailsReqData;
import com.edianniu.pscp.cs.bean.dutylog.DetailsResult;
import com.edianniu.pscp.cs.bean.dutylog.ListReqData;
import com.edianniu.pscp.cs.bean.dutylog.ListResult;
import com.edianniu.pscp.cs.bean.dutylog.SaveReqData;
import com.edianniu.pscp.cs.bean.dutylog.SaveResult;
import com.edianniu.pscp.cs.bean.dutylog.vo.DutyLogVO;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.DutyLogService;
import com.edianniu.pscp.cs.service.RoomService;
import com.edianniu.pscp.cs.service.dubbo.DutyLogInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

@Service
@Repository("dutyLogInfoService")
public class DutyLogInfoServiceImpl implements DutyLogInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(DutyLogInfoServiceImpl.class);
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("dutyLogService")
	private DutyLogService dutyLogService;
	
	@Autowired
	@Qualifier("roomService")
	private RoomService roomService;
	
	/**
	 * 配电房客户值班日志新增、编辑
	 */
	@Override
	public SaveResult saveDutyLog(SaveReqData saveReqData){
		SaveResult result = new SaveResult();
		try {
			Long uid = saveReqData.getUid();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
			if (null == companyInfo) {
				result.set(ResultCode.ERROR_201, "用户公司信息不存在");
				return result;
			}
			if (null == userInfo.getLoginName() || 0L == userInfo.getCompanyId()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "请进行实名认证");
                return result;
            }
            if (!userInfo.isCustomer()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "认证为客户后，才能为配电房添加值班日志");
                return result;
            }
			Long id = saveReqData.getId();
			if (null != id) {
				DutyLogVO dutyLogVO = dutyLogService.getDutyLogVO(id);
				if (null == dutyLogVO) {
					result.set(ResultCode.ERROR_401, "要编辑的日志不存在");
					return result;
				}
			}
			Long roomId = saveReqData.getRoomId();
			if (null == roomId) {
				result.set(ResultCode.ERROR_401, "需要关联配电房");
				return result;
			}
			RoomVO roomVO = roomService.queryRoomVO(roomId);
			if (null == roomVO) {
				result.set(ResultCode.ERROR_401, "关联的配电房不存在");
				return result;
			}
			String title = saveReqData.getTitle();
			if (! BizUtils.checkLength(title, 50)) {
				result.set(ResultCode.ERROR_402, "标题不能为空或超过50个字");
				return result;
			}
			String content = saveReqData.getContent();
			if (! BizUtils.checkLength(content, 1000)) {
				result.set(ResultCode.ERROR_403, "内容不能为空或超过1000字");
				return result;
			}
			dutyLogService.save(saveReqData, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("save:{}", e);
		}
		return result;
	}
	
	/**
	 * 值班日志列表
	 */
	@Override
	public ListResult dutyLogList(ListReqData listReqData){
		ListResult result = new ListResult();
		try {
			if (null == listReqData.getUid()) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(listReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long roomId = listReqData.getRoomId();
			if (roomId != null && roomId != -1) {  //如果等于-1或null，则查询所有配电房的操作记录
				RoomVO roomVO = roomService.queryRoomVO(roomId);
				if (null == roomVO) {
					result.set(ResultCode.ERROR_402, "关联的配电房不存在");
					return result;
				}
			}
			
			PageResult<DutyLogVO> pageResult =  dutyLogService.getDutyLogVOList(listReqData, userInfo);
			result.setDutyLogs(pageResult.getData());
			result.setTotalCount(pageResult.getTotal());
			result.setNextOffset(pageResult.getNextOffset());
			result.setHasNext(pageResult.isHasNext());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("list:{}", e);
		}
		return result;
	}
	
	/**
	 * 值班日志详情
	 * @param detailsReqData
	 * @return
	 */
	@Override
	public DetailsResult getDutyLogDetails(DetailsReqData detailsReqData){
		DetailsResult result = new DetailsResult();
		try {
			if (null == detailsReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(detailsReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long id = detailsReqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_401, "记录ID不能为空");
				return result;
			}
			DutyLogVO dutyLogVO = dutyLogService.getDutyLogVO(id);
			result.setDutyLog(dutyLogVO);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("details:{}", e);
		}
		return result;
	}
	
	/**
	 * 配电房值班日志删除
	 * @param deleteReqData
	 * @return
	 */
	@Override
	public DeleteResult deleteDutyLog(DeleteReqData deleteReqData){
		DeleteResult result = new DeleteResult();
		try {
			if (null == deleteReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(deleteReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long id = deleteReqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_401, "记录ID不能为空");
				return result;
			}
			DutyLogVO dutyLogVO = dutyLogService.getDutyLogVO(id);
			if (null == dutyLogVO) {
				result.set(ResultCode.ERROR_402, "要删除的日志不存在");
				return result;
			}
			dutyLogService.deleteDutyLog(id, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("delete:{}", e);
		}
		return result;
	}

}
