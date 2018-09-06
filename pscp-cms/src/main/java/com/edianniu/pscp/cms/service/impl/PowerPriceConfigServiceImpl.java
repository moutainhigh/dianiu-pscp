package com.edianniu.pscp.cms.service.impl;

import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edianniu.pscp.cms.dao.PowerPriceConfigDao;
import com.edianniu.pscp.cms.entity.PowerPriceConfigEntity;
import com.edianniu.pscp.cms.service.PowerPriceConfigService;

/**
 * 企业电价配置
 * @author zhoujianjian
 * @date 2018年1月3日 下午8:27:25
 */
@Service("powerPriceConfigService")
public class PowerPriceConfigServiceImpl implements PowerPriceConfigService {
	
	@Autowired
	private PowerPriceConfigDao powerPriceConfigDao;

	@Override
	public void save(PowerPriceConfigEntity entity) {
		Date now = new Date();
		Long id = entity.getId();
		if (null == id) {
			Long companyId = entity.getCompanyId();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("companyId", companyId);
			PowerPriceConfigEntity company = powerPriceConfigDao.queryObject(map);
			if (null != company) {
				return;
			}
			entity.setCreateTime(now);
			entity.setCreateUser("系统");
			powerPriceConfigDao.save(entity);
		} else {
			entity.setModifiedTime(now);
			entity.setModifiedUser("系统");
			powerPriceConfigDao.update(entity);
		}
	}

	@Override
	public PowerPriceConfigEntity queryObject(HashMap<String, Object> map) {
		return powerPriceConfigDao.queryObject(map);
	}

	@Override
	public void delete(Long id) {
		Date now = new Date();
		PowerPriceConfigEntity entity = new PowerPriceConfigEntity();
		entity.setId(id);
		entity.setDeleted(-1);
		entity.setModifiedTime(now);
		entity.setModifiedUser("系统");
		powerPriceConfigDao.update(entity);
	}

}
