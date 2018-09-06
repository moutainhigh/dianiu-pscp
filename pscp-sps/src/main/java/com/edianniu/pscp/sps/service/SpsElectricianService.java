package com.edianniu.pscp.sps.service;


import com.edianniu.pscp.sps.bean.electrician.vo.ElectricianVO;
import com.edianniu.pscp.sps.domain.Electrician;

import java.util.List;

/**
 * ClassName: SpsElectricianService
 * Author: tandingbo
 * CreateTime: 2017-05-17 10:41
 */
public interface SpsElectricianService {
    List<ElectricianVO> selectElectricianVOByCompanyId(Long companyId);

    Electrician selectElectricianVOByUid(Long uid);
}
