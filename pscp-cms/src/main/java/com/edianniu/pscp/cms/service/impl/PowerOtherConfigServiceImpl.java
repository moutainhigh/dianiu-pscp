package com.edianniu.pscp.cms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edianniu.pscp.cms.dao.PowerOtherConfigDao;
import com.edianniu.pscp.cms.entity.PowerOtherConfigEntity;
import com.edianniu.pscp.cms.service.PowerOtherConfigService;

/**
 * 企业用电其他配置
 * @author zhoujianjian
 * @date 2018年1月10日 下午3:32:49
 */
@Service("powerOtherConfigService")
public class PowerOtherConfigServiceImpl implements PowerOtherConfigService {
	
	@Autowired
	private PowerOtherConfigDao powerOtherConfigDao;

	/**
	 * 保存、编辑配置信息
	 */
	@Override
	public void save(PowerOtherConfigEntity[] entities) {
		Date now = new Date();
		for (PowerOtherConfigEntity entity : entities) {
			// 以companyId、type、key组合查询，判断记录是否存在
			Long companyId = entity.getCompanyId();
			Integer type = entity.getType();
			Integer key = entity.getKey();
			PowerOtherConfigEntity config = powerOtherConfigDao.isExist(companyId, type, key);
			if (null != config) { // 之前存在记录，作更新操作
				entity.setId(config.getId());
				entity.setCreateTime(now);
				entity.setCreateUser("系统");
				powerOtherConfigDao.update(entity);
			} else { // 之前不存在记录，作插入操作
				entity.setModifiedTime(now);
				entity.setModifiedUser("系统");
				powerOtherConfigDao.save(entity);
			}
		}
	}

	/**
	 * 获取配置详情
	 */
	@Override
	public List<PowerOtherConfigEntity> queryConfigs(HashMap<String, Object> map) {
		return powerOtherConfigDao.queryConfigs(map);
	}

	/**
	 * 删除配置信息
	 */
	@Override
	public void deleteConfigs(Long companyId, Integer type) {
		PowerOtherConfigEntity entity = new PowerOtherConfigEntity();
		entity.setCompanyId(companyId);
		entity.setType(type);
		entity.setDeleted(-1);
		entity.setModifiedTime(new Date());
		entity.setModifiedUser("系统");
		powerOtherConfigDao.update(entity);
	}
	
}
