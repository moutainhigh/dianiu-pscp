package com.edianniu.pscp.search.repository.meter;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Map;

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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edianniu.pscp.search.common.Constants;
import com.edianniu.pscp.search.support.SortField;
import com.edianniu.pscp.search.support.query.meter.DemandDetailQuery;

/**
 * ReportDemandDetailRepository
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:36:12 
 * @version V1.0
 */
@Repository
public class DemandDetailRepository {

    @Autowired
    private TransportClient client;

    /**
     * 分页查询
     *
     * @param listQuery
     * @return
     */
    public SearchResponse queryList(DemandDetailQuery listQuery) {
        // 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_DEMAND_DETAIL_TYPE);
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
     * @param listQuery
     * @return
     */
    private BoolQueryBuilder getQueryBuilder(DemandDetailQuery listQuery) {
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
            if (listQuery.getType()!=null) {
                TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("type", listQuery.getType());
                boolQueryBuilder.must(termQueryBuilder);
            }
            if (listQuery.getValue()!=null) {
                TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("value", listQuery.getValue());
                boolQueryBuilder.must(termQueryBuilder);
            }
            //日期查询 yyyyMMdd
            if (StringUtils.isNotBlank(listQuery.getDate())) {
            	PrefixQueryBuilder prefixQueryBuilder=QueryBuilders.prefixQuery("date", listQuery.getDate());
                boolQueryBuilder.must(prefixQueryBuilder);
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
    public Double getMaxPower(Long companyId, String meterId,Long startTime,Long endTime) {
    	 // 依据查询索引库名称创建查询索引
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constants.METER_INDEX);
        //设置查询文档,表名
        searchRequestBuilder.setTypes(Constants.METER_DEMAND_DETAIL_TYPE);
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
        
        //日期查询 yyyyMMdd
        if (startTime!=null||endTime!=null) {
        	RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("time");
        	if(startTime!=null){
        		rangeQueryBuilder.gte(startTime);
        	}
        	if(endTime!=null){
        		rangeQueryBuilder.lte(endTime);
        	}
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        MaxAggregationBuilder  teamAgg= AggregationBuilders.max("maxPower").field("power");  
    	searchRequestBuilder.addAggregation(teamAgg);
    	SearchResponse response = searchRequestBuilder.setQuery(boolQueryBuilder).execute().actionGet();
    	Aggregations  agg=response.getAggregations();
    	Max  max= agg.get("maxPower");
    	if(max.getValue()==Double.NaN||max.getValue()==Double.POSITIVE_INFINITY||max.getValue()==Double.NEGATIVE_INFINITY){
    		return 0.00D;
    	}
    	return max.getValue();
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
        
        
        return this.client.prepareIndex(Constants.METER_INDEX, Constants.METER_DEMAND_DETAIL_TYPE, id).setSource(builder).get();
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
        UpdateRequest updateRequest = new UpdateRequest(Constants.METER_INDEX, Constants.METER_DEMAND_DETAIL_TYPE, id);
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
        return this.client.prepareGet(Constants.METER_INDEX, Constants.METER_DEMAND_DETAIL_TYPE, id).get();
    }
    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    public DeleteResponse deleteById(String id) {
        return this.client.prepareDelete(Constants.METER_INDEX, Constants.METER_DEMAND_DETAIL_TYPE, id).get();
    }
}
