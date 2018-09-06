package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianResume;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-24 14:15:01
 */
public interface ElectricianResumeDao extends BaseDao<ElectricianResume> {

    ElectricianResume queryEntityByElectricianId(Long electricianId);
}
