package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.workorder.room.RoomInfo;
import com.edianniu.pscp.mis.bean.workorder.room.RoomListReqData;
import com.edianniu.pscp.mis.bean.workorder.room.RoomListResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.WorkOrder;
import com.edianniu.pscp.mis.service.CustomerDistributionRoomService;
import com.edianniu.pscp.mis.service.EngineeringProjectService;
import com.edianniu.pscp.mis.service.WorkOrderService;
import com.edianniu.pscp.mis.service.dubbo.CustomerDistributionRoomInfoService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ClassName: CustomerDistributionRoomServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-09-18 10:52
 */
@Service
@Repository("customerDistributionRoomInfoService")
public class CustomerDistributionRoomServiceImpl implements CustomerDistributionRoomInfoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WorkOrderService workOrderService;
    @Autowired
    private CustomerDistributionRoomService customerDistributionRoomService;
    @Autowired
    private EngineeringProjectService engineeringProjectService;

    @Override
    public RoomListResult listRoomInfo(RoomListReqData reqData) {
        RoomListResult result = new RoomListResult();
        try {
            if (StringUtils.isBlank(reqData.getOrderId())) {
                result.set(ResultCode.ERROR_401, "工单信息不存在");
                return result;
            }

            // 工单信息
            WorkOrder workOrder = workOrderService.getEntityByOrderId(reqData.getOrderId());
            if (workOrder == null) {
                result.set(ResultCode.ERROR_401, "工单信息不存在");
                return result;
            }

            // 项目需求配电房信息
            String distributionRoomIds = engineeringProjectService.getRoomIdsById(workOrder.getEngineeringProjectId());
            if (StringUtils.isBlank(distributionRoomIds)) {
                result.set(ResultCode.ERROR_401, "配电房信息不存在");
                return result;
            }

            Set<Long> roomIdSet = new HashSet<>();
            String[] roomIdsStr = distributionRoomIds.trim().split(",");
            for (String roomId : roomIdsStr) {
                roomIdSet.add(Long.valueOf(roomId));
            }

            List<RoomInfo> roomList = customerDistributionRoomService.getRoomInfoByIds(roomIdSet.toArray(new Long[]{}));
            if (CollectionUtils.isEmpty(roomList)) {
                roomList = new ArrayList<>();
            }
            result.setRoomList(roomList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }
}
