package com.edianniu.pscp.cs.service.dubbo;

import com.edianniu.pscp.cs.bean.equipment.DeleteReqData;
import com.edianniu.pscp.cs.bean.equipment.DeleteResult;
import com.edianniu.pscp.cs.bean.equipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.equipment.DetailsResult;
import com.edianniu.pscp.cs.bean.equipment.ListReqData;
import com.edianniu.pscp.cs.bean.equipment.ListResult;
import com.edianniu.pscp.cs.bean.equipment.SaveReqData;
import com.edianniu.pscp.cs.bean.equipment.SaveResult;

/**
 * @author zhoujianjian
 * 2017年9月28日下午9:12:20
 */
public interface EquipmentInfoService {

	SaveResult saveEquipment(SaveReqData saveReqData);

	ListResult equipmentVOList(ListReqData listReqData);

	DetailsResult getEquipmentVODetails(DetailsReqData detailsReqData);

	DeleteResult deleteEquipment(DeleteReqData deleteReqData);


}
