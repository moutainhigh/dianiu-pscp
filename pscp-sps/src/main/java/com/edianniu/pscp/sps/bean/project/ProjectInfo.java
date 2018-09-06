package com.edianniu.pscp.sps.bean.project;

import com.edianniu.pscp.sps.bean.Attachment;
import com.edianniu.pscp.sps.bean.DO.ImageDo;
import com.edianniu.pscp.sps.util.DateUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProjectInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String projectNo;

	private Long customerId;

	private String customerName;

	private String description;

	private Long needsId;

	private String roomIds;

	private String rooms;

	private Date startTime;

	private String name;

	private Date endTime;

	private Integer sceneInvestigation;

	private String contractFileIds;

	private Long companyId;

	private Date createTime;

	private String createUser;

	private Date modifiedTime;

	private String modifiedUser;

	private Integer deleted;

	private String cpName;

	private String cpAddress;

	private String cpContact;

	private String cpPhone;

	private String cpContactMobile;
	// 前台显示状态 notStart（未启动），ongoing（进行中），end（已结束）
	private String goStatus;

	private String startTimeConvert;

	private String endTimeConvert;

	private ImageDo contractFileIdImgDo = new ImageDo();
	// 是否由服务商自己创建 1:服务商自己创建 2.需求转项目
	private Integer isCreateBySelf;
	// -1：取消，0：确认中，1：进行中； 2：费用待确认 3：已结算
	private Integer status;
	// 支付状态 0:未支付 1:支付确认 (客户端) 2:支付成功(服务端异步通知) 3:支付失败 4:取消支付
	private Integer payStatus;
	
	private Integer payType;
	
	private Double payAmount;
	
	private Date payTime;
	
	private Date paySynctime;
	
	private Date payAsynctime;
	
	private String payMemo;
	
	// 合作附件
	private List<Attachment> cooperationAttachmentList;
	// 要删除的附件ids,多个用逗号分隔
	private String removedImgs;
	// 实际结算金额附件
	private List<Attachment> actualPriceAttachmentList;
	// 实际结算金额
	private Double actualSettlementAmount;
	
	private String[] orderIds;
	
	private Integer[] payStatuss;

	public String[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}

	public Integer[] getPayStatuss() {
		return payStatuss;
	}

	public void setPayStatuss(Integer[] payStatuss) {
		this.payStatuss = payStatuss;
	}

	public Double getActualSettlementAmount() {
		return actualSettlementAmount;
	}

	public void setActualSettlementAmount(Double actualSettlementAmount) {
		this.actualSettlementAmount = actualSettlementAmount;
	}

	public List<Attachment> getCooperationAttachmentList() {
		return cooperationAttachmentList;
	}

	public void setCooperationAttachmentList(List<Attachment> cooperationAttachmentList) {
		this.cooperationAttachmentList = cooperationAttachmentList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getSceneInvestigation() {
		return sceneInvestigation;
	}

	public void setSceneInvestigation(Integer sceneInvestigation) {
		this.sceneInvestigation = sceneInvestigation;
	}

	public String getContractFileIds() {
		return contractFileIds;
	}

	public void setContractFileIds(String contractFileIds) {
		this.contractFileIds = contractFileIds;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGoStatus() {
		return goStatus;
	}

	public void setGoStatus(String goStatus) {
		this.goStatus = goStatus;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getStartTimeConvert() {
		return startTimeConvert;
	}

	public void setStartTimeConvert(String startTimeConvert) {
		if (!StringUtils.isBlank(startTimeConvert)) {
			Date start = DateUtils.parse(startTimeConvert);
			this.setStartTime(start);
		}
		this.startTimeConvert = startTimeConvert;
	}

	public String getEndTimeConvert() {
		return endTimeConvert;
	}

	public void setEndTimeConvert(String endTimeConvert) {
		if (!StringUtils.isBlank(endTimeConvert)) {
			Date start = DateUtils.parse(endTimeConvert);
			this.setEndTime(start);
		}
		this.endTimeConvert = endTimeConvert;
	}

	public ImageDo getContractFileIdImgDo() {
		return contractFileIdImgDo;
	}

	public void setContractFileIdImgDo(ImageDo contractFileIdImgDo) {
		this.contractFileIdImgDo = contractFileIdImgDo;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCpAddress() {
		return cpAddress;
	}

	public void setCpAddress(String cpAddress) {
		this.cpAddress = cpAddress;
	}

	public String getCpContact() {
		return cpContact;
	}

	public void setCpContact(String cpContact) {
		this.cpContact = cpContact;
	}

	public String getCpPhone() {
		return cpPhone;
	}

	public void setCpPhone(String cpPhone) {
		this.cpPhone = cpPhone;
	}

	public String getCpContactMobile() {
		return cpContactMobile;
	}

	public void setCpContactMobile(String cpContactMobile) {
		this.cpContactMobile = cpContactMobile;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public Integer getIsCreateBySelf() {
		return isCreateBySelf;
	}

	public void setIsCreateBySelf(Integer isCreateBySelf) {
		this.isCreateBySelf = isCreateBySelf;
	}

	public Long getNeedsId() {
		return needsId;
	}

	public void setNeedsId(Long needsId) {
		this.needsId = needsId;
	}

	public String getRemovedImgs() {
		return removedImgs;
	}

	public void setRemovedImgs(String removedImgs) {
		this.removedImgs = removedImgs;
	}

	public List<Attachment> getActualPriceAttachmentList() {
		return actualPriceAttachmentList;
	}

	public void setActualPriceAttachmentList(List<Attachment> actualPriceAttachmentList) {
		this.actualPriceAttachmentList = actualPriceAttachmentList;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getRoomIds() {
		return roomIds;
	}

	public void setRoomIds(String roomIds) {
		this.roomIds = roomIds;
	}

	public String getRooms() {
		return rooms;
	}

	public void setRooms(String rooms) {
		this.rooms = rooms;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getPaySynctime() {
		return paySynctime;
	}

	public void setPaySynctime(Date paySynctime) {
		this.paySynctime = paySynctime;
	}

	public Date getPayAsynctime() {
		return payAsynctime;
	}

	public void setPayAsynctime(Date payAsynctime) {
		this.payAsynctime = payAsynctime;
	}

	public String getPayMemo() {
		return payMemo;
	}

	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}
}
