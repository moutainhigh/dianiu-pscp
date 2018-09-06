package com.edianniu.pscp.cs.service;

import java.util.List;

import com.edianniu.pscp.cs.bean.safetyequipment.ListReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.SaveReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.vo.SafetyEquipmentVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;

/**
 * 配电房安全用具
 * @author zhoujianjian
 * @date 2017年10月31日 下午1:10:09
 */
public interface SafetyEquipmentService {

	void saveSafetyEquipment(SaveReqData saveReqData, UserInfo userInfo);

	PageResult<SafetyEquipmentVO> getSafetyEquipmentVOList(ListReqData listReqData, UserInfo userInfo);

	SafetyEquipmentVO getSafetyEquipmentDetails(Long id);

	void deleteSafetyEquipment(Long id, UserInfo userInfo);

	List<Long> scanCanCheckSafetyEquipments();

	int handleCanCheckSafetyEquipment(Long id);

	List<Long> scanNoCheckedSafetyEquipments();

	int handleNoCheckSafetyEquipment(Long id);

	Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId);

}
