package com.edianniu.pscp.cms.controller.member;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.cms.controller.AbstractController;
import com.edianniu.pscp.cms.entity.MemberWallet;
import com.edianniu.pscp.cms.entity.MemberWalletDetail;
import com.edianniu.pscp.cms.service.MemberWalletDetailService;
import com.edianniu.pscp.cms.service.MemberWalletService;
import com.edianniu.pscp.cms.utils.DateUtils;
import com.edianniu.pscp.cms.utils.JsonResult;
import com.edianniu.pscp.cms.utils.PageUtils;
import com.edianniu.pscp.cms.utils.R;
import com.edianniu.pscp.mis.bean.WalletType;
import com.edianniu.pscp.mis.bean.wallet.BankType;
import com.edianniu.pscp.mis.bean.wallet.WithdrawalscashAuditReqData;
import com.edianniu.pscp.mis.bean.wallet.WithdrawalscashAuditResult;
import com.edianniu.pscp.mis.bean.wallet.WithdrawalscashPayConfirmReqData;
import com.edianniu.pscp.mis.bean.wallet.WithdrawalscashPayConfirmResult;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
/**
 * 会员钱包
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-19 11:23:06
 */
@Controller
@RequestMapping("/member/wallet")
public class MemberWalletController extends AbstractController{
	private static final Logger logger = LoggerFactory
			.getLogger(MemberWalletController.class);
	@Autowired
	private MemberWalletService memberWalletService;
	@Autowired
	private WalletInfoService walletInfoService;
	@Autowired
	private MemberWalletDetailService memberWalletDetailService;
	@RequestMapping("/wallet.html")
	public String wallet(){
		return "member/wallet/wallet.html";
	}
	@RequestMapping("/recharge.html")
	public String recharge(){
		return "member/wallet/recharge.html";
	}
	@RequestMapping("/details.html")
	public String details(){
		return "member/wallet/details.html";
	}
	//财务管理(提现)
	@RequestMapping("/withdrawalscash.html")
	public String list(){
	   return "member/wallet/withdrawalscash.html";
	}
	
