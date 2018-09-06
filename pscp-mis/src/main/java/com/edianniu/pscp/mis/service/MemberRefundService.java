/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月3日 下午4:01:17 
 * @version V1.0
 */
package com.edianniu.pscp.mis.service;

import java.util.List;

import com.edianniu.pscp.mis.domain.MemberRefund;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月3日 下午4:01:17 
 * @version V1.0
 */
public interface MemberRefundService {
   /**
    * 获取失败的退款记录
    * @param limit
    * @return
    */
   public List<MemberRefund> getFailList(int limit);
   /**
    * 获取处理中的退款记录
    * @param limit
    * @return
    */
   public List<MemberRefund> getProcessingList(int limit);
   /**
    * 获取退款记录
    * @param id
    * @return
    */
   public MemberRefund getById(Long id);
   /**
    * 更新退款记录
    * @param memberRefund
    * @return
    */
   public int update(MemberRefund memberRefund);
   /**
    * 保存退款记录
    * @param memberRefund
    */
   public void save(MemberRefund memberRefund);
   
}
