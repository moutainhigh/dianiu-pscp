package com.edianniu.pscp.mis.service.dubbo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.service.AreaService;
import com.edianniu.pscp.mis.service.dubbo.AreaInfoService;
@Service
@Repository("areaInfoService")
public class AreaInfoServiceImpl implements AreaInfoService {
	@Autowired
    private AreaService areaService;
	@Override
	public List<CityInfo> getCityInfos(Long provinceId) {
		return areaService.getCityInfos(provinceId);
	}

	@Override
	public List<AreaInfo> getAreaInfos(Long cityId) {
		return areaService.getAreaInfos(cityId);
	}

	@Override
	public List<ProvinceInfo> getProvinceInfos() {
		return areaService.getProvinceInfos();
	}

	@Override
	public ProvinceInfo getProvinceInfo(Long provinceId) {
		
		return areaService.getProvinceInfo(provinceId);
	}

	@Override
	public CityInfo getCityInfo(Long provinceId, Long cityId) {
		
		return areaService.getCityInfo(provinceId, cityId);
	}

	@Override
	public AreaInfo getAreaInfo(Long cityId, Long areaId) {
		
		return areaService.getAreaInfo(cityId, areaId);
	}

	@Override
	public boolean isExist(Long provinceId, Long cityId, Long areaId) {
	
		return areaService.isExist(provinceId, cityId, areaId);
	}

}
