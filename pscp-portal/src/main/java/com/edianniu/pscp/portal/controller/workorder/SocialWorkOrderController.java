package com.edianniu.pscp.portal.controller.workorder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.portal.commons.ResultCode;
import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.utils.DateUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateVO;
import com.edianniu.pscp.sps.bean.socialworkorder.SocialWorkOrderRequestType;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.cancel.CancelResult;
import com.edianniu.pscp.sps.bean.socialworkorder.confirm.ConfirmReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.confirm.ConfirmResult;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.details.DetailsResult;
import com.edianniu.pscp.sps.bean.socialworkorder.liquidate.*;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListQueryRequestInfo;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.list.ListResult;
import com.edianniu.pscp.sps.bean.socialworkorder.list.vo.SocialWorkOrderVO;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.ResumeReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.ResumeResult;
import com.edianniu.pscp.sps.service.dubbo.ElectricianResumeInfoService;
import com.edianniu.pscp.sps.service.dubbo.SocialWorkOrderInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * ClassName: SocialWorkOrderController
 * Author: tandingbo
 * CreateTime: 2017-05-09 14:39
 */
@Controller
@RequestMapping("cp/workorder/social")
public class SocialWorkOrderController extends AbstractController {

    @Autowired
    private SocialWorkOrderInfoService socialWorkOrderInfoService;
    @Autowired
    private ElectricianResumeInfoService electricianResumeInfoService;

    @RequestMapping(value = "/index.html")
    public String index() {
        return "cp/socialworkorder.html";
    }

