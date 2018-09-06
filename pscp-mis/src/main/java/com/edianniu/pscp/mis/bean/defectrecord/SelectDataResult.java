package com.edianniu.pscp.mis.bean.defectrecord;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * ClassName: SelectDataResult
 * Author: tandingbo
 * CreateTime: 2017-10-18 15:11
 */
public class SelectDataResult extends Result {
    private static final long serialVersionUID = -1492384836369428390L;

    private List<DefectRecordVO> defectRecordList;

    public List<DefectRecordVO> getDefectRecordList() {
        return defectRecordList;
    }

    public void setDefectRecordList(List<DefectRecordVO> defectRecordList) {
        this.defectRecordList = defectRecordList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
