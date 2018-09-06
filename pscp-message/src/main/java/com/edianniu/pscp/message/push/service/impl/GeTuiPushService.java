package com.edianniu.pscp.message.push.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.message.commons.ResultCode;
import com.edianniu.pscp.message.push.domain.PushMessage;
import com.edianniu.pscp.message.push.domain.PushResult;
import com.edianniu.pscp.message.push.service.PushService;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cyl
 */
@Service
@Repository("geTuiPushService")
public class GeTuiPushService implements PushService {
    private static final Logger logger = LoggerFactory.getLogger(GeTuiPushService.class);
    private Map<String,String> appidMap=new HashMap<String,String>();
    private Map<String,String> appkeyMap=new HashMap<String,String>();
    private Map<String,String> masterMap=new HashMap<String,String>();
    private String host = "https://api.getui.com/apiex.htm";

    @Value(value = "${getui.appids}")
    public void setAppIds(String appIds) {
        if(StringUtils.isNotBlank(appIds)){
        	String tmps[]=StringUtils.split(appIds, ",");
        	for(String appId:tmps){
        		String kv[]=StringUtils.split(appId,":");
        		if(kv!=null&&kv.length==2){
        			appidMap.put(kv[0], kv[1]);
        		}
        	}
        }
    }

    @Value(value = "${getui.appkeys}")
    public void setAppkey(String appkeys) {
    	if(StringUtils.isNotBlank(appkeys)){
        	String tmps[]=StringUtils.split(appkeys, ",");
        	for(String appKey:tmps){
        		String kv[]=StringUtils.split(appKey,":");
        		if(kv!=null&&kv.length==2){
        			appkeyMap.put(kv[0], kv[1]);
        		}
        	}
        }
    }

    @Value(value = "${getui.masters}")
    public  void setMasters(String masters) {
    	if(StringUtils.isNotBlank(masters)){
        	String tmps[]=StringUtils.split(masters, ",");
        	for(String master:tmps){
        		String kv[]=StringUtils.split(master,":");
        		if(kv!=null&&kv.length==2){
        			masterMap.put(kv[0], kv[1]);
        		}
        	}
        }
    }

    @Value(value = "${getui.host}")
    public  void setHost(String host) {
        this.host = host;
    }