    /**
     * 列表/查询
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    @RequiresPermissions("cp:workorder:social:list")
    public R list(@ModelAttribute ListQueryRequestInfo queryRequest,
                  @RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit) {

        ListResult listResult = new ListResult();
        ListReqData listReqData = new ListReqData();

        if (queryRequest == null) {
            queryRequest = new ListQueryRequestInfo();
            queryRequest.setStatus("unpublish");
        }

        if (!SocialWorkOrderRequestType.isExist(queryRequest.getStatus())) {
            PageUtils pageUtil = new PageUtils(listResult.getSocialWorkOrderList(), 0, limit, page);
            return R.ok().put("page", pageUtil);
        }

        SocialWorkOrderRequestType socialWorkOrderRequestType = SocialWorkOrderRequestType.get(queryRequest.getStatus());
        if (socialWorkOrderRequestType == null) {
            PageUtils pageUtil = new PageUtils(listResult.getSocialWorkOrderList(), 0, limit, page);
            return R.ok().put("page", pageUtil);
        }
        listReqData.setStatus(socialWorkOrderRequestType.getStatus());

        // 发布时间
        if (StringUtils.isNoneBlank(queryRequest.getStartTime())) {
            Date startTime = DateUtils.parse(queryRequest.getStartTime());
            if (startTime == null) {
                PageUtils pageUtil = new PageUtils(listResult.getSocialWorkOrderList(), 0, limit, page);
                return R.ok().put("page", pageUtil);
            }
            listReqData.setStartTime(DateUtils.getStartDate(startTime));
        }
        if (StringUtils.isNoneBlank(queryRequest.getEndTime())) {
            Date endTime = DateUtils.parse(queryRequest.getEndTime());
            if (endTime == null) {
                PageUtils pageUtil = new PageUtils(listResult.getSocialWorkOrderList(), 0, limit, page);
                return R.ok().put("page", pageUtil);
            }
            listReqData.setEndTime(DateUtils.getEndDate(endTime));
        }

        // 完成时间
        if (StringUtils.isNoneBlank(queryRequest.getFinishStartTime())) {
            Date finishStartTime = DateUtils.parse(queryRequest.getFinishStartTime());
            if (finishStartTime == null) {
                PageUtils pageUtil = new PageUtils(listResult.getSocialWorkOrderList(), 0, limit, page);
                return R.ok().put("page", pageUtil);
            }
            listReqData.setFinishStartTime(DateUtils.getStartDate(finishStartTime));
        }
        if (StringUtils.isNoneBlank(queryRequest.getFinishEndTime())) {
            Date finishEndTime = DateUtils.parse(queryRequest.getFinishEndTime());
            if (finishEndTime == null) {
                PageUtils pageUtil = new PageUtils(listResult.getSocialWorkOrderList(), 0, limit, page);
                return R.ok().put("page", pageUtil);
            }
            listReqData.setFinishEndTime(DateUtils.getEndDate(finishEndTime));
        }

        int offset = (page - 1) * limit;
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setListQueryRequestInfo(queryRequest);
        listReqData.setMemberId(ShiroUtils.getUserEntity().getUserId());
        listReqData.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());

        //查询列表数据
        listResult = socialWorkOrderInfoService.list(listReqData);
        List<SocialWorkOrderVO> socialWorkOrderVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(listResult.getSocialWorkOrderList())) {
            Map<Integer, String> mapSocialWorkOrderStatus = socialWorkOrderInfoService.getAllSocialWorkOrderStatus();
            for (SocialWorkOrderVO socialWorkOrderVO : listResult.getSocialWorkOrderList()) {
                if (StringUtils.isBlank(socialWorkOrderVO.getStatus())) {
                    continue;
                }
                Integer status = Integer.valueOf(socialWorkOrderVO.getStatus());
                if (mapSocialWorkOrderStatus.containsKey(status)) {
                    socialWorkOrderVO.setStatus(mapSocialWorkOrderStatus.get(status));

                }
                socialWorkOrderVOList.add(socialWorkOrderVO);
            }
        }
        PageUtils pageUtil = new PageUtils(socialWorkOrderVOList, listResult.getTotalCount(), limit, page);
        return R.ok().put("page", pageUtil);
    }

    @ResponseBody
    @RequestMapping(value = "/cancel/{id}")
    @RequiresPermissions("cp:workorder:social:cancel")
    public R cancel(@PathVariable("id") String orderId) {
        CancelReqData cancelReqData = new CancelReqData();
//        cancelReqData.setId(id);
        cancelReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        cancelReqData.setOrderId(orderId);
        CancelResult result = socialWorkOrderInfoService.cancel(cancelReqData);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/details/{id}")
    @RequiresPermissions("cp:workorder:social:details")
    public R details(@PathVariable("id") String orderId) {
        DetailsReqData detailsReqData = new DetailsReqData();
//        detailsReqData.setId(id);
        detailsReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        detailsReqData.setOrderId(orderId);
        DetailsResult result = socialWorkOrderInfoService.details(detailsReqData);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

    @ResponseBody
    @RequestMapping(value = "/confirm")
    @RequiresPermissions("cp:workorder:social:confirm")
    public R confirm(@RequestBody JSONObject jsonReq) {
        ConfirmReqData confirmReqData = new ConfirmReqData();
        confirmReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        confirmReqData.setSocialWorkOrderId(jsonReq.getLong("id"));

        JSONArray jsonArray = jsonReq.getJSONArray("socialElectricianList");
        if (jsonArray == null || jsonArray.size() <= 0) {
            return R.ok();
        }

        List<Map<String, Object>> mapList = new ArrayList<>();
        Integer size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String auditing = jsonObject.getString("auditing");
            if (StringUtils.isNoneBlank(auditing)) {
                Map<String, Object> map = new HashMap<>();
                map.put("auditing", auditing);
                map.put("electricianId", jsonObject.getLong("electricianId"));
                map.put("electricianWorkOrderId", jsonObject.getLong("electricianWorkOrderId"));
                mapList.add(map);
            }
        }
        confirmReqData.setSocialElectricianList(mapList);

        ConfirmResult result = socialWorkOrderInfoService.confirm(confirmReqData);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/resume/{id}")
    @RequiresPermissions("cp:workorder:social:resume")
    public R resume(@PathVariable("id") Long id) {
        ResumeReqData resumeReqData = new ResumeReqData();
        resumeReqData.setElectricianId(id);

        ResumeResult result = electricianResumeInfoService.details(resumeReqData);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

    @ResponseBody
    @RequestMapping(value = "/liquidate/{id}/details")
    @RequiresPermissions("cp:workorder:social:liquidate:details")
    public R liquidateDetails(@PathVariable("id") String orderId) {
        LiquidateDetailsBackgroundReqData reqData = new LiquidateDetailsBackgroundReqData();
        reqData.setOrderId(orderId);
        reqData.setUid(ShiroUtils.getUserEntity().getUserId());
        LiquidateDetailsBackgroundResult result = socialWorkOrderInfoService.liquidateDetailsBackground(reqData);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

    @ResponseBody
    @RequestMapping(value = "/liquidate")
    @RequiresPermissions("cp:workorder:social:liquidate")
    public R liquidate(@RequestBody JSONObject jsonReq, HttpServletRequest request) {
        if (jsonReq.get("electricianWorkOrderList") == null) {
            return R.error("结算数据不能为空");
        }

        // 支付数据解析
        JSONArray jsonArray = jsonReq.getJSONArray("electricianWorkOrderList");
        if (jsonArray == null || jsonArray.size() < 1) {
            return R.error("结算数据不能为空");
        }

        List<LiquidateInfo> liquidateInfoList = new ArrayList<>();
        Integer size = jsonArray.size();
        Long socialWorkOrderId = 0L;
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // 这里只处理页面选中的数据
            if (jsonObject.get("checked") != null && jsonObject.getInteger("checked").equals(1)) {
                LiquidateInfo liquidateInfo = new LiquidateInfo();
                liquidateInfo.setElectricianId(jsonObject.getLong("electricianId"));
                liquidateInfo.setActualWorkTime(jsonObject.getDouble("actualWorkTime"));
                liquidateInfo.setElectricianWorkOrderId(jsonObject.getLong("electricianWorkOrderId"));

                // 评价信息
                EvaluateVO evaluateVO = jsonObject.getObject("evaluate", EvaluateVO.class);
                // 第一次评价
                if (evaluateVO != null) {
                    liquidateInfo.setEvaluateInfo(evaluateVO);
                }

                // 社会工单ID
                socialWorkOrderId = jsonObject.getLong("socialWorkOrderId");
                liquidateInfoList.add(liquidateInfo);
            }
        }

        if (CollectionUtils.isEmpty(liquidateInfoList)) {
            return R.error(ResultCode.EXIST_LOGIN_NAME, "请选择结算社会工单信息");
        }

        LiquidateReqData liquidateReqData = new LiquidateReqData();
//        liquidateReqData.setIp(IPUtil.getIpAddress(request));
        liquidateReqData.setLiquidateInfoList(liquidateInfoList);
        liquidateReqData.setSocialWorkOrderId(socialWorkOrderId);
        liquidateReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        LiquidateResult result = socialWorkOrderInfoService.liquidate(liquidateReqData);
        if (StringUtils.isBlank(result.getStatus()) || "failure".equals(result.getStatus())) {
            return R.error(ResultCode.TO_PAYMENT, result.getResultMessage());
        }

        return R.ok().put("result", result);
    }

    @ResponseBody
    @RequestMapping(value = "/liquidate/evaluate")
    @RequiresPermissions("cp:workorder:social:liquidate:evaluate")
    public R liquidate(@RequestBody JSONObject jsonReq) {
        String orderId = jsonReq.getString("orderId");
        if (StringUtils.isBlank(orderId)) {
            return R.error(ResultCode.EXIST_LOGIN_NAME, "请求参数错误");
        }
        Long electricianId = jsonReq.getLong("electricianId");
        if (electricianId == null) {
            return R.error(ResultCode.EXIST_LOGIN_NAME, "请求参数错误");
        }

        LiquidateEvaluateReqData reqData = new LiquidateEvaluateReqData();
        reqData.setOrderId(orderId);
        reqData.setElectricianId(electricianId);
        reqData.setUid(ShiroUtils.getUserEntity().getUserId());
        LiquidateEvaluateResult result = socialWorkOrderInfoService.liquidateEvaluateForElectrician(reqData);
        if (result.getResultCode() != ResultCode.SUCCESS) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", result);
    }

}