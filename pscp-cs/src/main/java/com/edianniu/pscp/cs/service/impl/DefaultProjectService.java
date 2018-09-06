package com.edianniu.pscp.cs.service.impl;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.cs.bean.engineeringproject.*;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.bean.needs.ResponsedCompany;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.dao.CompanyCustomerDao;
import com.edianniu.pscp.cs.dao.EngineeringProjectDao;
import com.edianniu.pscp.cs.dao.NeedsDao;
import com.edianniu.pscp.cs.dao.NeedsOrderDao;
import com.edianniu.pscp.cs.dao.RoomDao;
import com.edianniu.pscp.cs.domain.CompanyCustomer;
import com.edianniu.pscp.cs.domain.Room;
import com.edianniu.pscp.cs.service.CommonAttachmentService;
import com.edianniu.pscp.cs.service.ProjectService;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.cs.util.MoneyUtils;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.attachment.common.BusinessType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujianjian 2017年9月19日下午2:56:34
 */
@Transactional
@Service
@Repository("projectService")
public class DefaultProjectService implements ProjectService {

	@Autowired
	EngineeringProjectDao engineeringProjectDao;

	@Autowired
	CommonAttachmentService commonAttachmentService;

	@Autowired
	NeedsDao needsDao;

	@Autowired
	NeedsOrderDao needsOrderDao;

	@Autowired
	RoomDao roomDao;
	
	@Autowired
	CompanyCustomerDao companyCustomerDao;

	// 项目列表
	@Override
	public PageResult<EngineeringProjectVO> listProject(ListReqData listReqData, Long memberId) {
		PageResult<EngineeringProjectVO> result = new PageResult<EngineeringProjectVO>();
		// 封装项目查询状态，如果查询状态为空，则查询所有
		List<Integer> queryStatusList = null;
		String status = listReqData.getStatus();
		if (StringUtils.isNotBlank(status)) {
			if (ProjectQueryStatus.isExist(status)) {
				queryStatusList = ProjectQueryStatus.getByValue(status).getStatus();
			}
		}
		// 获取客户所在公司所有的客户服务商对应关系
		HashMap<String, Object> queryCustomerMap = new HashMap<>();
		queryCustomerMap.put("uid", memberId);
		List<CompanyCustomer> companyCustomers = companyCustomerDao.getList(queryCustomerMap);
		List<Long> customerIdList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(companyCustomers)) {
			for (CompanyCustomer companyCustomer : companyCustomers) {
				Long customerId = companyCustomer.getId();
				customerIdList.add(customerId);
			}
		}
		if (CollectionUtils.isEmpty(customerIdList)) {//无客户服务商对应关系
			return result;
		}
		
		HashMap<String, Object> queryProjectMap = new HashMap<String, Object>();
		queryProjectMap.put("queryStatusList", queryStatusList);
		queryProjectMap.put("customerIdList", customerIdList);
		queryProjectMap.put("pageSize", listReqData.getPageSize());
		queryProjectMap.put("offset", listReqData.getOffset());
		// 客户查询时，新加参数
		queryProjectMap.put("projectNo", listReqData.getProjectNo());
		queryProjectMap.put("name", listReqData.getName());
		queryProjectMap.put("companyName", listReqData.getCompanyName());
		queryProjectMap.put("workTime", DateUtils.parse(listReqData.getWorkTime()));
		queryProjectMap.put("createTime", listReqData.getCreateTime());
		String subStatusDesc = listReqData.getSubStatus();
		if (StringUtils.isNotBlank(subStatusDesc)) { //子状态
			EngineeringProjectStatus projectStatus = EngineeringProjectStatus.getByDesc(subStatusDesc);
			queryProjectMap.put("subStatus", projectStatus.getValue());
		}

		Integer total = engineeringProjectDao.projectListCount(queryProjectMap);
		Integer nextOffset = 0;

