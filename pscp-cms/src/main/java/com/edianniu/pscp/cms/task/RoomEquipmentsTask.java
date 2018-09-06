package com.edianniu.pscp.cms.task;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.service.dubbo.FirefightingEquipmentInfoService;
import com.edianniu.pscp.cs.service.dubbo.SafetyEquipmentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 配电房安全用具及消防设施定时任务
 * @author zhoujianjian
 * @date 2017年11月2日 下午3:10:53
 */
@Component("roomEquipmentsTask")
public class RoomEquipmentsTask {
   
	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SafetyEquipmentInfoService safetyEquipmentInfoService;
   
    @Autowired
    private FirefightingEquipmentInfoService firefightingEquipmentInfoService;

    /**
     * 扫描所有进入检查周期范围的安全用具和消防设施，并将提示状态改为可提醒
     */
    public void scanCanCheckEquipments(){
    	try {
    		// 安全用具
			List<Long> safetyEquipmentIds = safetyEquipmentInfoService.scanCanCheckSafetyEquipments();
    		for (Long id : safetyEquipmentIds) {
				Result result = safetyEquipmentInfoService.handleCanCheckSafetyEquipment(id);
				if (! result.isSuccess()) {
					logger.error("处理进入检查周期范围的安全用具失败[{}]:{}", id, JSON.toJSON(result));
				}
			}
    		// 消防设施
    		List<Long> firefightingEquipmentIds = firefightingEquipmentInfoService.scanCanCheckFirefightingEquipments();
    		for (Long id : firefightingEquipmentIds) {
				Result result = firefightingEquipmentInfoService.handleCanCheckFirefightingEquipment(id);
				if (! result.isSuccess()) {
					logger.error("处理进入检查周期范围的消防设施失败[{}]:{}", id, JSON.toJSON(result));
				}
    		}
		} catch (Exception e) {
			logger.error("处理设备状态异常:{}", e);
		}
    }

    
}
