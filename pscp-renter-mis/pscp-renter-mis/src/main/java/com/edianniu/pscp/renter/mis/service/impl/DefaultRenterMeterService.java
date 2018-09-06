package com.edianniu.pscp.renter.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edianniu.pscp.cs.bean.power.EquipmentType;
import com.edianniu.pscp.mis.commons.CacheKey;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.renter.mis.bean.renter.RenterMeterInfo;
import com.edianniu.pscp.renter.mis.commons.PageResult;
import com.edianniu.pscp.renter.mis.dao.MeterInfoDao;
import com.edianniu.pscp.renter.mis.dao.RenterMeterDao;
import com.edianniu.pscp.renter.mis.domain.MeterInfo;
import com.edianniu.pscp.renter.mis.domain.RenterMeter;
import com.edianniu.pscp.renter.mis.service.RenterMeterService;
import stc.skymobi.cache.redis.JedisUtil;

@Service
@Transactional
@Repository("renterMeterService")
public class DefaultRenterMeterService implements RenterMeterService {
	
	@Autowired
	@Qualifier("renterMeterDao")
	private RenterMeterDao renterMeterDao;
	
	@Autowired
	@Qualifier("meterInfoDao")
	private MeterInfoDao meterInfoDao;
	
	@Autowired
	@Qualifier("jedisUtil")
	private JedisUtil jedisUtil;

	/**
	 * 是否存在公摊仪表
	 */
	@Override
	public boolean isExistPubilcMeter(Long renterId) {
		Map<String, Object> map = new HashMap<>();
		map.put("renterId", renterId);
		List<RenterMeter> meterList = renterMeterDao.queryList(map);
		for (RenterMeter renterMeter : meterList) {
			Long companyMeterId = renterMeter.getMeterId();
			if (null != companyMeterId) {
				int count = renterMeterDao.countByCompanyMeterId(companyMeterId);
				if (count > 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否是公摊仪表
	 */
	@Override
	public boolean isPublicMeter(Long companyMeterId) {
		int count = renterMeterDao.countByCompanyMeterId(companyMeterId);
		if (count > 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<RenterMeter> getMeters(Long renterId) {
		Map<String, Object> map = new HashMap<>();
		map.put("renterId", renterId);
		List<RenterMeter> meterList = renterMeterDao.queryList(map);
		return meterList;
	}

	@Override
	public PageResult<RenterMeterInfo> getPageMeters(Map<String, Object> map) {
		PageResult<RenterMeterInfo> result = new PageResult<>();
		Long renterId = (Long) map.get("renterId");
		int total = renterMeterDao.queryTotal(map);
		int nextOffset = 0;
		if (total > 0) {
			List<RenterMeter> list = renterMeterDao.queryList(map);
			List<RenterMeterInfo> renterMeterInfos = new ArrayList<>();
			for (RenterMeter renterMeter : list) {
				RenterMeterInfo renterMeterInfo = new RenterMeterInfo();
				BeanUtils.copyProperties(renterMeter, renterMeterInfo);
				renterMeterInfo.setId(renterMeter.getMeterId());//此处替换为公司仪表ID
				// 获取仪表类型
				String meterNo = renterMeter.getMeterNo();
				MeterInfo info = meterInfoDao.queryInfo(meterNo);
				if (null == info) {
					continue;
				}
				String subTermCode = info.getSubTermCode();
				if (StringUtils.isBlank(subTermCode)) {
					continue;
				}
				switch (subTermCode) {
					case "01C00": 
						renterMeterInfo.setType(EquipmentType.POWER.getValue());                //动力
						renterMeterInfo.setTypeName(EquipmentType.POWER.getDesc());   
						break; 
					case "01A00": 
						renterMeterInfo.setType(EquipmentType.LIGHTING.getValue());             //照明
						renterMeterInfo.setTypeName(EquipmentType.LIGHTING.getDesc());
						break; 
					case "01B00": 
						renterMeterInfo.setType(EquipmentType.AIR_CONDITIONER.getValue());     //空调
						renterMeterInfo.setTypeName(EquipmentType.AIR_CONDITIONER.getDesc());
						break; 
					default:
						renterMeterInfo.setType(EquipmentType.SPECIAL.getValue());             //特殊
						renterMeterInfo.setTypeName(EquipmentType.SPECIAL.getDesc());
						break; 
				}
				
				//判断是否是公摊仪表
				boolean publicMeter = isPublicMeter(renterMeter.getMeterId());
				if (publicMeter) {
					renterMeterInfo.setIsCommon(Constants.TAG_YES);
				} else {
					renterMeterInfo.setIsCommon(Constants.TAG_NO);
				}
				
				// 判断缓存 
				String r = jedisUtil.get(CacheKey.CACHE_KEY_NETDAU_CONTROL_SWITCH + renterId + "#" + meterNo);
				if (!StringUtils.isBlank(r)) {
					int length = r.length();
					if (length > 3) {
						String code = r.substring(length - 3, length);
						if (code.equals("001")) {       //合闸
							renterMeterInfo.setSwitchStatus(3); 
						} else if (code.equals("002")) {//开闸
							renterMeterInfo.setSwitchStatus(2);
						}
					}
				}
				
				renterMeterInfos.add(renterMeterInfo);
			}
			nextOffset = list.size() + (Integer)map.get("offset");
			result.setData(renterMeterInfos);
		}
		if (total > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setNextOffset(nextOffset);
		result.setTotal(total);
		
		return result;
	}

}
