package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.bean.identifications.StructureIdentificationsData;
import com.edianniu.pscp.sps.bean.identifications.vo.IdentificationsVO;
import com.edianniu.pscp.sps.bean.safetymeasures.StructureSafetyMeasuresData;
import com.edianniu.pscp.sps.bean.safetymeasures.vo.SafetyMeasuresVO;
import com.edianniu.pscp.sps.service.DataDictionaryDetailsService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典详情(未实现数据库储存数据)
 * ClassName: DefaultDataDictionaryDetailsService
 * Author: tandingbo
 * CreateTime: 2017-05-17 17:27
 */
@Service
@Repository("dataDictionaryDetailsService")
public class DefaultDataDictionaryDetailsServiceImpl implements DataDictionaryDetailsService {

    @Override
    public List<SafetyMeasuresVO> selectAllSafetyMeasures() {
        return StructureSafetyMeasuresData.getSafetyMeasuresVOList();
    }

    @Override
    public List<IdentificationsVO> selectAllIdentifications() {
        return StructureIdentificationsData.getIdentificationsVOList();
    }
}
