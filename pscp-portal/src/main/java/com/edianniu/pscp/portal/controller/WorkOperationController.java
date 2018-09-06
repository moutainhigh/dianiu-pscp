package com.edianniu.pscp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.cs.service.dubbo.OperationRecordInfoService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;

/**
 * 工作台操作记录
 */
@Controller
@RequestMapping("operation")
public class WorkOperationController extends AbstractController{

    @Autowired
    OperationRecordInfoService operationRecordInfoService;

    /**
     * 设备清单列表
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

        com.edianniu.pscp.cs.bean.operationrecord.ListReqData listReqData = new com.edianniu.pscp.cs.bean.operationrecord.ListReqData();
        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setRoomId(roomId);

        com.edianniu.pscp.cs.bean.operationrecord.ListResult result = operationRecordInfoService.operationRecordList(listReqData);
        PageUtils pageUtils = new PageUtils(result.getOperations(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }


    /**
     * 新增或者编辑清单列表
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@RequestParam(required = true) Long roomId,
                  @RequestParam(required = false) Long id,
                  @RequestParam(required = true) String equipmentTask,
                  @RequestParam(required = true) String groundLeadNum,
                  @RequestParam(required = true) String startTime,
                  @RequestParam(required = true) String endTime,
                  @RequestParam(required = true) String issuingCommand,
                  @RequestParam(required = true) String receiveCommand,
                  @RequestParam(required = true) String operator,
                  @RequestParam(required = true) String guardian,
                  @RequestParam(required = false) String remark) {

        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.operationrecord.SaveReqData saveReqData = new com.edianniu.pscp.cs.bean.operationrecord.SaveReqData();
        saveReqData.setUid(uid);
        saveReqData.setId(id);
        saveReqData.setRoomId(roomId);
        saveReqData.setEquipmentTask(equipmentTask);
        saveReqData.setGroundLeadNum(groundLeadNum);
        saveReqData.setStartTime(startTime);
        saveReqData.setEndTime(endTime);
        saveReqData.setIssuingCommand(issuingCommand);
        saveReqData.setReceiveCommand(receiveCommand);
        saveReqData.setOperator(operator);
        saveReqData.setGuardian(guardian);
        saveReqData.setRemark(remark);

        com.edianniu.pscp.cs.bean.operationrecord.SaveResult saveResult = operationRecordInfoService.saveOperationRecord(saveReqData);
        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getResultCode(), saveResult.getResultMessage());
        }
        return R.ok();
    }


    /**
     * 设备清单详情
     */
    @ResponseBody
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.operationrecord.DetailsReqData detailsReqData = new com.edianniu.pscp.cs.bean.operationrecord.DetailsReqData();
        detailsReqData.setId(id);
        detailsReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.operationrecord.DetailsResult detailsResult = operationRecordInfoService.getOperationRecordDetails(detailsReqData);
        if (!detailsResult.isSuccess()) {
            return R.error(detailsResult.getResultCode(), detailsResult.getResultMessage());
        }
        return R.ok().put("operation", detailsResult.getOperation());
    }


    /**
     * 设备清单详情
     */
    @ResponseBody
    @RequestMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.operationrecord.DeleteReqData deleteReqData = new com.edianniu.pscp.cs.bean.operationrecord.DeleteReqData();
        deleteReqData.setId(id);
        deleteReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.operationrecord.DeleteResult deleteResult = operationRecordInfoService.deleteOperationRecord(deleteReqData);
        if (!deleteResult.isSuccess()) {
            return R.error(deleteResult.getResultCode(), deleteResult.getResultMessage());
        }
        return R.ok();
    }


}
