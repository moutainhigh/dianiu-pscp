package com.edianniu.pscp.mis.util.alipay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 */
@Service
@Repository("alipayConfig")
public class AlipayConfig {
	private String env="online";
	@Value(value = "${pay.alipay.env:}")
	public void setEnv(String env) {
		this.env = env;
	}
	//***沙箱环境***//
	private String dev_gatewayUrl="https://openapi.alipaydev.com/gateway.do";
	public  String dev_partner = "2016072900121147";
	private String devAppPrivateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCsWLAAGuZdr6CUN/XLE5zZYst6rikIt9+uPSSDTGeq55bhENtLAQUhjguNzAEV7503QmlWmnTR/sMFP1XuCpzuVQwju5dO98xOEeS6AR5saijX91+zL3Drp1ItNSsqjTyif+JcHqQtGUSC9/gW1hrJS0UN+NpUrGBRiDQGuMOkqpwzmT3DGUkHnc1Ul2wJZiOR3h8Tx0jb5iMwDWdz5OY5oTAOuGwIhEXmcNoFhWCxFdj9E4bdvzNnio0atAMp+nnpom8sSS6o1NwqVWUz6SelvbHcXwsvX9xa+0e7UKJrqEFpOvowGvC1XH4N6UIvbK4YKkusQMIkbDPNqRWOwJsVAgMBAAECggEAZYI251jwpe1zXtk2481BbKhLwI4jjQb/r9hDUTIHPRn6O8V548tOAl1vYKzvVsQyjQGImYGPnL+4jbaoHfQSSi29YK50iAZbRwAy/3r5OFGujMUekKM4lxONyyKtsfpN5Ef+QtOpTwa624jBjnG8hSLYIUqZDZYZhOv7+JmKYclBoEL5PsZQ4Sojb6dozKKDBnRKtBK9PtVj5UfUajvDzQwt+Ar6FwLcb8h/O0I/cIMFyI6ETqjq1JVi2CsIvz5i7JnlN1zpGqKr17hLbHLui7Zl/x7GvTqoWdM5JssWv6eyngdbeMQfNccJOAgUPWd1oUjzUeZe3SCxNVglVb8PYQKBgQDuQ4MjqX/Ohc/hNQS/jQMDKnT/uG51mclfFxRPu3yFhJjOlmuK2RaT+K56VVZyUaNStC6PqUFYFXALHWgvmKKEXqfJqGQ67Ln41VjCwhcg8WWM56HvCJtRRs/ODuWk+Jk68+jrFaNEjTUss9ixtD3ztey6kWSi3w5YTQEA9vtiEwKBgQC5LQSwc7F2bNOs0H/IUGGJgOHs80Qfzx7RL1ae8u2rgxsyKwsFkbXUZfQl1MhoOfkJC+dElLPOSQeEZKpQm6Rz9ThkkKq+1whvLKLXXTEwyaPzk49IbW4uutnoLYfQ+DBiDLtrlLamu4EmYb/WG9CIIkNfXyIL1DLyAwI03ZBzNwKBgQDePPiwJeTBdMzeyj5AkT3zEQIKmDPn22caHAPlDwLp4zFI2MM8wpqtsLLGBp1Yik9l0AzyETSS/YQByg8o/hzwjP3COiArWi1pLU7acoFyR9ep41CHDicZW4xvLjQDeaIre+CQjiE4yuGDbck8j9uNW+6QYxdUV+wgbsr15Gm8IwKBgDh8If0NHWnpYlxJsWDSHB6MgN39ip9FV7opfXu40ITL1kTkWlVVkCh8/Q95K7SXUn7O8bm6vmpDXpak+kriXzS77XrHoj3abAk1jWqyU6n1KBzJuyA1uTFp78Rh9xboTiHqjlUPpPORKKEjmhfeniqYgQKbi1AeZ3fpMQgdOc3bAoGBANaTCS0C1HGPSm7LzN+hG6BgaxwH384GlamNe5p5hZpySdH2L0s5PL6A4ylaZa8d07t7RqBT9JAIB2tnnuaoVCPa0K2jvxvb9ySYRQXbdaIrjGc37IHvxZH5c00uIR8cYtlyTOXpTEFTP15dt13DS/2e5gMF+A4oAvLAnu2u789u";
	
