package com.edianniu.pscp.cs.bean.needsorder;

import java.io.Serializable;
import java.util.Map;

/**
 * ClassName: ListNeedsOrderReqData
 * Author: tandingbo
 * CreateTime: 2017-10-09 10:17
 */
public class ListNeedsOrderReqData implements Serializable {
    private static final long serialVersionUID = 4097870396206960658L;

    private Map<String, Object> queryMap;

    public Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, Object> queryMap) {
        this.queryMap = queryMap;
    }
}
