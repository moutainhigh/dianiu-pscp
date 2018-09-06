package com.edianniu.pscp.mis.bean.response.defectrecord;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ListResponse
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:12
 */
@JSONMessage(messageCode = 2002155)
public final class ListResponse extends BaseResponse {

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

