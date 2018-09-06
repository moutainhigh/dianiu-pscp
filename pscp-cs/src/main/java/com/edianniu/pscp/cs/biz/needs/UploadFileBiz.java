package com.edianniu.pscp.cs.biz.needs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.edianniu.pscp.cs.bean.needs.UploadFileReqData;
import com.edianniu.pscp.cs.bean.needs.UploadFileResult;
import com.edianniu.pscp.cs.bean.request.needs.UploadFileRequest;
import com.edianniu.pscp.cs.bean.response.needs.UploadFileResponse;
import com.edianniu.pscp.cs.biz.BaseBiz;
import com.edianniu.pscp.cs.service.dubbo.NeedsInfoService;
import com.edianniu.pscp.cs.commons.ResultCode;
import com.edianniu.pscp.mis.service.dubbo.UserInfoService;
import com.edianniu.pscp.mis.bean.Result;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnAccept;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;

/**
 * 合作附件上传、编辑
 * @author zhoujianjian
 * 2017年9月18日下午3:41:14
 */
public class UploadFileBiz extends BaseBiz<UploadFileRequest, UploadFileResponse> {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileBiz.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	@Autowired
	@Qualifier("needsInfoService")
	private NeedsInfoService needsInfoService;

	@StateTemplate(init = true)
	class RecvReq {
		RecvReq() {
		}

		@OnAccept
		Class<?> accept(FiniteStateMachine fsm, FSMContext ctx, UploadFileRequest req) {
			logger.debug("SaveNeedsRequest recv req : {}", req);
			UploadFileResponse resp = (UploadFileResponse) initResponse(ctx, req, new UploadFileResponse());

			Result isLogin = userInfoService.isLogin(req.getUid(), req.getToken());
			if (isLogin.getResultCode() != ResultCode.SUCCESS) {
				resp.setResultCode(isLogin.getResultCode());
				resp.setResultMessage(isLogin.getResultMessage());
				return SendResp.class;
			}

			UploadFileReqData uploadFileReqData = new UploadFileReqData();
			BeanUtils.copyProperties(req, uploadFileReqData);
			UploadFileResult result = needsInfoService.upload(uploadFileReqData);
			BeanUtils.copyProperties(result, resp);
			return SendResp.class;
			
		}
	}
}
