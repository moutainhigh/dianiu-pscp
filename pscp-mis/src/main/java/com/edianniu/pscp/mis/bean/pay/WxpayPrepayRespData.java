package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class WxpayPrepayRespData implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean isSuccess=false;
	private WxpayPrepayInfo weixinpay;
	private String returnCode;
	private String resultCode;
	private String codeUrl;//扫描支付
	private String mwebUrl;//wap手机网站支付
	private String  prepayid;
	private String errCode;
	private String errCodeDes;
	private String returnMsg;
	
	public String getErrorMessage(){
		if (StringUtils.isBlank(returnCode)) {
			return "微信支付调用失败";
		}
		if (!returnCode.equals("SUCCESS")) {
			return returnMsg;
		}
		if (StringUtils.isBlank(resultCode)) {
			return "微信支付调用失败";
		}
		if (!resultCode.equals("SUCCESS")) {
			return "err_code:" +errCode
					+ ",err_code_des:" +errCodeDes;
		}
		if (StringUtils.isBlank(prepayid)) {
			return "微信支付调用失败";
		}
    	return "";
    }
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public WxpayPrepayInfo getWeixinpay() {
		return weixinpay;
	}
	public void setWeixinpay(WxpayPrepayInfo weixinpay) {
		this.weixinpay = weixinpay;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getMwebUrl() {
		return mwebUrl;
	}
	public void setMwebUrl(String mwebUrl) {
		this.mwebUrl = mwebUrl;
	}

}
