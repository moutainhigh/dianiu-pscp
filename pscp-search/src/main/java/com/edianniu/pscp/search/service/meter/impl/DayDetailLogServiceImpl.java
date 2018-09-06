package com.edianniu.pscp.search.service.meter.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.repository.meter.DayDetailLogRepository;
import com.edianniu.pscp.search.service.meter.DayDetailLogService;
import com.edianniu.pscp.search.support.meter.vo.DayDetailLogVO;
import com.edianniu.pscp.search.support.query.meter.DayDetailLogQuery;

@Service("meterDayDetailLogService")
public class DayDetailLogServiceImpl implements
		DayDetailLogService {
	@Autowired
    private DayDetailLogRepository dayDetailLogRepository;
	@Override
	public PageResult<DayDetailLogVO> queryByPage(
			DayDetailLogQuery listQuery) {
		int nextOffset = 0;
        PageResult<DayDetailLogVO> result = new PageResult<>();

        SearchResponse response = dayDetailLogRepository.queryList(listQuery);
        // 获取总记录数
        int total = (int) response.getHits().getTotalHits();
        if (total > 0) {
            List<DayDetailLogVO> list = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject source = JSONObject.parseObject(searchHit.getSourceAsString());
                DayDetailLogVO entity = JSONObject.toJavaObject(source, DayDetailLogVO.class);
                list.add(entity);
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
	public IndexResponse save(String id, Map<String, Object> param)
			throws Exception {
		
		return dayDetailLogRepository.save(id, param);
	}

	@Override
	public UpdateResponse updateById(String id, Map<String, Object> param)
			throws Exception {
		
		return dayDetailLogRepository.updateById(id, param);
	}

	@Override
	public DeleteResponse deleteById(String id) {
		
		return dayDetailLogRepository.deleteById(id);
	}

	@Override
	public GetResponse getById(String id) {
		
		return dayDetailLogRepository.getById(id);
	}

	

}
