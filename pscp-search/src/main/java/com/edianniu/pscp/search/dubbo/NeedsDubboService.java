package com.edianniu.pscp.search.dubbo;

import com.edianniu.pscp.search.support.needs.*;

/**
 * ClassName: NeedsDubboService
 *
 * @author: tandingbo
 * CreateTime: 2017-09-24 15:57
 */
public interface NeedsDubboService {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    NeedsPageListResult pageListNeedsVO(NeedsPageListReqData reqData);

    /**
     * 添加数据
     *
     * @param reqData
     * @return
     */
    NeedsSaveResult save(NeedsSaveReqData reqData);

    /**
     * 删除数据
     *
     * @param reqData
     * @return
     */
    NeedsDeleteResult deleteById(NeedsDeleteReqData reqData);
}
