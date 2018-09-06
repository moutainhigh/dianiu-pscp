package com.edianniu.pscp.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.dao.AppCheckUpdateDao;
import com.edianniu.pscp.cms.entity.AppCheckUpdateEntity;
import com.edianniu.pscp.cms.service.AppCheckUpdateService;



@Service("appCheckUpdateService")
public class AppCheckUpdateServiceImpl implements AppCheckUpdateService {
	@Autowired
	private AppCheckUpdateDao appCheckUpdateDao;
	
	@Override
	public AppCheckUpdateEntity queryObject(Long id){
		return appCheckUpdateDao.queryObject(id);
	}
	
	@Override
	public List<AppCheckUpdateEntity> queryList(Map<String, Object> map){
		return appCheckUpdateDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appCheckUpdateDao.queryTotal(map);
	}
	
	@Override
	public void save(AppCheckUpdateEntity appCheckUpdate){
		appCheckUpdateDao.save(appCheckUpdate);
	}
	
	@Override
	public void update(AppCheckUpdateEntity appCheckUpdate){
		appCheckUpdateDao.update(appCheckUpdate);
	}
	
	@Override
	public void delete(Long id){
		appCheckUpdateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		appCheckUpdateDao.deleteBatch(ids);
	}

	@Override
	public Integer getMaxAppLatestVer(String appPkg) {
		return appCheckUpdateDao.getMaxAppLatestVer(appPkg);
	}
	
}
