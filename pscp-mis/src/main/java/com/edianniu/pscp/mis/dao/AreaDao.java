package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.Area;
import com.edianniu.pscp.mis.domain.City;
import com.edianniu.pscp.mis.domain.Province;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * @author  AbnerElk
 */
public interface AreaDao {
    public List<Area> getAreas(Long cityId);
    public List<City> getCitys(Long provinceId);
    public List<Province> getProvinces();
}