	/**
	 * 会员资金列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("member:wallet:list")
	public R list(Integer page, Integer limit,String mobile,String loginName,String memberName){
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("mobile", mobile);
		map.put("memberName", memberName);
		map.put("loginName", loginName);
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		//查询列表数据
		List<MemberWallet>list = memberWalletService.queryList(map);
		int count=memberWalletService.queryTotal(map);		
		PageUtils pageUtil = new PageUtils(list, count, limit, page);
		R r=R.ok();
		r.put("page", pageUtil);
		return r;
	}
	/**
	 * 会员提现列表
	 */
	@ResponseBody
	@RequestMapping("/withdrawalscash/list")
	@RequiresPermissions("member:wallet:details:withdrawalscash:list")
	public R withdrawalsList(Integer page, Integer limit,String orderId,String mobile,String loginName,String memberName,Integer status,Integer bankId,String createTimeStart,String createTimeEnd){
		Map<String,Object>map=new HashMap<String,Object>();
		if(StringUtils.isNoneBlank(createTimeStart))
		map.put("createTimeStart", DateUtils.parse(createTimeStart+" 00:00:00", DateUtils.DATE_TIME_PATTERN));
		if(StringUtils.isNoneBlank(createTimeEnd))
		map.put("createTimeEnd", DateUtils.parse(createTimeEnd+" 23:59:59", DateUtils.DATE_TIME_PATTERN));
		map.put("mobile", mobile);
		map.put("orderId", orderId);
		map.put("status", status);
		map.put("bankId", bankId);
		map.put("memberName", memberName);
		map.put("loginName", loginName);
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		//查询列表数据
		List<MemberWalletDetail>list = memberWalletDetailService.queryWithdrawalsList(map);
		int count=memberWalletDetailService.queryWithdrawalsTotal(map);
		PageUtils pageUtil = new PageUtils(list, count, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 充值列表
	 */
	@ResponseBody
	@RequestMapping("/recharge/list")
	@RequiresPermissions("member:wallet:details:recharge:list")
	public R rechargeList(Integer page, Integer limit,String orderId,String mobile,String loginName,String memberName,String createTimeStart,String createTimeEnd){
		Map<String,Object>map=new HashMap<String,Object>();
		if(StringUtils.isNoneBlank(createTimeStart))
		map.put("createTimeStart", DateUtils.parse(createTimeStart+" 00:00:00", DateUtils.DATE_TIME_PATTERN));
		if(StringUtils.isNoneBlank(createTimeEnd))
		map.put("createTimeEnd", DateUtils.parse(createTimeEnd+" 23:59:59", DateUtils.DATE_TIME_PATTERN));
		map.put("mobile", mobile);
		map.put("orderId", orderId);
		map.put("memberName", memberName);
		map.put("loginName", loginName);
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		map.put("type", WalletType.RECHARGE.getValue());
		//查询列表数据
		List<MemberWalletDetail>list = memberWalletDetailService.queryList(map);
		int count=memberWalletDetailService.queryTotal(map);		
		PageUtils pageUtil = new PageUtils(list, count, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	//资金列表
	@ResponseBody
	@RequestMapping("/details/list")
	@RequiresPermissions("member:wallet:details:list")
	public R detailsList(Integer page, Integer limit,String orderId,
			String mobile,String loginName,String memberName,String createTimeStart,String createTimeEnd){
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isNoneBlank(createTimeStart))
		map.put("createTimeStart", DateUtils.parse(createTimeStart+" 00:00:00", DateUtils.DATE_TIME_PATTERN));
		if(StringUtils.isNoneBlank(createTimeEnd))
		map.put("createTimeEnd", DateUtils.parse(createTimeEnd+" 23:59:59", DateUtils.DATE_TIME_PATTERN));
		map.put("mobile", mobile);
		map.put("orderId", orderId);
		map.put("memberName", memberName);
		map.put("loginName", loginName);
		
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		//查询列表数据
		List<MemberWalletDetail>list = memberWalletDetailService.queryList(map);
		int count=memberWalletDetailService.queryTotal(map);		
		PageUtils pageUtil = new PageUtils(list, count, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("member:wallet:update")
	public JsonResult update(@RequestBody MemberWallet bean){
		JsonResult json=new JsonResult();
		try{
			memberWalletService.update(bean);
			 json.setObject(bean);
			 json.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("系统异常");
			json.setSuccess(false);
		}
				
		return json;
		
	}
	
	/**
	 * 获取银行列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getbanks")
	@RequiresPermissions("member:wallet:getbanks")
	public R getBanks(){
		try{
			List<BankType> banks=walletInfoService.getBanks();
			return R.ok().put("banks", banks);				
		}
		catch(Exception e){
			logger.error("getbanks:{}",e);
		}
		return R.error("系统异常");
		
	}
	@ResponseBody
	@RequestMapping("/info")
	@RequiresPermissions("member:wallet:info")
	public R info(@RequestBody Long id){
		MemberWallet memberWallet=memberWalletService.queryObject(id);
		return R.ok().put("memberWallet", memberWallet);
	}
	@ResponseBody
	@RequestMapping("/detail/info")
	@RequiresPermissions("member:wallet:detail:info")
	public R detailinfo(@RequestBody Long id){
		MemberWalletDetail memberWalletDetail=memberWalletDetailService.getById(id);
		return R.ok().put("memberWalletDetail", memberWalletDetail);
	}
	@ResponseBody
	@RequestMapping("/withdrawalscash/info")
	@RequiresPermissions("member:wallet:withdrawalscash:info")
	public R withdrawalscashinfo(@RequestBody Long id){
		MemberWalletDetail memberWalletDetail=memberWalletDetailService.getWithdrawalsById(id);
		return R.ok().put("memberWalletDetail", memberWalletDetail);
	}
	/**
	 * 提现审核
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/withdrawalscash/audit")
	@RequiresPermissions("member:wallet:withdrawalscash:audit")
	public R withdrawalscashAudit(@RequestBody WithdrawalscashAuditReqData withdrawalscashAuditReqData){
		withdrawalscashAuditReqData.setOpUser(getUser().getLoginName());
		WithdrawalscashAuditResult result=walletInfoService.withdrawalscashAudit(withdrawalscashAuditReqData);
		if(!result.isSuccess()){
			return R.error(result.getResultMessage());
		}
		return 	R.ok();
	}

	/**
	 * 提现打款确认
	 */
	@ResponseBody
	@RequestMapping("/withdrawalscash/payconfirm")
	@RequiresPermissions("member:wallet:withdrawalscash:payconfirm")
	public R withdrawalscashPayconfirm(@RequestBody WithdrawalscashPayConfirmReqData withdrawalscashPayConfirmReqData){
		withdrawalscashPayConfirmReqData.setOpUser(getUser().getLoginName());
		withdrawalscashPayConfirmReqData.setPayTime(new Date());
		WithdrawalscashPayConfirmResult result=walletInfoService.withdrawalscashPayConfirm(withdrawalscashPayConfirmReqData);
		if(!result.isSuccess()){
			return R.error(result.getResultMessage());
		}
		return R.ok();
	}
	
}
