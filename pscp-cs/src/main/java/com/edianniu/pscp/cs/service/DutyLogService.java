package com.edianniu.pscp.cs.service;

import com.edianniu.pscp.cs.bean.dutylog.ListReqData;
import com.edianniu.pscp.cs.bean.dutylog.SaveReqData;
import com.edianniu.pscp.cs.bean.dutylog.vo.DutyLogVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;

/**
 * @author zhoujianjian
 * @date 2017年10月30日 下午1:40:10
 */
public interface DutyLogService {

	void save(SaveReqData saveReqData, UserInfo userInfo);

	PageResult<DutyLogVO> getDutyLogVOList(ListReqData listReqData, UserInfo userInfo);

	DutyLogVO getDutyLogVO(Long id);

	void deleteDutyLog(Long id, UserInfo userInfo);

	Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId);

}
