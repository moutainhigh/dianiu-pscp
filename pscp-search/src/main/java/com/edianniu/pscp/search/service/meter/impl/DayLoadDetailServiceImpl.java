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
import com.edianniu.pscp.search.repository.meter.DayLoadDetailRepository;
import com.edianniu.pscp.search.service.meter.DayLoadDetailService;
import com.edianniu.pscp.search.support.meter.vo.DayLoadDetailVO;
import com.edianniu.pscp.search.support.query.meter.DayLoadDetailQuery;
@Service("dayLoadDetailService")
public class DayLoadDetailServiceImpl implements
		DayLoadDetailService {
	@Autowired
    private DayLoadDetailRepository dayLoadDetailRepository;
	@Override
	public PageResult<DayLoadDetailVO> queryByPage(
			DayLoadDetailQuery dayLoadDetailQuery) {
		int nextOffset = 0;
        PageResult<DayLoadDetailVO> result = new PageResult<>();

        SearchResponse response = dayLoadDetailRepository.queryList(dayLoadDetailQuery);
        // 获取总记录数
        int total = (int) response.getHits().getTotalHits();
        if (total > 0) {
            List<DayLoadDetailVO> list = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject source = JSONObject.parseObject(searchHit.getSourceAsString());
                DayLoadDetailVO entity = JSONObject.toJavaObject(source, DayLoadDetailVO.class);
                list.add(entity);
            }

            result.setData(list);
            nextOffset = dayLoadDetailQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(dayLoadDetailQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);

        return result;
	}

	@Override
	public IndexResponse save(String id, Map<String, Object> param)
			throws Exception {
		return dayLoadDetailRepository.save(id, param);
	}

	@Override
	public UpdateResponse updateById(String id, Map<String, Object> param)
			throws Exception {
		
		return dayLoadDetailRepository.updateById(id, param);
	}

	@Override
	public DeleteResponse deleteById(String id) {
		
		return dayLoadDetailRepository.deleteById(id);
	}

	@Override
	public GetResponse getById(String id) {
		
		return dayLoadDetailRepository.getById(id);
	}

}
