package com.edianniu.pscp.message.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/data/ ")
public class DataAction {
    private static final Logger logger = LoggerFactory.getLogger(DataAction.class);
    /*@Autowired
    private PileInfoService pileInfoService;
   
    @RequestMapping(value="pileControl",method = RequestMethod.POST)
    @ResponseBody()
    public com.edianniu.mis.bean.Result pileControl(Model model,@RequestBody PileControlReqData pileControlReqData) {
    	com.edianniu.mis.bean.Result result=pileInfoService.stopCharge(pileControlReqData.getPileCode());
        return result;
    }*/
   
}