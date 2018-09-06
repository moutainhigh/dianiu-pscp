package com.edianniu.pscp.search;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.edianniu.pscp.search.support.meter.MeterLogReqData;

public class Test {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		MeterLogReqData reqData=new MeterLogReqData();
		int i=1;
		//reqData.setFcompanyId(1L);
		reqData.setCompanyId(102L);
		reqData.setMeterId("#000002");
		reqData.setCompanyName("测试客户101");
		reqData.setUa(200.21+i);
		reqData.setUb(200.22+i);
		reqData.setUc(200.20+i);
		reqData.setIa(12.92+i);
		reqData.setIb(12.90+i);
		reqData.setIc(12.91+i);
		reqData.setTime(new Date().getTime());
		Map<String, Object> param = new HashMap<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(reqData.getClass());
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
			String propertyName=propertyDescriptor.getName();
			if(!propertyName.equals("class")){
				Method method=propertyDescriptor.getReadMethod();
				Object value=method.invoke(reqData, new Object[0]);
        		if(value!=null)
        		param.put(propertyName,value);
			}
		}
		
		System.out.println("param"+param);
		/*Class clazz=reqData.getClass();
		
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
        	
        	if(!field.getName().equals("serialVersionUID")){
        		
        		
        	}
        }*/

	}

}
