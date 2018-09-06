package com.edianniu.pscp;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.das.meter.bean.ChargeInterval;
import com.edianniu.pscp.das.meter.domain.CompanyPowerPriceConfig;
import com.edianniu.pscp.das.meter.util.ChargeIntervalUtils;
import com.edianniu.pscp.das.meter.util.NumericUtils;
import com.edianniu.pscp.das.meter.util.PowerFactorUtils;
import com.edianniu.pscp.das.util.DateUtils;

/**
 * 
 */

/**
 * @author cyl
 *
 */
public class Test {
    private void testChareTimeInterval(){
    	//计算电费周期
    			int chargeTimeInterval=15;
    			String cycle="";
    			Date reportTime=DateUtils.parse("201801030", "yyyyMMdd");
    			int date=DateUtils.getDayOfMonth(reportTime);
    			String yyyyMM="";//结算月份
    			if(date<chargeTimeInterval){
    				String cycleStart=DateUtils.getDateOffsetDay(DateUtils.getDateOffsetMonth(reportTime, -1),(chargeTimeInterval-date),"yyyyMMdd");
    				String cycleEnd=DateUtils.getDateOffsetDay(reportTime, (chargeTimeInterval-date-1), "yyyyMMdd");
    				cycle=cycleStart+"~"+cycleEnd;
    				yyyyMM=DateUtils.getDateOffsetDay(reportTime, (chargeTimeInterval-date-1), "yyyyMM");
    			}
    			else{
    				String cycleStart=DateUtils.format(reportTime, "yyyyMM")+(chargeTimeInterval<=9?("0"+chargeTimeInterval):""+chargeTimeInterval);
    				String cycleEnd=DateUtils.getDateOffsetMonth(reportTime, 1,"yyyyMM")+((chargeTimeInterval-1)<=9?("0"+(chargeTimeInterval-1)):(""+(chargeTimeInterval-1)));
    				cycle=cycleStart+"~"+cycleEnd;
    				yyyyMM=DateUtils.getDateOffsetMonth(reportTime, 1,"yyyyMM");
    			}
    			System.out.println("结算月份:"+yyyyMM);
    			System.out.println("计费周期:"+cycle);
    }
    public void testInternal(CompanyPowerPriceConfig companyPowerPriceConfig,String day,Long time,int intervalType){
    	ChargeInterval chargeInterval=ChargeIntervalUtils.getChargeInterval(companyPowerPriceConfig, day, time);
    	if(intervalType==chargeInterval.getIntervalType()){
    		System.out.print("正确:");
    	}
    	else{
    		System.out.print("错误:");
    	}
    	System.out.println(JSON.toJSONString(chargeInterval));
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double total=0D;
		Double reactiveTotal=0.00D;
		if(!(reactiveTotal==0.00D&&total==0.00D)){
			Double factor=NumericUtils.format(0/Math.sqrt(Math.pow(total, 2)+Math.pow(1, 2)));
			System.out.println(factor);
		}
		
		Double adjustmentRate=PowerFactorUtils.getAdjustmentRate(0.85D, 0.907D);
		System.out.println("xx:"+adjustmentRate);
		Test test=new Test();
		CompanyPowerPriceConfig companyPowerPriceConfig=new CompanyPowerPriceConfig();
		companyPowerPriceConfig.setChargeMode(1);//计费方式：0 统一单价，1分时
		companyPowerPriceConfig.setBasePrice(1.0D);//统一单价
		companyPowerPriceConfig.setApexPrice(2.0D);//尖电价
		companyPowerPriceConfig.setApexTimeInterval("[\"00:00-08:00\"]");
		companyPowerPriceConfig.setPeakPrice(3.0D);//峰电价
		companyPowerPriceConfig.setPeakTimeInterval("[\"08:00-13:00\"]");
		companyPowerPriceConfig.setFlatPrice(4.0D);//平电价
		companyPowerPriceConfig.setFlatTimeInterval("[\"13:00-19:00\"]");
		companyPowerPriceConfig.setValleyPrice(5.0D);//谷电价
		companyPowerPriceConfig.setValleyTimeInterval("[\"19:00-24:00\"]");
		String dstr1="20170112 23:23:59";
		Date date=DateUtils.parse(dstr1, "yyyyMMdd HH:mm:ss");
		System.out.println(dstr1);
		test.testInternal(companyPowerPriceConfig, "20170112", date.getTime(),2);
		String dstr2="20170112 08:23:00";
		Date date2=DateUtils.parse(dstr2, "yyyyMMdd HH:mm:ss");
		System.out.println(dstr2);
		//test.testInternal(companyPowerPriceConfig, "20170112", date2.getTime(),1);
		System.out.println("startTime:"+DateUtils.format(new Date(1516808703000L), "yyyyMMdd HH:mm:ss"));
		System.out.println("endTime:"+DateUtils.format(new Date(1516809603000L), "yyyyMMdd HH:mm:ss"));
		
		/*Double ia=137.1D;
		Double ib=119.1D;
		Double ic=156.1D;
		
		Double iavg=(ia+ib+ic)/3;
		Double iadiff=ia-iavg;
		Double ibdiff=ib-iavg;
		Double icdiff=ic-iavg;
		List<Double> idiffList=new ArrayList<Double>();
		idiffList.add(iadiff);
		idiffList.add(ibdiff);
		idiffList.add(icdiff);
		Double max=idiffList.stream().max(new Comparator<Double>(){
			@Override
			public int compare(Double o1, Double o2) {
				return o1.compareTo(o2);
			}
		}).get();
		System.out.println("ia:"+ia+",ib:"+ib+",ic:"+ic);
		System.out.println("iadiff:"+iadiff+",ibdiff:"+ibdiff+",icdiff:"+icdiff);
		System.out.println("max:"+max+",iavg:"+iavg);
		System.out.println("max/iavg"+max/iavg);
		System.out.println(iadiff+"/"+iavg+":"+Math.abs(iadiff/iavg));
		System.out.println(ibdiff+"/"+iavg+":"+Math.abs(ibdiff/iavg));
		System.out.println(icdiff+"/"+iavg+":"+Math.abs(icdiff/iavg));
		Random uR=new Random(100);
		for(int i=0;i<50;i++)
		System.out.println("x:"+uR.nextInt(100));*/
		
		
		
	}
	
	private void testPeriodSets(){
		int period=15;
		Date reportTime=DateUtils.parse("201801050000", "yyyyMMddHHmm");
		for(int i=0;i<24*60/period;i++){
			String yyyyMMddhhMM=DateUtils.getDateOffsetMinute(reportTime, period*i, "yyyyMMddHHmm");
			System.out.println(i+":"+yyyyMMddhhMM);
		}
		String a=DateUtils.getDateOffsetMinute(reportTime, -1, "yyyyMMddHHmm");
		System.out.println("昨天"+a);
	}

}
