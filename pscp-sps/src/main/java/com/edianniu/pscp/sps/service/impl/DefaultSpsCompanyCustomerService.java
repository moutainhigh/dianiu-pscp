package com.edianniu.pscp.sps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.sps.bean.customer.CustomerInfo;
import com.edianniu.pscp.sps.bean.customer.ListPageQuery;
import com.edianniu.pscp.sps.bean.customer.ResetPasswordResult;
import com.edianniu.pscp.sps.bean.customer.SaveResult;
import com.edianniu.pscp.sps.bean.customer.vo.CustomerVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.dao.SpsCompanyCustomerDao;
import com.edianniu.pscp.sps.dao.SpsCompanyDao;
import com.edianniu.pscp.sps.dao.SysUserDao;
import com.edianniu.pscp.sps.domain.Company;
import com.edianniu.pscp.sps.domain.CompanyCustomer;
import com.edianniu.pscp.sps.domain.SysUserEntity;
import com.edianniu.pscp.sps.service.SpsCompanyCustomerService;
import com.edianniu.pscp.sps.util.BizUtils;
import com.edianniu.pscp.sps.util.PasswordUtil;
import com.edianniu.pscp.sps.util.ThreadUtil;

/**
 * ClassName: DefaultSpsCompanyCustomerService
 * Author: tandingbo
 * CreateTime: 2017-05-17 09:53
 */
@Service
@Repository("spsCompanyCustomerService")
public class DefaultSpsCompanyCustomerService implements SpsCompanyCustomerService {

    private static final String String = null;
	@Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SpsCompanyDao spsCompanyDao;
    @Autowired
    private SpsCompanyCustomerDao spsCompanyCustomerDao;
    @Autowired
    private MessageInfoService messageInfoService;

