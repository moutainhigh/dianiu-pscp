/**
 *
 */
package com.edianniu.pscp.mis.dao;
import com.edianniu.pscp.mis.domain.MemberRefund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @author cyl
 */
public interface MemberRefundDao {

    public void save(MemberRefund memberRefund);

    public MemberRefund getById(Long id);

    public int update(MemberRefund memberRefund);
    
    List<MemberRefund> queryList(@Param("limit") int limit,@Param("status")int status);
}
