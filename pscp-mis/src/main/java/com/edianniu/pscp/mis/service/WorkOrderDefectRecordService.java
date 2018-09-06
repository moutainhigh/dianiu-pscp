package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordDetailsVO;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.query.DefectRecordQuery;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.domain.WorkOrderDefectRecord;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface WorkOrderDefectRecordService {

    PageResult<DefectRecordVO> queryList(DefectRecordQuery defectRecordQuery);

    void save(WorkOrderDefectRecord defectRecord, UserInfo userInfo);

    WorkOrderDefectRecord getEntityById(Long id);

    void updateSolveInfo(WorkOrderDefectRecord defectRecord, UserInfo userInfo);

    void updateMapCondition(Map<String, Object> updateMap);

    /**
     * 获取修复缺陷记录
     *
     * @param ids
     * @return
     */
    List<DefectRecordVO> getRepairDefectRecord(List<Long> ids);

    DefectRecordDetailsVO getDefectRecordVOById(Long id);

    List<DefectRecordVO> queryListDefectRecord(Map<String, Object> param);
}
