package com.edianniu.pscp.mis.dao;

import java.util.List;

import com.edianniu.pscp.mis.domain.PayType;


/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月4日 下午5:19:07 
 * @version V1.0
 */
public interface PayTypeDao {
    public List<PayType> getPayTypes();
  
}
