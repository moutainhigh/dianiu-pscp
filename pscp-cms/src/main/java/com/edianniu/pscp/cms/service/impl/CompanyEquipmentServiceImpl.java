package com.edianniu.pscp.cms.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cms.dao.CompanyEquipmentDao;
import com.edianniu.pscp.cms.entity.CompanyEquipmentEntity;
import com.edianniu.pscp.cms.service.CompanyEquipmentService;

@Service("companyEquipmentService")
public class CompanyEquipmentServiceImpl implements CompanyEquipmentService {

	@Autowired
	private CompanyEquipmentDao companyEquipmentDao;

	@Override
	public void save(CompanyEquipmentEntity bean) {
		companyEquipmentDao.save(bean);
	}

	@Override
	public CompanyEquipmentEntity queryObject(Long id) {
		return companyEquipmentDao.queryObject(id);
	}

	@Override
	public List<CompanyEquipmentEntity> queryList(HashMap<String, Object> map) {
		return companyEquipmentDao.queryList(map);
	}

	@Override
	public int queryTotal(HashMap<String, Object> map) {
		return companyEquipmentDao.queryTotal(map);
	}

	@Override
	public void update(CompanyEquipmentEntity bean) {
		companyEquipmentDao.update(bean);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		companyEquipmentDao.deleteBatch(ids);
	}
	
}
