package com.edianniu.pscp.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edianniu.pscp.cms.commons.MemberType;
import com.edianniu.pscp.cms.entity.CompanyEntity;
import com.edianniu.pscp.cms.service.CompanyService;
import com.edianniu.pscp.cms.utils.R;

/**
 * 企业客户
 * @author zhoujianjian
 * @date 2017年12月19日 下午3:35:58
 */
@Controller
@RequestMapping("/companyCustomer")
public class CompanyCustomerController extends AbstractController{
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 获取客户下拉列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R getAllCompanyCustomerList(){
		/*//获取所有客户信息
		List<CustomerVO> list = spsCompanyCustomerInfoService.getAllCompanyCustomerList();
		//获取客户的memberId
		HashSet<Long> set = new HashSet<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (CustomerVO vo : list) {
				set.add(vo.getMemberId());
			}
		}*/
		//获取客户公司信息
		Map<String, Object> map = new HashMap<>();
		// map.put("memberIdList", set);
		List<CompanyEntity> companyList = companyService.queryList(map);
		//提取客户公司ID和名称
		List<Map<String, Object>> companyMapList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(companyList)) {
			for (CompanyEntity companyEntity : companyList) {
				Integer memberType = companyEntity.getMemberType();
				Integer status = companyEntity.getStatus();
				// 如果认证为客户且认证成功，才在列表显示
				if (memberType == MemberType.CUSTOMER.getValue() && status == 2) {
					HashMap<String, Object> companyMap = new HashMap<>();
					companyMap.put("id", companyEntity.getId());
					companyMap.put("name", companyEntity.getName());
					companyMapList.add(companyMap);
				}
			}
		}
		return R.ok().put("companyList", companyMapList);
	}

}
