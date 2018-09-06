package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.ElectricianEvaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-26 11:03:26
 */
public interface ElectricianEvaluateDao extends BaseDao<ElectricianEvaluate> {
    Long getId();

    ElectricianEvaluate queryEntityByElectricianWorkOrderId(@Param("electricianId") Long electricianId,
                                                            @Param("electricianWorkOrderId") Long electricianWorkOrderId);

    int saveBatchEvaluate(List<ElectricianEvaluate> evaluateList);

    void updateBatchEvaluate(List<ElectricianEvaluate> updateEvaluateList);
}
