package com.edianniu.pscp.search.dubbo.impl;

import com.edianniu.pscp.search.common.Constants;
import com.edianniu.pscp.search.common.PageResult;
import com.edianniu.pscp.search.support.needs.*;
import com.edianniu.pscp.search.support.query.ListQuery;
import com.edianniu.pscp.search.dubbo.NeedsDubboService;
import com.edianniu.pscp.search.service.NeedsService;
import com.edianniu.pscp.search.support.needs.vo.NeedsVO;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: NeedsDubboServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-09-24 15:58
 */
@Service("needsDubboService")
public class NeedsDubboServiceImpl implements NeedsDubboService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NeedsService needsService;

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    @Override
    public NeedsPageListResult pageListNeedsVO(NeedsPageListReqData reqData) {
        NeedsPageListResult result = new NeedsPageListResult();
        try {
            // 过滤有*的搜索文字，这里处理的结果是不返回任何数据
            if (StringUtils.isNotBlank(reqData.getSearchText())
                    && reqData.getSearchText().contains("*")) {
                return result;
            }

            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(reqData, listQuery);
            PageResult<NeedsVO> pageResult = needsService.queryNeedsVOByPage(listQuery);

            result.setNeedsList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
    }

    /**
     * 添加数据
     *
     * @param reqData
     * @return
     */
    @Override
    public NeedsSaveResult save(NeedsSaveReqData reqData) {
        NeedsSaveResult result = new NeedsSaveResult();
        try {
            Map<String, Object> param = new HashMap<>();
            if (StringUtils.isBlank(reqData.getId())) {
                result.set(Constants.BAD_REQUEST, "需求ID不能为空");
                return result;
            }
            param.put("needsId", reqData.getId());

            if (StringUtils.isBlank(reqData.getOrderId())) {
                result.set(Constants.BAD_REQUEST, "需求编号不能为空");
                return result;
            }
            param.put("orderId", reqData.getOrderId());

            if (StringUtils.isBlank(reqData.getName())) {
                result.set(Constants.BAD_REQUEST, "需求名称不能为空");
                return result;
            }
            param.put("name", reqData.getName());

            if (StringUtils.isBlank(reqData.getDescribe())) {
                result.set(Constants.BAD_REQUEST, "需求描述不能为空");
                return result;
            }
            param.put("describe", reqData.getDescribe());

            if (reqData.getPublishTime() == null) {
                result.set(Constants.BAD_REQUEST, "需求审核时间不能为空");
                return result;
            }
            param.put("publishTime", reqData.getPublishTime());

            if (reqData.getPublishCutoffTime() == null) {
                result.set(Constants.BAD_REQUEST, "需求截止时间不能为空");
                return result;
            }
            param.put("publishCutoffTime", reqData.getPublishCutoffTime());

            IndexResponse response = needsService.save(reqData.getId(), param);
            RestStatus status = response.status();
            // 返回状态有两种情况
            // 第一:该文档id不存在,进行创建写入RestStatus.CREATED
            // 第二,文档id已经存在,进行更新操作返回RestStatus.OK
            if (!RestStatus.OK.equals(status)
                    && !RestStatus.CREATED.equals(status)) {
                result.set(Constants.ERROR_CODE, "添加数据失败");
                logger.error("添加数据失败，IndexResponse返回状态码：{}", status);
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
    }

    /**
     * 删除数据
     *
     * @param reqData
     * @return
     */
    @Override
    public NeedsDeleteResult deleteById(NeedsDeleteReqData reqData) {
        NeedsDeleteResult result = new NeedsDeleteResult();
        try {
            if (StringUtils.isBlank(reqData.getNeedsId())) {
                result.set(Constants.BAD_REQUEST, "需求ID不能为空");
                return result;
            }
            // 检查数据是否存在
            GetResponse getResponse = needsService.getById(reqData.getNeedsId());
            if (getResponse.isSourceEmpty()) {
                result.set(Constants.ERROR_CODE, "删除数据不存在");
                logger.warn("删除数据不存在,操作标识：{}", reqData.getNeedsId());
                return result;
            }

            // 删除数据
            DeleteResponse response = needsService.deleteById(reqData.getNeedsId());
            if (RestStatus.OK != response.status()) {
                result.set(Constants.ERROR_CODE, "删除数据失败");
                logger.error("删除数据失败", response.toString());
            }
        } catch (Exception e) {
            result.set(Constants.ERROR_CODE, "系统异常");
            logger.error("系统异常信息", e);
        }
        return result;
    }
}
