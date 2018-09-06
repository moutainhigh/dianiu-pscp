package com.edianniu.pscp.cs.service.dubbo.impl;

import com.edianniu.pscp.cs.bean.DefaultResult;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.needs.NeedsStatus;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needsorder.*;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderListVO;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderVO;
import com.edianniu.pscp.cs.bean.query.NeedsOrderListQuery;
import com.edianniu.pscp.cs.commons.CacheKey;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.domain.CustomerNeedsOrder;
import com.edianniu.pscp.cs.domain.Needs;
import com.edianniu.pscp.cs.service.CompanyService;
import com.edianniu.pscp.cs.service.CustomerNeedsOrderService;
import com.edianniu.pscp.cs.service.NeedsService;
import com.edianniu.pscp.cs.service.dubbo.CustomerNeedsOrderInfoService;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import stc.skymobi.cache.redis.JedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: CustomerNeedsOrderServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-09-25 09:55
 */
@Service
@Repository("customerNeedsOrderInfoService")
public class CustomerNeedsOrderServiceImpl implements CustomerNeedsOrderInfoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("needsService")
    private NeedsService needsService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("customerNeedsOrderService")
    private CustomerNeedsOrderService customerNeedsOrderService;
    @Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;
    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;

    /**
     * 获取响应订单信息
     *
     * @param reqData
     * @return
     */
    @Override
    public NeedsOrderResult getNeedsOrderInfo(NeedsOrderReqData reqData) {
        NeedsOrderResult result = new NeedsOrderResult();
        try {
            if (reqData.getId() == null && StringUtils.isBlank(reqData.getOrderId())) {
                result.set(ResultCode.ERROR_401, "参数错误");
                return result;
            }

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            UserInfo userInfo = userInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }

            Map<String, Object> queryMap = new HashMap<>();
            if (reqData.getCompanyId() != null) {
                queryMap.put("companyId", reqData.getCompanyId());
            }
            if (reqData.getId() != null) {
                queryMap.put("id", reqData.getId());
            }
            if (StringUtils.isNotBlank(reqData.getOrderId())) {
                queryMap.put("orderId", reqData.getOrderId());
            }

            NeedsOrderInfo needsOrderInfo = customerNeedsOrderService.getEntityById(queryMap);
            result.setNeedsOrderInfo(needsOrderInfo);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 跟据条件获取响应订单信息
     *
     * @param reqData
     * @return
     */
    @Override
    public ListNeedsOrderResult listAllNeedsOrder(ListNeedsOrderReqData reqData) {
        ListNeedsOrderResult result = new ListNeedsOrderResult();
        try {
            if (MapUtils.isEmpty(reqData.getQueryMap())) {
                return result;
            }
            Map<String, Object> queryMap = reqData.getQueryMap();
            if (queryMap.get("orderIds") == null) {
                return result;
            }
            List<NeedsOrderVO> needsOrderVOList = customerNeedsOrderService.selectNeedsOrderByCondition(queryMap);
            result.setNeedsOrderList(needsOrderVOList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 服务商已响应需求列表
     *
     * @param reqData
     * @return
     */
    @Override
    public ListResult list(ListReqData reqData) {
        ListResult result = new ListResult();
        try {
            if (reqData.getUid() == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }

            NeedsOrderListQuery listQuery = new NeedsOrderListQuery();
            BeanUtils.copyProperties(reqData, listQuery);
            if (reqData.getPageSize() == null) {
                listQuery.setPageSize(Constants.DEFAULT_PAGE_SIZE);
            }

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }

            UserInfo userInfo = userInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            listQuery.setCompanyId(userInfo.getCompanyId());

            PageResult<NeedsOrderListVO> pageResult = customerNeedsOrderService.selectPageNeedsOrder(listQuery);
            result.setNeedsOrderList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 需求响应
     * @param reqData
     * @return
     */
    @Override
    public SaveResult save(SaveReqData reqData) {
        SaveResult result = new SaveResult();
        try {
            if (reqData.getUid() == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            String orderId = reqData.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_401, "需求不存在");
                return result;
            }
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            UserInfo userInfo = userInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            if (!userInfo.isFacilitator()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "账号未通过认证，请先前去认证");
                return result;
            }

            // 需求信息
            Needs needs = needsService.getCustomerNeedsById(null, orderId);
            if (needs == null) {
                result.set(ResultCode.ERROR_401, "需求不存在");
                return result;
            }
            if (!NeedsStatus.RESPONDING.getValue().equals(needs.getStatus())) {
                result.set(ResultCode.ERROR_401, "需求不在响应中");
                return result;
            }
            Long companyId = userInfo.getCompanyId();
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("needsId", needs.getId());
            queryMap.put("companyId", companyId);
            queryMap.put("unequalStatus", NeedsOrderStatus.CANCEL.getValue());
            NeedsOrderInfo needsOrderInfo = customerNeedsOrderService.getEntityById(queryMap);
            if (needsOrderInfo != null) {
                result.set(ResultCode.ERROR_401, "需求不能重复响应");
                return result;
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_NEEDS_ORDERID + orderId + CacheKey.SPLIT + companyId, orderId, 500L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_401, "需求响应中，请勿重复操作");
                return result;
            }

            CustomerNeedsOrder needsOrder = customerNeedsOrderService.save(userInfo, needs);
            result.setOrderId(needsOrder.getOrderId());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 需求报价
     * @param reqData
     * @return
     */
    @Override
    public OfferResult offer(OfferReqData reqData) {
        OfferResult result = new OfferResult();
        try {
            if (reqData.getUid() == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            String orderId = reqData.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_401, "订单不存在");
                return result;
            }
            if (StringUtils.isBlank(reqData.getQuotedPrice()) || reqData.getQuotedPrice().equals("0")) {
                result.set(ResultCode.ERROR_401, "报价金额格式不正确");
                return result;
            }
            if (!isNumeric(reqData.getQuotedPrice())) {
                result.set(ResultCode.ERROR_401, "报价金额格式不正确");
                return result;
            }
            if (CollectionUtils.isEmpty(reqData.getAttachmentList())) {
            	result.set(ResultCode.ERROR_401, "报价附件不能为空");
                return result;
			}
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            UserInfo userInfo = userInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            Long companyId = userInfo.getCompanyId();
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("orderId", orderId);
            parameter.put("companyId", companyId);
            NeedsOrderInfo needsOrderInfo = customerNeedsOrderService.getEntityById(parameter);
            if (needsOrderInfo == null) {
                result.set(ResultCode.ERROR_401, "订单不存在");
                return result;
            }
            Long needsId = needsOrderInfo.getNeedsId();
            NeedsVO needsVO = needsService.getNeedsById(needsId, null);
            if (null == needsVO) {
                result.set(ResultCode.ERROR_401, "需求不存在");
                return result;
            }
            if (!needsOrderInfo.getStatus().equals(NeedsOrderStatus.WAITING_QUOTE.getValue())) {
                result.set(ResultCode.ERROR_401, "订单不在报价时间");
                return result;
            }

            CustomerNeedsOrder needsOrder = new CustomerNeedsOrder();
            needsOrder.setId(needsOrderInfo.getId());
            needsOrder.setQuotedPrice(Double.valueOf(reqData.getQuotedPrice()));
            needsOrder.setStatus(NeedsOrderStatus.QUOTED.getValue());
            needsOrder.setModifiedUser(userInfo.getLoginName());
            needsOrder.setQuotedTime(new Date());

            List<Attachment> attachmentList = new ArrayList<>();
            for (Map<String, Object> mapAttachment : reqData.getAttachmentList()) {

                if (mapAttachment.get("orderNum") != null && !mapAttachment.get("orderNum").toString().matches("\\d+")) {
                    result.set(ResultCode.ERROR_401, "附件排序号格式不正确");
                    return result;
                }

                Attachment attachment = new Attachment();
                attachment.setFid(mapAttachment.get("fid").toString());
                attachment.setOrderNum(mapAttachment.get("orderNum") == null ? 0 : Integer.valueOf(mapAttachment.get("orderNum").toString()));
                attachmentList.add(attachment);
            }

            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_NEEDSORDER_ORDERID + orderId + CacheKey.SPLIT + companyId, String.valueOf(needsId), 500L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_401, "正在报价，请勿重复操作");
                return result;
            }
            customerNeedsOrderService.updateEntityById(needsOrder, attachmentList, userInfo, needsVO);
            
            // 给客户发送信息
            sendMessagePush(needsVO);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }
    
    
    /**
     * 需求响应推送消息
     */
    private void sendMessagePush(final NeedsVO needVO) {
        if (needVO == null) {
            return;
        }
        try {
            EXECUTOR_SERVICE.submit(new Runnable() {
                @Override
                public void run() {
                    // 客户信息
                    Map<String, Object> sendMessagePushCustomerMap = needsService.getSendMessagePushCustomer(needVO.getId());
                    if (MapUtils.isEmpty(sendMessagePushCustomerMap)) {
                        return;
                    }
                    Long uid = Long.valueOf(String.valueOf(sendMessagePushCustomerMap.get("memberId")));
                    String mobile = String.valueOf(sendMessagePushCustomerMap.get("mobile"));
                    MessageId messageId = MessageId.NEEDS_QUOTED_CUSTOMER;
                    
                    Map<String, String> params = new HashMap<>();
                    params.put("needs_name", needVO.getName());
                    
                    // 发消息给客户
                    messageInfoService.sendSmsAndPushMessage(uid, mobile, messageId, params);
                }
            });
        } catch (Exception e) {
            logger.error("需求响应推送消息异常", e);
        }
    }
    
    /**
     * 创建一个固定线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            // 工作队列
            new SynchronousQueue<Runnable>(),
            // 线程池饱和处理策略
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 取消响应订单
     *
     * @param reqData
     * @return
     */
    @Override
    public CancelResult cancel(CancelReqData reqData) {
        CancelResult result = new CancelResult();
        try {
            if (reqData.getUid() == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            String orderId = reqData.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_401, "订单不存在");
                return result;
            }
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            UserInfo userInfo = userInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.set(ResultCode.ERROR_401, "用户不存在");
                return result;
            }
            Long companyId = userInfo.getCompanyId();
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("orderId", orderId);
            NeedsOrderInfo needsOrderInfo = customerNeedsOrderService.getEntityById(parameter);
            if (needsOrderInfo == null) {
                result.set(ResultCode.ERROR_401, "订单不存在");
                return result;
            }
            if (!needsOrderInfo.getStatus().equals(NeedsOrderStatus.RESPONED.getValue())
                    || (needsOrderInfo.getPayStatus().equals(PayStatus.INPROCESSING.getValue())
                    || needsOrderInfo.getPayStatus().equals(PayStatus.SUCCESS.getValue()))) {
                result.set(ResultCode.ERROR_401, "订单不能取消");
                return result;
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_NEEDSORDER_ORDERID + orderId + CacheKey.SPLIT + companyId, orderId, 500L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_401, "订单取消中，请勿重复操作");
                return result;
            }

            CustomerNeedsOrder needsOrder = new CustomerNeedsOrder();
            needsOrder.setId(needsOrderInfo.getId());
            needsOrder.setStatus(NeedsOrderStatus.CANCEL.getValue());
            needsOrder.setModifiedUser(userInfo.getLoginName());
            needsOrder.setQuotedTime(new Date());
            customerNeedsOrderService.updateEntityById(needsOrder, null, null, null);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 更新订单信息(订单支付)
     *
     * @param reqData
     * @return
     */
    @Override
    public UpdateResult updateNeedsOrderVO(UpdateReqData reqData) {
        UpdateResult result = new UpdateResult();
        try {
            if (reqData.getNeedsOrderList() == null
                    || CollectionUtils.isEmpty(reqData.getNeedsOrderList())) {
                result.set(ResultCode.ERROR_401, "参数不能为空");
                return result;
            }
            List<NeedsOrderVO> needsOrderVOList = reqData.getNeedsOrderList();
            for (NeedsOrderVO needsOrderVO : needsOrderVOList) {
                if (StringUtils.isBlank(needsOrderVO.getOrderId())) {
                    result.set(ResultCode.ERROR_401, "订单ID不能为空");
                    return result;
                }
                if (needsOrderVO.getCompanyId() == null) {
                    result.set(ResultCode.ERROR_401, "公司信息不能为空");
                    return result;
                }
            }

            if (!result.isSuccess()) {
                return result;
            }

            customerNeedsOrderService.updateBatchNeedsOrderVO(needsOrderVOList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 金额格式判断
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            //异常 说明包含非数字。
            return false;
        }

        // matcher是全匹配
        Matcher isNum = IS_NUMERIC.matcher(bigStr);
        return isNum.matches();
    }

    /**
     * 扫描超时支付保证金的响应订单
     */
    @Override
    public List<Long> getOvertimeAndUnPayNeedsorders(int minutes) {
        return customerNeedsOrderService.getOvertimeAndUnPayNeedsorders(minutes);
    }

    /**
     * 处理超时支付保证金的响应订单
     */
    @Override
    public Result handleOvertimeAndUnPayNeedsorders(Long id) {
        Result result = new DefaultResult();
        try {
            if (null == id) {
                result.set(ResultCode.ERROR_401, "id不能为空");
                return result;
            }
            HashMap<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("id", id);
            NeedsOrderInfo needsOrderInfo = customerNeedsOrderService.getEntityById(queryMap);
            if (null == needsOrderInfo) {
                result.set(ResultCode.ERROR_402, "需求响应订单不存在");
                return result;
            }
            int a = customerNeedsOrderService.handleOvertimeAndUnPayNeedsorders(id);
            if (a < 1) {
                result.set(ResultCode.ERROR_403, "处理超时支付保证金的响应订单失败");
                return result;
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("handleOvertimeAndUnPayNeedsorders:{}", e);
        }
        return result;
    }

    /**
     * 扫描所有取消、不符合、不合作的需求订单（已支付且未退款）
     *
     * @return
     */
    @Override
    public List<String> getNotGoWellNeedsorders(int limit) {
        return customerNeedsOrderService.getNotGoWellNeedsorders(limit);
    }


    /**
     * 根据需求订单ID获取响应订单服务商推送信息
     *
     * @param id 需求订单ID
     * @return
     */
    @Override
    public Map<String, Object> getMapSendMessagePushInfo(Long id) {
        if (id == null) {
            return new HashMap<>();
        }
        return customerNeedsOrderService.getMapSendMessagePushInfo(id);
    }

    /**
     * 该正则表达式可以匹配所有的数字 包括负数
     */
    private static Pattern IS_NUMERIC = Pattern.compile("[0-9]+\\.?[0-9]*");
}
