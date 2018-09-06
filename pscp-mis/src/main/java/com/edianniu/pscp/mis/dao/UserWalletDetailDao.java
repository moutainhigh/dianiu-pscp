package com.edianniu.pscp.mis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.UserWalletDetail;

/**
 * @author AbnerElk
 */
public interface UserWalletDetailDao {

    void insert(UserWalletDetail userwalleDetail);

    public UserWalletDetail getUserwalleDetail(Long id);

    public void withdrawalscashAudit(UserWalletDetail userWalletDetail);

    public void withdrawalscashPayConfirm(UserWalletDetail userWalletDetail);
    
    public UserWalletDetail getUserwalleDetailByOrderId(String orderId);

    /**
     * 获取用户所有资金详情记录
     *
     * @param uid
     * @return list
     */
    public List<UserWalletDetail> getDetailsByUid(Long uid);
    /**
     * 查询总金额
     * @param uid
     * @param orderId
     * @param type
     * @return
     */
    public Double getSumAmount(@Param("uid")Long uid,@Param("orderId")String orderId, @Param("type")int type);

}
