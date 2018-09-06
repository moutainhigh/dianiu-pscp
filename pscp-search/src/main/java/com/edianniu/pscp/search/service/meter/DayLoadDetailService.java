package com.edianniu.pscp.search.service.meter;

import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.service.ProduceService;
import com.edianniu.pscp.search.support.meter.vo.DayLoadDetailVO;
import com.edianniu.pscp.search.support.query.meter.DayLoadDetailQuery;

/**
 * ReportDayLoadDetailService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:43:09 
 * @version V1.0
 */
public interface DayLoadDetailService extends ProduceService{
    /**
     * 分页搜索
     *
     * @param dayLoadDetailQuery
     * @return
     */
    PageResult<DayLoadDetailVO> queryByPage(DayLoadDetailQuery dayLoadDetailQuery);
}