	private String dev_appId = "2016072900121147";
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	private String gatewayUrl="https://openapi.alipay.com/gateway.do";
	public  String partner = "2088021780335478";
	
	
	private String appId = "2017051607253335";
	private String format = "JSON";
	private String charset = "utf-8";
	/**支付宝公钥**/
	private String devAlipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqBm7vYUWNasHswCVBwdUCdtIeg9ippdeFY7xsjBXnel8xup7/XsKs/WY3+gPWO/YqJq30rkTAPnrqHdH66YxTwueV1AHogR8MHEX8LTHTJfheMlOh2a0jI8zY15XqHyhcb4pVx0+/v2CNZSo+vWLJALFKR3l6m+SIhTuoz7hNqkgjevWyIRffoUS9hLn1GVb5bTMem7QP0nFtC6g1mAgGJB7gUkeT8P9r13oHZOHKDmTAa4TNyDFhlciVRv8kW5IuQuTWsGpBhS88h53vzooKK2odJKSxJDsXl/c4wY0n+YfPmO4ThhekZrLblBg7jxGdktY/VpKMkz6k3yGpD+SvwIDAQAB";
	private String alipayPublicKey    = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2b83ObEgHcFfFHtLZOKVPH/rwj6VSoEqR9EyWwV04q+VYxVKVx6O8qYXTNWkPX+hdKGb2Jook35McABHZxu+T79CY6bMxRCsrtZZBmiOFYMpiqDAmCjI877FU8ucRsKyLG7Bfsgs8xQOmtD9OVtjekgEnx71gePCe04+7CnEFSUjI9025MswsChc4nLu6n++1gcAwJkHlQUKePrqR7bZpjpleg7+fEVkFI6L3+nu2sN7kzKBM5Y5J83mFy1nXPeSEzf5gqjpeqAcJoKHlZ4YHReLhsHrtpefQr1fT33WfhD9Xz3UENgM4NkyR21rv+GL1myBqkJfS5eT+APUf83SEQIDAQAB";
	private String signType = "RSA2";
	private String alipayNotifyUrl = "http://www.edianniu.com/pay_center.html";
	private String alipayReturnUrl = "http://www.edianniu.com/pay_center.html";
	/**
	 * 商户私钥
	 **/
	private String appPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCrmTqxwsPFXpr/A/o1dh2pvlRIKOM1/wSdWO55wOmEArxQG7XSX/58W1S7IKPZ9vAUDnEWpc+22oLXvyMn5UpPvkYJkClr5FfbA3MsW4PzwQ5cpPMlwkmxhDVcewBtaLAakUD13qP9cr1UIYQvhkRHejvdv8m79tDTtcGTOH/pwBT0wq2VryjnJkWr5fL7A+cB5EHgVR/TGPGnmXKyyoZS8FCS73VTg/5NZIGooNEUXTxlbCTzbPqDKBU2PqPOJsUrwVsPL12lGmYlnyDtTP8kzR7aTnmfZ0Zc2TYpcQ50tCkL3PmFkI3oXOFmPN2quKy16xTmaZ9fYVXZAdjVpZuxAgMBAAECggEAbKFEFuRFKt0JIgdSjiWIAb0LfeLkc3pNYY666TjGm1tf+xaPs+4xHGh060FueL1qnkfGL6AoLomF6y6FAuyUbob1r3gjwLl9JJi6gIourlC41s1VX8HvBQG9Bh6X1qs/UJJDnWKkTf4k/O160NsevmETJP4Up3lIGb4mHRzXXRHCnJBBp3d34IpS+6ZxxQU9cQ/6OlrwpXwjNwL3koeU/RyTCT+K3FeqXzCnD9M3POZJEGotJWQDge2j4KBWwpJvqN+OtGnWl4v1Q5feIK49ZJNe7Hqukg5/ftOmOUOEQeph24ek5twRo7aIMQdLiCnZFlJ6MkdcZShIsAn7Z0CEAQKBgQDaPcOLSGwgooCcwwiT5qyuggLPMfBrCtSMjnnWxWfsrEV3q1fiOXNekAicx5mzzmJUcUp4EGVr+O0QzRnFH0VDkuZECqsRcE2uq+zwu6JsTHZr9NiV075mfAtP6F+2fNwoheqklIZ4LY+xNgXY1S8IuNTwIkmV7AJ6gPzVo9NZWQKBgQDJSZbKScAGcQ+Sj3VUJhGBBnZCVvzgOD5q51OlqtkgFraYTRFg+UWZYPsaSZ2H/zU3ldOyHz78lZO8vtA3BrEEcJRz/opZ0nOV6S1KZxmnXOy+JvTI9jqva2Xoi0An2rpDB2sSmyP/ay1GdD3A6b5ab4nylVGbMZbZOB06krKyGQKBgQCTNhxkJHsDXHoixBBSK8SJhhs9BfOlUM3k7epTrB8Y72+AdGy4pYYj4EZe8ujD99AuI6lW6cBwH55RuffLFvfSyBFZDlUsUzOjQkd2byr1oHI1Chjan9UMLlo3EYkWgIzFLOgFEkbOY/VMVSOGiu7KFFtWi9pVLbjSR5XEylgzKQKBgHjCc3rNIC09K1CMTKG9bQbxb4WBVpjWZWwReHeNtlGwDr/0jJaXNhA8xKkSvTXYAH9mnejhIhK7UjUMbTJ2C0FUoVutvCcDQWEQ0aEvlY55C3dRO85NGwwe7LbExCqk6NZJ3wz8km4sSK9aEappEVCEVkh5GMp4x71uEusPE7IRAoGBALSQuNXnQ3FYwG5rbUVYji+xPC7MbI9X6EloTesUU3pOEfHzdLOrzeP4YsYu1lMKb4938dPQ5iSX5O1INo1Oi8MuCCVVxzoGuSUvlRIrxpswerJZor1dDGnGK2YMkmHiktXwiA86e/D/CGiUlGzBGEGt09oKoUL0sIMDhc/9uU57";
	
