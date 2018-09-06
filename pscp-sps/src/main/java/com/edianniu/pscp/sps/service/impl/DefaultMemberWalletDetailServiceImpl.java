package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.MemberWalletDetailDao;
import com.edianniu.pscp.sps.domain.MemberWalletDetail;
import com.edianniu.pscp.sps.service.MemberWalletDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultMemberWalletDetailServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-06-30 16:08
 */
@Service
@Repository("memberWalletDetailService")
public class DefaultMemberWalletDetailServiceImpl implements MemberWalletDetailService {

    @Autowired
    private MemberWalletDetailDao memberWalletDetailDao;

    @Override
    public List<MemberWalletDetail> queryList(Map<String, Object> map) {
        return memberWalletDetailDao.queryList(map);
    }
}
