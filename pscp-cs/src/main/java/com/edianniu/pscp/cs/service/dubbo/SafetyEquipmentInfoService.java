package com.edianniu.pscp.cs.service.dubbo;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.safetyequipment.DeleteReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.DeleteResult;
import com.edianniu.pscp.cs.bean.safetyequipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.DetailsResult;
import com.edianniu.pscp.cs.bean.safetyequipment.ListReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.ListResult;
import com.edianniu.pscp.cs.bean.safetyequipment.SaveReqData;
import com.edianniu.pscp.cs.bean.safetyequipment.SaveResult;

/**
 * 配电房安全设备
 * @author zhoujianjian
 * @date 2017年10月31日 下午1:17:28
 */
public interface SafetyEquipmentInfoService {

	SaveResult saveSafetyEquipment(SaveReqData saveReqData);

	ListResult safetyEquipmentList(ListReqData listReqData);

	DetailsResult getSafetyEquipmentDetails(DetailsReqData detailsReqData);

	DeleteResult deleteSafetyEquipment(DeleteReqData deleteReqData);

	List<Long> scanCanCheckSafetyEquipments();

	Result handleCanCheckSafetyEquipment(Long id);

	List<Long> scanNoCheckedSafetyEquipments();

	Result handleNoCheckSafetyEquipment(Long id);

}
