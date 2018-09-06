package com.edianniu.pscp.portal.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edianniu.pscp.portal.bean.req.RenterConfigReq;
import com.edianniu.pscp.portal.bean.req.RenterReq;
import com.edianniu.pscp.portal.entity.CompanyBuildingEntity;
import com.edianniu.pscp.portal.entity.CompanyMeterEntity;
import com.edianniu.pscp.portal.entity.PowerPriceConfig;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.CompanyBuildingService;
import com.edianniu.pscp.portal.service.CompanyConfigService;
import com.edianniu.pscp.portal.service.CompanyMeterService;
import com.edianniu.pscp.portal.utils.BizUtils;
import com.edianniu.pscp.portal.utils.PageUtils;
import com.edianniu.pscp.portal.utils.PasswordUtil;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.edianniu.pscp.renter.mis.bean.renter.*;
import com.edianniu.pscp.renter.mis.service.dubbo.RenterInfoService;

/**
 * 租客管理
 * @author zhoujianjian
 * @date 2018年3月28日 下午5:47:58
 */
@Controller
@RequestMapping("/renter")
public class RenterController { 
	
	@Autowired
	private RenterInfoService renterInfoService;
	
	@Autowired
	private CompanyBuildingService companyBuildingService;
	
	@Autowired
	private CompanyMeterService companyMeterService;
	
	@Autowired
	private CompanyConfigService companyConfigService;
	
	/**
	 * 租客列表
	 * @param page         页数 
	 * @param limit        每页最大显示数量
	 * @param mobile       手机号码
	 * @param companyName  租客公司名称
	 * @param status       租客状态
	 * @param sdate        创建时间（较小）yyyy-MM-dd
	 * @param bdate        创建时间（较大）yyyy-MM-dd
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("renter:list")
	public R list(@RequestParam(required = false, defaultValue = "1") Integer page,
			      @RequestParam(required = false, defaultValue = "10") Integer limit,
			      @RequestParam(required = false) String mobile,
			      @RequestParam(required = false) String companyName,
			      @RequestParam(required = false) Integer status,
			      @RequestParam(required = false) String sdate,
			      @RequestParam(required = false) String bdate){
		Integer offset = (page - 1) * limit;
		Long uid = ShiroUtils.getUserId();
		
		ListReq req = new ListReq();
		req.setUid(uid);
		req.setLimit(limit);
		req.setOffset(offset);
		req.setMobile(mobile);
		req.setName(companyName);
		req.setStatus(status);
		req.setBdate(bdate);
		req.setSdate(sdate);
		
		ListResult result = renterInfoService.renterList(req);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		PageUtils pageUtils = new PageUtils(result.getRenterList(), result.getTotalCount(), limit, page);
		return R.ok().put("page", pageUtils);
	}
	
	/**
	 * 租客详情
	 * @param id  租客ID
	 */
	@ResponseBody
	@RequiresPermissions("renter:detail")
	@RequestMapping("/detail/{id}")
	public R getRenterDetail(@PathVariable("id") Long id){
		Long uid = ShiroUtils.getUserId();
		
		DetailReq req = new DetailReq();
		req.setRenterId(id);
		req.setUid(uid);
		DetailResult result = renterInfoService.getRenter(req);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		
		return R.ok().put("renter", result.getRenterVO());
	}
	
	/**
	 * 租客保存
	 * @param renter
	 * 租客对象包括的属性： Long id（新增时id为空）
     *                     String name
	 *                 	   String mobile
	 *                 	   String contract
	 *                     String pwd
	 *                     String address
	 */
	@ResponseBody
	@RequiresPermissions("renter:save")
	@RequestMapping("/save")
	public R saveRenter(@RequestBody RenterReq renter){
		SaveReq req = new SaveReq();

		if (!renterInfoService.isUserExist(renter.getMobile())) {
			if (! BizUtils.checkLength(renter.getName(), 32)) {
				return R.error("租客名称不得为空或超过32个字");
			}
			if (! BizUtils.checkMobile(renter.getMobile())) {
				return R.error("手机号码不合法");
			}
			if (! BizUtils.checkLength(renter.getContract(), 32)) {
				return R.error("联系人不得为空或超过32个字");
			}
			if (! BizUtils.checkLength(renter.getAddress(), 100)) {
				return R.error("地址不得为空或超过100个字");
			}
			String pwd = renter.getPwd();
			if (!BizUtils.checkPassword(pwd)) {
				return R.error("密码应由6-20位数字、字母或特殊字符组合");
			}
			req.setPwd(PasswordUtil.encode(pwd));
		} 
		req.setUid(ShiroUtils.getUserId());
		req.setId(renter.getId());
		req.setName(renter.getName());
		req.setMobile(renter.getMobile());
		req.setContract(renter.getContract());
		req.setAddress(renter.getAddress());
		
		SaveResult result = renterInfoService.saveRenter(req);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok().put("renterId", result.getRenterId());
	}
	
