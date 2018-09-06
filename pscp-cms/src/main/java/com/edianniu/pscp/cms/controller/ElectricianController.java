package com.edianniu.pscp.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edianniu.pscp.cms.entity.ElectricianCertificateEntity;
import com.edianniu.pscp.cms.entity.ElectricianEntity;
import com.edianniu.pscp.cms.service.ElectricianService;
import com.edianniu.pscp.cms.utils.JsonResult;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.mis.bean.electrician.AuditReqData;
import com.edianniu.pscp.mis.bean.electrician.AuditResult;
import com.edianniu.pscp.mis.bean.electrician.ElectricianCertificateInfo;
import com.edianniu.pscp.mis.service.dubbo.ElectricianInfoService;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.ResumeReqData;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.ResumeResult;
import com.edianniu.pscp.sps.service.dubbo.ElectricianResumeInfoService;

/**
 * 电工：社会+企业
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-04-14 18:17:07
 */
@Controller
@RequestMapping("/electrician")
public class ElectricianController extends AbstractController{
	@Autowired
	private ElectricianService electricianService;
	@Autowired
	private ElectricianInfoService electricianInfoService;
	@Autowired
	private ElectricianResumeInfoService	electricianResumeService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("electrician:list")
	public R list(ElectricianEntity bean,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("bean", bean);
		//查询列表数据
		List<ElectricianEntity> electricianList = electricianService.queryList(map);
		int total = electricianService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(electricianList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("electrician:info")
	public R info(@PathVariable("id") Long id){
		ElectricianEntity electrician = electricianService.getById(id);
		if(electrician==null){
			return R.error("电工信息不存在");
		}
		
		return R.ok().put("electrician", electrician);
	}
	

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/audit")
	@RequiresPermissions("electrician:audit")
	public R audit(@RequestBody ElectricianEntity electrician){
		AuditReqData auditReqData=new AuditReqData();
		auditReqData.setAuthStatus(electrician.getAuthStatus());
		List<ElectricianCertificateInfo> certificates=new ArrayList<>();
		if(electrician.getCertificates()!=null){
			for(ElectricianCertificateEntity ece:electrician.getCertificates()){
				if(ece.isSelected()){
					ElectricianCertificateInfo eci=new ElectricianCertificateInfo();
					BeanUtils.copyProperties(ece, eci);
					eci.setRemark(eci.getName());
					certificates.add(eci);
				}
				
			}
		}
		auditReqData.setCertificates(certificates);
		auditReqData.setElectricianId(electrician.getId());
		auditReqData.setOpUser(this.getUser().getLoginName());
		auditReqData.setRemark(electrician.getRemark());
		AuditResult result=electricianInfoService.audit(auditReqData);
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
	@RequiresPermissions("electrician:delete")
	public R delete(@RequestBody Long[] ids){
		electricianService.deleteBatch(ids);
		
		return R.ok();
	}
	
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/resume")
	@RequiresPermissions("electrician:resume")
	public JsonResult resume(@RequestBody ElectricianEntity electrician){
		JsonResult json=new JsonResult();
		try{
			ResumeReqData resumeReqData=new ResumeReqData();
			resumeReqData.setElectricianId(electrician.getId());
			ResumeResult resume=electricianResumeService.details(resumeReqData);
			json.setObject(resume);
			json.setSuccess(true);
		}catch(Exception e){
			json.setSuccess(false);
			json.setMsg("系统异常");
		}
		
		
		return json;
	}
	
}
