package com.edianniu.pscp.cms.controller.sys;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cms.service.SysGeneratorService;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;

/**
 * 代码生成器
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年02月20日 上午11:51:58
 */
@Controller
@RequestMapping("/sys/generatorPg")
public class SysGeneratorPgController {
	@Autowired
	private SysGeneratorService sysGeneratorPgService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:generator:list")
	public R list(String tableName, Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableName);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<Map<String, Object>> list = sysGeneratorPgService.queryList(map);
		int total = sysGeneratorPgService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	@RequiresPermissions("sys:generator:code")
	public void code(String tables, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		
		byte[] data = sysGeneratorPgService.generatorCode(tableNames);
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
  
        IOUtils.write(data, response.getOutputStream());  
	}
}
