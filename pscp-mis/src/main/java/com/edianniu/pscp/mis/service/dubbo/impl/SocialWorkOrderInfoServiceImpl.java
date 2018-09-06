/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午6:38:04
 * @version V1.0
 */
package com.edianniu.pscp.mis.service.dubbo.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.message.bean.MessageInfo;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.CertificateInfo;
import com.edianniu.pscp.mis.bean.DefaultResult;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.company.CompanyDetail;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;
import com.edianniu.pscp.mis.bean.electrician.ElectricianWorkStatus;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderStatus;
import com.edianniu.pscp.mis.bean.electricianworkorder.ElectricianWorkOrderType;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.*;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.service.*;
import com.edianniu.pscp.mis.service.dubbo.SocialWorkOrderInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.util.BizUtils;
import com.edianniu.pscp.mis.util.MoneyUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午6:38:04
 */
@Service
@Repository("socialWorkOrderInfoService")
public class SocialWorkOrderInfoServiceImpl implements
        SocialWorkOrderInfoService {
    private static final Logger logger = LoggerFactory
            .getLogger(SocialWorkOrderInfoServiceImpl.class);
    @Autowired
    @Qualifier("socialWorkOrderService")
    private SocialWorkOrderService socialWorkOrderService;
    @Autowired
    @Qualifier("electricianWorkOrderService")
    private ElectricianWorkOrderService electricianWorkOrderService;
    @Autowired
    @Qualifier("electricianService")
    private ElectricianService electricianService;
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("workOrderService")
    private WorkOrderService workOrderService;
    private Double distance = 100000D;

    @Value(value = "${social.work.order.map.distance:0D}")
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public Result onoff(SocialWorkOrderOnoffReqData req) {
        /**
         * 社会电工上线/下线功能 企业电工无权限操作 不能重复上线和下线
         */
        Result result = new DefaultResult();
        try {
            UserInfo userInfo = userService.getUserInfo(req.getUid());

            Electrician electrician = electricianService.getByUid(req.getUid());
            if (electrician == null) {
                result.setResultCode(ResultCode.UNAUTHORIZED_ERROR);
                result.setResultMessage("电工未认证");
                return result;
            }
            if (!electrician.isSocialElectrician()) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("企业电工无权操作");
                return result;
            }
            if (electrician.getStatus() == ElectricianAuthStatus.AUTHING.getValue()) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("认证中,请认证通过后再操作");
                return result;
            }
            if (electrician.getStatus() == ElectricianAuthStatus.FAIL
                    .getValue()) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("认证失败,请认证通过后再操作");
                return result;
            }
            if (userInfo == null) {
                result.set(ResultCode.ERROR_401, "status 只能输入'on'或者'off'");
                return result;
            }
            if (StringUtils.isBlank(req.getStatus())) {
                result.set(ResultCode.ERROR_402, "status 不能为空");
                return result;
            }
            if (!(req.getStatus().equals("on") || req.getStatus().equals("off"))) {
                result.set(ResultCode.ERROR_403, "status 只能输入'on'或者'off'");
                return result;
            }
            ElectricianWorkStatus workStatus = req.getStatus().equals("on") ? ElectricianWorkStatus.ON_LINE
                    : ElectricianWorkStatus.OFF_LINE;
            if (electrician.getWorkStatus() == workStatus.getValue()) {
                result.set(ResultCode.ERROR_405, "已经是:" + workStatus.getDesc()
                        + "状态，请勿重复操作");
                return result;
            }
            electricianService.updateWorkStatus(electrician,
                    workStatus.getValue());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("socialWorkOrder onoff:{}", e);
        }
        return result;
    }

    @Override
    public Result take(SocialWorkOrderTakeReqData req) {
        /**
         * 1)判断用户是否认证通过，认证没有通过无法接单 2)判断用户是否可以接这个单（资质是否符合） 3)判断工单人数是否已经满了
         * 4)是否社会电工
         *
         */
        Result result = new DefaultResult();
        try {
            Electrician electrician = electricianService.getByUid(req.getUid());
            if (electrician == null) {
                result.setResultCode(ResultCode.UNAUTHORIZED_ERROR);
                result.setResultMessage("电工未认证");
                return result;
            }
            if (!electrician.isSocialElectrician()) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("企业电工无权操作");
                return result;
            }
            if (electrician.getStatus() == ElectricianAuthStatus.NOTAUTH.getValue()) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("未认证,请先提交电工认证信息");
                return result;
            }
            if (electrician.getStatus() == ElectricianAuthStatus.AUTHING.getValue()) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("认证中,请认证通过后再操作");
                return result;
            }

            if (electrician.getStatus() == ElectricianAuthStatus.FAIL
                    .getValue()) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("认证失败,请认证通过后再操作");
                return result;
            }
            if (StringUtils.isBlank(req.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("orderId 不能为空");
                return result;
            }
            SocialWorkOrder socialWorkOrder = socialWorkOrderService
                    .getNotExpiryByOrderId(req.getOrderId().trim());
            if (socialWorkOrder == null) {
                result.setResultCode(ResultCode.ERROR_206);
                result.setResultMessage("orderId 不存在");
                return result;
            }
            if (socialWorkOrder.getStatus() == SocialWorkOrderStatus.CANCEL
                    .getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("订单已取消");
                return result;
            }
            if (socialWorkOrder.getStatus() == SocialWorkOrderStatus.RECRUIT_END
                    .getValue()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("订单已结束");
                return result;
            }
            if (electricianWorkOrderService.isExist(req.getUid(),
                    socialWorkOrder.getId())) {
                result.setResultCode(ResultCode.ERROR_402);
                result.setResultMessage("已接单，不能重复接单");
                return result;
            }
            if (electrician.getWorkStatus() == ElectricianWorkStatus.OFF_LINE.getValue()) {
                result.setResultCode(ResultCode.ERROR_403);
                result.setResultMessage("电工未上线，无法接单，请先上线");
                return result;
            }
            List<ElectricianCertificate> electricianCertificateList = electricianService.getCertificatesByElectricianId(req.getUid());
            //检查电工的资质是否符合
            //TODO
            // 开始接单
            ElectricianWorkOrder electricianWorkOrder = new ElectricianWorkOrder();
            electricianWorkOrder.setCompanyId(socialWorkOrder.getCompanyId());
            electricianWorkOrder.setElectricianId(req.getUid());
            electricianWorkOrder.setOrderId(BizUtils.getOrderId("EWD"));
            electricianWorkOrder.setSocialWorkOrderId(socialWorkOrder.getId());
            electricianWorkOrder.setWorkOrderId(socialWorkOrder
                    .getWorkOrderId());
            electricianWorkOrder
                    .setType(ElectricianWorkOrderType.SOCIAL_ELECTRICIAN_WD
                            .getValue());
            electricianWorkOrder
                    .setStatus(ElectricianWorkOrderStatus.UNCONFIRMED
                            .getValue());
            electricianWorkOrder.setWorkOrderLeader(0);
            Result cresult = electricianWorkOrderService
                    .create(electricianWorkOrder);
            if (!cresult.isSuccess()) {
                result.setResultCode(cresult.getResultCode());
                result.setResultMessage(cresult.getResultMessage());
                return result;
            }


            // 获取工单信息
            WorkOrder workOrder = workOrderService.getEntityById(electricianWorkOrder.getWorkOrderId());
            if (workOrder != null) {
                // 短信+消息推送
                Map<String, String> params = new HashMap<>();
                params.put("name", workOrder.getName());
                
                //2)派单人->sms+push
                GetUserInfoResult dispatchPersonResult = userInfoService.getUserInfo(workOrder.getMemberId());
                if (dispatchPersonResult.isSuccess()) {
                    UserInfo dispatchPerson = dispatchPersonResult.getMemberInfo();
                    CompanyInfo dispathCompanyInfo=dispatchPersonResult.getCompanyInfo();
                    params.put("member_name", dispathCompanyInfo.getName());
                    messageInfoService.sendSmsAndPushMessage(dispatchPerson.getUid(), dispatchPerson.getMobile(), MessageId.ORDER_ACCEPT_SUCCESS, params);
                }
                //1)自己->push
                params.put("member_name", electrician.getUserName());
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setMsgId(MessageId.ORDER_ACCEPT_SUCCESS.getValue());
                messageInfo.setUid(electricianWorkOrder.getElectricianId());
                messageInfo.setPushTime(new Date());
                messageInfo.setParams(params);
                messageInfoService.sendPushMessage(messageInfo);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("socialWorkOrder take:{}", e);
        }
        return result;
    }

    @Override
    public SocialWorkOrderDetailResult detail(SocialWorkOrderDetailReqData req) {
        SocialWorkOrderDetailResult result = new SocialWorkOrderDetailResult();
        try {

            if (StringUtils.isBlank(req.getOrderId())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("orderId 不能为空");
                return result;
            }
            SocialWorkOrderDetail orderDetail = socialWorkOrderService
                    .getDetailByOrderId(req.getOrderId().trim());
            if (orderDetail == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("orderId 不存在");
                return result;
            }

            result.setOrderDetail(orderDetail);

            Company company = companyService.getById(orderDetail
                    .getCompanyId());
            if (company != null) {
                CompanyDetail companyDetail = new CompanyDetail();
                companyDetail.setContactTel(company.getMobile());
                companyDetail.setId(company.getId());
                companyDetail.setLeader(company.getContacts());
                companyDetail.setName(company.getName());
                result.setCompanyInfo(companyDetail);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("socialWorkOrder detail:{}", e);
        }
        return result;

    }

    @Override
    public SocialWorkOrderListResult list(SocialWorkOrdeListReqData req) {
        SocialWorkOrderListResult result = new SocialWorkOrderListResult();
        try {
            if (req.getOffset() < 0) {
                result.setResultCode(ResultCode.ERROR_203);
                result.setResultMessage("offset 参数必须大于等于0");
                return result;
            }
            if (StringUtils.isBlank(req.getLatitude())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("latitude 不能为空");
                return result;
            }
            if (!BizUtils.isNumber(req.getLatitude())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("latitude 必须为数字");
                return result;
            }
            if (StringUtils.isBlank(req.getLongitude())) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("longitude 不能为空");
                return result;
            }
            if (!BizUtils.isNumber(req.getLongitude())) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("longitude 必须为数字");
                return result;
            }
            if (StringUtils.isBlank(req.getView())) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("view 不能为空");
                return result;
            }
            if (!(req.getView().equals("list") || req.getView().equals("map"))) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("view 只支持 list 或者 map");
                return result;
            }
            List<Long> existSocialWorkOrderIds = electricianWorkOrderService.getTakedSocialWorkOrderIds(req.getUid());
            List<ElectricianCertificate> electricianCertificateList = electricianService.getCertificatesByElectricianId(req.getUid());
            List<CertificateInfo> certList = new ArrayList<>();
            for (ElectricianCertificate ec : electricianCertificateList) {
                CertificateInfo ci = new CertificateInfo();
                ci.setId(ec.getCertificateId());
                certList.add(ci);
            }
            String qualifications = certList.isEmpty() ? null : JSON.toJSONString(certList);
            StringBuffer sorts = new StringBuffer(512);
            if (StringUtils.isNoneBlank(req.getSorts())) {
                String[] temp = req.getSorts().trim().split(",");
                if (temp != null) {
                    for (String s : temp) {
                        if (StringUtils.isBlank(s)) {
                            result.setResultCode(ResultCode.ERROR_207);
                            result.setResultMessage("排序字符串不能为空");
                            return result;
                        }
                        String ss[] = s.split("_");
                        String property = ss[0];
                        String direction = "asc";
                        if (ss.length > 2) {
                            result.setResultCode(ResultCode.ERROR_207);
                            result.setResultMessage("属性值输入有误,目前只支持price/dis");
                            return result;
                        }
                        if (property.equals("price")) {
                            property = "fee";
                        } else if (property.equals("dis")) {
                            property = "distance";
                        } else {
                            result.setResultCode(ResultCode.ERROR_207);
                            result.setResultMessage("属性值输入有误,目前只支持price/dis");
                            return result;
                        }
                        if (ss.length == 1) {// 默认升序
                            sorts.append(property + " asc").append(",");
                        } else if (ss.length >= 2) {
                            direction = ss[1];
                            if (direction.equals("asc")
                                    || direction.equals("desc")) {
                                sorts.append(property + " ").append(direction)
                                        .append(",");
                            } else {
                                result.setResultCode(ResultCode.ERROR_207);
                                result.setResultMessage("降序/排序字符串输入有误，智能输入asc/desc");
                                return result;
                            }
                        }
                    }
                }
                sorts.replace(sorts.lastIndexOf(","),
                        sorts.lastIndexOf(",") + 1, "");
            }
            if (req.getView().trim().equals("list")) {
                SocialWorkOrderQuery socialWorkOrderQuery = new SocialWorkOrderQuery();
                socialWorkOrderQuery.setLatitude(Double.parseDouble(req
                        .getLatitude().trim()));
                socialWorkOrderQuery.setLongitude(Double.parseDouble(req
                        .getLongitude().trim()));
                socialWorkOrderQuery.setOffset(req.getOffset());
                socialWorkOrderQuery.setSorts(sorts.toString());
                socialWorkOrderQuery.setExistSocialWorkOrderIds(existSocialWorkOrderIds);
                socialWorkOrderQuery.setQualifications(qualifications);
                PageResult<SocialWorkOrderInfo> pageResult = socialWorkOrderService
                        .query(socialWorkOrderQuery);
                result.setWorkOrders(pageResult.getData());
                result.setHasNext(pageResult.isHasNext());
                result.setNextOffset(pageResult.getNextOffset());
                result.setTotalCount(pageResult.getTotal());
            } else if (req.getView().trim().equals("map")) {
                List<SocialWorkOrderInfo> list = socialWorkOrderService.query(
                        distance, Double.parseDouble(req.getLatitude().trim()),
                        Double.parseDouble(req.getLongitude().trim()), existSocialWorkOrderIds, qualifications);
                result.setWorkOrders(list);
                result.setHasNext(false);
                result.setNextOffset(0);
                result.setTotalCount(list.size());
            }


        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("socialWorkOrder list:{}", e);
        }

        return result;
    }

    @Override
    public SocialWorkOrdeHomeResult home(SocialWorkOrdeHomeReqData req) {
        // 获取总接单数
        // 总收入0.00元
        // 在线时长(秒)
        // 0否，1是；是否在线
        // 判断是否是电工
        // TODO 在线时长 还包括未下线的情况，总收入需要明细里查询
        //非电工用户登录默认下发数据。
        //企业电工不能访问

        SocialWorkOrdeHomeResult result = new SocialWorkOrdeHomeResult();
        try {
            Electrician electrician = electricianService.getByUid(req.getUid());
            if (electrician == null) {
                result.setIsOnline(ElectricianWorkStatus.OFF_LINE.getValue());
                result.setOnlineTime(0L);
                result.setTotalIncome(MoneyUtils.format(0D));
                result.setTotalOrderCount(0);
                return result;
            }
            if (!electrician.isSocialElectrician()) {
                result.set(ResultCode.ERROR_401, "您无权操作");
                return result;
            }
            int statuss[] = {ElectricianWorkOrderStatus.LIQUIDATED.getValue()};
            int totalOrderCount = electricianWorkOrderService.getTotalCount(
                    req.getUid(), statuss);
            result.setIsOnline(electrician.getWorkStatus());
            if (electrician.getWorkStatus() == ElectricianWorkStatus.OFF_LINE.getValue()) {
                result.setOnlineTime(electrician.getWorkTime() == null ? 0 : electrician.getWorkTime() / 1000);
            } else {
                ElectricianWorkStatusLog workStatusLog = electricianService.getLatestWorkStatusLogByUid(req.getUid());
                Long dynamicWorkTime = 0L;
                if (workStatusLog != null && workStatusLog.getStatus() == ElectricianWorkStatus.ON_LINE.getValue()) {
                    dynamicWorkTime = System.currentTimeMillis() - workStatusLog.getOnlineTime().getTime();
                }
                Long onlineTime = electrician.getWorkTime() == null ? 0 : electrician.getWorkTime() / 1000 + dynamicWorkTime / 1000;
                result.setOnlineTime(onlineTime);
            }
            Double totalIncome = electricianWorkOrderService.getTotalLiquidatedFee(req.getUid());
            result.setTotalIncome(MoneyUtils.format(totalIncome));
            result.setTotalOrderCount(totalOrderCount);

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("socialWorkOrder detail:{}", e);
        }
        return result;
    }


}
