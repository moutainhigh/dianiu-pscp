package com.edianniu.pscp.portal.controller;

import com.edianniu.pscp.mis.bean.message.*;
import com.edianniu.pscp.mis.service.dubbo.MemberMessageInfoService;
import com.edianniu.pscp.portal.commons.Constants;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: MemberMessageController
 * Author: tandingbo
 * CreateTime: 2017-09-28 10:29
 */
@Controller
@RequestMapping("message")
public class MemberMessageController {

    @Autowired
    private MemberMessageInfoService memberMessageInfoService;

    /**
     * 获取未读消息
     *
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping("/unread")
    @RequiresPermissions("cp:message:unread")
    public R unread(@RequestParam(required = false, defaultValue = "1") Integer page,
                    @RequestParam(required = false, defaultValue = "10") Integer limit) {

        int offset = (page - 1) * limit;
        ListMessageReqData listMessageReqData = new ListMessageReqData();
        listMessageReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        listMessageReqData.setOffset(offset);
        listMessageReqData.setPageSize(limit);
        listMessageReqData.setIsRead(Constants.TAG_NO);

        ListMessageResult result = memberMessageInfoService.listMessage(listMessageReqData);
        if (result.isSuccess()) {
            PageUtils pageUtil = new PageUtils(result.getMessages(), result.getTotalCount(), limit, page);
            return R.ok().put("page", pageUtil);
            //return R.ok().put("messageList", result.getMessages());
        }
        return R.ok();
    }

    /**
     * 获取已读消息
     *
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping("/read")
    @RequiresPermissions("cp:message:read")
    public R read(@RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit) {

        int offset = (page - 1) * limit;
        ListMessageReqData listMessageReqData = new ListMessageReqData();
        listMessageReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        listMessageReqData.setOffset(offset);
        listMessageReqData.setPageSize(limit);
        ListMessageResult result = memberMessageInfoService.listMessage(listMessageReqData);
        if (result.isSuccess()) {
            PageUtils pageUtil = new PageUtils(result.getMessages(), result.getTotalCount(), limit, page);
            return R.ok().put("page", pageUtil);
        }
        return R.ok();
    }

    /**
     * 消息设置已读
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/read/{id}")
    @RequiresPermissions("cp:message:setread")
    public R setRead(@PathVariable("id") Long id) {
        SetReadMessageReqData setReadMessageReqData = new SetReadMessageReqData();
        setReadMessageReqData.setId(id);
        setReadMessageReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        SetReadMessageResult result = memberMessageInfoService.setReadMessage(setReadMessageReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok();
    }

    /**
     * 清空所有消息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/clear")
    @RequiresPermissions("cp:message:clear")
    public R clearAllMessage() {
        ClearAllMessageReqData clearAllMessageReqData = new ClearAllMessageReqData();
        clearAllMessageReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        ClearAllMessageResult result = memberMessageInfoService.clearAllMessage(clearAllMessageReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok();
    }

}
