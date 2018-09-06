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
public class AlipayTradePagePayData implements Serializable{
	private static final long serialVersionUID = 1L;
	/**必填**/
    private String out_trade_no;
    private String product_code="FAST_INSTANT_TRADE_PAY";
    private String total_amount;
    private String subject;
    private String body;
    /**非必填**/
    private String goods_detail;
    private String passback_params;//自定义回传参数
    private String extend_params;
    private String goods_type;
    private String timeout_express;
    private String enable_pay_channels;
    private String disable_pay_channels;
    private String auth_token;
    private String qr_pay_mode;
    private String qrcode_width;
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public String getProduct_code() {
		return product_code;
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
	public String getGoods_detail() {
		return goods_detail;
	}
	public String getPassback_params() {
		return passback_params;
	}
	public String getExtend_params() {
		return extend_params;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public String getTimeout_express() {
		return timeout_express;
	}
	public String getEnable_pay_channels() {
		return enable_pay_channels;
	}
	public String getDisable_pay_channels() {
		return disable_pay_channels;
	}
	public String getAuth_token() {
		return auth_token;
	}
	public String getQr_pay_mode() {
		return qr_pay_mode;
	}
	public String getQrcode_width() {
		return qrcode_width;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
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
	public void setGoods_detail(String goods_detail) {
		this.goods_detail = goods_detail;
	}
	public void setPassback_params(String passback_params) {
		this.passback_params = passback_params;
	}
	public void setExtend_params(String extend_params) {
		this.extend_params = extend_params;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}
	public void setEnable_pay_channels(String enable_pay_channels) {
		this.enable_pay_channels = enable_pay_channels;
	}
	public void setDisable_pay_channels(String disable_pay_channels) {
		this.disable_pay_channels = disable_pay_channels;
	}
	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}
	public void setQr_pay_mode(String qr_pay_mode) {
		this.qr_pay_mode = qr_pay_mode;
	}
	public void setQrcode_width(String qrcode_width) {
		this.qrcode_width = qrcode_width;
	}
}