	/**
	 * 判断用户是否存在
	 * @param mobile  租客电话号码
	 */
	@ResponseBody
	@RequestMapping("/isUserExist")
	public R isUserExist(String mobile){
		Boolean exist = renterInfoService.isUserExist(mobile);
		return R.ok().put("exist", exist);
	}
	
	/**
	 * 租客配置详情
	 * @param renterId 租客ID
	 */
	@ResponseBody
	@RequiresPermissions("renter:renterConfig")
	@RequestMapping("/renterConfig/{renterId}")
	public R getRenterConfigDetail(@PathVariable("renterId") Long renterId){
		Long uid = ShiroUtils.getUserId();
		
		DetailReq req = new DetailReq();
		req.setRenterId(renterId);
		req.setUid(uid);
		ConfigDetailResult result = renterInfoService.getRenterConfig(req);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		
		return R.ok().put("renterConfig", result.getRenterConfigVO());
	}
	
	/**
	 * 获取计费方式           0:普通     1:分时
	 * companyId:   房东公司ID（客户）
	 */
	@ResponseBody
	@RequestMapping("/getChargeMode/{companyId}")
	public R getChargeMode(@PathVariable("companyId")Long companyId){
		PowerPriceConfig config = companyConfigService.getPowerPriceConfig(companyId);
		if (null == config) {
			return R.error("请先配置用电参数");
		}
		Integer subChargeMode = config.getChargeMode();
		
		return R.ok().put("subChargeMode", subChargeMode);
	}
	
	/**
	 * 楼宇下拉列表
	 */
	@ResponseBody
	@RequestMapping("/buildingList")
	public R getBuildingList(){
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		Long companyId = userEntity.getCompanyId();
		
		if (null == companyId || 0L == companyId) {
			return R.error("请先进行身份认证");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		List<CompanyBuildingEntity> buildingList = companyBuildingService.queryList(map);
		
		return R.ok().put("buildingList", buildingList);
	}
	
	/**
	 * 设备下拉列表
	 * @param buildingIds  楼宇ID数组
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/meterList")
	public R getMeterList(@RequestBody Long[] buildingIds){
		if (null == buildingIds || buildingIds.length == 0) {
			return R.error("请选择楼宇");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("buildingIds", buildingIds);
		List<CompanyMeterEntity> meterList = companyMeterService.queryList(map);
		
		return R.ok().put("meterList", meterList);
	}
	
	/**
	 * 租客配置保存
	 * @param renterId       租客ID
	 * @param renterConfig   租客配置，配置对象属性包括：
	 *            Long id;                租客配置id（新增时为空）
	 *            Integer chargeMode;     缴费方式        1:预缴费(默认)   2:月结算
	 *            String  amountLimit     预缴费租客余额提醒临界值(不可为空)，月结算租客可为空
	 *            String  startTime;      租客入网日期         yyyy-MM-dd
	 *            String  endTime         租客解除合约时间  yyyy-MM-dd
	 *            Integer subChargeMode   计费方式     0:普通   1:分时
	 *            String  basePrice       普通单价
	 *            String  apexPrice       尖单价
	 *            String  peakPrice       峰单价
	 *            String  flatPrice       平单价
	 *            String  valleyPrice     谷单价
	 *		      List<RenterMeterInfo> meterList;  仪表设备
	 *                 RenterMeterInfo属性包括：
	 * 				     			 id         仪表ID
	 *                  		     name       仪表名称
	 *                    			 rate       费用占比
	 *                    			 buildingId 楼宇ID
	 */
	@ResponseBody
	@RequiresPermissions("renterConfig:save")
	@RequestMapping("/renterConfig/save/{renterId}")
	public R saveRenterConfig(@PathVariable("renterId") Long renterId, @RequestBody RenterConfigReq renterConfig){
		SaveConfigReq req = new SaveConfigReq();
		req.setUid(ShiroUtils.getUserId());
		req.setRenterId(renterId);
		req.setId(renterConfig.getId());
		BeanUtils.copyProperties(renterConfig, req);
		
		SaveResult result = renterInfoService.saveRenterConfig(req);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		return R.ok();
	}
	

}
