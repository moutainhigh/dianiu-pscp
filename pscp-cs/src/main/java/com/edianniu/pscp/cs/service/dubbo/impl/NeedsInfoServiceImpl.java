package com.edianniu.pscp.cs.service.dubbo.impl;

import com.edianniu.pscp.cs.bean.DefaultResult;
import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.engineeringproject.QuotedInfo;
import com.edianniu.pscp.cs.bean.needs.*;
import com.edianniu.pscp.cs.bean.needs.keyword.ShieldingKeyWords;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsMarketVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsViewVO;
import com.edianniu.pscp.cs.bean.needsorder.InitDataReqData;
import com.edianniu.pscp.cs.bean.needsorder.InitDataResult;
import com.edianniu.pscp.cs.bean.query.NeedsMarketListQuery;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.CacheKey;
import com.edianniu.pscp.cs.commons.Constants;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.domain.Company;
import com.edianniu.pscp.cs.domain.Needs;
import com.edianniu.pscp.cs.service.CommonAttachmentService;
import com.edianniu.pscp.cs.service.CompanyService;
import com.edianniu.pscp.cs.service.CustomerNeedsOrderService;
import com.edianniu.pscp.cs.service.NeedsService;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.cs.service.dubbo.RoomInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.cs.util.MoneyUtils;
import com.edianniu.pscp.message.commons.MessageId;
import com.edianniu.pscp.message.service.dubbo.MessageInfoService;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.NeedsOrderStatus;
import com.edianniu.pscp.mis.bean.attachment.common.BusinessType;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.bean.user.QuerySysUserReq;
import com.edianniu.pscp.mis.bean.user.SysUserInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.SysUserInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.search.dubbo.NeedsDubboService;
import com.edianniu.pscp.search.support.needs.*;
import stc.skymobi.cache.redis.JedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Repository("needsInfoService")
public class NeedsInfoServiceImpl implements NeedsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(NeedsInfoServiceImpl.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    @Qualifier("needsService")
    private NeedsService needsService;
    @Autowired
    @Qualifier("commonAttachmentService")
    private CommonAttachmentService commonAttachmentService;
    @Autowired
    @Qualifier("customerNeedsOrderService")
    private CustomerNeedsOrderService customerNeedsOrderService;
    @Autowired
    @Qualifier("needsDubboService")
    private NeedsDubboService needsDubboService;
    @Autowired
    @Qualifier("roomInfoService")
    private RoomInfoService roomInfoService;
    @Autowired
    @Qualifier("messageInfoService")
    private MessageInfoService messageInfoService;
    @Autowired
    @Qualifier("companyService")
    private CompanyService companyService;
    @Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;
    @Autowired
    @Qualifier("sysUserInfoService")
    private SysUserInfoService sysUserInfoService;

    /**
     * 保存需求及附件信息
     */
    @Override
    public SaveResult saveNeeds(SaveReqData saveReqData) {
        SaveResult result = new SaveResult();
        try {
            Long uid = saveReqData.getUid();
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_201, "用户信息不存在");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo.getLoginName() || 0L == userInfo.getCompanyId()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "请进行实名认证");
                return result;
            }
            if (!userInfo.isCustomer()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "认证为客户后，才能发布需求");
                return result;
            }
            String needsName = saveReqData.getName();
            if (!BizUtils.checkLength(needsName, 50)) {
                result.set(ResultCode.ERROR_401, "需求名不能为空或超过50字");
                return result;
            }
            String describe = saveReqData.getDescribe();
            if (!BizUtils.checkLength(describe, 1000)) {
                result.set(ResultCode.ERROR_401, "需求描述不能为空或超过1000字");
                return result;
            }
            Date publishCutoffTime = DateUtils.parse(saveReqData.getPublishCutoffTime(), DateUtils.DATE_PATTERN);
            publishCutoffTime = DateUtils.getEndDate(publishCutoffTime);
            Date workingStartTime = DateUtils.parse(saveReqData.getWorkingStartTime(), DateUtils.DATE_PATTERN);
            Date workingEndTime = DateUtils.parse(saveReqData.getWorkingEndTime(), DateUtils.DATE_PATTERN);
            workingEndTime = DateUtils.getEndDate(workingEndTime);
            if (null == publishCutoffTime || null == workingStartTime || null == workingEndTime) {
                result.set(ResultCode.ERROR_402, "时间不合法");
                return result;
            }
            if (publishCutoffTime.after(workingStartTime) || workingStartTime.after(workingEndTime)) {
                result.set(ResultCode.ERROR_402, "时间不合法");
                return result;
            }
            String contactPerson = saveReqData.getContactPerson();
            if (!BizUtils.checkLength(contactPerson, 20)) {
                result.set(ResultCode.ERROR_403, "联系人不能为空或超过20字");
                return result;
            }
            if (StringUtils.isBlank(saveReqData.getContactNumber())) {
                result.set(ResultCode.ERROR_404, "手机号码不能为空");
                return result;
            }
            if (!BizUtils.checkMobile(saveReqData.getContactNumber())) {
                result.set(ResultCode.ERROR_404, "手机号码格式不正确");
                return result;
            }
            String contactAddr = saveReqData.getContactAddr();
            if (!BizUtils.checkLength(contactAddr, 50)) {
                result.set(ResultCode.ERROR_405, "联系地址不能为空或超过50字");
                return result;
            }
            String distributionRoomIds = saveReqData.getDistributionRoomIds();
            if (!BizUtils.checkLength(distributionRoomIds, 200)) {
                result.set(ResultCode.ERROR_406, "配电房不能为空或超过200字");
                return result;
            }
            String[] roomIds = distributionRoomIds.trim().split(",");
            if (ArrayUtils.isEmpty(roomIds)) {
                result.set(ResultCode.ERROR_406, "配电房不能为空");
                return result;
            } else {
                for (String string : roomIds) {
                    long roomId = Long.parseLong(string);
                    RoomVO roomVO = roomInfoService.getRoomById(roomId);
                    if (null == roomVO) {
                        result.set(ResultCode.ERROR_406, roomId + "配电房不存在");
                        return result;
                    }
                }
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_USER_UID + uid, String.valueOf(uid), 200L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_407, "该需求已发布，请勿重复操作");
                return result;
            }
            // 保存需求及附件信息
            needsService.saveNeeds(saveReqData, userInfo);
            
            // 给运维发送短信 
            sendSmsPM(needsName);
            
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("saveNeeds:{}", e);
        }
        return result;
    }

    /**
     * 重新发布需求
     */
    @Override
    public RepublishResult republishNeeds(RepublishReqData republishReqData) {
        RepublishResult result = new RepublishResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(republishReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_201, "用户信息不存在");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo.getLoginName() || 0L == userInfo.getCompanyId()) {
                result.set(ResultCode.ERROR_201, "请进行认证");
                return result;
            }
            if (!userInfo.isCustomer()) {
                result.set(ResultCode.ERROR_201, "认证为客户后，才能发布需求");
                return result;
            }
            String needsName = republishReqData.getName();
            if (!BizUtils.checkLength(needsName, 50)) {
                result.set(ResultCode.ERROR_401, "需求名不能为空或超过50字");
                return result;
            }
            String describe = republishReqData.getDescribe();
            if (!BizUtils.checkLength(describe, 1000)) {
                result.set(ResultCode.ERROR_401, "需求描述不能为空或超过1000字");
                return result;
            }
            Date publishCutoffTime = DateUtils.parse(republishReqData.getPublishCutoffTime(), DateUtils.DATE_PATTERN);
            publishCutoffTime = DateUtils.getEndDate(publishCutoffTime);
            Date workingStartTime = DateUtils.parse(republishReqData.getWorkingStartTime(), DateUtils.DATE_PATTERN);
            Date workingEndTime = DateUtils.parse(republishReqData.getWorkingEndTime(), DateUtils.DATE_PATTERN);
            workingEndTime = DateUtils.getEndDate(workingEndTime);
            if (null == publishCutoffTime || null == workingStartTime || null == workingEndTime) {
                result.set(ResultCode.ERROR_402, "时间不合法");
                return result;
            }
            if (publishCutoffTime.after(workingStartTime) || workingStartTime.after(workingEndTime)) {
                result.set(ResultCode.ERROR_402, "时间不合法");
                return result;
            }
            String contactPerson = republishReqData.getContactPerson();
            if (!BizUtils.checkLength(contactPerson, 20)) {
                result.set(ResultCode.ERROR_403, "联系人不能为空或超过20字");
                return result;
            }
            if (StringUtils.isBlank(republishReqData.getContactNumber())) {
                result.set(ResultCode.ERROR_404, "手机号码不能为空");
                return result;
            }
            if (!BizUtils.checkMobile(republishReqData.getContactNumber())) {
                result.set(ResultCode.ERROR_404, "手机号码格式不正确");
                return result;
            }
            String contactAddr = republishReqData.getContactAddr();
            if (!BizUtils.checkLength(contactAddr, 50)) {
                result.set(ResultCode.ERROR_405, "联系地址不能为空或超过50字");
                return result;
            }
            String distributionRoomIds = republishReqData.getDistributionRoomIds();
            if (!BizUtils.checkLength(distributionRoomIds, 200)) {
                result.set(ResultCode.ERROR_406, "配电房不能为空或超过200字");
                return result;
            }
            String[] roomIds = distributionRoomIds.trim().split(",");
            if (ArrayUtils.isEmpty(roomIds)) {
                result.set(ResultCode.ERROR_406, "配电房不能为空");
                return result;
            } else {
                for (String string : roomIds) {
                    long roomId = Long.parseLong(string);
                    RoomVO roomVO = roomInfoService.getRoomById(roomId);
                    if (null == roomVO) {
                        result.set(ResultCode.ERROR_406, roomId + "配电房不存在");
                        return result;
                    }
                }
            }
            String orderId = republishReqData.getOrderId();
            NeedsVO needsVO = needsService.getNeedsById(null, orderId);
            if (needsVO == null) {
                result.set(ResultCode.ERROR_407, "需求不存在");
                return result;
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_NEEDS_ORDERID + orderId, orderId, 500L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_408, "该需求正在重新发布中，请勿重新操作");
                return result;
            }
            // 重新发布需求
            needsService.republishNeeds(republishReqData, userInfo, needsVO);
            
            // 给运维发送短信 
            sendSmsPM(needsName);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("republishNeeds:{}", e);
        }
        return result;
    }

    /**
     * 取消需求
     */
    @Override
    public CancelResult cancelNeeds(String orderId, Long uid) {
        CancelResult result = new CancelResult();
        try {
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
            NeedsVO needsVO = needsService.getNeedsById(null, orderId);
            if (needsVO == null) {
                result.set(ResultCode.ERROR_401, "需求不存在");
                return result;
            }
            Integer status = needsVO.getStatus();
            if (status.equals(NeedsStatus.AUDITING.getValue())) {
                result.set(ResultCode.ERROR_401, "需求审核中，不可取消");
                return result;
            }
            if (status.equals(NeedsStatus.COOPETATED.getValue())) {
                result.set(ResultCode.ERROR_402, "需求是已合作状态，不可取消");
                return result;
            }
            if (status.equals(NeedsStatus.OVERTIME.getValue())) {
                result.set(ResultCode.ERROR_403, "需求已超時");
                return result;
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_NEEDS_ORDERID + orderId, orderId, 500L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_404, "正在处理中，请勿重复操作");
                return result;
            }
            CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
            // 取消需求
            needsService.cancelNeeds(needsVO, userInfo, companyInfo);

            // ES服务删除需求数据
            deleteESNeedsInfo(String.valueOf(needsVO.getId()));
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("cancel :{}", e);
        }
        return result;
    }

    /**
     * 需求询价
     */
    @Override
    public QuoteResult quoteNeeds(String orderId, Long uid, String responsedOrderIds) {
        QuoteResult result = new QuoteResult();
        try {
            if (null == uid) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uid);
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_201, "用户信息不存在");
                return result;
            }
            NeedsVO needsVO = needsService.getNeedsById(null, orderId);
            if (null == needsVO) {
                result.set(ResultCode.ERROR_401, "需求不存在");
                return result;
            }
            if (!needsVO.getStatus().equals(NeedsStatus.RESPONDING.getValue())) {
                result.set(ResultCode.ERROR_402, "需求状态不合法，只有响应中的需求才能询价");
                return result;
            }
            if (StringUtils.isBlank(responsedOrderIds)) {
                result.set(ResultCode.ERROR_403, "请至少选择一个服务商");
                return result;
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_NEEDS_ORDERID + orderId, orderId, 500L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_404, "正在询价中，请勿重复操作");
                return result;
            }
            CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();

            // 更改客户需求表和服务商响应记录表状态
            needsService.quoteNeeds(needsVO, orderId, responsedOrderIds, userInfo, companyInfo);

            // ES服务删除需求数据
            deleteESNeedsInfo(String.valueOf(needsVO.getId()));
            
            // 消息推送
            sendMessagePush(needsVO);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("quote :{}", e);
        }
        return result;
    }

    /**
     * ES服务删除需求数据
     *
     * @param needsId 需求编号
     */
    private void deleteESNeedsInfo(final String needsId) {
        try {
            EXECUTOR_SERVICE.submit(new Runnable() {
                @Override
                public void run() {
                    // 构建ES保存接口请求参数
                    try {
                        NeedsDeleteReqData reqData = new NeedsDeleteReqData();
                        reqData.setNeedsId(needsId);
                        NeedsDeleteResult result = needsDubboService.deleteById(reqData);
                        if (!result.isSuccess()) {
                            logger.error("ES需求{}删除失败:{}", needsId, result.getResultMessage());
                        }
                    } catch (Exception e) {
                        logger.error("ES服务异常", e);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 确认合作
     */
    @Override
    public ConfirmCooperationResult confirmCooperation(ConfirmCooperationReqData confirmCooperationReqData) {
        ConfirmCooperationResult result = new ConfirmCooperationResult();
        try {
            if (confirmCooperationReqData.getUid() == null) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(confirmCooperationReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_201, "用户信息不存在");
                return result;
            }
            String orderId = confirmCooperationReqData.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_401, "需求编号不能为空");
                return result;
            }
            NeedsVO needsVO = needsService.getNeedsById(null, orderId);
            if (needsVO == null) {
                result.set(ResultCode.ERROR_402, "需求不存在");
                return result;
            }
            String responsedOrderId = confirmCooperationReqData.getResponsedOrderId();
            if (StringUtils.isBlank(responsedOrderId)) {
                result.set(ResultCode.ERROR_403, "需求订单编号不能为空");
                return result;
            }
            if (!needsVO.getStatus().equals(NeedsStatus.QUOTED.getValue())) {
                result.set(ResultCode.ERROR_404, "仅已报价状态下的需求可选择合作");
                return result;
            }
            ResponsedCompany responsedCompany = needsService.query(needsVO.getId(), responsedOrderId);
            if (responsedCompany == null || !responsedCompany.getStatus().equals(NeedsOrderStatus.QUOTED.getValue())) {
                result.set(ResultCode.ERROR_405, "该服务商未报价，不能合作");
                return result;
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_NEEDS_ORDERID + orderId, orderId, 500L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_404, "正在处理中，请勿重复操作");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
            // 更改客户需求表和服务商响应记录表状态,需求转状态
            needsService.confirmCooperation(needsVO, responsedCompany, userInfo, companyInfo);

            // 消息推送  
            sendMessagePush(needsVO);
            
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("confirmCooperation :{}", e);
        }
        return result;
    }

    /**
     * 获取需求列表
     * @param listReqData
     * @return
     */
    @Override
    public ListResult queryList(ListReqData listReqData) {
        ListResult result = new ListResult();
        try {
            if (null == listReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(listReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(400, "用户信息不存在");
                return result;
            }
            String status = listReqData.getStatus();
            if (!("verifying".equals(status) || "responding".equals(status) || "quoting".equals(status)
                    || "finished".equals(status))) {
                result.set(ResultCode.ERROR_401, "status不合法");
                return result;
            }
            if (StringUtils.isNotBlank(listReqData.getPublishTime())) {
				if (!BizUtils.checkYmd(listReqData.getPublishTime())) {
					result.set(ResultCode.ERROR_401, "日期格式不合法");
	                return result;
				}
			}
            
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            Long companyId = userInfo.getCompanyId();
            PageResult<NeedsVO> pageResult = needsService.queryList(listReqData, companyId);

            result.setNeedsList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("Needs list:{}", e);
        }
        return result;
    }

    /**
     * 需求详情
     */
    @Override
    public DetailsResult query(DetailsReqData detailReqData) {
        DetailsResult result = new DetailsResult();
        try {
            if (null == detailReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(detailReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(400, "用户信息不存在");
                return result;
            }
            if (null == detailReqData.getNeedsId() && StringUtils.isBlank(detailReqData.getOrderId())) {
                result.set(ResultCode.ERROR_401, "需求标识不能为空");
                return result;
            }
            NeedsVO needs = needsService.getNeedsById(detailReqData.getNeedsId(), detailReqData.getOrderId());
            if (null == needs) {
                result.set(ResultCode.ERROR_402, "需求标识不合法!");
                return result;
            }
            result.setNeeds(needs);
            Long needsId = needs.getId();
            Integer status = needs.getStatus();

            // 查询已响应服务商
            List<ResponsedCompany> responsedCompanys = needsService.queryRespondList(needsId, status);
            if (CollectionUtils.isNotEmpty(responsedCompanys)) {
                result.setResponsedCompanys(responsedCompanys);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("details", e);
        }
        return result;
    }

    /**
     * 合作附件上传、编辑
     */
    @Override
    public UploadFileResult upload(UploadFileReqData uploadFileReqData) {
        UploadFileResult result = new UploadFileResult();
        try {
            Long uid = uploadFileReqData.getUid();
            if (null == uid) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(uploadFileReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_201, "用户信息不存在");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo) {
                result.set(ResultCode.ERROR_201, "用户信息不存在");
                return result;
            }
            String orderId = uploadFileReqData.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_401, "orderId不能为空");
                return result;
            }
            NeedsVO needsVO = needsService.getNeedsById(null, orderId);
            if (null == needsVO) {
                result.set(ResultCode.ERROR_401, "需求编号不合法");
                return result;
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_USER_UID + uid, String.valueOf(uid), 200L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_402, "正在处理中，请勿重复操作");
                return result;
            }

            needsService.upload(userInfo, needsVO, uploadFileReqData);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("uploadFile", e);
        }
        return result;
    }

    /**
     * 获取服务商资质详情
     *
     * @param respondDetailsReqData
     * @return
     */
    @Override
    public RespondDetailsResult respondDetails(RespondDetailsReqData respondDetailsReqData) {
        RespondDetailsResult result = new RespondDetailsResult();
        try {
            if (null == respondDetailsReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(respondDetailsReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_201, "用户信息不存在");
                return result;
            }
            String orderId = respondDetailsReqData.getOrderId();
            // 获取响应订单信息
            NeedsOrderInfo needsOrderInfo = needsService.queryRespondDetails(orderId);
            if (null == needsOrderInfo) {
                result.set(ResultCode.ERROR_401, "响应订单编号不合法");
                return result;
            }
            // 获取服务商信息
            Long respondCompanyId = needsOrderInfo.getCompanyId();
            Company respondCompany = companyService.getCompanyById(respondCompanyId);
            GetUserInfoResult respondCompanyUserInfoResult = userInfoService.getUserInfo(respondCompany.getMemberId());
            if (!respondCompanyUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_201, "服务商信息不存在");
                return result;
            }
            CompanyInfo companyInfo = respondCompanyUserInfoResult.getCompanyInfo();

            result.setCompanyInfo(companyInfo);
            result.setNeedsOrderInfo(needsOrderInfo);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 需求市场列表(dubbo接口，外部调用)
     *
     * @param reqData
     * @return
     */
    @Override
    public NeedsMarketListResult queryListNeedsMarket(NeedsMarketListReqData reqData) {
        return queryListNeedsMarketPortal(reqData);
    }

    /**
     * 需求市场列表(dubbo接口，portal服务调用)
     *
     * @param reqData
     * @return
     */
    @Override
    public NeedsMarketListResult queryListNeedsMarketPortal(NeedsMarketListReqData reqData) {
        NeedsMarketListResult result = new NeedsMarketListResult();
        try {
            NeedsMarketListQuery listQuery = new NeedsMarketListQuery();
            BeanUtils.copyProperties(reqData, listQuery);
            if (reqData.getUid() != null) {
                GetUserInfoResult userInfoResult = userInfoService.getUserInfo(reqData.getUid());
                if (!userInfoResult.isSuccess()) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("用户信息不存在");
                    return result;
                }

                UserInfo userInfo = userInfoResult.getMemberInfo();
                if (userInfo == null) {
                    result.setResultCode(ResultCode.ERROR_401);
                    result.setResultMessage("用户信息不存在");
                    return result;
                }

                if (userInfo.getCompanyId() != null && !userInfo.getCompanyId().equals(0L)) {
                    listQuery.setCompanyId(userInfo.getCompanyId());
                }

            }

            // ES服务获取需求信息
            NeedsPageListReqData needsPageListReqData = new NeedsPageListReqData();
            needsPageListReqData.setOffset(listQuery.getOffset());
            needsPageListReqData.setPageSize(listQuery.getPageSize());
            if (StringUtils.isNotBlank(reqData.getSearchText())) {
                needsPageListReqData.setSearchText(reqData.getSearchText());
            }
            NeedsPageListResult needsPageListResult = needsDubboService.pageListNeedsVO(needsPageListReqData);
            if (!needsPageListResult.isSuccess()) {
                result.setResultCode(ResultCode.ERROR_401);
                result.setResultMessage(needsPageListResult.getResultMessage());
                return result;
            }

            List<com.edianniu.pscp.search.support.needs.vo.NeedsVO> needsVOList = needsPageListResult.getNeedsList();
            if (CollectionUtils.isNotEmpty(needsVOList)) {
                List<NeedsMarketVO> listNeedsMarketVO = new ArrayList<>();
                for (com.edianniu.pscp.search.support.needs.vo.NeedsVO needsVO : needsVOList) {
                    NeedsMarketVO needsMarketVO = new NeedsMarketVO();
                    BeanUtils.copyProperties(needsVO, needsMarketVO);
                    needsMarketVO.setId(Long.valueOf(needsVO.getNeedsId()));
                    // 时间戳格式转换
                    needsMarketVO.setPublishTime(DateUtils.timeStampFormatStr(needsVO.getPublishTime(), DateUtils.DATE_PATTERN));
                    needsMarketVO.setPublishCutoffTime(DateUtils.timeStampFormatStr(needsVO.getPublishCutoffTime(), DateUtils.DATE_PATTERN));
                    listNeedsMarketVO.add(needsMarketVO);
                }
                List<NeedsMarketVO> needsMarketVOList = needsService.buildListNeedsMarketVO(listNeedsMarketVO, listQuery);
                result.setNeedsList(needsMarketVOList);
            }

            result.setHasNext(needsPageListResult.isHasNext());
            result.setNextOffset(needsPageListResult.getNextOffset());
            result.setTotalCount(needsPageListResult.getTotalCount());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 需求详情(dubbo接口，外部调用)
     *
     * @param reqData
     * @return
     */
    @Override
    public NeedsMarketDetailsResult getNeedsMarketDetails(NeedsMarketDetailsReqData reqData) {
        NeedsMarketDetailsResult result = new NeedsMarketDetailsResult();
        try {
            UserInfo userInfo = null;
            if (reqData.getUid() != null) {
                GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(reqData.getUid());
                if (!getUserInfoResult.isSuccess()) {
                    result.set(ResultCode.ERROR_401, "用户信息不存在");
                    return result;
                }
                userInfo = getUserInfoResult.getMemberInfo();
            }

            if (StringUtils.isBlank(reqData.getOrderId()) && StringUtils.isBlank(reqData.getResponsedOrderId())) {
                result.set(ResultCode.ERROR_401, "请求参数错误");
                return result;
            }

            Long needsId = null;
            // 需求响应订单
            NeedsOrderInfo needsOrderInfo = null;
            Map<String, Object> parameter = new HashMap<>();
            if (StringUtils.isNotBlank(reqData.getResponsedOrderId())) {
                parameter.put("orderId", reqData.getResponsedOrderId());
                needsOrderInfo = customerNeedsOrderService.getEntityById(parameter);
            }
            if (needsOrderInfo == null) {
                needsOrderInfo = new NeedsOrderInfo();
                needsOrderInfo.setPayStatus(Constants.TAG_NO);
                // 未响应
                needsOrderInfo.setStatus(Constants.TAG_NO);
            }
            if (needsOrderInfo.getId() != null) {
                needsId = needsOrderInfo.getNeedsId();
            }

            // 需求信息
            NeedsVO needsVO = needsService.getNeedsById(needsId, reqData.getOrderId());
            if (needsVO != null) {
                if (StringUtils.isBlank(reqData.getResponsedOrderId())) {
                    Map<String, Object> parameter1 = new HashMap<>();
                    parameter1.put("needsId", needsVO.getId());
                    if (userInfo != null) {
                        parameter1.put("companyId", userInfo.getCompanyId());
                    }
                    parameter1.put("unequalStatus", NeedsOrderStatus.CANCEL.getValue());
                    NeedsOrderInfo needsOrderOldInfo = customerNeedsOrderService.getEntityById(parameter1);
                    if (needsOrderOldInfo != null) {
                        needsOrderInfo = needsOrderOldInfo;
                    }
                }

                if (StringUtils.isBlank(needsOrderInfo.getCautionMoney())) {
                    needsOrderInfo.setCautionMoney(MoneyUtils.format(needsVO.getCautionMoney()));
                }

                // 未支付屏蔽关键词
                Integer payStatus = needsOrderInfo.getPayStatus();
                if (!payStatus.equals(PayStatus.SUCCESS.getValue())) {
                    shieldingKeyWords(needsVO);
                    // 需求附件
                    if (CollectionUtils.isNotEmpty(needsVO.getAttachmentList())) {
                        List<Attachment> attachmentList = new ArrayList<>();
                        for (Attachment attachment : needsVO.getAttachmentList()) {
                            if (attachment.getIsOpen().equals(Constants.TAG_NO)) {
                                attachmentList.add(attachment);
                            }
                        }
                        needsVO.setAttachmentList(attachmentList);
                    }
                }
            }

            // 报价附件
            if (needsOrderInfo.getStatus() != null
                    && needsOrderInfo.getStatus() >= NeedsOrderStatus.QUOTED.getValue()) {
                needsOrderInfo.setQuotedAttachmentList(commonAttachmentService
                        .structureAttachmentList(needsOrderInfo.getId(), BusinessType.QUOTE_ATTACHMENT.getValue()));
            }
            result.setNeedsInfo(needsVO);
            result.setNeedsOrderInfo(needsOrderInfo);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("respondDetails", e);
        }
        return result;
    }

    /**
     * 需求列表，后台使用
     *
     * @param needsViewListReqData
     * @return
     */
    @Override
    public NeedsViewListResult getNeedsViewList(NeedsViewListReqData needsViewListReqData) {
        NeedsViewListResult result = new NeedsViewListResult();
        try {
            String status = needsViewListReqData.getStatus();
            if (!("not_audit".equals(status) || "audit_succeed".equals(status) || "audit_failed".equals(status))) {
                result.set(ResultCode.ERROR_401, "状态值不合法");
                return result;
            }
            PageResult<NeedsViewVO> pageResult = needsService.getNeedsViweList(needsViewListReqData);

            result.setNeedsViewVOList(pageResult.getData());
            result.setHasNext(pageResult.isHasNext());
            result.setNextOffset(pageResult.getNextOffset());
            result.setTotalCount(pageResult.getTotal());
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("needsList error", e);
        }
        return result;
    }

    /**
     * 替换关键词
     * @param needsVO
     */
    private void shieldingKeyWords(NeedsVO needsVO) {
        if (needsVO != null) {
            String keyword = needsVO.getKeyword();
            if (StringUtils.isNotBlank(keyword)) {
                String[] arr = keyword.trim().split(",");
                List<String> words = new ArrayList<String>();
                Collections.addAll(words, arr);
                try {
                    ShieldingKeyWords shieldingKeyWords = new ShieldingKeyWords();
                    shieldingKeyWords.createKeywordTree(words);

                    String needsName = shieldingKeyWords.searchKeyword(needsVO.getName());
                    needsVO.setName(needsName);
                    String needsDescribe = shieldingKeyWords.searchKeyword(needsVO.getDescribe());
                    needsVO.setDescribe(needsDescribe);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            // 设置其他字段值为空
            needsVO.setContactPerson(null);
            needsVO.setContactNumber(null);
            needsVO.setContactAddr(null);
            needsVO.setDistributionRooms(null);
            needsVO.setCompanyName(null);
        }
    }

    /**
     * 需求审核
     */
    @Override
    public AuditResult auditNeeds(AuditReqData auditReqData) {
        AuditResult result = new AuditResult();
        try {
            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(auditReqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_201, "用户信息不存在");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            Integer status = auditReqData.getStatus();
            if (!(NeedsStatus.RESPONDING.getValue().equals(status)
                    || NeedsStatus.FAIL_AUDIT.getValue().equals(status))) {
                result.set(ResultCode.ERROR_401, "状态不合法");
                return result;
            }
            String failReason = auditReqData.getFailReason();
            if (NeedsStatus.RESPONDING.getValue().equals(status)) {
                auditReqData.setFailReason(null);
            }
            if (NeedsStatus.FAIL_AUDIT.getValue().equals(status)) {
                if (StringUtils.isBlank(failReason)) {
                    result.set(ResultCode.ERROR_402, "请输入审核失败原因");
                    return result;
                }
                auditReqData.setMaskString(null);
            }
            String orderId = auditReqData.getOrderId();
            if (StringUtils.isBlank(orderId)) {
                result.set(ResultCode.ERROR_403, "orderId不能为空");
                return result;
            }
            NeedsVO needsVO = needsService.getNeedsById(null, orderId);
            if (null == needsVO) {
                result.set(ResultCode.ERROR_404, "需求信息不存在");
                return result;
            }
            Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_NEEDS_ORDERID + orderId, orderId, 500L);
            if (rs == 0) {
                result.set(ResultCode.ERROR_405, "需求审核中，请勿重复操作");
                return result;
            }
            needsService.auditNeeds(auditReqData, userInfo);

            // ES服务需求数据添加
            saveESNeedsInfo(orderId);
            // 消息推送
            auditNeedsPushMessage(needsVO);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("audit error", e);
        }
        return result;
    }

    /**
     * 客户发布需求，给运维发送短信 
     * @param needsName
     */
    private void sendSmsPM(final String needsName){
    	if (StringUtils.isBlank(needsName)) {
			return;
		}
    	try {
			EXECUTOR_SERVICE.submit(new Runnable() {
				@Override
				public void run() {
					MessageId messageId = MessageId.NEEDS_AUDIT;
                    Map<String, String> params = new HashMap<>();
                    params.put("needs_name", needsName);
                    QuerySysUserReq req = new QuerySysUserReq();
                	req.setNeedsAuditNotice(Constants.TAG_YES);
                	req.setStatus(Constants.TAG_YES);
            		List<SysUserInfo> list = sysUserInfoService.getList(req);
            		for (SysUserInfo sysUserInfo : list) {
            			messageInfoService.sendSmsMessage(sysUserInfo.getUserId(), sysUserInfo.getMobile(), messageId, params);
            		}
				}
			});
		} catch (Exception e) {
			 logger.error("给运维发信息异常{}", e);
		}
    }

    /**
     * 需求审核消息推送
     * @param needsVO
     */
    private void auditNeedsPushMessage(final NeedsVO needsVO) {
        try {
            EXECUTOR_SERVICE.submit(new Runnable() {
                @Override
                public void run() {
                    // 需求信息
                    NeedsVO needsNewVO = needsService.getNeedsById(needsVO.getId(), null);
                    if (needsNewVO == null) {
                        return;
                    }

                    // 获取需求客户信息
                    Map<String, Object> sendMessagePushCustomerMap = needsService.getSendMessagePushCustomer(needsNewVO.getId());
                    if (MapUtils.isEmpty(sendMessagePushCustomerMap)) {
                        return;
                    }

                    // 发送推送消息
                    Map<String, String> params = new HashMap<>();
                    params.put("needs_name", needsNewVO.getName());
                    params.put("member_name", String.valueOf(sendMessagePushCustomerMap.get("memberName")));
                    // 客户推送消息
                    customerPushMessage(needsVO, needsNewVO, sendMessagePushCustomerMap, params);
                }
            });
        } catch (Exception e) {
            logger.error("服务资质审核推送消息异常", e);
        }
    }
    
    /**
     * 服务资质审核推送消息
     * @param needsVO 
     */
    private void sendMessagePush(final NeedsVO needsVO) {
        if (needsVO == null) {
            return;
        }

        // 获取响应订单服务商信息
        final List<Map<String, Object>> sendMessagePushInfoList = customerNeedsOrderService.selectSendMessagePushInfo(needsVO.getId());
        if (CollectionUtils.isEmpty(sendMessagePushInfoList)) {
            return;
        }

        try {
            EXECUTOR_SERVICE.submit(new Runnable() {
                @Override
                public void run() {
                    // 需求信息
                    NeedsVO needsNewVO = needsService.getNeedsById(needsVO.getId(), null);
                    if (needsNewVO == null) {
                        return;
                    }

                    // 获取需求客户信息
                    Map<String, Object> sendMessagePushCustomerMap = needsService.getSendMessagePushCustomer(needsNewVO.getId());
                    if (MapUtils.isEmpty(sendMessagePushCustomerMap)) {
                        return;
                    }

                    // 发送推送消息
                    Map<String, String> params = new HashMap<>();
                    params.put("needs_name", needsNewVO.getName());
                    if (needsNewVO.getStatus().equals(NeedsStatus.FAIL_AUDIT.getValue())) {
                    	params.put("failure_cause", needsNewVO.getFailReason());
					}

                    // 客户推送消息
                    customerPushMessage(needsVO, needsNewVO, sendMessagePushCustomerMap, params);

                    // 获取响应订单服务商信息
                    List<Map<String, Object>> sendMessagePushInfoList = customerNeedsOrderService.selectSendMessagePushInfo(needsNewVO.getId());
                    if (CollectionUtils.isNotEmpty(sendMessagePushInfoList)) {
                        // 服务商推送消息
                        for (Map<String, Object> map : sendMessagePushInfoList) {
                            facilitatorPushMessage(needsVO, map, params);
                        }
                    }
                }
            });
        } catch (Exception e) {
            logger.error("服务资质审核推送消息异常", e);
        }
    }

    /**
     * ES服务需求数据添加
     * @param orderId 需求编号
     */
    private void saveESNeedsInfo(final String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return;
        }
        try {
            EXECUTOR_SERVICE.submit(new Runnable() {
                @Override
                public void run() {
                    Needs needs = needsService.getCustomerNeedsById(null, orderId);
                    if (needs == null || !NeedsStatus.RESPONDING.getValue().equals(needs.getStatus())) {
                        return;
                    }
                    // 构建ES保存接口请求参数
                    NeedsSaveReqData reqData = new NeedsSaveReqData();
                    reqData.setDescribe(needs.getDescribe());
                    reqData.setOrderId(needs.getOrderId());
                    reqData.setId(String.valueOf(needs.getId()));
                    reqData.setPublishTime(needs.getAuditTime().getTime());
                    reqData.setPublishCutoffTime(needs.getPublishCutoffTime().getTime());

                    reqData.setName(needs.getName());
                    reqData.setDescribe(needs.getDescribe());
                    try {
                        // 关键字替换
                        if (StringUtils.isNotBlank(needs.getKeyword())) {
                            // 关键字
                            String keyword = needs.getKeyword().trim();
                            String[] arr = keyword.trim().split(",");
                            List<String> words = new ArrayList<>();
                            Collections.addAll(words, arr);
                            // 字符窜替换类初始化
                            ShieldingKeyWords shieldingKeyWords = new ShieldingKeyWords();
                            shieldingKeyWords.createKeywordTree(words);
                            // 需求名称
                            String needsName = shieldingKeyWords.searchKeyword(needs.getName());
                            reqData.setName(needsName);
                            // 需求描述
                            String needsDescribe = shieldingKeyWords.searchKeyword(needs.getDescribe());
                            reqData.setDescribe(needsDescribe);
                        }

                        NeedsSaveResult esResult = needsDubboService.save(reqData);
                        if (!esResult.isSuccess()) {
                            logger.error("ES需求{}添加失败:{}", needs.getOrderId(), esResult.getResultMessage());
                        }
                    } catch (Exception e) {
                        logger.error("ES服务异常", e);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 需求详情，门户使用
     * @param orderId           需求编号
     * @param companyId         服务商的companyId
     * @param responsedOrderId  服务商响应ID  如果没有被响应，则为-1
     */
    @Override
    public NeedsInfoResult getNeedsInfo(String orderId, Long companyId, String responsedOrderId) {
        NeedsInfoResult needsInfoResult = new NeedsInfoResult();
        try {
            if (StringUtils.isBlank(orderId)) {
                needsInfoResult.set(ResultCode.ERROR_401, "orderId不能为空");
                return needsInfoResult;
            }
            NeedsVO needsVO = needsService.getNeedsById(null, orderId);
            if (null == needsVO) {
                needsInfoResult.set(ResultCode.ERROR_401, "需求不存在");
                return needsInfoResult;
            }
            if (null == companyId) {
                needsInfoResult.set(ResultCode.ERROR_402, "companyId不能为空");
                return needsInfoResult;
            }
            if (StringUtils.isBlank(responsedOrderId)) {
            	needsInfoResult.set(ResultCode.ERROR_402, "responsedOrderId不能为空");
                return needsInfoResult;
			}

            HashMap<String, Object> queryMap = new HashMap<String, Object>();
            // 如果已响应，则用响应订单ID查询
            if (! responsedOrderId.equals("-1")) {
				queryMap.put("orderId", responsedOrderId);
			} else { // 如果没有响应，则用需求ID和服务商公司ID匹配查询，并筛选出没有被取消的一条记录  （适用于从需求市场查询详情）
				queryMap.put("needsId", needsVO.getId());
	            queryMap.put("companyId", companyId);
	            queryMap.put("unequalStatus", NeedsOrderStatus.CANCEL.getValue());
			}
            NeedsOrderInfo needsOrderInfo = customerNeedsOrderService.getEntityById(queryMap);
            if (null != needsOrderInfo) {
                // 已响应
                // 将需求订单的状态赋给需求
                needsVO.setStatus(needsOrderInfo.getStatus());
                QuotedInfo quotedInfo = new QuotedInfo();
                if (StringUtils.isNotBlank(needsOrderInfo.getQuotedPrice())) {
                    quotedInfo.setQuotedPrice(needsOrderInfo.getQuotedPrice());
                }
                if (CollectionUtils.isNotEmpty(needsOrderInfo.getQuotedAttachmentList())) {
                    quotedInfo.setAttachmentList(needsOrderInfo.getQuotedAttachmentList());
                }
                needsVO.setQuotedInfo(quotedInfo);
            } else {
                // 未响应
                needsOrderInfo = new NeedsOrderInfo();
                needsOrderInfo.setStatus(NeedsOrderStatus.NOT_RESPONDING.getValue());
                needsOrderInfo.setPayStatus(Constants.TAG_NO);
            }
            needsInfoResult.setNeedsOrderInfo(needsOrderInfo);
            // 未支付的需要屏蔽关键字和关键附件
            Integer payStatus = needsOrderInfo.getPayStatus();
            if (!payStatus.equals(PayStatus.SUCCESS.getValue())) {
                // 屏蔽关键词
                shieldingKeyWords(needsVO);
                // 屏蔽关键需求附件
                if (CollectionUtils.isNotEmpty(needsVO.getAttachmentList())) {
                    List<Attachment> attachmentList = new ArrayList<>();
                    for (Attachment attachment : needsVO.getAttachmentList()) {
                        if (attachment.getIsOpen().equals(Constants.TAG_NO)) {
                            attachmentList.add(attachment);
                        }
                    }
                    needsVO.setAttachmentList(attachmentList);
                }
            }
            needsInfoResult.setNeedsVO(needsVO);
        } catch (Exception e) {
            logger.error("getdetails error", e);
        }
        return needsInfoResult;
    }

    /**
     * 需求派勘察订单初始化数据
     *
     * @param reqData
     * @return
     */
    @Override
    public InitDataResult surveyInitData(InitDataReqData reqData) {
        InitDataResult result = new InitDataResult();
        try {
            if (reqData.getUid() == null) {
                result.set(ResultCode.ERROR_401, "用户ID不能为空");
                return result;
            }
            if (StringUtils.isBlank(reqData.getOrderId())) {
                result.set(ResultCode.ERROR_401, "需求订单不能为空");
                return result;
            }

            GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(reqData.getUid());
            if (!getUserInfoResult.isSuccess()) {
                result.set(ResultCode.ERROR_401, "用户信息不存在");
                return result;
            }
            UserInfo userInfo = getUserInfoResult.getMemberInfo();

            result = needsService.getSurveyInitData(userInfo, reqData);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("audit error", e);
        }
        return result;
    }

    /**
     * 扫描超时需求
     */
    @Override
    public List<Long> getOvertimeNeeds(Integer limit) {
        return needsService.getOvertimeNeeds(limit);
    }

    /**
     * 处理超时需求
     */
    @Override
    public Result handleOvertimeNeeds(Long id) {
        Result result = new DefaultResult();
        try {
            if (null == id) {
                result.set(ResultCode.ERROR_401, "id不能为空");
                return result;
            }
            NeedsVO needsVO = needsService.getNeedsById(id, null);
            if (null == needsVO) {
                result.set(ResultCode.ERROR_402, "需求不存在");
                return result;
            }
            result = needsService.handleOvertimeNeeds(id);
            if (!result.isSuccess()) {
                return result;
            }
            if (!NeedsStatus.FAIL_AUDIT.getValue().equals(needsVO.getStatus())) {
                // 除审核失败的，都应删除ES服务需求数据
                deleteESNeedsInfo(String.valueOf(id));
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("handleOvertimeNeeds:{}", e);
        }
        return result;
    }

    

    /**
     * 服务商推送消息
     * @param needsVO
     * @param map
     * @param params
     */
    private void facilitatorPushMessage(NeedsVO needsVO, Map<String, Object> map, Map<String, String> params) {
        Integer status = Integer.valueOf(String.valueOf(map.get("status")));
        Long uid = Long.valueOf(String.valueOf(map.get("memberId")));
        String mobile = String.valueOf(map.get("mobile"));
        String companyName=String.valueOf(map.get("companyName"));
        params.put("member_name", companyName);
        MessageId messageId = null;

        if (NeedsStatus.RESPONDING.getValue().equals(needsVO.getStatus())
                && status.equals(NeedsOrderStatus.NOT_QUALIFIED.getValue())) {
            // 不符合
            messageId = MessageId.NEEDS_ORDER_INCONFORMITY_FACILITATOR;
        }
        if (NeedsStatus.RESPONDING.getValue().equals(needsVO.getStatus())
                && status.equals(NeedsOrderStatus.WAITING_QUOTE.getValue())) {
            // 符合(待报价)
            messageId = MessageId.NEEDS_ORDER_CONFORMITY_FACILITATOR;
        }
        if (NeedsStatus.QUOTED.getValue().equals(needsVO.getStatus())
                && status.equals(NeedsOrderStatus.NOT_COOPERATE.getValue())) {
            // 不合作
            messageId = MessageId.NEEDS_ORDER_UNCOOPERATIVE_FACILITATOR;
        }
        if (NeedsStatus.QUOTED.getValue().equals(needsVO.getStatus())
                && status.equals(NeedsOrderStatus.COOPERATED.getValue())) {
            // 合作
            messageId = MessageId.NEEDS_ORDER_COOPERATION_FACILITATOR;
        }

        if (messageId != null) {
            // 消息发送
            messageInfoService.sendSmsAndPushMessage(uid, mobile, messageId, params);
        }
    }

    /**
     * 客户推送消息
     * @param needsVO
     * @param needsNewVO
     * @param sendMessagePushCustomerMap
     * @param params
     */
    private void customerPushMessage(NeedsVO needsVO, NeedsVO needsNewVO, Map<String, Object> sendMessagePushCustomerMap, Map<String, String> params) {
        Long uid = Long.valueOf(String.valueOf(sendMessagePushCustomerMap.get("memberId")));
        String mobile = String.valueOf(sendMessagePushCustomerMap.get("mobile"));
        MessageId messageId = null;

        if (NeedsStatus.QUOTED.getValue().equals(needsVO.getStatus())
                && NeedsStatus.COOPETATED.getValue().equals(needsNewVO.getStatus())) {
            // 已合作
            messageId = MessageId.NEEDS_COOPERATION_SUCCESS_CUSTOMER;
        }
        if (NeedsStatus.QUOTED.getValue().equals(needsVO.getStatus())
                && NeedsStatus.CANCELED.getValue().equals(needsNewVO.getStatus())) {
            // 取消合作
            messageId = MessageId.NEEDS_CANCEL_COOPERATION_CUSTOMER;
        }
        boolean existed = (NeedsStatus.RESPONDING.getValue().equals(needsVO.getStatus())
                || NeedsStatus.QUOTING.getValue().equals(needsVO.getStatus()))
                && NeedsStatus.CANCELED.getValue().equals(needsNewVO.getStatus());
        if (existed) {
            // 取消发布
            messageId = MessageId.NEEDS_CANCEL_PUBLICATION_CUSTOMER;
        }
        if (NeedsStatus.AUDITING.getValue().equals(needsVO.getStatus())
                && NeedsStatus.RESPONDING.getValue().equals(needsNewVO.getStatus())) {
            // 需求审核通过
            messageId = MessageId.NEEDS_AUDIT_SUCCESS_CUSTOMER;
        }
        if (NeedsStatus.AUDITING.getValue().equals(needsVO.getStatus())
                && NeedsStatus.FAIL_AUDIT.getValue().equals(needsNewVO.getStatus())) {
            // 需求审核不通过
            messageId = MessageId.NEEDS_AUDIT_FAILURE_CUSTOMER;
            params.put("failure_cause", needsNewVO.getFailReason());
        }
        if (NeedsStatus.RESPONDING.getValue().equals(needsVO.getStatus())
                && NeedsStatus.OVERTIME.getValue().equals(needsNewVO.getStatus())) {
            // 需求已到截止时间
            messageId = MessageId.NEEDS_CLOSE_CUSTOMER;
        }

        if (messageId != null) {
            // 消息发送
            messageInfoService.sendSmsAndPushMessage(uid, mobile, messageId, params);
        }
    }

    /**
     * 创建一个固定线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            // 工作队列
            new SynchronousQueue<Runnable>(),
            // 线程池饱和处理策略
            new ThreadPoolExecutor.CallerRunsPolicy());
}
