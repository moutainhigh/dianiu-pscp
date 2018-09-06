package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.invoice.*;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.InvoiceDao;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.service.CompanyRenterService;
import com.edianniu.pscp.mis.service.MemberInvoiceService;
import com.edianniu.pscp.mis.service.PayOrderService;
import com.edianniu.pscp.mis.service.RenterChargeOrderService;
import com.edianniu.pscp.mis.util.BigDecimalUtil;
import com.edianniu.pscp.mis.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.edianniu.pscp.mis.util.DateUtil.YYYY_MM_DD_FORMAT;


@Service
@Repository("memberInvoiceService")
@Transactional
public class DefaultMemberInvoiceService implements MemberInvoiceService {

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    @Qualifier("renterChargeOrderService")
    RenterChargeOrderService mRenterChargeOrderService;


    @Autowired
    @Qualifier("payOrderService")
    PayOrderService mPayOrderService;

    @Autowired
    @Qualifier("companyRenterService")
    CompanyRenterService companyRenterService;


    /**
     * 获取发票抬头信息
     *
     * @param reqData
     * @return
     */
    @Override
    public InvoiceTitle getInvoiceTitleInfo(InvoiceTitleReqData reqData) {
        MemberInvoiceTitle memberInvoiceTitle = invoiceDao.getInvoiceTitle(reqData.getUid());
        InvoiceTitle result = null;
        if (memberInvoiceTitle != null) {
            result = getInvoiceTitle(memberInvoiceTitle);
        }
        return result;
    }

    @Override
    public InvoiceTitle getInvoiceTitleInfoByUid(Long uid) {
        MemberInvoiceTitle memberInvoiceTitle = invoiceDao.getInvoiceTitleByUid(uid);
        InvoiceTitle result = null;
        if (memberInvoiceTitle != null) {
            result = getInvoiceTitle(memberInvoiceTitle);
        }
        return result;
    }

    /**
     * 通过发票抬头id获取发票抬头信息
     *
     * @param id
     * @return
     */
    @Override
    public InvoiceTitle getInvoiceTitleInfoById(Long id) {
        MemberInvoiceTitle memberInvoiceTitle = invoiceDao.getInvoiceTitleById(id);
        InvoiceTitle result = null;
        if (memberInvoiceTitle != null) {
            result = getInvoiceTitle(memberInvoiceTitle);
        }
        return result;
    }

    /**
     * 获取发票抬头数量
     *
     * @param uid
     * @return
     */
    @Override
    public Integer getCountInvoiceTitleByUid(Long uid) {
        return invoiceDao.getCountInvoiceTitle(uid);
    }

