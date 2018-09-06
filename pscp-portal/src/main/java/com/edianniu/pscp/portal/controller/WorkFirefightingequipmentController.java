package com.edianniu.pscp.portal.controller;

import com.edianniu.pscp.cs.bean.firefightingequipment.SaveResult;
import com.edianniu.pscp.cs.service.dubbo.FirefightingEquipmentInfoService;
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
 * 工作台消防设施
 */
@Controller
@RequestMapping("firefightingequipment")
public class WorkFirefightingequipmentController extends AbstractController{

    @Autowired
    FirefightingEquipmentInfoService firefightingEquipmentInfoService;

    /**
     * 消防设施列表
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

        com.edianniu.pscp.cs.bean.firefightingequipment.ListReqData listReqData = new com.edianniu.pscp.cs.bean.firefightingequipment.ListReqData();
        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setRoomId(roomId);

        com.edianniu.pscp.cs.bean.firefightingequipment.ListResult result = firefightingEquipmentInfoService.firefightingEquipmentList(listReqData);
        PageUtils pageUtils = new PageUtils(result.getFirefightingEquipments(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }


    /**
     * 新增或者编辑消防设施
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@RequestParam(required = true) Long roomId,
                  @RequestParam(required = true) String name,
                  @RequestParam(required = true) String boxNumber,
                  @RequestParam(required = true) String serialNumber,
                  @RequestParam(required = true) String placementPosition,
                  @RequestParam(required = true) String specifications,
                  @RequestParam(required = true) Integer indoorOrOutdoor,
                  @RequestParam(required = true) String productionDate,
                  @RequestParam(required = true) String expiryDate,
                  @RequestParam(required = true) Integer viewCycle,
                  @RequestParam(required = true) Integer viewTimeUnit,
                  @RequestParam(required = true) String initialTestDate
    ) {

        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.firefightingequipment.SaveReqData saveReqData = new com.edianniu.pscp.cs.bean.firefightingequipment.SaveReqData();
        saveReqData.setUid(uid);
        saveReqData.setName(name);
        saveReqData.setRoomId(roomId);
        saveReqData.setSerialNumber(serialNumber);
        saveReqData.setExpiryDate(expiryDate);
        saveReqData.setBoxNumber(boxNumber);
        saveReqData.setPlacementPosition(placementPosition);
        saveReqData.setSpecifications(specifications);
        saveReqData.setIndoorOrOutdoor(indoorOrOutdoor);
        saveReqData.setViewCycle(viewCycle);
        saveReqData.setViewTimeUnit(viewTimeUnit);
        saveReqData.setProductionDate(productionDate);
        saveReqData.setInitialTestDate(initialTestDate);

        SaveResult saveResult = firefightingEquipmentInfoService.saveFirefightingEquipment(saveReqData);
        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getResultCode(), saveResult.getResultMessage());
        }
        return R.ok();
    }


    /**
     * 消防设施详情
     */
    @ResponseBody
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.firefightingequipment.DetailsReqData detailsReqData = new com.edianniu.pscp.cs.bean.firefightingequipment.DetailsReqData();
        detailsReqData.setId(id);
        detailsReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.firefightingequipment.DetailsResult detailsResult = firefightingEquipmentInfoService.getFriefightingEquipmentDetails(detailsReqData);
        if (!detailsResult.isSuccess()) {
            return R.error(detailsResult.getResultCode(), detailsResult.getResultMessage());
        }
        return R.ok().put("firefightingEquipment", detailsResult.getFirefightingEquipment());
    }


    /**
     * 删除消防设施
     */
    @ResponseBody
    @RequestMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();

        com.edianniu.pscp.cs.bean.firefightingequipment.DeleteReqData deleteReqData = new com.edianniu.pscp.cs.bean.firefightingequipment.DeleteReqData();
        deleteReqData.setId(id);
        deleteReqData.setUid(uid);

        com.edianniu.pscp.cs.bean.firefightingequipment.DeleteResult deleteResult = firefightingEquipmentInfoService.deleteFirefightingEquipment(deleteReqData);
        if (!deleteResult.isSuccess()) {
            return R.error(deleteResult.getResultCode(), deleteResult.getResultMessage());
        }
        return R.ok();
    }


}
