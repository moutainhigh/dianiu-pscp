package com.edianniu.pscp.portal.service.impl;

import com.edianniu.pscp.message.commons.ResultCode;
import com.edianniu.pscp.mis.bean.AreaInfo;
import com.edianniu.pscp.mis.bean.CityInfo;
import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.bean.ProvinceInfo;
import com.edianniu.pscp.mis.bean.company.*;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.service.dubbo.AreaInfoService;
import com.edianniu.pscp.mis.service.dubbo.CompanyInfoService;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.portal.dao.CompanyCertificateImageDao;
import com.edianniu.pscp.portal.dao.CompanyDao;
import com.edianniu.pscp.portal.dao.SysUserDao;
import com.edianniu.pscp.portal.entity.CompanyCertificateImageEntity;
import com.edianniu.pscp.portal.entity.CompanyEntity;
import com.edianniu.pscp.portal.entity.ImageDo;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.CompanyService;
import com.edianniu.pscp.portal.utils.ImageUtils;
import com.edianniu.pscp.portal.utils.ShiroUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AreaInfoService areaInfoService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private CompanyCertificateImageDao companyCertificateImageDao;

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private CompanyInfoService companyInfoService;

    private String picUrl;

    @Value(value = "${file.download.url}")
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public CompanyEntity getCompanyByCompanyId(Long companyId) {
        return companyDao.queryObject(companyId);
    }

    @Override
    public List<CompanyEntity> queryList(Map<String, Object> map) {
        return companyDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return companyDao.queryTotal(map);
    }

    @Override
    public CompanySaveOrAuthResult save(CompanyEntity company) {
        CompanySaveOrAuthResult result = new CompanySaveOrAuthResult();
        try {
            if (StringUtils.isBlank(company.getBusinessLicenceImgDo()
                    .getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("营业执照不能为空");
                return result;
            }
            if (StringUtils.isBlank(company.getApplicationLetterFidImgDo()
                    .getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("企业申请公函不能为空");
                return result;
            }
            if (StringUtils.isBlank(company.getIdCardFrontImgDo().getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("身份证正面不能为空");
                return result;
            }
            if (StringUtils.isBlank(company.getIdCardBackImgDo().getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("身份证反面不能为空");
                return result;
            }
            if (StringUtils.isBlank(company.getOrganizationCodeImgDo()
                    .getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("组织机构代码证不能为空");
                return result;
            }
            if (company.getEnterpriseQualificationImgList() == null
                    || company.getEnterpriseQualificationImgList().isEmpty()) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("企业承修资质证件不能为空");
                return result;
            }
            List<CertificateImgInfo> certificateImages = getCertificateImages(company);
            setOtherImages(company);
            CompanySaveOrAuthReqData companySaveOrAuthReqData = new CompanySaveOrAuthReqData();
            CompanyInfo companyInfo = new CompanyInfo();
            BeanUtils.copyProperties(company, companyInfo);
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setProvinceId(company.getProvinceId());
            addressInfo.setCityId(company.getCityId());
            addressInfo.setAreaId(company.getAreaId());
            addressInfo.setAddress(company.getAddress());
            companyInfo.setAddressInfo(addressInfo);
            // 设置企业承修资质
            companyInfo.setCertificateImages(certificateImages);
            companySaveOrAuthReqData.setCompanyInfo(companyInfo);
            companySaveOrAuthReqData.setUid(ShiroUtils.getUserId());
            companySaveOrAuthReqData.setActionType("auth");
            companySaveOrAuthReqData.setMemberType(company.getMemberType());
            result = companyInfoService.saveOrAuth(companySaveOrAuthReqData);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    /**
     * 获取证书图片
     *
     * @param company
     * @return
     */
    private List<CertificateImgInfo> getCertificateImages(CompanyEntity company) {
        List<CertificateImgInfo> list = new ArrayList<CertificateImgInfo>();
        int i = 0;
        for (ImageDo img : company.getEnterpriseQualificationImgList()) {
            CertificateImgInfo imgInfo = new CertificateImgInfo();
            if (img.getStatus() == 1) {
                String fileId = getFileId(img.getImgUrl());
                imgInfo.setFileId(fileId);
            } else if (img.getStatus() == 0) {
                imgInfo.setFileId(img.getImgId());
            } else if (img.getStatus() == -1) {
                continue;
            }
            imgInfo.setOrderNum(i);
            list.add(imgInfo);
            i++;
        }
        return list;
    }

    /**
     * 设置其他图片
     *
     * @param company
     */
    private void setOtherImages(CompanyEntity company) {
        if (company.getApplicationLetterFidImgDo().getStatus() == 1) {
            String fileId = getFileId(company.getApplicationLetterFidImgDo()
                    .getImgUrl());
            company.setApplicationLetterFid(fileId);
        }
        if (company.getIdCardBackImgDo().getStatus() == 1) {
            String fileId = getFileId(company.getIdCardBackImgDo().getImgUrl());

            company.setIdCardBackImg(fileId);
        }
        if (company.getBusinessLicenceImgDo().getStatus() == 1) {
            String fileId = getFileId(company.getBusinessLicenceImgDo()
                    .getImgUrl());
            company.setBusinessLicenceImg(fileId);
        }
        if (company.getIdCardFrontImgDo().getStatus() == 1) {
            String fileId = getFileId(company.getIdCardFrontImgDo().getImgUrl());
            company.setIdCardFrontImg(fileId);
        }
        if (company.getOrganizationCodeImgDo().getStatus() == 1) {
            String fileId = getFileId(company.getOrganizationCodeImgDo()
                    .getImgUrl());
            company.setOrganizationCodeImg(fileId);
        }
    }

    @Override
    public CompanySaveOrAuthResult update(CompanyEntity company) {
        CompanySaveOrAuthResult result = new CompanySaveOrAuthResult();
        try {
            if (company.getBusinessLicenceImgDo().getStatus() != 0
                    && StringUtils.isBlank(company.getBusinessLicenceImgDo()
                    .getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_201);
                result.setResultMessage("营业执照不能为空");
                return result;
            }
            if (company.getApplicationLetterFidImgDo().getStatus() != 0
                    && StringUtils.isBlank(company
                    .getApplicationLetterFidImgDo().getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_202);
                result.setResultMessage("企业申请公函不能为空");
                return result;
            }
            if (company.getIdCardFrontImgDo().getStatus() != 0
                    && StringUtils.isBlank(company.getIdCardFrontImgDo()
                    .getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("身份证正面不能为空");
                return result;
            }
            if (company.getIdCardBackImgDo().getStatus() != 0
                    && StringUtils.isBlank(company.getIdCardBackImgDo()
                    .getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_204);
                result.setResultMessage("身份证反面不能为空");
                return result;
            }
            if (company.getOrganizationCodeImgDo().getStatus() != 0
                    && StringUtils.isBlank(company.getOrganizationCodeImgDo()
                    .getImgUrl())) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("组织机构代码证不能为空");
                return result;
            }
            if (company.getEnterpriseQualificationImgList() == null
                    || company.getEnterpriseQualificationImgList().isEmpty()) {
                result.setResultCode(ResultCode.ERROR_205);
                result.setResultMessage("企业承修资质证件不能为空");
                return result;
            }

            List<CertificateImgInfo> certificateImages = getCertificateImages(company);
            setOtherImages(company);
            CompanySaveOrAuthReqData companySaveOrAuthReqData = new CompanySaveOrAuthReqData();
            CompanyInfo companyInfo = new CompanyInfo();
            BeanUtils.copyProperties(company, companyInfo);
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setProvinceId(company.getProvinceId());
            addressInfo.setCityId(company.getCityId());
            addressInfo.setAreaId(company.getAreaId());
            addressInfo.setAddress(company.getAddress());
            companyInfo.setAddressInfo(addressInfo);
            // 设置企业承修资质
            companyInfo.setCertificateImages(certificateImages);
            companySaveOrAuthReqData.setCompanyInfo(companyInfo);
            companySaveOrAuthReqData.setUid(ShiroUtils.getUserId());
            if (company.getStatus() == CompanyAuthStatus.SUCCESS.getValue()) {
                companySaveOrAuthReqData.setActionType("save");
            } else {
                companySaveOrAuthReqData.setActionType("auth");
            }
            result = companyInfoService.saveOrAuth(companySaveOrAuthReqData);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        companyDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        companyDao.deleteBatch(ids);
    }

    @Override
    public CompanyEntity getCompanyByUid(Long uid) {
        SysUserEntity userEntity = sysUserDao.getEntityById(uid);
        if (userEntity == null) {
            return new CompanyEntity();
        }
        SysUserEntity companyUserEntity = sysUserDao.getCompanyAdmin(userEntity.getCompanyId());
        if (companyUserEntity == null) {
            companyUserEntity = new SysUserEntity();
            companyUserEntity.setUserId(uid);
        }

        CompanyEntity bean = companyDao.getCompanyByUid(companyUserEntity.getUserId());
        if (bean != null) {
            List<CompanyCertificateImageEntity> imgs = companyCertificateImageDao
                    .getCertificateImagesByCompany(bean.getId());
            if (StringUtils.isNoneBlank(bean.getApplicationLetterFid())) {
                ImageDo appLetter = getImgDo(fileUploadService.conversionSmallFilePath(bean.getApplicationLetterFid()));
                bean.setApplicationLetterFidImgDo(appLetter);
            }

            if (StringUtils.isNoneBlank(bean.getBusinessLicenceImg())) {
                ImageDo appLetter = getImgDo(fileUploadService.conversionSmallFilePath(bean.getBusinessLicenceImg()));
                bean.setBusinessLicenceImgDo(appLetter);
            }
            if (StringUtils.isNoneBlank(bean.getIdCardBackImg())) {
                ImageDo appLetter = getImgDo(fileUploadService.conversionSmallFilePath(bean.getIdCardBackImg()));
                bean.setIdCardBackImgDo(appLetter);
            }
            if (StringUtils.isNoneBlank(bean.getIdCardFrontImg())) {
                ImageDo appLetter = getImgDo(fileUploadService.conversionSmallFilePath(bean.getIdCardFrontImg()));
                bean.setIdCardFrontImgDo(appLetter);
            }
            if (StringUtils.isNoneBlank(bean.getOrganizationCodeImg())) {
                ImageDo appLetter = getImgDo(fileUploadService.conversionSmallFilePath(bean.getOrganizationCodeImg()));
                bean.setOrganizationCodeImgDo(appLetter);
            }

            if (bean.getProvinceId() != null) {
                ProvinceInfo province = areaInfoService.getProvinceInfo(bean
                        .getProvinceId());
                if (province != null)
                    bean.setProvinceName(province.getName());
            }
            if (bean.getCityId() != null) {
                CityInfo city = areaInfoService.getCityInfo(
                        bean.getProvinceId(), bean.getCityId());
                if (city != null)
                    bean.setCityName(city.getName());
            }
            if (bean.getAreaId() != null) {
                AreaInfo area = areaInfoService.getAreaInfo(
                        bean.getCityId(), bean.getAreaId());
                if (area != null)
                    bean.setAreaName(area.getName());
            }
            List<ImageDo> list = new ArrayList<ImageDo>();
            if (imgs != null && imgs.size() > 0) {
                for (CompanyCertificateImageEntity img : imgs) {
                    ImageDo image = getImgDo(fileUploadService.conversionSmallFilePath(img.getFileId()));
                    image.setId(img.getId());
                    list.add(image);
                }
                bean.setEnterpriseQualificationImgList(list);
            }
        }
        return bean;
    }

    @Override
    public CompanyEntity getSimpleCompanyByUid(Long uid) {
        return companyDao.getCompanyByUid(uid);
    }

    private String getFileId(String image) {
        String fileId = "";
        if (!StringUtils.isBlank(image)) {
            FileUploadResult certImgResult = fileUploadService.upload("upload_"
                            + System.nanoTime() + ".jpg",
                    ImageUtils.getBase64ImageBytes(image), true, null, true);
            if (certImgResult.isSuccess()) {
                fileId = certImgResult.getFileId();
            }
        }
        return fileId;
    }

    private ImageDo getImgDo(String url) {
        ImageDo image = new ImageDo();
        image.setImgId(url);
        image.setImgUrl(picUrl + url);
        image.setStatus(0);
        return image;
    }


}
