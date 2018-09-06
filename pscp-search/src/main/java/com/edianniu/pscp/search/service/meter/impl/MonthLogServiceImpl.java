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
import com.edianniu.pscp.search.repository.meter.MonthLogRepository;
import com.edianniu.pscp.search.service.meter.MonthLogService;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.vo.MonthLogVO;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;
import com.edianniu.pscp.search.support.query.meter.MonthLogQuery;
@Service("meterMonthLogService")
public class MonthLogServiceImpl implements
		MonthLogService {
	@Autowired
    private MonthLogRepository monthLogRepository;
	@Override
	public PageResult<MonthLogVO> queryByPage(
			MonthLogQuery monthElectricChargeQuery) {
		int nextOffset = 0;
        PageResult<MonthLogVO> result = new PageResult<>();

        SearchResponse response = monthLogRepository.queryList(monthElectricChargeQuery);
        // 获取总记录数
        int total = (int) response.getHits().getTotalHits();
        if (total > 0) {
            List<MonthLogVO> list = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject source = JSONObject.parseObject(searchHit.getSourceAsString());
                MonthLogVO entity = JSONObject.toJavaObject(source, MonthLogVO.class);
                list.add(entity);
            }

            result.setData(list);
            nextOffset = monthElectricChargeQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(monthElectricChargeQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);

        return result;
	}
	@Override
	public Map<String, Double> getDividedElectric(Long companyId,
			String[] meterIds, String date) {
		return this.monthLogRepository.getDividedElectric(companyId,
				meterIds, date);
	}

	@Override
	public IndexResponse save(String id, Map<String, Object> param)
			throws Exception {
		return monthLogRepository.save(id, param);
	}

	@Override
	public UpdateResponse updateById(String id, Map<String, Object> param)
			throws Exception {
		
		return monthLogRepository.updateById(id, param);
	}

	@Override
	public DeleteResponse deleteById(String id) {
		
		return monthLogRepository.deleteById(id);
	}

	@Override
	public GetResponse getById(String id) {
		
		return monthLogRepository.getById(id);
	}
	@Override
	public List<ElectricChargeStat> getYearMonthStats(ElectricChargeStatReqData monthStatReqData) {
		
		return monthLogRepository.statList(monthStatReqData.getFromDate(),
				monthStatReqData.getToDate(),monthStatReqData.getType(), monthStatReqData.getMeterIds(), monthStatReqData.getCompanyId());
	}
	

}
