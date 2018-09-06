package com.edianniu.pscp.das.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: PageResult
 * Author: tandingbo
 * CreateTime: 2017-10-17 23:11
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -1333165823910102199L;

    private List<T> data = Collections.emptyList();
    private int total;
    private int offset;
    private int nextOffset;
    private boolean hasNext = false;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
