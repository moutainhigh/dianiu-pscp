package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.Needs;

/**
 * NeedsDao
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午2:35:25
 */
public interface NeedsDao extends BaseDao<Needs> {
    public Needs getById(Long id);

    Needs getByProjectId(Long projectId);
}
