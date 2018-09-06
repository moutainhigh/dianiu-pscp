package com.edianniu.pscp.portal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.mis.bean.FileUploadResult;
import com.edianniu.pscp.mis.service.dubbo.FileUploadService;
import com.edianniu.pscp.portal.commons.DefaultResult;
import com.edianniu.pscp.portal.commons.Result;
import com.edianniu.pscp.portal.dao.ElectricianDao;
import com.edianniu.pscp.portal.entity.ElectricianCertificateImageEntity;
import com.edianniu.pscp.portal.entity.ElectricianEntity;
import com.edianniu.pscp.portal.entity.ImageDo;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.ElectricianCertificateImageService;
import com.edianniu.pscp.portal.service.ElectricianService;
import com.edianniu.pscp.portal.service.SysMemberRoleService;
import com.edianniu.pscp.portal.service.SysUserService;
import com.edianniu.pscp.portal.utils.ImageUtils;

@Service("electricianService")
public class ElectricianServiceImpl implements ElectricianService {
    private static final Logger logger = LoggerFactory
            .getLogger(ElectricianServiceImpl.class);
    @Autowired
    private ElectricianDao electricianDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private SysMemberRoleService sysMemberRoleService;
    @Autowired
    private ElectricianCertificateImageService electricianCertificateImageService;
    private String fileDownloadUrl = "http://111.1.17.197:8091/";

