package com.edianniu.pscp.portal.controller.company;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.cs.bean.company.BindingFacilitatorReqData;
import com.edianniu.pscp.cs.bean.company.BindingFacilitatorResult;
import com.edianniu.pscp.cs.service.dubbo.CompanyCustomerInfoService;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationListReq;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationListResult;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationReq;
import com.edianniu.pscp.mis.service.dubbo.UserInvitationInfoService;
import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.sps.bean.customer.CustomerResult;
import com.edianniu.pscp.sps.bean.customer.DetailsReqData;
import com.edianniu.pscp.sps.bean.customer.DetailsResult;
import com.edianniu.pscp.sps.bean.customer.ResetPasswordReqData;
import com.edianniu.pscp.sps.bean.customer.ResetPasswordResult;
import com.edianniu.pscp.sps.service.dubbo.SpsCompanyCustomerInfoService;


/**
 * 企业客户管理
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-08 17:08:44
 */
@Controller
@RequestMapping("companycustomer")
public class CompanyCustomerController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyCustomerController.class);

    @Autowired
    private SpsCompanyCustomerInfoService companyCustomerInfoService;
    @Autowired
	private UserInvitationInfoService userInvitationInfoService;
    @Autowired
    private CompanyCustomerInfoService csCompanyCustomerInfoService;
    
    @RequestMapping("/companycustomer.html")
    public String list() {
        return "cp/companycustomer.html";
    }
    
    @RequestMapping("/cus_company.html")
    public String cusCompanylist() {
        return "cp/cus_company.html";
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("companycustomer:list")
    public R list(Integer page, Integer limit, @ModelAttribute CompanyInvitationListReq companyInvitationListReq) {
        int offset = (page - 1) * limit;
        companyInvitationListReq.setCompanyId(ShiroUtils.getUserEntity().getCompanyId());
        companyInvitationListReq.setOffset(offset);
        companyInvitationListReq.setPageSize(limit);
        CompanyInvitationListResult result=userInvitationInfoService.list(companyInvitationListReq);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        /*ListPageReqData listPageReqData = new ListPageReqData();
        BeanUtils.copyProperties(bean, listPageReqData);

        listPageReqData.setUid(ShiroUtils.getUserEntity().getUserId());
        listPageReqData.setOffset(offset);
        listPageReqData.setPageSize(limit);

        ListPageResult result = companyCustomerInfoService.listPage(listPageReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }*/
        PageUtils pageUtil = new PageUtils(result.getList(), result.getTotalCount(), limit, page);
        return R.ok().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("companycustomer:info")
    public R info(@PathVariable("id") Long id) {
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();

        DetailsReqData detailsReqData = new DetailsReqData();
        detailsReqData.setCustomerId(id);
        detailsReqData.setUid(sysUserEntity.getUserId());

        DetailsResult result = companyCustomerInfoService.details(detailsReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok().put("companyCustomer", result.getCompanyCustomer());
    }

    /**
     * 获取公司所有客户信息
     */
    @ResponseBody
    @RequestMapping("/getall")
    @RequiresPermissions("company:customer:getall")
    public R getAll() {
        CustomerResult result = companyCustomerInfoService.selectCustomerByCompanyId(this.getUser().getCompanyId());
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok().put("companyCustomers", result.getCustomerList());
    }

    /**
     * 邀请
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("companycustomer:save")
    public R save(@RequestBody CompanyInvitationReq companyInvitationReq) {
    	if(companyInvitationReq==null){
    		return R.error("参数错误");
    	}
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        companyInvitationReq.setUid(sysUserEntity.getUserId());
        Result result=userInvitationInfoService.companyInvitation(companyInvitationReq);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/resetpassword")
    @RequiresPermissions("companycustomer:resetpassword")
    public R resetPassword(@RequestBody JSONObject reqData) {
        Long id = reqData.getLong("id");

        ResetPasswordReqData resetPasswordReqData = new ResetPasswordReqData();
        resetPasswordReqData.setCustomerId(id);
        resetPasswordReqData.setUid(ShiroUtils.getUserId());

        ResetPasswordResult result = companyCustomerInfoService.resetPassword(resetPasswordReqData);
        if (!result.isSuccess()) {
            return R.error(result.getResultMessage());
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("companycustomer:delete")
    public R delete(@RequestBody Long[] ids) {
        com.edianniu.pscp.sps.bean.Result result = companyCustomerInfoService.delete(ShiroUtils.getUserId(), ids);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok();
    }
    
    @ResponseBody
    @RequiresPermissions("cp:cus:company")
    @RequestMapping("/bindedCompany")
    public R getBindedCompany(Integer page, Integer limit, @ModelAttribute BindingFacilitatorReqData listReqData){
    	int offset = (page - 1) * limit;
    	listReqData.setOffset(offset);
    	listReqData.setUid(ShiroUtils.getUserId());
    	//listReqData.setUid(1243L);
		BindingFacilitatorResult result = csCompanyCustomerInfoService.queryBindingFacilitator(listReqData);
    	if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		PageUtils pageUtils = new PageUtils(result.getCompanyList(), result.getTotalCount(), limit, page);
    	
    	return R.ok().put("page", pageUtils);
    }

}
