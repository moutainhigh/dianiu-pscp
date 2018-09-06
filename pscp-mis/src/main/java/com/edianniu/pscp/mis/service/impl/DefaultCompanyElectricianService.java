package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.history.vo.FacilitatorHistoryVO;
import com.edianniu.pscp.mis.bean.query.history.FacilitatorHistoryQuery;
import com.edianniu.pscp.mis.commons.PageResult;
import com.edianniu.pscp.mis.dao.CompanyElectricianDao;
import com.edianniu.pscp.mis.service.CompanyElectricianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DefaultCompanyElectricianService
 * Author: tandingbo
 * CreateTime: 2017-10-30 11:14
 */
@Service
@Repository("companyElectricianService")
public class DefaultCompanyElectricianService implements CompanyElectricianService {

    @Autowired
    private CompanyElectricianDao companyElectricianDao;

    /**
     * 获取电工历史服务商
     *
     * @param listQuery
     * @return
     */
    @Override
    public PageResult<FacilitatorHistoryVO> queryFacilitatorHistory(FacilitatorHistoryQuery listQuery) {
        PageResult<FacilitatorHistoryVO> result = new PageResult<FacilitatorHistoryVO>();
        int total = companyElectricianDao.queryFacilitatorHistoryCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<FacilitatorHistoryVO> list = companyElectricianDao.queryFacilitatorHistory(listQuery);

            result.setData(list);
            nextOffset = listQuery.getOffset() + list.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }
        result.setOffset(listQuery.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }
}
