package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.Needs;

/**
 * NeedsService
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午2:46:14
 */
public interface NeedsService {
    /**
     * 根据id获取需求
     *
     * @param id
     * @return
     */
    public Needs getById(Long id);

    /**
     * 根据项目Id获取需求信息
     *
     * @param projectId
     * @return
     */
    Needs getByProjectId(Long projectId);
}
