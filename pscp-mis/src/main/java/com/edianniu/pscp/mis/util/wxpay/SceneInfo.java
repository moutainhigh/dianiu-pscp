/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午6:13:23 
 * @version V1.0
 */
package com.edianniu.pscp.mis.util.wxpay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午6:13:23 
 * @version V1.0
 */
public class SceneInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private H5Info h5_info;
	public H5Info getH5_info() {
		return h5_info;
	}
	public void setH5_info(H5Info h5_info) {
		this.h5_info = h5_info;
	}

}
