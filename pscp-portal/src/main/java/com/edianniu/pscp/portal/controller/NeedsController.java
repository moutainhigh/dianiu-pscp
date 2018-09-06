package com.edianniu.pscp.portal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.cs.bean.ConfigureParamResult;
import com.edianniu.pscp.cs.bean.engineeringproject.QuotedInfo;
import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.needs.ConfirmCooperationReqData;
import com.edianniu.pscp.cs.bean.needs.ConfirmCooperationResult;
import com.edianniu.pscp.cs.bean.needs.DetailsReqData;
import com.edianniu.pscp.cs.bean.needs.DetailsResult;
import com.edianniu.pscp.cs.bean.needs.NeedsInfoResult;
import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.needs.QuoteResult;
import com.edianniu.pscp.cs.bean.needs.RespondDetailsReqData;
import com.edianniu.pscp.cs.bean.needs.RespondDetailsResult;
import com.edianniu.pscp.cs.bean.needs.ResponsedCompany;
import com.edianniu.pscp.cs.bean.needs.UploadFileReqData;
import com.edianniu.pscp.cs.bean.needs.UploadFileResult;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needsorder.*;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderListVO;
import com.edianniu.pscp.cs.service.dubbo.ConfigureParamService;
import com.edianniu.pscp.cs.service.dubbo.CustomerNeedsOrderInfoService;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.portal.bean.vo.NeedsInfoVO;
import com.edianniu.pscp.portal.utils.BizUtils;
import com.edianniu.pscp.portal.utils.ImageUtils;
import com.edianniu.pscp.portal.utils.MoneyUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需求管理
 * @author zhoujianjian 2017年9月27日上午9:21:02
 */
@Controller
@RequestMapping("/needs")
public class NeedsController extends AbstractController{

    @Autowired
    @Qualifier("needsInfoService")
    private NeedsInfoService needsInfoService;
    @Autowired
    @Qualifier("configureParamService")
    private ConfigureParamService configureParamService;
    @Autowired
    @Qualifier("customerNeedsOrderInfoService")
    private CustomerNeedsOrderInfoService customerNeedsOrderInfoService;
    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping("/requirement.html")
    public String list() {
        return "cp/requirement.html";
    }
    @RequestMapping("/cus_requirement.html")
    public String cusNeedsList(){
    	return "cp/cus_requirement.html";
    }
    
    
    /***********************服务商需求管理开始***********************/
    /**
     * 需求列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("needs:list")
    public R list(Integer page, Integer limit, String name, String contactPerson, String mobile) {

        ListReqData reqData = new ListReqData();
        reqData.setUid(ShiroUtils.getUserId());
        reqData.setOffset((page - 1) * limit);
        reqData.setPageSize(limit);
        reqData.setName(name);
        reqData.setContactPerson(contactPerson);
        reqData.setContactNumber(mobile);
        // 此处查询的是服务商响应订单表
        ListResult listResult = customerNeedsOrderInfoService.list(reqData);
        List<NeedsOrderListVO> needsOrderList = listResult.getNeedsOrderList();
        if (CollectionUtils.isNotEmpty(needsOrderList)) {
            for (NeedsOrderListVO needsOrderListVO : needsOrderList) {
                Integer status = needsOrderListVO.getStatus();
                String desc = NeedsOrderStatus.getDesc(status);
                needsOrderListVO.setShowStatus(desc);
            }
        }
        int total = listResult.getTotalCount();
        PageUtils pageUtils = new PageUtils(needsOrderList, total, limit, page);

        return R.ok().put("page", pageUtils);
    }

    /**
     * 需求详情
     * @param orderId           需求编号
     * @param responsedOrderId  需求订单编号，如果没有则为-1
     */
    @ResponseBody
    @RequestMapping("/info/{orderId}")
    @RequiresPermissions("needs:info")
    public R info(@PathVariable("orderId") String orderId, @RequestBody String responsedOrderId) {
        Long companyId = ShiroUtils.getUserEntity().getCompanyId();
        // 此处查询的是客户需求表
        NeedsInfoResult needsInfoResult = needsInfoService.getNeedsInfo(orderId, companyId, responsedOrderId);

        NeedsVO needsVO = needsInfoResult.getNeedsVO();
        if (null == needsVO) {
        	 return R.error("该需求不存在！");
		}
        
        // 需求附件
    	List<Attachment> attachmentList = needsVO.getAttachmentList();
        attachmentList = addPrefixForFids(attachmentList);
		
        // 合作附件
    	List<Attachment> cooperationAttachments = needsVO.getCooperationInfo();
        cooperationAttachments = addPrefixForFids(cooperationAttachments);
	
        // 报价信息
        QuotedInfo quotedInfo = needsVO.getQuotedInfo();
        if (null != quotedInfo) {
            List<Attachment> quotedAttachments = quotedInfo.getAttachmentList();
            quotedAttachments = addPrefixForFids(quotedAttachments);
        }

        NeedsOrderInfo needsOrderInfo = needsInfoResult.getNeedsOrderInfo();
        needsOrderInfo.setQuotedAttachmentList(null);

        NeedsInfoVO needsInfoVO = new NeedsInfoVO();
        needsInfoVO.setNeedsVO(needsVO);
        needsInfoVO.setNeedsOrderInfo(needsOrderInfo);

        return R.ok().put("bean", needsInfoVO);
    }

