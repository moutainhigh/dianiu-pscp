package com.edianniu.pscp.cs.bean.request.dutylog;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房值班日志新增、编辑
 * @author zhoujianjian
 * @date 2017年10月30日 下午12:02:05
 */
@JSONMessage(messageCode = 1002141)
public class SaveRequest extends TerminalRequest{
	
	private Long id;
	
	private Long roomId;
	
	private String title;
	
	private String content;
	
	private String removedImgs;
	
	private List<Attachment> attachmentList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String getRemovedImgs() {
		return removedImgs;
	}

	public void setRemovedImgs(String removedImgs) {
		this.removedImgs = removedImgs;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
