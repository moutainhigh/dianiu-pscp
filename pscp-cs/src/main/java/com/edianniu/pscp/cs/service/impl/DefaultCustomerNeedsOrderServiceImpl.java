package com.edianniu.pscp.cs.service.impl;

import com.edianniu.pscp.cs.bean.OrderIdPrefix;
import com.edianniu.pscp.cs.bean.needs.*;
import com.edianniu.pscp.cs.bean.needs.keyword.ShieldingKeyWords;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderListVO;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderVO;
import com.edianniu.pscp.cs.bean.query.NeedsOrderListQuery;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.CustomerNeedsOrderDao;
import com.edianniu.pscp.cs.dao.NeedsDao;
import com.edianniu.pscp.cs.domain.CustomerNeedsOrder;
import com.edianniu.pscp.cs.domain.Needs;
import com.edianniu.pscp.cs.service.CommonAttachmentService;
import com.edianniu.pscp.cs.service.CustomerNeedsOrderService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.MoneyUtils;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.attachment.common.BusinessType;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service("customerNeedsOrderService")
public class DefaultCustomerNeedsOrderServiceImpl implements CustomerNeedsOrderService {
    @Autowired
    private CustomerNeedsOrderDao customerNeedsOrderDao;
    @Autowired
    private CommonAttachmentService commonAttachmentService;
    @Autowired
    private NeedsDao needsDao;

    @Override
    public NeedsOrderInfo getEntityById(Map<String, Object> queryMap) {
        NeedsOrderInfo needsOrderInfo = null;
        if (MapUtils.isEmpty(queryMap)) {
            return null;
        }
        CustomerNeedsOrder needsOrder = customerNeedsOrderDao.getEntityById(queryMap);
        if (needsOrder != null) {
            needsOrderInfo = transformNeedsOrderInfo(needsOrder);
            List<Attachment> quotedAttachment =
                    commonAttachmentService.structureAttachmentList(needsOrderInfo.getId(), BusinessType.QUOTE_ATTACHMENT.getValue());
            needsOrderInfo.setQuotedAttachmentList(quotedAttachment);
            needsOrderInfo.setCautionMoney(MoneyUtils.format(needsOrder.getAmount()));
        }
        return needsOrderInfo;
    }

    @Override
    public List<NeedsOrderVO> selectNeedsOrderByCondition(Map<String, Object> queryMap) {
        return customerNeedsOrderDao.selectNeedsOrderByCondition(queryMap);
    }

