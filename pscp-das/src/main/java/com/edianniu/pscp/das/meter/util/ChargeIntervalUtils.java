package com.edianniu.pscp.das.meter.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.das.meter.bean.ChargeInterval;
import com.edianniu.pscp.das.meter.domain.CompanyPowerPriceConfig;
import com.edianniu.pscp.das.util.DateUtils;

public class ChargeIntervalUtils {
	/**
	 * 获取区间
	 * @param companyPowerPriceConfig
	 * @param day
	 * @param time
	 * @return
	 */
	public static ChargeInterval getChargeInterval(CompanyPowerPriceConfig companyPowerPriceConfig,String day,Long time){
	   if(companyPowerPriceConfig.getChargeMode()==0){//普通
		   ChargeInterval chargeInterval=new ChargeInterval();
		   chargeInterval.setIntervalType(0);
		   chargeInterval.setIntervalPrice(companyPowerPriceConfig.getBasePrice());
		   return chargeInterval;
	   }
	   else{//分时
		   for(ChargeInterval chargeInterval:getChargeIntervals(companyPowerPriceConfig, day)){
			   if(chargeInterval.include(time)){
				   return chargeInterval;
			   }
		   } 
	   }
	   return null;
	}
	private static void setChargeIntervals(String[] intervalTimes,Double price,String day,List<ChargeInterval> chargeIntervals,int intervalType){
		for(String intervalTime:intervalTimes){
			String[] times=StringUtils.split(intervalTime,"-");
			String start=times[0]+":00";
			String end=times[1].split(":")[0];
			Integer endNum=Integer.parseInt(end);
			endNum=endNum-1;
			String tmp=String.valueOf(endNum);
			if(tmp.length()==1){
				tmp="0"+tmp;
			}
			end=tmp+":59:59";
			long startTime=DateUtils.parse(day+" "+start, "yyyyMMdd HH:mm:ss").getTime();
			long endTime=DateUtils.parse(day+" "+end, "yyyyMMdd HH:mm:ss").getTime();
			String intervalStr=day+" "+start+"-"+day+" "+end;
			ChargeInterval interval=new ChargeInterval();
			interval.setInterval(intervalTime);
			interval.setIntervalStr(intervalStr);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			interval.setIntervalType(intervalType);
			interval.setIntervalPrice(price);
			chargeIntervals.add(interval);
			
		}
	}
	/**
	 * 计算区间
	 * @param companyPowerPriceConfig
	 * @param day
	 * @return
	 */
	private static List<ChargeInterval> getChargeIntervals(CompanyPowerPriceConfig companyPowerPriceConfig,String day){
		List<ChargeInterval> chargeIntervals=new ArrayList<ChargeInterval>();
		String apexTimeInterval=companyPowerPriceConfig.getApexTimeInterval();//12:00-18:00,22:00-24:00
		List<String> apexTimeIntervals=JSON.parseArray(apexTimeInterval, String.class);//StringUtils.split(apexTimeInterval,",");
		setChargeIntervals(apexTimeIntervals.toArray(new String[apexTimeIntervals.size()]),companyPowerPriceConfig.getApexPrice(),day,chargeIntervals,1);
	    String peakTimeInterval=companyPowerPriceConfig.getPeakTimeInterval();//12:00-18:00,22:00-24:00
	    //String[] peakTimeIntervals=StringUtils.split(peakTimeInterval,",");
	    List<String> peakTimeIntervals=JSON.parseArray(peakTimeInterval, String.class);
	    setChargeIntervals(peakTimeIntervals.toArray(new String[peakTimeIntervals.size()]),companyPowerPriceConfig.getPeakPrice(),day,chargeIntervals,2);
	    String flatTimeInterval=companyPowerPriceConfig.getFlatTimeInterval();//12:00-18:00,22:00-24:00
	    //String[] flatTimeIntervals=StringUtils.split(flatTimeInterval,",");
	    List<String> flatTimeIntervals=JSON.parseArray(flatTimeInterval, String.class);
	    setChargeIntervals(flatTimeIntervals.toArray(new String[flatTimeIntervals.size()]),companyPowerPriceConfig.getFlatPrice(),day,chargeIntervals,3);
	    String valleyTimeInterval=companyPowerPriceConfig.getValleyTimeInterval();//12:00-18:00,22:00-24:00
	    //String[] valleyTimeIntervals=StringUtils.split(valleyTimeInterval,",");
	    List<String> valleyTimeIntervals=JSON.parseArray(valleyTimeInterval, String.class);
	    setChargeIntervals(valleyTimeIntervals.toArray(new String[valleyTimeIntervals.size()]),companyPowerPriceConfig.getValleyPrice(),day,chargeIntervals,4);
	    return chargeIntervals;
	}
}
