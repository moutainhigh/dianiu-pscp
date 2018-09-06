package com.edianniu.pscp.mis.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.mis.bean.EngineeringProjectStatus;
import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.pay.PayStatus;
import com.edianniu.pscp.mis.dao.EngineeringProjectDao;
import com.edianniu.pscp.mis.dao.NeedsOrderDao;
import com.edianniu.pscp.mis.domain.EngineeringProject;
import com.edianniu.pscp.mis.domain.NeedsOrder;
import com.edianniu.pscp.mis.domain.PayOrder;
import com.edianniu.pscp.mis.service.EngineeringProjectService;
/**
 * DefaultProjectService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午5:42:13 
 * @version V1.0
 */
@Service
@Repository("engineeringProjectService")
public class DefaultEngineeringProjectService implements EngineeringProjectService {
	@Autowired
    private EngineeringProjectDao engineeringProjectDao;
	@Autowired
	private NeedsOrderDao needsOrderDao;
	
	@Override
	public EngineeringProject getById(Long id) {
		return engineeringProjectDao.getById(id);
	}

	@Override
	public EngineeringProject getByProjectNo(String projectNo) {
		return engineeringProjectDao.getByProjectNo(projectNo);
	}
	@Override
	public String getRoomIdsById(Long id) {	
		return engineeringProjectDao.getRoomIdsById(id);
	}

	@Override
	@Transactional
	public boolean updatePayStatus(PayOrder payOrder) {
		if (payOrder.getOrderType() == OrderType.PROJECT_SETTLEMENT.getValue()) {
            String[] orderIds = StringUtils.split(payOrder.getAssociatedOrderIds(), ",");
            for (String orderId : orderIds) {
            	EngineeringProject engineeringProject = this.getByProjectNo(orderId);
                if (engineeringProject != null && engineeringProject.getStatus() == EngineeringProjectStatus.COST_TO_BE_CONFIRMED.getValue()) {
                	if((engineeringProject.getPayStatus()==PayStatus.INPROCESSING.getValue()&&payOrder.getStatus()!=PayStatus.SUCCESS.getValue())
                			||(engineeringProject.getPayStatus()==PayStatus.SUCCESS.getValue())){
                		continue;
                	}
                	if(payOrder.getStatus()==PayStatus.SUCCESS.getValue()){
                		engineeringProject.setStatus(EngineeringProjectStatus.SETTLED.getValue());
                	}
                    //支付订单信息的支付信息同步到工单里
                    engineeringProject.setPayAmount(engineeringProject.getActualSettlementAmount());
                    engineeringProject.setPayMemo(payOrder.getMemo());
                    engineeringProject.setPayStatus(payOrder.getStatus());
                    engineeringProject.setPaySyncTime(payOrder.getPaySyncTime());
                    engineeringProject.setPayAsyncTime(payOrder.getPayAsyncTime());
                    engineeringProject.setPayTime(payOrder.getPayTime());
                    engineeringProject.setPayType(payOrder.getPayType());
                    int num = update(engineeringProject);
                    if (num == 0) {
                        return false;
                    }
                    //refundNeedsOrder(engineeringProject.getNeedsId(),engineeringProject.getCompanyId());
                }
            }
        }
        return true;
	}
	/**
	 * 返还保证金到余额
	 * @param needsId
	 * @param companyId
	 */
	private void refundNeedsOrder(Long needsId,Long companyId){
		NeedsOrder needsOrder=needsOrderDao.getByNeedsIdAndCompanyId(needsId, companyId);
	}

	@Override
	public int update(EngineeringProject engineeringProject) {
		
		return engineeringProjectDao.update(engineeringProject);
	}

}
