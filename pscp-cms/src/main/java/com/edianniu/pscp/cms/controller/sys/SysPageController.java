package com.edianniu.pscp.cms.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * 
 * @author yanlin.chen
 * @email yanlin.che@edianniu.com
 * @date 2017年04月19日 下午17:20:27
 */
@Controller
public class SysPageController {
	@RequestMapping("main.html")
	public String main(HttpServletRequest request){
		
		return "main.html";
	}
	@RequestMapping("pay/{url}.html")
	public String payPage(@PathVariable("url") String url){
		return "pay/" + url + ".html";
	}
	@RequestMapping("sys/{url}.html")
	public String sysPage(@PathVariable("url") String url){
		return "sys/" + url + ".html";
	}
	@RequestMapping("cp/{url}.html")
	public String cpPage(@PathVariable("url") String url){
		return "cp/" + url + ".html";
	}
	@RequestMapping("electrician/{url}.html")
	public String electricianPage(@PathVariable("url") String url){
		return "cp/" + url + ".html";
	}
	@RequestMapping("member/{url}.html")
	public String member(@PathVariable("url") String url){
		return "member/" + url + ".html";
	}
}
