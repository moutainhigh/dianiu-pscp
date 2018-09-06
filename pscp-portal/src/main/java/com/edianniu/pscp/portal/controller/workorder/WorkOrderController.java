package com.edianniu.pscp.portal.controller.workorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.company.CompanyCustomerInfo;
import com.edianniu.pscp.mis.bean.defectrecord.DetailsReqData;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordDetailsVO;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderType;
import com.edianniu.pscp.mis.bean.workorder.WorkOrderTypeInfo;
import com.edianniu.pscp.mis.bean.worksheetreport.ReportType;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportDetailsVO;
import com.edianniu.pscp.mis.bean.worksheetreport.vo.ReportListVO;
import com.edianniu.pscp.mis.service.dubbo.CompanyCustomerInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderReportInfoService;
import com.edianniu.pscp.portal.bean.vo.WorkOrderListVO;
import com.edianniu.pscp.portal.commons.ResultCode;
import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.entity.CompanyEntity;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.CompanyService;
import com.edianniu.pscp.portal.utils.*;
import com.edianniu.pscp.sps.bean.OrderIdPrefix;
import com.edianniu.pscp.sps.bean.certificate.CertificateVOResult;
import com.edianniu.pscp.sps.bean.customer.CustomerResult;
import com.edianniu.pscp.sps.bean.electrician.ElectricianVOResult;
import com.edianniu.pscp.sps.bean.identifications.IdentificationsVOResult;
import com.edianniu.pscp.sps.bean.project.ListProjectsReqData;
import com.edianniu.pscp.sps.bean.project.ListProjectsResult;
import com.edianniu.pscp.sps.bean.safetymeasures.SafetyMeasuresVOResult;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitInfo;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.recruit.RecruitResult;
import com.edianniu.pscp.sps.bean.toolequipment.ToolEquipmentVOResult;
import com.edianniu.pscp.sps.bean.workorder.chieforder.*;
import com.edianniu.pscp.sps.service.dubbo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * ClassName: WorkOrderController CreateTime: 2017-05-05 11:49
 *
 * @author : tandingbo
 */
