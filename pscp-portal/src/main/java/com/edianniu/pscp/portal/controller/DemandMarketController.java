package com.edianniu.pscp.portal.controller;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.needs.NeedsMarketListReqData;
import com.edianniu.pscp.cs.bean.needs.NeedsMarketListResult;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsMarketVO;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.portal.bean.vo.NeedsMarketListVO;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 需求市场
 * ClassName: DemandMarketController
 * Author: tandingbo
 * CreateTime: 2017-09-28 11:36
 */
@Controller
@RequestMapping("needs")
public class DemandMarketController extends AbstractController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("needsInfoService")
    private NeedsInfoService needsInfoService;

    /**
     * 需求市场
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/market")
    public R list(@RequestParam(required = false, defaultValue = "1") Integer page,
                  @RequestParam(required = false, defaultValue = "10") Integer limit,
                  @RequestParam(required = false) String searchText) {

        // 最新需求
        NeedsMarketListReqData listReqData = new NeedsMarketListReqData();
        if (super.getUser() != null) {
            listReqData.setUid(super.getUserId());
        }
        int offset = (page - 1) * limit;
        listReqData.setOffset(offset);
        listReqData.setPageSize(limit);
        listReqData.setSearchText(searchText);
        NeedsMarketListResult result = needsInfoService.queryListNeedsMarketPortal(listReqData);

        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }

        List<NeedsMarketListVO> needsMarketListVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result.getNeedsList())) {
            for (NeedsMarketVO needsMarketVO : result.getNeedsList()) {
                NeedsMarketListVO needsMarketListVO = new NeedsMarketListVO();
                BeanUtils.copyProperties(needsMarketVO, needsMarketListVO);
                needsMarketListVOList.add(needsMarketListVO);
            }
        }

        logger.debug(JSON.toJSONString(result.getNeedsList()));
        PageUtils pageUtil = new PageUtils(needsMarketListVOList, result.getTotalCount(), limit, page);
        return R.ok().put("page", pageUtil);
    }

}
