package com.edianniu.pscp.sps.service.dubbo;
import java.util.List;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.customer.CustomerInfo;
import com.edianniu.pscp.sps.bean.customer.CustomerResult;
import com.edianniu.pscp.sps.bean.customer.DetailsReqData;
import com.edianniu.pscp.sps.bean.customer.DetailsResult;
import com.edianniu.pscp.sps.bean.customer.ListPageReqData;
import com.edianniu.pscp.sps.bean.customer.ListPageResult;
import com.edianniu.pscp.sps.bean.customer.ResetPasswordReqData;
import com.edianniu.pscp.sps.bean.customer.ResetPasswordResult;
import com.edianniu.pscp.sps.bean.customer.SaveReqData;
import com.edianniu.pscp.sps.bean.customer.SaveResult;
import com.edianniu.pscp.sps.bean.customer.vo.CustomerVO;
import com.edianniu.pscp.sps.domain.CompanyCustomer;

/**
 * ClassName: SpsCompanyCustomerInfoService
 * Author: tandingbo
 * CreateTime: 2017-05-17 09:51
 */
public interface SpsCompanyCustomerInfoService {

    CustomerResult selectCustomerByCompanyId(Long companyId);

    /**
     * 删除客户
     *
     * @param uid
     * @param ids
     * @return
     */
    Result delete(Long uid, Long[] ids);

    /**
     * 分页获取客户信息
     *
     * @param listPageReqData
     * @return
     */
    ListPageResult listPage(ListPageReqData listPageReqData);

    /**
     * 保存客户信息
     *
     * @param saveReqData
     * @return
     */
    SaveResult save(SaveReqData saveReqData);

    /**
     * 客户详情信息
     *
     * @param detailsReqData
     * @return
     */
    DetailsResult details(DetailsReqData detailsReqData);

    /**
     * 重置客户登录密码
     *
     * @param resetPasswordReqData
     * @return
     */
    ResetPasswordResult resetPassword(ResetPasswordReqData resetPasswordReqData);

    CompanyCustomer getByCustomerId(Long customerId);
    
    /*//门户客户列表
    CustomerInfoListPageResult customerInfoList(HashMap<String, Object> map);*/

	CustomerInfo getCustomerInfoByCustomerId(Long customerId);

	/**
	 * 获取所有企业客户
	 * @return
	 */
	List<CustomerVO> getAllCompanyCustomerList();
}