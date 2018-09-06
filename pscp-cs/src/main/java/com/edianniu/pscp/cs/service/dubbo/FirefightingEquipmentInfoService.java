package com.edianniu.pscp.cs.service.dubbo;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.firefightingequipment.DeleteReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.DeleteResult;
import com.edianniu.pscp.cs.bean.firefightingequipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.DetailsResult;
import com.edianniu.pscp.cs.bean.firefightingequipment.ListReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.ListResult;
import com.edianniu.pscp.cs.bean.firefightingequipment.SaveReqData;
import com.edianniu.pscp.cs.bean.firefightingequipment.SaveResult;

/**
 * @author zhoujianjian
 * @date 2017年11月1日 下午4:52:45
 */
public interface FirefightingEquipmentInfoService {

	SaveResult saveFirefightingEquipment(SaveReqData saveReqData);

	ListResult firefightingEquipmentList(ListReqData listReqData);

	DetailsResult getFriefightingEquipmentDetails(DetailsReqData detailsReqData);

	DeleteResult deleteFirefightingEquipment(DeleteReqData deleteReqData);

	List<Long> scanCanCheckFirefightingEquipments();

	Result handleCanCheckFirefightingEquipment(Long id);

}
