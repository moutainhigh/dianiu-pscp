package com.edianniu.pscp.test.access;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import stc.skymobi.cache.redis.JedisUtil;
import stc.skymobi.cache.redis.MasterSlavePool;
import stc.skymobi.util.DefaultNumberCodecs;
import stc.skymobi.util.NumberCodec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.edianniu.pscp.test.access.bean.common.Sequence;
import com.edianniu.pscp.test.access.bean.netdau.AttributeDo;
import com.edianniu.pscp.test.access.bean.netdau.BeatHeartDo;
import com.edianniu.pscp.test.access.bean.netdau.CommonDo;
import com.edianniu.pscp.test.access.bean.netdau.ControlAckInfoDo;
import com.edianniu.pscp.test.access.bean.netdau.ControlDo;
import com.edianniu.pscp.test.access.bean.netdau.DefaultAttrDo;
import com.edianniu.pscp.test.access.bean.netdau.DeviceDo;
import com.edianniu.pscp.test.access.bean.netdau.FunctionDo;
import com.edianniu.pscp.test.access.bean.netdau.IdValidateDo;
import com.edianniu.pscp.test.access.bean.netdau.InstructionAckDo;
import com.edianniu.pscp.test.access.bean.netdau.MeterDo;
import com.edianniu.pscp.test.access.bean.netdau.NetDauBeatHeartRequest;
import com.edianniu.pscp.test.access.bean.netdau.NetDauBeatHeartResponse;
import com.edianniu.pscp.test.access.bean.netdau.NetDauCommonRequest;
import com.edianniu.pscp.test.access.bean.netdau.NetDauControlRequest;
import com.edianniu.pscp.test.access.bean.netdau.NetDauControlResponse;
import com.edianniu.pscp.test.access.bean.netdau.NetDauDeviceRequest;
import com.edianniu.pscp.test.access.bean.netdau.NetDauDeviceResponse;
import com.edianniu.pscp.test.access.bean.netdau.NetDauMd5Request;
import com.edianniu.pscp.test.access.bean.netdau.NetDauMd5Response;
import com.edianniu.pscp.test.access.bean.netdau.NetDauRegisterRequest;
import com.edianniu.pscp.test.access.bean.netdau.NetDauReportRequest;
import com.edianniu.pscp.test.access.bean.netdau.ReportDo;
import com.edianniu.pscp.test.access.codec.AESCoder;
import com.edianniu.pscp.test.access.codec.CRCUtil;
import com.edianniu.pscp.test.access.codec.HexUtil;
import com.edianniu.pscp.test.access.codec.NetDauHeader;
import com.edianniu.pscp.test.util.DateUtils;
import com.edianniu.pscp.test.util.Md5Utils;
import com.edianniu.pscp.test.util.PropertiesUtil;
import com.edianniu.pscp.test.util.XmlToJsonUtils;

/**
 * 
 */

/**
 * @author cyl
 *
 */
public class NetDauTcpClient {
	private Logger logger = LoggerFactory.getLogger(NetDauTcpClient.class);
	private String ip = "111.1.17.197";
	private int port = 9002;
	private String redisIp = "192.168.1.251";
	private int redisPort = 6379;
	private String buildingId = "330100A088";
	private String gatewayId = "01";
	private DataOutputStream out;
	private DataInputStream in;
	private Socket st = null;
	private volatile boolean isRegister = false;// 是否已注册
	private volatile boolean isAuth = false;// 认证是否成功
	private volatile boolean isRunning = false;// 服务是否启动
	private volatile long lastReadTime = 0L;// 最后读取时间
	private volatile int reportDeviceStatus = 0;// 设备信息是否上报状态0 未上报,1上报中,2已上报
	private static final String aes_iv_str = HexUtil
			.hexToStr("0102030405060708090a0b0c0d0e0f10");
	private static final String aes_secretkey_str = HexUtil
			.hexToStr("0102030405060708090a0b0c0d0e0f10");
	private static final Map<String, NetDauHeader> hMap = new ConcurrentHashMap();
	private static final NumberCodec NUMBERCODEC = DefaultNumberCodecs
			.getLittleEndianNumberCodec();
	private GateWayE gateWayE;
	private int logReporterTime = 1000;
	private String gateWayKey;
	JedisUtil jedisUtil = null;

	public NetDauTcpClient(String ip, int port, String redisIp, int redisPort,
			GateWayE gateWayE) {
		this.ip = ip;
		this.port = port;
		this.redisIp = redisIp;
		this.redisPort = redisPort;
		this.buildingId = gateWayE.getBuildingId();
		this.gatewayId = gateWayE.getGatewayId();
		this.gateWayE = gateWayE;
		this.gateWayKey = ("gateWayE#" + this.buildingId + this.gatewayId);
		this.logReporterTime = gateWayE.getTaskPeriod();
	}

