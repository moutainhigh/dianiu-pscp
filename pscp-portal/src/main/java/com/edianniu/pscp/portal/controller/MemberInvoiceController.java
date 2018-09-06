package com.edianniu.pscp.portal.controller;


import com.edianniu.pscp.mis.bean.invoice.*;
import com.edianniu.pscp.mis.service.dubbo.MemberInvoiceInfoService;
import com.edianniu.pscp.portal.entity.CompanyEntity;
import com.edianniu.pscp.portal.service.CompanyService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.renter.mis.bean.renter.DetailReq;
import com.edianniu.pscp.renter.mis.bean.renter.DetailResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 发票管理
 */
@Controller
@RequestMapping("invoice")
public class MemberInvoiceController extends AbstractController{

    @Autowired
    MemberInvoiceInfoService memberInvoiceInfoService;

    @Autowired
    CompanyService companyService;


    /**
     * 发票列表
     *
     * @param page
     * @param limit
     * @param status 1(未开票，已申请),2(已开票)
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit,
                  @RequestParam(required = false) String payDate,
                  @RequestParam(required = false) String payerCompanyName,
                  @RequestParam(required = false) Integer status) {

        Integer offset = (page - 1) * limit;
        Long uid = ShiroUtils.getUserId();
        ListInvoiceInfoReqData reqData = new ListInvoiceInfoReqData();
        reqData.setUid(uid);
        reqData.setOffset(offset);
        reqData.setPageSize(limit);
        reqData.setStatus(status);
        reqData.setPayDate(payDate);
        reqData.setPayerCompanyName(payerCompanyName);
        ListInvoiceInfoResult result = memberInvoiceInfoService.queryListInvoiceInfo(reqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        PageUtils pageUtils = new PageUtils(result.getInvoices(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }

    /**
     * 发票详情
     *
     * @param orderId 发票编号
     * @return
     */
    @ResponseBody
    @RequestMapping("/detail/{orderId}")
    public R detail(@PathVariable("orderId") String orderId) {
        Long uid = ShiroUtils.getUserId();
        DetailInvoiceInfoReqData req = new DetailInvoiceInfoReqData();
        req.setOrderId(orderId);
        req.setUid(uid);
        DetailInvoiceInfoResult result = memberInvoiceInfoService.getDetailInvoiceInfo(req);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }

        return R.ok().put("invoice", result.getInvoice());
    }


    /**
     * 确认开发票
     *
     * @param orderId 发票编号
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirm/{orderId}")
    public R confirm(@PathVariable("orderId") String orderId) {

        ConfirmInvoiceInfoReqData req = new ConfirmInvoiceInfoReqData();

        Long uid = ShiroUtils.getUserId();
        CompanyEntity company = companyService.getCompanyByUid(uid);
        if (company != null) {
            req.setModifiedUser(company.getName());
            req.setPrintUid(uid);
            req.setPrintCompanyId(company.getId());
        }

        req.setOrderId(orderId);
        req.setUid(uid);

        ConfirmInvoiceInfoResult result = memberInvoiceInfoService.confirmInvoice(req);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }

        return R.ok();
    }


}
