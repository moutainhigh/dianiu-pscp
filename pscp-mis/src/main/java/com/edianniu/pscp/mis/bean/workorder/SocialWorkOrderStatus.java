/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:39:20
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.workorder;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:39:20
 */
public enum SocialWorkOrderStatus {
    UN_PUBLISH(0, "未发布"),
    RECRUITING(1, "招募中"),
    RECRUIT_END(2, "招募结束"),
    FINISHED(3, "已完成"),
    LIQUIDATED(4, "已结算"),
    CANCEL(-1, "取消");

    private int value;
    private String desc;

    SocialWorkOrderStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
