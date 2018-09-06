package com.edianniu.pscp.cs.dao;

import com.edianniu.pscp.cs.bean.needs.NeedsInfo;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsMarketVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsViewVO;
import com.edianniu.pscp.cs.bean.query.NeedsMarketListQuery;
import com.edianniu.pscp.cs.domain.Needs;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NeedsDao extends BaseDao<NeedsInfo> {

    void save(NeedsInfo needsInfo);

    int update(NeedsInfo needsInfo);

    NeedsVO getNeedsByOrderId(@Param("orderId") String orderId, @Param("needsId") Long needsId);

    Integer queryNeedsListVOCount(HashMap<String, Object> queryMap);

    List<NeedsVO> queryList(HashMap<String, Object> queryMap);

    int queryNeedsMarketCount(NeedsMarketListQuery listQuery);
    
    List<NeedsMarketVO> queryNeedsMarketList(NeedsMarketListQuery listQuery);

    int queryNeedsMarketListNotRequiredCompanyIdCount(NeedsMarketListQuery listQuery);
    
    List<NeedsMarketVO> queryNeedsMarketListNotRequiredCompanyId(NeedsMarketListQuery listQuery);

    // 后台使用
    int getNeedsViewListCount(HashMap<String, Object> queryNeedsViewMap);

    // 后台使用
    List<NeedsViewVO> queryNeedsViewList(HashMap<String, Object> queryNeedsViewMap);

    Needs getCustomerNeedsById(@Param("id") Long id, @Param("orderId") String orderId);
    
    // 获取超时需求
    List<Long> getOvertimeNeeds(HashMap<String, Object> queryMap);

    Map<String,Object> getSendMessagePushCustomer(Long id);
}
