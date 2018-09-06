/**
 * 
 */
package test.edianniu.renter.mis.redis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author elliot.chen
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		/*Jedis jedis = new Jedis("172.16.3.148",6385);  
        jedis.connect();  
        jedis.set("key", "123");  
        String value = jedis.get("key"); 
        jedis.set("key", "1234");
        String value2 = jedis.get("key"); 
        System.out.println(value); 
        System.out.println(value2);  
        jedis.setex("key1", 20, "0909");
        String value3 = jedis.get("key1");
        System.out.println(value3);  
        jedis.setex("key1", 20, "09090");
        String value4 = jedis.get("key1");
        System.out.println(value4);  
        
        jedis.setnx("key2", "t0009");
        String value5 = jedis.get("key2");
        System.out.println(value5);  
        jedis.setnx("key2", "09090nw");
        String value6 = jedis.get("key2");
        System.out.println(value6); 
        //Thread.sleep(3*1000L);
        value6 = jedis.get("key2");
        System.out.println("haha:"+value6);*/
        //String s="BYvKiT4NLzm4s6NabqiFYF/+wC2QENpXNOkNQrPubqtlmn5e8tcPWCSdU+0hpbPvbtKY0sebkjpCUwsdo9DZtZSTwLsDbDvCtKH+4FvnLyk5OcawEpGX2/oSkRQdbn0ccZBSkE/BeHxpo+kCBCtveZajbSb0n/aVQ9PrnRyXhzGrzufZCsoCy8dyvx9PW8A/UoFHLojkdN4KtWhRAvuHKN/gecP5w4sr2bHk2NuaGeRBwAwsBsM5Cxv9PhSQQ+WvEGhigqiFBIpz6HsULEibhJXmGIWU/d+VRhxSv5X5jZ0jevvV91Q2X+lU4B0l/AP1TgCv+XjMsJjMMSH6jxw2tA==";
        //System.out.println("haha:"+s.length());
	/*	BigDecimal bigDecimal=new BigDecimal("0.57");
		System.out.println(bigDecimal.movePointRight(2).intValue());
		System.out.println(bigDecimal.intValue());
		System.out.println(Double.valueOf("0.57"));*/
		String uri1="/cp/workorder/electrician/index.html";
		String uri2="/cp/workorder/social/index.html";
		String regexp="/cp/company";
		if(regexp.endsWith(".html")){
   		 if(regexp.equals(uri1)){
   			System.out.println(uri1);
   		 }
   	}
   	else{
   		 Matcher matcher = Pattern.compile(regexp).matcher(uri1);
            if (matcher.find()) {
            	System.out.println(uri1);
            }
   	}

	}

}
