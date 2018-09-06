package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.MemberWalletDao;
import com.edianniu.pscp.sps.domain.MemberWallet;
import com.edianniu.pscp.sps.service.MemberWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("memberWalletService")
public class DefaultMemberWalletServiceImpl implements MemberWalletService {
    @Autowired
    private MemberWalletDao memberWalletDao;

    @Override
    public MemberWallet queryObject(Long id) {
        return memberWalletDao.queryObject(id);
    }

    @Override
    public List<MemberWallet> queryList(Map<String, Object> map) {
        return memberWalletDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return memberWalletDao.queryTotal(map);
    }

    @Override
    public void save(MemberWallet memberWallet) {
        memberWalletDao.save(memberWallet);
    }

    @Override
    public void update(MemberWallet memberWallet) {
        memberWalletDao.update(memberWallet);
    }

    @Override
    public void delete(Long id) {
        memberWalletDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        memberWalletDao.deleteBatch(ids);
    }

    @Override
    public MemberWallet queryEntityByUid(Long uid) {
        return memberWalletDao.queryEntityByUid(uid);
    }

}
