/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月24日 下午12:32:06 
 * @version V1.0
 */
package com.edianniu.pscp.cms.bean.req;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月24日 下午12:32:06 
 * @version V1.0
 */
public class ChangeMemberStatusReq implements Serializable{
	private static final long serialVersionUID = 1L;
	Long userId;
	Integer status;
	public Long getUserId() {
		return userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
