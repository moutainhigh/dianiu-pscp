package com.edianniu.pscp.cs.bean.dutylog;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.edianniu.pscp.cs.bean.needs.Attachment;

/**
 * 配电房客户值班日志新增、编辑
 * @author zhoujianjian
 * @date 2017年10月30日 下午1:35:17
 */
public class SaveReqData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	// 日志ID（新增时为空，编辑时不为空）
	private Long id;
	
	private Long roomId;
	
	private String title;
	
	private String content;
	
	private String removedImgs;
	
	private List<Attachment> attachmentList;
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

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
