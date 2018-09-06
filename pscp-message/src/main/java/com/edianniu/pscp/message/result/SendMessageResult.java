package com.edianniu.pscp.message.result;

import com.edianniu.pscp.message.commons.Result;

public class SendMessageResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 0>的数字	提交成功#提交成功手机号数量
     * -10	    余额不足
     * -11  	账号关闭
     * -12	    短信内容超过500字或为空
     * -13	    手机号码超过200个或合法的手机号码为空或手机号码与通道对应运营商不匹配
     * -14	    msg_id超过50个字符或未传msg_id字段
     * -16	    用户名不存在
     * -18	    访问ip错误	可支持多ip或号段绑定。
     * -19	    密码错误 *或业务代码错误或通道关闭或业务关闭，确认MD5_td_code是否正确
     * -20	    扩展码不合法
     */
    private String code;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
