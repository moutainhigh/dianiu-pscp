/**
 * 
 */
package test.edianniu.mis.redis;

import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.edianniu.pscp.mis.bean.log.Attribute;
import com.edianniu.pscp.mis.bean.log.CommonInfo;
import com.edianniu.pscp.mis.bean.log.Control;
import com.edianniu.pscp.mis.bean.log.ControlAckInfo;
import com.edianniu.pscp.mis.bean.log.FunctionInfo;
import com.edianniu.pscp.mis.bean.log.InstructionAckInfo;
import com.edianniu.pscp.mis.bean.request.log.NetDauControlAckRequest;
import com.edianniu.pscp.mis.util.DateUtils;
import com.edianniu.pscp.mis.util.Md5Utils;
import com.edianniu.pscp.mis.util.MoneyUtils;

import redis.clients.jedis.Jedis;

/**
 * @author elliot.chen
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static void main(String[] args) throws InterruptedException, IllegalAccessException, InvocationTargetException {
		List<FunctionInfo> l=new ArrayList<FunctionInfo>();
		for(int i=45;i>0;i--){
			FunctionInfo fi=new FunctionInfo();
			Attribute attr=new Attribute();
			attr.setId(i+"");
			attr.setCoding("1A001");
			fi.setAttr(attr);
			l.add(fi);
		}
		List<Object> rlist=l.stream().sorted(new Comparator<FunctionInfo>(){
			@Override
			public int compare(FunctionInfo o1, FunctionInfo o2) {
				return Integer.parseInt(o1.getAttr().getId())-Integer.parseInt(o2.getAttr().getId());
			}
		}).collect(Collectors.toList());
		
		for(int j=0;j<20;j++){
			String functions=JSON.toJSONString(rlist,SerializerFeature.SortField);
			System.out.println(j+":"+functions);
			String md5=Md5Utils.MD5(functions);
			System.out.println(j+":"+md5);
		}
		
		
		/*Jedis jedis = new Jedis("192.168.1.251",6379); 
		//meterIds.add("330100A00201003");
		//meterIds.add("330100A00201001");
		//meterIds.add("330100A00201002");
		jedis.sadd("a-1101", "330100A00201003");
		jedis.sadd("a-1101", "330100A00201002");
		jedis.sadd("a-1101", "330100A00201001");
		Set<String> set1=jedis.smembers("a-1101");
		jedis.srem("a-1101", "330100A00201001");
		set1.remove("330100A00201001");
		Set<String> set2=jedis.smembers("a-1101");
		System.out.println(URLEncoder.encode("http://www.edianniu.cn/alipay/return_url"));
		NetDauControlAckRequest req1=new NetDauControlAckRequest();
		CommonInfo common=new CommonInfo();
		common.setBuilding_id("330100A002");
		common.setGateway_id("01");
		common.setType("control_ack");
		req1.setCommon(common);
		InstructionAckInfo instruction=new InstructionAckInfo();
		Attribute attr=new Attribute();
		attr.setOperation("control_ack");
		instruction.setAttr(attr);
		ControlAckInfo control_info=new ControlAckInfo();
		Attribute attr1=new Attribute();
		control_info.setAttr(attr1);
		List<Control> control_ack=new ArrayList<>();
		Control control=new Control();
		Attribute attr2=new Attribute();
		attr2.setErr("F1");
		attr2.setIdx("1");
		attr2.setMeterId("1001");
		control.setAttr(attr2);
		control_ack.add(control);
		control_info.setControl_ack(control_ack);
		instruction.setControl_info(control_info);
		req1.setInstruction(instruction);
		String json2=JSON.toJSONString(req1);
		String json="{\"common\":{\"building_id\":\"330100A002\",\"gateway_id\":\"01\",\"type\":\"control_ack\"},\"instruction\":{\"attr\":{\"operation\":\"control_ack\"},\"control_info\":{\"control_ack\":[{\"attr\":{\"err\":\"F1\",\"idx\":\"2\",\"meterId\":\"1003\"}}]}},\"clientHost\":\"192.168.1.195\",\"accessHost\":\"192.168.1.101\",\"accessPort\":5001,\"bizSource\":0}";
		NetDauControlAckRequest req=JSON.parseObject(json, NetDauControlAckRequest.class);
		//jedis.connect();
        String s=UUID.fromString("terminal-292992-02002-88888-88888").toString();
        String s2=UUID.randomUUID().toString();
        System.out.println("s:"+s+",s2:"+s2);
        //List<String> list=jedis.lrange("latest#dynamic#msg#list", 0, 50);
        //System.out.println(JSON.toJSONString(list));
		Integer x=0x462;
		System.out.println(Integer.toHexString(x%256));
		String dateStr=DateUtils.format(new Date(), "HHmmssMMddyy");
		System.out.println(dateStr);
		String st = Integer.toHexString(in).toUpperCase();
	      st = String.format("%s",st);
	      st= st.replaceAll(" ","0");
		System.out.println(formatHex(dateStr.substring(0, 2)));
		System.out.println(formatHex(dateStr.substring(2, 4)));
		System.out.println(formatHex(dateStr.substring(4, 6)));
		System.out.println(formatHex(dateStr.substring(6, 8)));
		System.out.println(formatHex(dateStr.substring(8, 10)));
		System.out.println(formatHex(dateStr.substring(10, 12)));*/
        
	}
	public static String formatHex(String str){
		String s=Integer.toHexString(Integer.parseInt(str)).toUpperCase();
		  s = String.format("%2s",s);
	      s= s.replaceAll(" ","0");
	    return s;
	}
	

}
