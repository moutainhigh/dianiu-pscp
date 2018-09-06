/**
 * 
 */
package com.edianniu.pscp.cms.bean.req;

import java.io.Serializable;

/**
 * @author cyl
 *
 */
public class FileUploadRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long uid;//用户ID
	private String token;//token
	private String file;//文件二进制流通过base64转换为字符串
	public Long getUid() {
		return uid;
	}
	public String getToken() {
		return token;
	}
	public String getFile() {
		return file;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
}
