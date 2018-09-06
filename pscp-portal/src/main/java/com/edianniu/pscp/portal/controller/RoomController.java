package com.edianniu.pscp.portal.controller;

import com.edianniu.pscp.cs.bean.room.ListReqData;
import com.edianniu.pscp.cs.bean.room.ListResult;
import com.edianniu.pscp.cs.bean.room.RoomInfo;
import com.edianniu.pscp.cs.bean.room.SaveResult;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.portal.utils.BizUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.sps.bean.project.DeleteResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomInfoService roomInfoService;
    
    @RequestMapping("/index.html")
    public String list(){
    	return "/cp/room.html";
    }

    /**
     * 配电房列表
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequiresPermissions("room:list")
    @RequestMapping("/list")
    public R list(@RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit,
                  @ModelAttribute ListReqData listReqData) {

        Integer offset = (page - 1) * limit;
        Long uid = ShiroUtils.getUserId();

        listReqData.setUid(uid);
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        String startTime = listReqData.getStartTime();
        if (StringUtils.isNotBlank(startTime) && !BizUtils.checkYmd(startTime)) {
        	return R.error("开始时间不合法");
		}
        String endTime = listReqData.getEndTime();
        if (StringUtils.isNotBlank(endTime) && !BizUtils.checkYmd(endTime)) {
        	return R.error("结束时间不合法");
		}

        ListResult result = roomInfoService.roomListResult(listReqData);
        PageUtils pageUtils = new PageUtils(result.getDistributionRooms(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtils);
    }

    /**
     * 新增或者编辑配电房
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("room:save")
    public R save(@RequestParam(required = true) String name,
                  @RequestParam(required = true) String director,
                  @RequestParam(required = true) String contactNumber,
                  @RequestParam(required = true) String address,
                  @RequestParam(required = false) Long id) {

        Long uid = ShiroUtils.getUserId();
        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setName(name);
        roomInfo.setAddress(address);
        roomInfo.setId(id);
        roomInfo.setDirector(director);
        roomInfo.setContactNumber(contactNumber);
        SaveResult saveResult = roomInfoService.save(uid, roomInfo);
        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getResultCode(), saveResult.getResultMessage());
        }
        return R.ok();
    }
    
    /**
     * 详情
     */
    @ResponseBody
    @RequiresPermissions("room:info")
    @RequestMapping("/info/{id}")
    public R Info(@PathVariable("id") Long id){
    	RoomVO roomVO = roomInfoService.getRoomById(id);
    	if (null == roomVO) {
			return R.error("id不合法");
		}
    	return R.ok().put("room", roomVO);
    }

    /**
     * 删除配电房
     */
    @ResponseBody
    @RequiresPermissions("room:delete")
    @RequestMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        Long uid = ShiroUtils.getUserId();
        DeleteResult deleteResult = roomInfoService.delete(id, uid);
        if (!deleteResult.isSuccess()) {
            return R.error(deleteResult.getResultCode(), deleteResult.getResultMessage());
        }
        return R.ok();
    }


}
