/**
 * 
 */
package com.edianniu.pscp.portal.bean.resp;

import java.io.Serializable;

import com.edianniu.pscp.portal.commons.ResultCode;

/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月21日 上午10:47:40 
 * @version V1.0
 */
public class FileUploadResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	private int resultCode=ResultCode.SUCCESS;
	private String resultMessage;
	private String fileId;
	private String fileUrl;
	public int getResultCode() {
		return resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public String getFileId() {
		return fileId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
