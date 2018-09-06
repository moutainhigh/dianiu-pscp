package com.edianniu.pscp.cms.controller.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import com.edianniu.pscp.cms.entity.SmsSendLogEntity;
import com.edianniu.pscp.cms.service.SmsSendLogService;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;



/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 17:20:37
 */
@Controller
@RequestMapping("smssendlog")
public class SmsSendLogController {
    @Autowired
    private SmsSendLogService smsSendLogService;

    @RequestMapping("/smssendlog.html")
    public String list(){
        return "cp/smssendlog.html";
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("smssendlog:list")
    public R list(Integer page, Integer limit, @RequestParam(required = false) String mobile){
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        map.put("mobile", mobile);

        //查询列表数据
        List<SmsSendLogEntity> smsSendLogList = smsSendLogService.queryList(map);
        int total = smsSendLogService.queryTotal(map);

        PageUtils pageUtil = new PageUtils(smsSendLogList, total, limit, page);

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("smssendlog:info")
    public R info(@PathVariable("id") Long id){
        SmsSendLogEntity smsSendLog = smsSendLogService.queryObject(id);

        return R.ok().put("smsSendLog", smsSendLog);
    }



    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("smssendlog:update")
    public R update(@RequestBody SmsSendLogEntity smsSendLog){
        smsSendLogService.update(smsSendLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("smssendlog:delete")
    public R delete(@RequestBody Long[] ids){
        smsSendLogService.deleteBatch(ids);

        return R.ok();
    }

}
