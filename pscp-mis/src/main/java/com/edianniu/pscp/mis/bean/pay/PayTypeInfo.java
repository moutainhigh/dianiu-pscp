/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月27日 下午2:16:02 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月27日 下午2:16:02 
 * @version V1.0
 */
public class PayTypeInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//支付類型Id
	private String type;//alipay wxpay unionpay walletpay
	private String name;//	支付類型名稱,支付宝,微信支付,银联支付,余额支付
	private String description;//描述
	private Integer status;//0禁用,1启用
	private Integer disabled;//0   1 
	public Long getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
