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
import com.edianniu.pscp.search.repository.meter.DemandDetailRepository;
import com.edianniu.pscp.search.service.meter.DemandDetailService;
import com.edianniu.pscp.search.support.meter.vo.DemandDetailVO;
import com.edianniu.pscp.search.support.query.meter.DemandDetailQuery;
@Service("demandDetailService")
public class DemandDetailServiceImpl implements
DemandDetailService {
	@Autowired
    private DemandDetailRepository demandDetailRepository;
	@Override
	public PageResult<DemandDetailVO> queryByPage(
			DemandDetailQuery demandDetailQuery) {
		int nextOffset = 0;
        PageResult<DemandDetailVO> result = new PageResult<>();

        SearchResponse response = demandDetailRepository.queryList(demandDetailQuery);
        // 获取总记录数
        int total = (int) response.getHits().getTotalHits();
        if (total > 0) {
            List<DemandDetailVO> list = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject source = JSONObject.parseObject(searchHit.getSourceAsString());
                DemandDetailVO entity = JSONObject.toJavaObject(source, DemandDetailVO.class);
                list.add(entity);
            }

            result.setData(list);
            nextOffset = demandDetailQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(demandDetailQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);

        return result;
	}
	@Override
	public IndexResponse save(String id, Map<String, Object> param)
			throws Exception {
		
		return demandDetailRepository.save(id, param);
	}

	@Override
	public UpdateResponse updateById(String id, Map<String, Object> param)
			throws Exception {
		
		return demandDetailRepository.updateById(id, param);
	}

	@Override
	public DeleteResponse deleteById(String id) {
		
		return demandDetailRepository.deleteById(id);
	}

	@Override
	public GetResponse getById(String id) {
		
		return demandDetailRepository.getById(id);
	}
	@Override
	public Double getMaxPower(Long companyId, String meterId,Long startTime,Long endTime) {
		return demandDetailRepository.getMaxPower(companyId, meterId, startTime,endTime);
	}

	

}
