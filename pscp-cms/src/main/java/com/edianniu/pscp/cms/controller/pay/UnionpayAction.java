package com.edianniu.pscp.cms.controller.pay;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.pay.UnionpayNotifyReq;
import com.edianniu.pscp.mis.bean.pay.UnionpayNotifyResult;
import com.edianniu.pscp.mis.bean.pay.UnionpayPrepayInfo;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;


@Controller
@RequestMapping("/pay")
public class UnionpayAction {
	private static final Logger logger = LoggerFactory.getLogger(UnionpayAction.class);
	@Autowired
	@Qualifier("payInfoService")
	private PayInfoService payInfoService;
	
	
	@RequestMapping(value = "unionpay_notify", method = RequestMethod.POST)
	public void unionpayNotify(HttpServletRequest request, HttpServletResponse response){
		

		String encoding = request.getParameter("encoding");
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(request);	
		logger.debug("代付通知的--------返回参数是："+JSONObject.toJSONString(reqParam));
		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			try{
				while (it.hasNext()) {
					Entry<String, String> e = it.next();
					String key = (String) e.getKey();
					String value = (String) e.getValue();
					value = new String(value.getBytes(encoding), encoding);
					valideData.put(key, value);
				}
				UnionpayNotifyReq data=new UnionpayNotifyReq();
				UnionpayPrepayInfo info=new UnionpayPrepayInfo();
				data.setMap(valideData);
				logger.debug("通知的返回参数是："+JSONObject.toJSONString(valideData));
				BeanUtils.populate(info, valideData);				
				data.setUnionpayPrepayInfo(info);
				
				UnionpayNotifyResult result=payInfoService.unionpayNotify(data);
				logger.debug("处理结果"+JSONObject.toJSONString(result));
				if(result.isSuccess()){
					response.getWriter().print("ok");
				}
			}catch(Exception e){
				logger.error("unionpay_notify{}", e);
			}
			
		}
		
		
	}
	
	@RequestMapping(value = "unionpaydf_notify", method = RequestMethod.POST)
	public void unionpaydfNotify(HttpServletRequest request, HttpServletResponse response){
		String encoding=request.getParameter("encoding");
		Map<String,String>params=getAllRequestParam( request);
		Map<String,String>map=new HashMap<String,String>();
		logger.debug("代付通知-------------的返回参数是："+JSONObject.toJSONString(params));
		if(params!=null&&!params.isEmpty()){
			Iterator<Entry<String,String>>iterator=params.entrySet().iterator();
			try{
				while(iterator.hasNext()){
					Entry<String,String> entry=iterator.next();
					String key=entry.getKey();
					String value=entry.getValue();
					value = new String(value.getBytes(encoding), encoding);
					map.put(key, value);
				}
				UnionpayNotifyReq data=new UnionpayNotifyReq();
				UnionpayPrepayInfo info=new UnionpayPrepayInfo();
				logger.debug("代付通知的返回参数是："+JSONObject.toJSONString(map));
				BeanUtils.populate(info, map);
				data.setUnionpayPrepayInfo(info);
				data.setMap(map);
				UnionpayNotifyResult result=payInfoService.unionpaydfNotify(data);
				logger.debug("处理结果"+JSONObject.toJSONString(result));
				if(result.isSuccess()){
					response.getWriter().print("ok");
				}
			}catch(Exception e){
				logger.error("unionpaydf_notify{}", e);
			}
			
		}
	}
	
	private static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
	@RequestMapping(value = "notify", method = RequestMethod.GET)
	public void notify(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了这个方法");
	}
}
