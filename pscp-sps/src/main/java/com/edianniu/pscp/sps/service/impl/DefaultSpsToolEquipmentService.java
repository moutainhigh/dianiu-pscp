package com.edianniu.pscp.sps.service.impl;

import com.edianniu.pscp.sps.bean.toolequipment.vo.ToolEquipmentVO;
import com.edianniu.pscp.sps.dao.SpsToolEquipmentDao;
import com.edianniu.pscp.sps.domain.ToolEquipment;
import com.edianniu.pscp.sps.service.SpsToolEquipmentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DefaultSpsToolEquipmentService
 * Author: tandingbo
 * CreateTime: 2017-05-17 15:25
 */
@Service
@Repository("spsToolEquipmentService")
public class DefaultSpsToolEquipmentService implements SpsToolEquipmentService {

    @Autowired
    private SpsToolEquipmentDao spsToolEquipmentDao;

    @Override
    public List<ToolEquipmentVO> selectAllToolEquipmentVOByCompanyId(Long companyId) {
        List<ToolEquipmentVO> toolEquipmentVOList = new ArrayList<>();
        List<ToolEquipment> toolEquipmentList = spsToolEquipmentDao.queryListByCompanyId(companyId);
        if (CollectionUtils.isNotEmpty(toolEquipmentList)) {
            for (ToolEquipment entity : toolEquipmentList) {
                ToolEquipmentVO toolEquipmentVO = new ToolEquipmentVO();
                toolEquipmentVO.setId(entity.getId());
                String name = entity.getName();
                if (StringUtils.isNotBlank(entity.getModel())) {
                    name = String.format("%s-%s", name, entity.getModel());
                }
                toolEquipmentVO.setName(name);
                toolEquipmentVOList.add(toolEquipmentVO);
            }
        }
        return toolEquipmentVOList;
    }
}
