package com.edianniu.pscp.cs.bean.dutylog.vo;

import java.io.Serializable;
import java.util.List;

import com.edianniu.pscp.cs.bean.needs.Attachment;

public class DutyLogVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String title;
	
	private String content;
	
	private String createTime;
	
	private String roomName;
	
	private Long roomId;
	
	private List<Attachment> attachmentList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	
	
	
}
