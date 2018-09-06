package com.edianniu.pscp.portal.controller;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.needs.*;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsMarketVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.portal.bean.vo.NeedsMarketListVO;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.sps.bean.news.DetailsVOResult;
import com.edianniu.pscp.sps.bean.news.ListTopNumReqData;
import com.edianniu.pscp.sps.bean.news.ListTopNumResult;
import com.edianniu.pscp.sps.bean.news.NewsType;
import com.edianniu.pscp.sps.service.dubbo.NewsInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 无线应用接口
 *
 * @author tandingbo
 * @create 2017-11-21 10:17
 */
@RestController
@RequestMapping("/wap")
public class WirelessApplicationController {

    @Autowired
    @Qualifier("needsInfoService")
    private NeedsInfoService needsInfoService;
    @Autowired
    @Qualifier("newsInfoService")
    private NewsInfoService newsInfoService;

    /**
     * 首页接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public R index() {
        Map<String, Object> datas = new HashMap<>();
        // 行业资讯
        ListTopNumReqData listTopNumReqData = new ListTopNumReqData();
        listTopNumReqData.setTopn(4);
        listTopNumReqData.setType(NewsType.INDUSTRY.getValue());
        ListTopNumResult industryListTopNumResult = newsInfoService.selectNewsListInfoVOTopNum(listTopNumReqData);
        if (industryListTopNumResult.isSuccess()) {
            datas.put("industryTopList", industryListTopNumResult.getNewsList());
        }

        // 公告消息
        listTopNumReqData.setTopn(5);
        listTopNumReqData.setType(NewsType.NOTICE.getValue());
        ListTopNumResult noticeListTopNumResult = newsInfoService.selectNewsListInfoVOTopNum(listTopNumReqData);
        if (noticeListTopNumResult.isSuccess()) {
            datas.put("noticeTopList", noticeListTopNumResult.getNewsList());
        }

        // 最新需求
        NeedsMarketListReqData reqData = new NeedsMarketListReqData();
        reqData.setOffset(0);
        reqData.setPageSize(6);
        NeedsMarketListResult needsMarketListResult = needsInfoService.queryListNeedsMarketPortal(reqData);
        if (needsMarketListResult.isSuccess()) {
            List<NeedsMarketListVO> needsMarketList = new ArrayList<>();
            List<NeedsMarketVO> getNeedsList = needsMarketListResult.getNeedsList();
            if (CollectionUtils.isNotEmpty(getNeedsList)) {
                for (NeedsMarketVO needsMarketVO : getNeedsList) {
                    NeedsMarketListVO needsMarketListVO = new NeedsMarketListVO();
                    BeanUtils.copyProperties(needsMarketVO, needsMarketListVO);
                    needsMarketList.add(needsMarketListVO);
                }
            }
            datas.put("needsTopList", needsMarketList);
        }

        return R.ok().put("result", JSON.toJSONString(datas));
    }

    /**
     * 需求详情
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/needs/{orderId}", method = RequestMethod.POST)
    public R needsDetails(@PathVariable String orderId) {
        NeedsMarketDetailsReqData reqData = new NeedsMarketDetailsReqData();
        reqData.setOrderId(orderId);
        NeedsMarketDetailsResult needsMarketDetails = needsInfoService.getNeedsMarketDetails(reqData);
        if (!needsMarketDetails.isSuccess()) {
            return R.error(needsMarketDetails.getResultCode(), needsMarketDetails.getResultMessage());
        }
        NeedsVO needsInfo = needsMarketDetails.getNeedsInfo();
        if (needsInfo != null) {
            List<Attachment> attachmentList = needsInfo.getAttachmentList();
            if (CollectionUtils.isNotEmpty(attachmentList)) {
                for (Attachment attachment : attachmentList) {
                    attachment.setFid(prefix + attachment.getFid());
                }
            }
        }
        return R.ok().put("result", JSON.toJSONString(needsInfo));
    }

    /**
     * 新闻资讯详情
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/news/{id}", method = RequestMethod.POST)
    public R details(@PathVariable(value = "id") Long id) {
        DetailsVOResult result = newsInfoService.getNewsDetailsVOById(id);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok().put("result", JSON.toJSONString(result.getNewsDetails()));
    }

    /**
     * 附件下载域名地址
     */
    private String prefix;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String prefix) {
        this.prefix = prefix;
    }
}
