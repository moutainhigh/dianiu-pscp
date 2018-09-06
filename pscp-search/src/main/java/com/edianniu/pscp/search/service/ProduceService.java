/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月8日 上午11:59:46 
 * @version V1.0
 */
package com.edianniu.pscp.search.service;

import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月8日 上午11:59:46 
 * @version V1.0
 */
public interface ProduceService {
	 /**
     * 新增
     *
     * @param id
     * @param param
     * @return
     * @throws Exception
     */
    IndexResponse save(String id, Map<String, Object> param) throws Exception;

    /**
     * 根据主键修改
     *
     * @param id
     * @param param
     * @return
     * @throws Exception
     */
    UpdateResponse updateById(String id, Map<String, Object> param) throws Exception;

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    DeleteResponse deleteById(String id);

    /**
     * 根据主键获取
     *
     * @param id
     * @return
     */
    GetResponse getById(String id);
}
