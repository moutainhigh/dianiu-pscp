package com.edianniu.pscp.search.mapping;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.edianniu.pscp.search.ElasticssearchConfig;
import com.edianniu.pscp.search.common.Constants;
public class MonthElectricChargeMappingTests {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath*:test.xml");
	@Test
	public void rebuildIndex() throws Exception{
		ElasticssearchConfig elasticssearchConfig=(ElasticssearchConfig) ctx.getBean("elasticssearchConfig");
		//elasticssearchConfig.getClient().admin().indices().prepareDelete(Constants.REPORT_INDEX).execute().actionGet(); 
		//elasticssearchConfig.getClient().admin().indices().prepareCreate(Constants.REPORT_INDEX).execute().actionGet(); 
		createMapping();
		
	}
	//@Test
	public void createMapping() throws Exception{
		ElasticssearchConfig elasticssearchConfig=(ElasticssearchConfig) ctx.getBean("elasticssearchConfig");
		PutMappingRequestBuilder builder=elasticssearchConfig.getClient().admin().indices().preparePutMapping(Constants.METER_INDEX);
	      //testType就像当于数据的table  
		builder.setType(Constants.METER_MONTH_LOG_TYPE);  
	    XContentBuilder mapping = getMapping();
	    builder.setSource(mapping);  
	    PutMappingResponse  response = builder.execute().actionGet();  
	    System.out.println(response.isAcknowledged());
	}
	private  XContentBuilder getMapping() throws Exception{  
        XContentBuilder mapping = jsonBuilder()    
                   .startObject()    
                     .startObject(Constants.METER_MONTH_LOG_TYPE)    
                     .startObject("properties")           
                       .startObject("id")  
                            .field("type", "string")
                        .endObject() 
                        
                        .startObject("companyId")  
                            .field("type", "long")  
                        .endObject() 
                        .startObject("companyName")  
                            .field("type", "string")
                        .endObject()
                       .startObject("meterId")  
                            .field("type", "string")  
                            .field("index", "not_analyzed")  
                        .endObject()  
                        .startObject("subTermCode")  
                            .field("type", "string")  
                            .field("index", "not_analyzed")  
                        .endObject()
                       .startObject("date")  
                            .field("type", "string")  
                            .field("index", "not_analyzed")  
                        .endObject()
                         .startObject("cycle")  
                            .field("type", "string") 
                            .field("index", "not_analyzed")  
                       .endObject()  
                       .startObject("totalCharge")  
                            .field("type", "double")  
                       .endObject()  
                       .startObject("basicCharge")  
                            .field("type", "double")  
                       .endObject()  
                       .startObject("factorCharge")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("apexCharge")  
                            .field("type", "double")  
                       .endObject() 
                        .startObject("peakCharge")  
                            .field("type", "double")  
                       .endObject() 
                        .startObject("flatCharge")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("valleyCharge")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("factor")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("basicPrice")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("apexPrice")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("peakPrice")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("flatPrice")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("valleyPrice")  
                            .field("type", "double")  
                       .endObject() 
                       .startObject("createTime")  
                            .field("type", "long")  
                       .endObject()  
                       .startObject("updateTime")  
                            .field("type", "long")  
                       .endObject() 
                     .endObject()    
                    .endObject()    
                  .endObject();    
        return mapping;  
    }
}
