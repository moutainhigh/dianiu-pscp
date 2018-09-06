package com.edianniu.pscp.renter.mis.service.dubbo;

import java.util.List;

import com.edianniu.pscp.renter.mis.bean.Result;
import com.edianniu.pscp.renter.mis.bean.renter.ConfigDetailResult;
import com.edianniu.pscp.renter.mis.bean.renter.DataListReq;
import com.edianniu.pscp.renter.mis.bean.renter.DataListResult;
import com.edianniu.pscp.renter.mis.bean.renter.DetailReq;
import com.edianniu.pscp.renter.mis.bean.renter.DetailResult;
import com.edianniu.pscp.renter.mis.bean.renter.HomeReq;
import com.edianniu.pscp.renter.mis.bean.renter.HomeResult;
import com.edianniu.pscp.renter.mis.bean.renter.ListReq;
import com.edianniu.pscp.renter.mis.bean.renter.ListResult;
import com.edianniu.pscp.renter.mis.bean.renter.OrderDetailReq;
import com.edianniu.pscp.renter.mis.bean.renter.OrderDetailResult;
import com.edianniu.pscp.renter.mis.bean.renter.OrderListReq;
import com.edianniu.pscp.renter.mis.bean.renter.OrderListResult;
import com.edianniu.pscp.renter.mis.bean.renter.RenterMetersReq;
import com.edianniu.pscp.renter.mis.bean.renter.RenterMetersResult;
import com.edianniu.pscp.renter.mis.bean.renter.RentersResult;
import com.edianniu.pscp.renter.mis.bean.renter.SaveConfigReq;
import com.edianniu.pscp.renter.mis.bean.renter.SaveReq;
import com.edianniu.pscp.renter.mis.bean.renter.SaveResult;

/**
 * 租客管理
 * @author zhoujianjian
 * @date 2018年3月28日 下午6:27:31
 */
public interface RenterInfoService {
	/**
	 * 获取预付费租客Id列表
	 * @param limit
	 * @return
	 */
	List<Long> getPrePaymentRenterIds(int limit);
	/**
	 * 获取月结算租客Id列表
	 * @param limit
	 * @return
	 */
	List<Long> getMonthlyPaymentRenterIds(int limit);
	/**

	 */
	ListResult renterList(ListReq req);
	
	/**
	 * 门户租客管理--dubbo接口
	 * 新增、编辑租客
	 * @param req
	 */
	SaveResult saveRenter(SaveReq req);

	/**
	 * 门户租客管理--dubbo接口
	 * 判断租客姓名是否重复
	 * @param name      租客名称
	 * @param companyId 客户公司ID
	 */
	Boolean isRenterNameExist(String name, Long companyId);
	
	/**
	 * 门户租客管理--dubbo接口
	 * 判断用户是否已注册
	 * @param mobile 租客电话
	 */
	Boolean isUserExist(String mobile);

	/**
	 * 门户租客管理--dubbo接口
	 * 判断租客是否已经存在
	 * @param mobile    租客电话
	 * @param companyId 客户公司ID
	 */
	Boolean isRenterExit(String mobile, Long companyId);

	/**
	 * 门户租客管理--dubbo接口
	 * 租客详情
	 * @param req
	 */
	DetailResult getRenter(DetailReq req);

	/**
	 * 门户租客管理--dubbo接口
	 * 租客配置
	 * @param req
	 */
	SaveResult saveRenterConfig(SaveConfigReq req);

	/**
	 * 门户租客管理--dubbo接口
	 * 验证租客配置是否存在
	 * @param configId 配置ID
	 * @param renterId 租客ID
	 */
	Boolean isConfigExist(Long configId, Long renterId);

	/**
	 * 门户租客管理--dubbo接口
	 * 查询租客配置信息
	 */
	ConfigDetailResult getRenterConfig(DetailReq req);

	/**
	 * 租客用电数据列表
	 */
	DataListResult getDataList(DataListReq req);

	/**
	 * 租客账单列表
	 */
	OrderListResult getOrderList(OrderListReq req);

	/**
	 * 租客账单详情
	 */
	OrderDetailResult getOrderDetail(OrderDetailReq req);

	/**
	 * 租客首页
	 */
	HomeResult home(HomeReq req);

	/**
	 * 租客开合闸操作
	 * @param uid       客户uid
	 * @param token       token
	 * @param renterId  租客ID
	 * @param type      开合闸类型  0:开闸操作       1:合闸操作
	 * @param pwd       操作密码
	 * @param meterId   仪表ID，指公司仪表ID
	 */
	Result renterSwitch(Long uid,String token, Long renterId, Integer type, String pwd, Long meterId);
	
	/**
	 * 租客-房东关系对
	 * @param uid   租客uid
	 * @return
	 */
	RentersResult getRenters(Long uid);
	
	/**
	 * 获取租客仪表
	 * @param req
	 * @return
	 */
	RenterMetersResult getMeters(RenterMetersReq req);

	
	
}
