package com.edianniu.pscp.portal.controller;

import com.edianniu.pscp.cs.bean.dutylog.SaveResult;
import com.edianniu.pscp.cs.service.dubbo.DutyLogInfoService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作台值班日志
 */
@Controller
@RequestMapping("dutylog")
public class WorkDutyLogController extends AbstractController{

    @Autowired
    DutyLogInfoService dutyLogInfoService;

    /**
     * 值班日志列表
     *
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit,
                  @RequestParam(required = false, defaultValue = "-1") Long roomId) {

        Integer offset = (page - 1) * limit;
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.dutylog.ListReqData listReqData = new com.edianniu.pscp.cs.bean.dutylog.ListReqData();
        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setRoomId(roomId);

        com.edianniu.pscp.cs.bean.dutylog.ListResult result = dutyLogInfoService.dutyLogList(listReqData);
        PageUtils pageUtils = new PageUtils(result.getDutyLogs(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }


    /**
     * 新增或者编辑值班日志
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@RequestParam(required = true) Long roomId,
                  @RequestParam(required = false) Long id,
                  @RequestParam(required = true) String title,
                  @RequestParam(required = true) String content,
                  @RequestParam(required = false) String removedImgs,
                  @RequestBody(required = false) List<com.edianniu.pscp.cs.bean.needs.Attachment> attachmentList
    ) {

        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.dutylog.SaveReqData saveReqData = new com.edianniu.pscp.cs.bean.dutylog.SaveReqData();
        saveReqData.setUid(uid);
        saveReqData.setRoomId(roomId);
        saveReqData.setId(id);
        saveReqData.setTitle(title);
        saveReqData.setContent(content);
        saveReqData.setRemovedImgs(removedImgs);
        saveReqData.setAttachmentList(attachmentList);

        SaveResult saveResult = dutyLogInfoService.saveDutyLog(saveReqData);
        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getResultCode(), saveResult.getResultMessage());
        }
        return R.ok();
    }


    /**
     * 值班日志详情
     */
    @ResponseBody
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.dutylog.DetailsReqData detailsReqData = new com.edianniu.pscp.cs.bean.dutylog.DetailsReqData();
        detailsReqData.setId(id);
        detailsReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.dutylog.DetailsResult detailsResult = dutyLogInfoService.getDutyLogDetails(detailsReqData);
        if (!detailsResult.isSuccess()) {
            return R.error(detailsResult.getResultCode(), detailsResult.getResultMessage());
        }
        return R.ok().put("dutyLog", detailsResult.getDutyLog());
    }


    /**
     * 删除值班日志
     */
    @ResponseBody
    @RequestMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.dutylog.DeleteReqData deleteReqData = new com.edianniu.pscp.cs.bean.dutylog.DeleteReqData();
        deleteReqData.setId(id);
        deleteReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.dutylog.DeleteResult deleteResult = dutyLogInfoService.deleteDutyLog(deleteReqData);
        if (!deleteResult.isSuccess()) {
            return R.error(deleteResult.getResultCode(), deleteResult.getResultMessage());
        }
        return R.ok();
    }


}
