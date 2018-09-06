package com.edianniu.pscp.search.service;

import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.support.query.ListQuery;
import com.edianniu.pscp.search.support.needs.vo.NeedsVO;

/**
 * ClassName: NeedsService
 * Author: tandingbo
 * CreateTime: 2017-09-24 16:01
 */
public interface NeedsService extends ProduceService{
    /**
     * 分页搜索
     *
     * @param listQuery
     * @return
     */
    PageResult<NeedsVO> queryNeedsVOByPage(ListQuery listQuery);
}
