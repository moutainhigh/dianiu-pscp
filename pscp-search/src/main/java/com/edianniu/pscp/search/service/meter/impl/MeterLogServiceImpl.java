/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月13日 下午5:20:23 
 * @version V1.0
 */
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
import com.edianniu.pscp.search.repository.meter.MeterLogRepository;
import com.edianniu.pscp.search.service.meter.MeterLogService;
import com.edianniu.pscp.search.support.meter.vo.MeterLogVO;
import com.edianniu.pscp.search.support.query.meter.MeterLogQuery;

/**
 * MeterLogServiceImpl
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月13日 下午5:20:23 
 * @version V1.0
 */
@Service("meterLogService")
public class MeterLogServiceImpl implements MeterLogService{
	@Autowired
    private MeterLogRepository meterLogRepository;
	@Override
	public PageResult<MeterLogVO> queryByPage(MeterLogQuery meterLogQuery) {
		int nextOffset = 0;
        PageResult<MeterLogVO> result = new PageResult<>();

        SearchResponse response = meterLogRepository.queryList(meterLogQuery);
        // 获取总记录数
        int total = (int) response.getHits().getTotalHits();
        if (total > 0) {
            List<MeterLogVO> list = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject source = JSONObject.parseObject(searchHit.getSourceAsString());
                MeterLogVO entity = JSONObject.toJavaObject(source, MeterLogVO.class);
                list.add(entity);
            }

            result.setData(list);
            nextOffset = meterLogQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(meterLogQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);

        return result;
	}
	@Override
	public Double getAvgPower(Long companyId, String meterId, Long startTime,
			Long endTime) {
		
		return meterLogRepository.getAvgPower(companyId, meterId, startTime, endTime);
	}
	@Override
	public IndexResponse save(String id, Map<String, Object> param)
			throws Exception {
		
		return meterLogRepository.save(id, param);
	}

	@Override
	public UpdateResponse updateById(String id, Map<String, Object> param)
			throws Exception {
		
		return meterLogRepository.updateById(id, param);
	}

	@Override
	public DeleteResponse deleteById(String id) {
		
		return meterLogRepository.deleteById(id);
	}

	@Override
	public GetResponse getById(String id) {
		return meterLogRepository.getById(id);
	}
	@Override
	public PageResult<MeterLogVO> searchDistinctByMeterId(MeterLogQuery meterLogQuery) {
		int nextOffset = 0;
        PageResult<MeterLogVO> result = new PageResult<>();

        SearchResponse response= meterLogRepository.searchDistinctByMeterId(meterLogQuery);
		
        // 获取总记录数
        int total = (int) response.getHits().getTotalHits();
        int totalShard=response.getTotalShards();
        if (total > 0&&totalShard>0) {
            List<MeterLogVO> list = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject source = JSONObject.parseObject(searchHit.getSourceAsString());
                MeterLogVO entity = JSONObject.toJavaObject(source, MeterLogVO.class);
                list.add(entity);
            }

            result.setData(list);
           
            result.setNextOffset(nextOffset);
        }
        
        if (nextOffset > 0 && nextOffset < totalShard) {
            result.setHasNext(true);
        }
        result.setNextOffset(nextOffset);
        result.setTotal(totalShard);

        return result;
	}
	@Override
	public Double getVoltageQualifiedRate(Long companyId, String meterId,
			Long startTime, Long endTime) {
		return meterLogRepository.getVoltageQualifiedRate(companyId, meterId, startTime, endTime);
	}
	

	

}
