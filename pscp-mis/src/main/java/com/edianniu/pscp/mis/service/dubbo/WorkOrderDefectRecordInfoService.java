package com.edianniu.pscp.mis.service.dubbo;

import com.edianniu.pscp.mis.bean.defectrecord.*;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;

import java.util.List;

/**
 * ClassName: WorkOrderDefectRecordInfoService
 * Author: tandingbo
 * CreateTime: 2017-09-12 10:36
 */
public interface WorkOrderDefectRecordInfoService {
    ListResult pageListDefectRecord(ListReqData reqData);

    SelectDataResult getDefectRecordSelectData(SelectDataReqData reqData);

    SaveResult saveDefectRecord(SaveReqData reqData);

    DetailsResult getDefectRecord(DetailsReqData reqData);

    CorrectionResult correction(CorrectionReqData reqData);

    RemoveResult removeDefectRecord(RemoveReqData reqData);

    /**
     * 获取修复缺陷记录
     *
     * @param ids
     * @return
     */
    List<DefectRecordVO> getRepairDefectRecord(List<Long> ids);
}
