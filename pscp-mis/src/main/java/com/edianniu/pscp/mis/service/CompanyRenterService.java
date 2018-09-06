package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.CompanyRenter;
import com.edianniu.pscp.mis.domain.CompanyRenterConfig;
import com.edianniu.pscp.mis.domain.CompanyRenterMeter;

/**
 * CompanyRenterService
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午9:10:44
 */
public interface CompanyRenterService {
    CompanyRenter getById(Long id);
    
    
    CompanyRenter getByUid(Long uid);

    Long getCompanyUidById(Long id);

    /**
     * 通过租客uid获取 业主公司id
     *
     * @param uid
     * @return
     */
    Long getCompanyIdByUid(Long uid);
    /**
     * 根据租客ID和仪表编号获取租客仪表信息
     * @param renterId
     * @param meterNo
     * @return
     */
    CompanyRenterMeter getRenterMeter(Long renterId,String meterNo);
    /**
     * 
     * @param renterId
     * @return
     */
    CompanyRenterConfig getRenterConfig(Long renterId);
    /**
     * 更新租客仪表状态
     * @param renterId
     * @param meterId
     * @param switchStatus
     * @return
     */
    public boolean updateRenterSwitchStatus(Long renterId,String meterId,int switchStatus);
    
}