	private void initFromRedis() throws Exception {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(120);
		jedisPoolConfig.setMaxIdle(30000);
		jedisPoolConfig.setMaxWaitMillis(10000L);
		jedisPoolConfig.setTestOnBorrow(false);
		MasterSlavePool salvePool = new MasterSlavePool(jedisPoolConfig,
				this.redisIp, this.redisPort, 5000);
		salvePool.setRedisName("netdaucache");
		salvePool.initPool();
		this.jedisUtil = new JedisUtil();
		this.jedisUtil.setJedisPool(salvePool);
		String key = this.gateWayKey;
		String value = this.jedisUtil.get(key);
		if (StringUtils.isBlank(value)) {
			value = JSON.toJSONString(this.gateWayE);
			this.jedisUtil.set(key, value);
		} else {
			GateWayE gateWayE1 = (GateWayE) JSON.parseObject(value,
					GateWayE.class);
			for (MeterE meterE : this.gateWayE.getMeterList()) {
				boolean isExist = false;
				for (MeterE meterE1 : gateWayE1.getMeterList()) {
					if (meterE.getId().equals(meterE1.getId())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					gateWayE1.getMeterList().add(meterE);
				}
			}
			this.gateWayE = gateWayE1;
		}
	}
	private void updateConfigToFile(){
		try {
			File file=new File("config.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			FileUtils.writeStringToFile(file, JSON.toJSONString(gateWayE));
		} catch (Exception e) {
			logger.error("updateConfigToFile:{}",e);
		}
	}
	private void initConfigFromFile() throws IOException{
		File file=new File("config.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		String content=FileUtils.readFileToString(file);
		if(StringUtils.isBlank(content)){
			FileUtils.writeStringToFile(file, JSON.toJSONString(gateWayE));
		}
		else{
			GateWayE gateWayE1 = (GateWayE) JSON.parseObject(content,
					GateWayE.class);
			gateWayE1.setPeriod(gateWayE.getPeriod());
			for (MeterE meterE : this.gateWayE.getMeterList()) {
				boolean isExist = false;
				for (MeterE meterE1 : gateWayE1.getMeterList()) {
					if (meterE.getId().equals(meterE1.getId())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					gateWayE1.getMeterList().add(meterE);
				}
			}
			this.gateWayE = gateWayE1;
		}
	}
	public void start() throws UnknownHostException, IOException {
		try {
			initConfigFromFile();
		} catch (Exception e) {
			this.logger.error("initConfigFromFile:{}", e);
		}
		this.st = new Socket(this.ip, this.port);
		if (this.st != null) {
			this.logger.info("connect to iot server success!");
		} else {
			this.logger.error("connect to iot server fail!");
		}
		this.st.setKeepAlive(true);
		this.out = new DataOutputStream(this.st.getOutputStream());
		this.in = new DataInputStream(this.st.getInputStream());
		this.isRunning = true;
		this.isRegister = false;
		this.isAuth = false;
		this.reportDeviceStatus = 0;

		System.out.println("Tcp client is start...");
		Receiver receiver = new Receiver();
		new Thread(receiver).start();
		Sender sender = new Sender();
		new Thread(sender).start();
	}

	public void restart() {
		boolean rs = false;
		try {
			this.logger.info("try to reconnect to iot server success!");
			stop();
			start();
			this.logger.info("reconnect to iot server success!");
			rs = true;
		} catch (Exception e) {
			this.logger.error("reconnect to server error:{}", e);
		}
		if (!rs) {
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				this.logger.error(" sleap :{}", e);
			}
			restart();
		}
	}

	public void stop() throws InterruptedException {
		this.isRunning = false;
		this.isRegister = false;
		this.isAuth = false;
		this.reportDeviceStatus = 0;
		this.jedisUtil = null;
		Thread.sleep(1000L);
		System.out.println("Tcp client is stop...");
		try {
			if (this.out != null) {
				this.out.close();
				this.out = null;
			}
			if (this.out != null) {
				this.in.close();
				this.in = null;
			}
			if (this.st != null) {
				this.st.close();
				this.st = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(Object request) {
		String xmlContent = XmlToJsonUtils
				.getXmlFromJSON(JSON
						.toJSONString(
								request,
								new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect }));

		logger.info("发送的消息:{}", xmlContent);
		try {
			int contentTag = Sequence.get();
			byte[] contentTagBytes = NUMBERCODEC.int2Bytes(contentTag, 4);

			byte[] contentBytes = AESCoder.encrypt(xmlContent,
					aes_secretkey_str, aes_iv_str);

			int contentLength = contentTagBytes.length + contentBytes.length;
			byte[] validDataBytes = new byte[contentLength];
			System.arraycopy(contentTagBytes, 0, validDataBytes, 0,
					contentTagBytes.length);

			System.arraycopy(contentBytes, 0, validDataBytes,
					contentTagBytes.length, contentBytes.length);

			int crc = CRCUtil.getCrc16(validDataBytes, validDataBytes.length);
			byte[] crcBytes = NUMBERCODEC.int2Bytes(crc, 2);

			NetDauHeader header = new NetDauHeader();
			header.setContentLength(contentLength);
			header.setDataCrc(crc);
			ByteBuf bytes = Unpooled.buffer(header.getLength());
			bytes.writeBytes(header.toBytes());
			bytes.writeBytes(validDataBytes);
			bytes.writeBytes(crcBytes);
			bytes.writeBytes(header.toEndBytes());
			byte[] outBytes = new byte[header.getLength()];
			bytes.readBytes(outBytes);
			this.out.write(outBytes);
			this.out.flush();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public NetDauTcpClient() {
	}

	public void demo() {
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		if ((args == null) || (args.length <= 0)) {
			System.exit(-1);
			return;
		}
		String configPath = args[0];
		PropertiesUtil config = new PropertiesUtil(configPath);
		String accessIp = config.getProperty("access.ip");
		String accessPort = config.getProperty("access.port");
		String redisIp = config.getProperty("redis.ip");
		String redisPort = config.getProperty("redis.port");
		String buildingId = config.getProperty("building.id");
		String gatewayId = config.getProperty("gateway.id");
		String meterList = config.getProperty("meter.list");
		String period = config.getProperty("upload.period");
		String taskPeriod = config.getProperty("upload.task.period");
		String startTime = config.getProperty("upload.startTime");
		
		
		String chargeIntervalTimes = config
				.getProperty("charge.interval.times");
		GateWayE gateWayE = new GateWayE();
		gateWayE.setBuildingId(buildingId);
		gateWayE.setGatewayId(gatewayId);

		String[] meters = StringUtils.split(meterList, ",");
		gateWayE.setMeterList(getMeterList(meters));
		gateWayE.setStartTime(DateUtils.parse(startTime, "yyyyMMddHHmmss"));
		gateWayE.setPeriod(Integer.parseInt(period));
		gateWayE.setTaskPeriod(Integer.parseInt(taskPeriod));//毫秒
		gateWayE.setNextTime(null);
		gateWayE.setChargeInterValTimes(chargeIntervalTimes);
		NetDauTcpClient rpcClient = new NetDauTcpClient(accessIp,
				Integer.parseInt(accessPort), redisIp,
				Integer.parseInt(redisPort), gateWayE);
		rpcClient.start();
	}
	public static List<MeterE> getMeterList(String[] meterIds) {
		List<MeterE> meterList = new ArrayList();
		for (String meterId : meterIds) {
			MeterE me1 = new MeterE();
			String[] strs = StringUtils.split(meterId, ":");
			if (strs.length >= 2) {
				me1.setId(strs[0]);
				me1.setSubTermCode(strs[1]);
			}
			String type = "";
			if (strs.length >= 3) {
				type = strs[2];
				me1.setType(type);
			}
			if (StringUtils.isBlank(type)) {
				me1.getData().put("01", Double.valueOf(2000.0D));//有功功率
				me1.getData().put("02", Double.valueOf(1000.0D));//A相有功功率
				me1.getData().put("03", Double.valueOf(1000.0D));//B相有功功率
				me1.getData().put("04", Double.valueOf(1000.0D));//C相有功功率
				me1.getData().put("21", Double.valueOf(220.0D));//A电压
				me1.getData().put("22", Double.valueOf(220.0D));//B电压
				me1.getData().put("23", Double.valueOf(220.0D));//C电压
				me1.getData().put("24", Double.valueOf(30.0D));//A电流
				me1.getData().put("25", Double.valueOf(30.0D));//B电流
				me1.getData().put("26", Double.valueOf(30.0D));//C电流
				me1.getData().put("27", Double.valueOf(100.0D));//正向总有功电度
				me1.getData().put("28", Double.valueOf(10.0D));//正向尖有功电度
				me1.getData().put("29", Double.valueOf(40.0D));//正向峰有功电度
				me1.getData().put("30", Double.valueOf(30.0D));//正向平有功电度
				me1.getData().put("31", Double.valueOf(20.0D));//正向谷有功电度
				me1.getData().put("37", Double.valueOf(10.0D));//反向总无功电度
				me1.getData().put("38", Double.valueOf(10.0D));//反向尖无功电度
			} else {
				if (type.equals("dr")) {
					me1.getData().put("01", Double.valueOf(3800.0D));
					me1.getData().put("02", Double.valueOf(3800.0D));
					me1.getData().put("03", Double.valueOf(3800.0D));
					me1.getData().put("04", Double.valueOf(3800.0D));
					me1.getData().put("21", Double.valueOf(380.0D));
					me1.getData().put("22", Double.valueOf(380.0D));
					me1.getData().put("23", Double.valueOf(380.0D));
					me1.getData().put("24", Double.valueOf(10.0D));
					me1.getData().put("25", Double.valueOf(10.0D));
					me1.getData().put("26", Double.valueOf(10.0D));
					me1.getData().put("27", Double.valueOf(100.0D));
					me1.getData().put("28", Double.valueOf(10.0D));
					me1.getData().put("29", Double.valueOf(40.0D));
					me1.getData().put("30", Double.valueOf(30.0D));
					me1.getData().put("31", Double.valueOf(20.0D));
					me1.getData().put("37", Double.valueOf(10.0D));
					me1.getData().put("38", Double.valueOf(10.0D));
				}
				if (type.equals("bd")) {
					me1.getData().put("01", Double.valueOf(2000.0D));
					me1.getData().put("02", Double.valueOf(1000.0D));
					me1.getData().put("03", Double.valueOf(1000.0D));
					me1.getData().put("04", Double.valueOf(1000.0D));
					me1.getData().put("21", Double.valueOf(200.0D));
					me1.getData().put("22", Double.valueOf(200.0D));
					me1.getData().put("23", Double.valueOf(200.0D));
					me1.getData().put("24", Double.valueOf(10.0D));
					me1.getData().put("25", Double.valueOf(10.0D));
					me1.getData().put("26", Double.valueOf(10.0D));
					me1.getData().put("27", Double.valueOf(100.0D));
					me1.getData().put("28", Double.valueOf(10.0D));
					me1.getData().put("29", Double.valueOf(40.0D));
					me1.getData().put("30", Double.valueOf(30.0D));
					me1.getData().put("31", Double.valueOf(20.0D));
					me1.getData().put("37", Double.valueOf(10.0D));
					me1.getData().put("38", Double.valueOf(10.0D));
				}
				if (type.equals("device")) {
					me1.getData().put("01", Double.valueOf(1000.0D));
					me1.getData().put("02", Double.valueOf(1000.0D));
					me1.getData().put("03", Double.valueOf(1000.0D));
					me1.getData().put("04", Double.valueOf(1000.0D));
					me1.getData().put("21", Double.valueOf(200.0D));
					me1.getData().put("22", Double.valueOf(200.0D));
					me1.getData().put("23", Double.valueOf(200.0D));
					me1.getData().put("24", Double.valueOf(10.0D));
					me1.getData().put("25", Double.valueOf(10.0D));
					me1.getData().put("26", Double.valueOf(10.0D));
					me1.getData().put("27", Double.valueOf(100.0D));
					me1.getData().put("28", Double.valueOf(10.0D));
					me1.getData().put("29", Double.valueOf(40.0D));
					me1.getData().put("30", Double.valueOf(30.0D));
					me1.getData().put("31", Double.valueOf(20.0D));
					me1.getData().put("37", Double.valueOf(10.0D));
					me1.getData().put("38", Double.valueOf(10.0D));
				}
			}
			meterList.add(me1);
		}
		return meterList;
	}

	class Sender implements Runnable {
		Sender() {
		}

		public void run() {
			while (isRunning) {
				if (!isRegister) {
					NetDauRegisterRequest registerRequest = new NetDauRegisterRequest();
					CommonDo common = new CommonDo();
					common.setBuilding_id(buildingId);
					common.setGateway_id(gatewayId);
					common.setType("request");
					registerRequest.setCommon(common);
					IdValidateDo id_validate = new IdValidateDo();
					DefaultAttrDo attr = new DefaultAttrDo();
					attr.setOperation("request");
					id_validate.setAttr(attr);
					registerRequest.setId_validate(id_validate);

					sendMessage(registerRequest);
					isRegister = true;
				}
				if (isAuth) {
				}
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class BeatHearter implements Runnable {
		BeatHearter() {
		}

		public void run() {
			while ((isRunning) && (isAuth)) {
				if (reportDeviceStatus == 2) {
					System.out.println("给服务端发送心跳包，检查服务端是否正常...");
					NetDauBeatHeartRequest request = new NetDauBeatHeartRequest();
					CommonDo common = new CommonDo();
					common.setBuilding_id(buildingId);
					common.setGateway_id(gatewayId);
					common.setType("notify");
					request.setCommon(common);
					BeatHeartDo heart_beat = new BeatHeartDo();
					heart_beat.setNotify("master");
					DefaultAttrDo attr = new DefaultAttrDo();
					attr.setOperation("notify");
					heart_beat.setAttr(attr);
					request.setHeart_beat(heart_beat);
					sendMessage(request);
					try {
						Thread.sleep(5000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	class LogReporter implements Runnable {
		LogReporter() {
		}

		public void run() {
			while ((isRunning) && (isAuth)) {
				logger.info("Tcp client LogReporter is listen...");
				if (reportDeviceStatus == 2) {
					try {
						logger.info("日志上传开始");
						NetDauReportRequest request = getNetDauReportRequest();
						sendMessage(request);
						logger.info("配置写入文件：");
						updateConfigToFile();
						//jedisUtil.set(gateWayKey, JSON.toJSONString(gateWayE));
						logger.info("日志上传结束:time=[" + request.getData().getTime()
								+ "],[" + request.getCommon().getBuilding_id()
								+ "," + request.getCommon().getGateway_id()
								+ "]");
						Thread.sleep(logReporterTime);
					} catch (Exception e) {
						logger.error("logReporter:{}", e.getMessage());
					}
				}
			}
		}
	}

	private NetDauReportRequest getNetDauReportRequest() {
		NetDauReportRequest request = new NetDauReportRequest();
		CommonDo common = new CommonDo();
		common.setBuilding_id(this.buildingId);
		common.setGateway_id(this.gatewayId);
		common.setType("report");
		request.setCommon(common);
		ReportDo reportDo = new ReportDo();
		reportDo.setCurrent("1");

		reportDo.setParser("yes");
		reportDo.setSequence("1");
		if (this.gateWayE.getStartTime() == null) {
			reportDo.setTime(DateUtils.format(new Date(), "yyyyMMddHHmmss"));
		} else {
			Date nextTime = this.gateWayE.getNextTime();
			if (nextTime != null) {
				reportDo.setTime(DateUtils.format(nextTime, "yyyyMMddHHmmss"));
				this.gateWayE.setNextTime(DateUtils.parse(
						DateUtils.getDateOffsetMinute(nextTime,
								this.gateWayE.getPeriod(), "yyyyMMddHHmmss"),
						"yyyyMMddHHmmss"));
			} else {
				reportDo.setTime(DateUtils.format(this.gateWayE.getStartTime(),
						"yyyyMMddHHmmss"));
				this.gateWayE.setNextTime(DateUtils.parse(
						DateUtils.getDateOffsetMinute(
								this.gateWayE.getStartTime(),
								this.gateWayE.getPeriod(), "yyyyMMddHHmmss"),
						"yyyyMMddHHmmss"));
			}
		}
		List<MeterDo> meters = getMeters(DateUtils.parse(reportDo.getTime(),
				"yyyyMMddHHmmss"));
		reportDo.setMeter(meters);
		reportDo.setTotal("" + meters.size());
		AttributeDo attr = new AttributeDo();
		attr.setOperation("report");
		reportDo.setAttr(attr);
		request.setData(reportDo);
		return request;
	}

	private List<MeterDo> getMeters(Date time) {
		List<MeterDo> meters = new ArrayList();
		for (MeterE me : this.gateWayE.getMeterList()) {
			meters.add(getMeter(me, time));
		}
		return meters;
	}

	private MeterDo getMeter(MeterE me, Date time) {
		MeterDo meter = new MeterDo();
		AttributeDo attr = new AttributeDo();
		attr.setAddr("000000000006");
		attr.setCom("1");
		attr.setId(me.getId());
		attr.setName(me.getId() + "号变压器");
		attr.setTp("1");
		meter.setAttr(attr);
		meter.setFunction(getFunctions(me, time));
		return meter;
	}

	private FunctionDo getSuccessFunction(String id, String coding, String value) {
		return getFunction(id, coding, value, "192");
	}

	private FunctionDo getErrorFunction(String id, String coding, String value) {
		return getFunction(id, coding, value, "192");
	}

	private FunctionDo getFunction(String id, String coding, String value,
			String error) {
		FunctionDo functionDo = new FunctionDo();
		AttributeDo funAttr = new AttributeDo();
		funAttr.setCoding(coding);
		funAttr.setId(id);
		funAttr.setError(error);
		functionDo.setAttr(funAttr);
		functionDo.setValue(value);
		return functionDo;
	}

	private List<FunctionDo> getFunctions(MeterE me, Date time) {
		List<FunctionDo> functions = new ArrayList();
		String codeing = me.getSubTermCode();

		int prInt = 300;
		int urInt = 100;
		int irInt = 10;
		Double nocharge = Math.random()/5;
		Double charge = nocharge+Math.random()/5;
		if (me.getType().equals("dr")) {
			nocharge = nocharge*this.gateWayE.getPeriod();
			charge = charge*this.gateWayE.getPeriod();
		} else if (me.getType().equals("bd")) {
			nocharge = nocharge*this.gateWayE.getPeriod()*3;
			charge = charge*this.gateWayE.getPeriod()*3;
		} else if (me.getType().equals("device")) {
			//nocharge = nocharge*this.gateWayE.getPeriod()/10;
			//charge = charge*this.gateWayE.getPeriod()/10;
		}
		int r1 = new Random().nextInt(prInt);
		Double p = Double.valueOf(0.0D);
		if (r1 % 2 == 0) {
			p = Double.valueOf(((Double) me.getData().get("01")).doubleValue()
					+ Math.random() - r1);
		} else {
			p = Double.valueOf(((Double) me.getData().get("01")).doubleValue()
					+ Math.random() + r1);
		}
		int r2 = new Random().nextInt(prInt);
		Double pa = Double.valueOf(0.0D);
		if (r2 % 2 == 0) {
			pa = Double.valueOf(((Double) me.getData().get("02")).doubleValue()
					+ Math.random() - r2);
		} else {
			pa = Double.valueOf(((Double) me.getData().get("02")).doubleValue()
					+ Math.random() + r2);
		}
		int r3 = new Random().nextInt(prInt);
		Double pb = Double.valueOf(0.0D);
		if (r3 % 2 == 0) {
			pb = Double.valueOf(((Double) me.getData().get("03")).doubleValue()
					+ Math.random() - r3);
		} else {
			pb = Double.valueOf(((Double) me.getData().get("03")).doubleValue()
					+ Math.random() + r3);
		}
		int r4 = new Random().nextInt(prInt);
		Double pc = Double.valueOf(0.0D);
		if (r4 % 2 == 0) {
			pc = Double.valueOf(((Double) me.getData().get("03")).doubleValue()
					+ Math.random() - r4);
		} else {
			pc = Double.valueOf(((Double) me.getData().get("03")).doubleValue()
					+ Math.random() + r4);
		}
		functions.add(getSuccessFunction("01", codeing, String.valueOf(p)));
		functions.add(getSuccessFunction("02", codeing, String.valueOf(pa)));
		functions.add(getSuccessFunction("03", codeing, String.valueOf(pb)));
		functions.add(getSuccessFunction("04", codeing, String.valueOf(pc)));

		int r21 = new Random().nextInt(urInt);
		Double ua = Double.valueOf(0.0D);
		if (r21 % 2 == 0) {
			ua = Double.valueOf(((Double) me.getData().get("21")).doubleValue()
					+ Math.random() - r21);
		} else {
			ua = Double.valueOf(((Double) me.getData().get("21")).doubleValue()
					+ Math.random() + r21);
		}
		int r22 = new Random().nextInt(urInt);
		Double ub = Double.valueOf(0.0D);
		if (r22 % 2 == 0) {
			ub = Double.valueOf(((Double) me.getData().get("22")).doubleValue()
					+ Math.random() - r22);
		} else {
			ub = Double.valueOf(((Double) me.getData().get("22")).doubleValue()
					+ Math.random() + r22);
		}
		int r23 = new Random().nextInt(urInt);
		Double uc = Double.valueOf(0.0D);
		if (r23 % 2 == 0) {
			uc = Double.valueOf(((Double) me.getData().get("23")).doubleValue()
					+ Math.random() - r23);
		} else {
			uc = Double.valueOf(((Double) me.getData().get("23")).doubleValue()
					+ Math.random() + r23);
		}
		functions.add(getSuccessFunction("21", codeing, String.valueOf(ua)));
		functions.add(getSuccessFunction("22", codeing, String.valueOf(ub)));
		functions.add(getSuccessFunction("23", codeing, String.valueOf(uc)));

		int r24 = new Random().nextInt(irInt);
		Double ia = Double.valueOf(0.0D);
		if (r24 % 2 == 0) {
			ia = Double.valueOf(((Double) me.getData().get("24")).doubleValue()
					+ Math.random() - r24);
		} else {
			ia = Double.valueOf(((Double) me.getData().get("24")).doubleValue()
					+ Math.random() + r24);
		}
		int r25 = new Random().nextInt(irInt);
		Double ib = Double.valueOf(0.0D);
		if (r25 % 2 == 0) {
			ib = Double.valueOf(((Double) me.getData().get("25")).doubleValue()
					+ Math.random() - r25);
		} else {
			ib = Double.valueOf(((Double) me.getData().get("25")).doubleValue()
					+ Math.random() + r25);
		}
		int r26 = new Random().nextInt(irInt);
		Double ic = Double.valueOf(0.0D);
		if (r26 % 2 == 0) {
			ic = Double.valueOf(((Double) me.getData().get("26")).doubleValue()
					+ Math.random() - r26);
		} else {
			ic = Double.valueOf(((Double) me.getData().get("26")).doubleValue()
					+ Math.random() + r26);
		}
		functions.add(getSuccessFunction("24", codeing, String.valueOf(ia)));
		functions.add(getSuccessFunction("25", codeing, String.valueOf(ib)));
		functions.add(getSuccessFunction("26", codeing, String.valueOf(ic)));
		Integer type = Integer.valueOf(this.gateWayE == null ? 0
				: this.gateWayE.getChargeType(time).intValue());

		me.getData().put(
				"27",
				Double.valueOf(((Double) me.getData().get("27")).doubleValue()
						+ charge.doubleValue()));
		if (type.intValue() == 1) {
			me.getData().put(
					"28",
					Double.valueOf(((Double) me.getData().get("28"))
							.doubleValue() + charge.doubleValue()));
		} else if (type.intValue() == 2) {
			me.getData().put(
					"29",
					Double.valueOf(((Double) me.getData().get("29"))
							.doubleValue() + charge.doubleValue()));
		} else if (type.intValue() == 3) {
			me.getData().put(
					"30",
					Double.valueOf(((Double) me.getData().get("30"))
							.doubleValue() + charge.doubleValue()));
		} else if (type.intValue() == 4) {
			me.getData().put(
					"31",
					Double.valueOf(((Double) me.getData().get("31"))
							.doubleValue() + charge.doubleValue()));
		}
		if (me.getData().get("37") == null) {
			me.getData().put("37", Double.valueOf(300.05000000000001D));
		}
		me.getData().put(
				"37",
				Double.valueOf(((Double) me.getData().get("37")).doubleValue()
						+ nocharge.doubleValue()));

		me.getData().put(
				"38",
				Double.valueOf(((Double) me.getData().get("38")).doubleValue()
						+ nocharge.doubleValue()));

		functions.add(getSuccessFunction("05", codeing, "200.01"));
		functions.add(getSuccessFunction("06", codeing, "200.01"));
		functions.add(getSuccessFunction("07", codeing, "200.01"));
		functions.add(getSuccessFunction("08", codeing, "200.01"));
		functions.add(getSuccessFunction("09", codeing, "200.01"));
		functions.add(getSuccessFunction("10", codeing, "200.01"));
		functions.add(getSuccessFunction("11", codeing, "200.01"));
		functions.add(getSuccessFunction("12", codeing, "200.01"));
		functions.add(getSuccessFunction("13", codeing, "200.01"));
		functions.add(getSuccessFunction("14", codeing, "200.01"));
		functions.add(getSuccessFunction("15", codeing, "200.01"));
		functions.add(getSuccessFunction("16", codeing, "200.01"));
		functions.add(getSuccessFunction("17", codeing, "200.01"));
		functions.add(getSuccessFunction("18", codeing, "200.01"));
		functions.add(getSuccessFunction("19", codeing, "200.01"));
		functions.add(getSuccessFunction("20", codeing, "200.01"));

		functions.add(getSuccessFunction("27", codeing,
				String.valueOf(me.getData().get("27"))));
		functions.add(getSuccessFunction("28", codeing,
				String.valueOf(me.getData().get("28"))));
		functions.add(getSuccessFunction("29", codeing,
				String.valueOf(me.getData().get("29"))));
		functions.add(getSuccessFunction("30", codeing,
				String.valueOf(me.getData().get("30"))));
		functions.add(getSuccessFunction("31", codeing,
				String.valueOf(me.getData().get("31"))));
		functions.add(getSuccessFunction("32", codeing, "200.01"));
		functions.add(getSuccessFunction("33", codeing, "200.01"));
		functions.add(getSuccessFunction("34", codeing, "200.01"));
		functions.add(getSuccessFunction("35", codeing, "200.01"));
		functions.add(getSuccessFunction("36", codeing, "200.01"));
		functions.add(getSuccessFunction("37", codeing,
				String.valueOf(me.getData().get("37"))));
		functions.add(getSuccessFunction("38", codeing,
				String.valueOf(me.getData().get("38"))));
		functions.add(getSuccessFunction("39", codeing, "200.01"));
		functions.add(getSuccessFunction("40", codeing, "200.01"));
		functions.add(getSuccessFunction("41", codeing, "200.01"));
		functions.add(getSuccessFunction("42", codeing, "200.01"));
		functions.add(getSuccessFunction("43", codeing, "200.01"));
		functions.add(getSuccessFunction("44", codeing, "200.01"));
		functions.add(getSuccessFunction("45", codeing, "200.01"));
		functions.add(getSuccessFunction("46", codeing, "200.01"));

		return functions;
	}

	class Receiver implements Runnable {
		Receiver() {
		}

		public void run() {
			while (isRunning) {
				try {
					NetDauCommonRequest commonRequest = null;
					NetDauHeader header = (NetDauHeader) hMap.get("header");
					int availableLength = in.available();
					if ((availableLength == 0)
							&& (isAuth)
							&& (lastReadTime > 0L)
							&& (System.currentTimeMillis() - lastReadTime > 50000L)) {
						restart();
						return;
					}
					logger.info("Tcp client Receiver data len:"
							+ availableLength);
					if (header == null) {
						if (availableLength >= 8) {
							logger.info("reveive data availableLength:{}",
									Integer.valueOf(availableLength));
							byte[] hBytes = new byte[8];
							int rs = in.read(hBytes);
							lastReadTime = System.currentTimeMillis();
							if (rs > 0) {
								ByteBuf bytes = Unpooled.buffer(8);
								bytes.writeBytes(hBytes);
								header = new NetDauHeader(bytes.array());
								hMap.put("header", header);
							}
						}
					} else {
						if (availableLength >= header.getMessageLength()) {
							hMap.remove("header");
							byte[] body = new byte[header.getMessageLength()];

							int rs = in.read(body);
							ByteBuf bytes = Unpooled.buffer(header
									.getMessageLength());
							bytes.writeBytes(body);
							if (rs == header.getMessageLength()) {
								logger.info("reveive data length:{}",
										Integer.valueOf(rs));
								byte[] contentTagBytes = new byte[4];
								bytes.readBytes(contentTagBytes);
								int contentTag = NUMBERCODEC.bytes2Int(
										contentTagBytes, 4);

								byte[] contentBytes = new byte[header
										.getContentLength() - 4];

								bytes.readBytes(contentBytes);
								byte[] crcBytes = new byte[2];
								bytes.readBytes(crcBytes);
								int crc = NUMBERCODEC.bytes2Int(crcBytes, 2);

								byte[] validDataBytes = new byte[header
										.getContentLength()];
								System.arraycopy(contentTagBytes, 0,
										validDataBytes, 0,
										contentTagBytes.length);

								System.arraycopy(contentBytes, 0,
										validDataBytes, contentTagBytes.length,
										contentBytes.length);

								int myCrc = CRCUtil.getCrc16(validDataBytes,
										validDataBytes.length);
								if (crc != myCrc) {
									logger.error(
											"有效数据CRC校验失败(client crc={},server crc={})，建议断开连接!",

											Integer.valueOf(crc),
											Integer.valueOf(myCrc));
									return;
								}
								header.setDataCrc(crc);
								byte[] endBytes = new byte[4];
								bytes.readBytes(endBytes);
								String end = HexUtil.bytesToHex(endBytes);
								header.setEnd(end);
								byte[] dataDecryptBytes = AESCoder.decrypt(
										contentBytes, aes_secretkey_str,
										aes_iv_str);

								String xmlContent = new String(
										dataDecryptBytes, "UTF-8").trim();
								logger.debug("xmlContent:{}", xmlContent);

								String jsonContent = XmlToJsonUtils
										.getJSONObjectFromXML(xmlContent)
										.toJSONString();
								commonRequest = JSON
										.parseObject(jsonContent,
												NetDauCommonRequest.class);

								commonRequest = JSON
										.parseObject(jsonContent,
												NetDauCommonRequest.class);

								commonRequest.setJsonContent(jsonContent);
								logger.info("commonRequest:{}",
										JSON.toJSONString(commonRequest));
							}
						}
						if (commonRequest != null) {
							if (commonRequest.getCommon().getType()
									.equals("sequence")) {
								NetDauMd5Request request =  JSON
										.parseObject(
												commonRequest.getJsonContent(),
												NetDauMd5Request.class);

								request.getId_validate().getAttr()
										.setOperation("md5");
								request.getId_validate().setMd5(
										Md5Utils.MD5(request.getId_validate()
												.getSequence()
												+ "IJKLMNOPQRSTUVWX"));

								request.getId_validate().setSequence(null);
								request.getCommon().setType("md5");
								sendMessage(request);
								isRegister = true;
							} else if (commonRequest.getCommon().getType()
									.equals("result")) {
								NetDauMd5Response response = JSON
										.parseObject(
												commonRequest.getJsonContent(),
												NetDauMd5Response.class);
								if (response.getId_validate().getResult()
										.equals("pass")) {
									isAuth = true;
									logger.info("发送设备{},{}信息", new Object[] {
											buildingId, gatewayId });
									NetDauDeviceRequest request = new NetDauDeviceRequest();
									CommonDo common = new CommonDo();
									BeanUtils.copyProperties(common,
											commonRequest.getCommon());
									common.setType("device");
									request.setCommon(common);
									DeviceDo device = new DeviceDo();
									device.setBuild_name("杭州雅全");
									device.setBuild_no(buildingId);
									device.setDev_no(gatewayId);
									device.setFactory("Z3301D2012020008");
									device.setHardware("SUN-DAU-400");
									device.setSoftware("CCVer2.15");
									device.setMac("00:45:67:89:D0:01");
									device.setIp("192.168.1.195");
									device.setMask("255.255.255.0");
									device.setGate("192.168.1.1");
									device.setServer("192.168.1.100");
									device.setPort("36829");
									device.setHost("192.168.1.106");
									device.setEnable1("1");
									device.setCom("32869");
									device.setDev_num("1");
									device.setPeriod("10");
									device.setBegin_time("201303071411");
									device.setAddress("00000000199271001");
									DefaultAttrDo attr = new DefaultAttrDo();
									attr.setOperation("device");
									device.setAttr(attr);
									request.setDevice(device);
									sendMessage(request);
									reportDeviceStatus = 1;
								} else {
									System.out.println("认证失败，断开连接");
									stop();
								}
							} else if (commonRequest.getCommon().getType()
									.equals("device_ack")) {
								logger.info("服务端收到设备信息回复{},{}信息", new Object[] {
										buildingId, gatewayId });
								NetDauDeviceResponse response = JSON
										.parseObject(
												commonRequest.getJsonContent(),
												NetDauDeviceResponse.class);
								if (response.getDevice().getDevice_ack()
										.equals("pass")) {
									reportDeviceStatus = 2;

									BeatHearter beatHearter = new BeatHearter();
									new Thread(beatHearter).start();

									LogReporter logReporter = new LogReporter();
									new Thread(logReporter).start();
								}
							}
							else if(commonRequest.getCommon().getType()
									.equals("control")){
								logger.info("收到设备控制命令{},{} 信息：{}", new Object[] {
										buildingId, gatewayId,commonRequest.getJsonContent() });
								NetDauControlRequest request=JSON.parseObject(commonRequest.getJsonContent(), NetDauControlRequest.class);
								NetDauControlResponse response = new NetDauControlResponse();
								CommonDo common = new CommonDo();
								BeanUtils.copyProperties(common,
										commonRequest.getCommon());
								common.setType("control_ack");
								response.setCommon(common);
								InstructionAckDo instructionAck = new InstructionAckDo();
								DefaultAttrDo attr = new DefaultAttrDo();
								attr.setOperation("control_ack");
								instructionAck.setAttr(attr);
								ControlAckInfoDo control_info=new ControlAckInfoDo();
								control_info.setAttr(null);
								List<ControlDo> control_ack=new ArrayList<>();
								for(ControlDo controlDo:request.getInstruction().getControl_info().getControl()){
									ControlDo controlDo1=new ControlDo();
									AttributeDo attr1=new AttributeDo();
									attr1.setIdx(controlDo.getAttr().getIdx());
									attr1.setMeterId(controlDo.getAttr().getMeterId());
									attr1.setErr("F0");
									attr1.setData("FE6819180913000068DC01373116");
									controlDo1.setAttr(attr1);
									control_ack.add(controlDo1);
									control_info.setControl_ack(control_ack);
									instructionAck.setControl_info(control_info);
									response.setInstruction(instructionAck);
									sendMessage(response);
								}
							}
							else if (commonRequest.getCommon().getType()
									.equals("time")) {
								NetDauBeatHeartResponse response = JSON
										.parseObject(
												commonRequest.getJsonContent(),
												NetDauBeatHeartResponse.class);
								System.out.println("心跳返回的服务器时间："+response.getHeart_beat().getTime());
								System.out.println("server is live...");
							}
						}
					}
					Thread.sleep(1000L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class InterValT implements Serializable {
	private static final long serialVersionUID = 1L;
	private String interval;
	private Long startTime;
	private Long endTime;
	private Integer intervalType;
	private Double intervalPrice;
	private String intervalStr;

	/**
	 * 判断当前时间范围是不是在区间内
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean include(Long time) {
		if (time >= this.startTime && time <= this.endTime) {
			return true;
		}
		return false;
	}

	public String getInterval() {
		return interval;
	}

	public Long getStartTime() {
		return startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public Integer getIntervalType() {
		return intervalType;
	}

	public Double getIntervalPrice() {
		return intervalPrice;
	}

	public String getIntervalStr() {
		return intervalStr;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public void setIntervalType(Integer intervalType) {
		this.intervalType = intervalType;
	}

	public void setIntervalPrice(Double intervalPrice) {
		this.intervalPrice = intervalPrice;
	}

	public void setIntervalStr(String intervalStr) {
		this.intervalStr = intervalStr;
	}
}

class GateWayE implements Serializable {
	private static final long serialVersionUID = 1L;
	private String buildingId = "330100A088";
	private String gatewayId = "01";
	private Date startTime;// 开始收集时间
	private Date nextTime;// 下一次手机时间
	private int period;// 收集频率 分钟
	private int taskPeriod;
	private String chargeInterValTimes;// "00:00-00:08-1;00:00-00:08-2;00:00-00:08-3;"

	public void setChargeInterValTimes(String chargeInterValTimes) {
		this.chargeInterValTimes = chargeInterValTimes;
	}

	/**
	 * 0 统一单价 1 尖单价 2峰单价 3平单价 4谷单价
	 * 
	 * @param reportTime
	 * @return
	 */
	public Integer getChargeType(Date reportTime) {
		int result = 0;// 统一单价
		if (StringUtils.isBlank(chargeInterValTimes)) {
			return result;
		}
		String day = DateUtils.format(reportTime, "yyyyMMdd");
		String intervalTimes[] = StringUtils.split(chargeInterValTimes, ";");
		List<InterValT> interVals = new ArrayList<InterValT>();
		for (String intervalTime : intervalTimes) {
			String[] times = StringUtils.split(intervalTime, "-");
			String start = times[0] + ":00";
			String end = times[1].split(":")[0];
			Integer endNum = Integer.parseInt(end);
			endNum = endNum - 1;
			String tmp = String.valueOf(endNum);
			if (tmp.length() == 1) {
				tmp = "0" + tmp;
			}
			end = tmp + ":59:59";
			long startTime = DateUtils.parse(day + " " + start,
					"yyyyMMdd HH:mm:ss").getTime();
			long endTime = DateUtils
					.parse(day + " " + end, "yyyyMMdd HH:mm:ss").getTime();
			String intervalStr = day + " " + start + "-" + day + " " + end;
			InterValT interval = new InterValT();
			interval.setInterval(intervalTime);
			interval.setIntervalStr(intervalStr);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			interval.setIntervalType(Integer.parseInt(times[2]));
			interVals.add(interval);
		}
		for (InterValT chargeInterval : interVals) {
			if (chargeInterval.include(reportTime.getTime())) {
				result = chargeInterval.getIntervalType();
				break;
			}
		}
		return result;
	}

	private List<MeterE> meterList;

	public Date getStartTime() {
		return startTime;
	}

	public int getPeriod() {
		return period;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public List<MeterE> getMeterList() {
		return meterList;
	}

	public void setMeterList(List<MeterE> meterList) {
		this.meterList = meterList;
	}

	public Date getNextTime() {
		return nextTime;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public String getChargeInterValTimes() {
		return chargeInterValTimes;
	}

	public int getTaskPeriod() {
		return taskPeriod;
	}

	public void setTaskPeriod(int taskPeriod) {
		this.taskPeriod = taskPeriod;
	}

}

class MeterE implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String subTermCode;
	private String type;// 配电房:dr,楼宇,bd,设备 device
	private Map<String, Double> data = new HashMap<String, Double>();

	public String getId() {
		return id;
	}

	public Map<String, Double> getData() {
		return data;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setData(Map<String, Double> data) {
		this.data = data;
	}

	public String getSubTermCode() {
		return subTermCode;
	}

	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
