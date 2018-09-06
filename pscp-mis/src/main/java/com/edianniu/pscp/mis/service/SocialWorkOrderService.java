/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午5:55:09
 * @version V1.0
 */
package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderDetail;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderInfo;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderQuery;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.SocialWorkOrder;

import java.util.List;
import java.util.Map;


/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午5:55:09
 */
public interface SocialWorkOrderService {

    public PageResult<SocialWorkOrderInfo> query(SocialWorkOrderQuery socialWorkOrderQuery);

    public List<SocialWorkOrderInfo> query(Double distance,
                                           Double latitude, Double longitude, List<Long> existSocialWorkOrderIds, String qualifications);

    public SocialWorkOrderDetail getDetailByOrderId(String orderId);

    public SocialWorkOrder getNotExpiryByOrderId(String orderId);
    /**
     * 视图查询
     * @param orderId
     * @return
     */
    public SocialWorkOrder getViewByOrderId(String orderId);
    /**
     * 原始表查询
     * @param orderId
     * @return
     */
    public SocialWorkOrder getBySimpleOrderId(String orderId);
    /**
     * 保证金退款到余额
     * @param socialWorkOrder
     * @return
     */
    public boolean refundDeposit(SocialWorkOrder socialWorkOrder);

    public int updateOrderStatus(SocialWorkOrder socialWorkOrder);

    Double getSocialElectricianFee(Long id);
    /**
     * 获取结算不足金额
     * 工单总冻结金额-已结算电工工单金额-未结算电工工单金额-结算待确认工单金额
     * 
     * @param uid(会员ID)
     * @param socialWorkOrder
     * @return
     */
    Double getInsufficientSettlementAmount(Long uid,SocialWorkOrder socialWorkOrder);

    SocialWorkOrder getEntityById(Long id);

    public boolean updateOrderStatus(PayOrder order);
    

    List<Map<String, Object>> getMapIdToContentByIds(Long[] ids);
}