    @Value(value = "${file.download.url:}")
    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }

    @Override
    public ElectricianEntity getByUid(Long uid) {
        ElectricianEntity electricianEntity = electricianDao.getByUid(uid);
        if (electricianEntity != null) {
            List<ElectricianCertificateImageEntity> imageList = electricianCertificateImageService.queryListByElectricianId(electricianEntity.getMemberId());
            for (ElectricianCertificateImageEntity image : imageList) {
                ImageDo imageDo = new ImageDo(image.getId(), image.getFileId(), fileDownloadUrl);
                electricianEntity.getCertificateImgList().add(imageDo);
            }
            if (StringUtils.isNoneBlank(electricianEntity.getIdCardFrontImg())) {
                electricianEntity.setIdCardFrontImgDo(new ImageDo(0L,
                        electricianEntity.getIdCardFrontImg(), fileDownloadUrl));
            }
            if (StringUtils.isNoneBlank(electricianEntity.getIdCardBackImg())) {
                electricianEntity.setIdCardBackImgDo(new ImageDo(0L,
                        electricianEntity.getIdCardBackImg(), fileDownloadUrl));
            }
            //获取用户所属的角色列表
            List<Long> roleIdList = sysMemberRoleService.queryRoleIdList(electricianEntity.getUserId());
            electricianEntity.setRoleIdList(roleIdList);
        }
        return electricianEntity;
    }

    @Override
    public ElectricianEntity queryObject(Long id) {
        ElectricianEntity electricianEntity = electricianDao.queryObject(id);
        if (electricianEntity != null) {
            List<ElectricianCertificateImageEntity> imageList = electricianCertificateImageService.queryListByElectricianId(electricianEntity.getId());
            for (ElectricianCertificateImageEntity image : imageList) {
                ImageDo imageDo = new ImageDo(image.getId(), image.getFileId(), fileDownloadUrl);
                electricianEntity.getCertificateImgList().add(imageDo);
            }
            if (StringUtils.isNoneBlank(electricianEntity.getIdCardFrontImg())) {
                electricianEntity.setIdCardFrontImgDo(new ImageDo(0L,
                        electricianEntity.getIdCardFrontImg(), fileDownloadUrl));
            }
            if (StringUtils.isNoneBlank(electricianEntity.getIdCardBackImg())) {
                electricianEntity.setIdCardBackImgDo(new ImageDo(0L,
                        electricianEntity.getIdCardBackImg(), fileDownloadUrl));
            }
            //获取用户所属的角色列表
            List<Long> roleIdList = sysMemberRoleService.queryRoleIdList(electricianEntity.getUserId());
            electricianEntity.setRoleIdList(roleIdList);
        }
        return electricianEntity;
    }

    @Override
    public List<ElectricianEntity> queryList(Map<String, Object> map) {

        return electricianDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return electricianDao.queryTotal(map);
    }
    @Override
    @Transactional
    public Result save(ElectricianEntity electrician) {
        Result result = new DefaultResult();
        List<ImageDo> certificateImgList = electrician.getCertificateImgList();
        ImageDo idCardFrontImgDo = electrician.getIdCardFrontImgDo();
        ImageDo idCardBackImgDo = electrician.getIdCardBackImgDo();
        List<ElectricianCertificateImageEntity> imageList = new ArrayList<>();
        if (certificateImgList != null && !certificateImgList.isEmpty()) {
            int i = 0;
            for (ImageDo imageDo : certificateImgList) {
                if (StringUtils.isNoneBlank(imageDo.getImgUrl())) {
                    FileUploadResult certImgResult = fileUploadService.upload(
                            "upload_" + System.nanoTime() + ".jpg",
                            ImageUtils.getBase64ImageBytes(ImageUtils.parseBase64Image(imageDo.getImgUrl())));
                    ElectricianCertificateImageEntity image = new ElectricianCertificateImageEntity();
                    image.setElectricianId(electrician.getMemberId());
                    image.setFileId(certImgResult.getFileId());
                    image.setOrderNum(i++);
                    image.setStatus(0);
                    imageList.add(image);
                }
            }
        }
        if (idCardFrontImgDo != null && StringUtils.isNoneBlank(idCardFrontImgDo.getImgUrl())) {
            FileUploadResult frontResult = fileUploadService.upload("upload_"
                            + System.nanoTime() + ".jpg",
                    ImageUtils.getBase64ImageBytes(ImageUtils.parseBase64Image(idCardFrontImgDo.getImgUrl())));
            electrician.setIdCardFrontImg(frontResult.getFileId());
        }
        if (idCardBackImgDo != null && StringUtils.isNoneBlank(idCardBackImgDo.getImgUrl())) {
            FileUploadResult backResult = fileUploadService.upload("upload_"
                            + System.nanoTime() + ".jpg",
                    ImageUtils.getBase64ImageBytes(ImageUtils.parseBase64Image(idCardBackImgDo.getImgUrl())));
            electrician.setIdCardBackImg(backResult.getFileId());
        }
        for (ElectricianCertificateImageEntity image : imageList) {
            electricianCertificateImageService.save(image);
        }
        electricianDao.save(electrician);
        return result;

    }

    @Override
    @Transactional
    public Result update(ElectricianEntity electrician) {
        Result result = new DefaultResult();
        List<ImageDo> certificateImgList = electrician.getCertificateImgList();
        ImageDo idCardFrontImgDo = electrician.getIdCardFrontImgDo();
        ImageDo idCardBackImgDo = electrician.getIdCardBackImgDo();
        List<ElectricianCertificateImageEntity> newImageList = new ArrayList<>();
        List<Long> delImageList = new ArrayList<>();
        if (certificateImgList != null && !certificateImgList.isEmpty()) {
            int i = 0;
            for (ImageDo imageDo : certificateImgList) {
                if (StringUtils.isNoneBlank(imageDo.getImgUrl())) {
                    if (imageDo.isAdd()) {
                        FileUploadResult certImgResult = fileUploadService.upload(
                                "upload_" + System.nanoTime() + ".jpg",
                                ImageUtils.getBase64ImageBytes(ImageUtils.parseBase64Image(imageDo.getImgUrl())), true, null);
                        ElectricianCertificateImageEntity image = new ElectricianCertificateImageEntity();
                        image.setElectricianId(electrician.getId());
                        image.setFileId(certImgResult.getFileId());
                        image.setOrderNum(i++);
                        image.setStatus(0);
                        newImageList.add(image);
                    } else if (imageDo.isDel()) {
                        delImageList.add(imageDo.getId());
                    }

                }
            }
        }
        if (idCardFrontImgDo != null && StringUtils.isNoneBlank(idCardFrontImgDo.getImgUrl())) {
            if (idCardBackImgDo.isAdd()) {
                FileUploadResult frontResult = fileUploadService.upload("upload_"
                                + System.nanoTime() + ".jpg",
                        ImageUtils.getBase64ImageBytes(ImageUtils.parseBase64Image(idCardFrontImgDo.getImgUrl())));
                electrician.setIdCardFrontImg(frontResult.getFileId());
            }
        } else {
            electrician.setIdCardFrontImg(null);
        }
        if (idCardBackImgDo != null && StringUtils.isNoneBlank(idCardBackImgDo.getImgUrl())) {
            if (idCardBackImgDo.isAdd()) {
                FileUploadResult backResult = fileUploadService.upload("upload_"
                                + System.nanoTime() + ".jpg",
                        ImageUtils.getBase64ImageBytes(ImageUtils.parseBase64Image(idCardBackImgDo.getImgUrl())));
                electrician.setIdCardBackImg(backResult.getFileId());
            }
        } else {
            electrician.setIdCardBackImg(null);
        }
        for (ElectricianCertificateImageEntity image : newImageList) {
            electricianCertificateImageService.save(image);
        }
        if (!delImageList.isEmpty()) {
            electricianCertificateImageService.deleteBatch(delImageList.toArray(new Long[delImageList.size()]));
        }
        electricianDao.update(electrician);
        return result;
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        //更新企业电工为普通会员，如果企业电工还是服务商帐号，不去除服务商帐号关联。
        if (ids != null) {
            for (Long id : ids) {
                ElectricianEntity electricianEntity = electricianDao.queryObject(id);
                if (electricianEntity == null) {
                    continue;
                }
                electricianDao.delete(id);
                electricianCertificateImageService.deleteByElectricianId(electricianEntity.getMemberId());
                SysUserEntity user = sysUserService.queryObject(electricianEntity.getMemberId());
                if (user == null) {
                    continue;
                }
                user.setRoleIdList(new ArrayList<Long>());
                if (!user.isFacilitator()) {
                    user.setCompanyId(0L);
                }
                user.setPasswd(null);
                user.setIsElectrician(0);
                sysUserService.update(user);
            }
        }
    }


}
