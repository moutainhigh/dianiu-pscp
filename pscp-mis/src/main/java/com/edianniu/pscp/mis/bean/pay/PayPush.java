package com.edianniu.pscp.mis.bean.pay;

/**
 * @author AbnerElk
 */
public class PayPush {
    // 注: 暂未实现coupon_id_$n  & coupon_fee_$n (微信)
    // 注: 暂未实现 use_coupon & discount （支付宝)
    // 支付类型  2=支付宝,3=微信,4=银联
    protected Integer payType;
    // wx返回状态码(SUCCESS/FAIL)
    protected String returnCode;
    // wx返回信息
    protected String returnMsg;
    // wx公众账号ID
    protected String appid;
    // wx商户号
    protected String mchId;
    // 商户号
    protected String deviceInfo;
    // wx随机字符串
    protected String nonceStr;
    // wx签名 / ali签名
    protected String sign;
    // wx业务结果
    protected String resultCode;
    // wx错误代码
    protected String errCode;
    // wx错误描述
    protected String errCodeDes;
    // wx用户ID
    protected String openid;
    // wx是否关注公众号
    protected String isSubscribe;
    // wx交易类型
    protected String tradeType;
    // wx付款银行
    protected String bankType;
    // wx总金额 / ali交易金额（老版本）
    protected String totalFee;
    //ali新版本
    protected String totalAmount;
    // wx货币种类
    protected String feeType;
    // wx现金支付金额
    protected String cashFee;
    // wx现金支付货币类型
    protected String cashFeeType;
    // wx代金券或立减优惠使用数量
    protected String couponCount;
    // wx支付订单号 / ali支付宝交易号
    protected String transactionId;
    // wx商户订单号 / ali商户订单号
    protected String outTradeNo;
    // wx商家数据包
    protected String attach;
    // wx支付完成时间
    protected String timeEnd;
    // ali通知时间
    protected String notifyTime;
    // ali通知类型
    protected String notifyType;
    // ali通知校验ID
    protected String notifyId;
    // ali签名方式
    protected String signType;
    // ali商品标题
    protected String subject;
    // ali支付类型
    protected String paymentType;
    // ali交易状态
    protected String tradeStatus;
    // ali卖家支付宝用户号
    protected String sellerId;
    // ali卖家支付宝账号
    protected String sellerEmail;
    // ali买家支付宝用户号
    protected String buyerId;
    // ali买家支付宝账号
    protected String buyerEmail;
    // ali购买数量
    protected String quantity;
    // ali商品单价
    protected String price;
    // ali商品描述
    protected String body;
    // ali交易创建时间
    protected String gmtCreate;
    // ali是否调整总价
    protected String isTotalFeeAdjust;
    // ali退款状态
    protected String refundStatus;
    // ali退款时间
    protected String gmtRefund;
    
    /**
	 * unionpay签名
	 */
	protected String signature;
	
	/**
	 * unionpay签名证书，代付使用到
	 */
	protected String certId;
	
	/**
	 * unionpay加密证书，代付使用到
	 */
	protected String encryptCertId;
	/**
	 * unionpay交易类型
	 */
	protected String txnType;
	
	/**
	 * unionpay交易子类
	 */
	protected String txnSubType;
	
	/**
	 * unionpay产品类型
	 */
	protected String bizType;
	/**
	 * unionpay渠道类型
	 */
	protected String channelType;
	/**
	 * unionpay接入类型
	 */
	protected String accessType;
	/**
	 * unionpay订单id
	 */
	protected String orderId;
	/**
	 * unionpay消费金额，单位为分
	 */
	protected Long txnAmt;
	/**
	 * unionpay交易时间
	 */
	protected String txnTime;
	/**
	 * unionpay交易超时时间
	 */
	protected String payTimeout;
	/**
	 * unionpay消费银行卡号
	 */
	protected String accNo;
	
	/**
	 * unionpay订单描述
	 */
	protected String orderDesc;
	/**
	 * unionpay银行流水账号，控件支付调用
	 */
	protected String tn;	
	/**
	 * unionpay由银联返回，用于在后续类交易中唯一标识一笔交易
	 */
	protected String queryId;	
	
	/**
	 *unionpay返回码
	 */
	protected String respCode;
	/**
	 *unionpay返回消息
	 */
	protected String respMsg;
	/**
	 *unionpay系统跟踪号,收单机构对账时使用，该域由银联系统产生
	 */
	protected String traceNo;
	/**
	 *unionpay交易传输时间
	 */
	protected String traceTime;
	/**
	 *unionpay清算时间
	 */
	protected String settleDate;
	/**
	 *unionpay证件类型，代付使用
	 */
	protected String certifTp;
	/**
	 *unionpay证件类型号，代付使用
	 */
	protected String certifId;
	/**
	 *unionpay姓名，代付使用
	 */
	protected String customerNm;
	/**
	 *unionpay交易类型
	 */
	protected Integer type;
	/**
	 *unionpay原交易的查询返回码
	 */
	protected String origRespCode;
	/**
	 *unionpay原交易的查询返回信息
	 */
	protected String origRespMsg;

    public Integer getPayType() {
        return payType;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public String getAppid() {
        return appid;
    }

    public String getMchId() {
        return mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public String getOpenid() {
        return openid;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public String getSignType() {
        return signType;
    }

    public String getSubject() {
        return subject;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getBody() {
        return body;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public String getIsTotalFeeAdjust() {
        return isTotalFeeAdjust;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public String getGmtRefund() {
        return gmtRefund;
    }

	public String getSignature() {
		return signature;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getEncryptCertId() {
		return encryptCertId;
	}

	public void setEncryptCertId(String encryptCertId) {
		this.encryptCertId = encryptCertId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(Long txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getPayTimeout() {
		return payTimeout;
	}

	public void setPayTimeout(String payTimeout) {
		this.payTimeout = payTimeout;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getCertifTp() {
		return certifTp;
	}

	public void setCertifTp(String certifTp) {
		this.certifTp = certifTp;
	}

	public String getCertifId() {
		return certifId;
	}

	public void setCertifId(String certifId) {
		this.certifId = certifId;
	}

	public String getCustomerNm() {
		return customerNm;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOrigRespCode() {
		return origRespCode;
	}

	public void setOrigRespCode(String origRespCode) {
		this.origRespCode = origRespCode;
	}

	public String getOrigRespMsg() {
		return origRespMsg;
	}

	public void setOrigRespMsg(String origRespMsg) {
		this.origRespMsg = origRespMsg;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public void setCashFee(String cashFee) {
		this.cashFee = cashFee;
	}

	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}

	public void setCouponCount(String couponCount) {
		this.couponCount = couponCount;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
		this.isTotalFeeAdjust = isTotalFeeAdjust;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public void setGmtRefund(String gmtRefund) {
		this.gmtRefund = gmtRefund;
	}
    
    
    
    
}
