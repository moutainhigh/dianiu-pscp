package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.bean.news.ListTopNumReqData;
import com.edianniu.pscp.sps.bean.news.VO.NewsDetailsInfoVO;
import com.edianniu.pscp.sps.bean.news.VO.NewsListInfoVO;
import com.edianniu.pscp.sps.domain.News;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * ClassName: NewsDao
 * Author: tandingbo
 * CreateTime: 2017-08-10 16:22
 */
public interface NewsDao extends BaseDao<News> {
    List<NewsListInfoVO> selectNewsListInfoVOTopNum(ListTopNumReqData listTopNumReqData);

    NewsDetailsInfoVO getNewsDetailsVOById(Long id);

    News getNewsDetailsById(Long id);

    void updateStatus(@Param("id") Long id, @Param("status") Integer status,
                      @Param("modifiedUser") String modifiedUser,
                      @Param("modifiedTime") Date modifiedTime);

}
