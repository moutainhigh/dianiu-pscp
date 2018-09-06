package com.edianniu.pscp.mis.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.history.FacilitatorHistoryReqData;
import com.edianniu.pscp.mis.bean.history.FacilitatorHistoryResult;
import com.edianniu.pscp.mis.bean.history.vo.FacilitatorHistoryVO;
import com.edianniu.pscp.mis.bean.query.history.FacilitatorHistoryQuery;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.commons.ResultCode;
import com.edianniu.pscp.mis.service.CompanyElectricianService;
import com.edianniu.pscp.mis.service.dubbo.CompanyElectricianInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: CompanyElectricianServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-10-30 11:13
 */
@Service
@Repository("companyElectricianInfoService")
public class CompanyElectricianServiceImpl implements CompanyElectricianInfoService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyElectricianServiceImpl.class);

    @Autowired
    private CompanyElectricianService companyElectricianService;

    /**
     * 获取电工历史服务商
     *
     * @param reqData
     * @return
     */
    @Override
    public FacilitatorHistoryResult queryFacilitatorHistory(FacilitatorHistoryReqData reqData) {
        FacilitatorHistoryResult result = new FacilitatorHistoryResult();
        try {
            if (reqData.getOffset() == null) {
                reqData.setOffset(0);
            }

            FacilitatorHistoryQuery historyQuery = new FacilitatorHistoryQuery();
            BeanUtils.copyProperties(reqData, historyQuery);

            PageResult<FacilitatorHistoryVO> pageResult = companyElectricianService.queryFacilitatorHistory(historyQuery);
            if (pageResult.getData() != null) {
                result.setCompanyList(pageResult.getData());
            }
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
