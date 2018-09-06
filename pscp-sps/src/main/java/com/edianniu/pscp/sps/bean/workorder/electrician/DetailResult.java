/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午5:59:39
 * @version V1.0
 */
package com.edianniu.pscp.sps.bean.workorder.electrician;

import com.edianniu.pscp.mis.bean.defectrecord.vo.DefectRecordVO;
import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.evaluate.electrician.VO.EvaluateVO;
import com.edianniu.pscp.sps.bean.workorder.chieforder.vo.CompanyVO;
import com.edianniu.pscp.sps.bean.workorder.worklog.ElectricianWorkLogInfo;

import java.util.List;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月11日 下午5:59:39
 */
public class DetailResult extends Result {
    private static final long serialVersionUID = 1L;
    private OrderDetailInfo orderDetail;
    private CompanyVO companyInfo;
    private CompanyVO customerInfo;
    private EvaluateVO evaluateInfo;
    private SettlementInfo settlementInfo;
    private List<ElectricianWorkLogInfo> workLogList;
    /* 修复缺陷记录.*/
    private List<DefectRecordVO> defectRepairList;


    public OrderDetailInfo getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailInfo orderDetail) {
        this.orderDetail = orderDetail;
    }

    public CompanyVO getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyVO companyInfo) {
        this.companyInfo = companyInfo;
    }

    public EvaluateVO getEvaluateInfo() {
        return evaluateInfo;
    }

    public void setEvaluateInfo(EvaluateVO evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }

    public SettlementInfo getSettlementInfo() {
        return settlementInfo;
    }

    public void setSettlementInfo(SettlementInfo settlementInfo) {
        this.settlementInfo = settlementInfo;
    }

    public CompanyVO getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CompanyVO customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<ElectricianWorkLogInfo> getWorkLogList() {
        return workLogList;
    }

    public void setWorkLogList(List<ElectricianWorkLogInfo> workLogList) {
        this.workLogList = workLogList;
    }

    public List<DefectRecordVO> getDefectRepairList() {
        return defectRepairList;
    }

    public void setDefectRepairList(List<DefectRecordVO> defectRepairList) {
        this.defectRepairList = defectRepairList;
    }
}
