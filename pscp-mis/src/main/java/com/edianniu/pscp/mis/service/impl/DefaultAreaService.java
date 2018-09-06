package com.edianniu.pscp.mis.service.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.commons.CacheKey;
import com.edianniu.pscp.mis.dao.AreaDao;
import com.edianniu.pscp.mis.domain.Area;
import com.edianniu.pscp.mis.domain.City;
import com.edianniu.pscp.mis.domain.Province;
import com.edianniu.pscp.mis.service.AreaService;
import com.edianniu.pscp.mis.util.PinyinUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import stc.skymobi.cache.redis.JedisUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Repository("areaService")
public class DefaultAreaService implements AreaService {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultAreaService.class);
	private final static int CACHE_EX_TIME = 24 * 60 * 60;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private JedisUtil jedisUtil;

	@Override
	public List<CityInfo> getCityInfos(Long provinceId) {
		List<CityInfo> result = null;
		String content = jedisUtil.get(CacheKey.CACHE_KEY_AREA_CITY_LIST
				+ provinceId);

		if (StringUtils.isNoneBlank(content)) {
			result = JSON.parseArray(content, CityInfo.class);
		} else {
			result = new ArrayList<CityInfo>();
			List<City> list = areaDao.getCitys(provinceId);
			int i = 1;
			for (City city : list) {
				if (StringUtils.isNoneBlank(city.getName())) {
					CityInfo cityInfo = new CityInfo();
					BeanUtils.copyProperties(city, cityInfo);
					String letter = PinyinUtil.toPinyin(cityInfo.getName());
					letter = letter.substring(0, 1);
					cityInfo.setFistLetter(letter.toUpperCase());
					cityInfo.setSort(i++);
					result.add(cityInfo);
				}
			}
			jedisUtil.setex(CacheKey.CACHE_KEY_AREA_CITY_LIST + provinceId,
					CACHE_EX_TIME, JSON.toJSONString(result));
		}

		return result;
	}

	@Override
	public List<AreaInfo> getAreaInfos(final Long cityId) {
		List<AreaInfo> result = null;
		String content = jedisUtil.get(CacheKey.CACHE_KEY_AREA_AREA_LIST
				+ cityId);

		if (StringUtils.isNoneBlank(content)) {
			result = JSON.parseArray(content, AreaInfo.class);
		} else {
			List<Area> list = areaDao.getAreas(cityId);
			int i = 1;
			result = new ArrayList<AreaInfo>();
			for (Area area : list) {
				AreaInfo areaInfo = new AreaInfo();
				BeanUtils.copyProperties(area, areaInfo);
				String letter = PinyinUtil.toPinyin(areaInfo.getName());
				letter = letter.substring(0, 1);
				areaInfo.setFistLetter(letter.toUpperCase());
				areaInfo.setSort(i++);
				result.add(areaInfo);
			}
			jedisUtil.setex(CacheKey.CACHE_KEY_AREA_AREA_LIST + cityId,
					CACHE_EX_TIME, JSON.toJSONString(result));
		}

		return result;
	}

	@Override
	public List<ProvinceInfo> getProvinceInfos() {
		List<ProvinceInfo> result = null;
		String content = jedisUtil.get(CacheKey.CACHE_KEY_AREA_PROVINCE_LIST);

		if (StringUtils.isNoneBlank(content)) {
			result = JSON.parseArray(content, ProvinceInfo.class);
		} else {
			result = new ArrayList<ProvinceInfo>();
			List<Province> list = areaDao.getProvinces();
			int i = 1;
			for (Province province : list) {
				if (StringUtils.isNoneBlank(province.getName())) {
					ProvinceInfo provinceInfo = new ProvinceInfo();
					BeanUtils.copyProperties(province, provinceInfo);
					String letter = PinyinUtil.toPinyin(provinceInfo.getName());
					letter = letter.substring(0, 1);
					provinceInfo.setFistLetter(letter.toUpperCase());
					provinceInfo.setSort(i++);
					result.add(provinceInfo);
				}
				jedisUtil.setex(CacheKey.CACHE_KEY_AREA_PROVINCE_LIST,
						CACHE_EX_TIME, JSON.toJSONString(result));
			}
		}
		
		return result;
	}

	@Override
	public ProvinceInfo getProvinceInfo(Long provinceId) {
		List<ProvinceInfo> provinces=getProvinceInfos();
		Map<Long,ProvinceInfo> provinceMap=new HashMap<>();
		for(ProvinceInfo provinceInfo:provinces){
			provinceMap.put(provinceInfo.getId(), provinceInfo);
		}
		return provinceMap.get(provinceId);
	}

	@Override
	public CityInfo getCityInfo(Long provinceId,Long cityId) {
		List<CityInfo> citys=getCityInfos(provinceId);
		Map<Long,CityInfo> cityMap=new HashMap<>();
		for(CityInfo cityInfo:citys){
			cityMap.put(cityInfo.getId(), cityInfo);
		}
		return cityMap.get(cityId);
		
	}

	@Override
	public AreaInfo getAreaInfo(Long cityId,Long areaId) {
		List<AreaInfo> areas=getAreaInfos(cityId);
		Map<Long,AreaInfo> areaMap=new HashMap<>();
		for(AreaInfo areaInfo:areas){
			areaMap.put(areaInfo.getId(), areaInfo);
		}
		return areaMap.get(areaId);
	}

	@Override
	public boolean isExist(Long provinceId, Long cityId, Long areaId) {
		if(null==getProvinceInfo(provinceId)){
			return false;
		}
		if(null==getCityInfo(provinceId, cityId)){
			return false;
		}
		if(null==this.getAreaInfo(cityId, areaId)){
			return false;
		}
		return true;
	}

}
