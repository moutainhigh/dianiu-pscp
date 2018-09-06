package com.edianniu.pscp.portal.controller.workorder;

import com.edianniu.pscp.portal.bean.vo.EvaluateVO;
import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.evaluate.ListResult;
import com.edianniu.pscp.sps.service.dubbo.WorkOrderEvaluateInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工单评价
 * ClassName: WorkOrderEvaluateController
 * Author: tandingbo
 * CreateTime: 2017-05-09 10:31
 */
@Controller
@RequestMapping("cp/workorder/evaluate")
public class WorkOrderEvaluateController extends AbstractController {

    @Autowired
    private WorkOrderEvaluateInfoService workOrderEvaluateInfoService;


    @RequestMapping(value = "/index.html")
    public String index() {
        return "cp/evaluate.html";
    }

    /**
     * 查看工单评价
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{workOrderId}")
    @RequiresPermissions("cp:evaluate:list")
    public R list(@PathVariable("workOrderId") Long workOrderId) {

        ListReqData listReqData = new ListReqData();
        listReqData.setWorkOrderId(workOrderId);

        ListResult result = workOrderEvaluateInfoService.list(listReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }

        EvaluateVO evaluateVO = new EvaluateVO();
        BeanUtils.copyProperties(result.getWorkOrderEvaluate(), evaluateVO);
        
        return R.ok().put("result", evaluateVO);
    }
}