		if (total > 0) {
			List<EngineeringProjectVO> entityList = engineeringProjectDao.queryProjectList(queryProjectMap);

			result.setData(entityList);
			nextOffset = listReqData.getOffset() + entityList.size();
			result.setNextOffset(nextOffset);
		}
		if (nextOffset > 0 && nextOffset < total) {
			result.setHasNext(true);
		}
		result.setTotal(total);
		result.setOffset(listReqData.getOffset());
		result.setNextOffset(nextOffset);
		return result;
	}

	// 项目详情
	@Override
	public DetailsResult projectByProjectNo(String projectNo) {
		DetailsResult result = new DetailsResult();

		// 获得项目信息
		HashMap<String, Object> map =new HashMap<>();
		map.put("projectNo", projectNo);
		EngineeringProjectVO projectInfo = engineeringProjectDao.queryProjectInfo(map);
		if (null == projectInfo) {
			result.set(400, "项目不存在");
			return result;
		}
		// 查询合作附件 合作附件与项目关联
		Long projectId = projectInfo.getId();
		projectInfo.setCooperationInfo(commonAttachmentService.structureAttachmentList(projectId,
				BusinessType.COOPERATION_ATTACHMENT.getValue()));
		// 查询实际结算金额
		ActualPriceInfo actualPriceInfo = new ActualPriceInfo();
		String actualPrice = projectInfo.getActualSettlementAmount();
		actualPriceInfo.setActualPrice(actualPrice);
		// 查询结算附件 结算附件与项目关联
		actualPriceInfo.setAttachmentList(commonAttachmentService.structureAttachmentList(projectId,
				BusinessType.ACTUAL_PRICE_ATTACHMENT.getValue()));
		projectInfo.setActualPriceInfo(actualPriceInfo);
		// 获取项目联系人、联系电话、联系地址
		Long customerId = projectInfo.getCustomerId();
		CompanyCustomer companyCustomer = companyCustomerDao.getByCustomerId(customerId);
		if (null != companyCustomer) {
			projectInfo.setContactPerson(companyCustomer.getCpContact());
			projectInfo.setContactNumber(companyCustomer.getCpContactMobile());
			projectInfo.setContactAddr(companyCustomer.getCpAddress());
		}

		// 获取需求信息
		NeedsVO needsVO = new NeedsVO();
		Long needsId = projectInfo.getNeedsId();
		if (null != needsId) {
			NeedsVO needsInfo = needsDao.getNeedsByOrderId(null, needsId);
			if (null != needsInfo) {
				// 获取需求附件 需求附件与需求关联
				needsVO.setAttachmentList(
						commonAttachmentService.structureAttachmentList(needsId, BusinessType.NEEDS_ATTACHMENT.getValue()));
				// 关联配电房
				String distributionRooms = needsInfo.getDistributionRooms();
				if (StringUtils.isNotBlank(distributionRooms)) {
					String[] roomIdsArray = distributionRooms.split(",");
					List<Long> roomIds = new ArrayList<>();
					for (String roomIdStr : roomIdsArray) {
						if (StringUtils.isBlank(roomIdStr)) continue;
		                roomIds.add(Long.valueOf(roomIdStr));
					}
					Map<String, Object> queryMap = new HashMap<>();
		            queryMap.put("roomIds", roomIds);
		            List<Room> roomList = roomDao.queryRoomList(queryMap);
		            
		            if (CollectionUtils.isNotEmpty(roomList)) {
		            	List<HashMap<String, Object>> list = new ArrayList<>();
		                for (Room room : roomList) {
		                	HashMap<String, Object> roomMap = new HashMap<String, Object>();
		                	roomMap.put("id", room.getId());
		                	roomMap.put("name", room.getName());
		                	list.add(roomMap);
		                }
		                distributionRooms = JSON.toJSONString(list);
		            }
					needsVO.setDistributionRooms(distributionRooms);
				}
				// 报价及附件 报价附件与响应订单关联
				QuotedInfo quotedInfo = new QuotedInfo();
				List<Integer> queryStatusList = new ArrayList<Integer>();
				queryStatusList.add(NeedsOrderStatus.COOPERATED.getValue());
				HashMap<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("queryStatusList", queryStatusList);
				queryMap.put("needsId", needsId);
				List<ResponsedCompany> responsedCompanys = needsOrderDao.queryList(queryMap);
				if (CollectionUtils.isNotEmpty(responsedCompanys)) {
					ResponsedCompany responsedCompany = responsedCompanys.get(0);
					String quotedPrice = responsedCompany.getQuotedPrice();
					quotedPrice = MoneyUtils.format(MoneyUtils.formatToMoney(quotedPrice));
					quotedInfo.setQuotedPrice(quotedPrice);
					// 获取报价附件
					Long respondId = responsedCompany.getId();
					quotedInfo.setAttachmentList(commonAttachmentService.structureAttachmentList(respondId,
							BusinessType.QUOTE_ATTACHMENT.getValue()));
				}
				needsVO.setQuotedInfo(quotedInfo);
			}
		} 
		result.setNeedsInfo(needsVO);
		result.setProjectInfo(projectInfo);
		return result;
	}

	@Override
	public List<EngineeringProjectVO> queryProjectList(HashMap<String, Object> queryMap) {
		return engineeringProjectDao.queryProjectList(queryMap);
	}

	@Override
	public List<Long> getProjectIds(Long uid) {
		return engineeringProjectDao.getProjectId(uid);
	}

}
