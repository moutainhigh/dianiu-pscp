package com.edianniu.pscp.search.service.meter;

import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.service.ProduceService;
import com.edianniu.pscp.search.support.meter.vo.DayDetailLogVO;
import com.edianniu.pscp.search.support.query.meter.DayDetailLogQuery;

/**
 * DayDetailLogService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:43:09 
 * @version V1.0
 */
public interface DayDetailLogService extends ProduceService{
    /**
     * 分页搜索
     *
     * @param listQuery
     * @return
     */
    PageResult<DayDetailLogVO> queryByPage(DayDetailLogQuery listQuery);
}
