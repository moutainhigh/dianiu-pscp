package com.edianniu.pscp.cms.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cms.dao.SysGeneratorPgDao;
import com.edianniu.pscp.cms.service.SysGeneratorService;
import com.edianniu.pscp.cms.utils.generator.DBType;
import com.edianniu.pscp.cms.utils.generator.GenUtils;

@Service("sysGeneratorPgService")
public class SysGeneratorPgServiceImpl implements SysGeneratorService {
	@Autowired
	private SysGeneratorPgDao sysGeneratorPgDao;

	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return sysGeneratorPgDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysGeneratorPgDao.queryTotal(map);
	}

	@Override
	public Map<String, String> queryTable(String tableName) {
		return sysGeneratorPgDao.queryTable(tableName);
	}

	@Override
	public List<Map<String, String>> queryColumns(String tableName) {
		return sysGeneratorPgDao.queryColumns(tableName);
	}

	@Override
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			List<Map<String, String>> columnKeys = sysGeneratorPgDao.queryColumnKey(tableName);
			if(!columnKeys.isEmpty()){
				for(Map<String, String> column : columns){
						if(column.get("columnname").equals(columnKeys.get(0).get("columnname"))){
							column.put("columnKey", "PKI");
							break;
						}
				}
			}
			//生成代码
			GenUtils.generatorCode(table, columns, zip,DBType.POSTGRESQL);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
