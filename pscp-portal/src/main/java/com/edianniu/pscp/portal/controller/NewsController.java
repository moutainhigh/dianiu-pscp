package com.edianniu.pscp.portal.controller;

import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.sps.bean.news.DetailsVOResult;
import com.edianniu.pscp.sps.bean.news.ListPageVOResult;
import com.edianniu.pscp.sps.bean.news.NewsType;
import com.edianniu.pscp.sps.bean.news.PageNewsListInfoVOReqData;
import com.edianniu.pscp.sps.service.dubbo.NewsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: NewsControlller
 * Author: tandingbo
 * CreateTime: 2017-08-11 15:05
 */
@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    @Qualifier("newsInfoService")
    private NewsInfoService newsInfoService;

    /**
     * 门户新闻列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String newsIndex(Model model) {
        return "news/news.html";
    }

    /**
     * 移动端新闻列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/m_index.html", method = RequestMethod.GET)
    public String mnewsIndex(Model model) {
        return "news/m_news.html";
    }

    /**
     * 行业资讯
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/industry")
    @ResponseBody
    public R industryList(@RequestParam(required = false, defaultValue = "1") Integer page,
                          @RequestParam(required = false, defaultValue = "10") Integer limit) {
        Integer offset = (page - 1) * limit;

        PageNewsListInfoVOReqData reqData = new PageNewsListInfoVOReqData();
        reqData.setOffset(offset);
        reqData.setPageSize(limit);
        reqData.setType(NewsType.INDUSTRY.getValue());
        ListPageVOResult result = newsInfoService.selectPageNewsListInfoVO(reqData);

        PageUtils pageUtil = new PageUtils(result.getNewsList(), result.getTotalCount(), limit, page);
        return R.ok().put("page", pageUtil);
    }

    /**
     * 公告消息
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/notice")
    @ResponseBody
    public R noticeList(@RequestParam(required = false, defaultValue = "1") Integer page,
                        @RequestParam(required = false, defaultValue = "10") Integer limit) {
        Integer offset = (page - 1) * limit;

        PageNewsListInfoVOReqData reqData = new PageNewsListInfoVOReqData();
        reqData.setOffset(offset);
        reqData.setPageSize(limit);
        reqData.setType(NewsType.NOTICE.getValue());
        ListPageVOResult result = newsInfoService.selectPageNewsListInfoVO(reqData);

        PageUtils pageUtil = new PageUtils(result.getNewsList(), result.getTotalCount(), limit, page);
        return R.ok().put("page", pageUtil);
    }

    /**
     * 新闻详情
     *
     * @return
     */
    @RequestMapping(value = "/{id}/details", method = RequestMethod.GET)
    public String details(@PathVariable(value = "id") Long id, Model model) {
        DetailsVOResult result = newsInfoService.getNewsDetailsVOById(id);
        model.addAttribute("details", result.getNewsDetails());
        return "news/newsDetail.html";
    }
}
