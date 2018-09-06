package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.domain.MemberWalletDetail;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberWalletDetailService
 * Author: tandingbo
 * CreateTime: 2017-06-30 16:07
 */
public interface MemberWalletDetailService {

    List<MemberWalletDetail> queryList(Map<String, Object> map);
}
