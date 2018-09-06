package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ReportDo implements Serializable{
	private static final long serialVersionUID = 1L;
    private AttributeDo attr;
    private String sequence;
    private String parser;
    private String time;
    private String total; 
    private String current; 

    private List<MeterDo> meter;

	public AttributeDo getAttr() {
		return attr;
	}
	public void setAttr(AttributeDo attr) {
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
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public void setParser(String parser) {
		this.parser = parser;
	}
	public void setTime(String time) {
		this.time = time;
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
	
	public List<MeterDo> getMeter() {
		return meter;
	}
	public void setMeter(List<MeterDo> meter) {
		this.meter = meter;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
