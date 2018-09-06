package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.bean.socialworkorder.list.ListQuery;
import com.edianniu.pscp.sps.bean.socialworkorder.list.vo.SocialWorkOrderVO;
import com.edianniu.pscp.sps.domain.SocialWorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 09:24:00
 */
public interface SocialWorkOrderDao extends BaseDao<SocialWorkOrder> {

    List<SocialWorkOrder> getEntityByWorkOrderId(Long workOrderId);

    List<SocialWorkOrderVO> queryListWorkOrder(ListQuery listQuery);

    int queryListWorkOrderCount(ListQuery listQuery);

    int batchSave(List<SocialWorkOrder> socialWorkOrderList);

    SocialWorkOrder getById(@Param("id") Long id);

    SocialWorkOrder getByIdAndCompanyId(@Param("id") Long id, @Param("orderId") String orderId, @Param("companyId") Long companyId);

    int batchUpdatePayment(List<SocialWorkOrder> updateList);

    Double getSocialElectricianFee(@Param("id") Long id);

    public List<Long> getAfterExpiryTimeAndUnPayOrders();

    public List<Long> getBeforeExpiryTimeAndUnPayOrders(@Param("hour") int hour);

    public List<Long> getAfterExpiryTimeAndPaiedOrders();

    public List<Long> getFinishOrders();

    List<SocialWorkOrder> selectListByCondition(Map<String, Object> queryMap);

	List<String> scanNeedRefundOrders(HashMap<String, Object> map);
}
