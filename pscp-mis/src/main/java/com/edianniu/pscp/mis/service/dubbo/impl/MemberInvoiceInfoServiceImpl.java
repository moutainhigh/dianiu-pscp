package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.invoice.*;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.CacheKey;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.domain.MemberInvoiceInfo;
import com.edianniu.pscp.mis.service.CompanyRenterService;
import com.edianniu.pscp.mis.service.CompanyService;
import com.edianniu.pscp.mis.service.MemberInvoiceService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.service.dubbo.MemberInvoiceInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import stc.skymobi.cache.redis.JedisUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Repository("memberInvoiceInfoService")
public class MemberInvoiceInfoServiceImpl implements MemberInvoiceInfoService {

    private static final Logger logger = LoggerFactory.getLogger(MemberInvoiceInfoServiceImpl.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("memberInvoiceService")
    MemberInvoiceService memberInvoiceService;

    @Autowired
    @Qualifier("companyRenterService")
    CompanyRenterService companyRenterService;

    @Autowired
    @Qualifier("companyService")
    CompanyService companyService;

    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;
    
    @Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;


    /**
     * 获取发票抬头
     *
     * @param reqData
     * @return
     */
    @Override
    public InvoiceTitleResult getInvoiceTitleInfo(InvoiceTitleReqData reqData) {
        InvoiceTitleResult result = new InvoiceTitleResult();

        try {
            InvoiceTitle invoiceTitle = memberInvoiceService.getInvoiceTitleInfo(reqData);
            if (invoiceTitle == null) {
                invoiceTitle = new InvoiceTitle();
            }
            result.setInvoiceTitle(invoiceTitle);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }

    /**
     * 删除发票抬头
     *
     * @param reqData
     * @return
     */
    @Override
    public InvoiceTitleDeleteResult deleteInvoiceTitleInfo(InvoiceTitleDeleteReqData reqData) {
        InvoiceTitleDeleteResult result = new InvoiceTitleDeleteResult();

        try {
            if (reqData.getId() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票抬头id不能为null");
                return result;
            }

            boolean isExistInvoiceTitle = memberInvoiceService.getCountInvoiceTitleByUid(reqData.getUid()) == 1 ? true : false;
            if (!isExistInvoiceTitle) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票抬头信息不存在");
                return result;
            }

            InvoiceTitle invoiceTitle = memberInvoiceService.getInvoiceTitleInfoById(reqData.getId());
            if (invoiceTitle == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票抬头信息不存在");
            }


            UserInfo userInfo = userService.getUserInfo(reqData.getUid());
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }

            memberInvoiceService.deleteInvoiceTitleById(reqData.getId(), userInfo.getLoginName());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }

        return result;
    }

    /**
     * 新增发票抬头
     *
     * @param reqData
     * @return
     */
    @Override
    public InvoiceTitleAddResult addInvoiceTitleInfo(InvoiceTitleAddReqData reqData) {
        InvoiceTitleAddResult result = new InvoiceTitleAddResult();


        try {

            Long uid = reqData.getUid();
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }

            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }

            if (TextUtils.isEmpty(reqData.getCompanyName())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票的公司抬头不能空");
                return result;
            }


            if (TextUtils.isEmpty(reqData.getTaxpayerId())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票的纳税人识别号不能空");
                return result;
            }

