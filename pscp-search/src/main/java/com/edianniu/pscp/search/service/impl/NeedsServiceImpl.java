package com.edianniu.pscp.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.support.query.ListQuery;
import com.edianniu.pscp.search.repository.needs.NeedsRepository;
import com.edianniu.pscp.search.service.NeedsService;
import com.edianniu.pscp.search.support.needs.vo.NeedsVO;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: NeedsServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-09-24 16:01
 */
@Service("needsService")
public class NeedsServiceImpl implements NeedsService {

    @Autowired
    private NeedsRepository repository;

    /**
     * 分页搜索
     *
     * @param listQuery
     * @return
     */
    @Override
    public PageResult<NeedsVO> queryNeedsVOByPage(ListQuery listQuery) {
        int nextOffset = 0;
        PageResult<NeedsVO> result = new PageResult<>();

        SearchResponse response = repository.queryNeedsVOByPage(listQuery);
        // 获取总记录数
        int total = (int) response.getHits().getTotalHits();
        if (total > 0) {
            List<NeedsVO> list = new ArrayList<>();
            for (SearchHit searchHit : response.getHits().getHits()) {
                JSONObject source = JSONObject.parseObject(searchHit.getSourceAsString());
                NeedsVO entity = JSONObject.toJavaObject(source, NeedsVO.class);
                list.add(entity);
            }

            result.setData(list);
            nextOffset = listQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }

        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(listQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);

        return result;
    }

    /**
     * 新增
     *
     * @param id
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public IndexResponse save(String id, Map<String, Object> param) throws Exception {
        return repository.save(id, param);
    }

    /**
     * 根据主键修改
     *
     * @param id
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public UpdateResponse updateById(String id, Map<String, Object> param) throws Exception {
        return repository.updateById(id, param);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    @Override
    public DeleteResponse deleteById(String id) {
        return repository.deleteById(id);
    }

    /**
     * 根据主键获取
     *
     * @param id
     * @return
     */
    @Override
    public GetResponse getById(String id) {
        return repository.getById(id);
    }
}
