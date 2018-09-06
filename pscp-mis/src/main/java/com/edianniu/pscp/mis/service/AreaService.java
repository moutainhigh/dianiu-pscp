package com.edianniu.pscp.mis.service;

import java.util.List;

import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;


/**
 * 城市区域服务
 *
 * @author cyl
 */
public interface AreaService {

    public List<CityInfo> getCityInfos(Long provinceId);
    public List<AreaInfo> getAreaInfos(Long cityId);
    public List<ProvinceInfo> getProvinceInfos();
    public ProvinceInfo getProvinceInfo(Long provinceId);
    public CityInfo getCityInfo(Long provinceId,Long cityId);
    public AreaInfo getAreaInfo(Long cityId,Long areaId);
    public boolean isExist(Long provinceId,Long cityId,Long areaId);

}
