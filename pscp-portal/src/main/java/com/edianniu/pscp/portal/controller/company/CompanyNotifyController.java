package com.edianniu.pscp.portal.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edianniu.pscp.portal.controller.AbstractController;

@Controller
@RequestMapping("notify")
public class CompanyNotifyController extends AbstractController{

	
	@RequestMapping("/companyNotAuth.html")
	public String companyNotAuth(){
		return "notify/companyNotAuth.html";
	}
	
	@RequestMapping("/companyAuthing.html")
	public String companyAuthing(){
		return "notify/companyAuthing.html";
	}
	
	@RequestMapping("/companyAuthFail.html")
	public String companyAuthFail(){
		return "notify/companyAuthFail.html";
	}
	
	@RequestMapping("/companyAuthAgian.html")
	public String companyAuthAgian(){
		return "notify/companyAuthAgian.html";
	}

}
