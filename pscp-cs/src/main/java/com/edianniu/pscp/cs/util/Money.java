/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月12日 下午4:49:38 
 * @version V1.0
 */
package com.edianniu.pscp.cs.util;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月12日 下午4:49:38 
 * @version V1.0
 */
public class Money {
  private Double money=0.00d;
  public Money(){
	  
  }
  public Money(Double value){
	  this.money=value;
  }
  /**
   * 增加金额
   * @param value
   * @return
   */
  public  Money add(Double value){
	  money=money+value;
	return this;
  }
  public Money remove(Double value){
	  money=money-value;
	  return this;
  }
}
