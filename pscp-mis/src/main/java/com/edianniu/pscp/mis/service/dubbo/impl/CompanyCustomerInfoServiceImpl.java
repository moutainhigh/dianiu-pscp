package com.edianniu.pscp.mis.service.dubbo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.edianniu.pscp.mis.bean.company.CompanyCustomerInfo;
import com.edianniu.pscp.mis.bean.company.CompanyCustomerStatus;
import com.edianniu.pscp.mis.domain.CompanyCustomer;
import com.edianniu.pscp.mis.service.CompanyCustomerService;
import com.edianniu.pscp.mis.service.dubbo.CompanyCustomerInfoService;

@Service
@Repository("companyCustomerInfoService")
public class CompanyCustomerInfoServiceImpl implements CompanyCustomerInfoService {
	
	private static final Logger logger = LoggerFactory
            .getLogger(CompanyCustomerInfoServiceImpl.class);
	
	@Autowired
	private CompanyCustomerService companyCustomerService;

	@Override
	public CompanyCustomerInfo getById(Long id) {
		CompanyCustomerInfo companyCustomerInfo = new CompanyCustomerInfo();
		try {
			if (null == id) {
				logger.error("id为空");
				return companyCustomerInfo;
			}
			CompanyCustomer companyCustomer = companyCustomerService.getById(id);
			if (null != companyCustomer) {
				BeanUtils.copyProperties(companyCustomer, companyCustomerInfo);
			}
		} catch (Exception e) {
			logger.error("系统异常{}", e);
			return companyCustomerInfo;
		}
		return companyCustomerInfo;
	}

	@Override
	public List<CompanyCustomerInfo> getInfo(Long memberId, Long companyId) {
		List<CompanyCustomerInfo> list = new ArrayList<>();
		try {
			if (null == memberId && null == companyId) {
				logger.error("参数不能都为空");
				return list;
			}
			HashMap<String, Object> map = new HashMap<>();
			map.put("memberId", memberId);
			map.put("companyId", companyId);
			map.put("status", CompanyCustomerStatus.BOUND.getValue());
			List<CompanyCustomer> infoList = companyCustomerService.getInfo(map);
			if (CollectionUtils.isNotEmpty(infoList)) {
				for (CompanyCustomer companyCustomer : infoList) {
					CompanyCustomerInfo companyCustomerInfo = new CompanyCustomerInfo();
					BeanUtils.copyProperties(companyCustomer, companyCustomerInfo);
					list.add(companyCustomerInfo);
				}
			}
		} catch (Exception e) {
			logger.error("系统异常{}",e);
			return list;
		}
		return list;
	}

}
