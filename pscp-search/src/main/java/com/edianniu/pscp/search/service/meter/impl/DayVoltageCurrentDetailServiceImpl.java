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
import com.edianniu.pscp.search.repository.meter.DayVoltageCurrentDetailRepository;
import com.edianniu.pscp.search.service.meter.DayVoltageCurrentDetailService;
import com.edianniu.pscp.search.support.meter.vo.DayVoltageCurrentDetailVO;
import com.edianniu.pscp.search.support.query.meter.DayVoltageCurrentDetailQuery;
@Service("dayVoltageCurrentDetailService")
public class DayVoltageCurrentDetailServiceImpl implements
DayVoltageCurrentDetailService {
	@Autowired
    private DayVoltageCurrentDetailRepository dayVoltageCurrentDetailRepository;
	@Override
	public PageResult<DayVoltageCurrentDetailVO> queryByPage(
			DayVoltageCurrentDetailQuery dayVoltageCurrentDetailQuery) {
		int nextOffset = 0;
        PageResult<DayVoltageCurrentDetailVO> result = new PageResult<>();

        SearchResponse response = dayVoltageCurrentDetailRepository.queryList(dayVoltageCurrentDetailQuery);
        // 获取总记录数
        int total = (int) response.getHits().getTotalHits();
        if (total > 0) {
            List<DayVoltageCurrentDetailVO> list = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject source = JSONObject.parseObject(searchHit.getSourceAsString());
                DayVoltageCurrentDetailVO entity = JSONObject.toJavaObject(source, DayVoltageCurrentDetailVO.class);
                list.add(entity);
            }

            result.setData(list);
            nextOffset = dayVoltageCurrentDetailQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(dayVoltageCurrentDetailQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);

        return result;
	}
	@Override
	public IndexResponse save(String id, Map<String, Object> param)
			throws Exception {
		
		return dayVoltageCurrentDetailRepository.save(id, param);
	}

	@Override
	public UpdateResponse updateById(String id, Map<String, Object> param)
			throws Exception {
		
		return dayVoltageCurrentDetailRepository.updateById(id, param);
	}

	@Override
	public DeleteResponse deleteById(String id) {
		
		return dayVoltageCurrentDetailRepository.deleteById(id);
	}

	@Override
	public GetResponse getById(String id) {
		
		return dayVoltageCurrentDetailRepository.getById(id);
	}

	

}
