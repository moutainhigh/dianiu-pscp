package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderTypeInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.OrderIdPrefix;
import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;
import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.*;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.SelectDataVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.Company;
import com.edianniu.pscp.sps.domain.SysUserEntity;
import com.edianniu.pscp.sps.service.DataDictionaryDetailsService;
import com.edianniu.pscp.sps.service.SpsCompanyService;
import com.edianniu.pscp.sps.service.SpsToolEquipmentService;
import com.edianniu.pscp.sps.service.WorkOrderService;
import com.edianniu.pscp.sps.service.dubbo.WorkOrderInfoService;
import com.edianniu.pscp.sps.util.BizUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: WorkOrderInfoServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-12 16:47
 */
@Service
@Repository("workOrderInfoService")
public class WorkOrderInfoServiceImpl implements WorkOrderInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianWorkLogInfoServiceImpl.class);

    @Autowired
    @Qualifier("workOrderService")
    private WorkOrderService workOrderService;
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

    @Override
    public ListResult list(ListReqData listReqData) {
        ListResult result = new ListResult();
        try {
            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(listReqData, listQuery);

            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(listReqData.getUid());
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户不存在");
                return result;
            }

            UserInfo userInfo = userInfoResult.getMemberInfo();
            boolean isFacilitator = userInfo.isFacilitator();
            boolean isCustomer = userInfo.isCustomer();

            Company company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
            if (company == null || !company.getStatus().equals(CompanyAuthStatus.SUCCESS.getValue())) {
                result.setWorkOrderInfoList(new ArrayList<WorkOrderInfo>());
                result.setHasNext(false);
                result.setNextOffset(0);
                result.setTotalCount(0);
                return result;
            }
            if (isFacilitator) {
            	if (listQuery.getCompanyId() == null) {
                    listQuery.setCompanyId(userInfo.getCompanyId());
                }
			}
            if (isCustomer) { //操作人uid的指的是服务商的uid，如果是客户查询，则传递到customerUid之中
				listQuery.setMemberId(null);
				listQuery.setCustomerUid(listReqData.getUid());
			}

            String status = listReqData.getStatus();
            if (StringUtils.isNotBlank(status)) {
            	if (isFacilitator) {
            		if (!WorkOrderRequestType.isExist(status)) {
                        result.set(ResultCode.ERROR_402, "工单状态不存在");
                        return result;
                    }
                    WorkOrderRequestType type = WorkOrderRequestType.get(status);
                    listQuery.setStatus(type.getStatus(true));
				}
                if (isCustomer) {
					if (!WorkOrderRequestTypeByCus.isExist(status)) {
						result.set(ResultCode.ERROR_402, "工单状态不存在");
                        return result;
					}
					WorkOrderRequestTypeByCus typeByCus = WorkOrderRequestTypeByCus.get(status);
					listQuery.setStatus(typeByCus.getStatus());
					String subStutusName = listReqData.getSubStatus();
					if (StringUtils.isNotBlank(subStutusName)) {
						if (!typeByCus.isWokOrderStatusExist(subStutusName)) {
							result.set(ResultCode.ERROR_402, "工单状态不存在");
	                        return result;
						}
						WorkOrderStatus orderStatus = WorkOrderStatus.getWokOrderStatus(subStutusName);
						listQuery.setSubStatus(orderStatus.getValue());
					}
				}
            }

            if (listReqData.getListQueryRequestInfo() != null) {
                listQuery.setName(listReqData.getListQueryRequestInfo().getName());
                listQuery.setProjectName(listReqData.getListQueryRequestInfo().getProjectName());
                listQuery.setCustomerName(listReqData.getListQueryRequestInfo().getCustomerName());
                listQuery.setCompanyName(listReqData.getListQueryRequestInfo().getCompanyName());
            }

            PageResult<WorkOrderInfo> pageResult = workOrderService.selectPageWorkOrderInfo(listQuery);
            result.setWorkOrderInfoList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }

    /**
     * 工单取消
     *
     * @param id
     * @param userInfo
     */
    @Override
    public CancelResult cancelWorkOrder(Long id, SysUserEntity userInfo) {
        CancelResult result = new CancelResult();
        try {
            result = workOrderService.cancelWorkOrder(id, userInfo);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }

    /**
     * 获取工单查看/编辑信息
     *
     * @param orderId
     * @param uid
     * @return
     */
    @Override
    public ViewResult getWorkOrderViewInfo(String orderId, Long uid) {
        ViewResult result = new ViewResult();
        try {
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

            result = workOrderService.getWorkOrderViewInfo(orderId, userInfo);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }

    /**
     * 获取工单查看/编辑信息（服务商APP）
     *
     * @param orderId
     * @param uid
     * @return
     */
    @Override
    public FacilitatorAppDetailsResult getFacilitatorAppDetails(String orderId, Long uid) {
        FacilitatorAppDetailsResult result = new FacilitatorAppDetailsResult();
        try {
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
            result = workOrderService.getFacilitatorAppDetails(orderId, userInfo);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("FacilitatorAppDetails:{}", e);
        }
        return result;
    }

    /**
     * 保存(派单)、修改工单信息
     * @param saveOrUpdateInfo
     * @return
     */
    @Override
    public SaveOrUpdateResult saveOrUpdate(SaveOrUpdateInfo saveOrUpdateInfo) {
        SaveOrUpdateResult result = new SaveOrUpdateResult();
        try {
            if (saveOrUpdateInfo.getId() == null) {
                // 保存
                result = workOrderService.saveWorkOrderInfo(saveOrUpdateInfo);
            } else {
                // 修改
                result = workOrderService.updateWorkOrderInfo(saveOrUpdateInfo);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }

    /**
     * 派单数据初始化
     *
     * @return
     */
    @Override
    public InitDataResult initData(InitDataReqData initDataReqData) {
        InitDataResult result = new InitDataResult();

        GetUserInfoResult userInfoResult = userInfoService.getUserInfo(initDataReqData.getUid());
        if (!userInfoResult.isSuccess()) {
            result.set(ResultCode.ERROR_401, "用户信息不能为空");
            return result;
        }

        UserInfo userInfo = userInfoResult.getMemberInfo();
        if (userInfo == null) {
            result.set(ResultCode.ERROR_401, "用户信息不能为空");
            return result;
        }

        Long companyId = userInfo.getCompanyId();
        if (companyId == null) {
            result.set(ResultCode.ERROR_401, "没有找到公司信息");
            return result;
        }

        Company company = spsCompanyService.getCompanyById(companyId);
        if(company == null){
            result.set(ResultCode.ERROR_401, "没有找到公司信息");
            return result;
        }

        result.setName(company.getName());
        result.setContacts(company.getContacts());
        String phone = company.getMobile();
        if (StringUtils.isBlank(phone)) {
            phone = company.getPhone();
        }
        result.setPhone(phone);

        String orderId = BizUtils.getOrderId(OrderIdPrefix.WORK_ORDER_PREFIX);
        result.setOrderId(orderId);

        List<IdentificationsVO> identificationsVOList = dataDictionaryDetailsService.selectAllIdentifications();
        result.setIdentificationList(identificationsVOList);

        List<SafetyMeasuresVO> safetyMeasuresVOList = dataDictionaryDetailsService.selectAllSafetyMeasures();
        result.setSafetyMeasuresList(safetyMeasuresVOList);

        List<ToolEquipmentVO> toolEquipmentVOList = spsToolEquipmentService.selectAllToolEquipmentVOByCompanyId(company.getId());
        result.setToolEquipmentList(toolEquipmentVOList);

        // 工单类型列表数据
        List<WorkOrderTypeInfo> orderTypeList = WorkOrderType.buildWorkOrderTypeList();
        result.setOrderTypeList(orderTypeList);

        return result;
    }

    /**
     * 获取工单下拉数据列表
     *
     * @param selectDataReqData
     * @return
     */
    @Override
    public SelectDataResult listSelectData(SelectDataReqData selectDataReqData) {
        SelectDataResult result = new SelectDataResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(selectDataReqData.getUid());
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

            Company company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
            if (company == null || !company.getStatus().equals(2)) {
                result.setWorkOrderList(new ArrayList<SelectDataVO>());
                result.setHasNext(false);
                result.setNextOffset(0);
                result.setTotalCount(0);
                return result;
            }

            List<Integer> status = new ArrayList<>();
            status.add(WorkOrderStatus.UNCONFIRMED.getValue());
            status.add(WorkOrderStatus.CONFIRMED.getValue());
            status.add(WorkOrderStatus.EXECUTING.getValue());

            SelectDataQuery selectDataQuery = new SelectDataQuery();
            BeanUtils.copyProperties(selectDataReqData, selectDataQuery);

            selectDataQuery.setCompanyId(userInfo.getCompanyId());
            selectDataQuery.setStatus(status.toArray(new Integer[]{}));

            PageResult<SelectDataVO> pageResult = workOrderService.queryPageSelectData(selectDataQuery);
            result.setWorkOrderList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }

}
