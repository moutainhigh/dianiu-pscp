package com.edianniu.pscp.mis.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.mis.bean.CreateUserResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.dao.MemberDao;
import com.edianniu.pscp.mis.dao.MemberRoleDao;
import com.edianniu.pscp.mis.dao.UserFeedbackDao;
import com.edianniu.pscp.mis.dao.UserWalletDao;
import com.edianniu.pscp.mis.domain.Member;
import com.edianniu.pscp.mis.domain.MemberRole;
import com.edianniu.pscp.mis.domain.UserFeedback;
import com.edianniu.pscp.mis.domain.UserWallet;
import com.edianniu.pscp.mis.exception.BusinessException;
import com.edianniu.pscp.mis.service.UserService;

@Service
@Repository("userService")
public class DefaultUserService implements UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultUserService.class);
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberRoleDao memberRoleDao;
	@Autowired
	private UserWalletDao userWalletDao;
	@Autowired
	private UserFeedbackDao userFeedbackDao;
	
	
    private UserInfo transform(Member member){
    	if(member==null){
    		return null;
    	}
    	UserInfo userInfo=new UserInfo();
    	BeanUtils.copyProperties(member, userInfo);
    	return userInfo;
    }
    
	public UserInfo getUserInfo(Long companyId, String mobile) {
		return transform(memberDao.getByCompanyIdAndMobile(companyId, mobile));
	}

	public UserInfo getUserInfo(Long uid) {
		return transform(memberDao.getByUid(uid));
	}

	public boolean existByMobile(String mobile) {
		Integer count = this.memberDao.checkExistMobile( mobile);
		if (count == null) {
			return false;
		}
		if (count.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Transactional
	public CreateUserResult create(String mobile, String passwd)
			throws BusinessException {
		CreateUserResult result = new CreateUserResult();
		Member member = new Member();
		member.setMobile(mobile);
		member.setLoginName(mobile);
		member.setPasswd(passwd);
		memberDao.saveUser(member);
		//服务商角色
		MemberRole memberRole=new MemberRole();
		memberRole.setUserId(member.getUid());
		memberRole.setRoleId(Constants.FACILITATOR_ROLE_ID);
		memberRoleDao.save(memberRole);
		//电工角色
		MemberRole memberRole2=new MemberRole();
		memberRole2.setUserId(member.getUid());
		memberRole2.setRoleId(Constants.ELECTRICIAN_ROLE_ID);
		memberRoleDao.save(memberRole2);
		// 客户角色
		MemberRole memberRole3=new MemberRole();
		memberRole3.setUserId(member.getUid());
		memberRole3.setRoleId(Constants.CUSTOMER_ROLE_ID);
		memberRoleDao.save(memberRole3);
		//审核通过后，如果是服务商则，去掉电工角色；如果是电工，则去掉服务商角色
		int count = userWalletDao.exist(member.getUid());
		if (count == 0) {
			UserWallet userwalle = new UserWallet();
			userwalle.setAmount(0.00);
			userwalle.setFreezingAmount(0.00);
			userwalle.setUid(member.getUid());
			userWalletDao.save(userwalle);
		}
		result.setUid(member.getUid());
		return result;
	}

	public boolean changePwd(Long uid, String opUser, String passwd) {
		boolean rs = false;
		try {
			Member member = new Member();
			member.setUid(uid);
			member.setPasswd(passwd);
			member.setModifiedUser(opUser);
			this.memberDao.updateUserPwd(member);
			rs = true;
		} catch (Exception e) {
			logger.error("changePwd {}", e);
		}
		return rs;
	}
	
	@Override
	public boolean changeMobile(Long uid, String mobile, String newMobile) {
		boolean rs = false;
		try {
			Member member = new Member();
			member.setUid(uid);
			member.setMobile(newMobile);
			member.setLoginName(newMobile);
			member.setModifiedUser(mobile);
			memberDao.updateUserMobile(member);
			rs = true;
		} catch (Exception e) {
			logger.error("changeMobile {}", e);
		}
		return rs;
	}

	public UserInfo getSimpleUserInfo(Long companyId, String mobile) {
		return this.transform(memberDao.getSimpleByMobile(companyId, mobile));
	}

	public UserInfo getSimpleUserInfo(Long uid) {
		return this.transform(memberDao.getSimpleByUid(uid));
	}
	
	@Override
	public void updateUserInfo(UserInfo userInfo) {
		Member member=new Member();
		BeanUtils.copyProperties(userInfo, member);
		memberDao.updateUser(member);
	}
	
	@Override
	public void setSwitchPwd(Long uid, String encode) {
		Member member = new Member();
		member.setUid(uid);
		member.setSwitchpwd(encode);
		memberDao.updateSwitchpwd(member);
	}
	
	@Override
	public Boolean checkSwitchpwd(Long uid, String encode) {
		Member member = memberDao.getByUid(uid);
		if (null == member) {
			return false;
		}
		String switchpwd = member.getSwitchpwd();
		if (null == switchpwd) {
			return false;
		}
		if (switchpwd.equals(encode)) {
			return true;
		}
		return false;
	}
	
	@Override
	public UserInfo getUserInfoByMobile(String mobile) {
		return this.transform(memberDao.getByMobile(mobile));
	}

	@Override
	public UserInfo getUserInfoByLoginName(String loginName) {
		return this.transform(memberDao.getByLoginName(loginName));
	}
	
	@Override
	public UserInfo getUserInfoByCompanyName(String companyName) {
		return this.transform(memberDao.getByCompanyName(companyName));
	}
	
	@Override
	public void feedback(UserFeedback feedback) {
		this.userFeedbackDao.save(feedback);
	}
	
	@Override
	public UserInfo getFacilitatorAdmin(Long companyId) {
		return this.transform(memberDao.getFacilitatorAdminByCompanyId(companyId));
	}
	
	@Override
	public Long getFacilitatorAdminUid(Long companyId) {
		return memberDao.getFacilitatorAdminUidByCompanyId(companyId);
	}


}
