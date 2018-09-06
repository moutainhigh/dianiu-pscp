/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午3:30:18 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午3:30:18 
 * @version V1.0
 */
public class AttributeDo implements Serializable{
	private static final long serialVersionUID = 1L;
    private String operation;
    private String id;
    private String addr;
    private String tp;
    private String name;
    private String com;
    private String coding;
    private String error;
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
