package com.edianniu.pscp.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.cms.commons.MemberType;
import com.edianniu.pscp.cms.entity.CompanyEntity;
import com.edianniu.pscp.cms.service.CompanyService;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.mis.bean.company.AuditReqData;
import com.edianniu.pscp.mis.bean.company.AuditResult;
import com.edianniu.pscp.mis.service.dubbo.CompanyInfoService;



/**
 * 企业信息
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 上午10:26:34 
 * @version V1.0
 */
@Controller
@RequestMapping("company")
public class CompanyController extends AbstractController{
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyInfoService companyInfoService;
	
	@RequestMapping("/facilitator.html")
	public String facilitator(){
		return "company/facilitator.html";
	}
	@RequestMapping("/customer.html")
	public String customer(){
		return "company/customer.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("company:list")
	public R list(Integer page, Integer limit,
			Integer status,String mobile,
			String memberMobile,String loginName,
			String name,String contacts,String memberType){
	
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("status", status);
		map.put("mobile", mobile==null?null:mobile.trim());
		map.put("memberMobile", memberMobile==null?null:memberMobile.trim());
		map.put("loginName", loginName==null?null:loginName.trim());
		map.put("name", name==null?null:name.trim());
		map.put("contacts",  contacts==null?null:contacts.trim());
		if(StringUtils.isNotBlank(memberType)){
			if(MemberType.existName(memberType)){
				map.put("memberType", MemberType.parse(memberType).getValue());
			}
			else{
				return R.error("memberType 值不正确");
			}
		}
		else{
			return R.error("memberType 不能为空");
		}
		//查询列表数据
		List<CompanyEntity> companyList = companyService.queryList(map);
		int total = companyService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(companyList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 详情
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("company:info")
	public R info(@PathVariable("id") Long id){
		
		CompanyEntity company=companyService.getCompanyByCompanyId(id);
		
		return R.ok().put("company", company);
	}

	/**
	 * 审核
	 */
	@ResponseBody
	@RequestMapping("/audit")
	@RequiresPermissions("company:audit")
	public R audit(@RequestBody CompanyEntity company){
		AuditReqData auditReqData=new AuditReqData();
		auditReqData.setAuthStatus(company.getStatus());
		auditReqData.setCompanyId(company.getId());
		auditReqData.setOpUser(this.getUser().getLoginName());
		auditReqData.setRemark(company.getRemark());
		auditReqData.setIdCardNo(company.getIdCardNo());
		AuditResult result=companyInfoService.audit(auditReqData);
		if(!result.isSuccess()){
			return R.error(result.getResultMessage());
		}
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("company:delete")
	public R delete(@RequestBody Long[] ids){
		companyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
