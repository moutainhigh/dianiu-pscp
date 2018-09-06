/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:24:10
 * @version V1.0
 */
package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.Electrician;

import java.util.Map;

/**
 * ElectricianDao
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:24:10
 */
public interface ElectricianDao extends BaseDao<Electrician> {
    public Electrician getByUid(Long uid);

    public void updateWorkTimeAndStatus(Map<String, Object> map);
}
