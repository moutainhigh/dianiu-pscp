package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.electrician.ElectricianVOResult;
import com.edianniu.pscp.sps.bean.electrician.vo.ElectricianVO;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.Company;
import com.edianniu.pscp.sps.service.SpsCompanyService;
import com.edianniu.pscp.sps.service.SpsElectricianService;
import com.edianniu.pscp.sps.service.dubbo.SpsElectricianInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SpsElectricianServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-17 10:42
 */
@Service
@Repository("spsElectricianInfoService")
public class SpsElectricianServiceImpl implements SpsElectricianInfoService {
    private static final Logger logger = LoggerFactory.getLogger(SpsElectricianServiceImpl.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("spsCompanyService")
    private SpsCompanyService spsCompanyService;
    @Autowired
    @Qualifier("spsElectricianService")
    private SpsElectricianService spsElectricianService;

    @Override
    public ElectricianVOResult selectElectricianVOByCompanyId(Long uid) {
        ElectricianVOResult result = new ElectricianVOResult();
        try {
            GetUserInfoResult userInfoResult = userInfoService.getUserInfo(uid);
            if (!userInfoResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage("用户信息不存在");
                return result;
            }

            UserInfo userInfo = userInfoResult.getMemberInfo();

            Company company = spsCompanyService.getCompanyById(userInfo.getCompanyId());
            if (company == null || !company.getStatus().equals(2)) {
                result.setElectricianList(new ArrayList<ElectricianVO>());
                return result;
            }

            List<ElectricianVO> electricianVOList = spsElectricianService.selectElectricianVOByCompanyId(userInfo.getCompanyId());
            result.setElectricianList(electricianVOList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }
}
