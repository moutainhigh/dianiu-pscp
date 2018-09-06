package com.edianniu.pscp.mis.service;


import com.edianniu.pscp.mis.bean.invoice.*;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.MemberInvoiceInfo;

public interface MemberInvoiceService {

    /**
     * 获取发票抬头信息
     *
     * @param reqData
     * @return
     */
    InvoiceTitle getInvoiceTitleInfo(InvoiceTitleReqData reqData);

    /**
     * 通过UID获取发票抬头信息
     *
     * @param uid
     * @return
     */
    InvoiceTitle getInvoiceTitleInfoByUid(Long uid);


    /**
     * 通过发票抬头ID获取发票抬头信息
     *
     * @param id
     * @return
     */
    InvoiceTitle getInvoiceTitleInfoById(Long id);


    /**
     * 获取当前用户下发票抬头的数量
     *
     * @param uid
     * @return
     */
    Integer getCountInvoiceTitleByUid(Long uid);

    /**
     * 是否存在发票抬头信息
     *
     * @param uid
     * @return
     */
    boolean isExistInvoiceTitle(Long uid);


    /**
     * 通过指定的发票抬头id删除抬头
     *
     * @param id
     * @return
     */
    Integer deleteInvoiceTitleById(Long id, String name);


    /**
     * 新增一条发票抬头记录
     *
     * @param reqData
     */
    void addInvoiceTitle(InvoiceTitleAddReqData reqData);

    /**
     * 查询发票列表
     *
     * @param reqData
     * @return
     */
    PageResult<InvoiceInfo> getInvoiceInfoList(ListInvoiceInfoReqData reqData);

    /**
     * 开发票
     *
     * @param invoiceInfoD
     */
    void applyInvoice(MemberInvoiceInfo invoiceInfoD);

    /**
     * 根据发票id获取发票详情
     *
     * @param orderId
     * @return
     */
    InvoiceInfo getDetailInvoiceInfoById(String orderId);

    /**
     * 根据付款单号id获取发票详情
     *
     * @param payOrderId
     * @return
     */
    InvoiceInfo getDetailInvoiceInfoByPayOrderId(String payOrderId);

    /**
     * 获取需要开票的发票状态
     *
     * @param orderId
     * @return
     */
    Integer getInvoiceStatusById(String orderId);

    /**
     * 开票
     *
     * @param reqData
     */
    void confirmInvoice(ConfirmInvoiceInfoReqData reqData);
}
