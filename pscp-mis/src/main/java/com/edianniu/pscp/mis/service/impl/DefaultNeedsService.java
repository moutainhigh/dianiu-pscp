package com.edianniu.pscp.mis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.mis.dao.NeedsDao;
import com.edianniu.pscp.mis.domain.Needs;
import com.edianniu.pscp.mis.service.NeedsService;

/**
 * DefaultNeedsService
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午3:15:48
 */
@Service
@Repository("needsService")
public class DefaultNeedsService implements NeedsService {
    @Autowired
    private NeedsDao needsDao;

    @Override
    public Needs getById(Long id) {
        return needsDao.getById(id);
    }

    /**
     * 根据项目Id获取需求信息
     *
     * @param projectId
     * @return
     */
    @Override
    public Needs getByProjectId(Long projectId) {
        return needsDao.getByProjectId(projectId);
    }

}
