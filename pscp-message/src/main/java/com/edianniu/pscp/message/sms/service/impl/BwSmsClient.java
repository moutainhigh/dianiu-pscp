/**
 *
 */
package com.edianniu.pscp.message.sms.service.impl;

import com.edianniu.pscp.message.commons.ResultCode;
import com.edianniu.pscp.message.result.SmsResult;
import com.edianniu.pscp.message.sms.dao.SmsSendLogDao;
import com.edianniu.pscp.message.sms.domain.SmsMessage;
import com.edianniu.pscp.message.sms.domain.SmsSendLog;
import com.edianniu.pscp.message.web.util.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cyl
 */
@Service
@Repository("bwSmsClient")
public class BwSmsClient {
    private static final Logger logger = LoggerFactory.getLogger(BwSmsService.class);
    private String uri = "http://cloud.baiwutong.com:8080";
    private String id = "wj5360";
    private String passwd = "7809tj";
    private String md5TdCode = "6ece5a51e1ace644615cbcface9da4e0";
    private String smsSwitch="open";//open|close
    private Set<String> filterMobiles=new HashSet<>();
    @Autowired
    private SmsSendLogDao smsSendLogDao;

    /**
     * 0 暂时没有数据 9 访问地址不存在 -11 账户关闭 -16 用户名错误或用户名不存在 -17 密码错误 -18 不支持客户主动获取 -19
     * 用户访问超过我方限制频率（间隔200毫秒访问一次） 108 指定访问IP错误 Xml格式字符串
     * 有待推送的状态报告，并且状态报告以xml格式的字符串返回。
     */
    public SmsResult smsReport() {
        SmsResult result = new SmsResult();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost post = new HttpPost(uri + "/post_report.do");

            List<NameValuePair> parames = new ArrayList<NameValuePair>();
            parames.add(new BasicNameValuePair("corp_id", id));
            parames.add(new BasicNameValuePair("user_id", id));
            parames.add(new BasicNameValuePair("corp_pwd", passwd));
            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(parames, Charset.forName("gbk"));

            post.setEntity(requestEntity);
            post.addHeader("Connection", "Keep-Alive");
            post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
            // post.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
            // false);
            CloseableHttpResponse res = httpClient.execute(post);
            InputStream input = res.getEntity().getContent();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int len = 0;
            byte[] by = new byte[1024];
            while ((len = input.read(by)) != -1) {
                output.write(by, 0, len);
            }
            Header[] headers = res.getAllHeaders();
            int statusCode = res.getStatusLine().getStatusCode();

            logger.info("Sms statusCode:" + statusCode);
            for (Header h : headers) {
                logger.info(h.toString());
            }
            String content = new String(output.toByteArray());
            if (StringUtils.isBlank(content)) {
                logger.error("bw sms postReport error:{}", content);
            } else {
                if ("0".equals(content)) {
                    logger.info("bw sms postReport:{}", content + " 暂时没有数据");
                    result.set(ResultCode.SUCCESS, "暂时没有数据");
                    result.setCode(content);
                } else if ("9".equals(content)) {
                    logger.error("bw sms postReport:{}", content + " 访问地址不存在");
                    result.set(ResultCode.ERROR_201, "访问地址不存在");
                    result.setCode(content);
                } else if ("-11".equals(content)) {
                    logger.error("bw sms postReport:{}", content + " 账户关闭");
                    result.set(ResultCode.ERROR_201, "账户关闭");
                    result.setCode(content);
                } else if ("-16".equals(content)) {
                    logger.error("bw sms postReport:{}", content + " 用户名错误或用户名不存在");
                    result.set(ResultCode.ERROR_201, "账户关闭");
                    result.setCode(content);
                } else if ("-17".equals(content)) {
                    logger.error("bw sms postReport:{}", content + " 密码错误");
                    result.set(ResultCode.ERROR_201, "密码错误");
                    result.setCode(content);
                } else if ("-18".equals(content)) {
                    logger.error("bw sms postReport:{}", content + " 不支持客户主动获取");
                    result.set(ResultCode.ERROR_201, "不支持客户主动获取");
                    result.setCode(content);
                } else if ("-19".equals(content)) {
                    logger.error("bw sms postReport:{}", content + " 用户访问超过我方限制频率（间隔200毫秒访问一次）");
                    result.set(ResultCode.ERROR_201, "用户访问超过我方限制频率（间隔200毫秒访问一次）");
                    result.setCode(content);
                } else if ("108".equals(content)) {
                    logger.error("bw sms postReport:{}", content + " 指定访问IP错误");
                    result.set(ResultCode.ERROR_201, "指定访问IP错误");
                    result.setCode(content);
                } else {
                    List<Report> l = this.decodeXml(content);
                    for (Report report : l) {
                        if (report.getCorpId().equals(id)) {
                            SmsSendLog smsSendLog = smsSendLogDao.getByMsgId(report.getMsgId());
                            if (smsSendLog != null && smsSendLog.getMobile().equals(report.getMobile())) {
                                smsSendLog.setErr(report.getErr());
                                smsSendLog.setFailDesc(report.getFailDesc());
                                smsSendLog.setReportTime(DateUtils.parse(report.getReportTime(), "yyyy-MM-dd HH:mm:ss"));
                                smsSendLog.setSubSeq(Integer.parseInt(report.getSubSeq()));
                                if (report.getErr().equals("0")) {//成功
                                    smsSendLog.setStatus(1);
                                } else {//失败
                                    smsSendLog.setStatus(2);
                                }
                                smsSendLogDao.updateEntity(smsSendLog);

                            }
                        }

                    }
                    result.setCode("report num:" + l.size());
                }
            }
        } catch (ClientProtocolException e) {
            logger.error("bw sms postReport error:{}", e);
            result.set(ResultCode.ERROR_201, "接口异常");
        } catch (IOException e) {
            logger.error("bw sms postReport error:{}", e);
            result.set(ResultCode.ERROR_201, "接口异常");
        }
        return result;
    }

    public static void main(String args[]) throws ParserConfigurationException,
            SAXException, IOException {
        BwSmsClient client = new BwSmsClient();
        /*String s = "<?xml version='1.0' encoding='GBK' ?><reports><report><corp_id>test</corp_id>"
                + "<mobile>13810000001</mobile><sub_seq>0</sub_seq><msg_id>12345asd</msg_id><err>2</err><fail_desc>undeliver</fail_desc>"
				+ "<report_time>2010-07-02 00:00:00</report_time></report><report><corp_id>test</corp_id><mobile>13810000002</mobile><sub_seq>0</sub_seq>"
				+ "<msg_id>12345asd</msg_id><err>2</err><fail_desc>undeliver</fail_desc><report_time>2010-07-02 00:00:00</report_time></report></reports>";
		List<Report> list=client.decodeXml(s);
		System.out.println(list.size());*/
        client.smsReport();

    }

    class Report {
        private String msgId;
        private String corpId;
        private String subSeq;
        private String mobile;
        private String err;
        private String failDesc;
        private String reportTime;

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getCorpId() {
            return corpId;
        }

        public void setCorpId(String corpId) {
            this.corpId = corpId;
        }

        public String getSubSeq() {
            return subSeq;
        }

        public void setSubSeq(String subSeq) {
            this.subSeq = subSeq;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getErr() {
            return err;
        }

        public void setErr(String err) {
            this.err = err;
        }

        public String getFailDesc() {
            return failDesc;
        }

        public void setFailDesc(String failDesc) {
            this.failDesc = failDesc;
        }

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }
    }

    /**
     * 解析report xml结果
     *
     * @param content
     * @return
     */
    private List<Report> decodeXml(String content) {
        List<Report> list = new ArrayList<Report>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(
                    content)));

            Element root = doc.getDocumentElement();
            NodeList firstNodeList = root.getChildNodes();
            if (firstNodeList != null) {
                for (int i = 0; i < firstNodeList.getLength(); i++) {//一级
                    Node node = firstNodeList.item(i);
                    NodeList secondNodeList = node.getChildNodes();
                    Report report = new Report();
                    for (int j = 0; j < secondNodeList.getLength(); j++) {//二级
                        Node node1 = secondNodeList.item(j);
                        String nodeName = node1.getNodeName();
                        String nodeValue = node1.getFirstChild().getNodeValue();
                        if (nodeName.equals("msg_id")) {
                            report.setMsgId(nodeValue);
                        } else if (nodeName.equals("corp_id")) {
                            report.setCorpId(nodeValue);
                        } else if (nodeName.equals("sub_seq")) {
                            report.setSubSeq(nodeValue);
                        } else if (nodeName.equals("mobile")) {
                            report.setMobile(nodeValue);
                        } else if (nodeName.equals("err")) {
                            report.setErr(nodeValue);
                        } else if (nodeName.equals("fail_desc")) {
                            report.setFailDesc(nodeValue);
                        } else if (nodeName.equals("report_time")) {
                            report.setReportTime(nodeValue);
                        }
                    }
                    list.add(report);

                }
            }

        } catch (Exception e) {
            logger.error("decodeXml:{}", e);
        }
        return list;

    }

    /**
     * 0#数字 提交成功的手机数 -10 余额不足 -11 账号关闭 -12 短信内容超过1000字（包括1000字）或为空 -13
     * 手机号码超过200个或合法的手机号码为空，或者手机号码与通道代码正则不匹配 -14 msg_id超过50个字符或没有传msg_id字段 -16
     * 用户名不存在 -18 访问ip错误 -19 密码错误 或者业务代码错误 或者通道关闭 或者业务关闭
     *
     * @param smsMessage
     * @return
     */
    public SmsResult sendBwSms(SmsMessage smsMessage) {
        SmsResult result = new SmsResult();
        result.setResultCode(ResultCode.ERROR_201);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String msgId = System.nanoTime() + "";
        try {
        	String code="";
        	boolean isFilterMobile=filterMobiles.contains(smsMessage.getMobile());
        	if(smsSwitch.equals("open")&&!isFilterMobile){
        		HttpPost post = new HttpPost(uri + "/post_sms.do");
                List<NameValuePair> parames = new ArrayList<NameValuePair>();
                parames.add(new BasicNameValuePair("id", id));
                parames.add(new BasicNameValuePair("MD5_td_code", md5TdCode));
                parames.add(new BasicNameValuePair("mobile", smsMessage.getMobile()));
                parames.add(new BasicNameValuePair("msg_content", smsMessage.getContent()));
                
                parames.add(new BasicNameValuePair("msg_id", msgId));
                parames.add(new BasicNameValuePair("ext", ""));
                UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(parames, Charset.forName("gbk"));
                post.setEntity(requestEntity);
                post.addHeader("Connection", "Keep-Alive");
                post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
                // post.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
                // false);
                CloseableHttpResponse res = httpClient.execute(post);
                InputStream input = res.getEntity().getContent();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                int len = 0;
                byte[] by = new byte[1024];
                while ((len = input.read(by)) != -1) {
                    output.write(by, 0, len);
                }
                Header[] headers = res.getAllHeaders();
                int statusCode = res.getStatusLine().getStatusCode();

                logger.info("Sms statusCode:" + statusCode);
                for (Header h : headers) {
                    logger.info(h.toString());
                }
                code = new String(output.toByteArray());
                logger.info("Sms code:" + code);
                if (StringUtils.isNoneBlank(code)) {
                    String[] codes = code.split("#");
                    if (codes != null && codes.length == 2) {
                        if ("0".equals(codes[0])) {
                            result.setResultCode(ResultCode.SUCCESS);
                        }
                    }
                }
                result.setCode(code);
        	}
        	else{
        		result.setResultMessage("临时关闭短信通道或者部分手机临时禁用短信通道");
        		code="close";
        	}
            SmsSendLog smsSendLog = new SmsSendLog();
            smsSendLog.setChannelType(1);
            smsSendLog.setContent(smsMessage.getContent());
            smsSendLog.setCreateUser("系统");
            smsSendLog.setDeleted(0);
            smsSendLog.setMobile(smsMessage.getMobile());
            smsSendLog.setMsgId(msgId);
            smsSendLog.setMsgStatus(code);
            smsSendLog.setStatus(0);
            smsSendLogDao.saveEntity(smsSendLog);
        } catch (ClientProtocolException e) {
            logger.error("sendBwSms error:{}", e);
            result.setResultMessage("通道异常");
        } catch (IOException e) {
            logger.error("sendBwSms error:{}", e);
            result.setResultMessage("通道异常");
        }

        return result;
    }

    @Value(value = "${sms.bw.uri:}")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Value(value = "${sms.bw.uid:}")
    public void setId(String id) {
        this.id = id;
    }

    @Value(value = "${sms.bw.md5TdCode:}")
    public void setMd5TdCode(String md5TdCode) {
        this.md5TdCode = md5TdCode;
    }

    @Value(value = "${sms.bw.passwd:}")
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    @Value(value = "${sms.bw.smsSwitch:}")
	public void setSmsSwitch(String smsSwitch) {
		this.smsSwitch = smsSwitch;
	}
    @Value(value = "${sms.send.filter.mobiles:}")
    public void setFilterMobiles(String filterMobilesStr) {
       if(StringUtils.isNoneBlank(filterMobilesStr)){
    	   String[] mobiles=StringUtils.split(filterMobilesStr,",");
    	   if(mobiles!=null){
    		   for(String mobile:mobiles){
    			   filterMobiles.add(mobile);
    		   }
    	   }
    	   
       }
    }

}
