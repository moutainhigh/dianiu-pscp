package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.dao.MemberPayOrderDao;
import com.edianniu.pscp.sps.domain.MemberPayOrder;
import com.edianniu.pscp.sps.service.MemberPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("memberPayOrderService")
public class MemberPayOrderServiceImpl implements MemberPayOrderService {
    @Autowired
    private MemberPayOrderDao memberPayOrderDao;

    @Override
    public MemberPayOrder queryObject(Long id) {
        return memberPayOrderDao.queryObject(id);
    }

    @Override
    public List<MemberPayOrder> queryList(Map<String, Object> map) {
        return memberPayOrderDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return memberPayOrderDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(MemberPayOrder memberPayOrder) {
        memberPayOrderDao.save(memberPayOrder);
    }

    @Override
    @Transactional
    public void update(MemberPayOrder memberPayOrder) {
        memberPayOrderDao.update(memberPayOrder);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        memberPayOrderDao.delete(id);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        memberPayOrderDao.deleteBatch(ids);
    }

    @Override
    public MemberPayOrder queryEntityByOrderId(String orderId) {
        return memberPayOrderDao.queryEntityByOrderId(orderId);
    }

}
