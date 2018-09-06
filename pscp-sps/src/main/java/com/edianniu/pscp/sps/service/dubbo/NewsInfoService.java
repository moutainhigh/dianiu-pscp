package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.news.*;

/**
 * ClassName: NewsInfoService
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:24
 */
public interface NewsInfoService {
    /**
     * 获取最新指定数量新闻
     *
     * @param listTopNumReqData
     * @return
     */
    ListTopNumResult selectNewsListInfoVOTopNum(ListTopNumReqData listTopNumReqData);

    /**
     * 分页获取新闻信息
     *
     * @param reqData
     * @return
     */
    ListPageVOResult selectPageNewsListInfoVO(PageNewsListInfoVOReqData reqData);

    /**
     * 根据主键ID获取新闻信息
     *
     * @param id
     * @return
     */
    DetailsVOResult getNewsDetailsVOById(Long id);


    /**************************************CMS平台操作**************************************/

    /**
     * 保存新闻信息
     *
     * @param saveReqData
     * @return
     */
    SaveResult saveNews(SaveReqData saveReqData);

    /**
     * 新闻详情
     *
     * @param detailsReqData
     * @return
     */
    DetailsResult newsDetails(DetailsReqData detailsReqData);

    /**
     * 分页获取新闻信息
     *
     * @param listNewsReqData
     * @return
     */
    ListResult listPageNews(ListNewsReqData listNewsReqData);

    /**
     * 上下线状态切换
     *
     * @param switchStatusReqData
     * @return
     */
    SwitchStatusResult switchStatus(SwitchStatusReqData switchStatusReqData);
}
