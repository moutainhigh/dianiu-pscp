package com.edianniu.pscp.portal.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;
import com.edianniu.pscp.mis.bean.defectrecord.SaveReqData;
import com.edianniu.pscp.mis.bean.defectrecord.SaveResult;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderDefectRecordInfoService;
import com.edianniu.pscp.portal.utils.ImageUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;

/**
 * 工作台缺陷记录
 */
@Controller
@RequestMapping("defectRecord")
public class WorkDefectRecordController extends AbstractController{

	@Autowired
	private FileUploadService fileUploadService;
    @Autowired
    WorkOrderDefectRecordInfoService workOrderDefectRecordInfoService;

    /**
     * 缺陷记录列表
     *
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit,
                  @RequestParam(required = false) String orderId,
                  @RequestParam(required = false, defaultValue = "0") Integer status,
                  @RequestParam(required = false) Long projectId,
                  @RequestParam(required = false) Long roomId
    ) {

        Integer offset = (page - 1) * limit;
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.mis.bean.defectrecord.ListReqData listReqData = new com.edianniu.pscp.mis.bean.defectrecord.ListReqData();
        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setRoomId(roomId);
        listReqData.setOrderId(orderId);
        listReqData.setStatus(status);
        listReqData.setProjectId(projectId);

        com.edianniu.pscp.mis.bean.defectrecord.ListResult result = workOrderDefectRecordInfoService.pageListDefectRecord(listReqData);
        PageUtils pageUtils = new PageUtils(result.getDefectRecordList(), (int) result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }


    /**
     * 新增或者编辑缺陷记录
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@ModelAttribute SaveReqData saveReqData) {

        Long uid = ShiroUtils.getUserId();
        saveReqData.setUid(uid);
        List<CommonAttachment> attachmentList = saveReqData.getAttachmentList();
        buildAttachmentStructure(attachmentList);
        SaveResult saveResult = workOrderDefectRecordInfoService.saveDefectRecord(saveReqData);
        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getResultCode(), saveResult.getResultMessage());
        }
        return R.ok();
    }


    /**
     * 缺陷记录详情
     */
    @ResponseBody
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.mis.bean.defectrecord.DetailsReqData detailsReqData = new com.edianniu.pscp.mis.bean.defectrecord.DetailsReqData();
        detailsReqData.setId(id);
        detailsReqData.setUid(uid);

        com.edianniu.pscp.mis.bean.defectrecord.DetailsResult detailsResult = workOrderDefectRecordInfoService.getDefectRecord(detailsReqData);
        if (!detailsResult.isSuccess()) {
            return R.error(detailsResult.getResultCode(), detailsResult.getResultMessage());
        }
        return R.ok().put("defectRecordDetails", detailsResult.getDefectRecordDetails());
    }

    /**
	 * 将附件（base64格式）存到服务器
	 */
	public List<CommonAttachment> buildAttachmentStructure(List<CommonAttachment> attachmentList) {
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			for (CommonAttachment attachment : attachmentList) {
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
}
