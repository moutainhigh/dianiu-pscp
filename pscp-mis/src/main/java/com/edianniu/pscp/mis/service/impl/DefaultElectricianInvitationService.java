package com.edianniu.pscp.mis.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.mis.bean.company.CompanyElectricianStatus;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationInfo;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.TrueStatus;
import com.edianniu.pscp.mis.dao.CompanyElectricianDao;
import com.edianniu.pscp.mis.domain.CompanyElectrician;
import com.edianniu.pscp.mis.domain.Electrician;
import com.edianniu.pscp.mis.domain.Member;
import com.edianniu.pscp.mis.query.ElectricianInvitationQuery;
import com.edianniu.pscp.mis.service.ElectricianInvitationService;
import com.edianniu.pscp.mis.service.ElectricianService;
import com.edianniu.pscp.mis.service.UserService;
import com.edianniu.pscp.mis.util.IDCardUtils;
import com.edianniu.pscp.mis.util.IDCardUtils.IDCardInfo;
/**
 * ElectricianInvitationService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月28日 下午12:07:59 
 * @version V1.0
 */
@Service
@Repository("electricianInvitationService")
public class DefaultElectricianInvitationService implements
		ElectricianInvitationService {
	@Autowired
    private CompanyElectricianDao companyElectricianDao;
	@Autowired
	private ElectricianService electricianService;
	@Autowired
	private UserService userService;
	@Override
	public CompanyElectrician getByMobileAndCompanyId(String mobile,
			Long companyId) {
		
		return companyElectricianDao.getByMobileAndCompanyId(mobile, companyId);
	}
	@Override
	@Transactional
	public boolean invite(CompanyElectrician companyElectrician) {
		if(companyElectrician==null){
			return false;
		}
		int rs=0;
		if(companyElectrician.getId()!=null){
			rs=companyElectricianDao.update(companyElectrician);
		}
		else{
			companyElectricianDao.save(companyElectrician);
			rs=1;
		}
		return rs>0?true:false;
	}
	@Override
	public CompanyElectrician getById(Long id) {
		return companyElectricianDao.getById(id);
	}
	@Override
	@Transactional
	public boolean agree(CompanyElectrician companyElectrician,Electrician electrician,List<CertificateImgInfo> imgs) {
		if(companyElectrician==null||companyElectrician.getId()==null||electrician==null||imgs==null||imgs.isEmpty()){
			return false;
		}
		companyElectrician.setStatus(CompanyElectricianStatus.BOUND.getValue());
		companyElectrician.setInvitationAgreeTime(new Date());
		companyElectrician.setName(electrician.getUserName());
		companyElectricianDao.update(companyElectrician);
		IDCardInfo idCardInfo = IDCardUtils.getIDCardInfo(electrician.getIdCardNo());
		Member member=new Member();
		member.setUid(electrician.getMemberId());
		member.setSex(idCardInfo.getSex());
		member.setAge(idCardInfo.getAge());
		member.setCompanyId(electrician.getCompanyId());
		member.setIsElectrician(TrueStatus.YES.getValue());
		electricianService.bindCompany(member, electrician, imgs);
		return true;
	}
	@Override
	@Transactional
	public boolean reject(CompanyElectrician companyElectrician) {
		if(companyElectrician==null||companyElectrician.getId()==null){
			return false;
		}
		companyElectrician.setStatus(CompanyElectricianStatus.REJECT.getValue());
		companyElectrician.setInvitationRejectTime(new Date());
		companyElectricianDao.update(companyElectrician);
		return true;
	}
	@Override
	@Transactional
	public boolean applyUnBund(CompanyElectrician companyElectrician) {
		companyElectricianDao.update(companyElectrician);
		return true;
	}
	@Override
	@Transactional
	public boolean agreeUnBund(CompanyElectrician companyElectrician) {
		if(companyElectrician==null){
			return false;
		}
		Electrician electrician=electricianService.getByUid(companyElectrician.getMemberId());
		if(electrician==null){
			return false;
		}
		companyElectricianDao.update(companyElectrician);
		//会员信息修改
		UserInfo userInfo=userService.getUserInfo(companyElectrician.getMemberId());
		Member member=new Member();
		member.setUid(companyElectrician.getMemberId());
		member.setSex(null);
		member.setAge(null);
		if(!userInfo.isFacilitator()){//如果是服务商帐号，不修改公司ID
			member.setCompanyId(0L);
		}
		member.setIsElectrician(TrueStatus.NO.getValue());
		//电工信息修改
		electrician.setCompanyId(0L);
		electrician.setStatus(ElectricianAuthStatus.NOTAUTH.getValue());
		electricianService.unBindCompany(member, electrician);
		return true;
	}
	@Override
	@Transactional
	public boolean rejectUnBund(CompanyElectrician companyElectrician) {
		companyElectricianDao.update(companyElectrician);
		return true;
	}
	@Override
	@Transactional
	public int updateMemberId(Long id, Long memberId,String modifiedUser) {
		
		return companyElectricianDao.updateMemberId(id, memberId,modifiedUser);
	}
	@Override
	public PageResult<ElectricianInvitationInfo> list(
			ElectricianInvitationQuery electricianInvitationQuery) {
		PageResult<ElectricianInvitationInfo>  result=new PageResult<ElectricianInvitationInfo>();
		int total=companyElectricianDao.queryElectricianInvitationInfoCount(electricianInvitationQuery);
	    int nextOffset = 0;
	    if (total > 0) {
	            List<ElectricianInvitationInfo> list = companyElectricianDao.queryElectricianInvitationInfoList(electricianInvitationQuery);
	            result.setData(list);
	            nextOffset = electricianInvitationQuery.getOffset() + list.size();
	            result.setNextOffset(nextOffset);
	    }
	    if (nextOffset > 0 && nextOffset < total) {
	            result.setHasNext(true);
	    }
	    result.setOffset(electricianInvitationQuery.getOffset());
	    result.setNextOffset(nextOffset);
	    result.setTotal(total);
	    return result;
	}
	@Override
	public CompanyElectrician getByMemberIdAndCompanyId(Long memberId,
			Long companyId) {
		
		return companyElectricianDao.getByMemberIdAndCompanyId(memberId, companyId);
	}

}
