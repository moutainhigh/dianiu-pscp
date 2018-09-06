package com.edianniu.pscp.search.support;
/**
 * 排序字段
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 上午10:50:43 
 * @version V1.0
 */
public enum SortOrder {
	 /**
     * Ascending order.
     */
    ASC {
        @Override
        public String toString() {
            return "asc";
        }
    },
    /**
     * Descending order.
     */
    DESC {
        @Override
        public String toString() {
            return "desc";
        }
    };
}
