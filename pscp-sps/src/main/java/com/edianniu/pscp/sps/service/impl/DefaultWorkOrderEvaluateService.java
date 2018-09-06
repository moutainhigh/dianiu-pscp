package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.sps.bean.workorder.evaluate.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.evaluate.WorkOrderEvaluateInfo;
import com.edianniu.pscp.sps.dao.SpsCompanyCustomerDao;
import com.edianniu.pscp.sps.dao.WorkOrderEvaluateAttachmentDao;
import com.edianniu.pscp.sps.dao.WorkOrderEvaluateDao;
import com.edianniu.pscp.sps.domain.CompanyCustomer;
import com.edianniu.pscp.sps.domain.WorkOrderEvaluate;
import com.edianniu.pscp.sps.service.WorkOrderEvaluateService;
import com.edianniu.pscp.sps.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName: DefaultWorkOrderEvaluateService
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:19
 */
@Service
@Repository("workOrderEvaluateService")
public class DefaultWorkOrderEvaluateService implements WorkOrderEvaluateService {

    @Autowired
    private SpsCompanyCustomerDao companyCustomerDao;
    @Autowired
    private WorkOrderEvaluateDao workOrderEvaluateDao;
    @Autowired
    private WorkOrderEvaluateAttachmentDao workOrderEvaluateAttachmentDao;
    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public List<WorkOrderEvaluateInfo> selectPageEvaluateInfo(ListReqData listReqData) {
        List<WorkOrderEvaluate> entityList = workOrderEvaluateDao.queryListEvaluate(listReqData);
        List<WorkOrderEvaluateInfo> evaluateInfoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(entityList)) {
            for (WorkOrderEvaluate evaluateEntity : entityList) {
                WorkOrderEvaluateInfo evaluateInfo = new WorkOrderEvaluateInfo();
                evaluateInfo.setContent(evaluateEntity.getContent());
                evaluateInfo.setCreateTime(DateUtils.format(evaluateEntity.getCreateTime(), DateUtils.DATE_PATTERN));
                evaluateInfo.setServiceSpeed(evaluateEntity.getServiceSpeed());
                evaluateInfo.setServiceQuality(evaluateEntity.getServiceQuality());

                // 客户信息
                evaluateInfo.setCustomerName("");
                evaluateInfo.setCustomerId(evaluateEntity.getCustomerId());
                CompanyCustomer customerEntity = companyCustomerDao.queryObject(evaluateEntity.getCustomerId());
                if (customerEntity != null) {
                    evaluateInfo.setCustomerName(customerEntity.getCpName());
                }

                // 附件信息
                List<Map<String, Object>> attachmentList = workOrderEvaluateAttachmentDao.selectMapByEvaluateId(evaluateEntity.getId());
                if (CollectionUtils.isEmpty(attachmentList)) {
                    attachmentList = new ArrayList<>();
                }
                evaluateInfo.setAttachment(attachmentList);
                evaluateInfo.setAttachmentList(attachmentList);
                evaluateInfoList.add(evaluateInfo);
            }
        }

        return evaluateInfoList;
    }

    @Override
    public WorkOrderEvaluateInfo selectOneEvaluateInfo(ListReqData listReqData) {
        List<WorkOrderEvaluate> entityList = workOrderEvaluateDao.queryListEvaluate(listReqData);
        WorkOrderEvaluateInfo evaluateInfo = new WorkOrderEvaluateInfo();
        if (CollectionUtils.isNotEmpty(entityList)) {
            WorkOrderEvaluate evaluateEntity = entityList.get(0);
            evaluateInfo.setContent(evaluateEntity.getContent());
            evaluateInfo.setCreateTime(DateUtils.format(evaluateEntity.getCreateTime(), DateUtils.DATE_PATTERN));
            evaluateInfo.setServiceSpeed(evaluateEntity.getServiceSpeed());
            evaluateInfo.setServiceQuality(evaluateEntity.getServiceQuality());

            // 客户信息
            evaluateInfo.setCustomerName("");
            evaluateInfo.setCustomerId(evaluateEntity.getCustomerId());
            CompanyCustomer customerEntity = companyCustomerDao.queryObject(evaluateEntity.getCustomerId());
            if (customerEntity != null) {
                evaluateInfo.setCustomerName(customerEntity.getCpName());
            }

            // 附件信息
            List<Map<String, Object>> attachmentList = workOrderEvaluateAttachmentDao.selectMapByEvaluateId(evaluateEntity.getId());
            List<Map<String, Object>> attachments = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(attachmentList)) {
                for (Map<String, Object> attachmentMap : attachmentList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", attachmentMap.get("id"));
                    map.put("fid", attachmentMap.get("fid"));
                    attachments.add(map);

                    String pic = String.format("%s%s", picUrl, attachmentMap.get("fid").toString());
                    attachmentMap.put("fid", fileUploadService.conversionSmallFilePath(pic));
                }
            } else {
                attachmentList = new ArrayList<>();
            }
            evaluateInfo.setAttachmentList(attachmentList);
            evaluateInfo.setAttachment(attachments);
        }
        return evaluateInfo;
    }

    private String picUrl;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
