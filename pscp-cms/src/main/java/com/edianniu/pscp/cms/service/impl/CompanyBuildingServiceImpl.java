package com.edianniu.pscp.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edianniu.pscp.cms.dao.CompanyBuildingDao;
import com.edianniu.pscp.cms.entity.CompanyBuildingEntity;
import com.edianniu.pscp.cms.service.CompanyBuildingService;

@Service("companyBuildingService")
public class CompanyBuildingServiceImpl implements CompanyBuildingService {

	@Autowired
	private CompanyBuildingDao companyBuildingDao;

	@Override
	public void save(CompanyBuildingEntity bean) {
		companyBuildingDao.save(bean);
	}

	@Override
	public List<CompanyBuildingEntity> queryList(Map<String, Object> map) {
		return companyBuildingDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return companyBuildingDao.queryTotal(map);
	}

	@Override
	public CompanyBuildingEntity queryObject(Long id) {
		return companyBuildingDao.queryObject(id);
	}

	@Override
	public void update(CompanyBuildingEntity bean) {
		companyBuildingDao.update(bean);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		companyBuildingDao.deleteBatch(ids);
	}

	/**
	 * 获取绑定的线路条数
	 */
	@Override
	public int getCountOfLines(Long id) {
		return companyBuildingDao.getCountOfLines(id);
	}

	@Override
	public boolean isNameExist(String name, Long companyId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("companyId", companyId);
		return companyBuildingDao.queryObjectByName(map) == null ? false : true;
	}
	
}
