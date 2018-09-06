/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.edianniu.pscp.mis.bean.pay;

import java.text.ParseException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;




import com.edianniu.pscp.mis.util.DateUtil;

/**
 * 微信post过来的xml转换成bean
 *
 * @author AbnerElk
 */
public class WxPayPush extends PayPush {

    // 本类中的所有seter方法.对应父类中的属性,
    // 创建seter 是为了方便偷懒, 直接设置接口返回属性值
    // 本类不提供getter方法
    // 使用属性, 请使用父类中的getter方法获取
    
    // 注: 本类中, 暂未实现coupon_id_$n  & coupon_fee_$n

    public WxPayPush() {
        // 类型为微信支付
        this.payType = PayType.WEIXIN.getValue();
    }

    public void setReturn_code(String return_code) {
        super.returnCode = return_code;
    }

    public void setReturn_msg(String return_msg) {
        super.returnMsg = return_msg;
    }

    public void setAppid(String appid) {
        super.appid = appid;
    }

    public void setMch_id(String mch_id) {
        super.mchId = mch_id;
    }

    public void setDevice_info(String device_info) {
        super.deviceInfo = device_info;
    }

    public void setNonce_str(String nonce_str) {
        super.nonceStr = nonce_str;
    }

    public void setSign(String sign) {
        super.sign = sign;
    }

    public void setResult_code(String result_code) {
        super.resultCode = result_code;
    }

    public void setErr_code(String err_code) {
        super.errCode = err_code;
    }

    public void setErr_code_des(String err_code_des) {
        super.errCodeDes = err_code_des;
    }

    public void setOpenid(String openid) {
        super.openid = openid;
    }

    public void setIs_subscribe(String is_subscribe) {
        super.isSubscribe = is_subscribe;
    }

    public void setTrade_type(String trade_type) {
        super.tradeType = trade_type;
    }

    public void setBank_type(String bank_type) {
        super.bankType = bank_type;
    }

    public void setTotal_fee(String total_fee) {
        super.totalFee = total_fee;
    }

    public void setFee_type(String fee_type) {
        super.feeType = fee_type;
    }

    public void setCash_fee(String cash_fee) {
        super.cashFee = cash_fee;
    }

    public void setCash_fee_type(String cash_fee_type) {
        super.cashFeeType = cash_fee_type;
    }


    public void setTransaction_id(String transaction_id) {
        super.transactionId = transaction_id;
    }

    public void setOut_trade_no(String out_trade_no) {
        super.outTradeNo = out_trade_no;
    }

    public void setAttach(String attach) {
        super.attach = attach;
    }

    public void setTime_end(String time_end) {
    	super.timeEnd = DateUtil.getFormatDate(DateUtil.formString(time_end, "yyyyMMddHHmmss"), "yyyy-MM-dd HH:mm:ss");
    }

    public void setCoupon_count(String coupon_count) {
        super.couponCount = coupon_count;
    }
    private String orderId;
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }
}
