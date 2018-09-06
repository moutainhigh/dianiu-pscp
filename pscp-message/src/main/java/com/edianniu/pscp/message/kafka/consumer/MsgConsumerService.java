package com.edianniu.pscp.message.kafka.consumer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.message.bean.DynamicMessageInfo;
import com.edianniu.pscp.message.bean.MessageInfo;
import com.edianniu.pscp.message.commons.CacheKey;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.config.KafkaConsumerConfiguration;
import com.edianniu.pscp.message.getuiclient.domain.GeTuiClient;
import com.edianniu.pscp.message.getuiclient.service.GeTuiClientService;
import com.edianniu.pscp.message.msg.domain.MemberMessage;
import com.edianniu.pscp.message.msg.domain.MessageExt;
import com.edianniu.pscp.message.msg.domain.MessageSendLog;
import com.edianniu.pscp.message.msg.service.MemberMessageService;
import com.edianniu.pscp.message.msg.service.MessageSendLogService;
import com.edianniu.pscp.message.push.domain.PushMessage;
import com.edianniu.pscp.message.push.domain.PushResult;
import com.edianniu.pscp.message.push.service.impl.GeTuiPushService;
import com.edianniu.pscp.message.service.dubbo.SmsInfoService;
import com.edianniu.pscp.message.web.util.DateUtils;

/**
 * @author cyl
 */
@Service
@Repository("msgConsumerService")
public class MsgConsumerService extends AbsConsumerService {
	private static final Logger logger = LoggerFactory
			.getLogger(MsgConsumerService.class);

	private String topicName = "pscp_msg";
	private KafkaConsumer<String, String> consumer = null;

	@Autowired
    private KafkaConsumerConfiguration kc;
	@Autowired
	private GeTuiPushService geTuiPushService;
	@Autowired
	private GeTuiClientService geTuiClientService;
	@Autowired
	private SmsInfoService smsInfoService;
	@Autowired
	private MemberMessageService memberMessageService;
	@Autowired
	private MessageSendLogService messageSendLogService;
	@Autowired
	private JedisUtil jedisUtil;

	public MsgConsumerService() {

	}

	@PostConstruct
	public void listener() {
		init();
		consumer.subscribe(Arrays.asList(topicName));
		new Thread(new ListenerThread(consumer, geTuiPushService)).start();
		System.out.println("end");

	}

	class ListenerThread implements Runnable {
		KafkaConsumer<String, String> consumer;
		GeTuiPushService geTuiPushService;

		public ListenerThread(KafkaConsumer<String, String> consumer,
				GeTuiPushService geTuiPushService) {
			this.consumer = consumer;
			this.geTuiPushService = geTuiPushService;
		}

