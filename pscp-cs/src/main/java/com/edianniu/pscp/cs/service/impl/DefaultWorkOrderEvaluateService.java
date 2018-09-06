package com.edianniu.pscp.cs.service.impl;

import com.edianniu.pscp.cs.dao.WorkOrderEvaluateAttachmentDao;
import com.edianniu.pscp.cs.dao.WorkOrderEvaluateDao;
import com.edianniu.pscp.cs.service.WorkOrderEvaluateService;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: DefaultWorkOrderEvaluateService
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:19
 */
@Service
@Repository("workOrderEvaluateService")
public class DefaultWorkOrderEvaluateService implements WorkOrderEvaluateService {

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private WorkOrderEvaluateDao workOrderEvaluateDao;
    @Autowired
    private WorkOrderEvaluateAttachmentDao workOrderEvaluateAttachmentDao;


    private String picUrl;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
