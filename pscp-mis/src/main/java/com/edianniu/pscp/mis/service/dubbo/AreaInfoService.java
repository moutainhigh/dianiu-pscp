package com.edianniu.pscp.mis.service.dubbo;

import java.util.List;

import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;




public interface AreaInfoService {
	/**
	 * 获取城市列表
	 * @param provinceId
	 * @return
	 */
	public List<CityInfo> getCityInfos(Long provinceId);
	/**
	 * 获取城市区域列表
	 * @param cityId
	 * @return
	 */
    public List<AreaInfo> getAreaInfos(Long cityId);
    /**
     * 获取省份列表
     * @return
     */
    public List<ProvinceInfo> getProvinceInfos();
    /**
     * 获取省份
     * @param provinceId
     * @return
     */
    public ProvinceInfo getProvinceInfo(Long provinceId);
    /**
     * 获取城市
     * @param provinceId
     * @param cityId
     * @return
     */
    public CityInfo getCityInfo(Long provinceId,Long cityId);
    /**
     * 获取区域
     * @param cityId
     * @param areaId
     * @return
     */
    public AreaInfo getAreaInfo(Long cityId,Long areaId);
    /**
     * 判断省份，城市，区域是否存在
     * @param provinceId
     * @param cityId
     * @param areaId
     * @return
     */
    public boolean isExist(Long provinceId,Long cityId,Long areaId);

}
