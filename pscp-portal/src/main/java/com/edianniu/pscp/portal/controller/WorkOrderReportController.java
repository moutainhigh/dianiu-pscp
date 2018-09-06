package com.edianniu.pscp.portal.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.bean.attachment.common.CommonAttachment;
import com.edianniu.pscp.mis.bean.worksheetreport.ReportType;
import com.edianniu.pscp.mis.bean.worksheetreport.SaveReqData;
import com.edianniu.pscp.mis.bean.worksheetreport.SaveResult;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.mis.service.dubbo.WorkOrderReportInfoService;
import com.edianniu.pscp.portal.utils.ImageUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;

/**
 * 工作台修试记录或者巡视记录
 */
@Controller
@RequestMapping("worksheetreport")
public class WorkOrderReportController extends AbstractController{

	@Autowired
	private FileUploadService fileUploadService;
    @Autowired
    WorkOrderReportInfoService workOrderReportInfoService;

    /**
     * 修试记录或者巡视记录列表
     * @param page
     * @param limit
     * @param type  REPAIR_TEST(1, "修试报告"),   PATROL(2, "巡视报告"),
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit,
                  @RequestParam(required = false) String orderId,
                  @RequestParam(required = false, defaultValue = "0") Integer status,
                  @RequestParam(required = true) Integer type,
                  @RequestParam(required = false) Long roomId
    ) {

        Integer offset = (page - 1) * limit;
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.mis.bean.worksheetreport.ListReqData listReqData = new com.edianniu.pscp.mis.bean.worksheetreport.ListReqData();
        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setRoomId(roomId);
        listReqData.setOrderId(orderId);
        listReqData.setType(type);

        com.edianniu.pscp.mis.bean.worksheetreport.ListResult result = workOrderReportInfoService.pageListReport(listReqData);

        PageUtils pageUtils = null;
        if (type == ReportType.REPAIR_TEST.getValue()) {
            pageUtils = new PageUtils(result.getRepairTestRecordList(), (int) result.getTotalCount(), limit, page);
        } else if (type == ReportType.PATROL.getValue()) {
            pageUtils = new PageUtils(result.getPatrolRecordList(), (int) result.getTotalCount(), limit, page);
        } else if (type == ReportType.SURVEY.getValue()) {
            pageUtils = new PageUtils(result.getSurveyRecordList(), (int) result.getTotalCount(), limit, page);
        }

        return R.ok().put("page", pageUtils);
    }


    /**
     * 新增或修试记录或者巡视记录
     * @param type  (1, "修试报告"),   PATROL(2, "巡视报告"),
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@ModelAttribute SaveReqData saveReqData) {

        Long uid = ShiroUtils.getUserId();
        saveReqData.setUid(uid);
        List<CommonAttachment> attachmentList = saveReqData.getAttachmentList();
        buildAttachmentStructure(attachmentList);
        SaveResult saveResult = workOrderReportInfoService.saveWorkOrderReport(saveReqData);
        
        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getResultCode(), saveResult.getResultMessage());
        }
        return R.ok();
    }


    /**
     * 修试记录或者巡视记录详情
     * @param id
     * @param type REPAIR_TEST(1, "修试报告"),   PATROL(2, "巡视报告"),
     * @return
     */
    @ResponseBody
    @RequestMapping("/detail")
    public R detail(@RequestParam(required = true) Long id,
                    @RequestParam(required = true) Integer type) {
        com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData detailsReqData = new com.edianniu.pscp.mis.bean.worksheetreport.DetailsReqData();
        detailsReqData.setId(id);
        detailsReqData.setType(type);

        com.edianniu.pscp.mis.bean.worksheetreport.DetailsResult detailsResult = workOrderReportInfoService.getWorkOrderReportById(detailsReqData);
        if (!detailsResult.isSuccess()) {
            return R.error(detailsResult.getResultCode(), detailsResult.getResultMessage());
        }
        R r = R.ok();
        if (type == ReportType.REPAIR_TEST.getValue()) {
            r.put("repairTestDetails", detailsResult.getRepairTestDetails());
        } else if (type == ReportType.PATROL.getValue()) {
            r.put("patrolRecordDetails", detailsResult.getPatrolRecordDetails());
        }
        return r;

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
