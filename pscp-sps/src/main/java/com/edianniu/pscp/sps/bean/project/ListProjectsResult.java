package com.edianniu.pscp.sps.bean.project;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;

import java.util.List;

/**
 * ClassName: ListProjectsResult
 * Author: tandingbo
 * CreateTime: 2017-05-16 09:48
 */
public class ListProjectsResult extends Result {
    private static final long serialVersionUID = 1L;

    private List<ProjectVO> projectList;

    public List<ProjectVO> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectVO> projectList) {
        this.projectList = projectList;
    }
}
