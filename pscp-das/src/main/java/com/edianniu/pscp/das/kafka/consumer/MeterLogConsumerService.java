package com.edianniu.pscp.das.kafka.consumer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.das.common.Constants;
import com.edianniu.pscp.das.meter.bean.MeterLogDo;
import com.edianniu.pscp.das.meter.bean.WarningBean;
import com.edianniu.pscp.das.meter.domain.CompanyMeter;
import com.edianniu.pscp.das.meter.log.DWLogger;
import com.edianniu.pscp.das.meter.service.MeterLogAnalysisService;
import com.edianniu.pscp.das.meter.service.MeterLogService;
import com.edianniu.pscp.das.meter.service.MeterService;
import com.edianniu.pscp.das.meter.service.impl.PushWarningAndMessageMethod;
import com.edianniu.pscp.das.util.DateUtils;
import com.edianniu.pscp.message.bean.MeterLogInfo;

/**
 * @author cyl
 */
@Service
@Repository("meterLogConsumerService")
public class MeterLogConsumerService{
	private static final Logger logger = LoggerFactory
			.getLogger(MeterLogConsumerService.class);
	private static final DWLogger dwLogger=new DWLogger();
	
	@Autowired
	private KafkaConsumerClient kafkaConsumerClient = null;
	private static final String METER_LOG_LIST="meter_log_list#";
	private static final String METER_LOG_TAG="meter_log_tag#";
	private static final Map<String,BlockingQueue<MeterLogDo>> queueMap=new ConcurrentHashMap<String, BlockingQueue<MeterLogDo>>();
	@Autowired
    private JedisUtil jedisUtil;
	@Autowired
	private MeterService meterService;
	@Autowired
	private MeterLogService meterLogService;
	@Autowired
	private MeterLogAnalysisService meterLogAnalysisService;
	@Autowired
	private PushWarningAndMessageMethod pushWarningAndMessage;

	public MeterLogConsumerService() {
	}
	
	private void putToQueue(MeterLogDo meterLogDo,String topicName,int partition){
		try {
			BlockingQueue<MeterLogDo> queue=queueMap.get(topicName+"#"+partition);
			queue.put(meterLogDo);
		} catch (InterruptedException e) {
			logger.error("put to queue",e);
		}
		
	}
	
