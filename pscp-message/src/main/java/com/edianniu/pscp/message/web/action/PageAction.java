package com.edianniu.pscp.message.web.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ ")
public class PageAction {
    private static final Logger logger = LoggerFactory.getLogger(PageAction.class);
   
    
    /**get 请求**/
    @RequestMapping(method = RequestMethod.GET)
    public String defaultHome(Model model,HttpServletRequest request) {
    	
        return "index";
    }
    /**get 请求**/
    @RequestMapping(value="carControl.html",method = RequestMethod.GET)
    public String carControl(Model model,HttpServletRequest request) {
    	
        return "carControl";
    }
    /**get 请求**/
    @RequestMapping(value="pileControl.html",method = RequestMethod.GET)
    public String pileControl(Model model,HttpServletRequest request) {
    	
        return "pileControl";
    }
}