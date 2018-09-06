package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.MemberWalletDetail;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberWalletDetailDao
 * Author: tandingbo
 * CreateTime: 2017-06-30 15:55
 */
public interface MemberWalletDetailDao extends BaseDao<MemberWalletDetail> {

    List<MemberWalletDetail> queryList(Map<String, Object> map);

}
