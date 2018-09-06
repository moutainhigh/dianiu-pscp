package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.bean.electrician.vo.ElectricianVO;
import com.edianniu.pscp.sps.dao.SpsElectricianDao;
import com.edianniu.pscp.sps.domain.Electrician;
import com.edianniu.pscp.sps.service.SpsElectricianService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DefaultSpsElectricianService
 * Author: tandingbo
 * CreateTime: 2017-05-17 10:41
 */
@Service
@Repository("spsElectricianService")
public class DefaultSpsElectricianService implements SpsElectricianService {

    @Autowired
    private SpsElectricianDao spsElectricianDao;

    @Override
    public List<ElectricianVO> selectElectricianVOByCompanyId(Long companyId) {
        List<ElectricianVO> electricianVOList = new ArrayList<>();
        List<Electrician> electricianList = spsElectricianDao.queryListByCompanyId(companyId);
        if (CollectionUtils.isNotEmpty(electricianList)) {
            for (Electrician electrician : electricianList) {
                ElectricianVO electricianVO = new ElectricianVO();
                electricianVO.setId(electrician.getMemberId());
                electricianVO.setName(electrician.getUserName());
                electricianVOList.add(electricianVO);
            }
        }
        return electricianVOList;
    }

    @Override
    public Electrician selectElectricianVOByUid(Long uid) {
        return spsElectricianDao.selectElectricianVOByUid(uid);
    }
}
