package com.edianniu.pscp.das.meter.bean;

import java.io.Serializable;

import com.edianniu.pscp.das.meter.domain.CompanyMeter;

public class WarningBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private MeterLogDo meterLogDo;
	
	private CompanyMeter companyMeter;

	public CompanyMeter getCompanyMeter() {
		return companyMeter;
	}

	public void setCompanyMeter(CompanyMeter companyMeter) {
		this.companyMeter = companyMeter;
	}

	public MeterLogDo getMeterLogDo() {
		return meterLogDo;
	}

	public void setMeterLogDo(MeterLogDo meterLogDo) {
		this.meterLogDo = meterLogDo;
	}

	public WarningBean() {
	}

	public WarningBean(MeterLogDo meterLogDo, CompanyMeter companyMeter) {
		this.meterLogDo = meterLogDo;
		this.companyMeter = companyMeter;
	}
	
	

}
