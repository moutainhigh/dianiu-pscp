package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianCertificateImage;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:42
 */
public interface SpsElectricianCertificateImageDao extends BaseDao<ElectricianCertificateImage> {
    public List<ElectricianCertificateImage> queryListByElectricianId(Long electricianId);

    public void deleteByElectricianId(Long electricianId);

    List<Map<String, Object>> queryListMap(Long electricianId);
}
