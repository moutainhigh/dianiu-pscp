package com.edianniu.pscp.mis.bean.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class MultiClientRequest extends NetworkTrack {
	private String hsman;
	private String hstype;
	private int sdkver;
	private int extplat;
	private int screenwidth;
	private int screenheight;
	private int dpi;
	private int ram;
	private int rom;
	private String imsi;
	private String imei;
	private String loc;
	private String mcc;
	private String mnc;
	private String operator;
	private String romver;
	private String networktype;
	private String macver;
	private int clientver;
	private int clienttype;
	private String channelno;

	public String getHsman() {
		return this.hsman;
	}

	public void setHsman(String hsman) {
		this.hsman = hsman;
	}

	public String getHstype() {
		return this.hstype;
	}

	public void setHstype(String hstype) {
		this.hstype = hstype;
	}

	public int getSdkver() {
		return this.sdkver;
	}

	public void setSdkver(int sdkver) {
		this.sdkver = sdkver;
	}

	public int getExtplat() {
		return this.extplat;
	}

	public void setExtplat(int extplat) {
		this.extplat = extplat;
	}

	public int getScreenwidth() {
		return this.screenwidth;
	}

	public void setScreenwidth(int screenwidth) {
		this.screenwidth = screenwidth;
	}

	public int getScreenheight() {
		return this.screenheight;
	}

	public void setScreenheight(int screenheight) {
		this.screenheight = screenheight;
	}

	public int getDpi() {
		return this.dpi;
	}

	public void setDpi(int dpi) {
		this.dpi = dpi;
	}

	public int getRam() {
		return this.ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getRom() {
		return this.rom;
	}

	public void setRom(int rom) {
		this.rom = rom;
	}

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getMcc() {
		return this.mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getMnc() {
		return this.mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRomver() {
		return this.romver;
	}

	public void setRomver(String romver) {
		this.romver = romver;
	}

	public String getNetworktype() {
		return this.networktype;
	}

	public void setNetworktype(String networktype) {
		this.networktype = networktype;
	}

	public String getMacver() {
		return this.macver;
	}

	public void setMacver(String macver) {
		this.macver = macver;
	}

	public int getClientver() {
		return this.clientver;
	}

	public void setClientver(int clientver) {
		this.clientver = clientver;
	}

	public int getClienttype() {
		return this.clienttype;
	}

	public void setClienttype(int clienttype) {
		this.clienttype = clienttype;
	}

	public String getChannelno() {
		return this.channelno;
	}

	public void setChannelno(String channelno) {
		this.channelno = channelno;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