    /**
     * 需求响应
     */
    @ResponseBody
    @RequestMapping("/order/respond/{orderId}")
    @RequiresPermissions("needs:order:respond")
    public R respond(@PathVariable("orderId") String orderId) {
        Long uid = ShiroUtils.getUserEntity().getUserId();
        SaveReqData reqData = new SaveReqData();
        reqData.setOrderId(orderId);
        reqData.setUid(uid);
        SaveResult result = customerNeedsOrderInfoService.save(reqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }

        Double deposit = 0.0;
        ConfigureParamResult paramResult = configureParamService.buildConfigureParam();
        if (paramResult.isSuccess()) {
            JSONObject jsonObject = JSON.parseObject(paramResult.getConfigureParam());
            if (jsonObject.containsKey("deposit")) {
                deposit = jsonObject.getDouble("deposit");
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("orderId", result.getOrderId());
        resultMap.put("deposit", MoneyUtils.format(deposit));
        return R.ok().put("result", JSON.toJSON(resultMap));
    }

    /**
     * 需求报价
     */
    @ResponseBody
    @RequestMapping("/order/quote/{orderId}")
    @RequiresPermissions("needs:order:quote")
    public R quote(@PathVariable("orderId") String orderId, @RequestBody QuotedInfo quotedInfo) {

        String quotedPrice = quotedInfo.getQuotedPrice();
        List<Attachment> attachmentList = quotedInfo.getAttachmentList();
        if (CollectionUtils.isEmpty(attachmentList)) {
			return R.error("附件不能为空");
		}
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Attachment attachment : attachmentList) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(attachment.getFid())) {

                byte[] content = ImageUtils.getBase64ImageBytes(attachment.getFid());

                FileUploadResult frontResult = fileUploadService.upload("upload_" + System.nanoTime() + ".jpg",
                        content, true, null);
                map.put("fid", frontResult.getFileId());
                map.put("orderNum", attachment.getOrderNum());
                mapList.add(map);
            }
        }

