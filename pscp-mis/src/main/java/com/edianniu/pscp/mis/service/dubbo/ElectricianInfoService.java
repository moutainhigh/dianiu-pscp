/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:16:39 
 * @version V1.0
 */
package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.electrician.AuditReqData;
import com.edianniu.pscp.mis.bean.electrician.AuditResult;
import com.edianniu.pscp.mis.bean.user.ElectricianAuthReq;
import com.edianniu.pscp.mis.bean.user.ElectricianAuthResult;
import com.edianniu.pscp.mis.bean.user.GetElectricianReq;
import com.edianniu.pscp.mis.bean.user.GetElectricianResult;

/**
 * 电工dubbo服务接口
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:16:39 
 * @version V1.0
 */
public interface ElectricianInfoService {
   /**
    * 社会电工审核
    * @param auditReqData
    * @return
    */
   public AuditResult audit(AuditReqData auditReqData);
   /**
    * 获取电工认证信息
    *
    * @param GetElectricianReq
    * @return
    */
   public GetElectricianResult get(GetElectricianReq req);
   
   
   /**
    * 电工信息认证
    *
    * @param ElectricianAuthReq
    * @return
    */
   public ElectricianAuthResult auth(ElectricianAuthReq req); 
}
