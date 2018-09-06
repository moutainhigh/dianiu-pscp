package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.wallet.BankType;
import com.edianniu.pscp.mis.dao.BankDao;
import com.edianniu.pscp.mis.domain.Bank;
import com.edianniu.pscp.mis.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DefaultBankService
 * Author: tandingbo
 * CreateTime: 2017-08-09 16:43
 */
@Service
@Repository("bankService")
public class DefaultBankService implements BankService {

    @Autowired
    private BankDao bankDao;

    @Override
    public List<Bank> selectAllBank() {
        return bankDao.selectAllBank();
    }

    @Override
    public List<Map<String, Object>> getBankIdToCardIcon() {
        return bankDao.getBankIdToCardIcon();
    }

    @Override
    public List<Map<String, Object>> getBankIdToName() {
        return bankDao.getBankIdToName();
    }

    @Override
    public List<BankType> selectAllBankType() {
        return bankDao.selectAllBankType();
    }
}
