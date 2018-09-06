package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.domain.ElectricianResume;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-24 14:15:01
 */
public interface ElectricianResumeService {

    ElectricianResume queryObject(Long id);

    List<ElectricianResume> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ElectricianResume electricianResume);

    void update(ElectricianResume electricianResume);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    ElectricianResume queryEntityByElectricianId(Long electricianId);
}
