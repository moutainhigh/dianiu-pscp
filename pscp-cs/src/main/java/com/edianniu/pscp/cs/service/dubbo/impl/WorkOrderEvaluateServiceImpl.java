package com.edianniu.pscp.cs.service.dubbo.impl;

import com.edianniu.pscp.cs.service.WorkOrderEvaluateService;
import com.edianniu.pscp.cs.service.dubbo.WorkOrderEvaluateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: WorkOrderEvaluateServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:21
 */
@Service
@Repository("workOrderEvaluateInfoService")
public class WorkOrderEvaluateServiceImpl implements WorkOrderEvaluateInfoService {

    @Autowired
    @Qualifier("workOrderEvaluateService")
    private WorkOrderEvaluateService workOrderEvaluateService;

}
