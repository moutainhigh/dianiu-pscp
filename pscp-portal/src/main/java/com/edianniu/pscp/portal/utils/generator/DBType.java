/**
 * 
 */
package com.edianniu.pscp.portal.utils.generator;

/**
 * @author cyl
 *
 */
public enum DBType {
   MYSQL(1,"mysql"),POSTGRESQL(2,"postgresql");
   private int value;
   private String desc;
   DBType(int value,String desc){
	   this.value=value;
	   this.desc=desc;
   }
   public int getValue() {
	return value;
   }
   public String getDesc() {
	return desc;
   }
}
