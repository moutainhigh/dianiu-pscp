package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.sps.bean.news.ListQuery;
import com.edianniu.pscp.sps.bean.news.ListTopNumReqData;
import com.edianniu.pscp.sps.bean.news.NewsType;
import com.edianniu.pscp.sps.bean.news.SaveResult;
import com.edianniu.pscp.sps.bean.news.VO.NewsDetailsInfoVO;
import com.edianniu.pscp.sps.bean.news.VO.NewsListInfoVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.dao.NewsDao;
import com.edianniu.pscp.sps.domain.News;
import com.edianniu.pscp.sps.service.NewsService;
import com.edianniu.pscp.sps.util.DateUtils;
import com.edianniu.pscp.sps.util.ImageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: DefaultNewsService
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:22
 */
@Service
@Repository("newsService")
public class DefaultNewsService implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    @Qualifier("fileUploadService")
    private FileUploadService fileUploadService;

    @Override
    public List<NewsListInfoVO> selectNewsListInfoVOTopNum(ListTopNumReqData listTopNumReqData) {
        List<NewsListInfoVO> newsList = newsDao.selectNewsListInfoVOTopNum(listTopNumReqData);
        if (CollectionUtils.isNotEmpty(newsList)) {
            for (NewsListInfoVO newsListInfoVO : newsList) {
                if (StringUtils.isNotBlank(newsListInfoVO.getCoverPic())) {
                    String coverPic = String.format("%s%s", picUrl, newsListInfoVO.getCoverPic());
                    newsListInfoVO.setCoverPic(coverPic);
                }
            }
        }
        return newsList;
    }

    @Override
    public PageResult<NewsListInfoVO> selectPageNewsListInfoVO(ListQuery listQuery) {
        PageResult<NewsListInfoVO> result = new PageResult<NewsListInfoVO>();
        int total = newsDao.queryCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<News> list = newsDao.queryList(listQuery);
            List<NewsListInfoVO> newsListInfoVOList = new ArrayList<NewsListInfoVO>();
            for (News news : list) {
                NewsListInfoVO newsListInfoVO = new NewsListInfoVO();
                BeanUtils.copyProperties(news, newsListInfoVO);
                newsListInfoVO.setPublishTime(DateUtils.format(news.getCreateTime(), DateUtils.DATE_PATTERN_OTHER));
                if (StringUtils.isNotBlank(news.getCoverPic())) {
                    String coverPic = String.format("%s%s", picUrl, news.getCoverPic());
                    newsListInfoVO.setCoverPic(coverPic);
                }
                newsListInfoVOList.add(newsListInfoVO);
            }
            result.setData(newsListInfoVOList);
            nextOffset = listQuery.getOffset() + newsListInfoVOList.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }
        result.setOffset(listQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    @Override
    public NewsDetailsInfoVO getNewsDetailsVOById(Long id) {
        NewsDetailsInfoVO newsDetails = newsDao.getNewsDetailsVOById(id);
        if (newsDetails != null) {
            if (StringUtils.isNotBlank(newsDetails.getCoverPic())) {
                String coverPic = String.format("%s%s", picUrl, newsDetails.getCoverPic());
                newsDetails.setCoverPic(coverPic);
            }
        }
        return newsDetails;
    }

    @Override
    @Transactional
    public SaveResult saveNews(News news) {
        SaveResult result = new SaveResult();
        try {
            checkParameter(news);
        } catch (RuntimeException e) {
            result.setResultCode(ResultCode.ERROR_401);
            result.setResultMessage(e.getMessage());
            return result;
        }

        if (StringUtils.isNotBlank(news.getCoverPic())) {
            FileUploadResult fileUploadResult = fileUploadService.upload(
                    "upload_" + System.nanoTime() + ".jpg",
                    ImageUtils.getBase64ImageBytes(news.getCoverPic()));
            if (result.isSuccess()) {
                news.setCoverPic(fileUploadResult.getFileId());
            } else {
                result.setResultCode(fileUploadResult.getResultCode());
                result.setResultMessage(fileUploadResult.getResultMessage());
                return result;
            }
        }

        if (news.getId() == null) {
            newsDao.save(news);
        } else {
            News newsDetails = newsDao.getNewsDetailsById(news.getId());
            if (newsDetails == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("新闻不存在");
                return result;
            }

            newsDetails.setType(news.getType());
            newsDetails.setTitle(news.getTitle());
            newsDetails.setStatus(news.getStatus());
            newsDetails.setContent(news.getContent());
            newsDetails.setDescription(news.getDescription());
            if (StringUtils.isNotBlank(news.getCoverPic())) {
                newsDetails.setCoverPic(news.getCoverPic());
            }
            newsDao.update(newsDetails);
        }

        return result;
    }

    /**
     * 参数检查
     *
     * @param news
     * @throws RuntimeException
     */
    private void checkParameter(News news) throws RuntimeException {
        if (StringUtils.isBlank(news.getTitle())) {
            throw new RuntimeException("标题不能为空");
        }
        if (news.getTitle().length() > 100) {
            throw new RuntimeException("标题不能超过100个字");
        }
        if (StringUtils.isBlank(news.getContent())) {
            throw new RuntimeException("内容不能为空");
        }
        if (StringUtils.isBlank(news.getDescription())) {
            throw new RuntimeException("简介不能为空");
        }
        if (news.getTitle().length() > 200) {
            throw new RuntimeException("简介不能超过200个字");
        }
        if (news.getType() == null) {
            throw new RuntimeException("类型不能为空");
        }
        if (!NewsType.isExist(news.getType())) {
            throw new RuntimeException("类型错误");
        }
    }

    @Override
    public News getNewsDetailsById(Long id) {
        return newsDao.getNewsDetailsById(id);
    }

    @Override
    public PageResult<News> selectPageNewsList(ListQuery listQuery) {
        PageResult<News> result = new PageResult<News>();
        int total = newsDao.queryCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<News> list = newsDao.queryList(listQuery);
            for (News news : list) {
                if (StringUtils.isNotBlank(news.getCoverPic())) {
                    String coverPic = String.format("%s%s", picUrl, news.getCoverPic());
                    news.setCoverPic(coverPic);
                }
            }
            result.setData(list);
            nextOffset = listQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }
        result.setOffset(listQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    @Override
    public void updateStatus(Long id, Integer status, String modifiedUser, Date modifiedTime) {
        newsDao.updateStatus(id, status, modifiedUser, modifiedTime);
    }

    private String picUrl;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
