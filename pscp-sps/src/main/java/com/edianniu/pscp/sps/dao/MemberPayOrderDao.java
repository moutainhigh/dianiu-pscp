package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.MemberPayOrder;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-23 17:22:14
 */
public interface MemberPayOrderDao extends BaseDao<MemberPayOrder> {

    MemberPayOrder queryEntityByOrderId(String orderId);
}