        OfferReqData offerReqData = new OfferReqData();
        offerReqData.setUid(ShiroUtils.getUserId());
        offerReqData.setQuotedPrice(quotedPrice);
        offerReqData.setAttachmentList(mapList);
        offerReqData.setOrderId(orderId);
        OfferResult offerResult = customerNeedsOrderInfoService.offer(offerReqData);
        if (offerResult.isSuccess()) {
            return R.ok();
        } else {
            return R.error(offerResult.getResultCode(), offerResult.getResultMessage());
        }
    }

    /**
     * 需求派勘察订单初始化数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/survey/initdata/{orderId}")
    @RequiresPermissions("needs:survey:initdata")
    public R initData(@PathVariable("orderId") String orderId) {
        InitDataReqData reqData = new InitDataReqData();
        reqData.setOrderId(orderId);
        reqData.setUid(ShiroUtils.getUserId());
        InitDataResult result = needsInfoService.surveyInitData(reqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        Map<String, Object> mapResult = new HashMap<>();
        mapResult.put("customer", result.getCustomer());
        mapResult.put("project", result.getProject());
        return R.ok().put("result", mapResult);
    }

    /**
     * 需求订单取消
     *
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping("/order/{orderId}/cancel")
    @RequiresPermissions("needs:order:cancel")
    public R cancel(@PathVariable("orderId") String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return R.error("需求订单编号不能为空");
        }

        CancelReqData reqData = new CancelReqData();
        reqData.setOrderId(orderId);
        reqData.setUid(ShiroUtils.getUserId());
        CancelResult result = customerNeedsOrderInfoService.cancel(reqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok();
    }
    
    /***********************服务商需求管理结束***********************/
    /***********************客户我的需求开始***********************/
    
    /**
     * 客户需求列表
     */
    @ResponseBody
    @RequestMapping("/cus/list")
    @RequiresPermissions("needs:cus:list")
    public R getNeedList(Integer page, Integer limit, String status, 
    		@ModelAttribute com.edianniu.pscp.cs.bean.needs.ListReqData listReqData){
    	
    	int offset = (page - 1) * limit;
    	listReqData.setUid(getUserId());
    	//listReqData.setUid(1243L);
    	listReqData.setOffset(offset);
    	if (!("verifying".equals(status) || "responding".equals(status) || "quoting".equals(status)
                || "finished".equals(status))) {
    		return R.error("状态不合法");
        }
    	listReqData.setStatus(status);
    	if (StringUtils.isNotBlank(listReqData.getPublishTime())) {
			if (!BizUtils.checkYmd(listReqData.getPublishTime())) {
				return R.error("日期格式不合法");
			}
		}
		com.edianniu.pscp.cs.bean.needs.ListResult result = needsInfoService.queryList(listReqData);
    	if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
    	PageUtils pageUtils = new PageUtils(result.getNeedsList(), result.getTotalCount(), limit, page);
    	
    	return R.ok().put("page", pageUtils);
    }
    
    /**
     * 发布需求
     */
    @ResponseBody
    @RequestMapping("/cus/saveNeeds")
    @RequiresPermissions("needs:cus:saveNeeds")
    public R saveNeeds(@RequestBody com.edianniu.pscp.cs.bean.needs.SaveReqData saveReqData){
    	saveReqData.setUid(getUserId());
    	//将base存储到fastDFS
    	List<Attachment> attachmentList = saveReqData.getAttachmentList();
    	buildAttachmentStructure(attachmentList);
    	com.edianniu.pscp.cs.bean.needs.SaveResult result = needsInfoService.saveNeeds(saveReqData);
    	if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
    	return R.ok();
    }
    
    /**
     * 重新发布需求
     */
    @ResponseBody
    @RequestMapping("/cus/republishNeeds")
    @RequiresPermissions("needs:cus:republishNeeds")
    public R republishNeeds(@RequestBody com.edianniu.pscp.cs.bean.needs.RepublishReqData republishReqData){
    	republishReqData.setUid(getUserId());
    	List<Attachment> attachmentList = republishReqData.getAttachmentList();
    	buildAttachmentStructure(attachmentList);
    	com.edianniu.pscp.cs.bean.needs.RepublishResult result = needsInfoService.republishNeeds(republishReqData);
    	if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
    	return R.ok();
    }
    
    /**
     * 客户需求详情
     * @param id 需求ID
     */
    @ResponseBody
    @RequestMapping("/cus/needsDetails/{id}")
    @RequiresPermissions("needs:cus:needsDetails")
    public R needsDetails(@PathVariable("id") Long id){
    	DetailsReqData detailsReqData = new DetailsReqData();
    	detailsReqData.setNeedsId(id);
    	detailsReqData.setUid(getUserId());
    	//detailsReqData.setUid(1243L);
		DetailsResult result = needsInfoService.query(detailsReqData);
    	if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
    	// 需求信息
		NeedsVO needsVO = result.getNeeds();
		if (null == needsVO) {
			return R.error("需求不存在");
		}
		// 需求附件
    	List<Attachment> attachmentList = needsVO.getAttachmentList();
        attachmentList = addPrefixForFids(attachmentList);
		
        // 合作附件
    	List<Attachment> cooperationAttachments = needsVO.getCooperationInfo();
        cooperationAttachments = addPrefixForFids(cooperationAttachments);
	
		// 服务商响应信息
    	List<ResponsedCompany> responsedCompanys = result.getResponsedCompanys();
    	if (CollectionUtils.isNotEmpty(responsedCompanys)) {
    		for (ResponsedCompany responsedCompany : responsedCompanys) {
        		// 报价信息
        		List<Attachment> quotedAttachments = responsedCompany.getQuotedAttachment();
        		quotedAttachments = addPrefixForFids(quotedAttachments);
    		}
		}
    	
    	HashMap<String, Object> map = new HashMap<>();
    	map.put("needs", needsVO);
    	map.put("responsedCompanys", responsedCompanys);
    	return R.ok().put("details", map);
    }
    
    /**
     * 客户获取响应服务商资质详情
     * @param orderId  响应订单号
     */
    @ResponseBody
    @RequestMapping("/cus/respondCompanyDetails/{orderId}")
    @RequiresPermissions("needs:cus:respondCompanyDetails")
    public R respondCompanyDetails(@PathVariable("orderId") String orderId){
    	RespondDetailsReqData respondDetailsReqData = new RespondDetailsReqData();
    	respondDetailsReqData.setUid(getUserId());
    	respondDetailsReqData.setOrderId(orderId);
		RespondDetailsResult result = needsInfoService.respondDetails(respondDetailsReqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		
		CompanyInfo companyInfo = result.getCompanyInfo();
		if (null != companyInfo) {
			companyInfo.setBusinessLicenceImg(prefix + companyInfo.getBusinessLicenceImg());
			companyInfo.setOrganizationCodeImg(prefix + companyInfo.getOrganizationCodeImg());
			List<CertificateImgInfo> certificateImages = companyInfo.getCertificateImages();
			if (CollectionUtils.isNotEmpty(certificateImages)) {
				for (CertificateImgInfo certificateImgInfo : certificateImages) {
					String fileId = certificateImgInfo.getFileId();
					certificateImgInfo.setFileId(prefix + fileId);
				}
			}
		}
    	return R.ok().put("info", companyInfo);
    }
    
    /**
     * 客户需求询价
     * @param orderId 需求订单ID
     * @param responsedOrderIds 被选择询价的服务商响应的订单ID，多个用逗号分隔
     */
    @ResponseBody
    @RequestMapping("/cus/needsQuote/{orderId}")
    //@RequiresPermissions("needs:cus:needsQuote")
    
    public R needsQuote(@PathVariable("orderId") String orderId, 
    		@RequestParam(value="responsedOrderIds") String responsedOrderIds){
    	QuoteResult result = needsInfoService.quoteNeeds(orderId, getUserId(), responsedOrderIds);
    	if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
    	return R.ok();
    }
    
    /**
     * 客户取消需求
     * @param orderId需求订单ID
     */
    @ResponseBody
    @RequestMapping("/cus/cancelNeeds/{orderId}")
   // @RequiresPermissions("needs:cus:cancelNeeds")
    public R cancelNeeds(@PathVariable("orderId")String orderId){
    	com.edianniu.pscp.cs.bean.needs.CancelResult result = needsInfoService.cancelNeeds(orderId, getUserId());
    	if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
    	return R.ok();
    }
    
    /**
     * 客户需求确认合作
     * @param orderId  需求订单ID
     * @param responsedOrderId  被选择合作的服务商响应的订单ID
     */
    @ResponseBody
    @RequestMapping("/cus/confirmCooperation/{orderId}")
    @RequiresPermissions("needs:cus:confirmCooperation")
    public R confirmCooperation(@PathVariable("orderId")String orderId, String responsedOrderId){
    	ConfirmCooperationReqData reqData = new ConfirmCooperationReqData();
    	reqData.setUid(getUserId());
    	reqData.setOrderId(orderId);
    	reqData.setResponsedOrderId(responsedOrderId);
		ConfirmCooperationResult result = needsInfoService.confirmCooperation(reqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
    	return R.ok();
    }
    
    /**
     * 客户上传、编辑合作附件
     * @param orderId 需求订单ID
     * @param removedImgs 要删除的附件id,多个用逗号分隔,可为空
     * @param attachmentList 合作附件
     */
    @ResponseBody
    @RequestMapping("/cus/uploadFiles/{orderId}")
    @RequiresPermissions("needs:cus:uploadFiles")
    public R uploadFiles(@PathVariable("orderId")String orderId, String removedImgs, List<Attachment> attachmentList){
    	UploadFileReqData reqData = new UploadFileReqData();
    	reqData.setUid(getUserId());
    	reqData.setOrderId(orderId);
    	reqData.setRemovedImgs(removedImgs);
    	//将base64存储到fastDFS
    	buildAttachmentStructure(attachmentList);
    	reqData.setAttachmentList(attachmentList);
		UploadFileResult result = needsInfoService.upload(reqData);
		
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
    	return R.ok();
    }
    
    /***********************客户我的需求结束***********************/
    
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
    private List<Attachment> addPrefixForFids(List<Attachment> attachmentList) {
        if (CollectionUtils.isNotEmpty(attachmentList)) {
            for (Attachment attachment : attachmentList) {
            	String fid = attachment.getFid();
            	String conversionSmallFilePath = fileUploadService.conversionSmallFilePath(fid);
                attachment.setFid(prefix + conversionSmallFilePath);
            }
        }
        return attachmentList;
    }
}
