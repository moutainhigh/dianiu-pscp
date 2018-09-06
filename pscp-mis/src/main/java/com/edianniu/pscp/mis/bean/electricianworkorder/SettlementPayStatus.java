/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月5日 下午2:53:03 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.electricianworkorder;

/**
 * 电工工单结算支付状态
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月5日 下午2:53:03 
 * @version V1.0
 */
public enum SettlementPayStatus {
	UNPAY(0, "未支付"),
    PAIED(1, "已支付");
    private final Integer value;
    private final String name;

    SettlementPayStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}

