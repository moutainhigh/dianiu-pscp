package com.edianniu.pscp.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edianniu.pscp.cms.bean.LineBindType;
import com.edianniu.pscp.cms.commons.CacheKey;
import com.edianniu.pscp.cms.commons.Constants;
import com.edianniu.pscp.cms.entity.CompanyBuildingEntity;
import com.edianniu.pscp.cms.entity.CompanyEntity;
import com.edianniu.pscp.cms.entity.CompanyEquipmentEntity;
import com.edianniu.pscp.cms.entity.CompanyLineEntity;
import com.edianniu.pscp.cms.entity.CompanyMeterEntity;
import com.edianniu.pscp.cms.service.CompanyBuildingService;
import com.edianniu.pscp.cms.service.CompanyEquipmentService;
import com.edianniu.pscp.cms.service.CompanyLineService;
import com.edianniu.pscp.cms.service.CompanyMeterService;
import com.edianniu.pscp.cms.service.CompanyService;
import com.edianniu.pscp.cms.utils.BizUtils;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.cs.bean.power.CompanyLinesReqData;
import com.edianniu.pscp.cs.bean.power.CompanyLinesResult;
import com.edianniu.pscp.cs.bean.power.HasDataResult;
import com.edianniu.pscp.cs.bean.power.vo.LineVO;
import com.edianniu.pscp.cs.service.dubbo.PowerInfoService;

import stc.skymobi.cache.redis.JedisUtil;

/**
 * 企业线路
 * @author zhoujianjian
 * @date 2017年12月20日 上午9:46:24
 */
@Controller
@RequestMapping("/companyLine")
public class CompanyLineController extends AbstractController{
	
	@Autowired
	private CompanyLineService companyLineService;
	@Autowired
	private PowerInfoService powerInfoService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyMeterService companyMeterService;
	@Autowired
	private CompanyEquipmentService companyEquipmentService;
	@Autowired
	private CompanyBuildingService companyBuildingService;
	@Autowired
    @Qualifier("jedisUtil")
    private JedisUtil jedisUtil;
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(){
        return "company/line.html";
    }
	
	/**
	 * 客户线路列表---下拉列表框
	 * @param companyId  客户公司ID
	 */
	@ResponseBody
	@RequestMapping("/customerLineList/{companyId}")
	public R customerLineList(@PathVariable("companyId") Long companyId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		CompanyLinesReqData reqData = new CompanyLinesReqData();
		reqData.setUid(entity.getMemberId());
		CompanyLinesResult result = powerInfoService.getCompanyLines(reqData);
		if (!result.isSuccess()) {
			return R.error(result.getResultCode(), result.getResultMessage());
		}
		
		List<LineVO> lines = new ArrayList<>();
		// 园区主线
		LineVO mainLine = new LineVO(0L, "园区总线");
		mainLine.setParentId(-1L);
		lines.add(mainLine);
		lines.addAll(result.getLines());
		
		return R.ok().put("lineList", lines);
	}
	
	/**
	 * 客户采集点（仪表）列表---下拉列表框
	 * @param companyId   客户公司ID
	 */
	@ResponseBody
	@RequestMapping("/customerMeterList/{companyId}")
	public R customerMeterList(@PathVariable("companyId") Long companyId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("bindStatus", Constants.TAG_NO);
		List<CompanyMeterEntity> meterList = companyMeterService.queryList(map);
		
		return R.ok().put("meterList", meterList);
	}
	
