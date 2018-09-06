/**
 *
 */
package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.wallet.WalletQuery;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.domain.UserWalletDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cyl
 */
public interface UserWalletDao {

    public void save(UserWallet userwalle);

    public UserWallet get(Long uid);

    public int update(UserWallet userwalle);

    public int exist(Long uid);

    int queryDaybookCount(WalletQuery query);

    List<UserWalletDetail> queryDaybook(WalletQuery query);

    UserWallet getByUid(Long uid);

    int addAmount(@Param("uid") Long uid, @Param("amount") Double amount, @Param("modifiedUser") String modifiedUser);

    int subtractFreezingAmount(@Param("uid") Long uid, @Param("amount") Double amount, @Param("modifiedUser") String modifiedUser);
}
