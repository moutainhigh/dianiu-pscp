package com.edianniu.pscp.renter.mis.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edianniu.pscp.renter.mis.bean.renter.HomeResult;
import com.edianniu.pscp.renter.mis.bean.renter.SaveConfigReq;
import com.edianniu.pscp.renter.mis.bean.renter.UseType;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterConfigVO;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterDataVO;
import com.edianniu.pscp.renter.mis.bean.renter.vo.RenterVO;
import com.edianniu.pscp.renter.mis.commons.PageResult;
import com.edianniu.pscp.renter.mis.domain.Renter;
import com.edianniu.pscp.renter.mis.domain.RenterConfig;
import com.edianniu.pscp.search.support.meter.vo.ElectricChargeStat;

/**
 * 租客管理
 * @author zhoujianjian
 * @date 2018年3月28日 下午6:34:10
 */
public interface RenterService {
   /**
    * 获取租客Id列表
    * @param chargeMode
    * @param limit
    * @return
    */
   List<Long> getRenterIds(Integer chargeMode,Integer limit);
	/**
	 * 分页获取租客列表
	 * @param queryMap
	 * @return
	 */
	PageResult<RenterVO> getList(HashMap<String, Object> queryMap);
	
	/**
	 * 新建租客信息
	 * @param name     租客名称
	 * @param mobile   租客电话
	 * @param contract 租客联系人
	 * @param renterMemberId 租客用户UID
	 * @param companyId 客户公司ID
	 */
	Long save(String name, String mobile, String contract, String address, Long renterMemberId, Long companyId);
	
	/**
	 * 编辑租客信息
	 * @param renterId  租客ID
	 * @param name      租客名称
	 * @param mobile    租客电话
	 * @param contract  租客联系人
	 */
	void update(Long renterId, String name, String mobile, String contract, String address);

	/**
	 * 根据ID查询租客
	 * @param id         租客ID，不可为空
	 * @param companyId  客户公司ID，可为空
	 * @return
	 */
	RenterVO getById(Long id, Long companyId);
	/**
	 * 根据ID查询租客
	 * @param id
	 * @return
	 */
	Renter getById(Long id);

	/**
	 * 判断租客名称是否重复
	 * @param name      租客名称
	 * @param companyId 客户公司ID
	 * @return
	 */
	Boolean isRenterNameExist(String name, Long companyId);

	/**
	 * 判断租客是否已经存在
	 * @param mobile     租客电话
	 * @param companyId  客户公司ID
	 * @return
	 */
	Boolean isRenterExit(String mobile, Long companyId);

	/**
	 * 判断租客配置是否存在
	 * @param configId 配置ID
	 * @param renterId 租客ID
	 * @return
	 */
	Boolean isConfigExist(Long configId, Long renterId);

	/**
	 * 租客配置新增
	 * @param req
	 */
	void saveConfig(SaveConfigReq req);

	/**
	 * 租客配置编辑
	 * @param req
	 */
	void updateConfig(SaveConfigReq req);

	/**
	 * 获取仪表费用占比总和
	 * @param id 公司仪表ID
	 * @return
	 */
	double rateSum(Long id);
	
	/**
	 * 查询租客指定仪表的费用占比
	 * @param renterMeterId  公司仪表ID
	 * @param renterId       租客ID
	 * @return
	 */
	double getRate(Long meterId, Long renterId);

	/**
	 * 获取租客配置新消息
	 * @param renterId  租客ID
	 * @return
	 */
	RenterConfigVO getRenterConfigVO(Long renterId);
	/**
	 * 获取租客配置新消息
	 * @param renterId
	 * @return
	 */
	RenterConfig getRenterConfig(Long renterId);

	/**
	 * 租客用电数据列表
	 * @param queryMap
	 * @param companyId 
	 * @param time 查询时间  yyyyMMdd    yyyyMM    yyyy
	 * @return
	 */
	PageResult<RenterDataVO> getDataList(HashMap<String, Object> queryMap, Long companyId, String time);

	/**
	 * 租客首页
	 * @param renterId 租客ID
	 * @param companyId 业主公司ID （指客户）
	 * @param balance 余额 
	 */
	HomeResult home(Long renterId, Long companyId, String balance);

	/**
	 * 查询租客关系列表
	 * @param uid 会员memberId
	 * @return
	 */
	List<RenterVO> getList(Long uid);

	/**
	 * 更新租客配置
	 * @param id  配置ID
	 * @param renterId 租客ID
	 * @param chargeMode 收费模式
	 * @param switchStatus 开合闸状态
	 */
	void updateConfig(Long id, Long renterId, Integer chargeMode, Integer switchStatus);
	/**
	 * 更新租客配置账单任务状态
	 * @param id
	 * @param billTaskStatus
	 * @return
	 */
	boolean updateConfigBillTaskStatus(Long id,Integer billTaskStatus);
	/**
	 * 更新租客配置账单任务信息
	 * @param renterConfig
	 * @return
	 */
	boolean updateConfigBillTaskInfo(RenterConfig renterConfig);
	/**
	 * 计算仪表本月电费
	 * @param rate  费用占比
	 * @param renterConfig 租客配置
	 * @param electric  ElectricChargeStat信息
	 * @return
	 */
	double getChargeOfThisMeter(double rate, RenterConfig renterConfig, ElectricChargeStat electric);
	
	/**
	 * 构建分项用电数据
	 * @param ut          分项用电
	 * @param subTermCode 分项信息
	 * @return
	 */
	UseType buildUserType(UseType ut, String subTermCode);
	
	/**
	 * 构建查询时间范围
	 * @param companyId    所属客户公司ID
	 * @param renterConfig 租客配置
	 * @param time         查询时间参数支持 null,yyyy,yyyyMM,yyyyMMdd四种 
	 * @return
	 */
	Map<String, Date> buildSearchTimeSpace(Long companyId, RenterConfig renterConfig, String time);
	
	/**
	 * 归并
	 * @param useTypes
	 * @return
	 */
	List<UseType> mergeUseTypes(List<UseType> useTypes);
	
	

	




}
