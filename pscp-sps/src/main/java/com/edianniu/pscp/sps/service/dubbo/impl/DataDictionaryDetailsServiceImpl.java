package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.sps.bean.identifications.IdentificationsVOResult;
import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;
import com.edianniu.pscp.sps.bean.safetymeasures.SafetyMeasuresVOResult;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.DataDictionaryDetailsService;
import com.edianniu.pscp.sps.service.dubbo.DataDictionaryDetailsInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DataDictionaryDetailsServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-17 17:30
 */
@Service
@Repository("dataDictionaryDetailsInfoService")
public class DataDictionaryDetailsServiceImpl implements DataDictionaryDetailsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(DataDictionaryDetailsServiceImpl.class);

    @Autowired
    @Qualifier("dataDictionaryDetailsService")
    private DataDictionaryDetailsService dataDictionaryDetailsService;

    @Override
    public SafetyMeasuresVOResult selectAllSafetyMeasures() {
        SafetyMeasuresVOResult result = new SafetyMeasuresVOResult();
        try {
            List<SafetyMeasuresVO> safetyMeasuresVOList = dataDictionaryDetailsService.selectAllSafetyMeasures();
            result.setSafetyMeasuresVOList(safetyMeasuresVOList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }

    @Override
    public IdentificationsVOResult selectAllIdentifications() {
        IdentificationsVOResult result = new IdentificationsVOResult();
        try {
            List<IdentificationsVO> identificationsVOList = dataDictionaryDetailsService.selectAllIdentifications();
            result.setIdentificationsVOList(identificationsVOList);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }
}
