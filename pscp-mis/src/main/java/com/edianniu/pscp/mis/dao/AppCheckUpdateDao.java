/**
 * 
 */
package com.edianniu.pscp.mis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.AppCheckUpdate;



/**
 * @author cyl
 *
 */
public interface AppCheckUpdateDao {
   public List<AppCheckUpdate> getAppCheckUpdates(@Param("appType")Integer appType,@Param("appPkg")String appPkg,@Param("appVer")Long appVer);
}
