package com.edianniu.pscp.portal.controller;

import com.edianniu.pscp.cs.bean.needs.NeedsMarketListReqData;
import com.edianniu.pscp.cs.bean.needs.NeedsMarketListResult;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.message.bean.DynamicMessageInfo;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.sps.bean.member.*;
import com.edianniu.pscp.sps.bean.news.ListTopNumReqData;
import com.edianniu.pscp.sps.bean.news.ListTopNumResult;
import com.edianniu.pscp.sps.bean.news.NewsType;
import com.edianniu.pscp.sps.service.dubbo.MemberInformationService;
import com.edianniu.pscp.sps.service.dubbo.NewsInfoService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: IndexController
 * Author: tandingbo
 * CreateTime: 2017-08-14 15:45
 */
@Controller
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    @Qualifier("newsInfoService")
    private NewsInfoService newsInfoService;
    @Autowired
    @Qualifier("memberInformationService")
    private MemberInformationService memberInformationService;
    @Autowired
    @Qualifier("needsInfoService")
    private NeedsInfoService needsInfoService;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public ModelAndView index() {
        Map<String, Object> datas = new HashMap<>();
        // 行业资讯
        ListTopNumReqData listTopNumReqData = new ListTopNumReqData();
        listTopNumReqData.setTopn(4);
        listTopNumReqData.setType(NewsType.INDUSTRY.getValue());
        ListTopNumResult industryListTopNumResult = newsInfoService.selectNewsListInfoVOTopNum(listTopNumReqData);
        datas.put("industryTopList", industryListTopNumResult.getNewsList());

        // 公告消息
        listTopNumReqData.setTopn(5);
        listTopNumReqData.setType(NewsType.NOTICE.getValue());
        ListTopNumResult noticeListTopNumResult = newsInfoService.selectNewsListInfoVOTopNum(listTopNumReqData);
        datas.put("noticeTopList", noticeListTopNumResult.getNewsList());

        // 最新需求
        NeedsMarketListReqData reqData = new NeedsMarketListReqData();
        if (ShiroUtils.getUserEntity() != null) {
            reqData.setUid(ShiroUtils.getUserEntity().getUserId());
        }
        reqData.setOffset(0);
        reqData.setPageSize(6);
        NeedsMarketListResult result = needsInfoService.queryListNeedsMarketPortal(reqData);
        datas.put("needsTopList", result.getNeedsList());
        return new ModelAndView("index.ftl", datas);
    }
    /**
	 * 获取所有的省份
	 */
	@ResponseBody
	@RequestMapping("/index/getdmmsgs")
	public R getDynamicMessageInfos(){
		try{
			List<DynamicMessageInfo> list = messageInfoService.getDynamicMessageInfos();
			
			return R.ok().put("messages", list);
		}
		catch(Exception e){
			logger.error("getDynamicMessageInfos:{}",e);
		}
		return R.error();
	}
    @ResponseBody
    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public R main() {
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        if (sysUserEntity.isFacilitator() || sysUserEntity.isNormalMember()) {
            MemberFacilitatorReqData memberFacilitatorReqData = new MemberFacilitatorReqData();
            memberFacilitatorReqData.setUid(sysUserEntity.getUserId());
            MemberFacilitatorResult result = memberInformationService.getFacilitatorMemberInfo(memberFacilitatorReqData);
            if (!result.isSuccess()) {
                R.error(result.getResultMessage());
            }
            return R.ok().put("data", result);
        }
        if (sysUserEntity.isCustomer()) {
            MemberCustomerReqData memberCustomerReqData = new MemberCustomerReqData();
            memberCustomerReqData.setUid(sysUserEntity.getUserId());
            MemberCustomerResult result = memberInformationService.getCustomerMemberInfo(memberCustomerReqData);
            if (!result.isSuccess()) {
                R.error(result.getResultMessage());
            }
            return R.ok().put("data", result);
        }
        if (sysUserEntity.isElectrician()) {
            MemberElectricianReqData memberElectricianReqData = new MemberElectricianReqData();
            memberElectricianReqData.setUid(sysUserEntity.getUserId());
            MemberElectricianResult result = memberInformationService.getElectricianMemberInfo(memberElectricianReqData);
            if (!result.isSuccess()) {
                R.error(result.getResultMessage());
            }
            return R.ok().put("data", result);
        }
        return R.ok();
    }
}
