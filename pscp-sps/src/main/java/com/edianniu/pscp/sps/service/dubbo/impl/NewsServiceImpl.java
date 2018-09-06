package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.news.*;
import com.edianniu.pscp.sps.bean.news.VO.NewsDetailsInfoVO;
import com.edianniu.pscp.sps.bean.news.VO.NewsListInfoVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.News;
import com.edianniu.pscp.sps.service.NewsService;
import com.edianniu.pscp.sps.service.dubbo.NewsInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * ClassName: NewsServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:24
 */
@Service
@Repository("newsInfoService")
public class NewsServiceImpl implements NewsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    @Qualifier("newsService")
    private NewsService newsService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Override
    public ListTopNumResult selectNewsListInfoVOTopNum(ListTopNumReqData listTopNumReqData) {
        ListTopNumResult result = new ListTopNumResult();
        try {
            List<NewsListInfoVO> newsList = newsService.selectNewsListInfoVOTopNum(listTopNumReqData);
            result.setNewsList(newsList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("selectNewsTopNum :{}", e);
        }
        return result;
    }

    /**
     * 分页获取新闻信息
     *
     * @param reqData
     * @return
     */
    @Override
    public ListPageVOResult selectPageNewsListInfoVO(PageNewsListInfoVOReqData reqData) {
        ListPageVOResult result = new ListPageVOResult();
        try {
            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(reqData, listQuery);

            PageResult<NewsListInfoVO> pageResult = newsService.selectPageNewsListInfoVO(listQuery);
            result.setNewsList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("selectPageNewsListInfoVO :{}", e);
        }
        return result;
    }

    /**
     * 根据主键ID获取新闻信息
     *
     * @param id
     * @return
     */
    @Override
    public DetailsVOResult getNewsDetailsVOById(Long id) {
        DetailsVOResult result = new DetailsVOResult();
        try {
            if (id == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("内容不存在");
                return result;
            }

            NewsDetailsInfoVO newsDetails = newsService.getNewsDetailsVOById(id);
            result.setNewsDetails(newsDetails);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("getNewsDetailsVOById :{}", e);
        }
        return result;
    }

    /**************************************CMS平台操作**************************************/

    /**
     * 保存新闻信息
     *
     * @param saveReqData
     * @return
     */
    @Override
    public SaveResult saveNews(SaveReqData saveReqData) {
        SaveResult result = new SaveResult();
        try {
            Date now = new Date();
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(saveReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            News news = saveReqData.getNews();
            if (news.getId() == null) {
                news.setCreateTime(now);
                news.setCreateUser(userInfo.getLoginName());
            } else {
                news.setModifiedTime(now);
                news.setModifiedUser(userInfo.getLoginName());
            }
            result = newsService.saveNews(saveReqData.getNews());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("saveNews :{}", e);
        }
        return result;
    }

    /**
     * 新闻详情
     *
     * @param detailsReqData
     * @return
     */
    @Override
    public DetailsResult newsDetails(DetailsReqData detailsReqData) {
        DetailsResult result = new DetailsResult();
        try {
            if (detailsReqData.getNewsId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("新闻标识不能为空");
                return result;
            }

            News news = newsService.getNewsDetailsById(detailsReqData.getNewsId());
            result.setNews(news);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("newsDetails :{}", e);
        }
        return result;
    }

    /**
     * 分页获取新闻信息
     *
     * @param listNewsReqData
     * @return
     */
    @Override
    public ListResult listPageNews(ListNewsReqData listNewsReqData) {
        ListResult result = new ListResult();
        try {
            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(listNewsReqData, listQuery);

            PageResult<News> pageResult = newsService.selectPageNewsList(listQuery);
            result.setNewsList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("listPageNews :{}", e);
        }
        return result;
    }

    /**
     * 上下线状态切换
     *
     * @param reqData
     * @return
     */
    @Override
    public SwitchStatusResult switchStatus(SwitchStatusReqData reqData) {
        SwitchStatusResult result = new SwitchStatusResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (userInfo == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("登录信息异常");
                return result;
            }

            if (reqData.getNewsId() == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("请选择一条数据");
                return result;
            }
            News news = newsService.getNewsDetailsById(reqData.getNewsId());
            if (news == null) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("未匹配到信息");
                return result;
            }
            Integer status = news.getStatus().equals(0) ? 1 : 0;
            newsService.updateStatus(reqData.getNewsId(), status, userInfo.getLoginName(), new Date());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("listPageNews :{}", e);
        }
        return result;
    }
}
