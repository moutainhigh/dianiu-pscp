package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.workorder.room.RoomListReqData;
import com.edianniu.pscp.mis.bean.workorder.room.RoomListResult;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-18 10:33:12
 */
public interface CustomerDistributionRoomInfoService {

    RoomListResult listRoomInfo(RoomListReqData reqData);
}
