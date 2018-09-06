package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ReportInfo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Attribute attr;
    private String sequence;
    private String parser;
    private String time;
    private String total; 
    private String current; 

    private List<MeterInfo> meter;

	public Attribute getAttr() {
		return attr;
	}
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}
	public String getSequence() {
		return sequence;
	}
	public String getParser() {
		return parser;
	}
	public String getTime() {
		return time;
	}
	public List<MeterInfo> getMeter() {
		return meter;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public void setParser(String parser) {
		this.parser = parser;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setMeter(List<MeterInfo> meter) {
		this.meter = meter;
	}
	public String getTotal() {
		return total;
	}
	public String getCurrent() {
		return current;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
