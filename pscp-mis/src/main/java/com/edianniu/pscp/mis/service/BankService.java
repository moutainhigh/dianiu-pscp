package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.wallet.BankType;
import com.edianniu.pscp.mis.domain.Bank;

import java.util.List;
import java.util.Map;

/**
 * ClassName: BankService
 * Author: tandingbo
 * CreateTime: 2017-08-09 16:43
 */
public interface BankService {

    List<Bank> selectAllBank();

    List<Map<String, Object>> getBankIdToCardIcon();

    List<Map<String, Object>> getBankIdToName();

    List<BankType> selectAllBankType();
}
