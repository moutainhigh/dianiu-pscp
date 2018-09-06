package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.cs.bean.needs.DetailsReqData;
import com.edianniu.pscp.cs.bean.needs.DetailsResult;
import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needsorder.NeedsOrderReqData;
import com.edianniu.pscp.cs.bean.needsorder.NeedsOrderResult;
import com.edianniu.pscp.cs.service.dubbo.CustomerNeedsOrderInfoService;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.OrderIdPrefix;
import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;
import com.edianniu.pscp.sps.bean.needsorder.InitDataReqData;
import com.edianniu.pscp.sps.bean.needsorder.InitDataResult;
import com.edianniu.pscp.sps.bean.project.ProjectInfo;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;
import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.Company;
import com.edianniu.pscp.sps.service.DataDictionaryDetailsService;
import com.edianniu.pscp.sps.service.EngineeringProjectService;
import com.edianniu.pscp.sps.service.SpsCompanyService;
import com.edianniu.pscp.sps.service.SpsToolEquipmentService;
import com.edianniu.pscp.sps.service.dubbo.NeedsOrderInfoService;
import com.edianniu.pscp.sps.util.BizUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: NeedsOrderServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-09-26 16:27
 */
@Service
@Repository("needsOrderInfoService")
public class NeedsOrderServiceImpl implements NeedsOrderInfoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("spsCompanyService")
    private SpsCompanyService spsCompanyService;
    @Autowired
    @Qualifier("spsToolEquipmentService")
    private SpsToolEquipmentService spsToolEquipmentService;
    @Autowired
    @Qualifier("dataDictionaryDetailsService")
    private DataDictionaryDetailsService dataDictionaryDetailsService;
    @Autowired
    @Qualifier("customerNeedsOrderInfoService")
    private CustomerNeedsOrderInfoService customerNeedsOrderInfoService;
    @Autowired
    @Qualifier("engineeringProjectService")
    private EngineeringProjectService engineeringProjectService;
    @Autowired
    @Qualifier("needsInfoService")
    private NeedsInfoService needsInfoService;


    @Override
    public InitDataResult initData(InitDataReqData initDataReqData) {
        InitDataResult result = new InitDataResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(initDataReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_401, "登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.set(ResultCode.ERROR_401, "登录信息异常");
                return result;
            }

            if (StringUtils.isBlank(initDataReqData.getOrderId())) {
                result.set(ResultCode.ERROR_401, "订单编号不能为空");
                return result;
            }

            // 获取响应订单信息
            NeedsOrderReqData needsOrderReqData = new NeedsOrderReqData();
            needsOrderReqData.setUid(userInfo.getUid());
            needsOrderReqData.setCompanyId(userInfo.getCompanyId());
            needsOrderReqData.setOrderId(initDataReqData.getOrderId());
            NeedsOrderResult needsOrderResult = customerNeedsOrderInfoService.getNeedsOrderInfo(needsOrderReqData);
            if (!needsOrderResult.isSuccess()) {
                result.set(needsOrderResult.getResultCode(), needsOrderResult.getResultMessage());
                return result;
            }

            NeedsOrderInfo needsOrderInfo = needsOrderResult.getNeedsOrderInfo();
            if(needsOrderInfo == null){
                result.set(ResultCode.ERROR_401, "需求订单不存在");
                return result;
            }
            if (!needsOrderInfo.getStatus().equals(NeedsOrderStatus.WAITING_QUOTE.getValue())) {
                result.set(ResultCode.ERROR_401, "勘察订单只能在待报价派单");
                return result;
            }

            // 项目信息
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("needsId", needsOrderInfo.getNeedsId());
            ProjectInfo projectInfo = engineeringProjectService.getProjectInfoByNeedsId(queryMap);
            if (projectInfo == null) {
                result.set(ResultCode.ERROR_401, "项目信息不存在");
                return result;
            }

            CompanyVO projectInfoVO = new CompanyVO();
            projectInfoVO.setId(projectInfo.getId());
            projectInfoVO.setName(projectInfo.getName());
            result.setProjectInfo(projectInfoVO);

            // 需求信息
            DetailsReqData detailsReqData = new DetailsReqData();
            detailsReqData.setNeedsId(needsOrderInfo.getNeedsId());
            detailsReqData.setUid(userInfo.getUid());
            DetailsResult detailsResult = needsInfoService.query(detailsReqData);
            if (!detailsResult.isSuccess()) {
                result.set(ResultCode.ERROR_401, "需求信息不存在");
                return result;
            }
            NeedsVO needs = detailsResult.getNeeds();

            // 服务商公司信息
            Company customer = spsCompanyService.getCompanyById(needs.getCompanyId());
            if (customer == null) {
                result.set(ResultCode.ERROR_401, "客户信息不存在");
                return result;
            }

            // 客户公司信息
            CompanyVO customerInfo = new CompanyVO();
            customerInfo.setId(customer.getId());
            customerInfo.setName(customer.getName());
            customerInfo.setAddress(customer.getAddress());
            customerInfo.setContacts(customer.getContacts());
            customerInfo.setContactNumber(customer.getContactTel());
            result.setCustomerInfo(customerInfo);

            // 服务商公司信息
            Company company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
            if (company == null) {
                result.set(ResultCode.ERROR_401, "公司信息不存在");
                return result;
            }

            // 服务商公司信息
            CompanyVO companyInfo = new CompanyVO();
            companyInfo.setId(company.getId());
            companyInfo.setName(company.getName());
            companyInfo.setAddress(company.getAddress());
            companyInfo.setContacts(company.getContacts());
            companyInfo.setContactNumber(company.getContactTel());
            result.setCompanyInfo(companyInfo);

            String orderId = BizUtils.getOrderId(OrderIdPrefix.WORK_ORDER_PREFIX);
            result.setOrderId(orderId);

            List<IdentificationsVO> identificationsVOList = dataDictionaryDetailsService.selectAllIdentifications();
            result.setIdentificationList(identificationsVOList);

            List<SafetyMeasuresVO> safetyMeasuresVOList = dataDictionaryDetailsService.selectAllSafetyMeasures();
            result.setSafetyMeasuresList(safetyMeasuresVOList);

            List<ToolEquipmentVO> toolEquipmentVOList = spsToolEquipmentService.selectAllToolEquipmentVOByCompanyId(company.getId());
            result.setToolEquipmentList(toolEquipmentVOList);

            // 工单类型
            result.setType(WorkOrderType.PROSPECTING.getValue());
            result.setTypeName(WorkOrderType.PROSPECTING.getDesc());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getElectricianMemberInfo :{}", e);
        }
        return result;
    }
}
