package com.edianniu.pscp.cms.service;


import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.MemberWallet;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-31 19:16:24
 */
public interface MemberWalletService {

    MemberWallet queryObject(Long id);

    List<MemberWallet> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(MemberWallet memberWallet);

    void update(MemberWallet memberWallet);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    MemberWallet queryEntityByUid(Long uid);
}
