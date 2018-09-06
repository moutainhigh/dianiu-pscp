/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月17日 上午9:49:20 
 * @version V1.0
 */
package test;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.test.util.DateUtils;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月17日 上午9:49:20 
 * @version V1.0
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Wp
		Double total=11640D-2140D;
		//Wq
		Double reactiveTotal=315.05D -200.01D;
		Double factor=0.00D;
	    if(reactiveTotal>0.00D&&reactiveTotal>0.00D){
	    	factor=total/Math.sqrt(Math.pow(total, 2)+Math.pow(reactiveTotal, 2));
	    }
	    System.out.printf("功率因数:"+factor);
		/*String nowTime="";
		Date startTime=DateUtils.parse("20171201000000", "yyyyMMddHHmmss");
		Date nextTime=null;
		for(int i=0;i<1;i++)
		{
			if(nextTime!=null){
				nowTime=DateUtils.format(nextTime, "yyyyMMddHHmmss");
				nextTime=DateUtils.parse(DateUtils.getDateOffsetMinute(nextTime, 5, "yyyyMMddHHmmss"), "yyyyMMddHHmmss");
			}
			else{
				nowTime=DateUtils.format(startTime, "yyyyMMddHHmmss");
				nextTime=DateUtils.parse(DateUtils.getDateOffsetMinute(startTime, 5, "yyyyMMddHHmmss"), "yyyyMMddHHmmss");
			}
			System.out.println("日志的时间:"+nowTime);
			try {
				Thread.sleep(300L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String end="12";
		Integer s=Integer.parseInt(end);
		// TODO Auto-generated method stub
		List<String> apexTimeIntervals=JSON.parseArray("[\"00:00-08:00\",\"11:00-15:00\",\"22:00-24:00\"]", String.class);
		double d=200.11D;
        for(int i=0;i<1;i++){
        	int r=new Random().nextInt(20);
        	if(r%2==0){
        		;
        		System.out.println("r:"+r+",符号-"+":结果="+(d-Math.random()+r));
        	}
        	else{
        		System.out.println("r:"+r+",符号+"+":结果="+(d+Math.random()+r));
        	}
        }
        Date n=new Date(1516797938000L);
        System.out.print(DateUtils.format(n));*/
	}

}
