package com.edianniu.pscp.cs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.cs.bean.*;
import com.edianniu.pscp.cs.bean.engineeringproject.EngineerProjectInfo;
import com.edianniu.pscp.cs.bean.engineeringproject.EngineeringProjectStatus;
import com.edianniu.pscp.cs.bean.engineeringproject.ProjectQueryStatus;
import com.edianniu.pscp.cs.bean.engineeringproject.vo.EngineeringProjectVO;
import com.edianniu.pscp.cs.bean.needs.*;
import com.edianniu.pscp.cs.bean.needs.keyword.ShieldingKeyWords;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsMarketVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsViewVO;
import com.edianniu.pscp.cs.bean.needsorder.InitDataReqData;
import com.edianniu.pscp.cs.bean.needsorder.InitDataResult;
import com.edianniu.pscp.cs.bean.query.NeedsMarketListQuery;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.dao.*;
import com.edianniu.pscp.cs.domain.*;
import com.edianniu.pscp.cs.service.CommonAttachmentService;
import com.edianniu.pscp.cs.service.NeedsService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.cs.util.MoneyUtils;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.attachment.common.BusinessType;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.sps.bean.project.vo.ProjectVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Repository("needsService")
@Transactional
public class DefaultNeedsService implements NeedsService {

    /**
     * 需求保证金(默认1000)
     */
    @Value(value = "${needs.order.deposit:1000}")
    private Double cautionMoney;

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private NeedsDao needsDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private NeedsOrderDao needsOrderDao;
    @Autowired
    private CompanyCustomerDao companyCustomerDao;
    @Autowired
    private EngineeringProjectDao engineeringProjectDao;
    @Autowired
    private CommonAttachmentService commonAttachmentService;

