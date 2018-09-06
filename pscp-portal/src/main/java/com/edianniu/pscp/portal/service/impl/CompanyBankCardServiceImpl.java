package com.edianniu.pscp.portal.service.impl;

import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.bean.wallet.BankType;
import com.edianniu.pscp.mis.service.dubbo.AreaInfoService;
import com.edianniu.pscp.portal.commons.Constants;
import com.edianniu.pscp.portal.dao.CompanyBankCardDao;
import com.edianniu.pscp.portal.entity.CompanyBankCardEntity;
import com.edianniu.pscp.portal.service.CompanyBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("companyBankCardService")
public class CompanyBankCardServiceImpl implements CompanyBankCardService {
    @Autowired
    private CompanyBankCardDao companyBankCardDao;

    @Autowired
    private AreaInfoService areaInfoService;

    @Override
    public CompanyBankCardEntity queryObject(Long id) {
        return companyBankCardDao.queryObject(id);
    }

    @Override
    public List<CompanyBankCardEntity> queryList(Map<String, Object> map) {
        return companyBankCardDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return companyBankCardDao.queryTotal(map);
    }

    @Override
    public void save(CompanyBankCardEntity companyBankCard) {
        companyBankCardDao.save(companyBankCard);
    }

    @Override
    public void update(CompanyBankCardEntity companyBankCard) {
        companyBankCardDao.update(companyBankCard);
    }

    @Override
    public void delete(Long id) {
        companyBankCardDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        companyBankCardDao.deleteBatch(ids);
    }

    @Override
    public CompanyBankCardEntity getBankCardByCompany(Long companyId) {
        CompanyBankCardEntity bean = companyBankCardDao.getBankCardByCompany(companyId);
        if (bean == null) {
            bean = new CompanyBankCardEntity();
            bean.setCompanyId(companyId);
            bean.setBanks(Constants.bankTypes);
        } else {
            List<BankType> banks = Constants.bankTypes;
            for (BankType bank : banks) {
                if (bank.getId().intValue() == bean.getBankId().intValue()) {
                    bean.setBankName(bank.getName());
                }
            }
            ProvinceInfo province = areaInfoService.getProvinceInfo(bean.getProvinceId());
            if (province != null) {
                bean.setProvinceName(province.getName());
            }
            CityInfo city = areaInfoService.getCityInfo(bean.getProvinceId(), bean.getCityId());
            if (city != null) {
                bean.setCityName(city.getName());
            }
            bean.setBanks(Constants.bankTypes);
        }
        return bean;
    }

}