            boolean isExistInvoiceTitle = memberInvoiceService.getCountInvoiceTitleByUid(reqData.getUid()) == 1 ? true : false;
            if (isExistInvoiceTitle) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票抬头信息已存在");
                return result;
            }
            // 新增发票抬头的操作唯一性
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_INVOIC_TITLE_ID + uid, String.valueOf(uid), 200L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_407, "新增发票抬头已提交，请勿重复操作");
                return result;
            }


            memberInvoiceService.addInvoiceTitle(reqData);

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }

        return result;
    }

    /**
     * 发票信息列表查询
     *
     * @param reqData
     * @return
     */
    @Override
    public ListInvoiceInfoResult queryListInvoiceInfo(ListInvoiceInfoReqData reqData) {

        ListInvoiceInfoResult result = new ListInvoiceInfoResult();
        try {

            Long uid = reqData.getUid();
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }


            //根据客户uid获取所属公司的id
            Company company = companyService.getByMemberId(uid);
            if (company == null || company.getId() == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("客户所属公司id为空");
                return result;
            }

            //支付日期
            if (!StringUtils.isBlank(reqData.getPayDate())) {
                if (!BizUtils.checkYmd(reqData.getPayDate())) {
                    result.setResultCode(ResultCode.ERROR_201);
                    result.setResultMessage("搜索日期不合法");
                    return result;
                }
            }


            Integer pageSize = reqData.getPageSize();
            pageSize = pageSize == null ? 10 : pageSize;
            reqData.setPageSize(pageSize);

            reqData.setStatus(reqData.getStatus() == null ? 1 : reqData.getStatus());
            reqData.setOffset(reqData.getOffset() == null ? 0 : reqData.getOffset());
            reqData.setUid(company.getId());
            reqData.setPayDate(reqData.getPayDate());

            PageResult<InvoiceInfo> pageResult = memberInvoiceService.getInvoiceInfoList(reqData);
            result.setInvoices(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage(ResultCode.ERROR_500_MESSAGE);
            logger.error("list:{}", e);
        }

        return result;
    }

    /**
     * 发票申请
     */
    @Override
    public InvoiceApplyResult applyInvoice(InvoiceApplyReqData reqData) {
        InvoiceApplyResult result = new InvoiceApplyResult();

        try {
            Long uid = reqData.getUid();
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }

            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }

            if (TextUtils.isEmpty(reqData.getPayOrderId())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("付款单号不能为空");
                return result;
            }

            if ((reqData.getInvoiceTitleId() == null)) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票抬头id不能为空");
                return result;
            }

            InvoiceTitle memberInvoiceTitleD = memberInvoiceService.getInvoiceTitleInfoByUid(uid);
            if (memberInvoiceTitleD == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票抬头信息不存在");
                return result;
            }

            InvoiceInfo invoiceInfo = memberInvoiceService.getDetailInvoiceInfoByPayOrderId(reqData.getPayOrderId());
            if (invoiceInfo != null) {
                int status = invoiceInfo.getStatus();
                if (status == Constants.INVOICE_APPLY_STATUS) {
                    result.setResultCode(ResultCode.ERROR_201);
                    result.setResultMessage("该订单的发票已申请，不能重复申请");
                    return result;

                } else if (status == Constants.INVOICE_FINISHED_STATUS)
                    result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("该订单的发票已开，不能重复开票");
                return result;
            }


            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_INVOIC_INFO_APPLY_ID + reqData.getPayOrderId(), String.valueOf(uid), 200L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_407, "申请发票已提交，请勿重复操作");
                return result;
            }


            //查询租客客户表，获取当前用户的客户companyId
            Long companyId = companyRenterService.getCompanyIdByUid(reqData.getUid());

            MemberInvoiceInfo invoiceInfoD = new MemberInvoiceInfo();
            invoiceInfoD.setOrderId(BizUtils.getOrderId("I"));
            invoiceInfoD.setPayOrderIds(reqData.getPayOrderId());
            invoiceInfoD.setStatus(Constants.INVOICE_APPLY_STATUS);
            invoiceInfoD.setPrintCompanyId(companyId != null ? companyId : null);
            invoiceInfoD.setApplyTime(new Date());
            invoiceInfoD.setApplyUid(uid);
            invoiceInfoD.setCreateUser("系统");
            invoiceInfoD.setTitleName(memberInvoiceTitleD.getCompanyName());
            invoiceInfoD.setTitleTaxpayerNo(memberInvoiceTitleD.getTaxpayerId());
            invoiceInfoD.setTitleContactNumber(memberInvoiceTitleD.getContactNumber());
            invoiceInfoD.setTitleBankCardNo(memberInvoiceTitleD.getBankCardNo());
            invoiceInfoD.setTitleBankCardAddress(memberInvoiceTitleD.getBankCardAddress());
            memberInvoiceService.applyInvoice(invoiceInfoD);
            
            // TODO 消息推送
            pushMessage(uid, userInfo.getMobile(), Constants.INVOICE_APPLY_STATUS);
            
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage(ResultCode.ERROR_500_MESSAGE);
            logger.error("list:{}", e);
        }
        return result;
    }

    /**
     * 消息推送
     */
    private void pushMessage(final Long uid, final String mobile, final Integer status) {
		if (null == uid || StringUtils.isBlank(mobile) || null == status) {
			return;
		}
		if (!status.equals(Constants.INVOICE_APPLY_STATUS) && !status.equals(Constants.INVOICE_FINISHED_STATUS)){
			return;
		}
		try {
			Executor_Service.submit(new Runnable() {
				
				@Override
				public void run() {
					Map<String, String> params = new HashMap<>();
					MessageId messageId = null;
					if (status.equals(Constants.INVOICE_APPLY_STATUS)) {
						messageId = MessageId.RENTER_APPLY_INVOICE;
					} else {
						messageId = MessageId.RENTER_INVOICE_PUBLISHED;
					}
					messageInfoService.sendSmsAndPushMessage(uid, mobile, messageId, params);
				}
			});
		} catch (Exception e) {
			logger.error("发票申请消息推送异常", e);
		}
	}
    
    /**
     * 固定线程池
     */
    private static final ExecutorService Executor_Service = new ThreadPoolExecutor(
    		1, Runtime.getRuntime().availableProcessors(), 
    		60, TimeUnit.SECONDS, 
    		new SynchronousQueue<Runnable>(),  // 工作流
    		new ThreadPoolExecutor.CallerRunsPolicy()); // 线程饱和和处理策略
    
	/**
     * 发票详情
     * @param reqData
     * @return
     */
    @Override
    public DetailInvoiceInfoResult getDetailInvoiceInfo(DetailInvoiceInfoReqData reqData) {
        DetailInvoiceInfoResult detailInvoiceInfoResult = new DetailInvoiceInfoResult();
        try {
            if (TextUtils.isEmpty(reqData.getOrderId())) {
                detailInvoiceInfoResult.setResultCode(ResultCode.ERROR_401);
                detailInvoiceInfoResult.setResultMessage("发票编号不能为空");
                return detailInvoiceInfoResult;
            }

            Long uid = reqData.getUid();
            if (uid == null) {
                detailInvoiceInfoResult.setResultCode(ResultCode.ERROR_201);
                detailInvoiceInfoResult.setResultMessage("uid不能为空");
                return detailInvoiceInfoResult;
            }


            InvoiceInfo invoiceInfo = memberInvoiceService.getDetailInvoiceInfoById(reqData.getOrderId());
            if (invoiceInfo == null) {
                invoiceInfo = new InvoiceInfo();
            }
            detailInvoiceInfoResult.setInvoice(invoiceInfo);
        } catch (Exception e) {
            detailInvoiceInfoResult.setResultCode(ResultCode.ERROR_500);
            detailInvoiceInfoResult.setResultMessage(ResultCode.ERROR_500_MESSAGE);
            logger.error("list:{}", e);
        }

        return detailInvoiceInfoResult;
    }

    /**
     * 确认开票
     * @param reqData
     * @return
     */
    @Override
    public ConfirmInvoiceInfoResult confirmInvoice(ConfirmInvoiceInfoReqData reqData) {
        ConfirmInvoiceInfoResult result = new ConfirmInvoiceInfoResult();
        try {
            if (TextUtils.isEmpty(reqData.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票编号不能为空");
                return result;
            }
            Long uid = reqData.getUid();
            if (uid == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("uid不能为空");
                return result;
            }
            UserInfo userInfo = userService.getUserInfo(uid);
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("用户信息不存在");
                return result;
            }

            Integer status = memberInvoiceService.getInvoiceStatusById(reqData.getOrderId());

            //未开票
            if (status == Constants.INVOICE_NOT_STATUS || status == Constants.INVOICE_APPLY_STATUS) {
                InvoiceInfo invoiceInfo = memberInvoiceService.getDetailInvoiceInfoById(reqData.getOrderId());
                //根据客户uid获取所属公司的id
                Company company = companyService.getByMemberId(uid);
                if (company == null || company.getId() == null) {
                    result.setResultCode(ResultCode.ERROR_201);
                    result.setResultMessage("客户所属公司id为空");
                    return result;
                }

                if (invoiceInfo != null && company.getId().equals(invoiceInfo.getPrintCompanyUid())) {
                    // 确认开票的操作唯一性
                    Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_INVOIC_INFO_CONFIRM_ID + reqData.getOrderId(), reqData.getOrderId(), 200L);
                    if (rs == 0) {
                        result.set(ResultCode.ERROR_407, "确认开票已提交，请勿重复操作");
                        return result;
                    }

                    reqData.setPrintUid(reqData.getUid());
                    reqData.setModifiedUser("系统");
                    memberInvoiceService.confirmInvoice(reqData);
                    
                    // TODO 消息推送
                    Long applyUid = invoiceInfo.getApplyUid();
                    UserInfo applyUserInfo = userService.getUserInfo(uid);
                    pushMessage(applyUid, applyUserInfo.getMobile(), Constants.INVOICE_FINISHED_STATUS);
                    
                } else {
                    result.setResultCode(ResultCode.ERROR_201);
                    result.setResultMessage("该发票信息不存在");
                    return result;
                }
            } else {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("发票已开,不能重复开票");
                return result;
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }

        return result;
    }
}
