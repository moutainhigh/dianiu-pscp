package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianCertificate;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:41
 */
public interface SpsElectricianCertificateDao extends BaseDao<ElectricianCertificate> {

    List<ElectricianCertificate> queryAllList();

    List<Map<String,Object>> queryListMap(Long electricianId);
}
