/**
 *
 */
package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AbnerElk
 */
public class UserWalletDetail extends BaseDo implements Serializable {
	private static final long serialVersionUID = 1L;   
	public final static  int PAY_SUCCESS=1;
    public final static int PAY_FALSE=-1;
    public final static int PAYING =0;
    public final static int CHECKING =0;
    public final static int CHECK_SUCCESS=1;
    public final static int CHECK_FALSE=-1;
   
    private Long id;
    private Long uid;
    private int type;
    private int dealType;
    private String  dealAccount;
    private String orderId;//业务ID
    private String payOrderId;//支付订单ID
    private Long fundTarget;
    private Long fundSource;
    private String transactionId;
    private double availableAmount;
    private double availableFreezingAmount;
    
    private Integer checkStatus;
	private String checkUser;
	private Date checkTime;
	private String checkMemo;
	
	private Integer payStatus;
	private String payUser;
	private String payMemo;
	private Date payTime;
	private double amount;
	private String payTransactionId;
	private Date dealTime;
    private String remark;

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public double getAvailableFreezingAmount() {
        return availableFreezingAmount;
    }

    public void setAvailableFreezingAmount(double availableFreezingAmount) {
        this.availableFreezingAmount = availableFreezingAmount;
    }

    public String getDealAccount() {
        return dealAccount;
    }

    public void setDealAccount(String dealAccount) {
        this.dealAccount = dealAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public Long getFundTarget() {
        return fundTarget;
    }

    public void setFundTarget(Long fundTarget) {
        this.fundTarget = fundTarget;
    }

    public Long getFundSource() {
        return fundSource;
    }

    public void setFundSource(Long fundSource) {
        this.fundSource = fundSource;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckMemo() {
		return checkMemo;
	}

	public void setCheckMemo(String checkMemo) {
		this.checkMemo = checkMemo;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}

	public String getPayMemo() {
		return payMemo;
	}

	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getPayTransactionId() {
		return payTransactionId;
	}

	public void setPayTransactionId(String payTransactionId) {
		this.payTransactionId = payTransactionId;
	}



	
    
}
