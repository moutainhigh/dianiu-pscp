package com.edianniu.pscp.mis.bean;

import java.io.Serializable;
import java.util.List;

public class FileUploadResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileId;
    private List<String> slaveFileIds;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<String> getSlaveFileIds() {
        return slaveFileIds;
    }

    public void setSlaveFileIds(List<String> slaveFileIds) {
        this.slaveFileIds = slaveFileIds;
    }
}
