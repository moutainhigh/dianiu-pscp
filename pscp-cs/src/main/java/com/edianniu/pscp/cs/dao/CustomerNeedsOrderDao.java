package com.edianniu.pscp.cs.dao;

import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderListVO;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderVO;
import com.edianniu.pscp.cs.bean.query.NeedsOrderListQuery;
import com.edianniu.pscp.cs.domain.CustomerNeedsOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-21 15:52:07
 */
public interface CustomerNeedsOrderDao extends BaseDao<CustomerNeedsOrder> {
    CustomerNeedsOrder getEntityById(Map<String, Object> queryMap);

    int queryListNeedsOrderCount(NeedsOrderListQuery listQuery);

    List<NeedsOrderListVO> queryListNeedsOrder(NeedsOrderListQuery listQuery);

    List<NeedsOrderVO> selectNeedsOrderByCondition(Map<String, Object> queryMap);

    int updateEntityById(CustomerNeedsOrder needsOrder);

    void saveEntity(CustomerNeedsOrder needsOrder);

    void updateBatchNeedsOrderVO(List<NeedsOrderVO> needsOrderVOList);

	List<Long> getOvertimeAndUnPayNeedsorders(HashMap<String, Object> map);

    List<Map<String,Object>> selectSendMessagePushInfo(Long needsId);

    Map<String,Object> getMapSendMessagePushInfo(Long id);

	List<String> getNotGoWellNeedsorders(HashMap<String, Object> map);
}
