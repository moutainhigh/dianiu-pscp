package com.edianniu.pscp.portal.controller.workorder;

import com.edianniu.pscp.portal.commons.ResultCode;
import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.entity.ElectricianEntity;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.ElectricianService;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.sps.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.sps.bean.project.ListProjectsReqData;
import com.edianniu.pscp.sps.bean.project.ListProjectsResult;
import com.edianniu.pscp.sps.bean.workorder.electrician.*;
import com.edianniu.pscp.sps.domain.ElectricianWorkOrder;
import com.edianniu.pscp.sps.service.dubbo.ElectricianWorkOrderInfoService;
import com.edianniu.pscp.sps.service.dubbo.EngineeringProjectInfoService;
import com.edianniu.pscp.sps.service.dubbo.SpsElectricianCertificateInfoService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 电工工单
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-11 14:29:05
 */
@Controller
@RequestMapping("/cp/workorder/electrician")
public class ElectricianWorkOrderController extends AbstractController {
    @Autowired
    private ElectricianWorkOrderInfoService electricianWorkOrderService;
    @Autowired
    private ElectricianWorkOrderInfoService electricianWorkOrderInfoService;
    @Autowired
    private EngineeringProjectInfoService engineeringProjectInfoService;
    @Autowired
    private SpsElectricianCertificateInfoService spsElectricianCertificateInfoService;
    @Autowired
    private ElectricianService electricianService;

    @RequestMapping("/index.html")
    public String list() {
        return "cp/electricianworkorder.html";
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("cp:workorder:electrician:list")
    public R list(Integer page, Integer limit, Long projectId, Long customerId,
                  String mobile, String userName, String name, String publishStartTime,
                  String publishEndTime, String finishStartTime, String finishEndTime, String status) {
        int offset = (page - 1) * limit;
        ListReqData listReqData = new ListReqData();
        listReqData.setStatus(status);
        listReqData.setPageSize(limit);
        listReqData.setOffset(offset);
        if (customerId != null && customerId.compareTo(0L) == 0) customerId = null;
        listReqData.setCustomerId(customerId);
        listReqData.setFinishEndTime(finishEndTime);
        listReqData.setMobile(mobile);
        listReqData.setFinishStartTime(finishStartTime);
        listReqData.setName(name);
        if (projectId != null && projectId.compareTo(0L) == 0) projectId = null;
        listReqData.setProjectId(projectId);
        listReqData.setPublishEndTime(publishEndTime);
        listReqData.setPublishStartTime(publishStartTime);
        listReqData.setUserName(userName);
        listReqData.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        listReqData.setUid(ShiroUtils.getUserId());

        //查询列表数据
        ListResult result = electricianWorkOrderInfoService.list(listReqData);
        PageUtils pageUtil = new PageUtils(result.getElectricianWorkOrders(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtil);
    }

    /**
     * 订单详情
     */
    @ResponseBody
    @RequestMapping("/detail/{orderId}")
    @RequiresPermissions("cp:workorder:electrician:detail")
    public R detail(@PathVariable("orderId") String orderId) {
        DetailReqData detailReqData = new DetailReqData();
        detailReqData.setOrderId(orderId);
        DetailResult result = electricianWorkOrderInfoService.detail(detailReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }

        return R.ok().put("detail", result);
    }

    /**
     * 电工资质信息
     */
    @ResponseBody
    @RequestMapping("/electricianinfo/{memberId}")
    @RequiresPermissions("cp:workorder:electrician:electricianinfo")
    public R certificates(@PathVariable("memberId") Long memberId) {
        //根据memberId获取electricianId
        ElectricianEntity electrician = electricianService.getByUid(memberId);
        if (electrician != null) {
            Long electricianId = electrician.getId();
            List<Map<String, Object>> list = spsElectricianCertificateInfoService.getByElectricianId(electricianId);
            ElectricianInfo electricianInfo = new ElectricianInfo();
            electricianInfo.setCertificates(list);
            return R.ok().put("electricianInfo", electricianInfo);
        } else {
            return R.error();
        }

    }

    /**
     * 获取项目信息
     */
    @ResponseBody
    @RequestMapping("/getprojects/{customerId}")
    @RequiresPermissions("cp:workorder:electrician:getprojects")
    public R getProjects(@PathVariable("customerId") Long customerId) {
        ListProjectsReqData reqData = new ListProjectsReqData();
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        reqData.setCustomerId(customerId);
        reqData.setUid(sysUserEntity.getUserId());
        
        ListProjectsResult result = engineeringProjectInfoService.getListByCompanyIdAndCustomerId(reqData);
        if (!result.isSuccess()) {
            R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("companyProjects", result.getProjectList());
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("cp:workorder:electrician:save")
    public R save(@RequestBody ElectricianWorkOrder electricianWorkOrder) {
        electricianWorkOrderService.save(electricianWorkOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("cp:workorder:electrician:update")
    public R update(@RequestBody ElectricianWorkOrder electricianWorkOrder) {
        electricianWorkOrderService.update(electricianWorkOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("cp:workorder:electrician:delete")
    public R delete(@RequestBody Long[] ids) {
        electricianWorkOrderService.deleteBatch(ids);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/audit/{id}")
    @RequiresPermissions("cp:workorder:electrician:audit")
    public R audit(@PathVariable(value = "id") Long id,
                   @RequestParam(value = "auditStatus") String auditStatus,
                   @RequestParam(value = "failureReason", required = false) String failureReason) {

        AuditReqData auditReqData = new AuditReqData();
        auditReqData.setId(id);
        auditReqData.setAuditStatus(auditStatus);
        auditReqData.setFailureReason(failureReason);
        auditReqData.setUid(ShiroUtils.getUserId());

        AuditResult result = electricianWorkOrderService.audit(auditReqData);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok();
    }

}
