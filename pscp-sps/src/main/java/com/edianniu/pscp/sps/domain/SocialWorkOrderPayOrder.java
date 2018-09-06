package com.edianniu.pscp.sps.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-18 11:03:23
 */
public class SocialWorkOrderPayOrder extends BaseDo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private Long id;
	//$column.comments
	private Long uid;
	//$column.comments
	private String orderId;
	//$column.comments
	private Double amount;
	//$column.comments
	private Date payTime;
	//$column.comments
	private Integer payType;
	//$column.comments
	private String product;
	//$column.comments
	private String productDetail;
	//$column.comments
	private Integer status;
	//$column.comments
	private Date createTime;
	//$column.comments
	private String createUser;
	//$column.comments
	private Date modifiedTime;
	//$column.comments
	private String modifiedUser;
	//$column.comments
	private String payMemo;
	//$column.comments
	private String orderid;
	//$column.comments
	private String paymemo;
	//$column.comments
	private Date paytime;
	//$column.comments
	private Integer paytype;
	//$column.comments
	private String productdetail;

	/**
	 * 设置：${column.comments}
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getUid() {
		return uid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getPayTime() {
		return payTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getPayType() {
		return payType;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getProductDetail() {
		return productDetail;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getModifiedUser() {
		return modifiedUser;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getPayMemo() {
		return payMemo;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getOrderid() {
		return orderid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPaymemo(String paymemo) {
		this.paymemo = paymemo;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getPaymemo() {
		return paymemo;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getPaytime() {
		return paytime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getPaytype() {
		return paytype;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setProductdetail(String productdetail) {
		this.productdetail = productdetail;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getProductdetail() {
		return productdetail;
	}
}
