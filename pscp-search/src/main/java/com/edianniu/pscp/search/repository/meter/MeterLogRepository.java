package com.edianniu.pscp.search.repository.meter;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryParseContext;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.elasticsearch.search.aggregations.bucket.range.date.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edianniu.pscp.search.common.Constants;
import com.edianniu.pscp.search.support.SortField;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;
import com.edianniu.pscp.search.support.meter.vo.StatLog;
import com.edianniu.pscp.search.support.query.meter.MeterLogQuery;

/**
 * MeterLogRepository
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:36:12 
 * @version V1.0
 */
@Repository
public class MeterLogRepository {

    @Autowired
    private TransportClient client;

    /**
     * 分页查询
     *
     * @param listQuery
     * @return
     */
    public SearchResponse queryList(MeterLogQuery listQuery) {
        // 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_LOG_TYPE);
        //设置查询类型
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        //设置分页信息
        searchRequestBuilder.setFrom(listQuery.getOffset()).setSize(listQuery.getPageSize());
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
        for(SortField sortField:listQuery.getSorts()){
        	searchRequestBuilder.addSort(sortField.getName(),(sortField.isDesc())? SortOrder.DESC:SortOrder.ASC);
        }
        //更新时间倒序排序
        searchRequestBuilder.addSort("createTime", SortOrder.DESC);
        searchRequestBuilder.addSort("updateTime", SortOrder.DESC);
        // 拼接查询字段
        BoolQueryBuilder boolQueryBuilder = getQueryBuilder(listQuery);
        // 搜索结果
        return searchRequestBuilder.setQuery(boolQueryBuilder).execute().actionGet();
    }

    /**
     * 拼接查询字段
     *
     * @param dayAggregateQuery
     * @return
     */
    private BoolQueryBuilder getQueryBuilder(MeterLogQuery listQuery) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolean flag = false;
        if (null != listQuery) {
        	
            //客户ID
            if (listQuery.getCompanyId()!=null) {
                TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("companyId", listQuery.getCompanyId());
                boolQueryBuilder.must(termQueryBuilder);
            }
            //仪表ID 
            if (StringUtils.isNotBlank(listQuery.getMeterId())) {
                 TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("meterId", listQuery.getMeterId());
                 boolQueryBuilder.must(termQueryBuilder);
            }
            //日期查询 根据区间来区分
            if(!(listQuery.getStartTime()==null&&listQuery.getEndTime()==null)){
            	RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("time")
            			.from(listQuery.getStartTime()==null?0L:listQuery.getStartTime())
            			.to(listQuery.getEndTime()==null?0L:listQuery.getEndTime());
                boolQueryBuilder.must(rangeQueryBuilder);
            }
            //根据敏感词查询，IK分词器分词模糊查询
            String sensitivewords = "";
            if (!StringUtils.isBlank(sensitivewords)) {
                QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(sensitivewords);
                queryBuilder.analyzer("ES_ANALYSIS_IK").field("content");
                boolQueryBuilder.should(queryBuilder).should(QueryBuilders.matchAllQuery());
                flag = true;
            }
        }

