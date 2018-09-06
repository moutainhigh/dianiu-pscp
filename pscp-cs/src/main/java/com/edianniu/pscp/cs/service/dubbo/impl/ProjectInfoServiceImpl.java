package com.edianniu.pscp.cs.service.dubbo.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.edianniu.pscp.cs.bean.engineeringproject.DetailsReqData;
import com.edianniu.pscp.cs.bean.engineeringproject.DetailsResult;
import com.edianniu.pscp.cs.bean.engineeringproject.ListReqData;
import com.edianniu.pscp.cs.bean.engineeringproject.ListResult;
import com.edianniu.pscp.cs.bean.engineeringproject.ProjectQueryStatus;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.ProjectService;
import com.edianniu.pscp.cs.service.dubbo.ProjectInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

@Service
@Repository("projectInfoService")
public class ProjectInfoServiceImpl implements ProjectInfoService {
	private static final Logger logger = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);
	
	@Autowired
	@Qualifier("projectService")
	ProjectService projectService;
	@Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
	
	@Override
	public List<Long> getProjectIds(Long uid){
		return projectService.getProjectIds(uid);
	}
	
	//项目列表
	@Override
	public ListResult projectList(ListReqData listReqData){
		ListResult result = new ListResult();
		try {
			Long uid = listReqData.getUid();
			if (null==uid) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
			Long companyId = userInfo.getCompanyId();
			if (null == userInfo || null == companyInfo || null == companyId) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			if (companyId.equals(0)) { //平台用户
				return result; 
			}
			Long memberId = companyInfo.getMemberId();//所属公司memberId
			
			// 主状态
			String status = listReqData.getStatus();
			if (StringUtils.isNotBlank(status)) {
				if (!ProjectQueryStatus.isExist(status)) {
					result.set(ResultCode.ERROR_401, "状态值不合法");
					return result;
				}
			}
			// 子状态
			ProjectQueryStatus queryStatus = ProjectQueryStatus.getByValue(status);
			String subStatusDesc = listReqData.getSubStatus();
			if (StringUtils.isNotBlank(subStatusDesc) && !queryStatus.isProjectStatusExist(subStatusDesc)) {
				result.set(ResultCode.ERROR_401, "状态值不合法");
				return result;
			}
			
			if (StringUtils.isNotBlank(listReqData.getWorkTime())) {
				if(!BizUtils.checkYmd(listReqData.getWorkTime())){
					result.set(ResultCode.ERROR_401, "工作时间不合法");
					return result;
				}
			}
			if (StringUtils.isNotBlank(listReqData.getCreateTime())) {
				if(!BizUtils.checkYmd(listReqData.getCreateTime())){
					result.set(ResultCode.ERROR_401, "提交时间不合法");
					return result;
				}
			}
			
			PageResult<EngineeringProjectVO> pageResult = projectService.listProject(listReqData, memberId);
			
			result.setProjects(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Project list:{}", e);
		}
		return result;
	}

	//项目详情
	@Override
	public DetailsResult projectDetails(DetailsReqData detailsReqData) {
		DetailsResult result = new DetailsResult();
		try {
			Long uid = detailsReqData.getUid();
			if (null==uid) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(detailsReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			result = projectService.projectByProjectNo(detailsReqData.getProjectNo());
			if (null == result.getProjectInfo()) {
				result.set(ResultCode.ERROR_401, "项目不存在");
				return result;
			}
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Project details:{}", e);
		}
		return result;
	}
	
	
}
