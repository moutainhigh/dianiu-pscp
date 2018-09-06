package com.edianniu.pscp.cs.service;

import com.edianniu.pscp.cs.bean.equipment.DeleteResult;
import com.edianniu.pscp.cs.bean.equipment.ListReqData;
import com.edianniu.pscp.cs.bean.equipment.SaveReqData;
import com.edianniu.pscp.cs.bean.equipment.SaveResult;
import com.edianniu.pscp.cs.bean.equipment.vo.EquipmentVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;

/**
 * @author zhoujianjian
 * 2017年9月28日下午9:09:49
 */
public interface EquipmentService {

	SaveResult save(SaveReqData saveReqData, UserInfo userInfo);

	PageResult<EquipmentVO> getEquipmentVOList(ListReqData listReqData, UserInfo userInfo);

	EquipmentVO getEquipmentVO(Long id);

	DeleteResult deleteEquipment(Long id, UserInfo userInfo);

	Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId);

}
