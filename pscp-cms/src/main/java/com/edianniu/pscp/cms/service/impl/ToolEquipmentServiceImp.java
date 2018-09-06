package com.edianniu.pscp.cms.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cms.dao.ToolEquipmentDao;
import com.edianniu.pscp.cms.entity.ToolEquipmentEntity;
import com.edianniu.pscp.cms.service.ToolEquipmentService;
import com.edianniu.pscp.cms.utils.R;
@Service("toolEquipmentService")
public class ToolEquipmentServiceImp implements ToolEquipmentService{
	
	@Autowired
	private ToolEquipmentDao toolEquipmentDao;
	
	@Override
	public ToolEquipmentEntity queryObject(Long id) {
		
		return toolEquipmentDao.queryObject(id);
	}

	@Override
	public List<ToolEquipmentEntity> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return toolEquipmentDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return toolEquipmentDao.queryTotal(map);
	}

	@Override
	public R save(ToolEquipmentEntity bean) {
		R r=null;
		if(StringUtils.isBlank(bean.getName().trim())){
			r=R.error(201, "设备名称不能为空");
			return r;
		}
		if(StringUtils.isBlank(bean.getName().trim())){
			r=R.error(201, "设备规格不能为空");
			return r;
		}
		
		toolEquipmentDao.save(bean);
		r=R.ok();
		return r;
		
	}

	@Override
	public R update(ToolEquipmentEntity bean) {
		R r=null;
		if(StringUtils.isBlank(bean.getName().trim())){
			r=R.error(201, "设备名称不能为空");
			return r;
		}
		if(StringUtils.isBlank(bean.getName().trim())){
			r=R.error(201, "设备规格不能为空");
			return r;
		}
		toolEquipmentDao.update(bean);
		r=R.ok();
		
		return r;
		
	}

	@Override
	public void delete(Long id) {
		toolEquipmentDao.delete(id);
		
	}

	@Override
	public void deleteBatch(Long[] ids) {
		toolEquipmentDao.deleteBatch(ids);
		
	}

	@Override
	public ToolEquipmentEntity getToolById(Long id) {
		ToolEquipmentEntity bean=toolEquipmentDao.getToolById(id);
		return bean;
	}

	

}
