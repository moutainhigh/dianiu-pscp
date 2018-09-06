package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.domain.ElectricianEvaluateAttachment;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-26 11:03:26
 */
public interface ElectricianEvaluateAttachmentService {

    ElectricianEvaluateAttachment queryObject(Long id);

    List<ElectricianEvaluateAttachment> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ElectricianEvaluateAttachment electricianEvaluateAttachment);

    void update(ElectricianEvaluateAttachment electricianEvaluateAttachment);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    List<ElectricianEvaluateAttachment> queryListByEvaluateId(Long evaluateId);
}
