package com.edianniu.pscp.portal.dao;

import com.edianniu.pscp.portal.entity.MemberWalletEntity;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-31 19:16:24
 */
public interface MemberWalletDao extends BaseDao<MemberWalletEntity> {

    MemberWalletEntity queryEntityByUid(Long uid);
}
