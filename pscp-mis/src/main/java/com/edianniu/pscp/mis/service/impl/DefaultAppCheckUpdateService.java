package com.edianniu.pscp.mis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.mis.bean.UpdateType;
import com.edianniu.pscp.mis.dao.AppCheckUpdateDao;
import com.edianniu.pscp.mis.domain.AppCheckUpdate;
import com.edianniu.pscp.mis.service.AppCheckUpdateService;


@Service
@Repository("appCheckUpdateService")
public class DefaultAppCheckUpdateService implements AppCheckUpdateService {
	@Autowired
    private AppCheckUpdateDao appCheckUpdateDao;
	@Override
	public AppCheckUpdate getAppCheckUpdate(String osType, String appPkg, Long appVer) {
		int appType=1;
		if(osType.equals("ios")){
			appType=1;
		}
		else if(osType.equals("android")){
			appType=2;
		}
		List<AppCheckUpdate> list=appCheckUpdateDao.getAppCheckUpdates(appType, appPkg, appVer);
		if(list.isEmpty()){
			AppCheckUpdate appCheckUpdate=new AppCheckUpdate();
			appCheckUpdate.setUpdateType(UpdateType.NOT_UPDATE.getValue());
			appCheckUpdate.setUpdateDesc("当前版本已是最新版本");
			appCheckUpdate.setAppLatestVer(appVer);
			appCheckUpdate.setAppLatestSize(0.00F);
			return appCheckUpdate;
		}
		return list.get(0);
	}

}
