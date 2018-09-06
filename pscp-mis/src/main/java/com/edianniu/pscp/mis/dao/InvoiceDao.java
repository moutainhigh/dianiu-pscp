package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.MemberInvoiceInfo;
import com.edianniu.pscp.mis.domain.MemberInvoiceTitle;

public interface InvoiceDao extends BaseDao<MemberInvoiceInfo> {

    /**
     * 获取发票抬头信息
     *
     * @param uid
     * @return
     */
    MemberInvoiceTitle getInvoiceTitle(Long uid);


    /**
     * 通过发票抬头id获取发票抬头信息
     *
     * @param id
     * @return
     */
    MemberInvoiceTitle getInvoiceTitleByUid(Long id);


    /**
     * 通过发票抬头id获取发票抬头信息
     *
     * @param id
     * @return
     */
    MemberInvoiceTitle getInvoiceTitleById(Long id);


    /**
     * 获取发票抬头数量
     *
     * @param uid
     * @return
     */
    Integer getCountInvoiceTitle(Long uid);


    /**
     * 新增一条发票抬头信息数据
     *
     * @param invoiceTitle
     */
    void addInvoiceTitle(MemberInvoiceTitle invoiceTitle);


    /**
     * 根据发票id获取发票详情
     *
     * @param orderId
     * @return
     */
    MemberInvoiceInfo getInvoiceInfoById(String orderId);

    /**
     * 根据付款单号id获取发票详情
     *
     * @param payOrderId
     * @return
     */
    MemberInvoiceInfo getInvoiceInfoByPayOrderId(String payOrderId);

    /**
     * 获取发票的状态
     *
     * @param orderId
     * @return
     */
    Integer getInvoiceStatus(String orderId);

}
