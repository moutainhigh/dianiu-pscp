package com.edianniu.pscp.mis.service.pay.impl;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.pay.UnionpayPrepayInfo;
import com.edianniu.pscp.mis.bean.pay.UnionpayPrepayReqData;
import com.edianniu.pscp.mis.bean.pay.UnionpayPrepayRespData;
import com.edianniu.pscp.mis.bean.pay.UnionpayQueryReqData;
import com.edianniu.pscp.mis.bean.pay.UnionpayQueryRespData;
import com.edianniu.pscp.mis.bean.pay.UnionpaydfData;
import com.edianniu.pscp.mis.bean.pay.UnionpaydfPayRespData;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.pay.UnionpayService;
import com.edianniu.pscp.mis.util.unionpay.AcpService;
import com.edianniu.pscp.mis.util.unionpay.LogUtil;
import com.edianniu.pscp.mis.util.unionpay.SDKConfig;
@Service
@Repository("unionPayService")
public class DefaultUnionpayService  implements UnionpayService{
	
	
	@Override
	public UnionpayPrepayRespData prepay(UnionpayPrepayReqData data) {
		UnionpayPrepayRespData result=new UnionpayPrepayRespData();	
		Map<String,String> map=new HashMap<String ,String>();
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		map.put("version", "5.0.0");            //版本号 全渠道默认值
		map.put("encoding", data.getEncoding());     //字符集编码 可以使用UTF-8,GBK两种方式
		map.put("signMethod", data.getSignMethod());           		 	//签名方法 目前只支持01：RSA方式证书加密
		map.put("txnType", data.getTxnType());              		 	//交易类型 12：代付
		map.put("txnSubType", data.getTxnSubType());           		 	//默认填写00
		map.put("bizType", data.getBizType());          		 	//000401：代付
		map.put("channelType", data.getChannelType());          		 	//渠道类型
		
		/***商户接入参数***/
		map.put("merId",data.getMerId());   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		map.put("accessType", data.getAccessType());            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
		map.put("orderId", data.getOrderId()); //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		map.put("currencyCode", data.getCurrencyCode());
		map.put("txnAmt", data.getTxnAmt());
		//map.put("accNo", data.getAccNo());
		map.put("txnTime", data.getTxnTime());		 		    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		//自定义域
		map.put("reqReserved", data.getReqReserved());
		map.put("reserved",data.getReserved());
		map.put("backUrl",data.getBackUrl());
		Map<String, String> reqData = AcpService.sign(map,"UTF-8");
		Map<String, String> rspData = AcpService.post(reqData,data.getRequestUrl(),"UTF-8");
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData,"UTF-8")){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				UnionpayPrepayInfo unionpayPrepayInfo=new UnionpayPrepayInfo();
				if(("00").equals(respCode)){					
					try{
						BeanUtils.populate(unionpayPrepayInfo, rspData);
						result.setResultCode(ResultCode.SUCCESS);
						result.setUnionpayPrepayInfo(unionpayPrepayInfo);
					}catch(Exception e){
						result.setResultCode(ResultCode.ERROR_500);
						result.setResultMessage("系统异常");
					}					
					
				}else{
					try {
						BeanUtils.populate(unionpayPrepayInfo, rspData);
						result.setResultCode(ResultCode.ERROR_401);
						result.setResultMessage("报文格式错误");
					} catch (Exception e) {
						result.setResultCode(ResultCode.ERROR_500);
						result.setResultMessage("系统异常");						
					} 										
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				result.setResultCode(ResultCode.ERROR_402);
				result.setResultMessage("验证签名失败");	//TODO 检查验证签名失败的原因
			}	
		}else{
			
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			result.setResultCode(ResultCode.ERROR_403);
			result.setResultMessage("网络请求失败");	//TODO 检查验证签名失败的原因
		}
		
		LogUtil.debug("请求信息为："+JSONObject.toJSONString(reqData)+"---------------"+"返回信息为："+JSONObject.toJSONString(rspData));
		
		return result;
	}

	@Override
	public UnionpaydfPayRespData unionpaydf(UnionpaydfData data) {
		UnionpaydfPayRespData result=new UnionpaydfPayRespData();
		Map<String, String> map = new HashMap<String, String>();
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		map.put("version",data.getVersion());            //版本号 全渠道默认值
		map.put("encoding", data.getEncoding());     //字符集编码 可以使用UTF-8,GBK两种方式
		map.put("signMethod", data.getSignMethod());           		 	//签名方法 目前只支持01：RSA方式证书加密
		map.put("txnType", data.getTxnType());              		 	//交易类型 12：代付
		map.put("txnSubType", data.getTxnSubType());           		 	//默认填写00
		map.put("bizType", data.getBizType());          		 	//000401：代付
		map.put("channelType", data.getChannelType());          		 	//渠道类型

		/***商户接入参数***/
		map.put("merId", data.getMerId());   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		map.put("accessType", data.getAccessType());            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
		map.put("orderId",data.getOrderId());
		//商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		map.put("txnTime", data.getTxnTime());		 		    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		map.put("accType", data.getAccType());					 	//账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
		
		//////////如果商户号开通了  商户对敏感信息加密的权限那么，需要对 卡号accNo加密使用：
		map.put("encryptCertId",data.getEncryptCertId());      //上送敏感信息加密域的加密证书序列号
		String accNo = AcpService.encryptData(data.getAccNo(), data.getEncoding()); //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		map.put("accNo", accNo);
		//////////
		
		/////////商户未开通敏感信息加密的权限那么不对敏感信息加密使用：
		//map.put("accNo", "6216261000000000018");                  //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		////////

		//代收交易的上送的卡验证要素为：姓名或者证件类型+证件号码
		Map<String,String> customerInfoMap = new HashMap<String,String>();
		customerInfoMap.put("certifTp", data.getCertifId());						    //证件类型
		customerInfoMap.put("certifId", data.getCertifTp());		    //证件号码
		//customerInfoMap.put("customerNm", "全渠道");					//姓名
		//String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,"6216261000000000018","UTF-8");				
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, data.getAccNo(), data.getEncoding());//开通了敏感信息加密
		map.put("customerInfo", customerInfoStr);
		map.put("txnAmt", data.getTxnAmt());						 		//交易金额 单位为分，不能带小数点
		map.put("currencyCode", data.getCurrencyCode());                    	    //境内商户固定 156 人民币
		//map.put("reqReserved", "透传字段");                      //商户自定义保留域，如需使用请启用即可；交易应答时会原样返回
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		map.put("backUrl", data.getBackUrl());
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(map,"UTF-8");			 		 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();									 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,"UTF-8");        //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, "UTF-8")){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				UnionpayPrepayInfo unionpayPrepayInfo=new UnionpayPrepayInfo();
				if(("00").equals(respCode)){
					//交易已受理(不代表交易已成功），等待接收后台通知确定交易成功，也可以主动发起 查询交易确定交易状态。								
						try{
							BeanUtils.populate(unionpayPrepayInfo, rspData);
							result.setResultCode(ResultCode.SUCCESS);
							result.setUnionpayPrepayInfo(unionpayPrepayInfo);
						}catch(Exception e){
							result.setResultCode(ResultCode.ERROR_500);
							result.setResultMessage("系统异常");
						}										
					//如果返回卡号且配置了敏感信息加密，解密卡号方法：
					//String accNo1 = resmap.get("accNo");
					//String accNo2 = AcpService.decryptPan(accNo1, "UTF-8");	//解密卡号使用的证书是商户签名私钥证书acpsdk.signCert.path
					//LogUtil.writeLog("解密后的卡号："+accNo2);
				}else if(("03").equals(respCode) ||
						 ("04").equals(respCode) ||
						 ("05").equals(respCode) ||
						 ("01").equals(respCode) ||
						 ("12").equals(respCode) ||
						 ("34").equals(respCode) ||
						 ("60").equals(respCode) ){
					//后续需发起交易状态查询交易确定交易状态。
					try {
						BeanUtils.populate(unionpayPrepayInfo, rspData);
						result.setResultCode(ResultCode.ERROR_201);
						result.setUnionpayPrepayInfo(unionpayPrepayInfo);
						result.setResultMessage("后续需发起交易状态查询交易确定交易状态");
					} catch (Exception e) {
						result.setResultCode(ResultCode.ERROR_500);
						result.setResultMessage("系统异常");						
					} 	
				}else{
					try {
						BeanUtils.populate(unionpayPrepayInfo, rspData);
						result.setResultCode(ResultCode.ERROR_401);
						result.setResultMessage("报文格式错误");
					} catch (Exception e) {
						result.setResultCode(ResultCode.ERROR_500);
						result.setResultMessage("系统异常");						
					} 										
				
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				result.setResultCode(ResultCode.ERROR_402);
				result.setResultMessage("验证签名失败");	//TODO 检查验证签名失败的原因
			}	
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			result.setResultCode(ResultCode.ERROR_403);
			result.setResultMessage("网络请求失败");	//TODO 检查验证签名失败的原因
		}
		String str=JSONObject.toJSONString(rspData);
		System.out.println("result="+str);
		return result;
	}

	@Override
	public UnionpayQueryRespData unionapyQuery(UnionpayQueryReqData data) {
		UnionpayQueryRespData result=new UnionpayQueryRespData();
		Map<String, String> map = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		map.put("version", "5.0.0");                 //版本号
		map.put("encoding", data.getEncoding());               //字符集编码 可以使用UTF-8,GBK两种方式
		map.put("signMethod", data.getSignMethod());                          //签名方法 目前只支持01-RSA方式证书加密
		map.put("txnType", data.getTxnType());                             //交易类型 00-默认
		map.put("txnSubType", data.getTxnSubType());                          //交易子类型  默认00
		map.put("bizType", data.getBizType());                         //业务类型 代付
		
		/***商户接入参数***/
		map.put("merId", data.getMerId());                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		map.put("accessType", data.getAccessType());                           //接入类型，商户接入固定填0，不需修改
		map.put("reserved", "{cardNumberLock=1}");
		/***要调通交易以下字段必须修改***/
		map.put("orderId", data.getOederId());                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
		map.put("txnTime", data.getTxmTime());                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
		map.put("queryId", data.getQueryId());
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		
		Map<String, String> reqData = AcpService.sign(map,"UTF-8");			        //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getSingleQueryUrl();										//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
		Map<String, String> rspData = AcpService.post(reqData,url,"UTF-8");     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			try{
				if(AcpService.validate(rspData, "UTF-8")){
					LogUtil.writeLog("验证签名成功");
					UnionpayPrepayInfo unionpayPrepayInfo=new UnionpayPrepayInfo();
					if(("00").equals(rspData.get("respCode"))){//如果查询交易成功
						String origRespCode = rspData.get("origRespCode");						
						//处理被查询交易的应答码逻辑
						if(("00").equals(origRespCode) ||("A6").equals(origRespCode)){							
								BeanUtils.populate(unionpayPrepayInfo, rspData);
								result.setResultCode(ResultCode.SUCCESS);
								result.setUnionpayPrepayInfo(unionpayPrepayInfo);
								result.setResultMessage(unionpayPrepayInfo.getOrigRespMsg());
						}else if(("03").equals(origRespCode)||
								 ("04").equals(origRespCode)||
								 ("05").equals(origRespCode)||
								 ("01").equals(origRespCode)||
								 ("12").equals(origRespCode)||
								 ("34").equals(origRespCode)||
								 ("60").equals(origRespCode)){
							//后续需发起交易状态查询交易确定交易状态。							
								BeanUtils.populate(unionpayPrepayInfo, rspData);
								result.setResultCode(ResultCode.ERROR_201);
								result.setResultMessage("后续需发起交易状态查询交易确定交易状态");							
						}else{
							//其他应答码为交易失败
							BeanUtils.populate(unionpayPrepayInfo, rspData);
							result.setResultCode(ResultCode.ERROR_405);
							result.setResultMessage("交易失败:"+unionpayPrepayInfo.getOrigRespMsg());
						}
					}else if(("34").equals(rspData.get("respCode"))){
						//订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准						
							BeanUtils.populate(unionpayPrepayInfo, rspData);
							result.setResultCode(ResultCode.ERROR_201);
							result.setResultMessage("后续需发起交易状态查询交易确定交易状态");
					}else{//查询交易本身失败，如应答码10/11检查查询报文是否正确						
							BeanUtils.populate(unionpayPrepayInfo, rspData);
							result.setResultCode(ResultCode.ERROR_401);
							result.setResultMessage("报文格式错误");																			
					}
				}else{
					LogUtil.writeErrorLog("验证签名失败");
					result.setResultCode(ResultCode.ERROR_402);
					result.setResultMessage("验证签名失败");	//TODO 检查验证签名失败的原因
				}
			}catch(Exception e){
				result.setResultCode(ResultCode.ERROR_500);
				result.setResultMessage("系统异常");
			}
			
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			result.setResultCode(ResultCode.ERROR_403);
			result.setResultMessage("网络请求失败");	//TODO 检查验证签名失败的原因
		}
		String str=JSONObject.toJSONString(rspData);
		System.out.println("result="+str);
		return result;
	}

}
