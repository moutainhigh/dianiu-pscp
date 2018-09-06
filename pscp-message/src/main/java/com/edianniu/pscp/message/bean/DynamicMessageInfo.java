/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月10日 下午3:19:17 
 * @version V1.0
 */
package com.edianniu.pscp.message.bean;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月10日 下午3:19:17 
 * @version V1.0
 */
public class DynamicMessageInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String msgId;
	private String memberType;//会员类型
	private String memberName;//会员名字
	private String title;
	private String content;
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMemberType() {
		return memberType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
