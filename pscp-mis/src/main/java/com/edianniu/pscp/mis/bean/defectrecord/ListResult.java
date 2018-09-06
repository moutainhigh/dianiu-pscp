package com.edianniu.pscp.mis.bean.defectrecord;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: ListResult
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:17
 */
public class ListResult extends Result {
    private static final long serialVersionUID = 1L;

    private int nextOffset;
    private long totalCount;
    private boolean hasNext = false;

    private List<DefectRecordVO> defectRecordList;

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<DefectRecordVO> getDefectRecordList() {
        return defectRecordList;
    }

    public void setDefectRecordList(List<DefectRecordVO> defectRecordList) {
        this.defectRecordList = defectRecordList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
