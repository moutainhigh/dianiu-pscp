/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午5:53:11 
 * @version V1.0
 */
package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.workorder.electrician.*;
import com.edianniu.pscp.sps.domain.ElectricianWorkOrder;

/**
 * 电工工单服务dubbo
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午5:53:11 
 * @version V1.0
 */
public interface ElectricianWorkOrderInfoService {
    
	public ListResult list(ListReqData listReqData);
    
    public DetailResult detail(DetailReqData detailReqData);

    void save(ElectricianWorkOrder electricianWorkOrder);

    int update(ElectricianWorkOrder electricianWorkOrder);

    int delete(Long id);

    int deleteBatch(Long[] ids);

    /**
     * 社会电工工单审核
     *
     * @param auditReqData
     * @return
     */
    AuditResult audit(AuditReqData auditReqData);
}
