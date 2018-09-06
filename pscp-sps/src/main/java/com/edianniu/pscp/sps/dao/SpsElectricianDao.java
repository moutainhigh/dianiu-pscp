package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.Electrician;

import java.util.List;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-04-14 18:10:04
 */
public interface SpsElectricianDao extends BaseDao<Electrician> {
    public Electrician getByUid(Long uid);

    List<Electrician> queryListByCompanyId(Long companyId);

    Electrician selectElectricianVOByUid(Long uid);
}
