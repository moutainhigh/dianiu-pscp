/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午6:22:20 
 * @version V1.0
 */
package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrdeHomeReqData;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrdeHomeResult;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrdeListReqData;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderDetailReqData;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderDetailResult;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderListResult;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderOnoffReqData;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderTakeReqData;


/**
 * 社会工单服务接口
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午6:22:20
 * @version V1.0
 */
public interface SocialWorkOrderInfoService {
	/**
	 * 社会工单接单
	 * @param req
	 * @return
	 */
	public Result take(SocialWorkOrderTakeReqData req);
	/**
	 * 社会工单上下线
	 * @param req
	 * @return
	 */
	public Result onoff(SocialWorkOrderOnoffReqData req);
    /**
     * 社会工单列表
     * @param req
     * @return
     */
	public SocialWorkOrderListResult list(SocialWorkOrdeListReqData req);
    /**
     * 社会工单详情
     * @param req
     * @return
     */
	public SocialWorkOrderDetailResult detail(SocialWorkOrderDetailReqData req);
    /**
     * 社会工单首页
     * @param req
     * @return
     */
	public SocialWorkOrdeHomeResult home(SocialWorkOrdeHomeReqData req);
}
