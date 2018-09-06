package com.edianniu.pscp.sps.bean.news;

/**
 * 新闻类型
 * package name: com.edianniu.pscp.sps.bean.news
 * project name: pscp-sps
 * user: tandingbo
 * create time: 2017-08-10 17:20
 */
public enum NewsType {
    INDUSTRY(1, "行业资讯"),
    NOTICE(2, "公告消息");

    private Integer value;
    private String name;

    NewsType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /**
     * 判断值是否存在
     *
     * @param value
     * @return
     */
    public static Boolean isExist(Integer value) {
        NewsType[] values = NewsType.values();
        for (NewsType newsType : values) {
            if (value.equals(newsType.getValue())) {
                return true;
            }
        }
        return false;
    }
}
