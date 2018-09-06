package com.edianniu.pscp.mis.bean.pay;

import com.edianniu.pscp.mis.util.MoneyUtils;


/**
 * 支付宝支付结果push
 *
 * @author AbnerElk
 */
public class AliPayPush extends PayPush {
    // 本类中的所有seter方法.对应父类中的属性,
    // 创建seter 是为了方便偷懒, 直接设置接口返回属性值
    // 本类不提供getter方法
    // 使用属性, 请使用父类中的getter方法获取

    // 注: 暂未实现 use_coupon & discount （支付宝)
    public AliPayPush() {
        // 类型为支付宝支付
        this.payType = PayType.ALIPAY.getValue();
    }
    public void setNotify_time(String notify_time) {
        super.notifyTime = notify_time;
    }

    public void setNotify_type(String notify_type) {
        super.notifyType = notify_type;
    }

    public void setNotify_id(String notify_id) {
        super.notifyId = notify_id;
    }

    public void setSign_type(String sign_type) {
        super.signType = sign_type;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setOut_trade_no(String out_trade_no) {
        super.outTradeNo = out_trade_no;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPayment_type(String payment_type) {
        this.paymentType = payment_type;
    }

    public void setTrade_no(String trade_no) {
        this.transactionId = trade_no;
    }

    public void setTrade_status(String trade_status) {
        this.tradeStatus = trade_status;
    }

    public void setSeller_id(String seller_id) {
        this.sellerId = seller_id;
    }

    public void setSeller_email(String seller_email) {
        this.sellerEmail = seller_email;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyerId = buyer_id;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyerEmail = buyer_email;
    }

    public void setTotal_fee(String total_fee) {
        // 支付宝返回结果为 元  转换单位为分
        this.totalFee = MoneyUtils.yuanToStrFen(total_fee);
    }
    public void setTotal_amount(String total_amount) {
        // 支付宝返回结果为 元
       String total = MoneyUtils.yuanToStrFen(total_amount);
       this.totalAmount=total;
       this.totalFee=total;
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

    public void setGmt_create(String gmt_create) {
        this.gmtCreate = gmt_create;
    }

    public void setGmt_payment(String gmt_payment) {
        this.timeEnd = gmt_payment;
    }

    public void setIs_total_fee_adjust(String is_total_fee_adjust) {
        this.isTotalFeeAdjust = is_total_fee_adjust;
    }

}
