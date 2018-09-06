package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordDetailsVO;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.query.DefectRecordQuery;
import com.edianniu.pscp.mis.domain.WorkOrderDefectRecord;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface WorkOrderDefectRecordDao {

    int queryCount(DefectRecordQuery defectRecordQuery);

    List<WorkOrderDefectRecord> queryList(DefectRecordQuery defectRecordQuery);

    Long save(WorkOrderDefectRecord defectRecord);

    WorkOrderDefectRecord getEntityById(Long id);

    void updateSolveInfo(WorkOrderDefectRecord defectRecord);

    void updateMapCondition(Map<String, Object> updateMap);

    List<DefectRecordVO> getRepairDefectRecord(List<Long> ids);

    List<DefectRecordVO> queryListDefectRecordVO(DefectRecordQuery defectRecordQuery);
    
    int queryDefectRecordVOCount(DefectRecordQuery defectRecordQuery);

    DefectRecordDetailsVO getDefectRecordVOById(Long id);

    List<DefectRecordVO> queryListDefectRecord(Map<String, Object> param);
}
