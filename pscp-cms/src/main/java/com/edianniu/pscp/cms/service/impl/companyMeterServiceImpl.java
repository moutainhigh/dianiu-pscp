package com.edianniu.pscp.cms.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cms.dao.CompanyMeterDao;
import com.edianniu.pscp.cms.entity.CompanyMeterEntity;
import com.edianniu.pscp.cms.service.CompanyMeterService;

@Service("companyMeterService")
public class companyMeterServiceImpl implements CompanyMeterService {
	
	@Autowired
	private CompanyMeterDao companyMeterDao;

	@Override
	public void save(CompanyMeterEntity bean) {
		companyMeterDao.save(bean);
	}

	@Override
	public List<CompanyMeterEntity> queryList(HashMap<String, Object> map) {
		return companyMeterDao.queryList(map);
	}

	@Override
	public int queryTotal(HashMap<String, Object> map) {
		return companyMeterDao.queryTotal(map);
	}

	@Override
	public CompanyMeterEntity queryObject(Long id) {
		return companyMeterDao.queryObject(id);
	}

	@Override
	public void update(CompanyMeterEntity bean) {
		companyMeterDao.update(bean);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		companyMeterDao.deleteBatch(ids);
	}

	@Override
	public boolean isExist(String meterId) {
		int count = companyMeterDao.isExist(meterId);
		return count > 0 ? true : false;
	}

	@Override
	public boolean isNameExist(String name, Long companyId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("companyId", companyId);
		return companyMeterDao.queryObjectByName(map) == null ? false : true;
	}

}
