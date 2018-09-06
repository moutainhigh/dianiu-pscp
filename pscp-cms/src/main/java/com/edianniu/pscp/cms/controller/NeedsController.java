package com.edianniu.pscp.cms.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.cms.bean.NeedsInfo;
import com.edianniu.pscp.cms.entity.SysUserEntity;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.needs.AuditReqData;
import com.edianniu.pscp.cs.bean.needs.AuditResult;
import com.edianniu.pscp.cs.bean.needs.DetailsReqData;
import com.edianniu.pscp.cs.bean.needs.DetailsResult;
import com.edianniu.pscp.cs.bean.needs.NeedsViewListReqData;
import com.edianniu.pscp.cs.bean.needs.NeedsViewListResult;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsViewVO;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;

/**
 * 客户需求管理
 * 
 * @author zhoujianjian 2017年9月21日下午2:46:08
 */
@RestController
@RequestMapping("/needs")
public class NeedsController extends AbstractController {

	@Autowired
	NeedsInfoService needsInfoService;

	/**
	 * 需求列表
	 * 
	 * @param status
	 * @param memberName
	 * @param loginId
	 * @param needsName
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("needs:list")
	public R list(String status, String memberName, String loginId, String needsName, Integer page, Integer limit) {

		NeedsViewListReqData needsViewListReqData = new NeedsViewListReqData();

		SysUserEntity user = getUser();
		Long userId = user.getUserId();
		needsViewListReqData.setUid(userId);

		needsViewListReqData.setStatus(status);
		needsViewListReqData.setMemberName(memberName);
		needsViewListReqData.setLoginId(loginId);
		needsViewListReqData.setNeedsName(needsName);
		needsViewListReqData.setOffset((page - 1) * limit);
		needsViewListReqData.setPageSize(limit);
		NeedsViewListResult viewListResult = needsInfoService.getNeedsViewList(needsViewListReqData);

		List<NeedsViewVO> needsViewVOList = viewListResult.getNeedsViewVOList();

		int totalCount = viewListResult.getTotalCount();
		PageUtils pageUtils = new PageUtils(needsViewVOList, totalCount, limit, page);

		return R.ok().put("page", pageUtils);
	}

	/**
	 * 需求详情
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/details/{orderId}")
	@RequiresPermissions("needs:details")
	public R info(@PathVariable("orderId") String orderId) {
		DetailsReqData detailsReqData = new DetailsReqData();

		SysUserEntity user = getUser();
		Long userId = user.getUserId();
		detailsReqData.setUid(userId);
		detailsReqData.setOrderId(orderId);
		DetailsResult detailsResult = needsInfoService.query(detailsReqData);

		NeedsVO needsVO = detailsResult.getNeeds();
		// 附件加前缀
		List<Attachment> attachmentList = needsVO.getAttachmentList();
		attachmentList = addPrefixForFids(attachmentList);

		List<com.edianniu.pscp.cms.bean.Attachment> attachments = new ArrayList<com.edianniu.pscp.cms.bean.Attachment>();
		for (Attachment attachment : attachmentList) {
			com.edianniu.pscp.cms.bean.Attachment att = new com.edianniu.pscp.cms.bean.Attachment();
			BeanUtils.copyProperties(attachment, att);
			attachments.add(att);
		}

		NeedsInfo needsInfo = new NeedsInfo();
		BeanUtils.copyProperties(needsVO, needsInfo);
		needsInfo.setAttachmentList(attachments);

		return R.ok().put("needsInfo", needsInfo);
	}

	/**
	 * 需求审核
	 * 
	 * @param orderId
	 * @param status
	 * @param failReason
	 * @param maskString
	 * @param removedImgs
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/audit/{orderId}")
	@RequiresPermissions("needs:audit")
	public R audit(@PathVariable("orderId") String orderId, @RequestBody JSONObject reqData) {
		// 审核结果 -1.不通过 1.通过
		Integer status = reqData.getInteger("status");
		// 失败原因
		String failReason = reqData.getString("failReason");
		// 要屏蔽的字
		String maskString = reqData.getString("maskString");
		// 要屏蔽的图片FID，多个用逗号分隔
		String removedImgs = reqData.getString("removedImgs");
		Long userId = getUser().getUserId();

		AuditReqData auditReqData = new AuditReqData();
		auditReqData.setUid(userId);
		auditReqData.setOrderId(orderId);
		auditReqData.setStatus(status);
		auditReqData.setFailReason(failReason);
		auditReqData.setMaskString(maskString);
		auditReqData.setRemovedImgs(removedImgs);

		AuditResult auditResult = needsInfoService.auditNeeds(auditReqData);
		if (!auditResult.isSuccess()) {
			return R.error(auditResult.getResultMessage());
		}
		return R.ok();
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
	 * 为附件fid加上"http://192.168.1.251:8091/"前缀
	 */
	public List<Attachment> addPrefixForFids(List<Attachment> attachmentList) {
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (Attachment attachment : attachmentList) {
				attachment.setFid(prefix + attachment.getFid().toString());
			}
		}
		return attachmentList;
	}

}