        if (!flag) {
            boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }
        return boolQueryBuilder;
    }
    public SearchResponse searchDistinctByMeterId(MeterLogQuery meterLogQuery) {
    	 // 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_LOG_TYPE);
        //设置查询类型
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
      //设置分页信息
        searchRequestBuilder.setFrom(meterLogQuery.getOffset()).setSize(meterLogQuery.getPageSize());
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
        for(SortField sortField:meterLogQuery.getSorts()){
        	searchRequestBuilder.addSort(sortField.getName(),(sortField.isDesc())? SortOrder.DESC:SortOrder.ASC);
        }
        //更新时间倒序排序
        searchRequestBuilder.addSort("createTime", SortOrder.DESC);
        searchRequestBuilder.addSort("updateTime", SortOrder.DESC);
    
    	BoolQueryBuilder boolQueryBuilder = getQueryBuilder(meterLogQuery);
        
		try {
			XContentType xContentType = XContentType.JSON;
	        XContentParser parser= xContentType.xContent().createParser(NamedXContentRegistry.EMPTY, "{\"field\" : \"meterId\"}");
	        CollapseBuilder collapse= CollapseBuilder.fromXContent(new QueryParseContext( parser));
			searchRequestBuilder.setCollapse(collapse);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	SearchResponse response = searchRequestBuilder.setQuery(boolQueryBuilder).execute().actionGet();
    	return response;
    }
    public Double getAvgPower(Long companyId, String meterId, Long startTime,
			Long endTime) {
    	// 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_LOG_TYPE);
        //设置查询类型
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	//客户ID
        if (companyId!=null) {
            TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("companyId", companyId);
            boolQueryBuilder.must(termQueryBuilder);
        }
        //仪表ID 
        if (StringUtils.isNotBlank(meterId)) {
             TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("meterId", meterId);
             boolQueryBuilder.must(termQueryBuilder);
        }
        //日期查询 根据区间来区分
        if(!(startTime==null&&endTime==null)){
        	RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("time")
        			.from(startTime==null?0L:startTime)
        			.to(endTime==null?0L:endTime);
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        AvgAggregationBuilder  teamAgg= AggregationBuilders.avg("avgPower").field("abcActivePower");  
    	searchRequestBuilder.addAggregation(teamAgg);
    	SearchResponse response = searchRequestBuilder.setQuery(boolQueryBuilder).execute().actionGet();
    	Aggregations  agg=response.getAggregations();
    	Avg  avg= agg.get("avgPower");
    	//TODO 
    	return avg.getValue();
    }
    public Double getVoltageQualifiedRate(Long companyId, String meterId,
			Long startTime, Long endTime){
    	 // 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_LOG_TYPE);
        //设置查询类型
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	//客户ID
        if (companyId!=null) {
            TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("companyId", companyId);
            boolQueryBuilder.must(termQueryBuilder);
        }
        //仪表ID 
        if (StringUtils.isNotBlank(meterId)) {
             TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("meterId", meterId);
             boolQueryBuilder.must(termQueryBuilder);
        }
        //日期查询 根据区间来区分
        if(!(startTime==null&&endTime==null)){
        	RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("time")
        			.from(startTime==null?0L:startTime)
        			.to(endTime==null?0L:endTime);
            boolQueryBuilder.must(rangeQueryBuilder);
        }
    	TermsAggregationBuilder tb=  AggregationBuilders.terms("group_uStatus").field("uStatus");
    	searchRequestBuilder.addAggregation(tb);
    	SearchResponse response = searchRequestBuilder.setQuery(boolQueryBuilder).execute().actionGet();
    	Terms tms = response.getAggregations().get("group_uStatus");
    	Long total=0L;
    	Long success=0L;
    	for(Terms.Bucket tbb:tms.getBuckets()){
    		if(tbb.getKeyAsString().equals("0")){
    			success=success+tbb.getDocCount();
    		}
    		total=total+tbb.getDocCount();
    		System.out.println(tbb.getKey() + " 状态：" + tbb.getDocCount() +"条记录。");
    	}
    	if(total==0){
    		return 0.00D;
    	}
    	return success.doubleValue()/total.doubleValue();
    }
    public List<StatLog> getStatLogs(String fromDate,String toDate,String type,Set<String> meterIds,Long companyId){
    	// 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_LOG_TYPE);
        //设置查询类型
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
    	//客户ID
        if (companyId!=null) {
            TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("companyId", companyId);
            boolQueryBuilder.must(termQueryBuilder);
        }
        //仪表ID 
        if (meterIds!=null&&!meterIds.isEmpty()) {
             TermsQueryBuilder termsQueryBuilder=QueryBuilders.termsQuery("meterId", meterIds);
             boolQueryBuilder.must(termsQueryBuilder);
        }
        //日期范围查询
        if(StringUtils.isNotBlank(fromDate)||StringUtils.isNotBlank(toDate)){
        	RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("reportTime");
        	rangeQueryBuilder.format("yyyyMMdd");
        	if(StringUtils.isNotBlank(fromDate))rangeQueryBuilder.gte(fromDate);
        	if(StringUtils.isNotBlank(toDate))rangeQueryBuilder.lte(toDate);		
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        //查询日期区间内，每个仪表每天的总的电量，总的电费的情况
        //按照日期+仪表 group by 然后计算总量
        TermsAggregationBuilder meterIdAgg= AggregationBuilders.terms("meterId").field("meterId");//仪表Id聚合 group by
        TermsAggregationBuilder meterSubTermCodeAgg= AggregationBuilders.terms("subTermCode").field("subTermCode");//仪表Id聚合 group by
        DateHistogramAggregationBuilder dateAgg = AggregationBuilders.dateHistogram("reportTime").field("reportTime");
        if(type.equals("DAY")){
        	dateAgg.format("yyyyMMdd");
            dateAgg.dateHistogramInterval(DateHistogramInterval.DAY);
        }
        else if(type.equals("MONTH")){
        	dateAgg.format("yyyyMM");
            dateAgg.dateHistogramInterval(DateHistogramInterval.MONTH);
        }
        else if(type.equals("YEAR")){
        	dateAgg.format("yyyy");
            dateAgg.dateHistogramInterval(DateHistogramInterval.YEAR);
        }
        else{
        	throw new RuntimeException("not support this type:"+type);
        }
        /**
         * 获取最大和最小电量，计算周期内的电量
         */
        MaxAggregationBuilder maxTotalAgg=AggregationBuilders.max("max_total").field("positiveTotalActivePowerCharge");
        MinAggregationBuilder minTotalAgg=AggregationBuilders.min("min_total").field("positiveTotalActivePowerCharge");
        MaxAggregationBuilder maxApexAgg=AggregationBuilders.max("max_apex").field("positiveTotalActivePowerCharge");
        MinAggregationBuilder minApexAgg=AggregationBuilders.min("min_apex").field("positiveTotalActivePowerCharge");
        MaxAggregationBuilder maxPeakAgg=AggregationBuilders.max("max_peak").field("positiveTotalActivePowerCharge");
        MinAggregationBuilder minPeakAgg=AggregationBuilders.min("min_peak").field("positiveTotalActivePowerCharge");
        MaxAggregationBuilder maxFlatAgg=AggregationBuilders.max("max_flat").field("positiveTotalActivePowerCharge");
        MinAggregationBuilder minFlatAgg=AggregationBuilders.min("min_flat").field("positiveTotalActivePowerCharge");
        MaxAggregationBuilder maxValleyAgg=AggregationBuilders.max("max_valley").field("positiveTotalActivePowerCharge");
        MinAggregationBuilder minValleyAgg=AggregationBuilders.min("min_valley").field("positiveTotalActivePowerCharge");
        //获取每天最新的电压电流功率
        TopHitsAggregationBuilder topHitsAggregationBuilder=AggregationBuilders.topHits("top").fetchSource(true).sort("reportTime", SortOrder.DESC).size(1);
        
        dateAgg.subAggregation(maxTotalAgg);
        dateAgg.subAggregation(minTotalAgg);
        dateAgg.subAggregation(maxApexAgg);
        dateAgg.subAggregation(minApexAgg);
        dateAgg.subAggregation(maxPeakAgg);
        dateAgg.subAggregation(minPeakAgg);
        dateAgg.subAggregation(maxFlatAgg);
        dateAgg.subAggregation(minFlatAgg);
        dateAgg.subAggregation(maxValleyAgg);
        dateAgg.subAggregation(minValleyAgg);
        dateAgg.subAggregation(topHitsAggregationBuilder);
    	searchRequestBuilder.addAggregation(meterIdAgg.subAggregation(meterSubTermCodeAgg).subAggregation(dateAgg));
    	SearchResponse response = searchRequestBuilder.setQuery(boolQueryBuilder).execute().actionGet();
    	List<StatLog> statLogs=new ArrayList<>();
    	Aggregations apps=response.getAggregations();
    	Map<String, Aggregation> map=apps.getAsMap();
    	StringTerms meterIdTerms=(StringTerms)map.get("meterId");
    	List<Bucket> buckets=meterIdTerms.getBuckets();
    	for(Bucket bucket:buckets){
    		InternalDateHistogram dateTerms=(InternalDateHistogram)bucket.getAggregations().get("reportTime");
    		StringTerms meterSubTermCodeTerms=(StringTerms)bucket.getAggregations().get("subTermCode");
    		Bucket subTermCodeBucket=meterSubTermCodeTerms.getBuckets().get(0);
    		List<InternalDateHistogram.Bucket> dateBuckets=dateTerms.getBuckets();
    		for(InternalDateHistogram.Bucket dateBucket:dateBuckets){
    			StatLog statLog=new StatLog();
    			System.out.print(bucket.getKey()+",");
    			System.out.print(subTermCodeBucket.getKey()+",");
    			System.out.print(dateBucket.getKeyAsString()+",");
    			Map<String, Aggregation> aggMap=dateBucket.getAggregations().asMap();
    			InternalMax maxTotal=(InternalMax)aggMap.get("max_total");
    			InternalMin minTotal=(InternalMin)aggMap.get("min_total");
    			Double total=maxTotal.getValue()-minTotal.getValue();
    			InternalMax maxApex=(InternalMax)aggMap.get("max_apex");
    			InternalMin minApex=(InternalMin)aggMap.get("min_apex");
    			Double apex=maxApex.getValue()-minApex.getValue();
    			InternalMax maxPeak=(InternalMax)aggMap.get("max_peak");
    			InternalMin minPeak=(InternalMin)aggMap.get("min_peak");
    			Double peak=maxPeak.getValue()-minPeak.getValue();
    			InternalMax maxFlat=(InternalMax)aggMap.get("max_flat");
    			InternalMin minFlat=(InternalMin)aggMap.get("min_flat");
    			Double flat=maxFlat.getValue()-minFlat.getValue();
    			InternalMax maxValley=(InternalMax)aggMap.get("max_valley");
    			InternalMin minValley=(InternalMin)aggMap.get("min_valley");
    			Double valley=maxValley.getValue()-minValley.getValue();
    			
    			statLog.setMeterId(bucket.getKeyAsString());
    			statLog.setSubTermCode(subTermCodeBucket.getKeyAsString());
    			statLog.setDate(dateBucket.getKeyAsString());
    			statLog.setTotal(total);
    			statLog.setLastTotal(minTotal.getValue());
    			statLog.setThisTotal(maxTotal.getValue());
    			statLog.setApex(apex);
    			statLog.setLastApex(minApex.getValue());
    			statLog.setThisApex(maxApex.getValue());
    			statLog.setPeak(peak);
    			statLog.setLastPeak(minPeak.getValue());
    			statLog.setThisPeak(maxPeak.getValue());
    			statLog.setFlat(flat);
    			statLog.setLastFlat(minFlat.getValue());
    			statLog.setThisFlat(maxFlat.getValue());
    			statLog.setValley(valley);
    			statLog.setLastValley(minValley.getValue());
    			statLog.setThisValley(maxValley.getValue());
    			statLogs.add(statLog);
    		}
    	}
    	return statLogs;
    }
    /**
     * 新增
     *
     * @param id
     * @param param
     * @return
     * @throws Exception
     */
    public IndexResponse save(String id, Map<String, Object> param) throws Exception {
        XContentBuilder builder = jsonBuilder().startObject();
        
        param.forEach((k, v) -> {
            try {
            	builder.field(k, v);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        builder.endObject();
        
        
        return this.client.prepareIndex(Constants.METER_INDEX, Constants.METER_LOG_TYPE, id).setSource(builder).get();
    }
    

    /**
     * 根据主键修改
     *
     * @param id
     * @param param
     * @return
     * @throws Exception
     */
    public UpdateResponse updateById(String id, Map<String, Object> param) throws Exception {
        UpdateRequest updateRequest = new UpdateRequest(Constants.METER_INDEX, Constants.METER_LOG_TYPE, id);
        XContentBuilder content = jsonBuilder().startObject();
        param.forEach((k, v) -> {
            try {
                content.field(k, v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        content.endObject();
        updateRequest.doc(content);

        return this.client.update(updateRequest).get();
    }

    /**
     * 根据主键获取
     *
     * @param id
     * @return
     */
    public GetResponse getById(String id) {
        return this.client.prepareGet(Constants.METER_INDEX, Constants.METER_LOG_TYPE, id).get();
    }
    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    public DeleteResponse deleteById(String id) {
        return this.client.prepareDelete(Constants.METER_INDEX, Constants.METER_LOG_TYPE, id).get();
    }
}
