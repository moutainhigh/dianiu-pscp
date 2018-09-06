package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.bean.news.ListQuery;
import com.edianniu.pscp.sps.bean.news.ListTopNumReqData;
import com.edianniu.pscp.sps.bean.news.SaveResult;
import com.edianniu.pscp.sps.bean.news.VO.NewsDetailsInfoVO;
import com.edianniu.pscp.sps.bean.news.VO.NewsListInfoVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.domain.News;

import java.util.Date;
import java.util.List;

/**
 * ClassName: NewsService
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:21
 */
public interface NewsService {
    List<NewsListInfoVO> selectNewsListInfoVOTopNum(ListTopNumReqData listTopNumReqData);

    PageResult<NewsListInfoVO> selectPageNewsListInfoVO(ListQuery listQuery);

    NewsDetailsInfoVO getNewsDetailsVOById(Long id);

    SaveResult saveNews(News news);

    News getNewsDetailsById(Long id);

    PageResult<News> selectPageNewsList(ListQuery listQuery);

    void updateStatus(Long id, Integer status, String modifiedUser, Date modifiedTime);
}
