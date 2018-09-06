package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.MemberWallet;

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
