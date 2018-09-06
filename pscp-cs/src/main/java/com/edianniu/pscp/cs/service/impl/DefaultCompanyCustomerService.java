package com.edianniu.pscp.cs.service.impl;

import com.edianniu.pscp.cs.bean.company.vo.BindingFacilitatorVO;
import com.edianniu.pscp.cs.bean.query.BindingFacilitatorQuery;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.CompanyCustomerDao;
import com.edianniu.pscp.cs.domain.CompanyCustomer;
import com.edianniu.pscp.cs.service.CompanyCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * ClassName: DefaultCompanyCustomerService
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:14
 */
@Service
@Repository("companyCustomerService")
public class DefaultCompanyCustomerService implements CompanyCustomerService {

    @Autowired
    private CompanyCustomerDao companyCustomerDao;

    /**
     * 获取客户绑定过的服务商
     * @param listQuery
     * @return
     */
    @Override
    public PageResult<BindingFacilitatorVO> queryBindingFacilitator(BindingFacilitatorQuery listQuery) {
        PageResult<BindingFacilitatorVO> result = new PageResult<>();
        int total = companyCustomerDao.queryCountBindingFacilitatorVO(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<BindingFacilitatorVO> entityList = companyCustomerDao.queryListBindingFacilitatorVO(listQuery);
            result.setData(entityList);
            nextOffset = listQuery.getOffset() + entityList.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(listQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

	@Override
	public CompanyCustomer getByCustomerId(Long customerId) {
		return companyCustomerDao.getByCustomerId(customerId);
	}

	@Override
	public List<CompanyCustomer> getCompanyCustomers(HashMap<String, Object> map) {
		return companyCustomerDao.getList(map);
	}
}
