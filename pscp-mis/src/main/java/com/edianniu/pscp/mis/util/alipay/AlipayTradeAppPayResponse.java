package com.edianniu.pscp.mis.util.alipay;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AlipayTradeAppPayResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
    private String msg;
    private String app_id;//\":\"2014072300007148\",
    private String out_trade_no;//\":\"081622560194853\",
    private String trade_no;//\":\"2016081621001004400236957647\",
    private String total_amount;//\":\"0.01\",
    private String seller_id;//\":\"2088702849871851\",
    private String charset;//\":\"utf-8\",
    private String timestamp;//\":\"2016-10-11 17:43:36\"
    private String auth_app_id;
    public Map<String,String> getParamsMap(){
    	Map<String,String> params=new HashMap<String,String>();
    	params.put("code", code);
    	params.put("msg", msg);
    	params.put("app_id", app_id);
    	params.put("auth_app_id", auth_app_id);
    	params.put("charset", charset);
    	params.put("timestamp", timestamp);
    	params.put("total_amount", total_amount);
    	params.put("trade_no", trade_no);
    	params.put("seller_id", seller_id);
    	params.put("out_trade_no", out_trade_no);
    	return params;
    }
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public String getCharset() {
		return charset;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAuth_app_id() {
		return auth_app_id;
	}
	public void setAuth_app_id(String auth_app_id) {
		this.auth_app_id = auth_app_id;
	}
}