    /**
     * 需求详情
     */
    @Override
    public NeedsVO getNeedsById(Long id, String orderId) {
        // 查询需求信息
        NeedsVO needsVO = needsDao.getNeedsByOrderId(orderId, id);
        if (null == needsVO) {
            return null;
        }
        Long companyId = needsVO.getCompanyId();
        Company company = companyDao.getCompanyById(companyId);
        if (company != null) {
            String companyName = company.getName();
            needsVO.setCompanyName(companyName);
        }

        // 关联配电房
        String distributionRooms = needsVO.getDistributionRooms();
        if (StringUtils.isNotBlank(distributionRooms)) {
            List<Long> roomIds = new ArrayList<>();
            String[] roomIdsArray = distributionRooms.split(",");
            for (String roomIdStr : roomIdsArray) {
                if (StringUtils.isBlank(roomIdStr))
                    continue;
                roomIds.add(Long.valueOf(roomIdStr.trim()));
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

        Long needsId = needsVO.getId();
        // 查询需求附件
        needsVO.setAttachmentList(
                commonAttachmentService.structureAttachmentList(needsId, BusinessType.NEEDS_ATTACHMENT.getValue()));

        // 如果已合作，查询合作附件
        if (needsVO.getStatus().equals(NeedsStatus.COOPETATED.getValue())) {
            List<Integer> statusList = new ArrayList<>();
            List<Integer> status1 = ProjectQueryStatus.PROGRESSING.getStatus();
            List<Integer> status2 = ProjectQueryStatus.FINISHED.getStatus();
            statusList.addAll(status1);
            statusList.addAll(status2);
            HashMap<String, Object> map = new HashMap<>();
            map.put("statusList", statusList);
            map.put("needsId", needsId);
            EngineeringProjectVO engineerProjectInfo = engineeringProjectDao.queryProjectInfo(map);
            if (null != engineerProjectInfo) {
                Long projectId = engineerProjectInfo.getId();
                // 查询合作附件 合作附件与项目关联
                needsVO.setCooperationInfo(commonAttachmentService.structureAttachmentList(projectId, BusinessType.COOPERATION_ATTACHMENT.getValue()));
            }
        }
        return needsVO;
    }

    /**
     * 根据需求编号和服务商响应订单号查询响应的服务商信息
     */
    @Override
    public ResponsedCompany query(Long needsId, String responsedOrderId) {
        if (StringUtils.isNoneBlank(responsedOrderId)) {
            ResponsedCompany responsedCompany = needsOrderDao.query(needsId, responsedOrderId);
            if (responsedCompany == null) {
                return null;
            }

            String quotedPrice = responsedCompany.getQuotedPrice();
            if (StringUtils.isBlank(quotedPrice)) {
                quotedPrice = "0.0";
            }
            quotedPrice = MoneyUtils.format(MoneyUtils.formatToMoney(quotedPrice));
            responsedCompany.setQuotedPrice(quotedPrice);
            return responsedCompany;
        }
        return null;
    }

    /**
     * 保存需求及附件信息
     */
    @Override
    public void saveNeeds(SaveReqData saveReqData, UserInfo userInfo) {
        // 封装需求信息
        NeedsInfo needsInfo = new NeedsInfo();
        needsInfo.setCompanyId(userInfo.getCompanyId());
        String orderId = BizUtils.getOrderId(OrderIdPrefix.NEEDS_IDENTIFIER_PREFIX);
        needsInfo.setOrderId(orderId);
        needsInfo.setName(saveReqData.getName());
        needsInfo.setDescribe(saveReqData.getDescribe());
        Date publishCutoffTime = DateUtils.parse(saveReqData.getPublishCutoffTime(), DateUtils.DATE_PATTERN);
        publishCutoffTime = DateUtils.getEndDate(publishCutoffTime);
        Date workingStartTime = DateUtils.parse(saveReqData.getWorkingStartTime(), DateUtils.DATE_PATTERN);
        Date workingEndTime = DateUtils.parse(saveReqData.getWorkingEndTime(), DateUtils.DATE_PATTERN);
        workingEndTime = DateUtils.getEndDate(workingEndTime);
        needsInfo.setPublishCutoffTime(publishCutoffTime);
        needsInfo.setWorkingStartTime(workingStartTime);
        needsInfo.setWorkingEndTime(workingEndTime);
        needsInfo.setContactPerson(saveReqData.getContactPerson());
        needsInfo.setContactNumber(saveReqData.getContactNumber());
        needsInfo.setContactAddr(saveReqData.getContactAddr());
        needsInfo.setStatus(NeedsStatus.AUDITING.getValue());
        needsInfo.setDistributionRoomIds(saveReqData.getDistributionRoomIds());
        needsInfo.setCreateTime(new Date());
        needsInfo.setCreateUser(userInfo.getLoginName());
        needsInfo.setDeleted(0);
        needsInfo.setCautionMoney(cautionMoney);

        // 保存需求信息
        needsDao.save(needsInfo);

        // 获取附件业务外键needsId
        Long needsId = needsInfo.getId();
        // 保存附件
        commonAttachmentService.saveAttachmentHelper(needsId, userInfo, saveReqData.getAttachmentList(),
                BusinessType.NEEDS_ATTACHMENT.getValue());
    }

    /**
     * 重新发布需求
     */
    @Override
    public void republishNeeds(RepublishReqData republishReqData, UserInfo userInfo, NeedsVO needsVO) {
        // 封装需求信息
        NeedsInfo needsInfo = new NeedsInfo();
        needsInfo.setOrderId(republishReqData.getOrderId());
        needsInfo.setCompanyId(userInfo.getCompanyId());
        needsInfo.setName(republishReqData.getName());
        needsInfo.setDescribe(republishReqData.getDescribe());
        Date publishCutoffTime = DateUtils.parse(republishReqData.getPublishCutoffTime(), DateUtils.DATE_PATTERN);
        publishCutoffTime = DateUtils.getEndDate(publishCutoffTime);
        Date workingStartTime = DateUtils.parse(republishReqData.getWorkingStartTime(), DateUtils.DATE_PATTERN);
        Date workingEndTime = DateUtils.parse(republishReqData.getWorkingEndTime(), DateUtils.DATE_PATTERN);
        workingEndTime = DateUtils.getEndDate(workingEndTime);
        needsInfo.setPublishCutoffTime(publishCutoffTime);
        needsInfo.setWorkingStartTime(workingStartTime);
        needsInfo.setWorkingEndTime(workingEndTime);
        needsInfo.setContactPerson(republishReqData.getContactPerson());
        needsInfo.setContactNumber(republishReqData.getContactNumber());
        needsInfo.setContactAddr(republishReqData.getContactAddr());
        needsInfo.setStatus(NeedsStatus.AUDITING.getValue());
        needsInfo.setDistributionRoomIds(republishReqData.getDistributionRoomIds());
        needsInfo.setCreateTime(new Date());
        needsInfo.setCreateUser(userInfo.getLoginName());
        needsInfo.setDeleted(0);
        // 修改需求信息
        needsDao.update(needsInfo);

        // 获取要删除的附件ID
        String removedImgs = republishReqData.getRemovedImgs();
        if (!StringUtils.isBlank(removedImgs)) {
            commonAttachmentService.deleteAttachmentHelper(removedImgs, userInfo);
        }

        // 保存附件
        commonAttachmentService.saveAttachmentHelper(needsVO.getId(), userInfo, republishReqData.getAttachmentList(),
                BusinessType.NEEDS_ATTACHMENT.getValue());
    }

    /**
     * 取消需求
     */
    @Override
    public void cancelNeeds(NeedsVO needsVO, UserInfo userInfo, CompanyInfo companyInfo) {
        if (null == needsVO || null == userInfo || null == companyInfo) {
            return;
        }

        // 更改需求状态并下架(操作客户需求表)
        NeedsInfo needsInfo = new NeedsInfo();
        needsInfo.setOrderId(needsVO.getOrderId());
        needsInfo.setStatus(NeedsStatus.CANCELED.getValue());
        needsInfo.setOnShelves(Constants.TAG_NO);
        needsInfo.setModifiedTime(new Date());
        needsInfo.setModifiedUser("系统");
        needsDao.update(needsInfo);

        Integer needsStatus = needsVO.getStatus();
        // 如果该需求已被响应，则将已响应的服务商设为不符合状态(操作服务商响应订单表)
        if (needsStatus.equals(NeedsStatus.RESPONDING.getValue())) {
            List<Integer> oldNeedsOrderStatusList = new ArrayList<Integer>();
            oldNeedsOrderStatusList.add(NeedsOrderStatus.RESPONED.getValue());
            Integer newNeedsOrderStatus = NeedsOrderStatus.NOT_QUALIFIED.getValue();
            this.operationNeedsOrder(needsVO, oldNeedsOrderStatusList, newNeedsOrderStatus);
        }
        // 如果该需求报价中、已报价
        if (needsStatus.equals(NeedsStatus.QUOTING.getValue()) ||
                needsStatus.equals(NeedsStatus.QUOTED.getValue())) {
            // 将所有待报价、已报价的服务商设为不合作状态(操作服务商响应订单表)
            List<Integer> oldNeedsOrderStatusList = new ArrayList<Integer>();
            oldNeedsOrderStatusList.add(NeedsOrderStatus.WAITING_QUOTE.getValue());
            oldNeedsOrderStatusList.add(NeedsOrderStatus.QUOTED.getValue());
            Integer newNeedsOrderStatus = NeedsOrderStatus.NOT_COOPERATE.getValue();
            List<ResponsedCompany> responsedCompanies = this.operationNeedsOrder(needsVO, oldNeedsOrderStatusList, newNeedsOrderStatus);

            // 此时，该需求已转换成项目，还应将项目状态改为取消(操作工程项目表)
            HashMap<String, Object> queryProjectMap = new HashMap<>();
            queryProjectMap.put("needsId", needsVO.getId());
            List<EngineeringProjectVO> projectVOList = engineeringProjectDao.queryProjectList(queryProjectMap);
            if (CollectionUtils.isNotEmpty(projectVOList)) {
                Integer newStatus = EngineeringProjectStatus.CANCLED.getValue();
                this.operateProject(needsVO, null, newStatus);
            }

            // 此时，如果客户是被邀请状态，绑定状态改为拒绝(操作服务商客户表)
            Integer oldStatus = CompanyCustomerStatus.INVITATION.getValue();
            Integer newStatus = CompanyCustomerStatus.REJECT.getValue();
            Date invitationRejectTime = new Date();
            Date invitationAgreeTime = null;
            this.operateCompanyCustomer(companyInfo, responsedCompanies, oldStatus, newStatus, invitationRejectTime, invitationAgreeTime);
        }
    }

    /**
     * 需求询价
     */
    @Override
    public void quoteNeeds(NeedsVO needsVO, String orderId, String responsedOrderIds, UserInfo userInfo, CompanyInfo companyInfo) {
        if (null == needsVO || null == userInfo || null == companyInfo) {
            return;
        }
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(responsedOrderIds)) {
            return;
        }
        // 更改需求状态(操作客户需求表)
        NeedsInfo needsInfo = new NeedsInfo();
        needsInfo.setOrderId(orderId);
        needsInfo.setStatus(NeedsStatus.QUOTING.getValue());
        needsInfo.setModifiedTime(new Date());
        needsInfo.setModifiedUser("系统");
        needsInfo.setOnShelves(Constants.TAG_NO); 
        needsDao.update(needsInfo);

        String[] respOrderIds = responsedOrderIds.trim().split(",");
        if (ArrayUtils.isNotEmpty(respOrderIds)) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (String respOrder : respOrderIds) {
                // 将所选择报价的服务商改为待报价状态(操作服务商响应记录表)
                map.put("newStatus", NeedsOrderStatus.WAITING_QUOTE.getValue());
                map.put("needsId", needsVO.getId());
                map.put("responsedOrderId", respOrder.trim());
                needsOrderDao.operateNeedsOrder(map);
                map.clear();

                ResponsedCompany responsedCompany = query(needsVO.getId(), respOrder.trim());

                // 服务商邀请客户(操作服务商客户表)
                CompanyCustomer companyCustomer = this.insertOrUpdateCompanyCustomer(userInfo, companyInfo, responsedCompany);

                // 需求转项目(操作工程项目表)
                this.needsToProject(needsVO, userInfo, companyCustomer, responsedCompany);
            }

            // 将其他已响应的服务商改为不符合状态(操作服务商响应记录表)
            List<Integer> oldNeedsOrderStatusList = new ArrayList<Integer>();
            oldNeedsOrderStatusList.add(NeedsOrderStatus.RESPONED.getValue());
            Integer newNeedsOrderStatus = NeedsOrderStatus.NOT_QUALIFIED.getValue();
            this.operationNeedsOrder(needsVO, oldNeedsOrderStatusList, newNeedsOrderStatus);
        }
    }

