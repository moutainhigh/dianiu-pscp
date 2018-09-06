package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.sps.bean.workorder.worklog.ElectricianWorkLogInfo;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListQuery;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.dao.ElectricianWorkLogAttachmentDao;
import com.edianniu.pscp.sps.dao.ElectricianWorkLogDao;
import com.edianniu.pscp.sps.dao.SpsElectricianDao;
import com.edianniu.pscp.sps.domain.Electrician;
import com.edianniu.pscp.sps.domain.ElectricianWorkLog;
import com.edianniu.pscp.sps.service.ElectricianWorkLogService;
import com.edianniu.pscp.sps.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DefultElectricianWorkLogService
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:33
 */
@Service
@Repository("electricianWorkLogService")
public class DefaultElectricianWorkLogService implements ElectricianWorkLogService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultElectricianWorkLogService.class);

    @Autowired
    private SpsElectricianDao electricianDao;
    @Autowired
    private ElectricianWorkLogDao electricianWorkLogDao;
    @Autowired
    private ElectricianWorkLogAttachmentDao electricianWorkLogAttachmentDao;
    @Autowired
    private FileUploadService fileUploadService;

    public PageResult<ElectricianWorkLogInfo> selectPageWorkLogInfo(ListQuery listQuery) {
        PageResult<ElectricianWorkLogInfo> result = new PageResult<ElectricianWorkLogInfo>();
        int total = electricianWorkLogDao.queryCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", listQuery.getOffset());
            map.put("limit", listQuery.getPageSize());
            map.put("workOrderId", listQuery.getWorkOrderId());
            map.put("orderId", listQuery.getOrderId());

            List<ElectricianWorkLog> entityList = electricianWorkLogDao.queryListWorkLog(map);
            List<ElectricianWorkLogInfo> responseList = structureElectricianWorkLogInfo(entityList, listQuery.getSource());
            result.setData(responseList);
            nextOffset = listQuery.getOffset() + responseList.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(listQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    
    @Override
    public List<ElectricianWorkLogInfo> selectWorkLogByElectricianId(Long electricianId, Long electricianWorkOrderId) {
        List<ElectricianWorkLog> entityList = electricianWorkLogDao.selectWorkLogByElectricianId(electricianId, electricianWorkOrderId);
        return structureElectricianWorkLogInfo(entityList, null);
    }

    @Override
	public ElectricianWorkLogInfo getDetail(Long id, String source) {
    	ElectricianWorkLogInfo workLogInfo = new ElectricianWorkLogInfo();
    	ElectricianWorkLog workLog = electricianWorkLogDao.queryObject(id);
    	
    	workLogInfo.setId(workLog.getId());
    	workLogInfo.setContent(workLog.getContent());
    	workLogInfo.setCreateTime(DateUtils.format(workLog.getCreateTime(), DateUtils.DATE_TIME_PATTERN));

        // 电工信息
    	workLogInfo.setElectricianName("");
    	workLogInfo.setElectricianId(workLog.getElectricianId());
        Electrician electrician = electricianDao.getByUid(workLog.getElectricianId());
        if (electrician != null) {
        	workLogInfo.setElectricianName(electrician.getUserName());
        	workLogInfo.setTxImg(electrician.getTxImg());
        }

        // 附件信息
        List<Map<String, Object>> attachmentList = electricianWorkLogAttachmentDao.selectMapByWorkLogId(workLog.getId(), null, null);
        if (CollectionUtils.isNotEmpty(attachmentList)) {
            for (Map<String, Object> attachmentMap : attachmentList) {
                String fid = attachmentMap.get("fid").toString();
                if (StringUtils.isBlank(source) || !"APP".equals(source)) {
                    fid = String.format("%s%s", picUrl, fileUploadService.conversionSmallFilePath(fid));
                }
                attachmentMap.put("fid", fid);
            }
        } else {
            attachmentList = new ArrayList<>();
        }
        workLogInfo.setAttachmentList(attachmentList);
    	
		return workLogInfo;
	}
    
    /**
     * 构建电工工单记录信息
     * @param entityList
     * @param source
     * @return
     */
    private List<ElectricianWorkLogInfo> structureElectricianWorkLogInfo(List<ElectricianWorkLog> entityList, String source) {
        List<ElectricianWorkLogInfo> responseList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(entityList)) {
            for (ElectricianWorkLog electricianWorkLog : entityList) {
                ElectricianWorkLogInfo response = new ElectricianWorkLogInfo();
                response.setId(electricianWorkLog.getId());
                response.setContent(electricianWorkLog.getContent());
                response.setCreateTime(DateUtils.format(electricianWorkLog.getCreateTime(), DateUtils.DATE_TIME_PATTERN));

                // 电工信息
                response.setElectricianName("");
                response.setElectricianId(electricianWorkLog.getElectricianId());
                Electrician electrician = electricianDao.getByUid(electricianWorkLog.getElectricianId());
                if (electrician != null) {
                    response.setElectricianName(electrician.getUserName());
                    response.setTxImg(electrician.getTxImg());
                }

                // 附件信息
                List<Map<String, Object>> attachmentList = electricianWorkLogAttachmentDao.selectMapByWorkLogId(electricianWorkLog.getId(), 0, 3);
                if (CollectionUtils.isNotEmpty(attachmentList)) {
                    for (Map<String, Object> attachmentMap : attachmentList) {
                        String fid = attachmentMap.get("fid").toString();
                        if (StringUtils.isBlank(source) || !"APP".equals(source)) {
                            fid = String.format("%s%s", picUrl, fileUploadService.conversionSmallFilePath(fid));
                        }
                        attachmentMap.put("fid", fid);
                    }
                } else {
                    attachmentList = new ArrayList<>();
                }
                response.setAttachmentList(attachmentList);

                responseList.add(response);
            }
        }
        return responseList;
    }
    
    private String picUrl;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

	
}
