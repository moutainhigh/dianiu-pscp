package com.edianniu.pscp.cms.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.cms.bean.LineBindType;
import com.edianniu.pscp.cms.commons.Constants;
import com.edianniu.pscp.cms.dao.CompanyEquipmentDao;
import com.edianniu.pscp.cms.dao.CompanyLineDao;
import com.edianniu.pscp.cms.dao.CompanyMeterDao;
import com.edianniu.pscp.cms.entity.CompanyEquipmentEntity;
import com.edianniu.pscp.cms.entity.CompanyLineEntity;
import com.edianniu.pscp.cms.entity.CompanyMeterEntity;
import com.edianniu.pscp.cms.service.CompanyLineService;

@Service("companyLineService")
public class CompanyLineServiceImpl implements CompanyLineService {
	
	@Autowired
	private CompanyLineDao companyLineDao;
	@Autowired
	private CompanyMeterDao companyMeterDao;
	@Autowired
	private CompanyEquipmentDao companyEquipmentDao;
	 
	@Override
	@Transactional
	public void saveOrUpdate(CompanyLineEntity bean) {
		if (null != bean.getMeterId()) {
			CompanyMeterEntity meter = new CompanyMeterEntity();
			meter.setId(bean.getMeterId());
			meter.setBindStatus(Constants.TAG_YES);
			companyMeterDao.update(meter);
		}
		if (bean.getBindType().equals(LineBindType.EQUIPMENT.getValue())) {
			CompanyEquipmentEntity equipment = new CompanyEquipmentEntity();
			equipment.setId(bean.getBindId());
			equipment.setBindStatus(Constants.TAG_YES);
			companyEquipmentDao.update(equipment);
		}
		if (null == bean.getId()) {
			companyLineDao.save(bean);
		} else {
			companyLineDao.update(bean);
		}
	}

	@Override
	public CompanyLineEntity queryObject(Long id) {
		return companyLineDao.queryObject(id);
	}

	@Override
	public List<CompanyLineEntity> queryList(HashMap<String, Object> map) {
		return companyLineDao.queryList(map);
	}

	@Override
	public int queryTotal(HashMap<String, Object> map) {
		return companyLineDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] ids) {
		for (Long id : ids) {
			CompanyLineEntity lineEntity = companyLineDao.queryObject(id);
			// 将绑定的采集点绑定状态改为未绑定
			Long meterId = lineEntity.getMeterId();
			if (meterId != 0L) {
				CompanyMeterEntity meter = new CompanyMeterEntity();
				meter.setId(meterId);
				meter.setBindStatus(Constants.TAG_NO);
				companyMeterDao.update(meter);
			}
			// 将绑定设备的绑定状态改为未绑定
			Integer bindType = lineEntity.getBindType();
			Long bindId = lineEntity.getBindId();
			if (bindType == LineBindType.EQUIPMENT.getValue()) {
				CompanyEquipmentEntity equipment = new CompanyEquipmentEntity();
				equipment.setId(bindId);
				equipment.setBindStatus(Constants.TAG_NO);
				companyEquipmentDao.update(equipment);
			}
		}
		companyLineDao.deleteBatch(ids);
	}

	/**
	 * 获取子线路数量
	 */
	@Override
	public int getCountChildLines(Long id) {
		return companyLineDao.getCountChildLines(id);
	}

}