		@Override
		public void run() {
			while (true) {
				logger.debug(topicName + "consumer loading...");
				try {
					ConsumerRecords<String, String> records = consumer
							.poll(1000);
					for (ConsumerRecord<String, String> record : records) {
						logger.info(
								"topic={},offset ={}, key = {}, value = {}",
								record.topic(), record.offset(), record.key(),
								record.value());
						MessageId smsMsgId = MessageId.parse(record.key());
						// 消息传递+push
						MessageInfo messageInfo = JSONObject.parseObject(
								record.value(), MessageInfo.class);
						String content = smsInfoService.getContent(
								messageInfo.getMobile(), smsMsgId,
								messageInfo.getParams());
						/**
						 * 处理最新的消息，首页动态显示
						 */
					    handleLatestMessageInfo(messageInfo);
						PushMessage message = new PushMessage();
						message.setContent(content);
						message.setTitle(smsMsgId.getDesc());
						message.setType(1);
						message.setUid(messageInfo.getUid());
						message.setCategory(smsMsgId.getJumpTarget());
						message.setPushTime(DateUtils.format(messageInfo
								.getPushTime()));
						String ext=null;
						if(messageInfo.getExts()!=null){
							ext=JSON.toJSONString(messageInfo.getExts());
							if(smsMsgId.equals(MessageId.AGREE_ELECTRICIAN_INVITATION)||
									smsMsgId.equals(MessageId.REJECT_ELECTRICIAN_INVITATION)){//同意/拒绝电工邀请，修改以前的邀请信息
								List<MemberMessage> l=memberMessageService.queryElectricianInvitationMessage(messageInfo.getUid(), messageInfo.getExts().get("invitationId"));
								for(MemberMessage memberMessage:l){
									MessageExt messageExt=JSON.parseObject(memberMessage.getExt(), MessageExt.class);
									messageExt.setActionType(smsMsgId.equals(MessageId.AGREE_ELECTRICIAN_INVITATION)?"agree":"reject");
									memberMessage.setExt(JSON.toJSONString(messageExt));
									memberMessageService.update(memberMessage);
								}
							}
							else if(smsMsgId.equals(MessageId.AGREE_COMPANY_INVITATION)||
									smsMsgId.equals(MessageId.REJECT_COMPANY_INVITATION)){//同意/拒绝企业邀请，修改以前的邀请信息
								List<MemberMessage> l=memberMessageService.queryCompanyInvitationMessage(messageInfo.getUid(), messageInfo.getExts().get("invitationId"));
								for(MemberMessage memberMessage:l){
									MessageExt messageExt=JSON.parseObject(memberMessage.getExt(), MessageExt.class);
									messageExt.setActionType(smsMsgId.equals(MessageId.AGREE_COMPANY_INVITATION)?"agree":"reject");
									memberMessage.setExt(JSON.toJSONString(messageExt));
									memberMessageService.update(memberMessage);
								}
							}
							else if(smsMsgId.equals(MessageId.AGREE_ELECTRICIAN_UNBUND)||
									smsMsgId.equals(MessageId.REJECT_ELECTRICIAN_UNBUND)){//同意/拒绝电工解绑邀请，修改以前的邀请信息
								List<MemberMessage> l=memberMessageService.queryUnBundInvitationMessage(messageInfo.getUid(), messageInfo.getExts().get("invitationId"));
								for(MemberMessage memberMessage:l){
									MessageExt messageExt=JSON.parseObject(memberMessage.getExt(), MessageExt.class);
									messageExt.setActionType(smsMsgId.equals(MessageId.AGREE_ELECTRICIAN_UNBUND)?"agree":"reject");
									memberMessage.setExt(JSON.toJSONString(messageExt));
									memberMessageService.update(memberMessage);
								}
							}
						}
						message.setExt(ext);
						
						MemberMessage memberMessage = new MemberMessage();
						BeanUtils.copyProperties(message, memberMessage,
								new String[] { "pushTime" });
						memberMessage.setPushTime(messageInfo.getPushTime());
						memberMessage.setIsRead(0);
						boolean isSaveUserMsg = false;
						
						memberMessageService.save(memberMessage);
						isSaveUserMsg = true;

						if (isSaveUserMsg) {
							message.setId(memberMessage.getId());
							GeTuiClient geTuiClient = geTuiClientService
									.getClientId(message.getUid());
							MessageSendLog messageSendLog = new MessageSendLog();
							BeanUtils.copyProperties(memberMessage,
									messageSendLog, new String[] { "id" });
							if(geTuiClient!=null){
								PushResult result = geTuiPushService
										.pushMessageToSingle(message,geTuiClient.getAppType(), geTuiClient.getClientId());
								if (!result.isSuccess()) {
									logger.error("pushMessage:{}",
											JSONObject.toJSONString(result));
								}
								messageSendLog.setResult(result.getResult());
								messageSendLog.setTaskId(result.getTaskId());
								messageSendLog.setContentId(result.getContentId());
								messageSendLog.setResponse(result.getResponse());
								messageSendLog.setStatus(result.getStatus());
							}
							else{
								messageSendLog.setResult("CLIENT INFO NOT FOUND");
								messageSendLog.setTaskId("");
								messageSendLog.setContentId("");
								messageSendLog.setResponse("");
								messageSendLog.setStatus("");
							}
							messageSendLogService.saveEntity(messageSendLog);
						}

					}
					consumer.commitAsync();
				} catch (Exception e) {
					logger.error("saveUserMsg {}", e);
				}
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					logger.error("InterruptedException {}", e);
				}
			}
		}
	}
	// 创建一个固定线程池
    private final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            // 工作队列
            new SynchronousQueue<Runnable>(),
            // 线程池饱和处理策略
            new ThreadPoolExecutor.CallerRunsPolicy());
	private void handleLatestMessageInfo(final MessageInfo messageInfo){
		
		EXECUTOR_SERVICE.submit(new Runnable() {
			@Override
			public void run() {
				//某某客户发布需求
				//某某服务商响应需求
				//某某服务商和客户已合作
				//某某电工已接单
				//某某服务商发布社会工单（目前没有推送消息）
				
				MessageId messageId=MessageId.parse(messageInfo.getMsgId());
				DynamicMessageInfo dynamicMessageInfo=null;
				if(messageId.equals(MessageId.NEEDS_AUDIT_SUCCESS_CUSTOMER)){//客户需求审核通过
					String title=messageInfo.getParams().get("needs_name");
					String memberName=messageInfo.getParams().get("member_name");
					dynamicMessageInfo=new DynamicMessageInfo();
					dynamicMessageInfo.setMsgId(messageId.getValue());
					dynamicMessageInfo.setMemberType("客户");
					dynamicMessageInfo.setMemberName(memberName);
					dynamicMessageInfo.setTitle(title);
					pushDynamicMessageInfo(dynamicMessageInfo);
				}
				else if(messageId.equals(MessageId.ORDER_ACCEPT_SUCCESS)){//社会电工接单成功
					String title=messageInfo.getParams().get("name");
					String memberName=messageInfo.getParams().get("member_name");
					dynamicMessageInfo=new DynamicMessageInfo();
					dynamicMessageInfo.setMsgId(messageId.getValue());
					dynamicMessageInfo.setMemberType("社会电工");
					dynamicMessageInfo.setMemberName(memberName);
					dynamicMessageInfo.setTitle(title);
					pushDynamicMessageInfo(dynamicMessageInfo);
				}
				else if(messageId.equals(MessageId.SOCIAL_ORDER_PUBLISH_SUCCESS)){//服务商发布社会工单
					String title=messageInfo.getParams().get("title");
					String memberName=messageInfo.getParams().get("member_name");
					dynamicMessageInfo=new DynamicMessageInfo();
					dynamicMessageInfo.setMsgId(messageId.getValue());
					dynamicMessageInfo.setMemberType("服务商");
					dynamicMessageInfo.setMemberName(memberName);
					dynamicMessageInfo.setTitle(title);
					pushDynamicMessageInfo(dynamicMessageInfo);
				}
				else if(messageId.equals(MessageId.NEEDS_ORDER_PAYMENT_FACILITATOR)){//服务商响应需求并支付
					String title=messageInfo.getParams().get("needs_name");
					String memberName=messageInfo.getParams().get("member_name");
					dynamicMessageInfo=new DynamicMessageInfo();
					dynamicMessageInfo.setMsgId(messageId.getValue());
					dynamicMessageInfo.setMemberType("服务商");
					dynamicMessageInfo.setMemberName(memberName);
					dynamicMessageInfo.setTitle(title);
					pushDynamicMessageInfo(dynamicMessageInfo);
				}
				else if(messageId.equals(MessageId.NEEDS_ORDER_COOPERATION_FACILITATOR)){//服务商需求合作成功
					String title=messageInfo.getParams().get("needs_name");
					String memberName=messageInfo.getParams().get("member_name");
					dynamicMessageInfo=new DynamicMessageInfo();
					dynamicMessageInfo.setMsgId(messageId.getValue());
					dynamicMessageInfo.setMemberType("服务商");
					dynamicMessageInfo.setMemberName(memberName);
					dynamicMessageInfo.setTitle(title);
					pushDynamicMessageInfo(dynamicMessageInfo);
				}
				
				
				
			}
		});
	}
	private void pushDynamicMessageInfo(DynamicMessageInfo dynamicMessageInfo){
		jedisUtil.lpush(CacheKey.CACHE_KEY_LATEST_DYNAMIC_MESSAGE_LIST, JSON.toJSONString(dynamicMessageInfo));
		jedisUtil.ltrim(CacheKey.CACHE_KEY_LATEST_DYNAMIC_MESSAGE_LIST, 0, CacheKey.LATEST_DYNAMIC_MESSAGE_LIST_MAX_COUNT);
	}
	@Override
	protected void init() {
		try {
			consumer = new KafkaConsumer<>(kc.buildProperties());
		} catch (KafkaException e) {
			logger.error("kafka consumer load error:{}", e);
		}
	}

	@Value(value = "${msg.kafka.topic.name}")
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
