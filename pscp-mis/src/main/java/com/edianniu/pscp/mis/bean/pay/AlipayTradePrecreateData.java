/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月17日 下午7:08:22 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月17日 下午7:08:22 
 * @version V1.0
 */
public class AlipayTradePrecreateData implements Serializable{
	private static final long serialVersionUID = 1L;
	/**必填**/
    private String out_trade_no;
    private String total_amount;
    private String subject;
    private String body;
    /**非必填**/
    private String seller_id;
    private String discountable_amount;
    private String undiscountable_amount;
    
    
    private String goods_detail;
    
    private String operator_id;
    private String store_id;
    private String terminal_id;
    private String extend_params;
    private String time_expire;
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public String getSubject() {
		return subject;
	}
	public String getBody() {
		return body;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public String getDiscountable_amount() {
		return discountable_amount;
	}
	public String getUndiscountable_amount() {
		return undiscountable_amount;
	}
	public String getGoods_detail() {
		return goods_detail;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public String getExtend_params() {
		return extend_params;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public void setDiscountable_amount(String discountable_amount) {
		this.discountable_amount = discountable_amount;
	}
	public void setUndiscountable_amount(String undiscountable_amount) {
		this.undiscountable_amount = undiscountable_amount;
	}
	public void setGoods_detail(String goods_detail) {
		this.goods_detail = goods_detail;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	public void setExtend_params(String extend_params) {
		this.extend_params = extend_params;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
  
}
