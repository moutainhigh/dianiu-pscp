package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.wallet.BankType;
import com.edianniu.pscp.mis.domain.Bank;

import java.util.List;
import java.util.Map;

/**
 * ClassName: BankDao
 * Author: tandingbo
 * CreateTime: 2017-08-09 16:44
 */
public interface BankDao extends BaseDao<Bank> {
    List<Bank> selectAllBank();

    List<Map<String, Object>> getBankIdToCardIcon();

    List<Map<String, Object>> getBankIdToName();

    List<BankType> selectAllBankType();
}
