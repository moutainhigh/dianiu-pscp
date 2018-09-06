package com.edianniu.pscp.sps.bean.response.customer;

import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.response.BaseResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

import java.util.List;

/**
 * ClassName: ProjectsResponse
 * Author: tandingbo
 * CreateTime: 2017-07-12 09:48
 */
@JSONMessage(messageCode = 2002092)
public final class ProjectsResponse extends BaseResponse {
    private List<ProjectVO> projectList;

    public List<ProjectVO> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectVO> projectList) {
        this.projectList = projectList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
