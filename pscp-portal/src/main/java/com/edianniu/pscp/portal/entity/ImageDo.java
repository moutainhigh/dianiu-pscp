/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月20日 下午6:50:13 
 * @version V1.0
 */
package com.edianniu.pscp.portal.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月20日 下午6:50:13 
 * @version V1.0
 */
public class ImageDo implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final int STATUS_NORMAL=0;
	public static final int STATUS_ADD=1;
	public static final int STATUS_DEL=-1;
    private Long id;
    private String imgId;
    private String imgUrl;// 1 base64 数据,http://ip:port/fileId
    private Integer status=STATUS_NORMAL;//0,1(新增),-1(删除)
    public boolean isAdd(){
    	if(id==null&&status==STATUS_ADD){
    		return true;
    	}
    	return false;
    }
    public boolean isDel(){
    	if(id!=null&&status==STATUS_DEL){
    		return true;
    	}
    	return false;
    }
    public boolean isBase64(){
    	if(StringUtils.isNoneBlank(imgUrl)){
    		if(imgUrl.indexOf(",")>0){
    			String start=imgUrl.substring(0, imgUrl.indexOf(","));
    			if(start.startsWith("data:")&&start.endsWith(";base64")){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    public ImageDo(){
    	
    }
    public ImageDo(String imgId,String picDomain){
    	this.imgId=imgId;
    	this.imgUrl=picDomain+imgId;
    }
    public ImageDo(Long id,String imgId,String picDomain){
    	this.id=id;
    	this.imgId=imgId;
    	this.imgUrl=picDomain+imgId;
    }
	public Long getId() {
		return id;
	}
	public String getImgId() {
		return imgId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
