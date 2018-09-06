/**
 *
 */
package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderQuery;
import com.edianniu.pscp.mis.domain.SocialWorkOrder;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午4:17:25
 */
public interface SocialWorkOrderDao {

    public int queryCount(SocialWorkOrderQuery socialWorkOrderQuery);

    public List<SocialWorkOrder> queryList(SocialWorkOrderQuery socialWorkOrderQuery);

    public SocialWorkOrder getByOrderId(@Param(value = "orderId") String orderId, @Param(value = "includeExpiry") boolean includeExpiry);
    public SocialWorkOrder getEntityByOrderId(@Param(value = "orderId") String orderId);
    public List<SocialWorkOrder> queryListByDistance(@Param(value = "distance") Double distance,
                                                     @Param(value = "latitude") Double latitude, @Param(value = "longitude") Double longitude,
                                                     @Param(value = "existSocialWorkOrderIds") List<Long> existSocialWorkOrderIds, @Param(value = "qualifications") String qualifications);

    Double getSocialElectricianFee(Long id);

    SocialWorkOrder getEntityById(Long id);

    public int update(SocialWorkOrder socialWorkOrder);
    public int updateRefundStatus(SocialWorkOrder socialWorkOrder);

    List<Map<String, Object>> getMapIdToTitleByIds(Long[] ids);

    List<SocialWorkOrder> queryListByWorkOrderIds(Long[] workOrderIds);

    /**
     * @param id            社会工单ID
     * @param workOrderId   工单ID
     * @param modifiedUser  修改人
     * @param nstatus       目标状态
     * @param bstatus       原始状态
     * @return
     */
    int updateStatusById(@Param(value = "id") Long id,
    					 @Param(value = "workOrderId") Long workOrderId,
                         @Param(value = "modifiedUser") String modifiedUser,
                         @Param(value = "nstatus") Integer nstatus,
                         @Param(value = "bstatus") Integer bstatus);

    Integer queryCountByWorkOrderId(Long workOrderId);


}