    /**
     * 确认合作
     */
    @Override
    public void confirmCooperation(NeedsVO needsVO, ResponsedCompany responsedCompany, UserInfo userInfo,
                                   CompanyInfo companyInfo) {
        if (null == needsVO || null == userInfo || null == companyInfo || null == responsedCompany) {
            return;
        }
        Date date = new Date();

        // 更改需求状态并下架(操作客户需求表)
        NeedsInfo needsInfo = new NeedsInfo();
        needsInfo.setOrderId(needsVO.getOrderId());
        needsInfo.setStatus(NeedsStatus.COOPETATED.getValue());
        needsInfo.setModifiedTime(date);
        needsInfo.setModifiedUser("系统");
        needsDao.update(needsInfo);

        // 修改服务商响应记录状态(操作服务商响应订单表)
        // 将所选择的响应记录设为合作状态
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("newStatus", NeedsOrderStatus.COOPERATED.getValue());
        map.put("needsId", needsVO.getId());
        map.put("responsedOrderId", responsedCompany.getOrderId());
        map.put("cooperationTime", date);
        needsOrderDao.operateNeedsOrder(map);
        // 将其他所有待报价和已报价的响应记录设为不合作状态
        List<Integer> oldNeedsOrderStatusList = new ArrayList<Integer>();
        oldNeedsOrderStatusList.add(NeedsOrderStatus.WAITING_QUOTE.getValue());
        oldNeedsOrderStatusList.add(NeedsOrderStatus.QUOTED.getValue());
        Integer newNeedsOrderStatus = NeedsOrderStatus.NOT_COOPERATE.getValue();
        this.operationNeedsOrder(needsVO, oldNeedsOrderStatusList, newNeedsOrderStatus);

        // 更改工程项目状态(操作工程项目表)
        // 将所选择项目状态改为进行中
        Integer newStatus = EngineeringProjectStatus.ONGOING.getValue();
        this.operateProject(needsVO, responsedCompany, newStatus);
        // 将其他项目状态改为已取消
        newStatus = EngineeringProjectStatus.CANCLED.getValue();
        this.operateProject(needsVO, null, newStatus);

        // 修改服务商客户状态(操作服务商客户表)
        // 客户绑定到所选择合作的服务商，将邀请状态的客户改为绑定状态
        List<ResponsedCompany> responsedCompanies = new ArrayList<ResponsedCompany>();
        responsedCompanies.add(responsedCompany);
        Integer oldStatus = CompanyCustomerStatus.INVITATION.getValue();
        newStatus = CompanyCustomerStatus.AGREE.getValue();
        Date invitationRejectTime = null;
        Date invitationAgreeTime = date;
        this.operateCompanyCustomer(companyInfo, responsedCompanies, oldStatus, newStatus, invitationRejectTime, invitationAgreeTime);
        // 客户拒绝绑定未选择的服务商，将邀请状态的客户改为拒绝状态
        newStatus = CompanyCustomerStatus.REJECT.getValue();
        invitationRejectTime = date;
        invitationAgreeTime = null;
        this.operateCompanyCustomer(companyInfo, null, oldStatus, newStatus, invitationRejectTime, invitationAgreeTime);
    }

