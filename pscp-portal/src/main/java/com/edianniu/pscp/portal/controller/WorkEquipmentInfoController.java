package com.edianniu.pscp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.cs.bean.equipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.equipment.SaveReqData;
import com.edianniu.pscp.cs.service.dubbo.EquipmentInfoService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;

/**
 * 工作台设备清单
 */
@Controller
@RequestMapping("equipment")
public class WorkEquipmentInfoController extends AbstractController{

    @Autowired
    EquipmentInfoService equipmentInfoService;

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

        com.edianniu.pscp.cs.bean.equipment.ListReqData listReqData = new com.edianniu.pscp.cs.bean.equipment.ListReqData();
        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setRoomId(roomId);

        com.edianniu.pscp.cs.bean.equipment.ListResult result = equipmentInfoService.equipmentVOList(listReqData);
        PageUtils pageUtils = new PageUtils(result.getEquipments(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }


    /**
     * 新增或者编辑清单列表
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@RequestParam(required = true) Long roomId,
                  @RequestParam(required = false) Long id,
                  @RequestParam(required = true) String name,
                  @RequestParam(required = false) String model,
                  @RequestParam(required = false) String ratedVoltage,
                  @RequestParam(required = false) String ratedCurrent,
                  @RequestParam(required = false) String ratedBreakingCurrent,
                  @RequestParam(required = false) String ratedCapacity,
                  @RequestParam(required = false) String maxWorkingVoltage,
                  @RequestParam(required = false) String currentTransformerRatio,
                  @RequestParam(required = false) String voltageTransformerRatio,
                  @RequestParam(required = true) String productionDate,
                  @RequestParam(required = true) String serialNumber,
                  @RequestParam(required = true) String manufacturer) {

        Long uid = ShiroUtils.getUserId();

        SaveReqData saveReqData = new SaveReqData();
        saveReqData.setId(id);
        saveReqData.setUid(uid);
        saveReqData.setRoomId(roomId);
        saveReqData.setName(name);
        saveReqData.setModel(model);
        saveReqData.setRatedVoltage(ratedVoltage);
        saveReqData.setRatedCurrent(ratedCurrent);
        saveReqData.setRatedCapacity(ratedCapacity);
        saveReqData.setRatedBreakingCurrent(ratedBreakingCurrent);
        saveReqData.setMaxWorkingVoltage(maxWorkingVoltage);
        saveReqData.setCurrentTransformerRatio(currentTransformerRatio);
        saveReqData.setVoltageTransformerRatio(voltageTransformerRatio);
        saveReqData.setProductionDate(productionDate);
        saveReqData.setSerialNumber(serialNumber);
        saveReqData.setManufacturer(manufacturer);


        com.edianniu.pscp.cs.bean.equipment.SaveResult saveResult = equipmentInfoService.saveEquipment(saveReqData);
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

        DetailsReqData detailsReqData = new DetailsReqData();
        detailsReqData.setId(id);
        detailsReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.equipment.DetailsResult detailsResult = equipmentInfoService.getEquipmentVODetails(detailsReqData);
        if (!detailsResult.isSuccess()) {
            return R.error(detailsResult.getResultCode(), detailsResult.getResultMessage());
        }
        return R.ok().put("equipment", detailsResult.getEquipment());
    }


    /**
     * 设备清单详情
     */
    @ResponseBody
    @RequestMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.equipment.DeleteReqData deleteReqData = new com.edianniu.pscp.cs.bean.equipment.DeleteReqData();
        deleteReqData.setId(id);
        deleteReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.equipment.DeleteResult deleteResult = equipmentInfoService.deleteEquipment(deleteReqData);
        if (!deleteResult.isSuccess()) {
            return R.error(deleteResult.getResultCode(), deleteResult.getResultMessage());
        }
        return R.ok();
    }


}
