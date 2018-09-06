package com.edianniu.pscp.portal.dao;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年02月20日 上午10:23:04
 */
public interface SysGeneratorPgDao {
	
	List<Map<String, Object>> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Map<String, String> queryTable(String tableName);
	
	List<Map<String, String>> queryColumns(String tableName);
	
	List<Map<String, String>> queryColumnKey(String tableName);
}