    private void initQueue(){//系统停机后，加载未处理的先处理
    	logger.info("begin init queue");
    	String topicName=kafkaConsumerClient.getTopicName();
    	List<PartitionInfo> list=kafkaConsumerClient.getConsumer().partitionsFor(topicName);
    	for(PartitionInfo pi:list){
			final BlockingQueue<MeterLogDo> bq=new LinkedBlockingQueue<MeterLogDo>();
			queueMap.put(pi.topic()+"#"+pi.partition(), bq);
			//初始化未处理的数据
			Long count=jedisUtil.zcard(METER_LOG_LIST+topicName+"#"+pi.partition());
			if(count>0){
				Set<String> sets=jedisUtil.zrange(METER_LOG_LIST+topicName+"#"+pi.partition(), 0, count);
				for(String value:sets){
					if(StringUtils.isNotBlank(value)){
	        			MeterLogDo meterLogDo=JSON.parseObject(value, MeterLogDo.class);
	        			try {
							bq.put(meterLogDo);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	        		}
				}
			}
			/*Set<String> keys=jedisUtil.smembers(logKey+topicName+"#"+pi.partition());
        	for(String key:keys){//jedisLogUtil.mget(keys);,减少redis连接情况，提高性能
        		String value=jedisUtil.get(key);
        		if(StringUtils.isNotBlank(value)){
        			MeterLogDo meterLogDo=JSON.parseObject(value, MeterLogDo.class);
        			try {
						bq.put(meterLogDo);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
        	}*/
        	String threadName="queueThread:"+pi.topic()+"_"+pi.partition();
        	new Thread(new QueueThread(pi.topic(),pi.partition(),bq),threadName).start();
        	logger.info("启动数据分析线程:{}",threadName);
		}
    	logger.info("end init queue");
    }
    
	@PostConstruct
	public void listener() {
		initQueue();
		String threadName=kafkaConsumerClient.getTopicName()+" Consumer Thread";
		new Thread(new ConsumerThread(kafkaConsumerClient.getConsumer()),threadName).start();
	}
	
    class QueueThread implements Runnable{
    	private final int partition;
    	private final String topicName;
    	private final BlockingQueue<MeterLogDo> bq;
    	public QueueThread(String topicName,int partition,BlockingQueue<MeterLogDo> bq){
    		this.topicName=topicName;
    		this.partition=partition;
    		this.bq=bq;
    	}
		@Override
		public void run() {
			while(true){
				try {
					long queueSize=bq.size();
					String key=METER_LOG_LIST+topicName+"#"+partition;
					logger.info("{} queue size:{}",key,queueSize);
					if(queueSize>0){
						MeterLogDo meterLogDo=bq.take();
						if(meterLogDo!=null){
							long start=System.currentTimeMillis();
							if(meterLogAnalysisService.doAnalysis(meterLogDo)){
								jedisUtil.zremrangeByScore(key, meterLogDo.getScore(), meterLogDo.getScore());
							}
							String logStr=meterLogDo.getMeterId()+"#"+DateUtils.format(new Date(meterLogDo.getTime()),DateUtils.DATE_TIME_PATTERN);
							logger.info("doAnalysis log:{}, excute time= {} ms",logStr,(System.currentTimeMillis()-start));
						}
					} else{
						Thread.sleep(2000L);
					}
				} catch (Exception e) {
					logger.error(" 异常:{}",e);
				}
			}
		}
    }
 
	class ConsumerThread implements Runnable {
		KafkaConsumer<String, String> consumer;
		public ConsumerThread(KafkaConsumer<String, String> consumer) {
			this.consumer = consumer;
		}
		@Override
		public void run() {
			while(true){
				try {
					ConsumerRecords<String, String> records = consumer.poll(1000L);
					long start=System.currentTimeMillis();
					logger.info("meterLog handler pull size:{}",records.count());
					List<WarningBean> beans = new ArrayList<>();
					for (ConsumerRecord<String, String> record : records) {
						logger.info("topic={},partition = {},offset ={}, key = {},value length={}",
								record.topic(),record.partition(), record.offset(), record.key(),record.value().length());
						long start1=System.currentTimeMillis();
						//读取采集信息，匹配客户，匹配服务商，匹配其他基础信息
						MeterLogInfo meterLog=JSON.parseObject(record.value(), MeterLogInfo.class);
						//根据仪表ID读取仪表关联的服务商和客户信息，做缓存吧;
						String meterId=record.key();
						if(!meterLog.getErrors().isEmpty()&&meterLog.getErrors().size()==meterLog.getData().size()){//有错误输出
							logger.error("meterLog found errors:{}",meterId);
							dwLogger.log("[meterLog]",record.value());
							continue;
						}
						if(!jedisUtil.exists(METER_LOG_TAG+meterLog.getMeterId()+"#"+meterLog.getReportTime().getTime())){
							meterLogService.saveMeterLogToDb(meterLog);
							jedisUtil.setnx(METER_LOG_TAG+meterLog.getMeterId()+"#"+meterLog.getReportTime().getTime(), "1", 24*60*1000);
						}
						CompanyMeter companyMeter=meterService.getCompanyMeterById(meterId);
						if(companyMeter==null){
							logger.error("company meter not found:{}",meterId);
							dwLogger.log("[companyMeter]",record.value());
							continue;
						}
						Long companyId=companyMeter.getCompanyId();//客户ID
						String companyName=companyMeter.getCompanyName();//客户名称
						int multilpe=companyMeter.getMultiple();//倍率
						Integer meterType=companyMeter.getMeterType();//仪表类型
						Integer referRoom=companyMeter.getReferRoom();//是否绑定配电房
						String subTermCode=meterLog.getSubTermCode();//分项编码
						//仪表实时数据
						MeterLogDo meterLogDo=new MeterLogDo();
						BeanUtils.populate(meterLogDo, meterLog.getData());
						
						//计算三相电压是否正常
						if(meterLogDo.getUa()<companyMeter.getuLower()){
							meterLogDo.setUaStatus(Constants.U_STATUS_LOWER);
						} else if(meterLogDo.getUa()>companyMeter.getuHigh()){
							meterLogDo.setUaStatus(Constants.U_STATUS_HEIGHT);
						} else{
							meterLogDo.setUaStatus(Constants.U_STATUS_NORMAL);
						}
						if(meterLogDo.getUb()<companyMeter.getuLower()){
							meterLogDo.setUbStatus(Constants.U_STATUS_LOWER);
						} else if(meterLogDo.getUb()>companyMeter.getuHigh()){
							meterLogDo.setUbStatus(Constants.U_STATUS_HEIGHT);
						} else{
							meterLogDo.setUbStatus(Constants.U_STATUS_NORMAL);
						}
						if(meterLogDo.getUc()<companyMeter.getuLower()){
							meterLogDo.setUcStatus(Constants.U_STATUS_LOWER);
						} else if(meterLogDo.getUc()>companyMeter.getuHigh()){
							meterLogDo.setUcStatus(Constants.U_STATUS_HEIGHT);
						} else{
							meterLogDo.setUcStatus(Constants.U_STATUS_NORMAL);
						}
						if(meterLogDo.getUaStatus()==Constants.U_STATUS_NORMAL&&
						   meterLogDo.getUbStatus()==Constants.U_STATUS_NORMAL&&
						   meterLogDo.getUcStatus()==Constants.U_STATUS_NORMAL){
						   meterLogDo.setuStatus(Constants.U_STATUS_NORMAL);
						} else if(meterLogDo.getUaStatus()==Constants.U_STATUS_HEIGHT&&
								   meterLogDo.getUbStatus()==Constants.U_STATUS_HEIGHT&&
								   meterLogDo.getUcStatus()==Constants.U_STATUS_HEIGHT){
						    meterLogDo.setuStatus(Constants.U_STATUS_HEIGHT);
						} else if(meterLogDo.getUaStatus()==Constants.U_STATUS_LOWER&&
								   meterLogDo.getUbStatus()==Constants.U_STATUS_LOWER&&
								   meterLogDo.getUcStatus()==Constants.U_STATUS_LOWER){
							meterLogDo.setuStatus(Constants.U_STATUS_LOWER);
						} else{
							meterLogDo.setuStatus(Constants.U_STATUS_ERROR);
						}
						
						//计算三相电量的平衡度
						Double ia=meterLogDo.getIa()*multilpe;
						Double ib=meterLogDo.getIb()*multilpe;
						Double ic=meterLogDo.getIc()*multilpe;
						Double iavg=(ia+ib+ic)/3;
						Double iadiff=ia-iavg;
						Double ibdiff=ib-iavg;
						Double icdiff=ic-iavg;
						List<Double> idiffList=new ArrayList<Double>();
						idiffList.add(Math.abs(iadiff));
						idiffList.add(Math.abs(ibdiff));
						idiffList.add(Math.abs(icdiff));
						Double imax=idiffList.stream().max(new Comparator<Double>(){
							@Override
							public int compare(Double o1, Double o2) {
								return o1.compareTo(o2);
							}
						}).get();
						meterLogDo.setiUnbalanceDegree(imax/iavg);
						meterLogDo.setIaUnbalanceDegree(Math.abs(iadiff/iavg));
						meterLogDo.setIbUnbalanceDegree(Math.abs(ibdiff/iavg));
						meterLogDo.setIcUnbalanceDegree(Math.abs(icdiff/iavg));
						meterLogDo.setMeterId(meterLog.getMeterId());
						meterLogDo.setMeterType(meterType);
						meterLogDo.setReferRoom(referRoom);
						meterLogDo.setCompanyId(companyId);
						meterLogDo.setCompanyName(companyName);
						meterLogDo.setSubTermCode(subTermCode);
						meterLogDo.setMultilpe(multilpe);
						meterLogDo.setReportTime(meterLog.getReportTime());
						meterLogDo.setScore(System.nanoTime());
						jedisUtil.zadd(METER_LOG_LIST+record.topic()+"#"+record.partition(), meterLogDo.getScore(), JSON.toJSONString(meterLogDo));
						//jedisUtil.sadd(logKey+record.topic()+"#"+record.partition(), meterLogDo.getId());
						//jedisUtil.set(meterLogDo.getId(), JSON.toJSONString(meterLogDo));
						//jedisUtil.z
						putToQueue(meterLogDo,record.topic(),record.partition());
						beans.add(new WarningBean(meterLogDo, companyMeter));
						logger.info("meterLog meterId:{},logDate:{},consumer total time: {} ms:",meterLog.getMeterId(),DateUtils.format(meterLog.getReportTime(),DateUtils.DATE_TIME_PATTERN),(System.currentTimeMillis()-start1));
					}
					logger.info("meterLog excute total time:"+(System.currentTimeMillis()-start)+" ms");
					if(records.count()>0){
						consumer.commitAsync();
					}
					/** 判断电压、功率因数、负荷、电流是否存在异常，添加、处理告警 ，给客户服务商推送信息**/
					for (WarningBean bean : beans) {
						pushWarningAndMessage.pushWarningAndMessage(bean.getMeterLogDo(), bean.getCompanyMeter());
					}
				}
				/*catch (InterruptedException e) {
					logger.error("InterruptedException {}", e);
                }*/
				catch(Exception e){
					logger.error("Exception {}", e);
				}
			}
		}
	}
}
