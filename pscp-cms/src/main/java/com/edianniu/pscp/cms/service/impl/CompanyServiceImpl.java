package com.edianniu.pscp.cms.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cms.dao.CompanyCertificateImageDao;
import com.edianniu.pscp.cms.dao.CompanyDao;
import com.edianniu.pscp.cms.entity.CompanyCertificateImageEntity;
import com.edianniu.pscp.cms.entity.CompanyEntity;
import com.edianniu.pscp.cms.entity.ImageDo;
import com.edianniu.pscp.cms.service.CompanyService;
import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.service.dubbo.AreaInfoService;



@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private AreaInfoService areaInfoService;
	
	@Autowired
	private CompanyCertificateImageDao companyCertificateImageDao;
	
	

	private String picUrl;
	
	@Value(value = "${file.download.url}")
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public CompanyEntity getCompanyByCompanyId(Long companyId){
		
		CompanyEntity company=companyDao.getCompanyById(companyId);
		if(company==null){
			return null;
		}
		List<CompanyCertificateImageEntity> imgs=companyCertificateImageDao.getCertificateImagesByCompany(company.getId());
		if(StringUtils.isNoneBlank(company.getApplicationLetterFid())){
			ImageDo appLetter=getImgDo(company.getApplicationLetterFid());
			company.setApplicationLetterFidImgDo(appLetter);
		}
		
		if(StringUtils.isNoneBlank(company.getBusinessLicenceImg())){
			ImageDo appLetter=getImgDo(company.getBusinessLicenceImg());
			company.setBusinessLicenceImgDo(appLetter);
		}
		if(StringUtils.isNoneBlank(company.getIdCardBackImg())){
			ImageDo appLetter=getImgDo(company.getIdCardBackImg());
			company.setIdCardBackImgDo(appLetter);
		}
		if(StringUtils.isNoneBlank(company.getIdCardFrontImg())){
			ImageDo appLetter=getImgDo(company.getIdCardFrontImg());
			company.setIdCardFrontImgDo(appLetter);
		}
		if(StringUtils.isNoneBlank(company.getOrganizationCodeImg())){
			ImageDo appLetter=getImgDo(company.getOrganizationCodeImg());
			company.setOrganizationCodeImgDo(appLetter);
		}
		if(company.getAreaId()!=null){
			AreaInfo area=areaInfoService.getAreaInfo(company.getCityId(), company.getAreaId());
			if(area!=null)
			company.setAreaName(area.getName());
		}
		if(company.getCityId()!=null){
			CityInfo city=areaInfoService.getCityInfo(company.getProvinceId(), company.getCityId());
			if(city!=null)
			company.setCityName(city.getName());
		}
	    if(company.getProvinceId()!=null){
	    	ProvinceInfo province= areaInfoService.getProvinceInfo(company.getProvinceId());
	    	if(province!=null)
			company.setProvinceName(province.getName());
	    }
		List<ImageDo>list=new ArrayList<ImageDo>();
		if(imgs!=null&&imgs.size()>0){
			for(CompanyCertificateImageEntity img:imgs){
				
				ImageDo image=new ImageDo();
				image.setImgId(img.getFileId());
				image.setImgUrl(picUrl+img.getFileId());
				image.setId(img.getId());
				image.setStatus(0);
				list.add(image);
			}
			company.setEnterpriseQualificationImgList(list);
		}
		return company;
	}
	
	@Override
	public List<CompanyEntity> queryList(Map<String, Object> map){
		return companyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return companyDao.queryTotal(map);
	}
	@Override
	public void delete(Long id){
		companyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		companyDao.deleteBatch(ids);
	}
	private ImageDo getImgDo(String url){
		ImageDo image=new ImageDo();
		image.setImgId(url);
		image.setImgUrl(picUrl+url);
		image.setStatus(0);
		
		return image;
	}




	
	
}
