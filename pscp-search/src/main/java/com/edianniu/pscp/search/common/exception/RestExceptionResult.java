package com.edianniu.pscp.search.common.exception;

import com.edianniu.pscp.search.support.Result;

/**
 * @author tandingbo
 * @create 2017-11-06 15:09
 */
public class RestExceptionResult extends Result {
    private static final long serialVersionUID = 9013727368961209178L;

    private RestExceptionResult() {
    }

    public static RestExceptionResult newInstance() {
        return new RestExceptionResult();
    }
}
