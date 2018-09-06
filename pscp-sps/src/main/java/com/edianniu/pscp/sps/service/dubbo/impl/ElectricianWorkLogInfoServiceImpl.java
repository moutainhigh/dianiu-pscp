package com.edianniu.pscp.sps.service.dubbo.impl;

import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.sps.bean.workorder.worklog.DetailReqData;
import com.edianniu.pscp.sps.bean.workorder.worklog.DetailResult;
import com.edianniu.pscp.sps.bean.workorder.worklog.ElectricianWorkLogInfo;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListQuery;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListReqData;
import com.edianniu.pscp.sps.bean.workorder.worklog.ListResult;
import com.edianniu.pscp.sps.commons.PageResult;
import com.edianniu.pscp.sps.commons.ResultCode;
import com.edianniu.pscp.sps.service.ElectricianWorkLogService;
import com.edianniu.pscp.sps.service.dubbo.ElectricianWorkLogInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName: ElectricianWorkLogInfoServiceImpl
 * Author: tandingbo
 * CreateTime: 2017-05-12 14:32
 */
@Service
@Repository("electricianWorkLogInfoService")
public class ElectricianWorkLogInfoServiceImpl implements ElectricianWorkLogInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ElectricianWorkLogInfoServiceImpl.class);

    @Autowired
    @Qualifier("electricianWorkLogService")
    private ElectricianWorkLogService electricianWorkLogService;
    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Override
    public ListResult list(ListReqData listReqData) {
        ListResult result = new ListResult();
        if (listReqData.getWorkOrderId() == null && StringUtils.isBlank(listReqData.getOrderId())) {
            return result;
        }
        try {
            ListQuery listQuery = new ListQuery();
            BeanUtils.copyProperties(listReqData, listQuery);

            PageResult<ElectricianWorkLogInfo> pageResult = electricianWorkLogService.selectPageWorkLogInfo(listQuery);
            result.setWorkLogInfoList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Electrician WorkOrder list:{}", e);
        }
        return result;
    }
    
    @Override
    public DetailResult detail(DetailReqData detailReqData){
    	DetailResult result = new DetailResult();
    	try {
			Long uid = detailReqData.getUid();
			if (null == uid) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
    		if (!getUserInfoResult.isSuccess()) {
    			result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
    		UserInfo userInfo = getUserInfoResult.getMemberInfo();
    		if (null == userInfo) {
    			result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
    		if (null == detailReqData.getId()) {
    			result.set(ResultCode.ERROR_201, "id不能为空");
				return result;
			}
    		ElectricianWorkLogInfo info = electricianWorkLogService.getDetail(detailReqData.getId(), detailReqData.getSource());
    		result.setWorkLogInfo(info);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("workdetail detail error:{}", e);
		}
    	return result;
    }
}
