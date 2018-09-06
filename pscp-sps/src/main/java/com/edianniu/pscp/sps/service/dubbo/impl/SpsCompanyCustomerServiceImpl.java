package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyCustomerStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.DefaultResult;
import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.customer.*;
import com.edianniu.pscp.sps.bean.customer.vo.CustomerVO;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.domain.Company;
import com.edianniu.pscp.sps.domain.CompanyCustomer;
import com.edianniu.pscp.sps.service.EngineeringProjectService;
import com.edianniu.pscp.sps.service.SpsCompanyCustomerService;
import com.edianniu.pscp.sps.service.SpsCompanyService;
import com.edianniu.pscp.sps.service.dubbo.SpsCompanyCustomerInfoService;
import com.edianniu.pscp.sps.util.DateUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: SpsCompanyCustomerServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-17 09:52
 */
@Service
@Repository("spsCompanyCustomerInfoService")
public class SpsCompanyCustomerServiceImpl implements SpsCompanyCustomerInfoService {
    private static final Logger logger = LoggerFactory.getLogger(SpsCompanyCustomerServiceImpl.class);

    @Autowired
    @Qualifier("spsCompanyService")
    private SpsCompanyService spsCompanyService;
    @Autowired
    @Qualifier("spsCompanyCustomerService")
    private SpsCompanyCustomerService spsCompanyCustomerService;
    @Autowired
    @Qualifier("engineeringProjectService")
    private EngineeringProjectService engineeringProjectService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Override
    public CustomerResult selectCustomerByCompanyId(Long companyId) {
        CustomerResult result = new CustomerResult();
        try {
            Company company = spsCompanyService.getCompanyById(companyId);
            if (company == null || !company.getStatus().equals(2)) {
                result.setCustomerList(new ArrayList<CustomerVO>());
                return result;
            }
            List<Integer> statusList = new ArrayList<>();
            statusList.add(CompanyCustomerStatus.BOUND.getValue());
            List<CustomerVO> customerVOS = spsCompanyCustomerService.selectCustomerVOByCompanyId(companyId, statusList);
            result.setCustomerList(customerVOS);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }

    @Override
    public Result delete(Long uid, Long[] ids) {
        Result result = new DefaultResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.set(getUserInfoResult.getResultCode(), getUserInfoResult.getResultMessage());
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            for (Long id : ids) {
                CompanyCustomer companyCustomer = spsCompanyCustomerService.getById(id);
                if (companyCustomer == null) {
                    result.set(ResultCode.ERROR_201, "客户不存在");
                    return result;
                }
                if (!userInfo.getCompanyId().equals(companyCustomer.getCompanyId())) {
                    result.set(ResultCode.ERROR_202, "没有权限删除:" + companyCustomer.getCpName());
                    return result;
                }
                if (engineeringProjectService.existByCustomerId(id)) {
                    result.set(ResultCode.ERROR_203, "客户(" + companyCustomer.getCpName() + "):有项目关联，无法删除");
                    return result;
                }
            }
            spsCompanyCustomerService.delete(ids, userInfo.getLoginName());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("deleteCustomers:{}", e);
        }

        return result;
    }

    /**
     * 分页获取客户信息
     *
     * @param listPageReqData
     * @return
     */
    @Override
    public ListPageResult listPage(ListPageReqData listPageReqData) {
        ListPageResult result = new ListPageResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(listPageReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(getUserInfoResult.getResultCode(), getUserInfoResult.getResultMessage());
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            ListPageQuery listPageQuery = new ListPageQuery();
            BeanUtils.copyProperties(listPageReqData, listPageQuery);
            listPageQuery.setCompanyId(userInfo.getCompanyId());

            if (StringUtils.isNotBlank(listPageReqData.getCreateTimeStartConvert())) {
                Date createTimeStart = DateUtils.parse(listPageReqData.getCreateTimeStartConvert());
                listPageQuery.setCreateTimeStart(DateUtils.getStartDate(createTimeStart));
            }

            if (StringUtils.isNotBlank(listPageReqData.getCreateTimeEndConvert())) {
                Date createTimeEnd = DateUtils.parse(listPageReqData.getCreateTimeEndConvert());
                listPageQuery.setCreateTimeEnd(DateUtils.getEndDate(createTimeEnd));
            }

            PageResult<CustomerInfo> pageResult = spsCompanyCustomerService.search(listPageQuery);
            result.setCustomerList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("deleteCustomers:{}", e);
        }
        return result;
    }
    
    
    /**
     * 获取客户列表，门户使用
     * @return
     */
   /* @Override
    public CustomerInfoListPageResult customerInfoList(HashMap<String, Object> map){
    	CustomerInfoListPageResult result = new CustomerInfoListPageResult();
    	try {
    		PageResult<CustomerInfo> pageResult = spsCompanyCustomerService.customerInfoList(map);
        	result.setCustomerInfoList(pageResult.getData());
        	result.setHasNext(pageResult.isHasNext());
        	result.setNextOffset(pageResult.getNextOffset());
        	result.setTotalCount(pageResult.getTotal());
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("list:{}", e);
		}
    	
    	return result;
    }*/

