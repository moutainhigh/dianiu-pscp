package com.edianniu.pscp.das.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: NeedsController
 * Author: tandingbo
 * CreateTime: 2017-10-11 11:44
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    

   /* @PostMapping("/search")
    public NeedsPageListResult query(
            @RequestParam(value = "text") String searchText,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        NeedsPageListReqData reqData = new NeedsPageListReqData();
        reqData.setSearchText(searchText);
        reqData.setOffset(offset);
        reqData.setPageSize(size);
        return needsDubboService.pageListNeedsVO(reqData);
    }

    //    @PostMapping("/save")
    public NeedsSaveResult save(NeedsSaveReqData reqData) {
        NeedsSaveResult result = new NeedsSaveResult();
        try {

            result = needsDubboService.save(reqData);
        } catch (Exception e) {
            e.printStackTrace();
            result.set(500, "添加失败");
        }
        return result;
    }

    //    @PostMapping("/delete/{needsId}")
    public NeedsDeleteResult delete(@PathVariable(value = "needsId") String needsId) {
        NeedsDeleteResult result = new NeedsDeleteResult();
        try {
            NeedsDeleteReqData reqData = new NeedsDeleteReqData();
            reqData.setNeedsId(needsId);
            result = needsDubboService.deleteById(reqData);
        } catch (Exception e) {
            e.printStackTrace();
            result.set(500, "删除失败");
        }
        return result;
    }*/
}
