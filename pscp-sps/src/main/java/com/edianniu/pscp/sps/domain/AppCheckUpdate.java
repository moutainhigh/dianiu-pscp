package com.edianniu.pscp.sps.domain;

public class AppCheckUpdate extends BaseDo {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long companyId;
    private Long appStartVer;
    private Long appEndVer;
    private Integer appType;
    private String appPkg;
    private Long appLatestVer;
    private String appLatestShowVer;
    private Float appLatestSize;
    private Integer updateType;
    private String updateDesc;
    private String appDownloadUrl;
    private String appDownloadMd5;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getAppStartVer() {
        return appStartVer;
    }

    public void setAppStartVer(Long appStartVer) {
        this.appStartVer = appStartVer;
    }

    public Long getAppEndVer() {
        return appEndVer;
    }

    public void setAppEndVer(Long appEndVer) {
        this.appEndVer = appEndVer;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getAppPkg() {
        return appPkg;
    }

    public void setAppPkg(String appPkg) {
        this.appPkg = appPkg;
    }

    public Long getAppLatestVer() {
        return appLatestVer;
    }

    public void setAppLatestVer(Long appLatestVer) {
        this.appLatestVer = appLatestVer;
    }

    public String getAppLatestShowVer() {
        return appLatestShowVer;
    }

    public void setAppLatestShowVer(String appLatestShowVer) {
        this.appLatestShowVer = appLatestShowVer;
    }

    public Float getAppLatestSize() {
        return appLatestSize;
    }

    public void setAppLatestSize(Float appLatestSize) {
        this.appLatestSize = appLatestSize;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAppDownloadMd5() {
        return appDownloadMd5;
    }

    public void setAppDownloadMd5(String appDownloadMd5) {
        this.appDownloadMd5 = appDownloadMd5;
    }

}
