package com.edianniu.pscp.cms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.cms.commons.ResultCode;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cms.utils.ShiroUtils;
import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.sps.bean.news.*;
import com.edianniu.pscp.sps.domain.News;
import com.edianniu.pscp.sps.service.dubbo.NewsInfoService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: NewsController
 * Author: tandingbo
 * CreateTime: 2017-08-16 16:49
 */
@Controller
@RequestMapping("/news")
public class NewsController extends AbstractController {

    @Autowired
    private NewsInfoService newsInfoService;
    @Autowired
    private FileUploadService fileUploadService;

    private String fileDownloadUrl = "http://111.1.17.197:8091/";

    @Value(value = "${file.download.url:}")
    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }

    /**
     * 资讯列表
     */
    @ResponseBody
    @RequestMapping("/industry/list")
    @RequiresPermissions("news:list")
    public R listIndustry(Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;

        ListNewsReqData listNewsReqData = new ListNewsReqData();
        listNewsReqData.setType(NewsType.INDUSTRY.getValue());
        listNewsReqData.setOffset(offset);
        listNewsReqData.setPageSize(limit);

        ListResult result = newsInfoService.listPageNews(listNewsReqData);
        PageUtils pageUtil = new PageUtils(result.getNewsList(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtil);
    }

    /**
     * 公告列表
     */
    @ResponseBody
    @RequestMapping("/notice/list")
    @RequiresPermissions("news:list")
    public R listNotice(Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;

        ListNewsReqData listNewsReqData = new ListNewsReqData();
        listNewsReqData.setType(NewsType.NOTICE.getValue());
        listNewsReqData.setOffset(offset);
        listNewsReqData.setPageSize(limit);

        ListResult result = newsInfoService.listPageNews(listNewsReqData);
        PageUtils pageUtil = new PageUtils(result.getNewsList(), result.getTotalCount(), limit, page);

        return R.ok().put("page", pageUtil);
    }

    /**
     * 新闻详情信息
     *
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping("/details")
    @RequiresPermissions("news:details")
    public R details(@RequestBody JSONObject reqData) {
        News news = new News();
        Long id = reqData.getLong("id");
        if (id != null) {
            DetailsReqData detailsReqData = new DetailsReqData();
            detailsReqData.setNewsId(id);
            detailsReqData.setUid(ShiroUtils.getUserId());
            DetailsResult result = newsInfoService.newsDetails(detailsReqData);
            if (!result.isSuccess()) {
                return R.error(result.getResultMessage());
            }
            news = result.getNews();
            if (news != null && StringUtils.isNotBlank(news.getCoverPic())) {
                Map<String, String> map = new HashMap<>();
                map.put("fid", String.format("%s%s", fileDownloadUrl, news.getCoverPic()));
                news.setCoverPic(JSON.toJSONString(map));
            }
        }

        return R.ok().put("news", news);
    }

    /**
     * 新增/修改新闻信息
     * type:INDUSTRY(1, "行业资讯"), NOTICE(2, "公告消息");
     *
     * @param news
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("news:save")
    public R saveNews(@RequestBody News news) {
        if (news == null) {
            return R.error("请求参数不能为空");
        }
        if (!NewsType.isExist(news.getType())) {
            return R.error("类型参数错误");
        }
        try {
            // 参数检查
            parameterheckout(news, news.getType());
        } catch (RuntimeException e) {
            return R.error(e.getMessage());
        }

        if (StringUtils.isNotBlank(news.getCoverPic()) &&
                (news.getCoverPic().contains("http://") ||
                        news.getCoverPic().contains("https://"))) {
            news.setCoverPic("");
        }

        SaveReqData saveReqData = new SaveReqData();
        saveReqData.setNews(news);
        saveReqData.setUid(ShiroUtils.getUserId());
        SaveResult result = newsInfoService.saveNews(saveReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok();
    }

    /**
     * 上下线状态切换
     *
     * @param reqData
     * @return
     */
    @ResponseBody
    @RequestMapping("/switch/status")
    @RequiresPermissions("news:switch:status")
    public R switchStatus(@RequestBody JSONObject reqData) {
        if (reqData.getLong("id") == null) {
            R.error("请选择一条数据");
        }

        SwitchStatusReqData switchStatusReqData = new SwitchStatusReqData();
        switchStatusReqData.setNewsId(reqData.getLong("id"));
        switchStatusReqData.setUid(ShiroUtils.getUserId());
        SwitchStatusResult result = newsInfoService.switchStatus(switchStatusReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok();
    }

    /**
     * 图片上传(批量)
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public R base64Upload(@RequestParam MultipartFile[] file) {
        List<String> listImagePath = new ArrayList<String>();
        try {
            for (MultipartFile mf : file) {
                if (!mf.isEmpty()) {
                    byte[] content = IOUtils.toByteArray(mf.getInputStream());
                    // 图片压缩
                    if (content != null) {
                        if (content.length > 8 * 1024 * 1024) {// 压缩后的图片或者原图图片大于8M，则提示片太大了，请重新选择图片。
                            throw new RuntimeException("图片大小太大了，请重新选择图片");
                        }
                        FileUploadResult result = fileUploadService.upload("upload_" + System.nanoTime() + ".jpg", content);
                        if (result.getResultCode() == ResultCode.SUCCESS) {
                            listImagePath.add(fileDownloadUrl + result.getFileId());
                        } else {
                            throw new RuntimeException(result.getResultMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("upload file:{}", e);
            return R.error(e.getMessage());
        }
        return R.ok().put("errno", 0).put("data", listImagePath);
    }

    /**
     * 参数检查
     *
     * @param news
     * @throws RuntimeException
     */
    private void parameterheckout(News news, Integer type) throws RuntimeException {
        if (StringUtils.isBlank(news.getTitle())) {
            throw new RuntimeException("标题不能为空");
        }

        if (StringUtils.isBlank(news.getContent())) {
            throw new RuntimeException("内容不能为空");
        }

        if (news.getStatus() == null || (news.getStatus() != 0 && news.getStatus() != 1)) {
            throw new RuntimeException("上下线标识错误");
        }

        // 资讯信息参数检查
        if (NewsType.INDUSTRY.getValue().equals(type)) {
            if (StringUtils.isBlank(news.getDescription())) {
                throw new RuntimeException("内容简介不能为空");
            }
            if (news.getDescription().length() > 200) {
                throw new RuntimeException("内容简介不能超过200个字");
            }

            if (StringUtils.isBlank(news.getCoverPic())) {
                throw new RuntimeException("封面图不能为空");
            }
        }
    }
}
