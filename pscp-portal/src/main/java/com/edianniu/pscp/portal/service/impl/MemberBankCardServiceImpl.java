package com.edianniu.pscp.portal.service.impl;

import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.bean.wallet.BankType;
import com.edianniu.pscp.mis.service.dubbo.AreaInfoService;
import com.edianniu.pscp.portal.commons.Constants;
import com.edianniu.pscp.portal.dao.MemberBankCardDao;
import com.edianniu.pscp.portal.dao.SysUserDao;
import com.edianniu.pscp.portal.entity.MemberBankCardEntity;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.MemberBankCardService;
import com.edianniu.pscp.portal.utils.BizUtils;
import com.edianniu.pscp.portal.utils.JsonResult;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("memberBankCardService")
public class MemberBankCardServiceImpl implements MemberBankCardService {
    @Autowired
    private MemberBankCardDao memberBankCardDao;

    @Autowired
    private AreaInfoService areaInfoService;

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public MemberBankCardEntity queryObject(Long id) {

        MemberBankCardEntity bean = memberBankCardDao.queryObject(id);

        return bean;
    }

    @Override
    public List<MemberBankCardEntity> queryList(Map<String, Object> map) {
        return memberBankCardDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {

        return memberBankCardDao.queryTotal(map);
    }

    @Override
    public JsonResult save(MemberBankCardEntity memberBankCard) {
        JsonResult json = new JsonResult();
        if (memberBankCard.getProvinceId() == null || memberBankCard.getCityId() == null) {
            json.setSuccess(false);
            json.setMsg("所在地区不能为空");
            return json;
        }
        if (memberBankCard.getBankId() == null || memberBankCard.getBankId() == 0) {
            json.setSuccess(false);
            json.setMsg("所在地区不能为空");
            return json;
        }
        if (StringUtils.isBlank(memberBankCard.getBankBranchName())) {
            json.setSuccess(false);
            json.setMsg("开户地址不能为空");
            return json;
        }
        if (!BizUtils.checkLength(memberBankCard.getBankBranchName(), 32)) {
            json.setSuccess(false);
            json.setMsg("开户地址超过32位");
            return json;
        }
        if (StringUtils.isBlank(memberBankCard.getAccount())) {
            json.setSuccess(false);
            json.setMsg("开户地址不能为空");
            return json;
        }
        /*
        if(BizUtils.isBankCardValid(memberBankCard.getAccount())){
			json.setSuccess(false);
			json.setMsg("银行卡输入错误");
			return json;
		}*/
        memberBankCardDao.save(memberBankCard);
        json.setSuccess(true);
        json.setObject(memberBankCard);
        return json;
    }

    @Override
    public JsonResult update(MemberBankCardEntity memberBankCard) {
        JsonResult json = new JsonResult();
        if (memberBankCard.getProvinceId() == null || memberBankCard.getCityId() == null) {
            json.setSuccess(false);
            json.setMsg("所在地区不能为空");
            return json;
        }
        if (memberBankCard.getBankId() == null || memberBankCard.getBankId() == 0) {
            json.setSuccess(false);
            json.setMsg("所在地区不能为空");
            return json;
        }
        if (StringUtils.isBlank(memberBankCard.getBankBranchName())) {
            json.setSuccess(false);
            json.setMsg("开户地址不能为空");
            return json;
        }
        if (!BizUtils.checkLength(memberBankCard.getBankBranchName(), 32)) {
            json.setSuccess(false);
            json.setMsg("开户地址超过32位");
            return json;
        }
        if (StringUtils.isBlank(memberBankCard.getAccount())) {
            json.setSuccess(false);
            json.setMsg("开户地址不能为空");
            return json;
        }
        /*
        if(BizUtils.isBankCardValid(memberBankCard.getAccount())){
			json.setSuccess(false);
			json.setMsg("银行卡输入错误");
			return json;
		}
		*/
        memberBankCardDao.update(memberBankCard);
        json.setSuccess(true);
        json.setObject(memberBankCard);
        return json;
    }

    @Override
    public void delete(Long id) {
        memberBankCardDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        memberBankCardDao.deleteBatch(ids);
    }

    @Override
    public MemberBankCardEntity getAdminBankCard() {
        SysUserEntity entity = ShiroUtils.getUserEntity();
        MemberBankCardEntity bankCard = new MemberBankCardEntity();
        if (!entity.isAdmin()) {
            SysUserEntity adminUser = sysUserDao.getCompanyAdmin(entity.getCompanyId());
            if (adminUser != null) {
                bankCard.setUid(adminUser.getUserId());
            }
        } else {
            bankCard.setUid(entity.getUserId());
        }
        Map<String, MemberBankCardEntity> bankmap = new HashMap<String, MemberBankCardEntity>();
        bankmap.put("bean", bankCard);
        List<MemberBankCardEntity> bankCards = memberBankCardDao.queryList(bankmap);
        MemberBankCardEntity card = null;
        if (!bankCards.isEmpty() && bankCards.size() > 0) {
            card = bankCards.get(0);
        }

        if (card == null) {
            card = new MemberBankCardEntity();
            card.setBanks(Constants.bankTypes);
            card.setStatus(0);
            card.setUid(bankCard.getUid());
        } else {
            List<BankType> banks = Constants.bankTypes;
            for (BankType bank : banks) {
                if (bank.getId().intValue() == card.getBankId().intValue()) {
                    card.setBankName(bank.getName());
                }
            }
            ProvinceInfo province = areaInfoService.getProvinceInfo(card.getProvinceId());
            if (province != null) {
                card.setProvinceName(province.getName());
            }
            CityInfo city = areaInfoService.getCityInfo(card.getProvinceId(), card.getCityId());
            if (city != null) {
                card.setCityName(city.getName());
            }
            card.setBanks(Constants.bankTypes);
        }
        return card;
    }


}
