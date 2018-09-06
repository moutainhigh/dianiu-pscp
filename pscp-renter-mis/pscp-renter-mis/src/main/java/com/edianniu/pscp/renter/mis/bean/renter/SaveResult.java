package com.edianniu.pscp.renter.mis.bean.renter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;

/**
 * 门户租客管理--租客保存
 * @author zhoujianjian
 * @date 2018年4月4日 下午3:55:23
 */
public class SaveResult extends Result{

	private Long renterId;
	
	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	private static final long serialVersionUID = 1L;
	
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