    @Override
    public PushResult pushMessageToSingle(PushMessage message,String appType, String clientId) {
        PushResult result = new PushResult();
        if (message == null) {
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("消息体不能为空");
            return result;
        }
        if(StringUtils.isBlank(appType)){
        	result.setResultCode(ResultCode.ERROR_202);
            result.setResultMessage("appType不能为空");
            return result;
        }
        String appId=appidMap.get(appType);
        String appkey=appkeyMap.get(appType);
        String master=masterMap.get(appType);
        if(StringUtils.isBlank(appId)||StringUtils.isBlank(appkey)||StringUtils.isBlank(master)){
        	result.setResultCode(ResultCode.ERROR_202);
            result.setResultMessage("appId|appKey|master 配置有问题");
            return result;
        }
        if (StringUtils.isBlank(clientId)) {
            result.setResultCode(ResultCode.ERROR_203);
            result.setResultMessage("clientId不能为空");
            return result;
        }
        IGtPush push = new IGtPush(appkey, master, true);
        TransmissionTemplate template = getTemplate(message,appId,appkey);
        SingleMessage singleMessage = new SingleMessage();
        singleMessage.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        singleMessage.setOfflineExpireTime(24 * 3600 * 1000);
        singleMessage.setData(template);
        singleMessage.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(singleMessage, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(singleMessage, target, e.getRequestId());
        } finally {
            try {
                push.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        parseIpushResult(result, ret);
        return result;
    }

    private void parseIpushResult(PushResult result, IPushResult ret) {
        if (ret != null) {
            logger.debug("ipushResult:{}", JSONObject.toJSONString(ret));
            System.out.println(JSONObject.toJSONString(ret));
            if (ret.getResponse() != null) {
                ret.getResponse().get("taskId");
                ret.getResponse().get("status");
                if (ret.getResponse().get("result") != null) {
                    String r1 = (String) ret.getResponse().get("result");
                    if (!r1.equals("ok")) {
                        result.set(ResultCode.ERROR_201, "推送失败");
                    }
                    result.setResult(r1);
                }
                if (ret.getResponse().get("contentId") != null) {
                    result.setContentId((String) ret.getResponse().get("contentId"));
                }
                if (ret.getResponse().get("taskId") != null) {
                    result.setTaskId((String) ret.getResponse().get("taskId"));
                }
                if (ret.getResponse().get("status") != null) {
                    result.setStatus((String) ret.getResponse().get("status"));
                }
            }
        } else {
            logger.debug("ipushResult:{}", "服务器响应异常");
            result.set(ResultCode.ERROR_500, "服务器响应异常");
        }
    }

    @Override
    public PushResult pushMessageToList(PushMessage message, String appType,List<String> clientIds) {
        PushResult result = new PushResult();
        String appId=appidMap.get(appType);
        String appkey=appkeyMap.get(appType);
        String master=masterMap.get(appType);
        IGtPush push = new IGtPush(appkey, master, true);
        TransmissionTemplate template = getTemplate(message,appId,appkey);
        ListMessage listMessage = new ListMessage();
        listMessage.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        listMessage.setOfflineExpireTime(24 * 3600 * 1000);
        listMessage.setData(template);
        listMessage.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        List<Target> targetList = new ArrayList<Target>();
        for (String clientId : clientIds) {
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(clientId);
            targetList.add(target);
        }
        IPushResult ret = null;
        String contentId = push.getContentId(listMessage);
        try {
            ret = push.pushMessageToList(contentId, targetList);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToList(contentId, targetList);
        } finally {
            try {
                push.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        parseIpushResult(result, ret);
        return result;
    }

    @Override
    public PushResult pushMessageToApp(PushMessage message,String appType) {
        PushResult result = new PushResult();
        String appId=appidMap.get(appType);
        String appkey=appkeyMap.get(appType);
        String master=masterMap.get(appType);
        IGtPush push = new IGtPush(appkey, master, true);
        TransmissionTemplate template = getTemplate(message,appId,appkey);
        AppMessage appMessage = new AppMessage();
        appMessage.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        appMessage.setOfflineExpireTime(24 * 3600 * 1000);
        appMessage.setData(template);
        appMessage.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        appMessage.setAppIdList(appIdList);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToApp(appMessage);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToApp(appMessage);
        } finally {
            try {
                push.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        parseIpushResult(result, ret);
        return result;
    }

    @SuppressWarnings("deprecation")
	private TransmissionTemplate getTemplate(PushMessage message,String appId,String appKey) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(JSONObject.toJSONString(message));
        template.setTransmissionType(2);

        APNPayload payload = new APNPayload();
        payload.setBadge(2);
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory(message.getCategory());
        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(message.getTitle()));
        //字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(message));
        template.setAPNInfo(payload);
        return template;
    }

    private APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(PushMessage message) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(message.getContent());
        alertMsg.setActionLocKey("-查看消息");
        alertMsg.setLocKey(message.getTitle());
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        alertMsg.setTitle(message.getTitle());
        // IOS8.2以上版本支持
        alertMsg.setTitleLocKey(message.getTitle());
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }
    public static void main(String[] args) throws Exception {
        PushService pushService = new GeTuiPushService();
        PushMessage message = new PushMessage();
        message.setCategory("REGISTER");
        message.setContent("注册成功3-content");
        message.setPushTime("2016-09-02");
        message.setTitle("注册成功3");
        message.setType(1);
        pushService.pushMessageToApp(message,"");
        //pushService.push();
    }

    public void push(String appType,String clientId) {
    	
    	String appId=appidMap.get(appType);
        String appkey=appkeyMap.get(appType);
        String master=masterMap.get(appType);
        // https连接
        IGtPush push = new IGtPush(appkey, master, true);
        // 此处true为https域名，false为http，默认为false。Java语言推荐使用此方式
        // IGtPush push = new IGtPush(host, appkey, master);
        // host为域名，根据域名区分是http协议/https协议
        LinkTemplate template = linkTemplateDemo(appId,appkey);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);
        // 用户别名推送，cid和用户别名只能2者选其一
        // String alias = "个";
        // target.setAlias(alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }

    public  LinkTemplate linkTemplateDemo(String appId,String appKey) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 设置通知栏标题与内容
        template.setTitle("通知标题");
        template.setText("请输入通知栏内容");
        // 配置通知栏图标
        template.setLogo("icon.png");
        // 配置通知栏网络图标，填写图标URL地址
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的网址地址
        template.setUrl("http://www.baidu.com");
        return template;
    }

	public String getHost() {
		return host;
	}

}
