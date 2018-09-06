package com.edianniu.pscp.das.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReflectionUtils {
	/**
	 * 将对象数据复制到Map
	 * @param bean
	 * @param map
	 * @param ignoreProperties
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void beanToMap(Object bean,Map<String, Object> map,String... ignoreProperties) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(bean==null){
			throw new RuntimeException("bean is null");
		}
		if(map==null){
			throw new RuntimeException("map is null");
		}
		Set<String> ignoreSet=new HashSet<String>();
		if(ignoreProperties!=null){
			for(String ignore:ignoreProperties){
				ignoreSet.add(ignore);
			}
		}
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
			String propertyName=propertyDescriptor.getName();
			if(!propertyName.equals("class")&&!ignoreSet.contains(propertyName)){
				Method method=propertyDescriptor.getReadMethod();
				if(method!=null){
					Object value=method.invoke(bean, new Object[0]);
	        		if(value!=null)
	        			map.put(propertyName,value);
				}
				
			}
		}
	}
	/**
	 * 将对象数据复制到Map
	 * @param bean
	 * @param map
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void beanToMap(Object bean,Map<String, Object> map) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		beanToMap(bean,map,new String[]{});
	}
	/**
	 * 将Map数据复制到对象
	 * @param map
	 * @param bean
	 * @param ignoreProperties
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void mapToBean(Map<String, Object> map,Object bean,String... ignoreProperties) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(bean==null){
			throw new RuntimeException("bean is null");
		}
		if(map==null){
			throw new RuntimeException("map is null");
		}
		Set<String> ignoreSet=new HashSet<String>();
		if(ignoreProperties!=null){
			for(String ignore:ignoreProperties){
				ignoreSet.add(ignore);
			}
		}
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
			String propertyName=propertyDescriptor.getName();
			if(!propertyName.equals("class")&&!ignoreSet.contains(propertyName)){
				Method method=propertyDescriptor.getWriteMethod();
				Object value=map.get(propertyName);
				if(value!=null&&method!=null)
				method.invoke(bean, value);
			}
		}
	}
	/**
	 * 将Map数据复制到对象
	 * @param map
	 * @param bean
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void mapToBean(Map<String, Object> map,Object bean) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		mapToBean(map,bean,new String[]{});
	}
}
