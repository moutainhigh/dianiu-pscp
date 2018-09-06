package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.dao.ElectricianEvaluateAttachmentDao;
import com.edianniu.pscp.mis.domain.ElectricianEvaluateAttachment;
import com.edianniu.pscp.mis.service.ElectricianEvaluateAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DefaultElectricianEvaluateAttachmentService
 * Author: tandingbo
 * CreateTime: 2017-05-12 10:32
 */
@Service
@Repository("electricianEvaluateAttachmentService")
public class DefaultElectricianEvaluateAttachmentService implements ElectricianEvaluateAttachmentService {

    @Autowired
    private ElectricianEvaluateAttachmentDao electricianEvaluateAttachmentDao;

    @Override
    public List<ElectricianEvaluateAttachment> selectAllByEvaluateId(Long evaluateId) {
        return electricianEvaluateAttachmentDao.selectAllByEvaluateId(evaluateId);
    }
}
