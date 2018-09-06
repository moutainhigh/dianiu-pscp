package com.edianniu.pscp.cms.dao;

import com.edianniu.pscp.cms.entity.MemberWallet;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-31 19:16:24
 */
public interface MemberWalletDao extends BaseDao<MemberWallet> {

    MemberWallet queryEntityByUid(Long uid);
}