@Controller
@RequestMapping("/cp/workorder/chieforder")
public class WorkOrderController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(WorkOrderController.class);

	@Autowired
	private WorkOrderInfoService workOrderInfoService;
	@Autowired
	private SpsElectricianInfoService spsElectricianInfoService;
	@Autowired
	private SpsCompanyCustomerInfoService spsCompanyCustomerInfoService;
	@Autowired
	private EngineeringProjectInfoService engineeringProjectInfoService;
	@Autowired
	private SpsCertificateInfoService spsCertificateInfoService;
	@Autowired
	private SpsToolEquipmentInfoService spsToolEquipmentInfoService;
	@Autowired
	private DataDictionaryDetailsInfoService dataDictionaryDetailsInfoService;
	@Autowired
	private SocialWorkOrderInfoService socialWorkOrderInfoService;
	@Autowired
	private WorkOrderDefectRecordInfoService workOrderDefectRecordInfoService;
	@Autowired
	private WorkOrderReportInfoService workOrderReportInfoService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyCustomerInfoService companyCustomerInfoService;
	

	@RequestMapping("/index.html")
	public String list(Model model, String page) {
		if (!StringUtils.isBlank(page)) {
			model.addAttribute("page", page);
		}
		return "cp/workorder.html";
	}
	@RequestMapping("/cus_index.html")
	public String cusList(Model model, String page) {
		if (!StringUtils.isBlank(page)) {
			model.addAttribute("page", page);
		}
		return "cp/cus_workorder.html";
	}

	/**
	 * 客户工单查询  状态下拉列表
	 * @param status 主查詢類型  "ongoing":進行中   "finished":已结束
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/subStatus")
	public R customerSubTypes(String status){
		List<String> result = new ArrayList<>();
		if (StringUtils.isNotBlank(status)) {
			if (!WorkOrderRequestTypeByCus.isExist(status)) {
				return R.error();
			}
			WorkOrderRequestTypeByCus typeByCus = WorkOrderRequestTypeByCus.get(status);
			WorkOrderStatus[] workOrderStatusArray = typeByCus.getWorkOrderStatus();
			for (WorkOrderStatus workOrderStatus : workOrderStatusArray) {
				result.add(workOrderStatus.getName());
			}
		}
		return R.ok().put("result", result);
	}
	
	/**
	 * 获取客户的所有服务商列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/customerCompanys")
	public R getCustomerCompanys(){
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		
		boolean isCustomer = getUser().isCustomer();
		Long userId = userEntity.getUserId();
		List<CompanyCustomerInfo> list = new ArrayList<CompanyCustomerInfo>();
		if (isCustomer) {
			list = companyCustomerInfoService.getInfo(userId, null);
		}
		return R.ok().put("list", list);
	}
	
	/**
	 * 列表查询
	 * 服务商/客户适用
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	@RequiresPermissions("cp:workorder:list")
	public R list(@ModelAttribute ListQueryRequestInfo queryRequest,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer limit) {
		ListResult listResult = new ListResult();
		ListReqData listReqData = new ListReqData();
		PageUtils pageUtil = new PageUtils(listResult.getWorkOrderInfoList(), 0, limit, page);
		boolean isCustomer = getUser().isCustomer();
		boolean isFacilitator = getUser().isFacilitator();

		if (queryRequest == null) {
			queryRequest = new ListQueryRequestInfo();
		}

		// 查询主状态
		String status = queryRequest.getStatus();
		if (StringUtils.isBlank(status)) {
			if (isFacilitator) {
				queryRequest.setStatus(WorkOrderRequestType.UN_CONFIRM.getValue());
			}
			if (isCustomer) {
				queryRequest.setStatus(WorkOrderRequestTypeByCus.ONGOING.getValue());
			}
		} else {
			if (isFacilitator && ! WorkOrderRequestType.isExist(status)) {
				return R.ok().put("page", pageUtil);
			}
			if (isCustomer && ! WorkOrderRequestTypeByCus.isExist(status)) {
				return R.ok().put("page", pageUtil);
			}
		}
		listReqData.setStatus(queryRequest.getStatus());

		// 查询子状态-->客户查询时
		if (isCustomer) {
			WorkOrderRequestTypeByCus typeByCus = WorkOrderRequestTypeByCus.get(status);
			String subStutusName = queryRequest.getSubStatus();
			if (StringUtils.isNotBlank(subStutusName) && ! typeByCus.isWokOrderStatusExist(subStutusName)) {
				return R.ok().put("page", pageUtil);
			}
			listReqData.setSubStatus(subStutusName);
			listReqData.setCompanyName(queryRequest.getCompanyName());
		}

		if (StringUtils.isNoneBlank(queryRequest.getStartTime())) {
			Date startTime = DateUtils.parse(queryRequest.getStartTime());
			if (startTime == null) {
				return R.ok().put("page", pageUtil);
			}
			listReqData.setStartTime(DateUtils.getStartDate(startTime));
		}
		if (StringUtils.isNoneBlank(queryRequest.getEndTime())) {
			Date endTime = DateUtils.parse(queryRequest.getEndTime());
			if (endTime == null) {
				return R.ok().put("page", pageUtil);
			}
			listReqData.setEndTime(DateUtils.getEndDate(endTime));
		}

		int offset = (page - 1) * limit;
		listReqData.setOffset(offset);
		listReqData.setPageSize(limit);
		listReqData.setListQueryRequestInfo(queryRequest);
		listReqData.setUid(ShiroUtils.getUserEntity().getUserId());

		listResult = workOrderInfoService.list(listReqData);
		// 返回数据类型转换
		List<WorkOrderListVO> workOrderListVOList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(listResult.getWorkOrderInfoList())) {
			for (WorkOrderInfo workOrderInfo : listResult.getWorkOrderInfoList()) {
				WorkOrderListVO workOrderListVO = new WorkOrderListVO();
				BeanUtils.copyProperties(workOrderInfo, workOrderListVO);
				workOrderListVOList.add(workOrderListVO);
			}
		}
		pageUtil = new PageUtils(workOrderListVOList, listResult.getTotalCount(), limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 详情
	 * 编辑页面数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/view/{orderId}")
	@RequiresPermissions("cp:workorder:view")
	public R editView(@PathVariable("orderId") String orderId) {
		SysUserEntity userInfo = ShiroUtils.getUserEntity();
		ViewResult result = workOrderInfoService.getWorkOrderViewInfo(orderId, userInfo.getUserId());
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("result", result);
	}

	/**
	 * 新增/编辑
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	@RequiresPermissions("cp:workorder:save")
	public R saveOrUpdate(@RequestBody SaveOrUpdateReqData reqData) {
		// 社会工单招募信息
		JSONArray recruitList = reqData.getRecruitList();
		List<RecruitInfo> socialWorkOrderInfoList = analysisRecruitData(recruitList);

		// 企业电工工单信息
		List<ElectricianWorkOrderInfo> electricianWorkOrderInfoList = new ArrayList<>();
		JSONArray electricianWorkOrderList = reqData.getElectricianWorkOrderList();
		if (electricianWorkOrderList != null && electricianWorkOrderList.size() > 0) {
			int size = electricianWorkOrderList.size();
			for (int i = 0; i < size; i++) {
				List<Long> electricianIdList = new ArrayList<>();
				ElectricianWorkOrderInfo electricianWorkOrderInfo = new ElectricianWorkOrderInfo();

				JSONObject jsonObject = electricianWorkOrderList.getJSONObject(i);

				JSONObject component = jsonObject.getJSONObject("component");
				JSONArray electricianArray = component.getJSONArray("electrician");
				if (electricianArray != null && electricianArray.size() > 0) {
					for (int j = 0; j < electricianArray.size(); j++) {
						JSONObject electricianJson = electricianArray.getJSONObject(j);
						Long electricianId = electricianJson.getLong("id");
						electricianIdList.add(electricianId);
					}
				}
				electricianWorkOrderInfo.setElectricianIdList(electricianIdList);

				electricianWorkOrderInfo.setCheckOption(component.getString("projectName"));
				electricianWorkOrderInfo.setCheckOptionId(component.getString("projectId"));

				electricianWorkOrderInfoList.add(electricianWorkOrderInfo);
			}
			logger.debug("electricianWorkOrderList:{}", JSON.toJSONString(electricianWorkOrderInfoList));
		}

		SaveOrUpdateInfo saveOrUpdateInfo = new SaveOrUpdateInfo();
		BeanUtils.copyProperties(reqData, saveOrUpdateInfo);

		saveOrUpdateInfo.setUid(ShiroUtils.getUserEntity().getUserId());
		saveOrUpdateInfo.setSocialRecruitList(socialWorkOrderInfoList);
		saveOrUpdateInfo.setElectricianWorkOrderInfoList(electricianWorkOrderInfoList);
		if (StringUtils.isNoneBlank(reqData.getToolequipmentInfo())) {
			try {
				// JSON数据格式检查
				JSONArray.parseArray(reqData.getToolequipmentInfo());
			} catch (Exception e) {
				logger.error("json parse exception: {}", e);
				return R.error(ResultCode.EXIST_LOGIN_NAME, "携带机械或设备内容解析出错");
			}
			saveOrUpdateInfo.setToolequipmentInfo(reqData.getToolequipmentInfo());
		}
		SaveOrUpdateResult result = workOrderInfoService.saveOrUpdate(saveOrUpdateInfo);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("result", result);
	}

	/**
	 * 招募社会电工
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recruit")
	@RequiresPermissions("cp:workorder:recruit")
	public R recruit(@RequestBody JSONObject jsonReq) {

		// 社会工单招募信息
		JSONArray recruitList = jsonReq.getJSONArray("recruitList");
		List<RecruitInfo> socialWorkOrderInfoList = analysisRecruitData(recruitList);

		RecruitReqData recruitReqData = new RecruitReqData();
		recruitReqData.setWorkOrderId(jsonReq.getLong("id"));
		recruitReqData.setOrderId(jsonReq.getString("orderId"));
		recruitReqData.setUid(ShiroUtils.getUserEntity().getUserId());
		recruitReqData.setRecruitInfoList(socialWorkOrderInfoList);

		RecruitResult result = socialWorkOrderInfoService.recruit(recruitReqData);
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("result", result);
	}

	/**
	 * 社会工单招募信息
	 *
	 * @param recruitList
	 * @return
	 */
	private List<RecruitInfo> analysisRecruitData(JSONArray recruitList) {
		List<RecruitInfo> recruitInfoList = new ArrayList<>();
		if (recruitList != null && recruitList.size() > 0) {
			int size = recruitList.size();
			for (int i = 0; i < size; i++) {
				RecruitInfo recruitInfo = new RecruitInfo();

				JSONObject jsonObject = recruitList.getJSONObject(i);
				JSONObject component = jsonObject.getJSONObject("component");

				JSONArray qualifications = component.getJSONArray("qualifications");
				if (qualifications != null && qualifications.size() > 0) {
					List<Map<String, Object>> mapList = new ArrayList<>();
					for (int j = 0; j < qualifications.size(); j++) {
						JSONObject qualification = qualifications.getJSONObject(j);
						Map<String, Object> map = new HashMap<>();
						map.put("id", qualification.getLongValue("id"));
						mapList.add(map);
					}
					recruitInfo.setQualifications(JSON.toJSONString(mapList));
				}

				recruitInfo.setFee(component.getDouble("fee"));
				recruitInfo.setTitle(component.getString("title"));
				recruitInfo.setContent(component.getString("content"));
				recruitInfo.setQuantity(component.getInteger("quantity"));
				recruitInfo.setExpiryTime(component.getString("expiryTime"));
				recruitInfo.setEndWorkTime(component.getString("endWorkTime"));
				recruitInfo.setBeginWorkTime(component.getString("beginWorkTime"));

				recruitInfoList.add(recruitInfo);
			}
		}
		return recruitInfoList;
	}

	/**
	 * 服务商、OrderId信息
	 */
	@ResponseBody
	@RequestMapping(value = "/facilitator")
	public R facilitator() {
		Map<String, Object> result = new HashMap<>();
		SysUserEntity userInfo = ShiroUtils.getUserEntity();
		CompanyEntity companyEntity = companyService.getCompanyByUid(userInfo.getUserId());
		if (companyEntity != null) {
			result.put("name", companyEntity.getName());
			result.put("contacts", companyEntity.getContacts());
			String phone = companyEntity.getMobile();
			if (StringUtils.isBlank(phone)) {
				phone = companyEntity.getPhone();
			}
			result.put("phone", phone);
			String orderId = BizUtils.getOrderId(OrderIdPrefix.WORK_ORDER_PREFIX);
			result.put("orderId", orderId);
		}
		return R.ok().put("facilitator", JSON.toJSON(result));
	}

	/**
	 * 客户信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/customer")
	public R customer() {
		SysUserEntity userInfo = ShiroUtils.getUserEntity();
		CustomerResult result = spsCompanyCustomerInfoService.selectCustomerByCompanyId(userInfo.getCompanyId());
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("customer", result.getCustomerList());
	}

	/**
	 * 项目信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/project/{customerId}")
	public R project(@PathVariable("customerId") Long customerId) {
		SysUserEntity userInfo = ShiroUtils.getUserEntity();
		ListProjectsReqData reqData = new ListProjectsReqData();
		reqData.setCustomerId(customerId);
		/* reqData.setCompanyId(userInfo.getCompanyId()); */
		reqData.setUid(userInfo.getUserId());
		reqData.setIncludeExpire(true);
		ListProjectsResult result = engineeringProjectInfoService.getListByCompanyIdAndCustomerId(reqData);
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("project", result.getProjectList());
	}

	/**
	 * 企业电工信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/electrician")
	public R electrician() {
		SysUserEntity userInfo = ShiroUtils.getUserEntity();
		ElectricianVOResult result = spsElectricianInfoService.selectElectricianVOByCompanyId(userInfo.getUserId());
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("electrician", result.getElectricianList());
	}

	/**
	 * 电工资质信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/certificate")
	public R certificate() {
		CertificateVOResult result = spsCertificateInfoService.selectAllCertificateVO();
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("certificate", result.getCertificateList());
	}

	/**
	 * 危险有害因数辨识
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/identifications")
	public R hazardFactorIdentifications() {
		IdentificationsVOResult result = dataDictionaryDetailsInfoService.selectAllIdentifications();
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("identifications", result.getIdentificationsVOList());
	}

	/**
	 * 安全措施
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/safetymeasures")
	public R safetyMeasures() {
		SafetyMeasuresVOResult result = dataDictionaryDetailsInfoService.selectAllSafetyMeasures();
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("safetyMeasures", result.getSafetyMeasuresVOList());
	}

	/**
	 * 携带机械或设备
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toolequipment")
	public R carryingTools() {
		SysUserEntity userInfo = ShiroUtils.getUserEntity();
		ToolEquipmentVOResult result = spsToolEquipmentInfoService
				.selectAllToolEquipmentVOByCompanyId(userInfo.getCompanyId());
		if (!result.isSuccess()) {
			R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("toolequipment", result.getToolEquipments());
	}

	/**
	 * 项目缺陷记录
	 *
	 * @param projectId
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/defectrecord")
	public R defectRecordList(@RequestParam(required = false) Long projectId,
			@RequestParam(required = false) String orderId,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer limit) {
		if (projectId == null && StringUtils.isBlank(orderId)) {
			return R.error("请求参数不合法！");
		}

		Long uid = ShiroUtils.getUserId();
		int offset = (page - 1) * limit;

		com.edianniu.pscp.mis.bean.defectrecord.ListReqData reqData = new com.edianniu.pscp.mis.bean.defectrecord.ListReqData();
		reqData.setUid(uid);
		if (projectId != null) {
			reqData.setProjectId(projectId);
		}
		if (StringUtils.isNotBlank(orderId)) {
			reqData.setOrderId(orderId);
		}
		reqData.setOffset(offset);
		reqData.setPageSize(limit);
		com.edianniu.pscp.mis.bean.defectrecord.ListResult listResult = workOrderDefectRecordInfoService
				.pageListDefectRecord(reqData);

		Long count = listResult.getTotalCount();
		PageUtils pageUtil = new PageUtils(listResult.getDefectRecordList(), count.intValue(), limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 项目缺陷记录详情
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/defectrecord/{id}")
	public R defectRecordDetails(@PathVariable("id") Long id) {
		if (id == null) {
			return R.error("请选择一条记录");
		}

		DetailsReqData reqData = new DetailsReqData();
		reqData.setId(id);
		reqData.setUid(ShiroUtils.getUserId());
		com.edianniu.pscp.mis.bean.defectrecord.DetailsResult result = workOrderDefectRecordInfoService
				.getDefectRecord(reqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultMessage());
		}
		DefectRecordDetailsVO defectrecord = result.getDefectRecordDetails();
		// 为附件添加前缀
		if (null != defectrecord) {
			List<Map<String, Object>> attachmentList = defectrecord.getAttachmentList();
			attachmentList = addPrefixForFids(attachmentList);
		}

		return R.ok().put("defectrecord", defectrecord);
	}

	/**
	 * 巡视报告
	 * 
	 * @param orderId
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/patrol")
	public R patrolRecordList(@RequestParam String orderId,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer limit) {
		Long uid = ShiroUtils.getUserId();
		int offset = (page - 1) * limit;

		com.edianniu.pscp.mis.bean.worksheetreport.ListReqData reqData = new com.edianniu.pscp.mis.bean.worksheetreport.ListReqData();
		reqData.setUid(uid);
		reqData.setOffset(offset);
		reqData.setPageSize(limit);
		reqData.setOrderId(orderId);
		reqData.setType(ReportType.PATROL.getValue());

		com.edianniu.pscp.mis.bean.worksheetreport.ListResult listResult = workOrderReportInfoService
				.pageListReport(reqData);
		Long count = listResult.getTotalCount();
		PageUtils pageUtil = new PageUtils(listResult.getPatrolRecordList(), count.intValue(), limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 巡视报告详情
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/patrol/{id}")
	public R patrolRecordDetails(@PathVariable("id") Long id) {
		if (id == null) {
			return R.error("请选择一条记录");
		}
		com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData reqData = new com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData();
		reqData.setId(id);
		reqData.setType(ReportType.PATROL.getValue());
		com.edianniu.pscp.mis.bean.worksheetreport.DetailsResult result = workOrderReportInfoService
				.getWorkOrderReportById(reqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultMessage());
		}
		ReportDetailsVO patrolRecord = result.getPatrolRecordDetails();
		// 为附件添加附件
		if (null != patrolRecord) {
			List<Map<String, Object>> attachmentList = patrolRecord.getAttachmentList();
			attachmentList = addPrefixForFids(attachmentList);
		}

		return R.ok().put("patrolRecord", patrolRecord);
	}

	/**
	 * 勘察报告
	 * 
	 * @param orderId
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/survey")
	public R surveyRecordList(@RequestParam String orderId,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer limit) {
		Long uid = ShiroUtils.getUserId();
		int offset = (page - 1) * limit;

		com.edianniu.pscp.mis.bean.worksheetreport.ListReqData reqData = new com.edianniu.pscp.mis.bean.worksheetreport.ListReqData();
		reqData.setUid(uid);
		reqData.setOffset(offset);
		reqData.setPageSize(limit);
		reqData.setOrderId(orderId);
		reqData.setType(ReportType.SURVEY.getValue());

		com.edianniu.pscp.mis.bean.worksheetreport.ListResult listResult = workOrderReportInfoService
				.pageListReport(reqData);
		Long count = listResult.getTotalCount();

		List<ReportListVO> list = listResult.getSurveyRecordList();
		for (ReportListVO reportListVO : list) {
			List<Map<String, Object>> attachmentList = reportListVO.getAttachmentList();
			attachmentList = addPrefixForFids(attachmentList);
		}

		PageUtils pageUtil = new PageUtils(listResult.getSurveyRecordList(), count.intValue(), limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 勘察报告详情
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/survey/{id}")
	public R surveyRecordDetails(@PathVariable("id") Long id) {
		if (id == null) {
			return R.error("请选择一条记录");
		}
		com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData reqData = new com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData();
		reqData.setId(id);
		reqData.setType(ReportType.SURVEY.getValue());
		com.edianniu.pscp.mis.bean.worksheetreport.DetailsResult result = workOrderReportInfoService
				.getWorkOrderReportById(reqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultMessage());
		}
		ReportDetailsVO surveyDetails = result.getSurveyDetails();
		if (null != surveyDetails) {
			List<Map<String, Object>> attachmentList = surveyDetails.getAttachmentList();
			attachmentList = addPrefixForFids(attachmentList);
		}

		return R.ok().put("surveyRecord", result.getSurveyDetails());
	}

	/**
	 * 修试记录
	 *
	 * @param orderId
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/repairtest")
	public R repairTestRecordList(@RequestParam String orderId,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer limit) {
		Long uid = ShiroUtils.getUserId();
		int offset = (page - 1) * limit;

		com.edianniu.pscp.mis.bean.worksheetreport.ListReqData reqData = new com.edianniu.pscp.mis.bean.worksheetreport.ListReqData();
		reqData.setUid(uid);
		reqData.setOffset(offset);
		reqData.setPageSize(limit);
		reqData.setOrderId(orderId);
		reqData.setType(ReportType.REPAIR_TEST.getValue());

		com.edianniu.pscp.mis.bean.worksheetreport.ListResult listResult = workOrderReportInfoService
				.pageListReport(reqData);
		Long count = listResult.getTotalCount();
		PageUtils pageUtil = new PageUtils(listResult.getRepairTestRecordList(), count.intValue(), limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 修试记录详情
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/repairtest/{id}")
	public R repairTestDetails(@PathVariable("id") Long id) {
		if (id == null) {
			return R.error("请选择一条记录");
		}

		com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData reqData = new com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData();
		reqData.setId(id);
		reqData.setType(ReportType.REPAIR_TEST.getValue());
		com.edianniu.pscp.mis.bean.worksheetreport.DetailsResult result = workOrderReportInfoService
				.getWorkOrderReportById(reqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultMessage());
		}
		ReportDetailsVO repairtest = result.getRepairTestDetails();
		if (null != repairtest) {
			List<Map<String, Object>> attachmentList = repairtest.getAttachmentList();
			attachmentList = addPrefixForFids(attachmentList);
		}

		return R.ok().put("repairtest", result.getRepairTestDetails());
	}

	/**
	 * 工单类型列表数据
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/types")
	public R workOrderTypeList() {
		// 工单类型列表数据
		List<WorkOrderTypeInfo> orderTypeList = WorkOrderType.buildWorkOrderTypeList();
		return R.ok().put("types", orderTypeList);
	}

	/**
	 * 项目缺陷记录下拉数据
	 *
	 * @param projectId
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/defectrecord/selectdata")
	public R defectRecordSelectData(@RequestParam(required = false) Long projectId,
			@RequestParam(required = false) String orderId,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer limit) {
		if (projectId == null && StringUtils.isBlank(orderId)) {
			return R.error("请求参数不合法！");
		}

		Long uid = ShiroUtils.getUserId();
		com.edianniu.pscp.mis.bean.defectrecord.SelectDataReqData reqData = new com.edianniu.pscp.mis.bean.defectrecord.SelectDataReqData();
		reqData.setUid(uid);
		if (projectId != null) {
			reqData.setProjectId(projectId);
		}
		if (StringUtils.isNotBlank(orderId)) {
			reqData.setOrderId(orderId);
		}
		com.edianniu.pscp.mis.bean.defectrecord.SelectDataResult listResult = workOrderDefectRecordInfoService
				.getDefectRecordSelectData(reqData);
		if (!listResult.isSuccess()) {
			return R.error(listResult.getResultMessage());
		}
		return R.ok().put("defectRecordList", listResult.getDefectRecordList());
	}

	/**
	 * 图片前缀
	 */
	@Value(value = "${file.download.url}")
	private String prefix;

	/**
	 * 为附件fid加"http://192.168.1.251:8091/"前缀 并返回原图的缩略图路径
	 */
	private List<Map<String, Object>> addPrefixForFids(List<Map<String, Object>> attachmentList) {
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (Map<String, Object> map : attachmentList) {
				String fid = (String) map.get("fid");
				map.put("fid", prefix + fid);
			}
		}
		return attachmentList;
	}
}
