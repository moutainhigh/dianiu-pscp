package com.edianniu.pscp.sps.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.SocialWorkOrderRefundStatus;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.socialworkorder.SocialWorkOrderStatus;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsResult;
import com.edianniu.pscp.sps.bean.socialworkorder.details.vo.DetailsVO;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.electrician.ElectricianResult;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListQuery;
import com.edianniu.pscp.sps.bean.socialworkorder.list.vo.SocialWorkOrderVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.ElectricianVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.WorkOrderVO;
import com.edianniu.pscp.sps.bean.workorder.electrician.ElectricianWorkOrderStatus;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.dao.*;
import com.edianniu.pscp.sps.domain.*;
import com.edianniu.pscp.sps.service.SocialWorkOrderService;
import com.edianniu.pscp.sps.util.BigDecimalUtil;
import com.edianniu.pscp.sps.util.DateUtils;
import com.edianniu.pscp.sps.util.DistanceUtil;
import com.edianniu.pscp.sps.util.MoneyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultSocialWorkOrderService
 * Author: tandingbo
 * CreateTime: 2017-05-18 09:41
 */
@Service
@Repository("socialWorkOrderService")
public class DefaultSocialWorkOrderService implements SocialWorkOrderService {

    @Autowired
    private WorkOrderDao workOrderDao;
    @Autowired
    private SpsCertificateDao spsCertificateDao;
    @Autowired
    private SocialWorkOrderDao socialWorkOrderDao;
    @Autowired
    private ElectricianWorkOrderDao electricianWorkOrderDao;
    @Autowired
    private EngineeringProjectDao engineeringProjectDao;
    @Autowired
    private SpsCompanyDao spsCompanyDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SpsCompanyCustomerDao companyCustomerDao;
    @Autowired
    private SpsElectricianCertificateDao spsElectricianCertificateDao;

    @Override
    public PageResult<SocialWorkOrderVO> selectPageSocialWorkOrderVO(ListQuery listQuery) {
        PageResult<SocialWorkOrderVO> result = new PageResult<>();
        int total = socialWorkOrderDao.queryListWorkOrderCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<SocialWorkOrderVO> list = socialWorkOrderDao.queryListWorkOrder(listQuery);
            if (CollectionUtils.isNotEmpty(list)) {
                for (SocialWorkOrderVO socialWorkOrderVO : list) {
                    // 响应人数,招募人数，结算人数
                    Map<String, String> map = getElectricianWorkOrderRatio(socialWorkOrderVO.getWorkOrderId(), socialWorkOrderVO.getId(), socialWorkOrderVO.getQuantity());
                    socialWorkOrderVO.setRecruitNumber(map.get("recruitNumber"));
                    socialWorkOrderVO.setResponseNumber(map.get("responseNumber"));
                    socialWorkOrderVO.setLiquidateNumber(map.get("liquidateNumber"));

                    // 状态
                    socialWorkOrderVO.setStatus(socialWorkOrderVO.getStatus());

                    // 电工资质名称转换
                    socialWorkOrderVO.setCertificate(getCertificateName(socialWorkOrderVO.getQualifications()));

                    // 根据经纬度计算距离
                    double distance = 0D;
                    if (StringUtils.isNotBlank(listQuery.getLongitude()) &&
                            StringUtils.isNotBlank(listQuery.getLatitude())) {
                        distance = DistanceUtil.getDistance(Double.valueOf(listQuery.getLatitude()),
                                Double.valueOf(listQuery.getLongitude()),
                                Double.valueOf(socialWorkOrderVO.getLatitude()),
                                Double.valueOf(socialWorkOrderVO.getLongitude()));
                    }
                    if (distance > 1D) {
                        socialWorkOrderVO.setDistance(String.format("%skm", BigDecimalUtil.dobCoverTwoDecimal(distance)));
                    } else {
                        BigDecimal bigDecimal = BigDecimalUtil.mul(distance, 1000D);
                        socialWorkOrderVO.setDistance(String.format("%sm", BigDecimalUtil.dobCoverTwoDecimal(bigDecimal.doubleValue())));
                    }

                    Map<String, Object> social = new HashMap<>();
                    social.put("status", 1);
                    social.put("ratio", map.get("recruitNumber"));
                    social.put("unconfirmed", map.get("unconfirmedNumber"));
                    socialWorkOrderVO.setSocial(social);
                }
            }

            result.setData(list);
            nextOffset = listQuery.getOffset() + list.size();
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

    /**
     * 电工资质名称转换
     *
     * @param qualifications
     * @return
     */
    public String getCertificateName(String qualifications) {
        Map<Long, String> mapAllCertificate = queryMapAllCertificate();

        // 电工资质名称转换
        StringBuilder certificate = new StringBuilder("");
        if (StringUtils.isNoneBlank(qualifications)) {
            JSONArray jsonArray = JSON.parseArray(qualifications);
            if (jsonArray != null && jsonArray.size() > 0) {
                Integer size = jsonArray.size();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Long id = Long.valueOf(jsonObject.get("id").toString());
                    if (mapAllCertificate.containsKey(id)) {
                        if (StringUtils.isNotBlank(certificate.toString())) {
                            certificate.append(",");
                        }
                        certificate.append(mapAllCertificate.get(id));
                    }
                }
            }
        }
        return certificate.toString();
    }