	/**
	 * 线路绑定类型列表
	 */
	@ResponseBody
	@RequestMapping("/bindTypeList")
	public R bindTypeList(){
		List<HashMap<String, Object>> mapList = new ArrayList<>();
		LineBindType[] values = LineBindType.values();
		for (LineBindType lineBindType : values) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("bindType", lineBindType.getValue());
			map.put("bindTypeName", lineBindType.getDesc());
			mapList.add(map);
		}
		return R.ok().put("bindTypeList", mapList);
	}
	
	/**
	 * 客户楼宇列表---下拉列表框
	 * @param companyId  客户公司ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/customerBuildingList/{companyId}")
	public R customerBuildingList(@PathVariable("companyId") Long companyId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		List<CompanyBuildingEntity> buildingList = companyBuildingService.queryList(map);
		
		return R.ok().put("buildingList", buildingList);
	}
	
	/**
	 * 客户设备列表---下拉列表框
	 * @param companyId  客户公司ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/customerEquipmentList/{companyId}")
	public R customerEquipmentList(@PathVariable("companyId") Long companyId){
		CompanyEntity entity = companyService.getCompanyByCompanyId(companyId);
		if (null == entity) {
			return R.error("companyId不合法");
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("bindStatus", Constants.TAG_NO);
		List<CompanyEquipmentEntity> equipmentList = companyEquipmentService.queryList(map);
		for (CompanyEquipmentEntity equipmentEntity : equipmentList) {
			equipmentEntity.setName(equipmentEntity.getNameWithAddress());
		}
		return R.ok().put("equipmentList", equipmentList);
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	@RequiresPermissions("companyLine:saveOrUpdate")
	public R saveOrUpdate(@RequestBody CompanyLineEntity bean){
		if (null == bean) {
			return R.error("参数不合法");
		}
		if (! BizUtils.checkLength(bean.getName(), 20)) {
			return R.error("名称不能为空且不能超过20字");
		}
		Long companyId = bean.getCompanyId();
		if (null == companyId) {
			return R.error("客户不能为空");
		}
		if (null == bean.getMeterId()) {
			bean.setMeterId(0L);
		}
		// 编辑时，已绑定的采集点，不能修改
		if (null != bean.getId()) {
			CompanyLineEntity lineEntity = companyLineService.queryObject(bean.getId());
			if (lineEntity.getMeterId() != 0L) {
				if (!bean.getMeterId().equals(lineEntity.getMeterId())) {
					return R.error("已绑定的采集点不能修改");
				}
			}
		}
		Long parentId = bean.getParentId();
		Integer isReferRoom = bean.getIsReferRoom();  //是否与配电房关联(仅对主线有意义)   1:关联(默认) 0:不关联
		if (null == parentId || 0L == parentId) {
			if (null == isReferRoom) {
				return R.error("属性不能为空");
			}
			// 如果父线路为0（空），则默认为园区总线路，绑定类型为只能是主线，bindID和buildingID为空
			bean.setParentId(0L);
			bean.setBindType(LineBindType.MAIN_LINE.getValue());
			bean.setBindId(0L);
			bean.setBuildingId(0L);
		} else {
			if (null == bean.getBindType() || 0L == bean.getBindType()) {
				return R.error("绑定类型不能为空");
			}
			if (bean.getBindType().equals(LineBindType.MAIN_LINE.getValue())) {
				return R.error("您添加的线路不从属与主线，所以不能绑定主线，只能绑定设备或楼宇");
			} else {
				// 如果绑定类型不是主线，则必须选择要绑定的楼宇或设备
				if (null == bean.getBindId()) {
					return R.error("请选择要绑定的楼宇或设备");
				}
				// 如果绑定类型为楼宇，则buildingId = bindId
				if (bean.getBindType().equals(LineBindType.BUILDING.getValue())) {
					bean.setBuildingId(bean.getBindId());
				}
				// 如果绑定类型是设备，则buildingId为父线路的buildingId
				if (bean.getBindType().equals(LineBindType.EQUIPMENT.getValue())) {
					CompanyLineEntity entity = companyLineService.queryObject(parentId);
					if (null != entity) {
						bean.setBuildingId(entity.getBuildingId());
					} else {
						bean.setBuildingId(0L);
					}
				}
			}
		}
		bean.setIsReferRoom(isReferRoom == null ? 1 : isReferRoom);
		
		Long rs = jedisUtil.setnx(CacheKey.CACHE_KEY_COMPANY_LINE_COMPANYID + companyId, String.valueOf(companyId), 500L);
        if (rs == 0) {
        	return R.error("请勿重复操作！");
        }
		companyLineService.saveOrUpdate(bean);
		if (null == bean.getId()) {
			return R.ok("新建成功");
		} 
		return R.ok("更新成功");
	}
	
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("companyLine:info")
	public R info(@PathVariable("id") Long id){
		CompanyLineEntity entity = companyLineService.queryObject(id);
		Long parentId = entity.getParentId();
		Integer bindType = entity.getBindType();
		Long bindId = entity.getBindId();
		Long meterId = entity.getMeterId();
		// 园区主线路
		if (0L == parentId) {
			entity.setParentName("园区总线");
			entity.setBindTypeName(LineBindType.MAIN_LINE.getDesc());
		} else {
			entity.setParentName(companyLineService.queryObject(parentId).getName());
			if (bindType == LineBindType.BUILDING.getValue()) {
				entity.setBindTypeName(LineBindType.BUILDING.getDesc());
				entity.setBindName(companyBuildingService.queryObject(bindId).getName());
			}
			if (bindType == LineBindType.EQUIPMENT.getValue()) {
				entity.setBindTypeName(LineBindType.EQUIPMENT.getDesc());
				entity.setBindName(companyEquipmentService.queryObject(bindId).getName());
			}
		}
		if (0L != meterId) {
			entity.setMeterName(companyMeterService.queryObject(meterId).getName());
		}
		
		return R.ok().put("companyLine", entity);
	}
	
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("companyLine:list")
	public R list(@RequestParam(required = false, defaultValue = "10") Integer limit,
				  @RequestParam(required = false, defaultValue = "1") Integer page,
				  @RequestParam(required = false) String companyName,
				  @RequestParam(required = false) String mobile){
		HashMap<String, Object> map = new HashMap<>();
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		map.put("companyName", companyName == null ? null : companyName.trim());
		map.put("mobile", mobile == null ? null : mobile.trim());
		
		List<CompanyLineEntity> list = companyLineService.queryList(map);
		int total = companyLineService.queryTotal(map);
		PageUtils pageUtils = new PageUtils(list, total, limit, page);
		
		return R.ok().put("page", pageUtils);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("companyLine:delete")
	public R delete(@RequestBody Long[] ids){
		for (Long id : ids) {
			// 判断是否有子线路
			int count = companyLineService.getCountChildLines(id);
			if (count > 0) {
				return R.error("ID为" + id + "的线路有子线路，不可删除");
			}
			CompanyLineEntity lineEntity = companyLineService.queryObject(id);
			if (null == lineEntity) {
				return R.error("ID为" + id + "的线路不存在");
			}
			// 判断是否绑定采集点
			Long meterId = lineEntity.getMeterId();
			if (meterId != 0L) {
				CompanyMeterEntity meterEntity = companyMeterService.queryObject(meterId);
				if (null == meterEntity) {
					return R.error("ID为" + id + "的线路绑定的仪表ID不合法");
				}
				Long companyId = meterEntity.getCompanyId();
				String meterNo = meterEntity.getMeterId();
				HasDataResult result = powerInfoService.hasData(meterNo, companyId);
				if (!result.isSuccess()) {
					return R.error("系统异常");
				}
				Integer hasData = result.getHasData();
				if (null == hasData) {
					return R.error("hasData为空");
				}
				if (!hasData.equals(Constants.TAG_NO)) {
					return R.error("ID为" + id + "的线路绑定的仪表已有采集数据数据，不可删除");
				}
			}
		}
		companyLineService.deleteBatch(ids);
		return R.ok("删除成功");
	}
	

}


