package com.edianniu.pscp.cs.service.dubbo;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.*;
import com.edianniu.pscp.cs.bean.needsorder.InitDataReqData;
import com.edianniu.pscp.cs.bean.needsorder.InitDataResult;

/**
 * @author zhoujianjian
 * 2017年9月13日下午10:20:52
 */
public interface NeedsInfoService {

    /**
     * 新增需求信息,发布需求
     *
     * @param saveReqData
     * @return
     */
    SaveResult saveNeeds(SaveReqData saveReqData);

    /**
     * 重新发布需求
     *
     * @param republishReqData
     * @return
     */
    RepublishResult republishNeeds(RepublishReqData republishReqData);

    /**
     * 取消需求
     *
     * @param orderId
     * @param uid
     * @return
     */
    CancelResult cancelNeeds(String orderId, Long uid);

    /**
     * 需求询价
     *
     * @param orderId
     * @param uid
     * @return
     */
    QuoteResult quoteNeeds(String orderId, Long uid, String responsedOrderIds);


    /**
     * 确认合作
     *
     * @return
     */
    ConfirmCooperationResult confirmCooperation(ConfirmCooperationReqData confirmCooperationReqData);

    /**
     * 获取需求列表
     *
     * @param listReqData
     * @return
     */
    ListResult queryList(ListReqData listReqData);

    /**
     * 需求详情
     *
     * @param detailsReqData
     * @return
     */
    DetailsResult query(DetailsReqData detailsReqData);

    /**
     * 合作附件上传、编辑
     *
     * @param uploadFileReqData
     * @return
     */
    UploadFileResult upload(UploadFileReqData uploadFileReqData);

    /**
     * 服务商资质详情
     *
     * @param respondDetailsReqData
     * @return
     */
    RespondDetailsResult respondDetails(RespondDetailsReqData respondDetailsReqData);

    /**
     * 需求市场列表(dubbo接口，外部调用)
     *
     * @param reqData
     * @return
     */
    NeedsMarketListResult queryListNeedsMarket(NeedsMarketListReqData reqData);

    /**
     * 需求市场列表(dubbo接口，portal服务调用)
     *
     * @param reqData
     * @return
     */
    NeedsMarketListResult queryListNeedsMarketPortal(NeedsMarketListReqData reqData);

    /**
     * 需求详情(dubbo接口，外部调用)
     *
     * @param reqData
     * @return
     */
    NeedsMarketDetailsResult getNeedsMarketDetails(NeedsMarketDetailsReqData reqData);

    /**
     * 需求列表  后台使用
     *
     * @param needsViewListReqData
     * @return
     */
    NeedsViewListResult getNeedsViewList(NeedsViewListReqData needsViewListReqData);

    /**
     * 需求审核
     * @param auditReqData
     * @return
     */
    AuditResult auditNeeds(AuditReqData auditReqData);

    /**
     * 需求详情，门户使用
     * @param orderId
     * @param companyId
     * @return
     */
    NeedsInfoResult getNeedsInfo(String orderId, Long companyId, String responsedOrderId);

    /**
     * 需求派勘察订单初始化数据
     *
     * @param reqData
     * @return
     */
    InitDataResult surveyInitData(InitDataReqData reqData);

    /**
     * 获取超时需求
     * @return
     */
	List<Long> getOvertimeNeeds(Integer limit);

	/**
	 * 处理超市需求
	 * @param id
	 * @return
	 */
	Result handleOvertimeNeeds(Long id);


}
