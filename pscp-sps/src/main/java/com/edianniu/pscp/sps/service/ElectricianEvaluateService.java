package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.domain.ElectricianEvaluate;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-26 11:03:26
 */
public interface ElectricianEvaluateService {

    ElectricianEvaluate queryObject(Long id);

    List<ElectricianEvaluate> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ElectricianEvaluate electricianEvaluate);

    void update(ElectricianEvaluate electricianEvaluate);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    ElectricianEvaluate queryEntityByElectricianWorkOrderId(Long electricianId, Long electricianWorkOrderId);

    Long getId();
}
