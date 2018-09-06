package com.edianniu.pscp.portal.controller.company;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.user.invitation.ApplyElectricianUnBindReq;
import com.edianniu.pscp.mis.bean.user.invitation.ConfirmElectricianUnBindReq;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationListReq;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationListResult;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationReq;
import com.edianniu.pscp.mis.service.dubbo.UserInvitationInfoService;
import com.edianniu.pscp.portal.controller.AbstractController;
import com.edianniu.pscp.portal.entity.ElectricianEntity;
import com.edianniu.pscp.portal.service.ElectricianService;
import com.edianniu.pscp.portal.service.SysUserService;
import com.edianniu.pscp.portal.utils.BizUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;


/**
 * 企业电工
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-04-14 18:17:07
 */
@Controller
@RequestMapping("/companyelectrician")
public class CompanyElectricianController extends AbstractController{
	@Autowired
	private ElectricianService electricianService;
	@Autowired
	private UserInvitationInfoService userInvitationInfoService;
	@Autowired
	private SysUserService sysUserService;
	 @RequestMapping("/companyelectrician.html")
	    public String list() {
	        return "cp/companyelectrician.html";
	    }
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("electrician:list")
	public R list(Integer page, Integer limit,@ModelAttribute ElectricianInvitationListReq electricianInvitationListReq){
		electricianInvitationListReq.setCompanyId(getUser().getCompanyId());
		electricianInvitationListReq.setOffset((page - 1) * limit);
		electricianInvitationListReq.setPageSize(limit);
		ElectricianInvitationListResult result=userInvitationInfoService.list(electricianInvitationListReq);
		if(!result.isSuccess()){
			return R.error(result.getResultMessage());
		}
		PageUtils pageUtil = new PageUtils(result.getList(), result.getTotalCount(), limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("electrician:info")
	public R info(@PathVariable("id") Long id){
		ElectricianEntity electrician = electricianService.queryObject(id);
		
		return R.ok().put("electrician", electrician);
	}
	
	/**
	 * 邀请
	 */
	@ResponseBody
	@RequestMapping("/invitation")
	@RequiresPermissions("electrician:invitation")
	public R save(@RequestBody ElectricianInvitationReq electricianInvitationReq){
		if(electricianInvitationReq==null){
			return R.error("参数错误");
		}
		electricianInvitationReq.setUid(ShiroUtils.getUserId());
		Result result=userInvitationInfoService.electricianInvitation(electricianInvitationReq);
		if(!result.isSuccess()){
			return R.error(result.getResultMessage());
		}
		return R.ok();
	}
	/**
	 * 申请解绑
	 */
	@ResponseBody
	@RequestMapping("/applyunbind")
	@RequiresPermissions("electrician:applyunbind")
	public R applyunbind(@RequestBody ApplyElectricianUnBindReq applyElectricianUnBindReq){
		if(applyElectricianUnBindReq==null){
			return R.error("参数错误");
		}
		applyElectricianUnBindReq.setUid(ShiroUtils.getUserId());
		Result result=userInvitationInfoService.applyElectricianUnBind(applyElectricianUnBindReq);
		if(!result.isSuccess()){
			return R.error(result.getResultMessage());
		}
		return R.ok();
	}
	/**
	 * 确认解绑
	 */
	@ResponseBody
	@RequestMapping("/confirmunbind")
	@RequiresPermissions("electrician:confirmunbind")
	public R confirmunbind(@RequestBody ConfirmElectricianUnBindReq confirmElectricianUnBindReq){
		if(confirmElectricianUnBindReq==null){
			return R.error("参数错误");
		}
		confirmElectricianUnBindReq.setUid(ShiroUtils.getUserId());
		Result result=userInvitationInfoService.confirmElectricianUnBind(confirmElectricianUnBindReq);
		if(!result.isSuccess()){
			return R.error(result.getResultMessage());
		}
		return R.ok();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("electrician:update")
	public R update(@RequestBody ElectricianEntity electrician){
		if(StringUtils.isBlank(electrician.getLoginName())){
			return R.error("登录名不能为空");
		}
		if(!BizUtils.checkLength(electrician.getLoginName().trim(), 20)){
			return R.error("登录名长度控制在20个字");
		}
		if(StringUtils.isBlank(electrician.getMobile())){
			return R.error("手机号不能为空");
		}
		if(!BizUtils.checkMobile(electrician.getMobile().trim())){
			return R.error("手机号格式不正确");
		}
		if(StringUtils.isBlank(electrician.getIdCardNo())){
			return R.error("身份证号码不能为空");
		}
		if(!BizUtils.checkCardNo(electrician.getIdCardNo().trim())){
			return R.error("身份证号码格式不正确");
		}
		com.edianniu.pscp.portal.commons.Result result=electricianService.update(electrician);
		if(!result.isSuccess()){
			return R.error(result.getMessage());
		}
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("electrician:delete")
	public R delete(@RequestBody Long[] ids){
		electricianService.deleteBatch(ids);	
		return R.ok();
	}
	
}
