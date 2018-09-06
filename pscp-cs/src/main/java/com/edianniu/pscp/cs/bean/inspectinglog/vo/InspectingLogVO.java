package com.edianniu.pscp.cs.bean.inspectinglog.vo;

import java.io.Serializable;

public class InspectingLogVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Integer type;
	
	private String historyFixCheckDate;
	
	private String historyActualCheckDate;
	
	private Integer status;
	
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHistoryFixCheckDate() {
		return historyFixCheckDate;
	}

	public void setHistoryFixCheckDate(String historyFixCheckDate) {
		this.historyFixCheckDate = historyFixCheckDate;
	}

	public String getHistoryActualCheckDate() {
		return historyActualCheckDate;
	}

	public void setHistoryActualCheckDate(String historyActualCheckDate) {
		this.historyActualCheckDate = historyActualCheckDate;
	}



}
