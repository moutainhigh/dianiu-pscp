package com.edianniu.pscp.cms.dao;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.commons.BaseQuery;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 下午17:25:36
 */
public interface BaseDao<T> {
	
	void save(T t);
	
	void save(Map<String, Object> map);
	
	void saveBatch(List<T> list);
	
	int update(T t);
	
	int update(Map<String, Object> map);
	int delete(Object id);
	
	int delete(Map<String, Object> map);
	
	int deleteBatch(Object[] ids);

	T queryObject(Object id);
	
	T queryObject(Map<String, Object> map);
	
	List<T> queryList(Map<String, Object> map);
	
	List<T> queryList(Object id);
	
	int queryTotal(Map<String, Object> map);
	List<T> queryList(BaseQuery baseQuery);
	
	int queryCount(BaseQuery baseQuery);

	int queryTotal();
}
