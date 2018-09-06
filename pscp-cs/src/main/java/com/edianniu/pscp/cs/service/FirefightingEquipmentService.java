package com.edianniu.pscp.cs.service;

import java.util.List;

import com.edianniu.pscp.cs.bean.firefightingequipment.ListReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.SaveReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.vo.FirefightingEquipmentVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;

/**
 * @author zhoujianjian
 * @date 2017年11月1日 下午4:44:55
 */
public interface FirefightingEquipmentService {

	void saveFirefightingEquipment(SaveReqData saveReqData, UserInfo userInfo);

	PageResult<FirefightingEquipmentVO> getFirefightingEquipmentVOList(ListReqData listReqData, UserInfo userInfo);

	FirefightingEquipmentVO getFirefightingEquipmentDetails(Long id);

	void deleteFirefightingEquipment(Long id, UserInfo userInfo);

	List<Long> scanCanCheckFirefightingEquipments();

	int handleCanCheckFirefightingEquipment(Long id);

	Boolean isExistByRoomIdAndCompanyId(Long roomId, Long companyId);

}
