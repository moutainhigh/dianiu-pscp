package com.edianniu.pscp.cs.service.dubbo.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.cs.bean.equipment.DeleteReqData;
import com.edianniu.pscp.cs.bean.equipment.DeleteResult;
import com.edianniu.pscp.cs.bean.equipment.DetailsReqData;
import com.edianniu.pscp.cs.bean.equipment.DetailsResult;
import com.edianniu.pscp.cs.bean.equipment.ListReqData;
import com.edianniu.pscp.cs.bean.equipment.ListResult;
import com.edianniu.pscp.cs.bean.equipment.SaveReqData;
import com.edianniu.pscp.cs.bean.equipment.SaveResult;
import com.edianniu.pscp.cs.bean.equipment.vo.EquipmentVO;
import com.edianniu.pscp.cs.bean.room.vo.RoomVO;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.cs.service.EquipmentService;
import com.edianniu.pscp.cs.service.RoomService;
import com.edianniu.pscp.cs.service.dubbo.EquipmentInfoService;
import com.edianniu.pscp.cs.util.BizUtils;
import com.edianniu.pscp.cs.util.DateUtils;
import com.edianniu.pscp.mis.bean.GetUserInfoResult;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;

@Service
@Repository("equipmentInfoService")
public class EquipmentInfoServiceImpl implements EquipmentInfoService {
	private static final Logger logger = LoggerFactory.getLogger(EquipmentInfoServiceImpl.class);
	
	@Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("equipmentService")
	private EquipmentService equipmentService;
	
	@Autowired
	@Qualifier("roomService")
	private RoomService roomService;
	
