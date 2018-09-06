package com.edianniu.pscp.search.repository.meter;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.search.common.Constants;
import com.edianniu.pscp.search.support.SortField;
import com.edianniu.pscp.search.support.meter.vo.AvgOfMeterStat;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;
import com.edianniu.pscp.search.support.query.meter.DayLogQuery;

/**
 * DayLogRepository
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:36:12 
 * @version V1.0
 */
@Repository
public class DayLogRepository {

    @Autowired
    private TransportClient client;
    
    public List<AvgOfMeterStat> avgOfMeter(String fromDate, Set<String> meterIds, Long companyId){
		List<AvgOfMeterStat> list = new ArrayList<>();
		// 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_DAY_LOG_TYPE);
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
        //日期范围
        if(StringUtils.isNotBlank(fromDate)){
        	RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("reportTime");
        	rangeQueryBuilder.format("yyyyMMdd");
        	if(StringUtils.isNotBlank(fromDate))rangeQueryBuilder.gte(fromDate);
            boolQueryBuilder.must(rangeQueryBuilder);
            //boolQueryBuilder.filter(rangeQueryBuilder);
        }
        //按照仪表+星期 group by 
        TermsAggregationBuilder meterIdAgg= AggregationBuilders.terms("meterId").field("meterId");
        TermsAggregationBuilder weekAgg = AggregationBuilders.terms("week").field("week");
        weekAgg.subAggregation(AggregationBuilders.avg("total_electric") .field("total"));   //总电量
        weekAgg.subAggregation(AggregationBuilders.avg("apex_electric")  .field("apex"));    //尖电量
        weekAgg.subAggregation(AggregationBuilders.avg("peak_electric")  .field("peak"));    //峰电量
        weekAgg.subAggregation(AggregationBuilders.avg("flat_electric")  .field("flat"));    //平电量
        weekAgg.subAggregation(AggregationBuilders.avg("valley_electric").field("valley"));  //谷电量
        searchRequestBuilder.addAggregation(meterIdAgg.subAggregation(weekAgg));
        searchRequestBuilder = searchRequestBuilder.setQuery(boolQueryBuilder);
        System.out.println(searchRequestBuilder);
        //查询
        SearchResponse response = searchRequestBuilder.get();

        Aggregations aggregations = response.getAggregations();
        Aggregation aggre = aggregations.get("meterId");
        StringTerms meterIdTerms = (StringTerms)aggre;
        List<Bucket> buckets = meterIdTerms.getBuckets();
        for (Bucket bucket : buckets) {
        	AvgOfMeterStat stat = new AvgOfMeterStat();
        	stat.setMeterId(bucket.getKeyAsString());
        	
        	Aggregations aggres = bucket.getAggregations();
			Aggregation aggregation = aggres.get("week");
			LongTerms weekTerms = (LongTerms)aggregation;
			List<LongTerms.Bucket> weekBuckets = weekTerms.getBuckets();
			double totalOfTotal     = 0.0;
			double totalOfApex      = 0.0;
			double totalOfPeak      = 0.0;
			double totalOfFlat      = 0.0;
			double totalOfValley    = 0.0;
			double totalOfTotalAll  = 0.0;
			double totalOfApexAll   = 0.0;
			double totalOfPeakAll   = 0.0;
			double totalOfFlatAll   = 0.0;
			double totalOfValleyAll = 0.0;
			for (LongTerms.Bucket weekBucket : weekBuckets) {
				long keyAsNumber = (long)weekBucket.getKeyAsNumber();
				Aggregations aggs = weekBucket.getAggregations();
				double total   = ((InternalAvg)aggs.get("total_electric")).getValue();
				double apex    = ((InternalAvg)aggs.get("apex_electric")).getValue();
				double peak    = ((InternalAvg)aggs.get("peak_electric")).getValue();
				double flat    = ((InternalAvg)aggs.get("flat_electric")).getValue();
				double valley  = ((InternalAvg)aggs.get("valley_electric")).getValue();
				
				totalOfTotalAll  += total;
				totalOfApexAll   += apex;
				totalOfPeakAll   += peak;
				totalOfFlatAll   += flat;
				totalOfValleyAll += valley;
				if (!(keyAsNumber == Calendar.SUNDAY || keyAsNumber == Calendar.SATURDAY)) { //非周日、周六
					totalOfTotal  += total;
					totalOfApex   += apex;
					totalOfPeak   += peak;
					totalOfFlat   += flat;
					totalOfValley += valley;
				} 
			}
			stat.setAvgOfTotal(totalOfTotal / 5);
			stat.setAvgOfApex(totalOfApex / 5);
			stat.setAvgOfPeak(totalOfPeak / 5);
			stat.setAvgOfFlat(totalOfFlat / 5);
			stat.setAvgOfValley(totalOfValley / 5);
			stat.setAvgOfTotalAll(totalOfTotalAll / 7);
			stat.setAvgOfApexAll(totalOfApexAll / 7);
			stat.setAvgOfPeakAll(totalOfPeakAll / 7);
			stat.setAvgOfFlatAll(totalOfFlatAll / 7);
			stat.setAvgOfValleyAll(totalOfValleyAll / 7);
			list.add(stat);
		}
        System.out.println(JSON.toJSONString(list));
        
        return list;
    }
    
    
    public List<ElectricChargeStat> statList(String fromDate,String toDate,String type,Set<String> meterIds,Long companyId){
    	// 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_DAY_LOG_TYPE);
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
        SumAggregationBuilder totalElectricAgg=AggregationBuilders.sum("total_electric").field("total");//总电量
        SumAggregationBuilder apexElectricAgg=AggregationBuilders.sum("apex_electric").field("apex");//尖电量
        SumAggregationBuilder peekElectricAgg=AggregationBuilders.sum("peak_electric").field("peak");//峰电量
        SumAggregationBuilder flatElectricAgg=AggregationBuilders.sum("flat_electric").field("flat");//平电量
        SumAggregationBuilder valleyElectricAgg=AggregationBuilders.sum("valley_electric").field("valley");//谷电量
        dateAgg.subAggregation(totalElectricAgg);
        dateAgg.subAggregation(apexElectricAgg);
        dateAgg.subAggregation(peekElectricAgg);
        dateAgg.subAggregation(flatElectricAgg);
        dateAgg.subAggregation(valleyElectricAgg);
        SumAggregationBuilder totalChargeAgg=AggregationBuilders.sum("total_charge").field("totalCharge");//总电费
        SumAggregationBuilder apexChargeAgg=AggregationBuilders.sum("apex_charge").field("apexCharge");//尖电费
        SumAggregationBuilder peakChargeAgg=AggregationBuilders.sum("peak_charge").field("peakCharge");//峰电费
        SumAggregationBuilder flatChargeAgg=AggregationBuilders.sum("flat_charge").field("flatCharge");//平电费
        SumAggregationBuilder valleyChargeAgg=AggregationBuilders.sum("valley_charge").field("valleyCharge");//谷电费
        dateAgg.subAggregation(totalChargeAgg);
        dateAgg.subAggregation(apexChargeAgg);
        dateAgg.subAggregation(peakChargeAgg);
        dateAgg.subAggregation(flatChargeAgg);
        dateAgg.subAggregation(valleyChargeAgg);
    	AvgAggregationBuilder  apexPriceAgg=AggregationBuilders.avg("apex_price").field("apexPrice");// 尖单价，
        AvgAggregationBuilder  peekPriceAgg=AggregationBuilders.avg("peak_price").field("peakPrice");// 峰单价
        AvgAggregationBuilder  flatPriceAgg=AggregationBuilders.avg("flat_price").field("flatPrice");// 平单价
        AvgAggregationBuilder  valleyPriceAgg=AggregationBuilders.avg("valley_price").field("valleyPrice");// 谷单价
        dateAgg.subAggregation(apexPriceAgg);
        dateAgg.subAggregation(peekPriceAgg);
        dateAgg.subAggregation(flatPriceAgg);
        dateAgg.subAggregation(valleyPriceAgg);
    	searchRequestBuilder.addAggregation(meterIdAgg.subAggregation(meterSubTermCodeAgg).subAggregation(dateAgg));
    	SearchResponse response = searchRequestBuilder.setQuery(boolQueryBuilder).execute().actionGet();
    	List<ElectricChargeStat> dayStats=new ArrayList<>();
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
    			ElectricChargeStat dayStat=new ElectricChargeStat();
    			System.out.print(bucket.getKey()+",");
    			System.out.print(subTermCodeBucket.getKey()+",");
    			System.out.print(dateBucket.getKeyAsString()+",");
    			Map<String, Aggregation> aggMap=dateBucket.getAggregations().asMap();
    			InternalSum totalElectricSum=(InternalSum)aggMap.get("total_electric");
    			InternalSum apexElectricSum=(InternalSum)aggMap.get("apex_electric");
    			InternalSum peakElectricSum=(InternalSum)aggMap.get("peak_electric");
    			InternalSum flatElectricSum=(InternalSum)aggMap.get("flat_electric");
    			InternalSum valleyElectricSum=(InternalSum)aggMap.get("valley_electric");
    			
    			InternalSum totalChargeSum=(InternalSum)aggMap.get("total_charge");
    			InternalSum apexChargeSum=(InternalSum)aggMap.get("apex_charge");
    			InternalSum peakChargeSum=(InternalSum)aggMap.get("peak_charge");
    			InternalSum faltChargeSum=(InternalSum)aggMap.get("flat_charge");
    			InternalSum valleyChargeSum=(InternalSum)aggMap.get("valley_charge");
    			
    			InternalAvg apexPriceAvg=(InternalAvg)aggMap.get("apex_price");
    			InternalAvg peakPriceAvg=(InternalAvg)aggMap.get("peak_price");
    			InternalAvg faltPriceAvg=(InternalAvg)aggMap.get("flat_price");
    			InternalAvg valleyPriceAvg=(InternalAvg)aggMap.get("valley_price");
    			dayStat.setMeterId(bucket.getKeyAsString());
    			dayStat.setSubTermCode(subTermCodeBucket.getKeyAsString());
    			dayStat.setDate(dateBucket.getKeyAsString());
    			dayStat.setTotalCharge(totalChargeSum.getValue());
    			dayStat.setApexCharge(apexChargeSum.getValue());
    			dayStat.setPeakCharge(peakChargeSum.getValue());
    			dayStat.setFlatCharge(faltChargeSum.getValue());
    			dayStat.setValleyCharge(valleyChargeSum.getValue());
    			dayStat.setTotalElectric(totalElectricSum.getValue());
    			dayStat.setApexElectric(apexElectricSum.getValue());
    			dayStat.setPeakElectric(peakElectricSum.getValue());
    			dayStat.setFlatElectric(flatElectricSum.getValue());
    			dayStat.setValleyElectric(valleyElectricSum.getValue());
    			dayStats.add(dayStat);
    		}
    	}
    	return dayStats;
    }
    
    /**
     * 分页查询
     *
     * @param listQuery
     * @return
     */
    public SearchResponse queryList(DayLogQuery listQuery) {
        // 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_DAY_LOG_TYPE);
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
    private BoolQueryBuilder getQueryBuilder(DayLogQuery listQuery) {
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
           //仪表类型
            if(listQuery.getMeterTypes()!=null) {
            	int[] types=new int[listQuery.getMeterTypes().length]; 
            	int i=0;
            	for(Integer meterType:listQuery.getMeterTypes()){
            		types[i++]=meterType;
            	}
            	TermsQueryBuilder termsQueryBuilder=QueryBuilders.termsQuery("meterType",types);
                boolQueryBuilder.must(termsQueryBuilder);
        	}
            //日期查询 yyyyMMdd
            if (StringUtils.isNotBlank(listQuery.getDate())) {
            	PrefixQueryBuilder termQueryBuilder=QueryBuilders.prefixQuery("date", listQuery.getDate());
                boolQueryBuilder.must(termQueryBuilder);
            }
            if(listQuery.getuStatus()!=null){
            	TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("uStatus",listQuery.getuStatus());
                boolQueryBuilder.must(termQueryBuilder);
            }
            if(listQuery.getUaStatus()!=null){
            	TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("uaStatus",listQuery.getUaStatus());
                boolQueryBuilder.must(termQueryBuilder);
            }
            if(listQuery.getUbStatus()!=null){
            	TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("ubStatus",listQuery.getUbStatus());
                boolQueryBuilder.must(termQueryBuilder);
            }
            if(listQuery.getUcStatus()!=null){
            	TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("ucStatus",listQuery.getUcStatus());
                boolQueryBuilder.must(termQueryBuilder);
            }
            if(listQuery.getiUnbalanceDegree()!=null){
            	RangeQueryBuilder rangQueryBuilder=QueryBuilders.rangeQuery("iUnbalanceDegree").gt(listQuery.getiUnbalanceDegree());
            	boolQueryBuilder.must(rangQueryBuilder);
            }
            if(listQuery.getIaUnbalanceDegree()!=null){
            	RangeQueryBuilder rangQueryBuilder=QueryBuilders.rangeQuery("iaUnbalanceDegree").gt(listQuery.getIaUnbalanceDegree());
            	boolQueryBuilder.must(rangQueryBuilder);
            }
            if(listQuery.getIbUnbalanceDegree()!=null){
            	RangeQueryBuilder rangQueryBuilder=QueryBuilders.rangeQuery("ibUnbalanceDegree").gt(listQuery.getIbUnbalanceDegree());
            	boolQueryBuilder.must(rangQueryBuilder);
            }
            if(listQuery.getIcUnbalanceDegree()!=null){
            	RangeQueryBuilder rangQueryBuilder=QueryBuilders.rangeQuery("icUnbalanceDegree").gt(listQuery.getIcUnbalanceDegree());
            	boolQueryBuilder.must(rangQueryBuilder);
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
        
        
        return this.client.prepareIndex(Constants.METER_INDEX, Constants.METER_DAY_LOG_TYPE, id).setSource(builder).get();
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
        UpdateRequest updateRequest = new UpdateRequest(Constants.METER_INDEX, Constants.METER_DAY_LOG_TYPE, id);
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
        return this.client.prepareGet(Constants.METER_INDEX, Constants.METER_DAY_LOG_TYPE, id).get();
    }
    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    public DeleteResponse deleteById(String id) {
        return this.client.prepareDelete(Constants.METER_INDEX, Constants.METER_DAY_LOG_TYPE, id).get();
    }
}
