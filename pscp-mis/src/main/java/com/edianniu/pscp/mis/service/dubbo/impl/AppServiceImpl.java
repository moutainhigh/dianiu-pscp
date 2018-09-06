package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.CheckAppUpdateReqData;
import com.edianniu.pscp.mis.bean.CheckAppUpdateResult;
import com.edianniu.pscp.mis.bean.HomeData;
import com.edianniu.pscp.mis.bean.HomeResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.domain.AppCheckUpdate;
import com.edianniu.pscp.mis.service.AppCheckUpdateService;
import com.edianniu.pscp.mis.service.dubbo.AppService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository("appService")
public class AppServiceImpl implements AppService {
    @Autowired
    @Qualifier("appCheckUpdateService")
    private AppCheckUpdateService appCheckUpdateService;


    private String picServerUrl;
    private String apkServerUrl;
    ;

    @Value(value = "${pic.server.baseurl}")
    public void setPicServerUrl(String picServerUrl) {
        this.picServerUrl = picServerUrl;
    }

    @Value(value = "${apk.server.baseurl}")
    public void setApkServerUrl(String apkServerUrl) {
        this.apkServerUrl = apkServerUrl;
    }

    @Override
    public HomeResult home(HomeData data) {
        HomeResult result = new HomeResult();
        if (data.getUid() == null) {
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("uid 不能为空");
            return result;
        }

        Long uid = new Long(data.getUid());


        return result;
    }


    @Override
    public CheckAppUpdateResult checkAppUpdate(
            CheckAppUpdateReqData checkAppUdpateReqData) {
        CheckAppUpdateResult result = new CheckAppUpdateResult();
        if (StringUtils.isBlank(checkAppUdpateReqData.getAppPkg())) {
            result.setResultCode(ResultCode.ERROR_201);
            result.setResultMessage("appPkg 不能为空");
            return result;
        }
        if (checkAppUdpateReqData.getAppVer() == null || checkAppUdpateReqData.getAppVer() == 0) {
            result.setResultCode(ResultCode.ERROR_202);
            result.setResultMessage("appVer 不能为空");
            return result;
        }
        if (StringUtils.isBlank(checkAppUdpateReqData.getOsType())) {
            result.setResultCode(ResultCode.ERROR_203);
            result.setResultMessage("osType 不能为空");
            return result;
        }
        String osType = checkAppUdpateReqData.getOsType().trim();
        if (!(osType.equals("android") || osType.equals("ios"))) {
            result.setResultCode(ResultCode.ERROR_203);
            result.setResultMessage("osType 只能是android|ios");
            return result;
        }
        if (StringUtils.isBlank(checkAppUdpateReqData.getOsVer())) {
            result.setResultCode(ResultCode.ERROR_204);
            result.setResultMessage("osVer 不能为空");
            return result;
        }
        AppCheckUpdate appCheckUpdate = appCheckUpdateService.getAppCheckUpdate(osType,
                checkAppUdpateReqData.getAppPkg(), checkAppUdpateReqData.getAppVer());
        result.setLastAPPShowVer(appCheckUpdate.getAppLatestShowVer());
        result.setLastAppVer(appCheckUpdate.getAppLatestVer());
        if (appCheckUpdate.getAppLatestSize() == null) {
            result.setLastAppSize("0 M");
        } else {
            result.setLastAppSize(appCheckUpdate.getAppLatestSize() / 1024 + " M");
        }
        result.setUpdateDesc(appCheckUpdate.getUpdateDesc());
        result.setUpdateType(appCheckUpdate.getUpdateType());
        if (StringUtils.isNotBlank(appCheckUpdate.getAppDownloadUrl())) {
            if (appCheckUpdate.getAppDownloadUrl().startsWith("http://") ||
                    appCheckUpdate.getAppDownloadUrl().startsWith("https://")) {
                result.setUpdateUrl(appCheckUpdate.getAppDownloadUrl());
            } else {
                result.setUpdateUrl(apkServerUrl + appCheckUpdate.getAppDownloadUrl());
            }
        }
        result.setUpdateMd5(appCheckUpdate.getAppDownloadMd5());
        return result;
    }

}