    /**
     * 删除发票抬头
     *
     * @param id
     * @param name
     * @return
     */
    @Override
    public Integer deleteInvoiceTitleById(Long id, String name) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", id);
        queryMap.put("modifiedUser", name);
        return invoiceDao.delete(queryMap);
    }

    /**
     * 新增发票抬头
     *
     * @param reqData
     */
    @Override
    public void addInvoiceTitle(InvoiceTitleAddReqData reqData) {
        MemberInvoiceTitle memberInvoiceTitle = new MemberInvoiceTitle();
        memberInvoiceTitle.setUid(reqData.getUid());
        memberInvoiceTitle.setName(reqData.getCompanyName());
        memberInvoiceTitle.setTaxpayerNo(reqData.getTaxpayerId());
        memberInvoiceTitle.setContactNumber(reqData.getContactNumber());
        memberInvoiceTitle.setBankCardNo(reqData.getBankCardNo());
        memberInvoiceTitle.setBankCardAddress(reqData.getBankCardAddress());
        memberInvoiceTitle.setCreateUser("liji");
        invoiceDao.addInvoiceTitle(memberInvoiceTitle);
    }

    /**
     * 查询发票列表
     *
     * @param reqData
     * @return
     */
    @Override
    public PageResult<InvoiceInfo> getInvoiceInfoList(ListInvoiceInfoReqData reqData) {
        PageResult<InvoiceInfo> pageResult = new PageResult<>();

        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("pageSize", reqData.getPageSize());
        queryMap.put("offset", reqData.getOffset());
        queryMap.put("status", reqData.getStatus());
        queryMap.put("companyId", reqData.getUid());

        //开票公司
        if (!StringUtils.isBlank(reqData.getPayerCompanyName())) {
            queryMap.put("payCompany", reqData.getPayerCompanyName());
        }

        //支付日期
        if (!StringUtils.isBlank(reqData.getPayDate())) {
            queryMap.put("payDate", reqData.getPayDate());
        }


        int total = invoiceDao.queryTotal(queryMap);
        int nextOffset = 0;
        if (total > 0) {
            List<MemberInvoiceInfo> memberInvoiceDaoList = invoiceDao.queryList(queryMap);
            List<InvoiceInfo> mInvoiceList = new ArrayList<>();
            for (MemberInvoiceInfo memberInvoiceDao : memberInvoiceDaoList) {
                if (memberInvoiceDao != null) {
                    InvoiceInfo invoiceInfo = getInvoiceInfo(memberInvoiceDao);
                    mInvoiceList.add(invoiceInfo);
                }
            }
            pageResult.setData(mInvoiceList);
            nextOffset = reqData.getOffset() + mInvoiceList.size();
        }

        if (nextOffset > 0 && nextOffset < total) {
            pageResult.setHasNext(true);
        }

        pageResult.setOffset(reqData.getOffset());
        pageResult.setNextOffset(nextOffset);
        pageResult.setTotal(total);


        return pageResult;
    }

    /**
     * 申请发票
     *
     * @param invoiceInfoD
     */
    @Override
    public void applyInvoice(MemberInvoiceInfo invoiceInfoD) {

        //发票信息表中插入一条数据
        invoiceDao.save(invoiceInfoD);

        //会员支付订单表pscp_member_pay_order中更新发票编号以及是否需要发票
        updatePayOrder(invoiceInfoD.getPayOrderIds(), invoiceInfoD.getOrderId(), Constants.INVOICE_APPLY_STATUS);

    }

    /**
     * 根据付款单号更新会员支付订单表pscp_member_pay_order中更新发票编号以及是否需要发票
     *
     * @param payOrderIds,orderId
     */
    private void updatePayOrder(String payOrderIds, String orderId, Integer type) {
        if (StringUtils.isBlank(payOrderIds))
            return;

        String[] payOrderId = payOrderIds.split(",");
        for (String payId : payOrderId) {
            PayOrder payOrder = mPayOrderService.getPayOrderByOrderId(payId);
            if (payOrder != null) {
                payOrder.setInvoiceOrderId(orderId);
                payOrder.setNeedInvoice(type);
                payOrder.setModifiedUser("系统");
            }
            mPayOrderService.updateInvoiceOrderId(payOrder);
        }

    }

    /**
     * 根据发票id获取发票详情
     *
     * @param orderId
     * @return
     */
    @Override
    public InvoiceInfo getDetailInvoiceInfoById(String orderId) {
        MemberInvoiceInfo memberInvoiceInfo = invoiceDao.getInvoiceInfoById(orderId);
        InvoiceInfo invoiceInfo = null;
        if (memberInvoiceInfo != null) {
            invoiceInfo = getInvoiceInfo(memberInvoiceInfo);

        }
        return invoiceInfo;
    }

    /**
     * 根据付款单号id获取发票详情
     *
     * @param payOrderId
     * @return
     */
    @Override
    public InvoiceInfo getDetailInvoiceInfoByPayOrderId(String payOrderId) {
        MemberInvoiceInfo memberInvoiceDao = invoiceDao.getInvoiceInfoByPayOrderId(payOrderId);
        InvoiceInfo invoiceInfo = null;
        if (memberInvoiceDao != null) {
            invoiceInfo = getInvoiceInfo(memberInvoiceDao);
        }

        return invoiceInfo;
    }

    /**
     * 根据发票id获取发票的开票状态
     *
     * @param orderId
     * @return
     */
    @Override
    public Integer getInvoiceStatusById(String orderId) {
        return invoiceDao.getInvoiceStatus(orderId);
    }

    @Override
    public void confirmInvoice(ConfirmInvoiceInfoReqData reqData) {

        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("orderId", reqData.getOrderId());
        queryMap.put("printUid", reqData.getPrintUid());
        queryMap.put("modifiedUser", reqData.getModifiedUser());
        queryMap.put("status", Constants.INVOICE_FINISHED_STATUS);
        invoiceDao.update(queryMap);

        //更改员支付订单表pscp_member_pay_order中是否需要发票
        InvoiceInfo invoiceInfo = getDetailInvoiceInfoById(reqData.getOrderId());
        if (invoiceInfo != null) {
            List<InvoiceInfoPay> pays = invoiceInfo.getPays();
            if (pays != null && !pays.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (InvoiceInfoPay pay : pays) {
                    sb.append(pay.getPayOrderId()).append(",");
                }
                updatePayOrder(sb.toString(), reqData.getOrderId(), Constants.INVOICE_FINISHED_STATUS);
            }
        }
    }


    /**
     * 发票信息，列表中的bean与数据库bean转化
     *
     * @param memberInvoiceDao
     * @return
     */
    private InvoiceInfo getInvoiceInfo(MemberInvoiceInfo memberInvoiceDao) {
        InvoiceInfo result = new InvoiceInfo();
        result.setId(memberInvoiceDao.getId());
        result.setOrderId(memberInvoiceDao.getOrderId());
        result.setStatus(memberInvoiceDao.getStatus());
        result.setCompanyName(memberInvoiceDao.getTitleName());
        result.setContactNumber(memberInvoiceDao.getTitleContactNumber());
        result.setTaxpayerId(memberInvoiceDao.getTitleTaxpayerNo());
        result.setPrintCompanyUid(memberInvoiceDao.getPrintCompanyId());
        result.setApplyUid(memberInvoiceDao.getApplyUid());

        if (memberInvoiceDao.getPrintTime() != null) {
            result.setConfirmInvoiceTime(DateUtil.getFormatDate(memberInvoiceDao.getPrintTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_FORMAT));
        }


        if (memberInvoiceDao.getApplyTime() != null) {
            result.setApplyInvoiceTime(DateUtil.getFormatDate(memberInvoiceDao.getApplyTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_FORMAT));
        }


        //支付金额
        PayOrder payOrder = mPayOrderService.getPayOrderByInvoiceOrderId(memberInvoiceDao.getOrderId());
        if (payOrder != null) {
            result.setAmount("" + payOrder.getAmount());
            if (payOrder.getPayTime() != null) {
                result.setPayTime(DateUtil.getFormatDate(payOrder.getPayTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_FORMAT));
            }

        }

        //付款单位名称
        CompanyRenter companyRenter = companyRenterService.getByUid(memberInvoiceDao.getApplyUid());

        if (companyRenter != null) {
            String payerName = companyRenter.getName();
            result.setPayerName(payerName);
        }


        //付款单号
        String payOrderIdsT = memberInvoiceDao.getPayOrderIds();

        if (!StringUtils.isBlank(payOrderIdsT)) {
            String[] payOrderIds = payOrderIdsT.split(",");
            if (payOrderIds != null && payOrderIds.length > 0) {

                List<InvoiceInfoPay> invoiceInfoPays = new ArrayList<>();


                //遍历付款单号
                for (String payOrderId : payOrderIds) {

                    InvoiceInfoPay invoiceInfoPay = new InvoiceInfoPay();
                    invoiceInfoPay.setPayOrderId(payOrderId);

                    //取出关联的业务订单Ids
                    PayOrder payOrderByOrderId = mPayOrderService.getPayOrderByOrderId(payOrderId);
                    if (payOrderByOrderId != null && !StringUtils.isBlank(payOrderByOrderId.getAssociatedOrderIds())) {

                        List<InvoiceInfoPayContent> mInvoicePayConent = new ArrayList<>();
                        String[] orderIds = payOrderByOrderId.getAssociatedOrderIds().split(",");

                        for (String orderId : orderIds) {

                            //获取每个订单的金额及日期
                            RenterChargeOrder renterChargeOrder = mRenterChargeOrderService.getByOrderId(orderId);
                            if (renterChargeOrder != null) {
                                InvoiceInfoPayContent payContent = new InvoiceInfoPayContent();
                                payContent.setFee(BigDecimalUtil.CovertTwoDecimal(renterChargeOrder.getCharge().toString()));
                                if ((renterChargeOrder.getLastCheckDate() != null) && renterChargeOrder.getThisCheckDate() != null) {
                                    payContent.setPeriod(DateUtil.getFormatDate(renterChargeOrder.getLastCheckDate(), YYYY_MM_DD_FORMAT) +
                                            "至" + DateUtil.getFormatDate(renterChargeOrder.getThisCheckDate(), YYYY_MM_DD_FORMAT) + "电费"
                                    );
                                }
                                mInvoicePayConent.add(payContent);
                            }
                        }
                        invoiceInfoPay.setPayContents(mInvoicePayConent);
                    }
                    invoiceInfoPays.add(invoiceInfoPay);
                }

                if (invoiceInfoPays != null && !invoiceInfoPays.isEmpty()) {
                    result.setPays(invoiceInfoPays);
                }
            }

        }

        return result;
    }

    /**
     * 发票抬头转换
     *
     * @param memberInvoiceTitle
     * @return
     */
    private InvoiceTitle getInvoiceTitle(MemberInvoiceTitle memberInvoiceTitle) {
        InvoiceTitle result = new InvoiceTitle();
        result.setBankCardAddress(memberInvoiceTitle.getBankCardAddress());
        result.setBankCardNo(memberInvoiceTitle.getBankCardNo());
        result.setCompanyName(memberInvoiceTitle.getName());
        result.setContactNumber(memberInvoiceTitle.getContactNumber());
        result.setId(memberInvoiceTitle.getId());
        result.setUid(memberInvoiceTitle.getUid());
        result.setTaxpayerId(memberInvoiceTitle.getTaxpayerNo());
        return result;
    }


    @Override
    public boolean isExistInvoiceTitle(Long uid) {
        if (this.getCountInvoiceTitleByUid(uid) > 0) {
            return true;
        }
        return false;
    }


}