    @Override
    public PageResult<NeedsOrderListVO> selectPageNeedsOrder(NeedsOrderListQuery listQuery) {
        PageResult<NeedsOrderListVO> result = new PageResult<>();
        int total = customerNeedsOrderDao.queryListNeedsOrderCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<NeedsOrderListVO> entityList = customerNeedsOrderDao.queryListNeedsOrder(listQuery);
            if (CollectionUtils.isNotEmpty(entityList)) {
                shieldingKeyWords(entityList);
            }
            result.setData(entityList);
            nextOffset = listQuery.getOffset() + entityList.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(listQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    @Transactional
    @Override
    public CustomerNeedsOrder save(UserInfo userInfo, Needs needs) {
        CustomerNeedsOrder needsOrder = new CustomerNeedsOrder();
        if (needs != null) {
            needsOrder.setNeedsId(needs.getId());
            needsOrder.setCompanyId(userInfo.getCompanyId());
            needsOrder.setOrderId(BizUtils.getOrderId(OrderIdPrefix.NEEDS_ORDER_IDENTIFIER_PREFIX));
            needsOrder.setStatus(NeedsOrderStatus.RESPONED.getValue());
            needsOrder.setAmount(needs.getCautionMoney());
            needsOrder.setCreateUser(userInfo.getLoginName());
            customerNeedsOrderDao.saveEntity(needsOrder);
        }
        return needsOrder;
    }

    @Transactional
    @Override
    public void updateEntityById(CustomerNeedsOrder needsOrder, List<Attachment> attachmentList, UserInfo userInfo, NeedsVO needsVO) {
        if (needsOrder == null) {
            return;
        }
        customerNeedsOrderDao.updateEntityById(needsOrder);
        if (null != needsVO) {
            Integer status = needsVO.getStatus();
            // 如果该需求的状态不是已报价，更改状态为已报价
            if (!status.equals(NeedsStatus.QUOTED.getValue())) {
                NeedsInfo needsInfo = new NeedsInfo();
                needsInfo.setId(needsVO.getId());
                needsInfo.setStatus(NeedsStatus.QUOTED.getValue());
                needsDao.update(needsInfo);
            }
        }
        if (CollectionUtils.isNotEmpty(attachmentList)) {
            // 保存附件
            commonAttachmentService.saveAttachmentHelper(needsOrder.getId(), userInfo, attachmentList,
                    BusinessType.QUOTE_ATTACHMENT.getValue());
        }
    }

    @Transactional
    @Override
    public void updateBatchNeedsOrderVO(List<NeedsOrderVO> needsOrderVOList) {
        customerNeedsOrderDao.updateBatchNeedsOrderVO(needsOrderVOList);
    }

    /**
     * 替换关键词
     *
     * @param needsOrderListVOList
     */
    private void shieldingKeyWords(List<NeedsOrderListVO> needsOrderListVOList) {
        if (CollectionUtils.isNotEmpty(needsOrderListVOList)) {
            for (NeedsOrderListVO needsOrderListVO : needsOrderListVOList) {
                String keyword = needsOrderListVO.getKeyword();
                // 未支付
                if (StringUtils.isNotBlank(keyword)
                        && !needsOrderListVO.getPayStatus().equals(PayStatus.SUCCESS.getValue())) {
                    String[] arr = keyword.trim().split(",");
                    List<String> words = new ArrayList<String>();
                    Collections.addAll(words, arr);
                    try {
                        ShieldingKeyWords shieldingKeyWords = new ShieldingKeyWords();
                        shieldingKeyWords.createKeywordTree(words);

                        String needsName = shieldingKeyWords.searchKeyword(needsOrderListVO.getName());
                        needsOrderListVO.setName(needsName);
                        String needsDescribe = shieldingKeyWords.searchKeyword(needsOrderListVO.getDescribe());
                        needsOrderListVO.setDescribe(needsDescribe);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private NeedsOrderInfo transformNeedsOrderInfo(CustomerNeedsOrder needsOrder) {
        NeedsOrderInfo needsOrderInfo = new NeedsOrderInfo();
        BeanUtils.copyProperties(needsOrder, needsOrderInfo);
        String quotedPrice = "";
        if (needsOrder.getQuotedPrice() != null) {
            quotedPrice = MoneyUtils.format(needsOrder.getQuotedPrice());
        }
        needsOrderInfo.setQuotedPrice(quotedPrice);
        return needsOrderInfo;
    }

    /**
     * 查询超时支付保证金的需求订单
     */
    @Override
    public List<Long> getOvertimeAndUnPayNeedsorders(int minutes) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Integer> payStatusList = new ArrayList<Integer>();
        payStatusList.add(PayStatus.UNPAY.getValue());
        payStatusList.add(PayStatus.FAIL.getValue());
        payStatusList.add(PayStatus.CANCLE.getValue());
        map.put("payStatusList", payStatusList);
        map.put("minutes", minutes);
        return customerNeedsOrderDao.getOvertimeAndUnPayNeedsorders(map);
    }
    
    /**
     * 查询所有取消、不符合、不合作的需求订单（已支付且未退款）
     */
	@Override
	public List<String> getNotGoWellNeedsorders(int limit) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(NeedsOrderStatus.CANCEL.getValue());
		statusList.add(NeedsOrderStatus.NOT_QUALIFIED.getValue());
		statusList.add(NeedsOrderStatus.NOT_COOPERATE.getValue());
		map.put("statusList", statusList);
		map.put("payStatus", PayStatus.SUCCESS.getValue());
		map.put("refundStatus", Constants.TAG_NO);
		map.put("limit", limit);
		return customerNeedsOrderDao.getNotGoWellNeedsorders(map);
	}

    /**
     * 处理超时支付定金的需求响应订单
     */
    @Override
    public int handleOvertimeAndUnPayNeedsorders(Long id) {
        CustomerNeedsOrder needsOrder = new CustomerNeedsOrder();
        needsOrder.setId(id);
        needsOrder.setStatus(NeedsOrderStatus.CANCEL.getValue());
        int a = customerNeedsOrderDao.updateEntityById(needsOrder);
        return a;
    }

    @Override
    public List<Map<String, Object>> selectSendMessagePushInfo(Long needsId) {
        return customerNeedsOrderDao.selectSendMessagePushInfo(needsId);
    }

    @Override
    public Map<String, Object> getMapSendMessagePushInfo(Long id) {
        return customerNeedsOrderDao.getMapSendMessagePushInfo(id);
    }

    
}
