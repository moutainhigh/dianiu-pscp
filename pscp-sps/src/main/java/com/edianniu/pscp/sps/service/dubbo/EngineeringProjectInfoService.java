package com.edianniu.pscp.sps.service.dubbo;

import com.edianniu.pscp.sps.bean.project.*;
import java.util.List;
import java.util.Map;

/**
 * ClassName: EngineeringProjectInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-16 09:33
 */
public interface EngineeringProjectInfoService {
    ListProjectsResult getListByCompanyIdAndCustomerId(ListProjectsReqData reqData);

    ProjectInfo getById(Long id);

    List<ProjectInfo> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    SaveResult save(Long uid, ProjectInfo projectInfo);

    UpdateResult update(Long uid, ProjectInfo projectInfo);

    DeleteResult delete(Long uid, Long id);

    DeleteResult deleteBatch(Long uid, Long[] ids);
    
    SettleResult settle(Long uid, ProjectInfo projectInfo);


}
