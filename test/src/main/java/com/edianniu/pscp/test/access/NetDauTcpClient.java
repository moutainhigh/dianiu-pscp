package com.edianniu.pscp.test.access;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import redis.clients.jedis.Jedis;
import stc.skymobi.util.DefaultNumberCodecs;
import stc.skymobi.util.NumberCodec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.edianniu.pscp.test.access.bean.common.Sequence;
import com.edianniu.pscp.test.access.bean.netdau.AttributeDo;
import com.edianniu.pscp.test.access.bean.netdau.BeatHeartDo;
import com.edianniu.pscp.test.access.bean.netdau.CommonDo;
import com.edianniu.pscp.test.access.bean.netdau.DefaultAttrDo;
import com.edianniu.pscp.test.access.bean.netdau.DeviceDo;
import com.edianniu.pscp.test.access.bean.netdau.FunctionDo;
import com.edianniu.pscp.test.access.bean.netdau.IdValidateDo;
import com.edianniu.pscp.test.access.bean.netdau.MeterDo;
import com.edianniu.pscp.test.access.bean.netdau.NetDauBeatHeartRequest;
import com.edianniu.pscp.test.access.bean.netdau.NetDauBeatHeartResponse;
import com.edianniu.pscp.test.access.bean.netdau.NetDauCommonRequest;
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
	private volatile long lastReadTime = 0L;// 服务是否启动
	private volatile int reportDeviceStatus = 0;// 设备信息是否上报状态0 未上报,1上报中,2已上报
	private static final String aes_iv_str = HexUtil
			.hexToStr("0102030405060708090a0b0c0d0e0f10");
	private static final String aes_secretkey_str = HexUtil
			.hexToStr("0102030405060708090a0b0c0d0e0f10");
	private static final Map<String, NetDauHeader> hMap = new ConcurrentHashMap<String, NetDauHeader>();
	private static final NumberCodec NUMBERCODEC = DefaultNumberCodecs
			.getLittleEndianNumberCodec();
	private GateWayE gateWayE;
	private int logReporterTime = 60;
	private String gateWayKey;
	Jedis jedis = null;
	public NetDauTcpClient(String ip, int port,String redisIp,int redisPort, GateWayE gateWayE) {
		this.ip = ip;
		this.port = port;
		this.redisIp=redisIp;
		this.redisPort=redisPort;
		this.buildingId = gateWayE.getBuildingId();
		this.gatewayId = gateWayE.getGatewayId();
		this.gateWayE = gateWayE;
		this.gateWayKey = "gateWayE#" + buildingId + gatewayId;
		this.logReporterTime=gateWayE.getPeriod()*60;

	}

	public void start() throws UnknownHostException, IOException {
		jedis = new Jedis(redisIp, redisPort);
		jedis.connect();
		String key = gateWayKey;
		String value = jedis.get(key);
		if (StringUtils.isBlank(value)) {
			value = JSON.toJSONString(gateWayE);
			jedis.set(key, value);
		}
		gateWayE = JSON.parseObject(value, GateWayE.class);
		st = new Socket(this.ip, this.port);
		if (st != null) {
			logger.info("connect to iot server success!");

		} else {
			logger.error("connect to iot server fail!");
		}
		st.setKeepAlive(true);
		out = new DataOutputStream(st.getOutputStream());
		in = new DataInputStream(st.getInputStream());
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
		logger.info("reconnect to iot server success!");
		try {
			this.stop();
			this.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stop() throws InterruptedException {
		isRunning = false;
		this.isRegister = false;
		this.isAuth = false;
		this.reportDeviceStatus = 0;
		Thread.sleep(1000);
		System.out.println("Tcp client is stop...");
		try {
			if (out != null) {
				out.close();
				out = null;

			}
			if (out != null) {
				in.close();
				in = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 发送消息 1）消息编码 2）消息发送
	 * 
	 * @param request
	 */
	private void sendMessage(Object request) {
		String xmlContent = XmlToJsonUtils.getXmlFromJSON(JSON.toJSONString(
				request, SerializerFeature.DisableCircularReferenceDetect));
		logger.info("发送的消息:", xmlContent);
		try {
			int contentTag = Sequence.get();
			byte[] contentTagBytes = NUMBERCODEC.int2Bytes(contentTag,
					NetDauHeader.CONTENT_TAG_LENGTH);

			byte[] contentBytes = AESCoder.encrypt(xmlContent,
					aes_secretkey_str, aes_iv_str);
			int contentLength = contentTagBytes.length + contentBytes.length;
			byte[] validDataBytes = new byte[contentLength];
			System.arraycopy(contentTagBytes, 0, validDataBytes, 0,
					contentTagBytes.length);
			System.arraycopy(contentBytes, 0, validDataBytes,
					contentTagBytes.length, contentBytes.length);
			int crc = CRCUtil.getCrc16(validDataBytes, validDataBytes.length);
			byte[] crcBytes = NUMBERCODEC.int2Bytes(crc,
					NetDauHeader.CRC_LENGTH);
			logger.debug("xml data encrypt bytes:{}",
					Arrays.toString(validDataBytes));
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
			out.write(outBytes);
			out.flush();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public NetDauTcpClient() {

	}
    public void demo(){
    	/*GateWayE gateWayE=new GateWayE();//1112
        gateWayE.setBuildingId("330100A000");
        gateWayE.setGatewayId("02");
        gateWayE.setMeterList(getMeterList(new String[]{"001:01A00","002:01A00","003:01A00","004:01A00","005:01A00","006:01C00","007:01A00",
        		"020:01B00","021:01C00","022:01A00","019:01D00","016:01B00","017:01C00",
        		"018:01A00","011:01D00","012:01B00","013:01C00","014:01A00"}));
        gateWayE.setStartTime(DateUtils.parse("20171001 00:00:00", "yyyyMMdd HH:mm:ss"));
        gateWayE.setPeriod(5);
        gateWayE.setNextTime(null);
        NetDauTcpClient rpcClient0 = new NetDauTcpClient("192.168.1.251", 5001,gateWayE);
		rpcClient0.start();*/
     /*   GateWayE gateWayE=new GateWayE();
        gateWayE.setBuildingId("330100A000");//1107
        gateWayE.setGatewayId("01");
        gateWayE.setMeterList(getMeterList(new String[]{"001:01A00","002:01A00","003:01A00","004:01A00","005:01A00","006:01C00","007:01A00","008:01B00","009:01D00"}));
        gateWayE.setStartTime(DateUtils.parse("20171201 00:00:00", "yyyyMMdd HH:mm:ss"));
        gateWayE.setPeriod(5);
        gateWayE.setNextTime(null);
        NetDauTcpClient rpcClient0 = new NetDauTcpClient("192.168.1.251", 5001,gateWayE);
		rpcClient0.start();*/
		/*GateWayE gateWayE=new GateWayE();
        gateWayE.setBuildingId("330100A000");//1107
        gateWayE.setGatewayId("05");
        gateWayE.setMeterList(getMeterList(new String[]{"001:01A00",
        		"002:01A00","003:01A00","004:01A00","005:01A00","010:01A00","011:01B00","012:01C00","013:01D00"}));
        gateWayE.setStartTime(null);
        gateWayE.setPeriod(1);//一分钟上传数据一次
        gateWayE.setNextTime(null);
        gateWayE.setChargeInterValTimes("");//设置尖峰平谷时间
        NetDauTcpClient rpcClient0 = new NetDauTcpClient("192.168.1.251", 5001,gateWayE);
		rpcClient0.start();*/
		/*GateWayE gateWayE=new GateWayE();
        gateWayE.setBuildingId("330100A000");//1107
        gateWayE.setGatewayId("06");
        gateWayE.setMeterList(getMeterList(new String[]{"001:01A00:dr",
        		"002:01A00:bd","003:01A00:bd","004:01A00:bd","005:01A00:bd","006:01A00:bd","007:01A00:bd","008:01A00:bd","009:01A00:bd",
        		"010:01A00:bd",
        		"020:01A00:device","021:01B00:device","022:01C00:device","023:01D00:device",
        		"030:01A00:device","031:01B00:device","032:01C00:device","033:01D00:device",
        		"040:01A00:device","041:01B00:device","042:01C00:device","043:01D00:device",
        		"050:01A00:device","051:01B00:device","052:01C00:device","053:01D00:device",
        		"060:01A00:device","061:01B00:device","062:01C00:device","063:01D00:device",
        		"070:01A00:device","071:01B00:device","072:01C00:device","073:01D00:device",
        		"080:01A00:device","081:01B00:device","082:01C00:device","083:01D00:device",
        		"090:01A00:device","091:01B00:device","092:01C00:device","093:01D00:device"
        		
        }));
        gateWayE.setStartTime(null);
        gateWayE.setPeriod(1);//一分钟上传数据一次
        gateWayE.setNextTime(null);
        gateWayE.setChargeInterValTimes("00:00-08:00-4;08:00-12:00-1;12:00-14:00-3;14:00-19:00-2;19:00-22:00-3;22:00-24:00-4");//设置尖峰平谷时间
        NetDauTcpClient rpcClient0 = new NetDauTcpClient("192.168.1.251", 5001,gateWayE);
		rpcClient0.start();*/
		/*GateWayE gateWayE=new GateWayE();
        gateWayE.setBuildingId("030100A000");//1107
        gateWayE.setGatewayId("01");
        gateWayE.setMeterList(getMeterList(new String[]{"001:01A00:dr",
        		"002:01A00:bd","003:01A00:bd",
        		"004:01A00:device","005:01B00:device","006:01C00:device","007:01D00:device"
        		
        }));
        gateWayE.setStartTime(null);
        gateWayE.setPeriod(1);//一分钟上传数据一次
        gateWayE.setNextTime(null);
        gateWayE.setChargeInterValTimes("00:00-08:00-4;08:00-12:00-1;12:00-14:00-3;14:00-18:00-2;18:00-22:00-3;22:00-24:00-4");//设置尖峰平谷时间
        NetDauTcpClient rpcClient0 = new NetDauTcpClient("127.0.0.1", 5001,gateWayE);
		rpcClient0.start();*/
    }
	/**
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		//POWER("01C00","动力"),AIR("01B00","空调"),SPECIAL("01D00","特殊"),LIGHTING("01A00","照明"),OTHER("01X00","其他");
		if(args==null||args.length<=0){
			System.exit(-1);
			return ;
		}
		String configPath=args[0];
		PropertiesUtil config = new PropertiesUtil(configPath);//"D://work//web-work//test//src//main//resources//app.properties"
		String accessIp=config.getProperty("access.ip");
		String accessPort=config.getProperty("access.port");
		String redisIp=config.getProperty("redis.ip");
		String redisPort=config.getProperty("redis.port");
		String buildingId=config.getProperty("building.id");
		String gatewayId=config.getProperty("gateway.id");
		String meterList=config.getProperty("meter.list");
		String period=config.getProperty("upload.period");
		String chargeIntervalTimes=config.getProperty("charge.interval.times");
		GateWayE gateWayE=new GateWayE();
        gateWayE.setBuildingId(buildingId);//1098
        gateWayE.setGatewayId(gatewayId);
        //bd 表示楼宇
        //dr 表示配电房即主线
        //device 表示设备
        String[] meters=StringUtils.split(meterList,",");
        gateWayE.setMeterList(getMeterList(meters));
        gateWayE.setStartTime(null);
        gateWayE.setPeriod(Integer.parseInt(period));//一分钟上传数据一次
        gateWayE.setNextTime(null);
        gateWayE.setChargeInterValTimes(chargeIntervalTimes);//设置尖峰平谷时间
        NetDauTcpClient rpcClient = new NetDauTcpClient(accessIp,Integer.parseInt(accessPort),redisIp,Integer.parseInt(redisPort),gateWayE);
		rpcClient.start();
	}
	public static List<MeterE> getMeterList(String[] meterIds){
		List<MeterE> meterList = new ArrayList<MeterE>();
		for(String meterId:meterIds){
			MeterE me1 = new MeterE();
			String strs[]=StringUtils.split(meterId,":");
			if(strs.length>=2){
				me1.setId(strs[0]);// 总表
				me1.setSubTermCode(strs[1]);
			}
			String type="";// 配电房:dr,楼宇,bd,设备 device
			if(strs.length>=3){
				type=strs[2];
				me1.setType(type);
			}
			// 01,02,03,04,21,22,23,24,25,26 ,27,38
			if(StringUtils.isBlank(type)){
				me1.getData().put("01", 2000D);//总功率
				me1.getData().put("02", 1000D);//功率
				me1.getData().put("03", 1000D);//功率
				me1.getData().put("04", 1000D);//功率
				me1.getData().put("21", 200D);//A电压 
				me1.getData().put("22", 200D);//B电压 
				me1.getData().put("23", 200D);//C电压 
				me1.getData().put("24", 10D);//A电流
				me1.getData().put("25", 10D);//B电流
				me1.getData().put("26", 10D);//C电流
				me1.getData().put("27", 100D);// 有功总电量
				me1.getData().put("28", 10D);// 有功尖电量
				me1.getData().put("29", 40D);// 有功峰电量
				me1.getData().put("30", 30D);// 有功平电量
				me1.getData().put("31", 20D);// 有功谷电量
				me1.getData().put("37", 10D);//无功总电量
				me1.getData().put("38", 10D);//无功尖电量
			}
			else{
				if(type.equals("dr")){
					me1.getData().put("01", 3800D);//总功率
					me1.getData().put("02", 3800D);//功率
					me1.getData().put("03", 3800D);//功率
					me1.getData().put("04", 3800D);//功率
					me1.getData().put("21", 380D);//电压 
					me1.getData().put("22", 380D);
					me1.getData().put("23", 380D);
					me1.getData().put("24", 10D);//电流
					me1.getData().put("25", 10D);
					me1.getData().put("26", 10D);
					me1.getData().put("27", 100D);// 有功总电量
					me1.getData().put("28", 10D);// 有功尖电量
					me1.getData().put("29", 40D);// 有功峰电量
					me1.getData().put("30", 30D);// 有功平电量
					me1.getData().put("31", 20D);// 有功谷电量
					me1.getData().put("37", 10D);//无功总电量
					me1.getData().put("38", 10D);//无功尖电量
				}
				if(type.equals("bd")){
					me1.getData().put("01", 2000D);//总功率
					me1.getData().put("02", 1000D);//功率
					me1.getData().put("03", 1000D);//功率
					me1.getData().put("04", 1000D);//功率
					me1.getData().put("21", 200D);//A电压 
					me1.getData().put("22", 200D);//B电压 
					me1.getData().put("23", 200D);//C电压 
					me1.getData().put("24", 10D);//A电流
					me1.getData().put("25", 10D);//B电流
					me1.getData().put("26", 10D);//C电流
					me1.getData().put("27", 100D);// 有功总电量
					me1.getData().put("28", 10D);// 有功尖电量
					me1.getData().put("29", 40D);// 有功峰电量
					me1.getData().put("30", 30D);// 有功平电量
					me1.getData().put("31", 20D);// 有功谷电量
					me1.getData().put("37", 10D);//无功总电量
					me1.getData().put("38", 10D);//无功尖电量
				}
				if(type.equals("device")){
					me1.getData().put("01", 1000D);//总功率
					me1.getData().put("02", 1000D);//功率
					me1.getData().put("03", 1000D);//功率
					me1.getData().put("04", 1000D);//功率
					me1.getData().put("21", 200D);//A电压 
					me1.getData().put("22", 200D);//B电压 
					me1.getData().put("23", 200D);//C电压 
					me1.getData().put("24", 5D);//A电流
					me1.getData().put("25", 5D);//B电流
					me1.getData().put("26", 5D);//C电流
					me1.getData().put("27", 100D);// 有功总电量
					me1.getData().put("28", 10D);// 有功尖电量
					me1.getData().put("29", 40D);// 有功峰电量
					me1.getData().put("30", 30D);// 有功平电量
					me1.getData().put("31", 20D);// 有功谷电量
					me1.getData().put("37", 10D);//无功总电量
					me1.getData().put("38", 10D);//无功尖电量
				}
			}
			
			meterList.add(me1);
		}
		return meterList;
	}

	class Sender implements Runnable {

		@Override
		public void run() {
			while (isRunning) {
				// System.out.println("Tcp client Sender is listen...");
				if (!isRegister) {// 发送注册信息
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
				if (isAuth) {// 认证成功后发送定时任务以及设备信息

					// System.out.println("上传设备定时任务日志...");
				}
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	class BeatHearter implements Runnable {

		@Override
		public void run() {
			while (isRunning && isAuth) {
				// System.out.println("Tcp client BeatHearter is listen...");
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}

	}

	class LogReporter implements Runnable {// 日志上传

		@Override
		public void run() {
			while (isRunning && isAuth) {
				System.out.println("Tcp client LogReporter is listen...");
				if (reportDeviceStatus == 2) {
					try {
					System.out.println("开始日志上传...");
					NetDauReportRequest request = getNetDauReportRequest();
					sendMessage(request);
					System.out.println("日志信息:time=["+request.getData().getTime()+"],["+request.getCommon().getBuilding_id()+","+request.getCommon().getGateway_id()+"]");
					Thread.sleep(logReporterTime*1000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch(Exception e){
						System.out.println("error:"+e.getMessage());
						e.printStackTrace();
					}
				}

			}
		}

	}

	private NetDauReportRequest getNetDauReportRequest() {
		NetDauReportRequest request = new NetDauReportRequest();
		CommonDo common = new CommonDo();
		common.setBuilding_id(buildingId);
		common.setGateway_id(gatewayId);
		common.setType("report");
		request.setCommon(common);
		ReportDo reportDo = new ReportDo();
		reportDo.setCurrent("1");

		reportDo.setParser("yes");
		reportDo.setSequence("1");
		if(gateWayE.getStartTime()==null){
			reportDo.setTime(DateUtils.format(new Date(), "yyyyMMddHHmmss"));
		}
		else{
			Date nextTime=gateWayE.getNextTime();
			if(nextTime!=null){
				reportDo.setTime(DateUtils.format(nextTime, "yyyyMMddHHmmss"));
				gateWayE.setNextTime(DateUtils.parse(DateUtils.getDateOffsetMinute(nextTime, gateWayE.getPeriod(), "yyyyMMddHHmmss"), "yyyyMMddHHmmss"));
			}
			else{
				reportDo.setTime(DateUtils.format(gateWayE.getStartTime(), "yyyyMMddHHmmss"));
				gateWayE.setNextTime(DateUtils.parse(DateUtils.getDateOffsetMinute(gateWayE.getStartTime(), gateWayE.getPeriod(), "yyyyMMddHHmmss"), "yyyyMMddHHmmss"));
			}
		}
		List<MeterDo> meters = getMeters(DateUtils.parse(reportDo.getTime(),"yyyyMMddHHmmss"));
		//更新gateWayE 信息，下一次上传数据时使用
		jedis.set(gateWayKey, JSON.toJSONString(gateWayE));
		reportDo.setMeter(meters);
		reportDo.setTotal("" + meters.size());
		AttributeDo attr = new AttributeDo();
		attr.setOperation("report");
		reportDo.setAttr(attr);
		request.setData(reportDo);
		return request;
	}

	/**
	 * 获取日志数据
	 * 
	 * @return
	 */
	private List<MeterDo> getMeters(Date time) {
		List<MeterDo> meters = new ArrayList<MeterDo>();
		for (MeterE me : gateWayE.getMeterList()) {
			meters.add(getMeter(me,time));
		}
		return meters;
	}

	private MeterDo getMeter(MeterE me,Date time) {
		MeterDo meter = new MeterDo();
		AttributeDo attr = new AttributeDo();
		attr.setAddr("1972828");
		attr.setCom("1");
		attr.setId(me.getId());
		attr.setName(me.getId() + "号变压器");
		attr.setTp("1");
		meter.setAttr(attr);
		meter.setFunction(getFunctions(me,time));
		return meter;
	}

	private FunctionDo getSuccessFunction(String id, String coding, String value) {
		return this.getFunction(id, coding, value, "192");
	}

	private FunctionDo getErrorFunction(String id, String coding, String value) {
		return this.getFunction(id, coding, value, "192");
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

	private List<FunctionDo> getFunctions(MeterE me,Date time) {
		List<FunctionDo> functions = new ArrayList<FunctionDo>();
		String codeing=me.getSubTermCode();
		// 01,02,03,04,21,22,23,24,25,26 ,27,38
		int prInt=300;
		int urInt=100;
		int irInt=80;
		Double nocharge=gateWayE.getPeriod()*2+Math.random();
		Double charge=gateWayE.getPeriod()*10+0.01;
		if(me.getType().equals("dr")){
			nocharge=gateWayE.getPeriod()*4+Math.random();
			charge=gateWayE.getPeriod()*20+0.01;
		}
		else if(me.getType().equals("bd")){
			nocharge=gateWayE.getPeriod()*2+Math.random();
			charge=gateWayE.getPeriod()*10+0.01;
		}
		else if(me.getType().equals("device")){
			nocharge=gateWayE.getPeriod()+Math.random();
			charge=gateWayE.getPeriod()*5+0.01;
		}
		//功率--一个基准值，不断变化，不是累计值
		
    	int r1=new Random().nextInt(prInt);
    	Double p=0.00D;
    	if(r1%2==0){
    		p=me.getData().get("01")+Math.random()-r1;
    	}
    	else{
    		p=me.getData().get("01")+Math.random()+r1;
    	}
    	int r2=new Random().nextInt(prInt);
    	Double pa=0.00D;
    	if(r2%2==0){
    		pa=me.getData().get("02")+Math.random()-r2;
    	}
    	else{
    		pa=me.getData().get("02")+Math.random()+r2;
    	}
    	int r3=new Random().nextInt(prInt);
    	Double pb=0.00D;
    	if(r3%2==0){
    		pb=me.getData().get("03")+Math.random()-r3;
    	}
    	else{
    		pb=me.getData().get("03")+Math.random()+r3;
    	}
    	int r4=new Random().nextInt(prInt);
    	Double pc=0.00D;
    	if(r4%2==0){
    		pc=me.getData().get("03")+Math.random()-r4;
    	}
    	else{
    		pc=me.getData().get("03")+Math.random()+r4;
    	}
    	functions.add(this.getSuccessFunction("01", codeing,
				String.valueOf(p)));// 三相总有功功率
		functions.add(this.getSuccessFunction("02", codeing,
				String.valueOf(pa)));// A相有功功率
		functions.add(this.getSuccessFunction("03", codeing,
				String.valueOf(pb)));// B相有功功率
		functions.add(this.getSuccessFunction("04", codeing,
				String.valueOf(pc)));// C相有功功率
    	//电压 
		
    	int r21=new Random().nextInt(urInt);
    	Double ua=0.00D;
    	if(r21%2==0){
    		ua=me.getData().get("21")+Math.random()-r21;
    	}
    	else{
    		ua=me.getData().get("21")+Math.random()+r21;
    	}
    	int r22=new Random().nextInt(urInt);
    	Double ub=0.00D;
    	if(r22%2==0){
    		ub=me.getData().get("22")+Math.random()-r22;
    	}
    	else{
    		ub=me.getData().get("22")+Math.random()+r22;
    	}
    	int r23=new Random().nextInt(20);
    	Double uc=0.00D;
    	if(r23%2==0){
    		uc=me.getData().get("23")+Math.random()-r23;
    	}
    	else{
    		uc=me.getData().get("23")+Math.random()+r23;
    	}
    	functions.add(this.getSuccessFunction("21", codeing,
				String.valueOf(ua)));// A电压
		functions.add(this.getSuccessFunction("22", codeing,
				String.valueOf(ub)));// B电压
		functions.add(this.getSuccessFunction("23", codeing,
				String.valueOf(uc)));// C电压
    	// 电流
		
    	int r24=new Random().nextInt(irInt);
    	Double ia=0.00D;
    	if(r24%2==0){
    		ia=me.getData().get("24")+Math.random()-r24;
    	}
    	else{
    		ia=me.getData().get("24")+Math.random()+r24;
    	}
    	int r25=new Random().nextInt(irInt);
    	Double ib=0.00D;
    	if(r25%2==0){
    		ib=me.getData().get("25")+Math.random()-r25;
    	}
    	else{
    		ib=me.getData().get("25")+Math.random()+r25;
    	}
    	int r26=new Random().nextInt(irInt);
    	Double ic=0.00D;
    	if(r26%2==0){
    		ic=me.getData().get("26")+Math.random()-r26;
    	}
    	else{
    		ic=me.getData().get("26")+Math.random()+r26;
    	}
    	functions.add(this.getSuccessFunction("24", codeing,
				String.valueOf(ia)));// A电流
		functions.add(this.getSuccessFunction("25", codeing,
				String.valueOf(ib)));// B电流
		functions.add(this.getSuccessFunction("26", codeing,
				String.valueOf(ic)));// C电流
		Integer type=gateWayE.getChargeType(time);
		
		me.getData().put("27", me.getData().get("27") +charge );// 有功总电量
		//根据尖峰平谷的时间段来进行电量的计算
		if(type==1){
			me.getData().put("28", me.getData().get("28") + charge);// 有功尖电量
		}
		else if(type==2){
			me.getData().put("29", me.getData().get("29") + charge);// 有功峰电量
		}
		else if(type==3){
			me.getData().put("30", me.getData().get("30") + charge);// 有功平电量
		}
		else if(type==4){
			me.getData().put("31", me.getData().get("31") + charge);// 有功谷电量
		}
		if(me.getData().get("37")==null){
			me.getData().put("37", 300.05);
		}
		me.getData().put("37", me.getData().get("37") + nocharge);// 无功总电量
		
		
		me.getData().put("38", me.getData().get("38") + nocharge);// 无功尖电量
		
		functions.add(this.getSuccessFunction("05", codeing, "200.01"));// 三相总无功功率
		functions.add(this.getSuccessFunction("06", codeing, "200.01"));// A相无功功率
		functions.add(this.getSuccessFunction("07", codeing, "200.01"));// B相无功功率
		functions.add(this.getSuccessFunction("08", codeing, "200.01"));// C相无功功率
		functions.add(this.getSuccessFunction("09", codeing, "200.01"));// 三相总视在功率
		functions.add(this.getSuccessFunction("10", codeing, "200.01"));// A相视在功率
		functions.add(this.getSuccessFunction("11", codeing, "200.01"));// B相视在功率
		functions.add(this.getSuccessFunction("12", codeing, "200.01"));// C相视在功率
		functions.add(this.getSuccessFunction("13", codeing, "200.01"));// 三相总功率因数
		functions.add(this.getSuccessFunction("14", codeing, "200.01"));// A相功率因数
		functions.add(this.getSuccessFunction("15", codeing, "200.01"));// B相功率因数
		functions.add(this.getSuccessFunction("16", codeing, "200.01"));// C相功率因数
		functions.add(this.getSuccessFunction("17", codeing, "200.01"));// 正向有功最大需量
		functions.add(this.getSuccessFunction("18", codeing, "200.01"));// 反向有功最大需量
		functions.add(this.getSuccessFunction("19", codeing, "200.01"));// 正向无功最大需量
		functions.add(this.getSuccessFunction("20", codeing, "200.01"));// 反向无功最大需量
	
		
		functions.add(this.getSuccessFunction("27", codeing,
				String.valueOf(me.getData().get("27"))));// 正向总有功电度
		functions.add(this.getSuccessFunction("28", codeing,
				String.valueOf(me.getData().get("28"))));// 正向尖有功电度
		functions.add(this.getSuccessFunction("29", codeing,
				String.valueOf(me.getData().get("29"))));// 正向峰有功电度
		functions.add(this.getSuccessFunction("30", codeing,
				String.valueOf(me.getData().get("30"))));// 正向平有功电度
		functions.add(this.getSuccessFunction("31", codeing,
				String.valueOf(me.getData().get("31"))));// 正向谷有功电度
		functions.add(this.getSuccessFunction("32", codeing, "200.01"));// 反向总有功电度
		functions.add(this.getSuccessFunction("33", codeing, "200.01"));// 反向尖有功电度
		functions.add(this.getSuccessFunction("34", codeing, "200.01"));// 反向峰有功电度
		functions.add(this.getSuccessFunction("35", codeing, "200.01"));// 反向平有功电度
		functions.add(this.getSuccessFunction("36", codeing, "200.01"));// 反向谷有功电度
		functions.add(this.getSuccessFunction("37", codeing, String.valueOf(me.getData().get("37"))));// 正向总无功电度
		functions.add(this.getSuccessFunction("38", codeing,
				String.valueOf(me.getData().get("38"))));// 正向尖无功电度
		functions.add(this.getSuccessFunction("39", codeing, "200.01"));// 正向峰无功电度
		functions.add(this.getSuccessFunction("40", codeing, "200.01"));// 正向平无功电度
		functions.add(this.getSuccessFunction("41", codeing, "200.01"));// 正向谷无功电度
		functions.add(this.getSuccessFunction("42", codeing, "200.01"));// 反向总无功电度
		functions.add(this.getSuccessFunction("43", codeing, "200.01"));// 反向尖无功电度
		functions.add(this.getSuccessFunction("44", codeing, "200.01"));// 反向峰无功电度
		functions.add(this.getSuccessFunction("45", codeing, "200.01"));// 反向平无功电度
		functions.add(this.getSuccessFunction("46", codeing, "200.01"));// 反向谷无功电度

		return functions;
	}

	class Receiver implements Runnable {
		@Override
		public void run() {
			while (isRunning) {
				try {

					NetDauCommonRequest commonRequest = null;
					NetDauHeader header = hMap.get("header");
					int availableLength = in.available();
					if (availableLength == 0 && isAuth) {
						if (lastReadTime > 0
								&& (System.currentTimeMillis() - lastReadTime > 50 * 1000)) {// 大于没有读到数据，说明服务端已经断开连接了
							restart();
							return;
						}
					}
					logger.info("Tcp client Receiver data len:"
							+ availableLength);
					if (header == null) {
						if (availableLength >= NetDauHeader.HEADER_LENGTH) {
							System.out.println("reveive data len:"
									+ availableLength);
							byte[] hBytes = new byte[NetDauHeader.HEADER_LENGTH];
							int rs = in.read(hBytes);
							lastReadTime = System.currentTimeMillis();
							if (rs > 0) {
								ByteBuf bytes = Unpooled
										.buffer(NetDauHeader.HEADER_LENGTH);
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
							if (rs == header.getMessageLength()) {// 解析整個包
								System.out.println("reveive data len:" + rs);
								byte[] contentTagBytes = new byte[NetDauHeader.CONTENT_TAG_LENGTH];
								bytes.readBytes(contentTagBytes);
								int contentTag = NUMBERCODEC.bytes2Int(
										contentTagBytes,
										NetDauHeader.CONTENT_TAG_LENGTH);
								byte[] contentBytes = new byte[header
										.getContentLength()
										- NetDauHeader.CONTENT_TAG_LENGTH];
								bytes.readBytes(contentBytes);
								byte[] crcBytes = new byte[NetDauHeader.CRC_LENGTH];
								bytes.readBytes(crcBytes);
								int crc = NUMBERCODEC.bytes2Int(crcBytes,
										NetDauHeader.CRC_LENGTH);
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
											crc, myCrc);
									return;

								}
								header.setDataCrc(crc);
								byte[] endBytes = new byte[NetDauHeader.END_LENGTH];
								bytes.readBytes(endBytes);
								String end = HexUtil.bytesToHex(endBytes);
								header.setEnd(end);
								// TODO 校验end
								byte[] dataDecryptBytes = AESCoder.decrypt(
										contentBytes, aes_secretkey_str,
										aes_iv_str);
								String xmlContent = new String(
										dataDecryptBytes, "UTF-8").trim();
								logger.debug("xmlContent:{}", xmlContent);
								String jsonContent = XmlToJsonUtils
										.getJSONObjectFromXML(xmlContent)
										.toJSONString();
								commonRequest = JSON.parseObject(jsonContent,
										NetDauCommonRequest.class);
								commonRequest = JSON.parseObject(jsonContent,
										NetDauCommonRequest.class);
								commonRequest.setJsonContent(jsonContent);
								System.out.println("commonRequest:"
										+ JSON.toJSONString(commonRequest));
							}
						}
						if (commonRequest != null) {
							if (commonRequest.getCommon().getType()
									.equals("sequence")) {
								NetDauMd5Request request = JSON.parseObject(
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
									.equals("result")) {// 收到result=pass时立即发送设备信息，fail时掐掉连接
								NetDauMd5Response response = JSON.parseObject(
										commonRequest.getJsonContent(),
										NetDauMd5Response.class);
								if (response.getId_validate().getResult()
										.equals("pass")) {
									isAuth = true;
									System.out.println("发送设备信息");
									NetDauDeviceRequest request = new NetDauDeviceRequest();
									CommonDo common = new CommonDo();
									BeanUtils.copyProperties(
											commonRequest.getCommon(), common);
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
								NetDauDeviceResponse response = JSON
										.parseObject(
												commonRequest.getJsonContent(),
												NetDauDeviceResponse.class);
								if (response.getDevice().getDevice_ack()
										.equals("pass")) {
									reportDeviceStatus = 2;
									// 启动心跳线程
									BeatHearter beatHearter = new BeatHearter();
									new Thread(beatHearter).start();
									// 启动日志上传线程
									LogReporter logReporter = new LogReporter();
									new Thread(logReporter).start();
								}
							} else if (commonRequest.getCommon().getType()
									.equals("time")) {
								NetDauBeatHeartResponse response = JSON
										.parseObject(
												commonRequest.getJsonContent(),
												NetDauBeatHeartResponse.class);
								response.getHeart_beat().getTime();
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
class InterValT implements Serializable{
	private static final long serialVersionUID = 1L;
	private String interval;
	private Long startTime;
	private Long endTime;
	private Integer intervalType;
	private Double intervalPrice;
	private String intervalStr;
	/**
	 * 判断当前时间范围是不是在区间内
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean include(Long time){
		if(time>=this.startTime&&time<=this.endTime){
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
	private Date nextTime;//下一次手机时间
	private int period;// 收集频率 分钟
	private String chargeInterValTimes;// "00:00-00:08-1;00:00-00:08-2;00:00-00:08-3;"
	public void setChargeInterValTimes(String chargeInterValTimes) {
		this.chargeInterValTimes = chargeInterValTimes;
	}
	/**
	 * 0 统一单价
	 * 1 尖单价
	 * 2峰单价
	 * 3平单价
	 * 4谷单价
	 * @param reportTime
	 * @return
	 */
    public Integer getChargeType(Date reportTime){
    	int result=0;//统一单价
    	if(StringUtils.isBlank(chargeInterValTimes)){
    		return result;
    	}
		String day=DateUtils.format(reportTime, "yyyyMMdd");
		String intervalTimes[] = StringUtils.split(chargeInterValTimes,";");
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
		for(InterValT chargeInterval:interVals){
		   if(chargeInterval.include(reportTime.getTime())){
		      result=chargeInterval.getIntervalType();
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

}

class MeterE implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String subTermCode;
	private String type;//配电房:dr,楼宇,bd,设备 device
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
