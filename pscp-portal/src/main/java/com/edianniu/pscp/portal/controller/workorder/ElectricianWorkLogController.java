package com.edianniu.pscp.portal.controller.workorder;

import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListResult;
import com.edianniu.pscp.sps.bean.workorder.worklog.DetailReqData;
import com.edianniu.pscp.sps.bean.workorder.worklog.DetailResult;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListReqData;
import com.edianniu.pscp.sps.service.dubbo.ElectricianWorkLogInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 11:16:43
 */
@Controller
@RequestMapping("cp/electrician/worklog")
public class ElectricianWorkLogController {

    @Autowired
    private ElectricianWorkLogInfoService electricianWorkLogInfoService;

    @RequestMapping("/index.html")
    public String index() {
        return "cp/workOrder/worklog.html";
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/{workOrderId}")
    @RequiresPermissions("cp:worklog:list")
    public R list(@PathVariable("workOrderId") String orderId,
                  @RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit) {

        int offset = (page - 1) * limit;
        ListReqData listReqData = new ListReqData();
        listReqData.setOrderId(orderId);
        listReqData.setPageSize(limit);
        listReqData.setOffset(offset);

        ListResult result = electricianWorkLogInfoService.list(listReqData);
        PageUtils pageUtil = new PageUtils(result.getWorkLogInfoList(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtil);
    }
    
    @ResponseBody
    @RequestMapping("/detail/{id}")
    //@RequiresPermissions("cp:worklog:detail")
    public R info(@PathVariable("id") Long id){
    	DetailReqData detailReqData = new DetailReqData();
    	Long userId = ShiroUtils.getUserId();
    	detailReqData.setUid(userId);
    	detailReqData.setId(id);
    	detailReqData.setSource("PC");
		DetailResult result = electricianWorkLogInfoService.detail(detailReqData);
    	if (! result.isSuccess()) {
    		return R.error(result.getResultMessage());
		}
		
    	return R.ok().put("detail", result.getWorkLogInfo());
    }

}
