package com.edianniu.pscp.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 智能监控控制类
 * 服务商查询
 * @create 2017-11-28 11:41
 */
@Controller
@RequestMapping("/st")
public class CyberMonitorController {
    private Logger logger = LoggerFactory.getLogger(CyberMonitorController.class);
    
    
    @RequestMapping(value = "/smart-alarm-all.html", method = RequestMethod.GET)
    public String alarmAllindex(){
        return "st/smart-alarm-all.html";
    }
    
    @RequestMapping(value = "/smart-main.html", method = RequestMethod.GET)
    public String index(){
        return "st/smart-main.html";
    }
    
    @RequestMapping(value = "/smart-device.html", method = RequestMethod.GET)
    public String deviceIndex(Model model) {
        return "st/smart-device.html";
    }
    
    @RequestMapping(value = "/smart-fee.html", method = RequestMethod.GET)
    public String feeIndex(Model model) {
        return "st/smart-fee.html";
    }
    
    @RequestMapping(value = "/smart-health.html", method = RequestMethod.GET)
    public String healthIndex(Model model) {
        return "st/smart-health.html";
    }
    
    @RequestMapping(value = "/smart-balance.html", method = RequestMethod.GET)
    public String balanceIndex(Model model) {
        return "st/smart-balance.html";
    }
    
    @RequestMapping(value = "/smart-factor.html", method = RequestMethod.GET)
    public String factorIndex(Model model) {
        return "st/smart-factor.html";
    }
    
    @RequestMapping(value = "/smart-peakvalley.html", method = RequestMethod.GET)
    public String peakvalleyIndex(Model model) {
        return "st/smart-peakvalley.html";
    }
    
    @RequestMapping(value = "/smart-invalid.html", method = RequestMethod.GET)
    public String invalidIndex(Model model) {
        return "st/smart-invalid.html";
    }
    
    @RequestMapping(value = "/smart-ranking.html", method = RequestMethod.GET)
    public String rankingIndex(Model model) {
        return "st/smart-ranking.html";
    }
    
    @RequestMapping(value = "/smart-cuslist.html", method = RequestMethod.GET)
    public String cuslistIndex(Model model) {
        return "st/smart-cuslist.html";
    }
}
