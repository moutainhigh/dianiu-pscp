package com.edianniu.pscp.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.CertificateInfo;
import com.edianniu.pscp.mis.bean.SocialWorkOrderRefundStatus;
import com.edianniu.pscp.mis.bean.WalletDealType;
import com.edianniu.pscp.mis.bean.WalleFundType;
import com.edianniu.pscp.mis.bean.WalletType;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderStatus;
import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderDetail;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderInfo;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderQuery;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderStatus;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.CertificateDao;
import com.edianniu.pscp.mis.dao.SocialWorkOrderDao;
import com.edianniu.pscp.mis.domain.Certificate;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.domain.Member;
import com.edianniu.pscp.mis.domain.Needs;
import com.edianniu.pscp.mis.domain.NeedsOrder;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.domain.SocialWorkOrder;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import com.edianniu.pscp.mis.service.CompanyService;
import com.edianniu.pscp.mis.service.ElectricianWorkOrderService;
import com.edianniu.pscp.mis.service.SocialWorkOrderService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.service.UserWalletService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.DateUtil;
import com.edianniu.pscp.mis.util.MoneyUtils;

@Service
@Repository("socialWorkOrderService")
public class DefaultSocialWorkOrderService implements SocialWorkOrderService {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultSocialWorkOrderService.class);
	@Autowired
	private SocialWorkOrderDao socialWorkOrderDao;
	@Autowired
	private CertificateDao certificateDao;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private ElectricianWorkOrderService electricianWorkOrderService;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MessageInfoService messageInfoService;

	@Override
	public PageResult<SocialWorkOrderInfo> query(
			SocialWorkOrderQuery socialWorkOrderQuery) {
		PageResult<SocialWorkOrderInfo> result = new PageResult<SocialWorkOrderInfo>();
		int total = socialWorkOrderDao.queryCount(socialWorkOrderQuery);
		int nextOffset = 0;
		if (total > 0) {
			List<SocialWorkOrder> list = socialWorkOrderDao
					.queryList(socialWorkOrderQuery);
			List<SocialWorkOrderInfo> rlist = new ArrayList<SocialWorkOrderInfo>();
			for (SocialWorkOrder swo : list) {
				SocialWorkOrderInfo swoi = getSocialWorkOrderInfo(swo);
				rlist.add(swoi);
			}
			result.setData(rlist);
			nextOffset = socialWorkOrderQuery.getOffset() + rlist.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setOffset(socialWorkOrderQuery.getOffset());
		result.setNextOffset(nextOffset);
		result.setTotal(total);
		return result;

	}

	@Override
	public List<SocialWorkOrderInfo> query(Double distance, Double latitude,
			Double longitude, List<Long> existSocialWorkOrderIds,
			String qualifications) {
		List<SocialWorkOrderInfo> l = new ArrayList<SocialWorkOrderInfo>();
		List<SocialWorkOrder> list = socialWorkOrderDao.queryListByDistance(
				distance, latitude, longitude, existSocialWorkOrderIds,
				qualifications);
		for (SocialWorkOrder swo : list) {
			SocialWorkOrderInfo swoi = getSocialWorkOrderInfo(swo);
			l.add(swoi);
		}
		return l;
	}

	private SocialWorkOrderInfo getSocialWorkOrderInfo(SocialWorkOrder swo) {
		SocialWorkOrderInfo swoi = new SocialWorkOrderInfo();
		swoi.setAddress(swo.getAddress());
		swoi.setDistance(BizUtils.getDistance(swo.getDistance()));
		swoi.setFee(MoneyUtils.format(swo.getFee()) + "元/天");
		swoi.setId(swo.getId());
		swoi.setLatitude(String.valueOf(swo.getLatitude()));
		swoi.setLongitude(String.valueOf(swo.getLongitude()));
		swoi.setName(swo.getName());
		swoi.setTitle(swo.getTitle());
		swoi.setContent(swo.getContent());
		swoi.setOrderId(swo.getOrderId());
		swoi.setPubTime(DateUtil.getFormatDate(swo.getCreateTime(),
				DateUtil.YYYY_MM_DD_HH_MM_FORMAT));
		return swoi;
	}

	@Override
	public SocialWorkOrderDetail getDetailByOrderId(String orderId) {
		SocialWorkOrder socialWorkOrder = getViewByOrderId(orderId);
		if (socialWorkOrder == null) {
			return null;
		}
		SocialWorkOrderDetail detail = new SocialWorkOrderDetail();
		detail.setAddress(socialWorkOrder.getAddress());
		detail.setDistance(BizUtils.getDistance(socialWorkOrder.getDistance()));
		detail.setFee(MoneyUtils.format(socialWorkOrder.getFee()) + "元/天");
		detail.setId(socialWorkOrder.getId());
		detail.setLatitude(String.valueOf(socialWorkOrder.getLatitude()));
		detail.setLongitude(String.valueOf(socialWorkOrder.getLongitude()));
		detail.setName(socialWorkOrder.getName());
		detail.setOrderId(socialWorkOrder.getOrderId());
		detail.setPubTime(DateUtil.getFormatDate(
				socialWorkOrder.getCreateTime(),
				DateUtil.YYYY_MM_DD_HH_MM_FORMAT));
		String workTime = DateUtil.getFormatDate(
				socialWorkOrder.getBeginWorkTime(),
				DateUtil.YYYY_MM_DD_HH_MM_FORMAT)
				+ " - "
				+ DateUtil.getFormatDate(socialWorkOrder.getEndWorkTime(),
						DateUtil.YYYY_MM_DD_HH_MM_FORMAT);
		detail.setWorkTime(workTime);
		detail.setTitle(socialWorkOrder.getTitle());
		detail.setContent(socialWorkOrder.getContent());
		detail.setQualifications(JSON
				.toJSONString(getCertificateList(socialWorkOrder)));
		detail.setQuantity(socialWorkOrder.getQuantity());
		detail.setStatus(socialWorkOrder.getStatus());
		detail.setCompanyId(socialWorkOrder.getCompanyId());
		detail.setTypeName(WorkOrderType.getDesc(socialWorkOrder.getType()));
		return detail;
	}

	private List<CertificateInfo> getCertificateList(
			SocialWorkOrder socialWorkOrder) {
		List<CertificateInfo> certificateList = new ArrayList<>();
		if (StringUtils.isNoneBlank(socialWorkOrder.getQualifications())) {
			List<Certificate> certList = JSON.parseArray(
					socialWorkOrder.getQualifications(), Certificate.class);
			if (certList != null && !certList.isEmpty()) {
				Long[] ids = new Long[certList.size()];
				int i = 0;
				for (Certificate cert : certList) {
					ids[i++] = cert.getId();
				}
				List<Certificate> list = this.certificateDao
						.getCertificateListByIds(ids);
				for (Certificate cert : list) {
					CertificateInfo certificateInfo = new CertificateInfo();
					BeanUtils.copyProperties(cert, certificateInfo);
					certificateList.add(certificateInfo);
				}
			}
		}
		return certificateList;
	}

	@Override
	public SocialWorkOrder getNotExpiryByOrderId(String orderId) {
		return socialWorkOrderDao.getByOrderId(orderId, false);
	}

	@Override
	public SocialWorkOrder getViewByOrderId(String orderId) {
		return socialWorkOrderDao.getByOrderId(orderId, true);
	}

	@Override
	public Double getSocialElectricianFee(Long id) {
		return socialWorkOrderDao.getSocialElectricianFee(id);
	}

	@Override
	public SocialWorkOrder getEntityById(Long id) {
		return socialWorkOrderDao.getEntityById(id);
	}

	@Override
	public int updateOrderStatus(SocialWorkOrder socialWorkOrder) {
		return socialWorkOrderDao.update(socialWorkOrder);
	}

	/**
	 * 更新社会工单状态
	 *
	 * @param order
	 * @return
	 */
	@Transactional
	public boolean updateOrderStatus(PayOrder order) {
		if (order.getOrderType() == OrderType.SOCIAL_WORK_ORDER_PAY.getValue()) {// 社会工单支付
			String[] orderIds = StringUtils.split(
					order.getAssociatedOrderIds(), ",");
			for (String orderId : orderIds) {
				SocialWorkOrder socialWorkOrder = getViewByOrderId(orderId);
				if (socialWorkOrder != null
						&& socialWorkOrder.getStatus() == SocialWorkOrderStatus.UN_PUBLISH
								.getValue()) {
					if ((socialWorkOrder.getPayStatus() == PayStatus.INPROCESSING
							.getValue() && order.getStatus() != PayStatus.SUCCESS
							.getValue())
							|| (socialWorkOrder.getPayStatus() == PayStatus.SUCCESS
									.getValue())) {
						continue;
					}
					if (order.getStatus() == PayStatus.SUCCESS.getValue()) {
						socialWorkOrder
								.setStatus(SocialWorkOrderStatus.RECRUITING
										.getValue());
					}
					// 支付订单信息的支付信息同步到工单里
					socialWorkOrder.setPayAmount(socialWorkOrder.getTotalFee());
					socialWorkOrder.setPayAsynctime(order.getPayAsyncTime());
					socialWorkOrder.setPayMemo(order.getMemo());
					socialWorkOrder.setPayStatus(order.getStatus());
					socialWorkOrder.setPaySynctime(order.getPaySyncTime());
					socialWorkOrder.setPayAsynctime(order.getPayAsyncTime());
					socialWorkOrder.setPayTime(order.getPayTime());
					socialWorkOrder.setPayType(order.getPayType());
					int num = updateOrderStatus(socialWorkOrder);
					if (num == 0) {
						return false;
					}
					// 发送推送消息
                    if (order.getStatus().equals(PayStatus.SUCCESS.getValue())) {
                        sendMessagePushInfo(socialWorkOrder);
                    }
				}
			}
		}
		return true;

	}
	/**
     * 发送推送消息(异步多线程)
     * @param needOrder
     */
    private void sendMessagePushInfo(final SocialWorkOrder socialWorkOrder) {
        if (socialWorkOrder == null || socialWorkOrder.getCompanyId() == null) {
            return;
        }
        EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                // 服务商信息
                Company facilitator = companyService.getById(socialWorkOrder.getCompanyId());
                if (facilitator == null) {
                    return;
                }
                UserInfo userInfo=userService.getFacilitatorAdmin(socialWorkOrder.getCompanyId());
                if (userInfo == null) {
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("title", socialWorkOrder.getTitle());
                params.put("member_name", facilitator.getName());
                // 给服务商发消息
                messageInfoService.sendSmsAndPushMessage(facilitator.getMemberId(), userInfo.getMobile(), MessageId.SOCIAL_ORDER_PUBLISH_SUCCESS, params);
            }
        });
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
	@Override
	public List<Map<String, Object>> getMapIdToContentByIds(Long[] ids) {
		return socialWorkOrderDao.getMapIdToTitleByIds(ids);
	}

	@Override
	public Double getInsufficientSettlementAmount(Long uid,
			SocialWorkOrder socialWorkOrder) {
		// 社会工单总冻结金额--从消费明细里根据社会工单编号获取冻结类型的sum
		Double totalFrozenAmount = userWalletService
				.getUserWalletDetailTotalAmount(uid,
						socialWorkOrder.getOrderId(),
						WalletType.FROZEN.getValue());
		// 电工工单(社会)预付费用(已确认,已开始,已结束)
		socialWorkOrder.getTotalFee();// 总费用
		socialWorkOrder.getQuantity();// 总人数
		int statuss[] = { ElectricianWorkOrderStatus.CONFIRMED.getValue(),
				ElectricianWorkOrderStatus.EXECUTING.getValue(),
				ElectricianWorkOrderStatus.FINISHED.getValue() };
		int preCount = electricianWorkOrderService
				.getTotalCountBySocialWorkOrderId(socialWorkOrder.getId(),
						statuss);
		Double preTotalAmount = socialWorkOrder.getTotalFee() * preCount
				/ socialWorkOrder.getQuantity();
		// 实际费用=确认费用+已结算费用
		int statuss2[] = { ElectricianWorkOrderStatus.FEE_CONFIRM.getValue(),
				ElectricianWorkOrderStatus.LIQUIDATED.getValue() };
		Double actualFee = electricianWorkOrderService
				.getTotalActualFeeBySocialWorkOrderId(socialWorkOrder.getId(),
						statuss2);
		if (MoneyUtils.greaterThanOrEqual(totalFrozenAmount,
				(preTotalAmount + actualFee))) {
			return 0.00D;
		}
		return preTotalAmount + actualFee - totalFrozenAmount;
	}

	@Override
	public SocialWorkOrder getBySimpleOrderId(String orderId) {
		return socialWorkOrderDao.getEntityByOrderId(orderId);
	}

	@Override
	@Transactional
	public boolean refundDeposit(SocialWorkOrder socialWorkOrder) {

		if (socialWorkOrder == null) {
			logger.warn("socialWorkOrder can not be null");
			return false;
		}
		Long fuid = userService.getFacilitatorAdminUid(socialWorkOrder
				.getCompanyId());
		UserWallet fuserWallet = userWalletService.getByUid(fuid);
		if (fuserWallet == null) {
			logger.warn("user({}) wallet is not exist", fuid);
			return false;
		}
		Double amount = 0.00D;
		if (socialWorkOrder.getStatus() == SocialWorkOrderStatus.CANCEL
				.getValue()) {
			amount = socialWorkOrder.getTotalFee();
		} else if (socialWorkOrder.getStatus() == SocialWorkOrderStatus.LIQUIDATED
				.getValue()) {
			// 社会工单总冻结金额--从消费明细里根据社会工单编号获取冻结类型的sum
			Double totalFrozenAmount = userWalletService
					.getUserWalletDetailTotalAmount(fuid,
							socialWorkOrder.getOrderId(),
							WalletType.FROZEN.getValue());
			// 实际费用=已结算费用
			int statuss2[] = {
					ElectricianWorkOrderStatus.LIQUIDATED.getValue() };
			Double actualFee = electricianWorkOrderService
					.getTotalActualFeeBySocialWorkOrderId(socialWorkOrder.getId(),
							statuss2);
			amount=totalFrozenAmount-actualFee;
            
		}
		if(MoneyUtils.greaterThan(amount, 0.00D)){
			// 解冻冻结金额里的保证金
			fuserWallet.setFreezingAmount(MoneyUtils.sub(
					fuserWallet.getFreezingAmount(), amount));
			// 将解冻的保证金直接返回到余额
			fuserWallet.setAmount(MoneyUtils.add(fuserWallet.getAmount(), amount));
			// 增加解冻明细
			UserWalletDetail userWalletDetail = new UserWalletDetail();

			userWalletDetail.setUid(fuid);
			// 账单类型 -解冻
			userWalletDetail.setType(WalletType.UNFROZEN.getValue());
			// 金额
			userWalletDetail.setAmount(amount);
			// 交易类型 - 收入
			userWalletDetail.setDealType(WalletDealType.INCOME.getValue());
			// 订单编号
			userWalletDetail.setOrderId(socialWorkOrder.getOrderId());
			userWalletDetail.setPayOrderId(socialWorkOrder.getOrderId());
			// 资金来源
			userWalletDetail.setFundSource(new Long(WalleFundType.PLATFORM
					.getValue()));
			// 资金去向 -
			userWalletDetail.setFundTarget(new Long(WalleFundType.WALLE_BALANCE
					.getValue()));
			// 交易后 钱包可用余额
			userWalletDetail.setAvailableAmount(fuserWallet.getAmount());
			// 交易后 钱包冻结金额
			userWalletDetail.setAvailableFreezingAmount(fuserWallet
					.getFreezingAmount());
			// 微信/支付宝订单号
			userWalletDetail.setTransactionId(socialWorkOrder.getOrderId());
			// 支付者账号(目前只有支付宝有)
			userWalletDetail.setDealAccount("");
			// 备注
			userWalletDetail.setRemark("保证金返回余额");
			userWalletService.addUserWalletDetail(userWalletDetail);
			userWalletService.update(fuserWallet);
		}
		socialWorkOrder.setRefundStatus(SocialWorkOrderRefundStatus.SUCCESS
				.getValue());
		socialWorkOrderDao.updateRefundStatus(socialWorkOrder);
		
		return true;

	}

}