    @Override
    public List<CustomerVO> selectCustomerVOByCompanyId(Long companyId, List<Integer> statusList) {
        List<CompanyCustomer> companyCustomerList = spsCompanyCustomerDao.getListByCompanyId(companyId, statusList);
        List<CustomerVO> customerVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(companyCustomerList)) {
            for (CompanyCustomer companyCustomer : companyCustomerList) {
                CustomerVO customerVO = new CustomerVO();
                customerVO.setId(companyCustomer.getId());
                customerVO.setName(companyCustomer.getCpName());
                customerVO.setPhone(companyCustomer.getCpPhone());
                customerVO.setContact(companyCustomer.getCpContact());
                customerVO.setAddress(companyCustomer.getCpAddress());
                customerVO.setContactMobile(companyCustomer.getContactTel());
                customerVOList.add(customerVO);
            }
        }
        return customerVOList;
    }

    @Override
    public CompanyCustomer getById(Long id) {
        return spsCompanyCustomerDao.queryObject(id);
    }

    @Override
    @Transactional
    public void delete(Long[] ids, String loginName) {
        spsCompanyCustomerDao.deletes(ids, loginName);
    }

    @Override
    @Transactional
    public SaveResult save(UserInfo userInfo, CompanyCustomer companyCustomer) {
        Date now = new Date();
        SaveResult result = validate(companyCustomer);
        if (!result.isSuccess()) {
            return result;
        }

        if (userInfo.getMobile().equals(companyCustomer.getMobile().trim())) {
            result.set(ResultCode.ERROR_401, "不能添加自己");
            return result;
        }

        companyCustomer.setCreateTime(now);
        companyCustomer.setCreateUser(userInfo.getLoginName());
        companyCustomer.setCompanyId(userInfo.getCompanyId());

        Map<String, Object> map = new HashMap<String, Object>();
        //查看该手机号码是否是服务商何其子账号
        SysUserEntity bean = new SysUserEntity();
        bean.setCompanyId(companyCustomer.getCompanyId());
        bean.setIsFacilitator(1);
        map.put("bean", bean);
        //先查询服务商
        int count = sysUserDao.queryTotal(map);
        if (count > 0) {
            result.set(ResultCode.ERROR_401, "不能添加自己");
            return result;
        }
        //再查询服务商所属电工,判断是否是服务商下的电工
        SysUserEntity abean = new SysUserEntity();
        abean.setCompanyId(companyCustomer.getCompanyId());
        abean.setIsElectrician(1);
        map.put("bean", abean);
        int acount = sysUserDao.queryTotal(map);
        if (acount != 0) {
            result.set(ResultCode.ERROR_401, "不能添加自己");
            return result;
        }

        SysUserEntity member = sysUserDao.queryByMobile(companyCustomer.getMobile().trim());


        //查看该手机号码是否已经注册用户，有注册就要判断是否是服务商何其子账号,判断是否是服务上下的电工
        if (member == null) {
            final String mobile = companyCustomer.getMobile().trim();
            final String password = String.valueOf(getRandNum());

            //查看该手机号码是否已经注册用户，没注册就要注册用户，添加企业信息和客户信息
            member = new SysUserEntity();
            member.setCompanyId(0L);
            member.setIsAdmin(0);
            member.setIsCustomer(0);
            member.setMobile(mobile);
            member.setPasswd(PasswordUtil.encode(password));
            member.setSex(0);
            member.setAge(0);
            member.setStatus(1);

            member.setCreateTime(now);
            member.setCreateUser(userInfo.getLoginName());
            sysUserDao.save(member);

            Company company = new Company();
            company.setProvinceId(0L);
            company.setCityId(0L);
            company.setAreaId(0L);
            company.setAddress(companyCustomer.getCpAddress());
            company.setMemberId(member.getUserId());
            company.setMobile(companyCustomer.getMobile());
            company.setPhone(companyCustomer.getCpPhone());
            company.setName(companyCustomer.getCpName());
            company.setContacts(companyCustomer.getCpContact());
            company.setStatus(CompanyAuthStatus.NOTAUTH.getValue());
            company.setCreateTime(now);
            company.setCreateUser(userInfo.getLoginName());
            spsCompanyDao.save(company);

            final Long memberId = member.getUserId();
            companyCustomer.setMemberId(memberId);
            spsCompanyCustomerDao.save(companyCustomer);

            // 给客户推送登录密码
            ThreadUtil.getSortTimeOutThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 发送推送消息
                        Map<String, String> params = new HashMap<>();
                        params.put("code", password);
                        params.put("mobile", mobile);
                        messageInfoService.sendSmsMessage(memberId, mobile, MessageId.NEW_PASSWORD_CODE, params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            // 企业客户关系
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("memberId", member.getUserId());
            queryMap.put("companyId", userInfo.getCompanyId());
            CompanyCustomer companyCustomerEntity = spsCompanyCustomerDao.getCompanyCustomerByMap(queryMap);
            if (companyCustomerEntity != null) {
                result.set(ResultCode.ERROR_401, "已经存在，不能重复添加");
                return result;
            }
            //1.认证的电工或者认证中的电工不能添加
            if (member.getIsElectrician() == SysUserEntity.TAG_YES) {
                result.set(ResultCode.ERROR_401, "账号已认证为电工，不能添加为客户");
                return result;
            }

            // 2.认证中的服务商不能添加
            if (member.getIsFacilitator() == SysUserEntity.TAG_NO ||
                    member.getIsCustomer() == SysUserEntity.TAG_NO) {
                Company companyEntity = spsCompanyDao.getCompanyByUid(member.getUserId());
                if (companyEntity != null) {
                    result.set(ResultCode.ERROR_401, "账号已认证为电工，不能添加为客户");
                    return result;
                }
            }

            Company company = new Company();
            company.setProvinceId(8L);
            company.setCityId(888L);
            company.setAreaId(1074L);
            company.setAddress(companyCustomer.getCpAddress());
            company.setMemberId(member.getUserId());
            company.setMobile(companyCustomer.getMobile());
            company.setPhone(companyCustomer.getCpPhone());
            company.setName(companyCustomer.getCpName());
            company.setContacts(companyCustomer.getCpContact());

            company.setCreateTime(now);
            company.setCreateUser(userInfo.getLoginName());
            spsCompanyDao.save(company);

            companyCustomer.setMemberId(member.getUserId());
            spsCompanyCustomerDao.save(companyCustomer);
        }
        return result;
    }

    @Override
    @Transactional
    public SaveResult update(UserInfo userInfo, CompanyCustomer companyCustomer) {
        SaveResult result = validate(companyCustomer);
        if (!result.isSuccess()) {
            return result;
        }

        // 修改者信息
        companyCustomer.setModifiedTime(new Date());
        companyCustomer.setModifiedUser(userInfo.getLoginName());
        spsCompanyCustomerDao.update(companyCustomer);
        return result;
    }

    /**
     * 校验企业客户请求信息
     *
     * @param companyCustomer
     * @return
     */
    private SaveResult validate(CompanyCustomer companyCustomer) {
        SaveResult result = new SaveResult();
        if (StringUtils.isBlank(companyCustomer.getCpContactMobile())) {
            result.set(ResultCode.ERROR_401, "联系人手机号码不能为空");
            return result;
        }
        if (!BizUtils.checkMobile(companyCustomer.getCpContactMobile())) {
            result.set(ResultCode.ERROR_401, "联系人手机号码格式不正确");
            return result;
        }
        if (StringUtils.isBlank(companyCustomer.getCpAddress())) {
            result.set(ResultCode.ERROR_401, "业主地址不能为空");
            return result;
        }
        if (!BizUtils.checkLength(companyCustomer.getCpAddress(), 64)) {
            result.set(ResultCode.ERROR_401, "业主地址名字过长");
            return result;
        }
        if (StringUtils.isBlank(companyCustomer.getCpName())) {
            result.set(ResultCode.ERROR_401, "业主单位不能为空");
            return result;
        }
        if (!BizUtils.checkLength(companyCustomer.getCpName(), 30)) {
            result.set(ResultCode.ERROR_401, "业主单位名字过长");
            return result;
        }

        if (StringUtils.isBlank(companyCustomer.getMobile())) {
            result.set(ResultCode.ERROR_401, "账号不能为空");
            return result;
        }
        if (!BizUtils.checkMobile(companyCustomer.getMobile())) {
            result.set(ResultCode.ERROR_401, "账号格式不正确");
            return result;
        }
        if (StringUtils.isBlank(companyCustomer.getCpContact())) {
            result.set(ResultCode.ERROR_401, "业主单位负责人不能为空");
            return result;
        }
        if (!BizUtils.checkLength(companyCustomer.getCpContact(), 30)) {
            result.set(ResultCode.ERROR_401, "业主单位负责人名字过长");
            return result;
        }
        if (StringUtils.isBlank(companyCustomer.getCpContactMobile())) {
            result.set(ResultCode.ERROR_401, "联系人手机号码不能为空");
            return result;
        }
        if (!BizUtils.checkMobile(companyCustomer.getCpContactMobile())) {
            result.set(ResultCode.ERROR_401, "联系人手机号码格式不正确");
            return result;
        }
        return result;
    }

    @Override
    @Transactional
    public ResetPasswordResult resetPassword(UserInfo userInfo, Long customerId) {
        ResetPasswordResult result = new ResetPasswordResult();

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", customerId);
        queryMap.put("companyId", userInfo.getCompanyId());
        CompanyCustomer companyCustomer = spsCompanyCustomerDao.getCompanyCustomerByMap(queryMap);
        if (companyCustomer == null) {
            result.set(ResultCode.ERROR_401, "客户信息不存在");
            return result;
        }

        SysUserEntity sysUserEntity = sysUserDao.queryObject(companyCustomer.getMemberId());
        if (sysUserEntity == null) {
            result.set(ResultCode.ERROR_401, "客户信息不存在");
            return result;
        }

        final Long userId = sysUserEntity.getUserId();
        final String password = String.valueOf(getRandNum());
        final String mobile = companyCustomer.getMobile().trim();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("newPasswd", PasswordUtil.encode(password));
        int c = sysUserDao.updatePassword(map);
        if (c < 1) {
            result.set(ResultCode.ERROR_401, "密码重置失败");
            return result;
        }

        // 给客户推送登录密码
        ThreadUtil.getSortTimeOutThread(new Runnable() {
            @Override
            public void run() {
                // 发送推送消息
                Map<String, String> params = new HashMap<>();
                params.put("password", password);
                messageInfoService.sendSmsMessage(userId, mobile, MessageId.NEW_PASSWORD_CODE, params);
            }
        });
        return result;
    }

    @Override
    public PageResult<CustomerInfo> search(ListPageQuery listPageQuery) {
        PageResult<CustomerInfo> result = new PageResult<CustomerInfo>();
        int total = spsCompanyCustomerDao.queryCount(listPageQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<CompanyCustomer> list = spsCompanyCustomerDao.queryList(listPageQuery);
            List<CustomerInfo> list2=new ArrayList<CustomerInfo>();
            for(CompanyCustomer companyCustomer:list){
            	list2.add(transform(companyCustomer));
            }
            result.setData(list2);
            nextOffset = listPageQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }
        result.setOffset(listPageQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }
    private CustomerInfo transform(CompanyCustomer companyCustomer){
    	CustomerInfo customerInfo=new CustomerInfo();
    	if(companyCustomer!=null){
    		BeanUtils.copyProperties(companyCustomer, customerInfo);
        	customerInfo.setAddress(companyCustomer.getCpAddress());
        	customerInfo.setContactTel(companyCustomer.getCpContactMobile());
        	customerInfo.setPhone(companyCustomer.getCpPhone());
        	customerInfo.setName(companyCustomer.getCpName());
        	customerInfo.setLeader(companyCustomer.getCpContact());
    	}
    	return customerInfo;
    }

    @Override
    public CompanyCustomer getByCustomerId(Long customerId) {
        return spsCompanyCustomerDao.getByCustomerId(customerId);
    }

    
    @Override
	public CustomerInfo getCustomerInfoByCustomerId(Long customerId) {
    	CompanyCustomer companyCustomer = spsCompanyCustomerDao.getByCustomerId(customerId);
    	CustomerInfo customerInfo = new CustomerInfo();
    	customerInfo.setId(companyCustomer.getId());
    	customerInfo.setMemberId(companyCustomer.getMemberId());
    	customerInfo.setMobile(companyCustomer.getMobile());
    	customerInfo.setName(companyCustomer.getCpName());
    	customerInfo.setAddress(companyCustomer.getCpAddress());
    	customerInfo.setPhone(companyCustomer.getCpPhone());
		return customerInfo;
	}
    
    /**
     * 获取6位随机数
     *
     * @return
     */
    private static int getRandNum() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }
    /*
	@Override
	@Deprecated
	public PageResult<CustomerInfo> customerInfoList(HashMap<String, Object> map) {
		PageResult<CustomerInfo> result = new PageResult<CustomerInfo>();
		
		ListPageQuery listPageQuery = new ListPageQuery();
		listPageQuery.setCompanyId((Long)map.get("companyId"));
		listPageQuery.setPageSize((Integer)map.get("limit"));
		listPageQuery.setOffset((Integer)map.get("offset"));
		listPageQuery.setCpName((String)map.get("name"));
		listPageQuery.setMobile((String)map.get("mobile"));
		String createTimeStart = (String) map.get("createTimeStart");
		String createTimeEnd = (String) map.get("createTimeEnd");
		if (createTimeStart != null && !createTimeStart.equals("")) {
			Date parse = DateUtils.parse(createTimeStart, DateUtils.DATE_PATTERN);
			if (parse != null) {
				listPageQuery.setCreateTimeStart(parse);
			}
		}
		if (createTimeEnd != null && !createTimeEnd.equals("")) {
			Date parse = DateUtils.parse(createTimeEnd, DateUtils.DATE_PATTERN);
			if (parse != null) {
				listPageQuery.setCreateTimeEnd(parse);
			}
		}
		
		int total = spsCompanyCustomerDao.queryCount(listPageQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<CompanyCustomer> list = spsCompanyCustomerDao.queryPageList(listPageQuery);
            List<CustomerInfo> customerInfoList = new ArrayList<CustomerInfo>();
            for (CompanyCustomer companyCustomer : list) {
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setId(companyCustomer.getId());
				customerInfo.setMobile(companyCustomer.getMobile());
				customerInfo.setName(companyCustomer.getCpName());
            	customerInfo.setStatus(companyCustomer.getStatus());
            	String invitationTime = DateUtils.format(companyCustomer.getInvitationTime(), DateUtils.DATE_PATTERN);
            	customerInfo.setInvitationTime(invitationTime);
            	customerInfoList.add(customerInfo);
			}
            result.setData(customerInfoList);
            nextOffset = listPageQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
        	result.setHasNext(true);
        }
        result.setOffset(listPageQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
		return result;
	}
*/

	@Override
	public List<CompanyCustomer> getAllCompanyCustomerList() {
		return spsCompanyCustomerDao.getAllCompanyCustomerList();
	}
	
}
