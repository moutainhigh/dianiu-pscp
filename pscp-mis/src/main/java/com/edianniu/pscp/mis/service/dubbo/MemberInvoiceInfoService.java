package com.edianniu.pscp.mis.service.dubbo;


import com.edianniu.pscp.mis.bean.invoice.*;

public interface MemberInvoiceInfoService {


    /**
     * 获取发票抬头信息
     *
     * @param reqData
     * @return
     */
    InvoiceTitleResult getInvoiceTitleInfo(InvoiceTitleReqData reqData);

    /**
     * 删除发票抬头信息
     *
     * @param reqData
     * @return
     */
    InvoiceTitleDeleteResult deleteInvoiceTitleInfo(InvoiceTitleDeleteReqData reqData);

    /**
     * 新增一条发票抬头记录
     *
     * @param reqData
     * @return
     */
    InvoiceTitleAddResult addInvoiceTitleInfo(InvoiceTitleAddReqData reqData);


    /**
     * 查询发票列表信息
     *
     * @param reqData
     * @return
     */
    ListInvoiceInfoResult queryListInvoiceInfo(ListInvoiceInfoReqData reqData);


    /**
     * 申请开票
     *
     * @param reqData
     * @return
     */
    InvoiceApplyResult applyInvoice(InvoiceApplyReqData reqData);


    /**
     * 获取发票详情
     *
     * @param reqData
     * @return
     */
    DetailInvoiceInfoResult getDetailInvoiceInfo(DetailInvoiceInfoReqData reqData);


    /**
     * 确认开票
     * @param reqData
     * @return
     */
    ConfirmInvoiceInfoResult confirmInvoice(ConfirmInvoiceInfoReqData reqData);


}