	public String getPartner() {
		if(this.env.equals("dev")){
			return this.dev_partner;
		}
		return partner;
	}
	public String getAppId() {
		if(this.env.equals("dev")){
			return this.dev_appId;
		}
		return appId;
	}
	public String getFormat() {
		return format;
	}
	public String getCharset() {
		return charset;
	}
	public String getAlipayPublicKey() {
		if(this.env.equals("dev")){
			return this.devAlipayPublicKey;
		}
		return alipayPublicKey;
	}
	public String getSignType() {
		return signType;
	}
	public String getAlipayNotifyUrl() {
		return alipayNotifyUrl;
	}
	public String getAlipayReturnUrl() {
		return alipayReturnUrl;
	}
	public String getAppPrivateKey() {
		if(this.env.equals("dev")){
			return this.devAppPrivateKey;
		}
		return appPrivateKey;
	}
	@Value(value = "${pay.alipay.partner:}")
	public  void setPartner(String partner) {
		this.partner = partner;
	}
	@Value(value = "${pay.alipay.appId:}")
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	@Value(value = "${pay.alipay.alipayPublicKey:}")
	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	@Value(value = "${pay.alipay.notify.url:}")
	public void setAlipayNotifyUrl(String alipayNotifyUrl) {
		this.alipayNotifyUrl = alipayNotifyUrl;
	}
	@Value(value = "${pay.alipay.return.url:}")
	public void setAlipayReturnUrl(String alipayReturnUrl) {
		this.alipayReturnUrl = alipayReturnUrl;
	}
	@Value(value = "${pay.alipay.appPrivateKey:}")
	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey = appPrivateKey;
	}
	public String getGatewayUrl() {
		if(this.env.equals("dev")){
			return this.dev_gatewayUrl;
		}
		return gatewayUrl;
	}
	@Value(value = "${pay.alipay.gatewayUrl:}")
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	@Value(value = "${pay.alipay.dev.gatewayUrl:}")
	public void setDev_gatewayUrl(String dev_gatewayUrl) {
		this.dev_gatewayUrl = dev_gatewayUrl;
	}
	@Value(value = "${pay.alipay.dev.partner:}")
	public void setDev_partner(String dev_partner) {
		this.dev_partner = dev_partner;
	}
	@Value(value = "${pay.alipay.dev.appPrivateKey:}")
	public void setDevAppPrivateKey(String devAppPrivateKey) {
		this.devAppPrivateKey = devAppPrivateKey;
	}
	@Value(value = "${pay.alipay.dev.appId:}")
	public void setDev_appId(String dev_appId) {
		this.dev_appId = dev_appId;
	}
	@Value(value = "${pay.alipay.dev.alipayPublicKey:}")
	public void setDevAlipayPublicKey(String devAlipayPublicKey) {
		this.devAlipayPublicKey = devAlipayPublicKey;
	}
}
