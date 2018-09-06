package com.edianniu.pscp.cs.service;

import com.edianniu.pscp.cs.bean.needs.Attachment;
import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.needs.vo.NeedsVO;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderListVO;
import com.edianniu.pscp.cs.bean.needsorder.vo.NeedsOrderVO;
import com.edianniu.pscp.cs.bean.query.NeedsOrderListQuery;
import com.edianniu.pscp.cs.commons.PageResult;
import com.edianniu.pscp.cs.domain.CustomerNeedsOrder;
import com.edianniu.pscp.cs.domain.Needs;
import com.edianniu.pscp.mis.bean.user.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-09-21 15:52:07
 */
public interface CustomerNeedsOrderService {

    NeedsOrderInfo getEntityById(Map<String, Object> queryMap);

    List<NeedsOrderVO> selectNeedsOrderByCondition(Map<String, Object> queryMap);

    PageResult<NeedsOrderListVO> selectPageNeedsOrder(NeedsOrderListQuery listQuery);

    CustomerNeedsOrder save(UserInfo userInfo, Needs needs);

    void updateEntityById(CustomerNeedsOrder needsOrder, List<Attachment> attachmentList, UserInfo userInfo, NeedsVO needsVO);

    void updateBatchNeedsOrderVO(List<NeedsOrderVO> needsOrderVOList);

	List<Long> getOvertimeAndUnPayNeedsorders(int minutes);

	int handleOvertimeAndUnPayNeedsorders(Long id);

    List<Map<String,Object>> selectSendMessagePushInfo(Long needsId);

    Map<String,Object> getMapSendMessagePushInfo(Long id);

	List<String> getNotGoWellNeedsorders(int limit);
}
