package com.edianniu.pscp.mis.service.dubbo;



import com.edianniu.pscp.mis.bean.CheckAppUpdateReqData;
import com.edianniu.pscp.mis.bean.CheckAppUpdateResult;
import com.edianniu.pscp.mis.bean.HomeData;
import com.edianniu.pscp.mis.bean.HomeResult;

public interface AppService {
	
	public HomeResult home(HomeData data);
	 /**
     * app自更新检查
     * @param checkAppUdpateReqData
     * @return
     */
    CheckAppUpdateResult checkAppUpdate(CheckAppUpdateReqData checkAppUdpateReqData);

}
