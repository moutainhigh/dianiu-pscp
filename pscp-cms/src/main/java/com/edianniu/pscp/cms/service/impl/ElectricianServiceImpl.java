package com.edianniu.pscp.cms.service.impl;

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

import com.edianniu.pscp.cms.commons.DefaultResult;
import com.edianniu.pscp.cms.commons.Result;
import com.edianniu.pscp.cms.dao.ElectricianCertificateDao;
import com.edianniu.pscp.cms.dao.ElectricianDao;
import com.edianniu.pscp.cms.entity.ElectricianCertificateEntity;
import com.edianniu.pscp.cms.entity.ElectricianCertificateImageEntity;
import com.edianniu.pscp.cms.entity.ElectricianEntity;
import com.edianniu.pscp.cms.entity.ImageDo;
import com.edianniu.pscp.cms.entity.MemberEntity;
import com.edianniu.pscp.cms.service.ElectricianCertificateImageService;
import com.edianniu.pscp.cms.service.ElectricianCertificateService;
import com.edianniu.pscp.cms.service.ElectricianService;
import com.edianniu.pscp.cms.service.MemberMemberRoleService;
import com.edianniu.pscp.cms.service.MemberService;

@Service("electricianService")
public class ElectricianServiceImpl implements ElectricianService {
	private static final Logger logger = LoggerFactory
			.getLogger(ElectricianServiceImpl.class);
	@Autowired
	private ElectricianDao electricianDao;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberMemberRoleService sysMemberRoleService;
	@Autowired
	private ElectricianCertificateImageService electricianCertificateImageService;
	@Autowired
	private ElectricianCertificateService electricianCertificateService;
	
	@Autowired
	private ElectricianCertificateDao electricianCertificateDao;
	
	private String fileDownloadUrl = "http://111.1.17.197:8091/";

	@Value(value = "${file.download.url:}")
	public void setFileDownloadUrl(String fileDownloadUrl) {
		this.fileDownloadUrl = fileDownloadUrl;
	}
	@Override
	public ElectricianEntity getByUid(Long uid) {
		ElectricianEntity electricianEntity = electricianDao.getByUid(uid);
		setElectricianExtInfo(electricianEntity);
		return electricianEntity;
	}
	@Override
	public ElectricianEntity getById(Long id) {
		ElectricianEntity electricianEntity = electricianDao.queryObject(id);
		setElectricianExtInfo(electricianEntity);
		return electricianEntity;
	}
	private void setElectricianExtInfo(ElectricianEntity electricianEntity) {
		if (electricianEntity != null) {
			List<ElectricianCertificateImageEntity> imageList=electricianCertificateImageService.queryListByElectricianId(electricianEntity.getId());
			for(ElectricianCertificateImageEntity image:imageList){
				ImageDo imageDo=new ImageDo(image.getId(),image.getFileId(),fileDownloadUrl);
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
			List<ElectricianCertificateEntity> certificates=electricianCertificateService.getElectricianCertificates(electricianEntity.getId());
			electricianEntity.setCertificates(certificates);
			//获取用户所属的角色列表
		    List<Long> roleIdList = sysMemberRoleService.queryRoleIdList(electricianEntity.getUserId());
		    electricianEntity.setRoleIdList(roleIdList);
		     
		}
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
	public void deleteBatch(Long[] ids) {
		//更新企业电工为普通会员，如果企业电工还是服务商帐号，不去除服务商帐号关联。
		if(ids!=null){
			for(Long id:ids){
				ElectricianEntity electricianEntity=electricianDao.queryObject(id);
				if(electricianEntity==null){
					continue;
				}
				electricianDao.delete(id);
				electricianCertificateImageService.deleteByElectricianId(electricianEntity.getMemberId());
				MemberEntity user=memberService.queryObject(electricianEntity.getMemberId());
				if(user==null){
					continue;
				}
				user.setRoleIdList(new ArrayList<Long>());
				if(!user.isFacilitator()){
					user.setCompanyId(0L);
				}
				user.setPasswd(null);
				user.setIsElectrician(0);
				memberService.update(user);
			}
		}
	}		

}
