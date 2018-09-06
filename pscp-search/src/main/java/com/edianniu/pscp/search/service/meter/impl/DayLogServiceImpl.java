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
import com.edianniu.pscp.search.repository.meter.DayLogRepository;
import com.edianniu.pscp.search.service.meter.DayLogService;
import com.edianniu.pscp.search.support.meter.AvgOfMetreReqData;
import com.edianniu.pscp.search.support.meter.list.ElectricChargeStatReqData;
import com.edianniu.pscp.search.support.meter.vo.AvgOfMeterStat;
import com.edianniu.pscp.search.support.meter.vo.DayLogVO;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;
import com.edianniu.pscp.search.support.query.meter.DayLogQuery;

@Service("meterDayLogService")
public class DayLogServiceImpl implements DayLogService {
	@Autowired
	private DayLogRepository dayLogRepository;

	@Override
	public PageResult<DayLogVO> queryByPage(DayLogQuery listQuery) {
		int nextOffset = 0;
		PageResult<DayLogVO> result = new PageResult<>();

		SearchResponse response = dayLogRepository.queryList(listQuery);
		// 获取总记录数
		int total = (int) response.getHits().getTotalHits();
		if (total > 0) {
			List<DayLogVO> list = new ArrayList<>();
			for (SearchHit searchHit : response.getHits().getHits()) {
				JSONObject source = JSONObject.parseObject(searchHit
						.getSourceAsString());
				DayLogVO entity = JSONObject.toJavaObject(source,
						DayLogVO.class);
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

		return dayLogRepository.save(id, param);
	}

	@Override
	public UpdateResponse updateById(String id, Map<String, Object> param)
			throws Exception {

		return dayLogRepository.updateById(id, param);
	}

	@Override
	public DeleteResponse deleteById(String id) {

		return dayLogRepository.deleteById(id);
	}

	@Override
	public GetResponse getById(String id) {

		return dayLogRepository.getById(id);
	}

	@Override
	public List<ElectricChargeStat> getDayStats(ElectricChargeStatReqData electricChargeStatReqData) {
		return dayLogRepository.statList(electricChargeStatReqData.getFromDate(),
				electricChargeStatReqData.getToDate(),electricChargeStatReqData.getType(), electricChargeStatReqData.getMeterIds(),
				electricChargeStatReqData.getCompanyId());
	}

	@Override
	public List<AvgOfMeterStat> avgOfMeter(AvgOfMetreReqData req) {
		return dayLogRepository.avgOfMeter(req.getFromDate(), req.getMeterIds(), req.getCompanyId());
	}

}
