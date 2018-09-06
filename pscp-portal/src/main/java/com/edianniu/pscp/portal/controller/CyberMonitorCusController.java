package com.edianniu.pscp.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 智能监控控制类
 * 客户查询
 */
@Controller
@RequestMapping("/cus_st")
public class CyberMonitorCusController {
    private Logger logger = LoggerFactory.getLogger(CyberMonitorCusController.class);
    
    @RequestMapping(value = "/smart-main.html", method = RequestMethod.GET)
    public String index(){
        return "cus_st/smart-main.html";
    }
    
    @RequestMapping(value = "/smart-device.html", method = RequestMethod.GET)
    public String deviceIndex(Model model) {
        return "cus_st/smart-device.html";
    }
    
    @RequestMapping(value = "/smart-alarm.html", method = RequestMethod.GET)
    public String alramIndex(Model model) {
    	return "cus_st/smart-alarm.html";
    }
    
}
