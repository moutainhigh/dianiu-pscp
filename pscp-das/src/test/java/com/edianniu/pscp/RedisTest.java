package com.edianniu.pscp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.das.meter.bean.MeterLogDo;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.251",6380);  
		String logKey="meter_log_list#";
		String newLogKey="meter_log_list_new#";
        jedis.connect();
        Long count=jedis.zcard(newLogKey);
        jedis.zremrangeByRank(newLogKey, 0, jedis.zcard(newLogKey));
        Set<String> keys0=jedis.smembers(logKey+"test_meter_log_0"+"#"+0);
        Set<String> keys1=jedis.smembers(logKey+"test_meter_log_0"+"#"+1);
        Set<String> keys2=jedis.smembers(logKey+"test_meter_log_0"+"#"+2);
        Set<String> keys3=jedis.smembers(logKey+"test_meter_log_0"+"#"+3);
        //jedis.del(logKey+"test_meter_log_0"+"#"+0,logKey+"test_meter_log_0"+"#"+1,logKey+"test_meter_log_0"+"#"+2,logKey+"test_meter_log_0"+"#"+3);
        jedis.del(keys0.toArray(new String[keys0.size()]));
        jedis.del(keys1.toArray(new String[keys1.size()]));
        jedis.del(keys2.toArray(new String[keys2.size()]));
        jedis.del(keys3.toArray(new String[keys3.size()]));
       /* System.out.println("keys0 start size:"+keys0.size());
        System.out.println("keys1 start size:"+keys1.size());
        System.out.println("keys2 start size:"+keys2.size());
        System.out.println("keys3 start size:"+keys3.size());
        //keys0
        System.out.println("keys0 start size:"+keys0.size());
        String[] ks0=keys0.toArray(new String[keys0.size()]);
        List<String> list0=jedis.mget(ks0);
        List<MeterLogDo> logs0=new ArrayList<MeterLogDo>();
        for(String value:list0){
        	if(StringUtils.isNoneBlank(value)){
        		MeterLogDo meterLogDo=JSON.parseObject(value, MeterLogDo.class);
            	logs0.add(meterLogDo);
        	}
        }
        Collections.sort(logs0,new Comparator<MeterLogDo>(){
			@Override
			public int compare(MeterLogDo o1, MeterLogDo o2) {
				if(o1.getTime()==null){
					System.out.println("有空值");
				}
				return (int)(o1.getTime()-o2.getTime());//升序
			}
        });
        for(MeterLogDo meterLogDo:logs0){
        	meterLogDo.setScore(System.nanoTime());
        	jedis.zadd(newLogKey+"test_meter_log_0"+"#"+0, meterLogDo.getScore(), JSON.toJSONString(meterLogDo));
        }
        System.out.println("keys0 end size:"+keys0.size());
      //keys1
        System.out.println("keys1 start size:"+keys1.size());
        String[] ks1=keys1.toArray(new String[keys1.size()]);
        List<String> list1=jedis.mget(ks1);
        List<MeterLogDo> logs1=new ArrayList<MeterLogDo>();
        for(String value:list1){
        	if(StringUtils.isNoneBlank(value)){
        		MeterLogDo meterLogDo=JSON.parseObject(value, MeterLogDo.class);
            	logs1.add(meterLogDo);
        	}
        	
        }
        Collections.sort(logs1,new Comparator<MeterLogDo>(){
			@Override
			public int compare(MeterLogDo o1, MeterLogDo o2) {
				
				return (int)(o1.getTime()-o2.getTime());//升序
			}
        });
        for(MeterLogDo meterLogDo:logs1){
        	meterLogDo.setScore(System.nanoTime());
        	jedis.zadd(newLogKey+"test_meter_log_0"+"#"+1, meterLogDo.getScore(), JSON.toJSONString(meterLogDo));
        }
        System.out.println("keys1 end size:"+keys1.size());
      //keys2
        System.out.println("keys2 start size:"+keys2.size());
        String[] ks2=keys2.toArray(new String[keys2.size()]);
        List<String> list2=jedis.mget(ks2);
        List<MeterLogDo> logs2=new ArrayList<MeterLogDo>();
        for(String value:list2){
        	if(StringUtils.isNoneBlank(value)){
        		MeterLogDo meterLogDo=JSON.parseObject(value, MeterLogDo.class);
            	logs2.add(meterLogDo);
        	}
        	
        }
        Collections.sort(logs2,new Comparator<MeterLogDo>(){
			@Override
			public int compare(MeterLogDo o1, MeterLogDo o2) {
				
				return (int)(o1.getTime()-o2.getTime());//升序
			}
        });
        for(MeterLogDo meterLogDo:logs2){
        	meterLogDo.setScore(System.nanoTime());
        	jedis.zadd(newLogKey+"test_meter_log_0"+"#"+2, meterLogDo.getScore(), JSON.toJSONString(meterLogDo));
        }
        System.out.println("keys2 end size:"+keys2.size());
      //keys3
        System.out.println("keys3 start size:"+keys3.size());
        String[] ks3=keys3.toArray(new String[keys3.size()]);
        List<String> list3=jedis.mget(ks3);
        List<MeterLogDo> logs3=new ArrayList<MeterLogDo>();
        for(String value:list3){
        	if(StringUtils.isNoneBlank(value)){
        		MeterLogDo meterLogDo=JSON.parseObject(value, MeterLogDo.class);
            	logs3.add(meterLogDo);
        	}
        	
        }
        Collections.sort(logs3,new Comparator<MeterLogDo>(){
			@Override
			public int compare(MeterLogDo o1, MeterLogDo o2) {
				
				return (int)(o1.getTime()-o2.getTime());//升序
			}
        });
        for(MeterLogDo meterLogDo:logs3){
        	meterLogDo.setScore(System.nanoTime());
        	jedis.zadd(newLogKey+"test_meter_log_0"+"#"+0, meterLogDo.getScore(), JSON.toJSONString(meterLogDo));
        }
        System.out.println("keys3 end size:"+keys3.size());
        List<MeterLogDo> testList=new ArrayList<MeterLogDo>();
        MeterLogDo meterLogDo=new MeterLogDo();
        meterLogDo.setTime(1000L);
        testList.add(meterLogDo);
        MeterLogDo meterLogDo1=new MeterLogDo();
        meterLogDo1.setTime(1002L);
        testList.add(meterLogDo1);
        
        MeterLogDo meterLogDo2=new MeterLogDo();
        meterLogDo2.setTime(100L);
        testList.add(meterLogDo2);
        
        
        Collections.sort(testList,new Comparator<MeterLogDo>(){
			@Override
			public int compare(MeterLogDo o1, MeterLogDo o2) {
				
				return (int)(o1.getTime()-o2.getTime());//升序
			}
        });
    	for(MeterLogDo mld:testList){
    		System.out.println(mld.getTime());
    	}
        
        //jedis.zadd("test#01", 1000D, "hello1");
        //jedis.zadd("test#01", 1001D, "hello2");
        //jedis.zadd("test#01", 1002D, "hello3");
        //jedis.zadd("test#01", 1003D, "hello4");
        //jedis.zadd("test#01", 900D, "hello0");
        Long count=jedis.zcard("test#01");
        Set<String> sets=jedis.zrange("test#01", 0, count);
        for(String s:sets){
        	System.out.println(s);
        }
        jedis.zremrangeByScore("test#01", 1001D, 1001D);
        String s=jedis.get("dayDetailData#201803021315#030100A00101001");
        
        */
       
        
       /* for(String key:keys){//jedisLogUtil.mget(keys);,减少redis连接情况，提高性能
    		String value=jedis.get(key);
    		if(key.startsWith("1131#")){
    			//System.out.println(key);
    		}
    		
    	}*/

	}

}
