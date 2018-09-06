/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午9:11:11
 * @version V1.0
 */
package com.edianniu.pscp.mis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.mis.dao.CompanyRenterConfigDao;
import com.edianniu.pscp.mis.dao.CompanyRenterDao;
import com.edianniu.pscp.mis.dao.CompanyRenterMeterDao;
import com.edianniu.pscp.mis.domain.Company;
import com.edianniu.pscp.mis.domain.CompanyRenter;
import com.edianniu.pscp.mis.domain.CompanyRenterConfig;
import com.edianniu.pscp.mis.domain.CompanyRenterMeter;
import com.edianniu.pscp.mis.service.CompanyRenterService;
import com.edianniu.pscp.mis.service.CompanyService;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午9:11:11
 */
@Service
@Repository("companyRenterService")
public class DefaultCompanyRenterService implements CompanyRenterService {

    @Autowired
    private CompanyRenterDao companyRenterDao;
    @Autowired
    private CompanyRenterMeterDao companyRenterMeterDao;
    @Autowired
    private CompanyRenterConfigDao companyRenterConfigDao;
    @Autowired
    private CompanyService companyService;

    @Override
    public CompanyRenter getById(Long id) {

        return companyRenterDao.getById(id);
    }

    @Override
    public CompanyRenter getByUid(Long uid) {
        return companyRenterDao.getCompanyRenterByUid(uid);
    }

    @Override
    public Long getCompanyUidById(Long id) {
        CompanyRenter companyRenter = getById(id);
        if (companyRenter != null) {
            Company company = companyService.getById(companyRenter.getCompanyId());
            return company != null ? company.getMemberId() : null;
        }
        return null;

    }

    @Override
    public Long getCompanyIdByUid(Long uid) {
        return companyRenterDao.getCompanyIdByUid(uid);

    }

	@Override
	public CompanyRenterMeter getRenterMeter(Long renterId, String meterNo) {
	
		return companyRenterMeterDao.getByRenterIdAndMeterNo(renterId, meterNo);
	}

	@Override
	public CompanyRenterConfig getRenterConfig(Long renterId) {
		
		return companyRenterConfigDao.getByRenterId(renterId);
	}

	@Override
	@Transactional
	public boolean updateRenterSwitchStatus(Long renterId, String meterId,int switchStatus) {
		CompanyRenterConfig companyRenterConfig=this.getRenterConfig(renterId);
		if(companyRenterConfig!=null){
			if(companyRenterConfig.getSwitchStatus()!=switchStatus){
				companyRenterConfig.setSwitchStatus(switchStatus);
				companyRenterConfigDao.updateSwitchStatus(companyRenterConfig);
			}
			CompanyRenterMeter companyRenterMeter=this.getRenterMeter(renterId, meterId);
			if(companyRenterMeter!=null&&companyRenterMeter.getSwitchStatus()!=switchStatus){
				companyRenterMeter.setSwitchStatus(switchStatus);
				companyRenterMeterDao.updateSwitchStatus(companyRenterMeter);
			}
		}
		return true;
	}

}
