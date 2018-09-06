package com.edianniu.pscp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.cs.service.dubbo.InspectingLogInfoService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;

/**
 * 工作台消防设备或者安全用具检视试验
 */
@Controller
@RequestMapping("inspect")
public class WorkInspectingLogController extends AbstractController{

    @Autowired
    InspectingLogInfoService inspectingLogInfoService;

    /**
     * 消防设备或者安全用具历史试验列表
     *
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit,
                  @RequestParam(required = true) Long equipmentId,
                  @RequestParam(required = true, defaultValue = "1") Integer type
    ) {

        Integer offset = (page - 1) * limit;
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.inspectinglog.ListReqData listReqData = new com.edianniu.pscp.cs.bean.inspectinglog.ListReqData();
        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setEquipmentId(equipmentId);
        listReqData.setType(type);

        com.edianniu.pscp.cs.bean.inspectinglog.ListResult result = inspectingLogInfoService.getInspectingLogList(listReqData);
        PageUtils pageUtils = new PageUtils(result.getLogs(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }


    /**
     * 消防设备或者安全用具试验
     */
    @ResponseBody
    @RequestMapping("/inspect")
    public R inspect(@RequestParam(required = true) Long equipmentId,
                     @RequestParam(required = true, defaultValue = "1") Integer type,
                     @RequestParam(required = false) String content
    ) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.inspectinglog.SaveReqData saveReqData = new com.edianniu.pscp.cs.bean.inspectinglog.SaveReqData();
        saveReqData.setContent(content);
        saveReqData.setUid(uid);
        saveReqData.setEquipmentId(equipmentId);
        saveReqData.setType(type);

        com.edianniu.pscp.cs.bean.inspectinglog.SaveResult saveResult = inspectingLogInfoService.saveInspectingLog(saveReqData);
        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getResultCode(), saveResult.getResultMessage());
        }
        return R.ok();
    }


}
