/**
 * 
 */
package com.edianniu.pscp.mis.util.wxpay;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author cyl
 *
 */
@Service
@Repository("wxPayConfig")
public class WxPayConfig {
    @Value(value = "${pay.wxpay.notify.url:}")
    private String wxpayNotifyUrl = "http://www.edianniu.com/pay_center.html";
    @Value(value="${pay.wxpay.cert.dir:}")
    private String wxpayCertDir;
    
    private static final Map<String,Config> channelConfigMap=new ConcurrentHashMap<>();
    private static final Map<String,String> appIdChannelMap=new ConcurrentHashMap<>();
    public class Config{
    	private String appId;
    	private String mchId;
    	private String apiKey;
		public String getAppId() {
			return appId;
		}
		public String getMchId() {
			return mchId;
		}
		public String getApiKey() {
			return apiKey;
		}
		public void setAppId(String appId) {
			this.appId = appId;
		}
		public void setMchId(String mchId) {
			this.mchId = mchId;
		}
		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}
    }
    public String getPayChannel(String appId){
    	return appIdChannelMap.get(appId);
    }
    public Config getConfigByAppId(String appId){
    	return getConfig(getPayChannel(appId));
    }
    public Config getConfig(String payChannel){
    	return channelConfigMap.get(payChannel);
    }
    public String getApiKey(String tradeType,String payChannel){
    	if("APP".equals(tradeType)){
    		Config config=getConfig(payChannel);
    		return config==null?"":config.getApiKey();
    	}
    	else{//JSAPI,NATIVE,PC,MWEB
    		Config config=getConfig("mp");
    		return config==null?"":config.getApiKey();
    	}
    }
    public String getAppId(String tradeType,String payChannel){
    	if("APP".equals(tradeType)){
    		Config config=getConfig(payChannel);
    		return config==null?"":config.getAppId();
    	}
    	else{//JSAPI,NATIVE,PC,MWEB
    		Config config=getConfig("mp");
    		return config==null?"":config.getAppId();
    	}
    }
    public String getMchId(String tradeType,String payChannel){
    	if("APP".equals(tradeType)){
    		Config config=getConfig(payChannel);
    		return config==null?"":config.getMchId();
    	}
    	else{//JSAPI,NATIVE,PC,MWEB
    		Config config=getConfig("mp");
    		return config==null?"":config.getMchId();
    	}
    }
	public String getWxpayNotifyUrl() {
		return wxpayNotifyUrl;
	}
	public void setWxpayNotifyUrl(String wxpayNotifyUrl) {
		this.wxpayNotifyUrl = wxpayNotifyUrl;
	}
	@Value(value = "${pay.wxpay.apiKeys:}")
	public void setWxpayApiKeys(String wxpayApiKeys) {
		if(StringUtils.isNotBlank(wxpayApiKeys)){
		  String ccstrs[]=StringUtils.split(wxpayApiKeys, ",");
		  if(ccstrs!=null){
			  for(String ccstr:ccstrs){
				  String cc[]=StringUtils.split(ccstr, ":");
				  Config config=channelConfigMap.get(cc[0]);
				  if(config==null){
					  config=new Config();
					  channelConfigMap.put(cc[0], config);
				  }
				  config.setApiKey(cc[1]);
			  }
		  }
		}
	}
	@Value(value = "${pay.wxpay.appIds:}")
	public void setWxpayAppIds(String wxpayAppIds) {
		if(StringUtils.isNotBlank(wxpayAppIds)){
			  String ccstrs[]=StringUtils.split(wxpayAppIds, ",");
			  if(ccstrs!=null){
				  for(String ccstr:ccstrs){
					  String cc[]=StringUtils.split(ccstr, ":");
					  Config config=channelConfigMap.get(cc[0]);
					  if(config==null){
						  config=new Config();
						  channelConfigMap.put(cc[0], config);
					  }
					  config.setAppId(cc[1]);
					  appIdChannelMap.put(cc[1],cc[0]);
				  }
			  }
		}
	}
	@Value(value = "${pay.wxpay.mchIds:}")
	public void setWxpayMchIds(String wxpayMchIds) {
		if(StringUtils.isNotBlank(wxpayMchIds)){
			  String ccstrs[]=StringUtils.split(wxpayMchIds, ",");
			  if(ccstrs!=null){
				  for(String ccstr:ccstrs){
					  String cc[]=StringUtils.split(ccstr, ":");
					  Config config=channelConfigMap.get(cc[0]);
					  if(config==null){
						  config=new Config();
						  channelConfigMap.put(cc[0], config);
					  }
					  config.setMchId(cc[1]);
				  }
			  }
		}
	}
	public String getWxpayCertDir() {
		return wxpayCertDir;
	}
    
}
