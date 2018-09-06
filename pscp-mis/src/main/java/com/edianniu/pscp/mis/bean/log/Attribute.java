/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午3:30:18 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午3:30:18 
 * @version V1.0
 */
public class Attribute implements Serializable{
	private static final long serialVersionUID = 1L;
    private String operation;
    private String id;
    private String addr;
    private String tp;
    private String name;
    private String com;
    private String coding;
    private String error;
    private String sml;//采集项标识-Ep+
    private String sid;//采集项ID-1
    private String pm;//采集项名字--有功电度
    private String ut;//采集项单位--KWh
    //idx	透传命令序号
    //meterId	对应的仪表ID
    //timeout	仪表响应最大时间（毫秒），不填该属性时默认为100毫秒  (100 ~ 65536)
    //retry	重发次数,不填该属性时默认为1次,重发间隔1秒钟  (100 ~ 1000)
    //size	透传命令长度，为cmd后跟的字符串长度除以2
    //cmd	控制命令，格式16进制字符串，2个字符表示一个字节
       //采集器不解析该段数据，直接发送给仪表id对应的仪表
    //err 错误码
    //F0 -> 正常
    //F1 -> 没找到当前meterId 仪表
    //F2 -> 当前仪表对应的串口参数没有配置
    //F3 -> 仪表返回超时
   // <?xml version="1.0" encoding="UTF-8" ?>
   // <root>
   //   <common>
   //     <building_id>330100A002</building_id>
   //     <gateway_id>01</gateway_id>
   //     <type>control_ack</type>
   //   </common>
   //   <instruction operation="control_ack">
   //     <control_info>
   //       <control_ack idx="0" meterId="3" err="F0" size="14" data="FE6819180913000068DC01373116"/>
   //     </control_info>
   //   </instruction>
   // </root>
    private String idx;
    private String meterId;
    private String timeout;
    private String retry;
    private String size;
    private String cmd;
    private String err;
    private String data;

    public String getOperation() {
		return operation;
	}
	public String getId() {
		return id;
	}
	public String getAddr() {
		return addr;
	}
	public String getTp() {
		return tp;
	}
	public String getName() {
		return name;
	}
	public String getCom() {
		return com;
	}
	public String getCoding() {
		return coding;
	}
	public String getError() {
		return error;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public void setTp(String tp) {
		this.tp = tp;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public void setCoding(String coding) {
		this.coding = coding;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getSml() {
		return sml;
	}
	public String getSid() {
		return sid;
	}
	public String getPm() {
		return pm;
	}
	public String getUt() {
		return ut;
	}
	public void setSml(String sml) {
		this.sml = sml;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public void setPm(String pm) {
		this.pm = pm;
	}
	public void setUt(String ut) {
		this.ut = ut;
	}
	public String getIdx() {
		return idx;
	}
	public String getMeterId() {
		return meterId;
	}
	public String getTimeout() {
		return timeout;
	}
	public String getRetry() {
		return retry;
	}
	public String getSize() {
		return size;
	}
	public String getCmd() {
		return cmd;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public void setRetry(String retry) {
		this.retry = retry;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