    /**
     * 保存客户信息
     *
     * @param saveReqData
     * @return
     */
    @Override
    public SaveResult save(SaveReqData saveReqData) {
        SaveResult result = new SaveResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(saveReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(getUserInfoResult.getResultCode(), getUserInfoResult.getResultMessage());
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            if (saveReqData.getCompanyCustomer() == null) {
                result.set(ResultCode.ERROR_401, "客户信息不能为空");
                return result;
            }

            CompanyCustomer companyCustomer = saveReqData.getCompanyCustomer();
            if (companyCustomer.getId() == null) {
                // 新增
                result = spsCompanyCustomerService.save(userInfo, companyCustomer);
            } else {
                // 修改
                result = spsCompanyCustomerService.update(userInfo, companyCustomer);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("deleteCustomers:{}", e);
        }
        return result;
    }

    /**
     * 客户详情信息
     *
     * @param detailsReqData
     * @return
     */
    @Override
    public DetailsResult details(DetailsReqData detailsReqData) {
        DetailsResult result = new DetailsResult();
        try {
            CompanyCustomer companyCustomer = spsCompanyCustomerService.getById(detailsReqData.getCustomerId());
            if (companyCustomer == null) {
                result.set(ResultCode.ERROR_401, "客户信息不存在");
                return result;
            }
            result.setCompanyCustomer(companyCustomer);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("deleteCustomers:{}", e);
        }
        return result;
    }

    /**
     * 重置客户登录密码
     *
     * @param resetPasswordReqData
     * @return
     */
    @Override
    public ResetPasswordResult resetPassword(ResetPasswordReqData resetPasswordReqData) {
        ResetPasswordResult result = new ResetPasswordResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(resetPasswordReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(getUserInfoResult.getResultCode(), getUserInfoResult.getResultMessage());
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            if (resetPasswordReqData.getCustomerId() == null) {
                result.set(ResultCode.ERROR_401, "请选择一条数据");
                return result;
            }

            result = spsCompanyCustomerService.resetPassword(userInfo, resetPasswordReqData.getCustomerId());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("deleteCustomers:{}", e);
        }
        return result;
    }

    @Override
    public CompanyCustomer getByCustomerId(Long customerId) {
        return spsCompanyCustomerService.getByCustomerId(customerId);
    }
    
    @Override
    public CustomerInfo getCustomerInfoByCustomerId(Long customerId) {
    	return spsCompanyCustomerService.getCustomerInfoByCustomerId(customerId);
    }
    
    /**
     * 获取所有客户列表
     * @return
     */
    @Override
    public List<CustomerVO> getAllCompanyCustomerList(){
    	List<CustomerVO> VOlist = new ArrayList<>();
    	List<CompanyCustomer> list = spsCompanyCustomerService.getAllCompanyCustomerList();
    	if (CollectionUtils.isNotEmpty(list)) {
			for (CompanyCustomer companyCustomer : list) {
				CustomerVO vo = new CustomerVO();
				BeanUtils.copyProperties(companyCustomer, vo);
				vo.setName(companyCustomer.getCpName());
				vo.setPhone(companyCustomer.getCpPhone());
				vo.setContact(companyCustomer.getCpContact());
				vo.setAddress(companyCustomer.getCpAddress());
				vo.setContactMobile(companyCustomer.getContactTel());
				VOlist.add(vo);
			}
		}
    	return VOlist;
    }
    
}
