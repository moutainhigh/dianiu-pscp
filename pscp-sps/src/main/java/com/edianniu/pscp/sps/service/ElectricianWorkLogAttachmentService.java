package com.edianniu.pscp.sps.service;

import com.edianniu.pscp.sps.domain.ElectricianWorkLogAttachment;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-09 11:16:43
 */
public interface ElectricianWorkLogAttachmentService {

    ElectricianWorkLogAttachment queryObject(Long id);

    List<ElectricianWorkLogAttachment> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ElectricianWorkLogAttachment electricianWorkLogAttachment);

    void update(ElectricianWorkLogAttachment electricianWorkLogAttachment);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
