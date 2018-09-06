package com.edianniu.pscp.portal.controller;

import com.edianniu.pscp.cs.bean.safetyequipment.SaveResult;
import com.edianniu.pscp.cs.service.dubbo.SafetyEquipmentInfoService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工作台安全用具
 */
@Controller
@RequestMapping("safetyequipment")
public class WorkSafetyEquipmentController extends AbstractController{

    @Autowired
    SafetyEquipmentInfoService safetyEquipmentInfoService;

    /**
     * 安全用具列表
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

        com.edianniu.pscp.cs.bean.safetyequipment.ListReqData listReqData = new com.edianniu.pscp.cs.bean.safetyequipment.ListReqData();
        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setRoomId(roomId);

        com.edianniu.pscp.cs.bean.safetyequipment.ListResult result = safetyEquipmentInfoService.safetyEquipmentList(listReqData);
        PageUtils pageUtils = new PageUtils(result.getSafetyEquipments(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }


    /**
     * 新增或者编辑安全用具
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@RequestParam(required = true) Long roomId,
                  @RequestParam(required = true) String name,
                  @RequestParam(required = true) String serialNumber,
                  @RequestParam(required = true) String modelNumber,
                  @RequestParam(required = true) String voltageLevel,
                  @RequestParam(required = true) String manufacturer,
                  @RequestParam(required = true) Integer testCycle,
                  @RequestParam(required = true) Integer testTimeUnit,
                  @RequestParam(required = true) String initialTestDate,
                  @RequestParam(required = false) String remark) {

        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.safetyequipment.SaveReqData saveReqData = new com.edianniu.pscp.cs.bean.safetyequipment.SaveReqData();
        saveReqData.setUid(uid);
        saveReqData.setName(name);
        saveReqData.setRoomId(roomId);
        saveReqData.setSerialNumber(serialNumber);
        saveReqData.setModelNumber(modelNumber);
        saveReqData.setVoltageLevel(voltageLevel);
        saveReqData.setManufacturer(manufacturer);
        saveReqData.setTestCycle(testCycle);
        saveReqData.setTestTimeUnit(testTimeUnit);
        saveReqData.setInitialTestDate(initialTestDate);
        saveReqData.setRemark(remark);

        SaveResult saveResult = safetyEquipmentInfoService.saveSafetyEquipment(saveReqData);
        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getResultCode(), saveResult.getResultMessage());
        }
        return R.ok();
    }


    /**
     * 安全用具详情
     */
    @ResponseBody
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.safetyequipment.DetailsReqData detailsReqData = new com.edianniu.pscp.cs.bean.safetyequipment.DetailsReqData();
        detailsReqData.setId(id);
        detailsReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.safetyequipment.DetailsResult detailsResult = safetyEquipmentInfoService.getSafetyEquipmentDetails(detailsReqData);
        if (!detailsResult.isSuccess()) {
            return R.error(detailsResult.getResultCode(), detailsResult.getResultMessage());
        }
        return R.ok().put("operation", detailsResult.getSafetyEquipment());
    }


    /**
     * 删除安全用具
     */
    @ResponseBody
    @RequestMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.safetyequipment.DeleteReqData deleteReqData = new com.edianniu.pscp.cs.bean.safetyequipment.DeleteReqData();
        deleteReqData.setId(id);
        deleteReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.safetyequipment.DeleteResult deleteResult = safetyEquipmentInfoService.deleteSafetyEquipment(deleteReqData);
        if (!deleteResult.isSuccess()) {
            return R.error(deleteResult.getResultCode(), deleteResult.getResultMessage());
        }
        return R.ok();
    }




}