    /**
     * 构建电工资质id->name
     *
     * @param qualifications
     * @return
     */
    public List<Map<String, Object>> structureCertificateIdAndName(String qualifications) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (StringUtils.isNoneBlank(qualifications)) {
            Map<Long, String> mapAllCertificate = queryMapAllCertificate();
            JSONArray jsonArray = JSON.parseArray(qualifications);
            if (jsonArray != null && jsonArray.size() > 0) {
                Integer size = jsonArray.size();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Long id = jsonObject.getLong("id");
                    if (mapAllCertificate.containsKey(id)) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("name", mapAllCertificate.get(id));
                        result.add(map);
                    }
                }
            }
        }
        return result;
    }

    @Override
    @Transactional
    public int batchUpdatePayment(List<SocialWorkOrder> updateList) {
        return socialWorkOrderDao.batchUpdatePayment(updateList);
    }

    /**
     * 获取电工资质信息
     *
     * @return
     */
    private Map<Long, String> queryMapAllCertificate() {
        Map<Long, String> result = new HashMap<>();

        List<Map<String, Object>> mapList = spsCertificateDao.queryMapAllCertificate();
        if (CollectionUtils.isNotEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                result.put(Long.valueOf(map.get("id").toString()), map.get("name").toString());
            }
        }
        return result;
    }

    @Override
    @Transactional
    public int batchSave(List<SocialWorkOrder> socialWorkOrderList) {
        return socialWorkOrderDao.batchSave(socialWorkOrderList);
    }

    @Override
    public SocialWorkOrder getById(Long id, String orderId, Long companyId) {
        return socialWorkOrderDao.getByIdAndCompanyId(id, orderId, companyId);
    }

    @Override
    public SocialWorkOrder getById(Long id) {
        return socialWorkOrderDao.getById(id);
    }

    @Override
    @Transactional
    public int update(SocialWorkOrder socialWorkOrder) {
        return socialWorkOrderDao.update(socialWorkOrder);
    }

    @Override
    public DetailsResult details(DetailsReqData detailsReqData) {
        DetailsResult result = new DetailsResult();

        Long uid = detailsReqData.getUid();
        GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
        if (!getUserInfoResult.isSuccess()) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("登录信息异常");
            return result;
        }
        UserInfo userInfo = getUserInfoResult.getMemberInfo();
        if (userInfo == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("登录信息异常");
            return result;
        }
        SocialWorkOrder socialWorkOrder = socialWorkOrderDao.getByIdAndCompanyId(detailsReqData.getId(), detailsReqData.getOrderId(), userInfo.getCompanyId());
        if (socialWorkOrder == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("未匹配到社会工单信息");
            return result;
        }

        // 社会工单信息
        DetailsVO detailsVO = new DetailsVO();
        detailsVO.setId(socialWorkOrder.getId());
        detailsVO.setTitle(socialWorkOrder.getTitle());
        detailsVO.setStatus(socialWorkOrder.getStatus());
        detailsVO.setContent(socialWorkOrder.getContent());
        detailsVO.setOrderId(socialWorkOrder.getOrderId());
        detailsVO.setFee(MoneyUtils.format(socialWorkOrder.getFee()));
        detailsVO.setTotalFee(MoneyUtils.format(socialWorkOrder.getTotalFee()));
        detailsVO.setEndTime(DateUtils.formatToParse(socialWorkOrder.getExpiryTime(), DateUtils.DATE_PATTERN));
        detailsVO.setStartTime(DateUtils.formatToParse(socialWorkOrder.getCreateTime(), DateUtils.DATE_PATTERN));
        detailsVO.setExpiryTime(DateUtils.format(socialWorkOrder.getExpiryTime(), DateUtils.DATE_PATTERN));
        detailsVO.setCertificate(getCertificateName(socialWorkOrder.getQualifications()));
        detailsVO.setPublishTime((DateUtils.format(socialWorkOrder.getCreateTime(), DateUtils.DATE_PATTERN)));
        detailsVO.setQuantity(socialWorkOrder.getQuantity());
        String startTime1 = DateUtils.format(socialWorkOrder.getBeginWorkTime(), DateUtils.DATE_PATTERN);
        String endTime1 = DateUtils.format(socialWorkOrder.getEndWorkTime(), DateUtils.DATE_PATTERN);
        detailsVO.setWorkTime(String.format("%s~%s", startTime1, endTime1));

        // 电工资质数据结构调整:[{"id":123, "name": "asdsafd"}]格式
        List<Map<String, Object>> listCertificateIdAndName = structureCertificateIdAndName(socialWorkOrder.getQualifications());
        detailsVO.setQualifications(JSON.toJSONString(listCertificateIdAndName));

        // 响应人数,招募人数
        Map<String, String> map = getElectricianWorkOrderRatio(socialWorkOrder.getWorkOrderId(), socialWorkOrder.getId(), socialWorkOrder.getQuantity());
        detailsVO.setRecruitNumber(map.get("recruitNumber"));
        detailsVO.setResponseNumber(map.get("responseNumber"));
        detailsVO.setUnconfirmed(map.get("unconfirmedNumber"));
        result.setDetails(detailsVO);

        // 工单信息
        WorkOrder workOrder = workOrderDao.queryObject(socialWorkOrder.getWorkOrderId(), null, userInfo.getCompanyId());
        if (workOrder == null) {
            result.setResultCode(ResultCode.ERROR_402);
            result.setResultMessage("未匹配到工单信息");
            return result;
        }
        WorkOrderVO workOrderVO = new WorkOrderVO();
        workOrderVO.setId(workOrder.getId());
        workOrderVO.setOrderId(workOrder.getOrderId());
        workOrderVO.setName(workOrder.getName());
        workOrderVO.setAddress(workOrder.getAddress());
        workOrderVO.setContent(workOrder.getContent());
        workOrderVO.setPublishTime(DateUtils.format(workOrder.getCreateTime(), DateUtils.DATE_PATTERN));
        String startTime = DateUtils.format(workOrder.getStartTime(), DateUtils.DATE_PATTERN);
        String endTime = DateUtils.format(workOrder.getEndTime(), DateUtils.DATE_PATTERN);
        workOrderVO.setStartTime(startTime);
        workOrderVO.setEndTime(endTime);
        workOrderVO.setWorkTime(String.format("%s~%s", startTime, endTime));
        workOrderVO.setType(workOrder.getType());
        workOrderVO.setTypeName(WorkOrderType.getDesc(workOrder.getType()));

        // 项目信息
        ProjectInfo project = engineeringProjectDao.queryObject(workOrder.getEngineeringProjectId());
        ProjectVO projectVO = new ProjectVO();

        // 客户信息
        CompanyVO customerInfo = new CompanyVO();
        if (project != null) {
            projectVO.setId(project.getId());
            projectVO.setName(project.getName());
            workOrderVO.setProjectName(project.getName());

            CompanyCustomer companyCustomer = companyCustomerDao.getByCustomerId(project.getCustomerId());
            if (companyCustomer != null) {
                customerInfo.setId(companyCustomer.getId());
                customerInfo.setName(companyCustomer.getCpName());// 单位名称
                customerInfo.setContacts(companyCustomer.getCpContact());// 单位联系人
                customerInfo.setContactNumber(companyCustomer.getContactTel());// 单位联系电话
                customerInfo.setAddress(companyCustomer.getCpAddress());// 单位联系地址
            }
        }
        result.setCustomerInfo(customerInfo);
        result.setProject(projectVO);
        result.setWorkOrder(workOrderVO);

        // 服务商信息
        CompanyVO companyInfo = new CompanyVO();
        Company facilitator = spsCompanyDao.getCompanyById(workOrder.getCompanyId());
        if (facilitator != null) {
            companyInfo.setId(facilitator.getId());
            companyInfo.setName(facilitator.getName());// 单位名称
            companyInfo.setAddress(facilitator.getAddress());// 单位联系地址
            companyInfo.setContacts(facilitator.getContacts());// 单位联系人
            companyInfo.setContactNumber(facilitator.getContactTel());// 单位联系电话
        }
        result.setCompanyInfo(companyInfo);

        // 项目负责人
        ElectricianVO workOrderLeader = new ElectricianVO();
        Map<String, Object> leaderMap = electricianWorkOrderDao.getWorkOrderLeader(workOrder.getId());
        if (MapUtils.isNotEmpty(leaderMap)) {
            Long id = leaderMap.get("id") == null ? 0L : Long.valueOf(leaderMap.get("id").toString());
            String name = leaderMap.get("name") == null ? "" : leaderMap.get("name").toString();
            String contactTel = leaderMap.get("contactTel") == null ? "" : leaderMap.get("contactTel").toString();

            workOrderVO.setContactTel(contactTel);
            workOrderVO.setProjectLeader(name);
            workOrderVO.setProjectLeaderId(id);

            workOrderLeader.setUid(id);
            workOrderLeader.setName(name);
        }
        result.setWorkOrderLeader(workOrderLeader);

        //已经接单的社会电工信息
        List<Map<String, Object>> mapListSocialElectrician = electricianWorkOrderDao.queryMapListSocialElectrician(workOrder.getId(), socialWorkOrder.getId());
        if (CollectionUtils.isNotEmpty(mapListSocialElectrician)) {
            for (Map<String, Object> mapSocialElectrician : mapListSocialElectrician) {
                // 电工资质数据结构调整:[{"id":123, "name": "asdsafd"}]格式
                if (mapSocialElectrician.get("electrician_id") != null) {
                    Long electricianId = Long.valueOf(mapSocialElectrician.get("electrician_id").toString());
                    List<Map<String, Object>> electricianCertificateList = spsElectricianCertificateDao.queryListMap(electricianId);
                    List<Map<String, Object>> newElectricianCertificateList = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(electricianCertificateList)) {
                        for (Map<String, Object> mapElectricianCertificate : electricianCertificateList) {
                            Map<String, Object> newMapElectricianCertificate = new HashMap<>();
                            newMapElectricianCertificate.put("id", mapElectricianCertificate.get("certificateId"));
                            newMapElectricianCertificate.put("name", mapElectricianCertificate.get("name"));
                            newElectricianCertificateList.add(newMapElectricianCertificate);
                        }
                    }
                    mapSocialElectrician.put("qualifications", JSON.toJSONString(newElectricianCertificateList));
                }
            }
        }
        result.setSocialElectricianList(mapListSocialElectrician);

        return result;
    }

    @Override
    @Transactional
    public int confirm(SocialWorkOrder socialWorkOrder, List<ElectricianWorkOrder> electricianWorkOrderList,
                       Integer bstatus, Integer nstatus) {
        int c = electricianWorkOrderDao.confirmBatch(electricianWorkOrderList.toArray(new ElectricianWorkOrder[electricianWorkOrderList.size()]));
        if (c > 0 && socialWorkOrder != null) {
            socialWorkOrderDao.update(socialWorkOrder);

            electricianWorkOrderDao.updateBySocialWorkOrderId(socialWorkOrder.getId(), bstatus, nstatus);
        }
        return c;
    }

    /**
     * 计算电工确认比例
     *
     * @param workOrderId
     * @param quantity
     * @return
     */
    private Map<String, String> getElectricianWorkOrderRatio(Long workOrderId, Long socialWorkOrderId, Integer quantity) {
        Integer recruitNumber = 0, responseNumber = 0, liquidateNumber = 0, unconfirmedNumber = 0;
        Map<String, Object> socialWorkOrderVOMap = new HashMap<>();
        socialWorkOrderVOMap.put("type", 2);
        socialWorkOrderVOMap.put("workOrderId", workOrderId);
        socialWorkOrderVOMap.put("socialWorkOrderId", socialWorkOrderId);
        List<ElectricianWorkOrder> entityList = electricianWorkOrderDao.queryList(socialWorkOrderVOMap);
        if (CollectionUtils.isNotEmpty(entityList)) {
            // 响应人数
            responseNumber = entityList.size();
            // 招募人数,结算人数
            for (ElectricianWorkOrder electricianWorkOrder : entityList) {
                // 未确认人数
                if (electricianWorkOrder.getStatus().equals(ElectricianWorkOrderStatus.UNCONFIRMED.getValue())) {
                    unconfirmedNumber++;
                }
                // 招募人数
                if (electricianWorkOrder.getStatus() > ElectricianWorkOrderStatus.UNCONFIRMED.getValue()) {
                    recruitNumber++;
                }
                // 结算人数
                if (electricianWorkOrder.getStatus().equals(ElectricianWorkOrderStatus.LIQUIDATED.getValue())) {
                    liquidateNumber++;
                }
            }
        }

        Map<String, String> result = new HashMap<>();
        result.put("responseNumber", String.format("%s/%s", responseNumber, quantity));
        result.put("recruitNumber", String.format("%s/%s", recruitNumber, quantity));
        result.put("liquidateNumber", String.format("%s/%s", liquidateNumber, quantity));
        result.put("unconfirmedNumber", "" + unconfirmedNumber);
        result.put("recruitCount", "" + recruitNumber);
        return result;
    }

    @Override
    public Double getSocialElectricianFee(Long id) {
        return socialWorkOrderDao.getSocialElectricianFee(id);
    }

    @Override
    public List<Long> getAfterExpiryTimeAndUnPayOrders() {
        return socialWorkOrderDao.getAfterExpiryTimeAndUnPayOrders();
    }

    @Override
    public List<Long> getBeforeExpiryTimeAndUnPayOrders(int hour) {
        return socialWorkOrderDao.getBeforeExpiryTimeAndUnPayOrders(hour);
    }

    @Override
    public List<Long> getAfterExpiryTimeAndPaiedOrders() {
        return socialWorkOrderDao.getAfterExpiryTimeAndPaiedOrders();
    }

    @Override
    public List<Long> getFinishOrders() {
        return socialWorkOrderDao.getFinishOrders();
    }

    @Override
    public ElectricianResult electricianWorkOrder(ElectricianReqData electricianReqData) {
        ElectricianResult result = new ElectricianResult();

        Long uid = electricianReqData.getUid();
        GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
        if (!getUserInfoResult.isSuccess()) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("登录信息异常");
            return result;
        }
        UserInfo userInfo = getUserInfoResult.getMemberInfo();
        if (userInfo == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("登录信息异常");
            return result;
        }
        SocialWorkOrder socialWorkOrder = socialWorkOrderDao.getByIdAndCompanyId(null, electricianReqData.getOrderId(), userInfo.getCompanyId());
        if (socialWorkOrder == null) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage("未匹配到社会工单信息");
            return result;
        }

        // 响应人数,确认人数
        Map<String, String> map = getElectricianWorkOrderRatio(socialWorkOrder.getWorkOrderId(), socialWorkOrder.getId(), socialWorkOrder.getQuantity());
        result.setRecruitNumber(Integer.valueOf(map.get("recruitCount")));
        result.setTotleNumber(socialWorkOrder.getQuantity());

        result.setTitle(socialWorkOrder.getTitle());

        // 已经接单的社会电工信息
        List<Map<String, Object>> mapListSocialElectrician = electricianWorkOrderDao.queryMapListSocialElectrician(socialWorkOrder.getWorkOrderId(), socialWorkOrder.getId());
        result.setSocialElectricianList(mapListSocialElectrician);
        return result;
    }

    @Override
    public List<SocialWorkOrder> selectListByCondition(Map<String, Object> queryMap) {
        return socialWorkOrderDao.selectListByCondition(queryMap);
    }

    /**
     * 查询需要退款的社会工单
     * 所有已取消、已结算的社会工单（已支付且未退款）
     */
    @Override
    public List<String> scanNeedRefundOrders(int limit) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(SocialWorkOrderStatus.CANCEL.getValue());
        statusList.add(SocialWorkOrderStatus.LIQUIDATED.getValue());
        map.put("statusList", statusList);
        map.put("payStatus", PayStatus.SUCCESS.getValue());
        map.put("refundStatus", SocialWorkOrderRefundStatus.NORMAL.getValue());
        map.put("limit", limit);
        return socialWorkOrderDao.scanNeedRefundOrders(map);
    }

}
