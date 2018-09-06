package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsResult;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianResult;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListQuery;
import com.edianniu.pscp.sps.bean.socialworkorder.list.vo.SocialWorkOrderVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.domain.ElectricianWorkOrder;
import com.edianniu.pscp.sps.domain.SocialWorkOrder;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SocialWorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-05-18 09:26
 */
public interface SocialWorkOrderService {
    PageResult<SocialWorkOrderVO> selectPageSocialWorkOrderVO(ListQuery listQuery);

    int batchSave(List<SocialWorkOrder> socialWorkOrderList);

    SocialWorkOrder getById(Long id, String orderId, Long companyId);

    SocialWorkOrder getById(Long id);

    int update(SocialWorkOrder socialWorkOrder);

    DetailsResult details(DetailsReqData detailsReqData);

    int confirm(SocialWorkOrder socialWorkOrder, List<ElectricianWorkOrder> electricianWorkOrderList,
                Integer bstatus, Integer nstatus);

    String getCertificateName(String qualifications);

    /**
     * 构建电工资质id->name
     *
     * @param qualifications
     * @return
     */
    public List<Map<String, Object>> structureCertificateIdAndName(String qualifications);


    Double getSocialElectricianFee(Long id);

    int batchUpdatePayment(List<SocialWorkOrder> updateList);

    public List<Long> getAfterExpiryTimeAndUnPayOrders();

    public List<Long> getBeforeExpiryTimeAndUnPayOrders(int hour);

    public List<Long> getAfterExpiryTimeAndPaiedOrders();

    public List<Long> getFinishOrders();

    ElectricianResult electricianWorkOrder(ElectricianReqData electricianReqData);

    List<SocialWorkOrder> selectListByCondition(Map<String, Object> queryMap);

	List<String> scanNeedRefundOrders(int limit);
}
