package com.edianniu.pscp.cs.service.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cs.bean.company.BindingFacilitatorReqData;
import com.edianniu.pscp.cs.bean.company.BindingFacilitatorResult;
import com.edianniu.pscp.cs.bean.company.vo.BindingFacilitatorVO;
import com.edianniu.pscp.cs.bean.query.BindingFacilitatorQuery;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.CompanyCustomerService;
import com.edianniu.pscp.cs.service.dubbo.CompanyCustomerInfoService;

/**
 * ClassName: CompanyCustomerServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-10-30 15:13
 */
@Service
@Repository("csCompanyCustomerInfoService")
public class CompanyCustomerServiceImpl implements CompanyCustomerInfoService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyCustomerServiceImpl.class);

    @Autowired
    private CompanyCustomerService companyCustomerService;

    /**
     * 获取客户绑定过的服务商信息
     *
     * @param listReqData
     * @return
     */
    @Override
    public BindingFacilitatorResult queryBindingFacilitator(BindingFacilitatorReqData listReqData) {
        BindingFacilitatorResult result = new BindingFacilitatorResult();
        try {
            if (listReqData.getOffset() == null) {
                listReqData.setOffset(0);
            }

            BindingFacilitatorQuery listQuery = new BindingFacilitatorQuery();
            BeanUtils.copyProperties(listReqData, listQuery);
            PageResult<BindingFacilitatorVO> pageResult = companyCustomerService.queryBindingFacilitator(listQuery);
            result.setCompanyList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("nearbyBuss:{}", e);
        }
        return result;
    }
}