	/**
	 * 保存、编辑配电房设备
	 * @return
	 */
	@Override
	public SaveResult saveEquipment(SaveReqData saveReqData){
		SaveResult result = new SaveResult();
		try {
			if (null == saveReqData.getUid()) {
				result.set(ResultCode.ERROR_201, "uid不能为为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(saveReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
            UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
            CompanyInfo companyInfo = getUserInfoResult.getCompanyInfo();
			if (null == companyInfo) {
				result.set(ResultCode.ERROR_201, "用户公司信息不存在");
				return result;
			}
            if (null == userInfo.getLoginName() || 0L == userInfo.getCompanyId()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "请进行实名认证");
                return result;
            }
            if (!userInfo.isCustomer()) {
                result.set(ResultCode.UNAUTHORIZED_ERROR, "认证为客户后，才能为配电房添加设备");
                return result;
            }
            Long equipmentId = saveReqData.getId();
            if (null != equipmentId) {
				EquipmentVO equipmentVO = equipmentService.getEquipmentVO(equipmentId);
            	if (null == equipmentVO) {
            		result.set(ResultCode.ERROR_401, "要编辑的设备不存在");
    				return result;
				}
			}
			if (null == saveReqData.getRoomId()) {
				result.set(ResultCode.ERROR_401, "需要关联配电房");
				return result;
			}
			RoomVO roomVO = roomService.queryRoomVO(saveReqData.getRoomId());
			if (null == roomVO ) {
				result.set(ResultCode.ERROR_401, "关联的配电房不存在");
				return result;
			}
			if (! BizUtils.checkLength(saveReqData.getName(), 200)) {
				result.set(ResultCode.ERROR_402, "设备名称不能为空或超过200个字");
				return result;
			}
			if (StringUtils.isBlank(saveReqData.getProductionDate())) {
				result.set(ResultCode.ERROR_403, "生产日期不能为空");
				return result;
			}
			Date productionDate = DateUtils.parse(saveReqData.getProductionDate(), DateUtils.DATE_PATTERN);
			if (productionDate == null) {
				result.set(ResultCode.ERROR_403, "生产日期不合法");
				return result;
			}
			if (! BizUtils.checkLength(saveReqData.getSerialNumber(), 20)) {
				result.set(ResultCode.ERROR_404, "出厂编号不应为空或超过20字");
			}
			if (! BizUtils.checkLength(saveReqData.getManufacturer(), 20)) {
				result.set(ResultCode.ERROR_405, "生产厂家不能为空或超过50字");
				return result;
			}
			String model = saveReqData.getModel();
			if (StringUtils.isNotBlank(model) && model.trim().length() > 20) {
				result.set(ResultCode.ERROR_406, "型号不能超过20字");
				return result;
			}
			String ratedVoltage = saveReqData.getRatedVoltage();
			if (StringUtils.isNotBlank(ratedVoltage) && ratedVoltage.trim().length() > 20) {
				result.set(ResultCode.ERROR_406, "额定电压不能超过20字");
				return result;
			}
			String ratedCurrent = saveReqData.getRatedCurrent();
			if (StringUtils.isNotBlank(ratedCurrent) && ratedCurrent.trim().length() > 20) {
				result.set(ResultCode.ERROR_406, "额定电流不能超过20字");
				return result;
			}
			String ratedBreakingCurrent = saveReqData.getRatedBreakingCurrent();
			if (StringUtils.isNotBlank(ratedBreakingCurrent) && ratedBreakingCurrent.trim().length() > 20) {
				result.set(ResultCode.ERROR_406, "额定开断电流不能超过20字");
				return result;
			}
			String ratedCapacity = saveReqData.getRatedCapacity();
			if (StringUtils.isNotBlank(ratedCapacity) && ratedCapacity.trim().length() > 20) {
				result.set(ResultCode.ERROR_406, "额定容量不能超过20字");
				return result;
			}
			String maxWorkingVoltage = saveReqData.getMaxWorkingVoltage();
			if (StringUtils.isNotBlank(maxWorkingVoltage) && maxWorkingVoltage.trim().length() > 20) {
				result.set(ResultCode.ERROR_406, "最高工作电压不能超过20字");
				return result;
			}
			String currentTransformerRatio = saveReqData.getCurrentTransformerRatio();
			if (StringUtils.isNotBlank(currentTransformerRatio) && currentTransformerRatio.trim().length() > 30) {
				result.set(ResultCode.ERROR_406, "电流互感器变比不能超过30字");
				return result;
			}
			String voltageTransformerRatio = saveReqData.getVoltageTransformerRatio();
			if (StringUtils.isNotBlank(voltageTransformerRatio) && voltageTransformerRatio.trim().length() > 30) {
				result.set(ResultCode.ERROR_406, "电压互感器变比不能超过30字");
				return result;
			}
            //保存设备信息
			result = equipmentService.save(saveReqData, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("save:{}", e);
		}
		return result;
	}
	
	/**
	 * 查询配电房设备列表
	 * @param listReqData
	 * @return
	 */
	@Override
	public ListResult equipmentVOList(ListReqData listReqData){
		ListResult result = new ListResult();
		try {
			if (null == listReqData.getUid()) {
				result.set(ResultCode.ERROR_201, "uid不能为空");
				return result;
			}
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(listReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
            if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long roomId = listReqData.getRoomId();
			if (roomId != null && roomId != -1) {  //如果等于-1或null，则查询所有配电房的设备
				RoomVO roomVO = roomService.queryRoomVO(roomId);
				if (null == roomVO) {
					result.set(ResultCode.ERROR_402, "关联的配电房不存在");
					return result;
				}
			}
			PageResult<EquipmentVO> pageResult = equipmentService.getEquipmentVOList(listReqData, userInfo);
			
			result.setEquipments(pageResult.getData());
			result.setHasNext(pageResult.isHasNext());
			result.setNextOffset(pageResult.getNextOffset());
			result.setTotalCount(pageResult.getTotal());
			
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("list:{}", e);
		 }
		return result;
	}
	
	/**
	 * 设备详情
	 */
	@Override
	public DetailsResult getEquipmentVODetails(DetailsReqData detailsReqData){
		DetailsResult result = new DetailsResult();
		try {
			if (null == detailsReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(detailsReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long id = detailsReqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_401, "设备ID不能为空");
				return result;
			}
			EquipmentVO equipmentVO = equipmentService.getEquipmentVO(id);
			result.setEquipment(equipmentVO);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("details:{}", e);
		}
		return result;
	}
	
	/**
	 * 配电房设备删除
	 */
	@Override
	public DeleteResult deleteEquipment(DeleteReqData deleteReqData){
		DeleteResult result = new DeleteResult();
		try {
			if (null == deleteReqData.getUid()) {
                result.set(ResultCode.ERROR_201, "uid不能为空");
                return result;
            }
			GetUserInfoResult getUserInfoResult = userInfoService.getUserInfo(deleteReqData.getUid());
			if (!getUserInfoResult.isSuccess()) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			UserInfo userInfo = getUserInfoResult.getMemberInfo();
			if (null == userInfo) {
				result.set(ResultCode.ERROR_201, "用户信息不存在");
				return result;
			}
			Long id = deleteReqData.getId();
			if (null == id) {
				result.set(ResultCode.ERROR_401, "设备ID不能为空");
				return result;
			}
			EquipmentVO equipmentVO = equipmentService.getEquipmentVO(id);
        	if (null == equipmentVO) {
        		result.set(ResultCode.ERROR_401, "要删除的设备不存在");
				return result;
			}
        	result = equipmentService.deleteEquipment(id, userInfo);
		} catch (Exception e) {
			result.setResultCode(ResultCode.ERROR_500);
            result.setResultMessage("系统异常");
            logger.error("delete:{}", e);
		}
		return result;
	}
	
}
