/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午12:20:25
 * @version V1.0
 */
package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.commons.BaseQuery;

import java.util.List;
import java.util.Map;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午12:20:25
 */
public interface BaseDao<T> {

    void save(T t);

    void save(Map<String, Object> map);

    void saveBatch(List<T> list);

    int update(T t);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    T get(Object id);

    List<T> queryList(Map<String, Object> map);

    List<T> queryList(BaseQuery baseQuery);

    int queryCount(BaseQuery baseQuery);

    List<T> queryList(Object id);

    int queryTotal(Map<String, Object> map);

    int queryTotal();
}
