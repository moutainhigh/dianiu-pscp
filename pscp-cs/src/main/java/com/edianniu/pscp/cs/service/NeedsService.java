package com.edianniu.pscp.cs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.AuditReqData;
import com.edianniu.pscp.cs.bean.needs.ListReqData;
import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.needs.NeedsViewListReqData;
import com.edianniu.pscp.cs.bean.needs.RepublishReqData;
import com.edianniu.pscp.cs.bean.needs.ResponsedCompany;
import com.edianniu.pscp.cs.bean.needs.SaveReqData;
import com.edianniu.pscp.cs.bean.needs.UploadFileReqData;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsMarketVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsViewVO;
import com.edianniu.pscp.cs.bean.needsorder.InitDataReqData;
import com.edianniu.pscp.cs.bean.needsorder.InitDataResult;
import com.edianniu.pscp.cs.bean.query.NeedsMarketListQuery;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.domain.Needs;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;

/**
 * @author zhoujianjian
 * 2017年9月13日下午8:12:37
 */
public interface NeedsService {

    /**
     * 通过需求编号获取需求信息
     *
     * @param id
     * @param orderId
     * @return
     */
    NeedsVO getNeedsById(Long id, String orderId);

    /**
     * 根据需求ID和服务商响应订单号查询响应的服务商信息
     *
     * @param orderId
     * @param responsedOrderId
     * @return
     */
    ResponsedCompany query(Long needsId, String responsedOrderId);


    /**
     * 保存需求及附件信息
     *
     * @param needsInfo
     * @param attachmentLists
     */
    void saveNeeds(SaveReqData saveReqData, UserInfo userInfo);

    /**
     * 重新发布需求
     *
     * @param needsInfo
     * @param removedImgs
     * @param attachmentList
     */
    void republishNeeds(RepublishReqData republishReqData, UserInfo userInfo, NeedsVO needsVO);

    /**
     * 需求取消
     *
     * @param needsVO
     * @param companyInfo 
     */
    void cancelNeeds(NeedsVO needsVO, UserInfo userInfo, CompanyInfo companyInfo);

    /**
     * 需求询价
     *
     * @param needsId
     * @param responsedOrderIds
     * @param companyInfo
     */
    void quoteNeeds(NeedsVO needsVO, String orderId, String responsedOrderIds, UserInfo userInfo, CompanyInfo companyInfo);

    /**
     * 确认合作
     *
     * @param needsId
     * @param responsedCompany
     */
    void confirmCooperation(NeedsVO needsVO, ResponsedCompany responsedCompany, UserInfo userInfo, CompanyInfo companyInfo);

    /**
     * 获取需求列表
     *
     * @param listReqData
     * @return
     */
    PageResult<NeedsVO> queryList(ListReqData listReqData, Long companyId);

    /**
     * 根据需求ID和需求状态获取 响应服务商列表
     *
     * @param needsId
     * @param status
     * @return
     */
    List<ResponsedCompany> queryRespondList(Long needsId, Integer status);

    /**
     * 合作附件上传、编辑
     *
     * @param needsId
     * @param uploadFileReqData
     */
    void upload(UserInfo userInfo, NeedsVO needsVO, UploadFileReqData uploadFileReqData);

    /**
     * 根据服务商订单编号获取响应详情
     *
     * @param orderId
     */
    NeedsOrderInfo queryRespondDetails(String orderId);

    /**
     * 需求市场列表
     *
     * @param listQuery
     * @return
     */
    PageResult<NeedsMarketVO> selectPageNeedsMarketVO(NeedsMarketListQuery listQuery);

    /**
     * 需求市场列表(portal服务调用)
     *
     * @param listQuery
     * @return
     */
    List<NeedsMarketVO> selectNeedsMarketVOPortal(NeedsMarketListQuery listQuery);

    /**
     * 后台需求列表
     *
     * @param needsViewListReqData
     * @return
     */
    PageResult<NeedsViewVO> getNeedsViweList(NeedsViewListReqData needsViewListReqData);

    /**
     * 需求审核
     *
     * @param auditReqData
     */
    void auditNeeds(AuditReqData auditReqData, UserInfo userInfo);

    /**
     * 不带分页需求列表
     * @param map
     * @return
     */
    List<NeedsVO> getList(HashMap<String, Object> map);
    
    Needs getCustomerNeedsById(Long id, String orderId);

    InitDataResult getSurveyInitData(UserInfo userInfo, InitDataReqData reqData);

    List<Long> getOvertimeNeeds(Integer limit);

    Result handleOvertimeNeeds(Long id);

    List<NeedsMarketVO> buildListNeedsMarketVO(List<NeedsMarketVO> listNeedsMarketVO, NeedsMarketListQuery listQuery);

    Map<String,Object> getSendMessagePushCustomer(Long id);
}
