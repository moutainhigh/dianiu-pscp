package com.edianniu.pscp.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edianniu.pscp.cs.bean.engineeringproject.*;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.room.RoomListResult;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.service.dubbo.ProjectInfoService;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.service.dubbo.CompanyCustomerInfoService;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.portal.bean.req.ProjectInfosReq;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.utils.BizUtils;
import com.edianniu.pscp.portal.utils.ImageUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.sps.bean.Attachment;
import com.edianniu.pscp.sps.bean.customer.CustomerResult;
import com.edianniu.pscp.sps.bean.customer.vo.CustomerVO;
import com.edianniu.pscp.sps.bean.project.*;
import com.edianniu.pscp.sps.domain.CompanyCustomer;
import com.edianniu.pscp.sps.service.dubbo.EngineeringProjectInfoService;
import com.edianniu.pscp.sps.service.dubbo.SpsCompanyCustomerInfoService;

/**
 * ${comments}
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-15 15:38:18
 */
@Controller
@RequestMapping("engineeringproject")
public class EngineeringProjectController extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(EngineeringProjectController.class);

	@Autowired
	private EngineeringProjectInfoService engineeringProjectService;
	
	@Autowired
	private ProjectInfoService projectInfoService;

	@Autowired
	private SpsCompanyCustomerInfoService spsCompanyCustomerInfoService;
	
	@Autowired
	private CompanyCustomerInfoService companyCustomerInfoService;

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private RoomInfoService roomInfoService;

	@RequestMapping("/engineeringproject.html")
	public String list() {
		return "cp/engineeringproject.html";
	}
	
	@RequestMapping("/cus_engineeringproject.html")
	public String listForCustomer(){
		return "cp/cus_engineeringproject.html";
	}
	
	/*****************  服务商角色---工程项目管理模块开始   *********************/
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("engineeringproject:list")
	public R list(Integer page, Integer limit, @ModelAttribute ProjectInfo bean) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		bean.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
		map.put("bean", bean);
		map.put("", "");

		// 项目是否由服务商自己创建 isCreateBySelf    1:服务商自己创建            2:需求转项目
		List<ProjectInfo> engineeringProjectList = engineeringProjectService.queryList(map);
		int total = engineeringProjectService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(engineeringProjectList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("engineeringproject:info")
	public R info(@PathVariable("id") Long id) {
		ProjectInfo engineeringProject = engineeringProjectService.getById(id);
		// 获取合作附件
		List<Attachment> cooperationAttachmentList = engineeringProject.getCooperationAttachmentList();
		addPrefixForFids(cooperationAttachmentList);
		// 获取结算附件
		List<Attachment> actualPriceAttachmentList = engineeringProject.getActualPriceAttachmentList();
		addPrefixForFids(actualPriceAttachmentList);
		// 获取customerName
		Long customerId = engineeringProject.getCustomerId();
		CompanyCustomer companyCustomer = spsCompanyCustomerInfoService.getByCustomerId(customerId);
		if (null != companyCustomer) {
			engineeringProject.setCustomerName(companyCustomer.getCpName());
		}

		return R.ok().put("bean", engineeringProject);
	}

	/**
	 * 新建项目，加载客户列表
	 * 
	 * @return
	 */
	@RequestMapping("/create")
	@RequiresPermissions("engineeringproject:create")
	public R create() {
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		Long companyId = userEntity.getCompanyId();

		CustomerResult customerResult = spsCompanyCustomerInfoService.selectCustomerByCompanyId(companyId);
		List<CustomerVO> customerList = customerResult.getCustomerList();

		return R.ok().put("customerList", customerList);
	}
	
	/**
	 * 新建项目，选择客户后，列出客户所有的配电房
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/room/{customerId}")
	public R roomList(@PathVariable("customerId") Long customerId){
		try {
			SysUserEntity userEntity = ShiroUtils.getUserEntity();
			Long companyId = userEntity.getCompanyId();
			RoomListResult result = roomInfoService.getRoomsByCustomerId(customerId, companyId);
			if (result.isSuccess()) {
				List<RoomVO> roomList = result.getRoomList();
				if (CollectionUtils.isEmpty(roomList)) {
					return R.error("该客户名下尚无配电房，请告知客户新建配电房");
				}
				return R.ok().put("roomList", roomList);
			} else {
				return R.error(result.getResultCode(), result.getResultMessage());
			}
		} catch (Exception e) {
			logger.debug("update:{}", e.getMessage());
			return R.error(500, "系统异常");
		}
	}	

	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("engineeringproject:save")
	public R save(@RequestBody ProjectInfo projectInfo) {
		try {   
			List<Attachment> cooperationAttachmentList = projectInfo.getCooperationAttachmentList();
			// 附件上传
			buildAttachmentStructure(cooperationAttachmentList);
			SaveResult result = engineeringProjectService.save(ShiroUtils.getUserId(), projectInfo);
			if (result.isSuccess()) {
				return R.ok();
			} else {
				return R.error(result.getResultCode(), result.getResultMessage());
			}
		} catch (Exception e) {
			logger.debug("save:{}", e.getMessage());
			return R.error(500, "系统异常");
		}
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("engineeringproject:update")
	public R update(@RequestBody ProjectInfo projectInfo) {
		try {
			R info = info(projectInfo.getId());
			ProjectInfo engineeringProject = (ProjectInfo) info.get("bean");
			if (null == engineeringProject) {
				return R.error("该项目不存在");
			}
			// 获取之前所有附件信息
			List<Attachment> oldAttachmentList = engineeringProject.getCooperationAttachmentList();
			// 获得新传入的附件信息
			List<Attachment> newAttachmentList = projectInfo.getCooperationAttachmentList();
			// 获取要删除的附件ID、要保存的附件信息
			HashMap<String, Object> map = getRemovedImgsAndAttachmentList(oldAttachmentList, newAttachmentList);
			if (MapUtils.isNotEmpty(map)) {
				projectInfo.setRemovedImgs((String) map.get("removedImgs"));
				projectInfo.setCooperationAttachmentList((List<Attachment>) map.get("attachmentList"));
			}
			UpdateResult result = engineeringProjectService.update(ShiroUtils.getUserId(), projectInfo);
			if (result.isSuccess()) {
				return R.ok();
			} else {
				return R.error(result.getResultCode(), result.getResultMessage());
			}
		} catch (Exception e) {
			logger.debug("update:{}", e.getMessage());
			return R.error(500, "系统异常");
		}
	}

	/**
	 * 结算（需求转换的项目）
	 * 结束（自建项目）
	 */
	@ResponseBody
	@RequestMapping("/settle")
	@RequiresPermissions("engineeringproject:settle")
	public R settle(@RequestBody ProjectInfo projectInfo) {
		try {
			R info = info(projectInfo.getId());
			ProjectInfo engineeringProject = (ProjectInfo) info.get("bean");
			if (null == engineeringProject) {
				return R.error("该项目不存在");
			}
			
			// 获取之前所有附件信息
			List<Attachment> oldAttachmentList = engineeringProject.getActualPriceAttachmentList();
			// 获得新传入的附件信息
			List<Attachment> newAttachmentList = projectInfo.getActualPriceAttachmentList();
			// 获取要删除的附件ID、要保存的附件信息
			HashMap<String, Object> map = getRemovedImgsAndAttachmentList(oldAttachmentList, newAttachmentList);
			if (MapUtils.isNotEmpty(map)) {
				projectInfo.setRemovedImgs((String) map.get("removedImgs"));
				projectInfo.setActualPriceAttachmentList((List<Attachment>) map.get("attachmentList"));
			}
			
			SettleResult settleResult = engineeringProjectService.settle(ShiroUtils.getUserId(), projectInfo);
			if (settleResult.isSuccess()) {
				return R.ok();
			} else {
				return R.error(settleResult.getResultCode(), settleResult.getResultMessage());
			}
		} catch (Exception e) {
			logger.debug("settle:{}", e.getMessage());
			return R.error(500, "系统异常");
		}
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("engineeringproject:delete")
	public R delete(@RequestBody Long[] ids) {
		try {
			DeleteResult result = engineeringProjectService.deleteBatch(ShiroUtils.getUserId(), ids);
			if (result.isSuccess()) {
				return R.ok();
			} else {
				return R.error(result.getResultCode(), result.getResultMessage());
			}
		} catch (Exception e) {
			logger.debug("delete:{}", e.getMessage());
			return R.error(500, "系统异常");
		}
	}
	/***********************   服务商角色---工程项目管理模块结束   ****************************/
	
	/***********************   客户角色---我的项目模块开始 **********************************/
	
	/**
	 * 获取客户的所有服务商列表
	 */
	/*@ResponseBody
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
	}*/
	
	/**
	 *  状态下拉列表
	 * @param status 主查詢類型  "progressing":进行中    "finished":已结束
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/subStatus")
	public R projectSubTypes(@RequestParam(required=false) String status){
		List<String> result = new ArrayList<>();
		if (StringUtils.isNotBlank(status)) {
			if (!ProjectQueryStatus.isExist(status)) {
				return R.error();
			}
			ProjectQueryStatus queryStatus = ProjectQueryStatus.getByValue(status);
			List<EngineeringProjectStatus> list = queryStatus.getProjectStatus();
			for (EngineeringProjectStatus projectStatus : list) {
				result.add(projectStatus.getDesc());
			}
		}
		return R.ok().put("result", result);
	}
	
	/**
	 * 我的项目 --列表
	 * @param 查询主状态 status:   "progressing":进行中          "finished":已结束
	 */
	@ResponseBody
	//@CrossOrigin(value="*")
	@RequestMapping(value="/projectList",method=RequestMethod.GET)
	@RequiresPermissions("engineeringproject:projectList")
	public R myProjectList(Integer page, Integer limit, String status, @ModelAttribute ProjectInfosReq bean){
		ListReqData listReqData = new ListReqData(); 
		//主状态
		if (!ProjectQueryStatus.isExist(status)) {
			return R.error("状态不合法");
		}
		// 子状态
		ProjectQueryStatus queryStatus = ProjectQueryStatus.getByValue(status);
		String subStatus = bean.getSubStatus();
		if (StringUtils.isNotBlank(subStatus) && !queryStatus.isProjectStatusExist(subStatus)) {
			return R.error("子状态不合法");
		}
		if (StringUtils.isNotBlank(bean.getWorkTime())) {
			if(!BizUtils.checkYmd(bean.getWorkTime())){
				return R.error("工作时间不合法");
			}
		}
		if (StringUtils.isNotBlank(bean.getCreateTime())) {
			if(!BizUtils.checkYmd(bean.getCreateTime())){
				return R.error("提交时间不合法");
			}
		}
		
		BeanUtils.copyProperties(bean, listReqData);
		listReqData.setStatus(status);
		//listReqData.setUid(1243L);
		listReqData.setUid(getUserId());
		listReqData.setPageSize(limit);
		listReqData.setOffset((page-1) * limit);
		
		ListResult listResult = projectInfoService.projectList(listReqData);
		if (!listResult.isSuccess()) {
			return R.error(listResult.getResultCode(), listResult.getResultMessage());
		}
		List<EngineeringProjectVO> list = listResult.getProjects();
		int total = listResult.getTotalCount();
		PageUtils pageUtils = new PageUtils(list, total, limit, page);
		
		return R.ok().put("pageUtils", pageUtils);
	}
	
	/**
	 * 我的项目 --详情
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("engineeringproject:projectDetail")
	@RequestMapping(value="/projectDetail/{projectNo}")
	public R myProjectDetail(@PathVariable("projectNo") String projectNo){
		
		DetailsReqData detailsReqData = new DetailsReqData();
		//detailsReqData.setUid(1243L);
		detailsReqData.setUid(getUserId());
		detailsReqData.setProjectNo(projectNo);
		DetailsResult detailsResult = projectInfoService.projectDetails(detailsReqData);
		if (!detailsResult.isSuccess()) {
			return R.error(detailsResult.getResultCode(), detailsResult.getResultMessage());
		}
		NeedsVO needsVO = detailsResult.getNeedsInfo();
		EngineeringProjectVO projectVO = detailsResult.getProjectInfo();
		List<com.edianniu.pscp.cs.bean.needs.Attachment> cooperationInfo = projectVO.getCooperationInfo();
		addPrefix(cooperationInfo);
		Map<String, Object> map = new HashMap<>();
		map.put("needsVO", needsVO);
		map.put("projectVO", projectVO);
		
		return R.ok().put("detail", map);
	}
	
	/***********************   客户角色---我的项目模块结束 **********************************/

	/**
	 * 将附件（base64格式）存到服务器
	 */
	public List<Attachment> buildAttachmentStructure(List<Attachment> attachmentList) {
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (Attachment attachment : attachmentList) {
				if (StringUtils.isNotBlank(attachment.getFid())) {
					byte[] content = ImageUtils.getBase64ImageBytes(attachment.getFid());
					FileUploadResult frontResult = fileUploadService.upload("upload_" + System.nanoTime() + ".jpg",
							content, true, null);
					//生成小图，但是保存到数据库的为原图路径
					attachment.setFid(frontResult.getFileId());
				} else {
					attachment.setFid(null);
				}
			}
		}
		return attachmentList;
	}
	
	/**
	 * 附件前缀
	 */
	private String prefix;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String prefix) {
        this.prefix = prefix;
    }
	
	/**
	 * 为附件fid加"http://192.168.1.251:8091/"前缀
	 * 并返回原图的缩略图路径
	 */
	public List<Attachment> addPrefixForFids(List<Attachment> attachmentList){
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (Attachment attachment : attachmentList) {
				String fid = attachment.getFid();
				String conversionSmallFilePath = fileUploadService.conversionSmallFilePath(fid);
				attachment.setFid(prefix + conversionSmallFilePath);
			}
		}
		return attachmentList;
	}
	
	/**
	 * 为附件fid加"http://192.168.1.251:8091/"前缀
	 * 并返回原图的缩略图路径
	 */
	public List<com.edianniu.pscp.cs.bean.needs.Attachment> addPrefix(List<com.edianniu.pscp.cs.bean.needs.Attachment> attachmentList){
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (com.edianniu.pscp.cs.bean.needs.Attachment attachment : attachmentList) {
				String fid = attachment.getFid();
				String conversionSmallFilePath = fileUploadService.conversionSmallFilePath(fid);
				attachment.setFid(prefix + conversionSmallFilePath);
			}
		}
		return attachmentList;
	}
	
	/**
	 * 获取需要删除的附件ID字符串(逗号分隔)和新要存储的附件集合attachmentList
	 */
	public HashMap<String, Object> getRemovedImgsAndAttachmentList(List<Attachment> oldAttachmentList, List<Attachment> newAttachmentList){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 获取之前所有附件的id，并放到oldIdList中
		List<Long> oldIdList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(oldAttachmentList)) {
			for (Attachment attachment : oldAttachmentList) {
				oldIdList.add(attachment.getId());
			}
		}
		// 将新传入的附件fid(混有base64格式的fid)，放到两个list中，一个是之前已存储到服务器的附件id集合，一个是base64格式的(尚未存储到服务器)附件fid集合
		List<Long> alreadySavedIdList = new ArrayList<Long>();
		List<String> notSavedFidList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(newAttachmentList)) {
			for (Attachment attachment : newAttachmentList) {
				if (attachment.getFid().startsWith(prefix)) {
					alreadySavedIdList.add(attachment.getId());
				} else {
					notSavedFidList.add(attachment.getFid());
				}
			}
		}
		// 获取oldIdList与alreadySavedIdList的差集，得到被删除的附件id集合
		List<Long> removedIdList = new ArrayList<Long>();
		removedIdList.addAll(oldIdList);
		removedIdList.removeAll(alreadySavedIdList);
		// 将要删除的附件id集合整理成字符串，以逗号分隔
		String removedImgs = new String();
		StringBuilder sb = new StringBuilder("");
		if (CollectionUtils.isNotEmpty(removedIdList)) {
			for (Long removedId : removedIdList) {
				sb.append(removedId.toString().trim()).append(",");
			}
			removedImgs = sb.toString().substring(0, sb.length()-1);
		}
		// 将尚未存储到服务器的附件(即最新添加的附件)存储到服务器，并返回附件信息
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		if (CollectionUtils.isNotEmpty(notSavedFidList)) {
			for (String notSavedFid : notSavedFidList) {
				Attachment attachment = new Attachment();
				attachment.setFid(notSavedFid);
				attachmentList.add(attachment);
			}
			attachmentList = buildAttachmentStructure(attachmentList);
		}
		
		map.put("removedImgs", removedImgs);
		map.put("attachmentList", attachmentList);
		return map;
	}
	
	
	
	
}