    /**
     * 更改服务商客户状态
     *
     * @param companyInfo          客户公司信息
     * @param responsedCompanies   响应信息
     * @param oldStatus            服务商客户原状态
     * @param newStatus            服务商客户新状态
     * @param invitationRejectTime 客户拒绝绑定时间
     * @param invitationAgreeTime  客户同意绑定时间
     */
    public void operateCompanyCustomer(CompanyInfo companyInfo, List<ResponsedCompany> responsedCompanies, Integer oldStatus, Integer newStatus, Date invitationRejectTime, Date invitationAgreeTime) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("status", oldStatus);
        queryMap.put("memberId", companyInfo.getMemberId());
        List<Long> companyIdList = null;
        if (CollectionUtils.isNotEmpty(responsedCompanies)) {
            companyIdList = new ArrayList<>();
            for (ResponsedCompany responsedCompany : responsedCompanies) {
                companyIdList.add(responsedCompany.getCompanyId());
            }
            queryMap.put("companyIdList", companyIdList);
        }
        CompanyCustomer companyCustomer = companyCustomerDao.getCompanyCustomer(queryMap);
        if (null != companyCustomer) {
            HashMap<String, Object> changeMap = new HashMap<String, Object>();
            changeMap.put("memberId", companyInfo.getMemberId());
            changeMap.put("companyIdList", companyIdList);
            changeMap.put("newStatus", newStatus);
            changeMap.put("oldStatus", oldStatus);
            if (null != invitationRejectTime) {
                changeMap.put("invitationRejectTime", invitationRejectTime);
            }
            if (null != invitationAgreeTime) {
                changeMap.put("invitationAgreeTime", invitationAgreeTime);
            }
            companyCustomerDao.updateCompanyCustomer(changeMap);
        }
    }

    /**
     * 更改项目状态
     *
     * @param needsVO          需求信息
     * @param responsedCompany 响应信息
     * @param newStatus        项目新状态
     */
    public void operateProject(NeedsVO needsVO, ResponsedCompany responsedCompany, Integer newStatus) {
        HashMap<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("needsId", needsVO.getId());
        if (null != responsedCompany) {
            updateMap.put("companyId", responsedCompany.getCompanyId());
        }
        updateMap.put("status", newStatus);
        engineeringProjectDao.updateProject(updateMap);
    }

    /**
     * 服务商邀请客户
     *
     * @param userInfo         客户信息
     * @param companyInfo      客户公司信息
     * @param responsedCompany 响应信息
     * @return 服务商客户
     */
    public CompanyCustomer insertOrUpdateCompanyCustomer(UserInfo userInfo, CompanyInfo companyInfo, ResponsedCompany responsedCompany) {
        Date date = new Date();
        // 先查询是否存在记录
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("memberId", companyInfo.getMemberId());
        List<Long> companyIdList = null;
        if (null != responsedCompany) {
            companyIdList = new ArrayList<>();
            companyIdList.add(responsedCompany.getCompanyId());
            queryMap.put("companyIdList", companyIdList);
        }
        CompanyCustomer companyCustomer = companyCustomerDao.getCompanyCustomer(queryMap);

        // 如果之前没有记录则新增一条记录
        if (null == companyCustomer) {
            companyCustomer = new CompanyCustomer();
            companyCustomer.setMemberId(companyInfo.getMemberId());
            companyCustomer.setMobile(userInfo.getMobile());
            companyCustomer.setCpName(companyInfo.getName());
            companyCustomer.setCpContact(companyInfo.getContacts());
            companyCustomer.setCpContactMobile(companyInfo.getMobile());
            companyCustomer.setCpPhone(companyInfo.getPhone());
            companyCustomer.setCpAddress(companyInfo.getAddressInfo().getAddress());
            if (null != responsedCompany) {
                companyCustomer.setCompanyId(responsedCompany.getCompanyId());
            }
            companyCustomer.setStatus(CompanyCustomerStatus.INVITATION.getValue());
            companyCustomer.setInvitationTime(date);
            companyCustomer.setCreateTime(date);
            companyCustomer.setCreateUser(userInfo.getLoginName());
            companyCustomer.setDeleted(0);
            companyCustomerDao.insert(companyCustomer);
        } else { // 如果之前有记录，则做修改操作，将之前拒绝绑定的客户改为邀请状态
            Integer status = companyCustomer.getStatus();
            if (null != status) {
                if (status.equals(CompanyCustomerStatus.REJECT.getValue())) {
                    HashMap<String, Object> changeMap = new HashMap<String, Object>();
                    changeMap.put("companyIdList", companyIdList);
                    changeMap.put("memberId", companyInfo.getMemberId());
                    changeMap.put("newStatus", CompanyCustomerStatus.INVITATION.getValue());
                    changeMap.put("oldStatus", CompanyCustomerStatus.REJECT.getValue());
                    changeMap.put("invitationTime", date);
                    companyCustomerDao.updateCompanyCustomer(changeMap);
                }
            }
        }
        return companyCustomer;
    }

    /**
     * 需求转项目
     *
     * @param needsVO          需求信息
     * @param userInfo         客户信息
     * @param companyCustomer  服务商客户
     * @param responsedCompany 响应信息
     */
    public void needsToProject(NeedsVO needsVO, UserInfo userInfo, CompanyCustomer companyCustomer, ResponsedCompany responsedCompany) {
        Date date = new Date();

        EngineerProjectInfo engineerProjectInfo = new EngineerProjectInfo();
        String projectNo = BizUtils.getOrderId(OrderIdPrefix.PROJECTNO_PREFIX);
        engineerProjectInfo.setProjectNo(projectNo);
        if (null != companyCustomer) {
            engineerProjectInfo.setCustomerId(companyCustomer.getId());        // 服务商客户表的主键
        }
        if (null != responsedCompany) {
            engineerProjectInfo.setCompanyId(responsedCompany.getCompanyId()); // 服务商的companyId
        }
        if (null != needsVO) {
            engineerProjectInfo.setName(needsVO.getName());
            engineerProjectInfo.setDescription(needsVO.getDescribe());
            Date workingStartTime = DateUtils.parse(needsVO.getWorkingStartTime(), DateUtils.DATE_PATTERN);
            engineerProjectInfo.setStartTime(workingStartTime);
            Date workingEndTime = DateUtils.parse(needsVO.getWorkingEndTime(), DateUtils.DATE_PATTERN);
            workingEndTime = DateUtils.getEndDate(workingEndTime);
            engineerProjectInfo.setEndTime(workingEndTime);
            engineerProjectInfo.setNeedsId(needsVO.getId());
            // distributionRooms: [{"id":1011,"name":"配电房1"},{"id":1222,"name":"配电房2"}]
            String distributionRooms = needsVO.getDistributionRooms();
            JSONArray jsonArray = JSONArray.parseArray(distributionRooms);
            StringBuffer roomIds = new StringBuffer("");
            for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Integer id = (Integer) jsonObject.get("id");
				roomIds.append(id).append(",");
			}
            engineerProjectInfo.setRoomIds(roomIds.toString());
        }
        engineerProjectInfo.setSceneInvestigation(0);
        engineerProjectInfo.setContractFileId(null);
        engineerProjectInfo.setStatus(EngineeringProjectStatus.CONFIRMING.getValue());
        engineerProjectInfo.setCreateTime(date);
        if (null != userInfo) {
            engineerProjectInfo.setCreateUser(userInfo.getLoginName());
        }
        engineerProjectInfo.setDeleted(0);
        engineeringProjectDao.createProject(engineerProjectInfo);
    }

    /**
     * 更改服务商响应订单表状态
     *
     * @param needsVO                 需求信息
     * @param oldNeedsOrderStatusList 响应订单原状态
     * @param newNeedsOrderStatus     响应订单新状态
     * @return 响应订单列表
     */
    public List<ResponsedCompany> operationNeedsOrder(NeedsVO needsVO, List<Integer> oldNeedsOrderStatusList, Integer newNeedsOrderStatus) {
        HashMap<String, Object> queryNeedsOrederMap = new HashMap<String, Object>();
        queryNeedsOrederMap.put("needsId", needsVO.getId());
        queryNeedsOrederMap.put("queryStatusList", oldNeedsOrderStatusList);
        List<ResponsedCompany> responsedCompanies = needsOrderDao.queryList(queryNeedsOrederMap);
        if (CollectionUtils.isNotEmpty(responsedCompanies)) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("needsId", needsVO.getId());
            map.put("oldStatusList", oldNeedsOrderStatusList);
            map.put("newStatus", newNeedsOrderStatus);
            needsOrderDao.operateNeedsOrder(map);
        }
        return responsedCompanies;
    }

    /**
     * 获取需求列表
     */
    @Override
    public PageResult<NeedsVO> queryList(ListReqData listReqData, Long companyId) {
        PageResult<NeedsVO> result = new PageResult<NeedsVO>();

        List<Integer> queryStatusList = null;
        String status = listReqData.getStatus();
        if (NeedsQueryStatus.isExist(status)) {
            queryStatusList = NeedsQueryStatus.getByValue(status).getStatus();
        }

        HashMap<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("queryStatus", queryStatusList);
        queryMap.put("companyId", companyId);
        if (companyId == 0L) {  // 平台用户
            queryMap.put("companyId", -1L);
        } else {
            queryMap.put("companyId", companyId);
        }
        queryMap.put("pageSize", listReqData.getPageSize());
        queryMap.put("offset", listReqData.getOffset());
        queryMap.put("name", listReqData.getName());
        queryMap.put("orderId", listReqData.getOrderId());
        queryMap.put("publishTime", listReqData.getPublishTime());

        Integer total = needsDao.queryNeedsListVOCount(queryMap);
        int nextOffset = 0;

        if (total > 0) {
            List<NeedsVO> entityList = needsDao.queryList(queryMap);

            result.setData(entityList);
            nextOffset = listReqData.getOffset() + entityList.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(listReqData.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    /**
     * 根据需求ID和需求状态 服务商响应列表(已成功缴纳保证金的)
     */
    @Override
    public List<ResponsedCompany> queryRespondList(Long needsId, Integer status) {
        List<ResponsedCompany> responsedCompanys = new ArrayList<ResponsedCompany>();
        if (null == status) {
            return null;
        }
        List<Integer> queryStatusList = new ArrayList<Integer>();

        // 需求响应中
        if (status.equals(NeedsStatus.RESPONDING.getValue())) {
            queryStatusList.add(NeedsOrderStatus.RESPONED.getValue());
        } else if (status.equals(NeedsStatus.QUOTING.getValue()) ||
                status.equals(NeedsStatus.QUOTED.getValue())) {       // 需求报价中、已报价
            queryStatusList.add(NeedsOrderStatus.QUOTED.getValue());
            queryStatusList.add(NeedsOrderStatus.WAITING_QUOTE.getValue());
        } else if (status.equals(NeedsStatus.COOPETATED.getValue())) {   // 需求已合作
            queryStatusList.add(NeedsOrderStatus.COOPERATED.getValue());
            queryStatusList.add(NeedsOrderStatus.NOT_COOPERATE.getValue());
        } else if (status.equals(NeedsStatus.CANCELED.getValue())) {      // 需求已取消
            queryStatusList.add(NeedsOrderStatus.NOT_COOPERATE.getValue());
        } else { // 需求审核中、审核未通过、已超时
            return null;
        }

        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("needsId", needsId);
        queryMap.put("payStatus", PayStatus.SUCCESS.getValue());
        if (CollectionUtils.isNotEmpty(queryStatusList)) {
            queryMap.put("queryStatusList", queryStatusList);
        }

        responsedCompanys = needsOrderDao.queryList(queryMap);
        List<ResponsedCompany> responsedCompanyList = new ArrayList<ResponsedCompany>();
        if (CollectionUtils.isNotEmpty(responsedCompanys)) {
            for (ResponsedCompany responsedCompany : responsedCompanys) {
                Integer payStatus = responsedCompany.getPayStatus();
                if (payStatus.equals(PayStatus.SUCCESS.getValue())) {
                    String quotedPrice = responsedCompany.getQuotedPrice();
                    quotedPrice = MoneyUtils.format(MoneyUtils.formatToMoney(quotedPrice));
                    responsedCompany.setQuotedPrice(quotedPrice);
                    // 查询报价附件
                    List<Attachment> quotedAttachment =
                            commonAttachmentService.structureAttachmentList(responsedCompany.getId(), BusinessType.QUOTE_ATTACHMENT.getValue());
                    responsedCompany.setQuotedAttachment(quotedAttachment);

                    Long companyId = responsedCompany.getCompanyId();
                    Company company = companyDao.getCompanyById(companyId);
                    responsedCompany.setCompanyName(company.getName());

                    //如果需求已合作或者已取消,将所有不合作的订单状态修改为两种状态（未报价、已报价）供前端显示
                    if (status.equals(NeedsStatus.COOPETATED.getValue()) || status.equals(NeedsStatus.CANCELED.getValue())) {
                        if ((responsedCompany.getStatus()).equals(NeedsOrderStatus.NOT_COOPERATE.getValue())) {
                            if (quotedPrice.equals("0.00")) {
                                responsedCompany.setStatus(NeedsOrderStatus.WAITING_QUOTE.getValue());
                            } else {
                                responsedCompany.setStatus(NeedsOrderStatus.QUOTED.getValue());
                            }
                        }
                    }
                    responsedCompanyList.add(responsedCompany);
                }
            }
        }
        return responsedCompanyList;
    }

    /**
     * 上传、编辑合作附件
     */
    @Override
    public void upload(UserInfo userInfo, NeedsVO needsVO, UploadFileReqData uploadFileReqData) {
        // 获取要删除的附件ID
        String removedImgs = uploadFileReqData.getRemovedImgs();
        if (!StringUtils.isBlank(removedImgs)) {
            commonAttachmentService.deleteAttachmentHelper(removedImgs, userInfo);
        }

        // 封装附件
        if (CollectionUtils.isNotEmpty(uploadFileReqData.getAttachmentList())) {
            // 获取附件业务外键 合作附件与项目关联
            List<Integer> statusList = new ArrayList<>();
            List<Integer> status1 = ProjectQueryStatus.PROGRESSING.getStatus();
            List<Integer> status2 = ProjectQueryStatus.FINISHED.getStatus();
            statusList.addAll(status1);
            statusList.addAll(status2);
            HashMap<String, Object> map = new HashMap<>();
            map.put("statusList", statusList);
            map.put("needsId", needsVO.getId());
            EngineeringProjectVO engineerProjectInfo = engineeringProjectDao.queryProjectInfo(map);
            Long projectId = engineerProjectInfo.getId();
            // 保存附件信息
            commonAttachmentService.saveAttachmentHelper(projectId, userInfo, uploadFileReqData.getAttachmentList(),
                    BusinessType.COOPERATION_ATTACHMENT.getValue());
        }
    }

    /**
     * 根据服务商响应编号获取服务商资质
     */
    @Override
    public NeedsOrderInfo queryRespondDetails(String orderId) {
        NeedsOrderInfo needsOrderInfo = needsOrderDao.queryRespondDetails(orderId);
        String quotedPrice = needsOrderInfo.getQuotedPrice();
        quotedPrice = MoneyUtils.format(MoneyUtils.formatToMoney(quotedPrice));
        needsOrderInfo.setQuotedPrice(quotedPrice);

        // 获取业务外键
        Long respondId = needsOrderInfo.getId();
        // 查询报价附件
        needsOrderInfo.setQuotedAttachmentList(
                commonAttachmentService.structureAttachmentList(respondId, BusinessType.QUOTE_ATTACHMENT.getValue()));
        return needsOrderInfo;
    }

    /**
     * 需求市场列表
     *
     * @param listQuery
     * @return
     */
    @Override
    public PageResult<NeedsMarketVO> selectPageNeedsMarketVO(NeedsMarketListQuery listQuery) {
        PageResult<NeedsMarketVO> result = new PageResult<NeedsMarketVO>();
        int total = needsDao.queryNeedsMarketCount(listQuery);
        int nextOffset = 0;
        if (total > 0) {
            List<NeedsMarketVO> needsMarketList = needsDao.queryNeedsMarketList(listQuery);
            if (CollectionUtils.isNotEmpty(needsMarketList)) {
                shieldingKeyWords(needsMarketList);
            }
            result.setData(needsMarketList);
            nextOffset = listQuery.getOffset() + needsMarketList.size();
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

    /**
     * 需求市场列表(portal服务调用)
     *
     * @param listQuery
     * @return
     */
    @Override
    public List<NeedsMarketVO> selectNeedsMarketVOPortal(NeedsMarketListQuery listQuery) {
        List<NeedsMarketVO> needsMarketList = needsDao.queryNeedsMarketListNotRequiredCompanyId(listQuery);
        if (CollectionUtils.isNotEmpty(needsMarketList)) {
            structureNeedsMarketPortalData(needsMarketList, listQuery);
        }
        return needsMarketList;
    }

    /**
     * 构建portal服务需求市场数据
     *
     * @param needsMarketList
     * @param listQuery
     */
    private void structureNeedsMarketPortalData(List<NeedsMarketVO> needsMarketList, NeedsMarketListQuery listQuery) {
        if (CollectionUtils.isEmpty(needsMarketList)) {
            return;
        }

        Long companyId = listQuery.getCompanyId();
        // 获取需求订单信息
        List<CustomerNeedsOrder> needsOrderList = new ArrayList<>();
        List<Long> needsIds = new ArrayList<>();
        for (NeedsMarketVO needsMarketVO : needsMarketList) {
            needsIds.add(needsMarketVO.getId());
        }
        if (CollectionUtils.isNotEmpty(needsIds)) {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("needsIds", needsIds);
            queryMap.put("unequalStatus", NeedsOrderStatus.CANCEL.getValue());
            needsOrderList = needsOrderDao.queryEntityList(queryMap);
        }

        // 根据companyId判断需求有无支付
        Map<Long, Integer> needsToPay = new HashMap<>();
        // 根据companyId判断需求有无响应
        Map<Long, Integer> needsToResponse = new HashMap<>();
        // 需求响应人数
        Map<Long, Integer> needsToResponseNumber = new HashMap<>();
        if (CollectionUtils.isNotEmpty(needsOrderList)) {
            for (CustomerNeedsOrder customerNeedsOrder : needsOrderList) {
                Long needsId = customerNeedsOrder.getNeedsId();

                // 根据companyId判断需求有无响应
                if (customerNeedsOrder.getCompanyId().equals(companyId)) {
                    needsToResponse.put(needsId, 1);
                    needsToPay.put(needsId,
                            customerNeedsOrder.getPayType() == null ? 0 : customerNeedsOrder.getPayType());
                }

                // 需求响应人数，这里未区分响应订单状态
                if (needsToResponseNumber.containsKey(needsId)) {
                    Integer responseNumber = needsToResponseNumber.get(needsId);
                    needsToResponseNumber.put(needsId, responseNumber + 1);
                } else {
                    needsToResponseNumber.put(needsId, 1);
                }
            }
        }

        for (NeedsMarketVO needsMarketVO : needsMarketList) {
            Long needsId = needsMarketVO.getId();

            needsMarketVO.setRespondStatus(Constants.TAG_NO);
            if (needsToResponse.containsKey(needsId)) {
                needsMarketVO.setRespondStatus(needsToResponse.get(needsId));
            }

            needsMarketVO.setResponseNumber(Constants.TAG_NO);
            if (needsToResponseNumber.containsKey(needsId)) {
                needsMarketVO.setResponseNumber(needsToResponseNumber.get(needsId));
            }

            needsMarketVO.setPayStatus(Constants.TAG_NO);
            if (needsToPay.containsKey(needsId)) {
                needsMarketVO.setPayStatus(needsToPay.get(needsId));
            }
        }
    }

    /**
     * 替换关键词
     *
     * @param needsMarketList
     */
    private void shieldingKeyWords(List<NeedsMarketVO> needsMarketList) {
        if (CollectionUtils.isNotEmpty(needsMarketList)) {
            for (NeedsMarketVO needsMarketVO : needsMarketList) {
                String keyword = needsMarketVO.getKeyword();
                // 未支付
                if (StringUtils.isNotBlank(keyword) && (!needsMarketVO.getPayStatus().equals(PayStatus.SUCCESS.getValue()))) {
                    String[] arr = keyword.trim().split(",");
                    List<String> words = new ArrayList<String>();
                    Collections.addAll(words, arr);
                    try {
                        ShieldingKeyWords shieldingKeyWords = new ShieldingKeyWords();
                        shieldingKeyWords.createKeywordTree(words);

                        String needsName = shieldingKeyWords.searchKeyword(needsMarketVO.getName());
                        needsMarketVO.setName(needsName);
                        String needsDescribe = shieldingKeyWords.searchKeyword(needsMarketVO.getDescribe());
                        needsMarketVO.setDescribe(needsDescribe);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 后台需求列表
     */
    @Override
    public PageResult<NeedsViewVO> getNeedsViweList(NeedsViewListReqData needsViewListReqData) {
        PageResult<NeedsViewVO> result = new PageResult<>();
        String status = needsViewListReqData.getStatus();
        List<Integer> queryStatusList = null;
        if (NeedsQueryStatus.isExist(status)) {
            queryStatusList = NeedsQueryStatus.getByValue(status).getStatus();
        }

        HashMap<String, Object> queryNeedsViewMap = new HashMap<String, Object>();
        String memberName = needsViewListReqData.getMemberName();
        String loginId = needsViewListReqData.getLoginId();
        HashMap<String, Object> queryCompanyMap = new HashMap<>();

        if (null != memberName || null != loginId) {
            queryCompanyMap.put("name", memberName);
            queryCompanyMap.put("mobile", loginId);
            List<Company> companies = companyDao.getCompanyList(queryCompanyMap);
            List<Long> companyIds = new ArrayList<>();
            Long companyId = null;
            if (CollectionUtils.isNotEmpty(companies)) {
                for (Company company : companies) {
                    companyId = company.getId();
                    companyIds.add(companyId);
                }
            } else {
                companyId = -1L;
                companyIds.add(companyId);
            }
            queryNeedsViewMap.put("companyIds", companyIds);
        }

        String needsName = needsViewListReqData.getNeedsName();
        if (null != needsName && !needsName.trim().equals("")) {
            queryNeedsViewMap.put("needsName", needsName);
        }
        queryNeedsViewMap.put("queryStatusList", queryStatusList);
        queryNeedsViewMap.put("pageSize", needsViewListReqData.getPageSize());
        queryNeedsViewMap.put("offset", needsViewListReqData.getOffset());

        int total = needsDao.getNeedsViewListCount(queryNeedsViewMap);
        int nextOffset = 0;

        if (total > 0) {
            List<NeedsViewVO> needsViewVOList = needsDao.queryNeedsViewList(queryNeedsViewMap);

            for (NeedsViewVO needsViewVO : needsViewVOList) {
                Integer needsStatus = needsViewVO.getNeedsStatus();

                List<Integer> notAuditList = NeedsQueryStatus.getByValue("not_audit").getStatus();
                List<Integer> auditSucceedList = NeedsQueryStatus.getByValue("audit_succeed").getStatus();
                List<Integer> auditFailedList = NeedsQueryStatus.getByValue("audit_failed").getStatus();

                if (notAuditList.contains(needsStatus)) {
                    needsViewVO.setNeedsShowStatus("未审核");
                    needsViewVO.setNeedsStatus(null);
                }
                if (auditSucceedList.contains(needsStatus)) {
                    needsViewVO.setNeedsShowStatus("审核通过");
                    needsViewVO.setNeedsStatus(null);
                }
                if (auditFailedList.contains(needsStatus)) {
                    needsViewVO.setNeedsShowStatus("审核失败");
                    needsViewVO.setNeedsStatus(null);
                }
            }

            result.setData(needsViewVOList);
            nextOffset = needsViewListReqData.getOffset() + needsViewVOList.size();
            result.setNextOffset(nextOffset);
        }
        if (nextOffset > 0 && nextOffset < total) {
            result.setHasNext(true);
        }

        result.setOffset(needsViewListReqData.getOffset());
        result.setNextOffset(nextOffset);
        result.setTotal(total);
        return result;
    }

    /**
     * 需求审核
     */
    @Override
    public void auditNeeds(AuditReqData auditReqData, UserInfo userInfo) {
        Date date = new Date();
        NeedsInfo needsInfo = new NeedsInfo();
        needsInfo.setOrderId(auditReqData.getOrderId());
        needsInfo.setStatus(auditReqData.getStatus());
        needsInfo.setFailReason(auditReqData.getFailReason());
        String maskString = auditReqData.getMaskString();
        if (null != maskString) {
            maskString = maskString.replace("，", ",");
        }
        needsInfo.setKeyword(maskString);
        if (NeedsStatus.RESPONDING.getValue().equals(auditReqData.getStatus())) {
            needsInfo.setOnShelves(Constants.TAG_YES);
        }
        needsInfo.setAuditTime(date);
        needsInfo.setModifiedTime(date);
        needsInfo.setModifiedUser("系统");

        // 更改需求状态
        needsDao.update(needsInfo);

        // 屏蔽关键图片，将isOpen改为1
        String removedImgs = auditReqData.getRemovedImgs();
        if (!StringUtils.isBlank(removedImgs)) {
            List<Attachment> attachmentList = new ArrayList<Attachment>();
            String[] keyImg = removedImgs.split(",");
            for (String id : keyImg) {
                Attachment attachment = new Attachment();
                attachment.setId(Long.valueOf(id));
                attachment.setIsOpen(1);
                attachmentList.add(attachment);
            }
            commonAttachmentService.updateAttachmentHelper(attachmentList, userInfo);
        }
    }

    @Override
    public Needs getCustomerNeedsById(Long id, String orderId) {
        if (id == null && StringUtils.isBlank(orderId)) {
            return null;
        }
        return needsDao.getCustomerNeedsById(id, orderId);
    }

    @Override
    public InitDataResult getSurveyInitData(UserInfo userInfo, InitDataReqData reqData) {
        InitDataResult result = new InitDataResult();

        // 需求信息
        Needs needs = needsDao.getCustomerNeedsById(null, reqData.getOrderId());
        if (needs == null) {
            result.set(ResultCode.ERROR_401, "需求信息不存在");
            return result;
        }

        // 客户公司信息
        Company company = companyDao.getCompanyById(needs.getCompanyId());
        if (company == null) {
            result.set(ResultCode.ERROR_401, "客户公司信息不存在");
            return result;
        }

        // 服务商客户信息
        HashMap<String, Object> companyCustomerQueryMap = new HashMap<>();
        companyCustomerQueryMap.put("memberId", company.getMemberId());
        List<Long> companyIdList = new ArrayList<>();
        companyIdList.add(userInfo.getCompanyId());
        companyCustomerQueryMap.put("companyIdList", companyIdList);
        CompanyCustomer companyCustomer = companyCustomerDao.getCompanyCustomer(companyCustomerQueryMap);

        if (companyCustomer == null) {
            result.set(ResultCode.ERROR_401, "客户信息不存在");
            return result;
        }

        // 服务商项目信息
        HashMap<String, Object> engineeringProjectQueryMap = new HashMap<>();
        engineeringProjectQueryMap.put("needsId", needs.getId());
        EngineeringProjectVO engineeringProjectVO = engineeringProjectDao.queryProjectInfo(engineeringProjectQueryMap);
        if (engineeringProjectVO == null) {
            result.set(ResultCode.ERROR_401, "客户项目信息不存在");
            return result;
        }

        CompanyVO customer = new CompanyVO();
        customer.setId(companyCustomer.getId());
        customer.setName(companyCustomer.getCpName());
        customer.setAddress(companyCustomer.getCpAddress());
        customer.setContacts(companyCustomer.getCpContact());
        customer.setContactNumber(companyCustomer.getContactTel());
        result.setCustomer(customer);

        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(engineeringProjectVO.getId());
        projectVO.setName(engineeringProjectVO.getName());
        result.setProject(projectVO);
        return result;
    }

    /**
     * 查询超时需求
     */
    @Override
    public List<Long> getOvertimeNeeds(Integer limit) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Integer auditing = NeedsStatus.AUDITING.getValue();
        Integer responding = NeedsStatus.RESPONDING.getValue();
        map.put("auditing", auditing);
        map.put("responding", responding);
        map.put("limit", limit);
        return needsDao.getOvertimeNeeds(map);
    }

    /**
     * 处理超时需求
     */
    @Override
    public Result handleOvertimeNeeds(Long id) {
        Result result = new DefaultResult();
        try {
            NeedsVO needsVO = needsDao.getNeedsByOrderId(null, id);
            if (null == needsVO) {
                result.set(ResultCode.ERROR_401, "需求不存在");
                return result;
            }

            NeedsInfo needsInfo = new NeedsInfo();
            needsInfo.setId(id);
            needsInfo.setOnShelves(Constants.TAG_NO);

            // 需求审核中
            if (needsVO.getStatus().equals(NeedsStatus.AUDITING.getValue())) {
                needsInfo.setStatus(NeedsStatus.FAIL_AUDIT.getValue());
                needsInfo.setFailReason("未审核，请重新发布！");
            }

            // 需求响应中
            if (needsVO.getStatus().equals(NeedsStatus.RESPONDING.getValue())) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("needsId", id);
                List<ResponsedCompany> responsedCompanies = needsOrderDao.queryList(map);
                if (CollectionUtils.isEmpty(responsedCompanies)) {
                    needsInfo.setStatus(NeedsStatus.OVERTIME.getValue());
                }
            }

            int c = needsDao.update(needsInfo);
            if (c < 1) {
                result.set(ResultCode.ERROR_401, "需求超时更新失败");
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            return result;
        }
        return result;
    }


    @Override
    public List<NeedsMarketVO> buildListNeedsMarketVO
            (List<NeedsMarketVO> listNeedsMarketVO, NeedsMarketListQuery listQuery) {
        if (CollectionUtils.isEmpty(listNeedsMarketVO)) {
            return listNeedsMarketVO;
        }
        structureNeedsMarketPortalData(listNeedsMarketVO, listQuery);
        return listNeedsMarketVO;
    }

    @Override
    public Map<String, Object> getSendMessagePushCustomer(Long id) {
        return needsDao.getSendMessagePushCustomer(id);
    }

    @Override
    public List<NeedsVO> getList(HashMap<String, Object> map) {
        return needsDao.queryList(map);
    }

}
