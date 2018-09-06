package com.edianniu.pscp.renter.mis.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.renter.mis.bean.response.BaseResponse;
import com.edianniu.pscp.renter.mis.commons.Constants;
import stc.skymobi.bean.AbstractCommonBean;
import stc.skymobi.ebus.EventBus;
import stc.skymobi.fsm.FSMContext;
import stc.skymobi.fsm.FiniteStateMachine;
import stc.skymobi.fsm.tmpl.annotation.OnEnter;
import stc.skymobi.fsm.tmpl.annotation.StateTemplate;
import stc.skymobi.netty.transport.Sender;
import stc.skymobi.netty.transport.TransportUtils;

public abstract class BaseBiz<Q extends AbstractCommonBean, T extends BaseResponse> {
	private static final Logger logger = LoggerFactory.getLogger(BaseBiz.class);
	protected static Logger timeoutLogger = LoggerFactory
			.getLogger("timeoutLogger");

	protected EventBus eventBus;
	protected long timeout = 5000;

	protected String eventToMktLog = "event.sendTo.mktLog";
	protected String eventToMktMis = "event.sendTo.mktMis";
	protected String eventToAppService = "event.sendTo.appService";

	protected T initResponse(FSMContext ctx, Q request, T response) {
		response.setIdentification(request.getIdentification());
		setRequest(ctx, request);
		setResponse(ctx, response);
		return response;
	}

	@StateTemplate
	protected class SendResp {
		@OnEnter
		boolean enter(FiniteStateMachine fsm, FSMContext ctx) {
			// 处理向client返回
			if (getResponse(ctx) != null) {

				Q request = getRequest(ctx);
				T response = getResponse(ctx);

				// send response
				Sender sender = TransportUtils.getSenderOf(request);
				if (logger.isDebugEnabled()) {
					logger.debug("SendClientRespState={}", response);
				}

				if (sender != null) {
					sender.send(response);
				} else {
					logger.error("getSender error!");
				}

				ctx.setEndReason(request.getClass().getSimpleName() + "_"
						+ response.getResultCode());
				return false;
			} else {
				logger.error("getResponse error!");
				return false;
			}
		}
	}

	protected void saveLog(Object logObject) {// TODO
		// eventBus.fireEvent(eventToMktLog, new MktLog(logObject));
	}

	@SuppressWarnings("unchecked")
	protected Q getRequest(FSMContext ctx) {
		return (Q) ctx.getProperty(Constants.FSM_CONTEXT_REQ);
	}

	protected void setRequest(FSMContext ctx, Q request) {
		ctx.setProperty(Constants.FSM_CONTEXT_REQ, request);
	}

	@SuppressWarnings("unchecked")
	protected T getResponse(FSMContext ctx) {
		return (T) ctx.getProperty(Constants.FSM_CONTEXT_REP);
	}

	protected void setResponse(FSMContext ctx, T response) {
		ctx.setProperty(Constants.FSM_CONTEXT_REP, response);
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getEventToMktLog() {
		return eventToMktLog;
	}

	public void setEventToMktLog(String eventToMktLog) {
		this.eventToMktLog = eventToMktLog;
	}

	public String getEventToMktMis() {
		return eventToMktMis;
	}

	public void setEventToMktMis(String eventToMktMis) {
		this.eventToMktMis = eventToMktMis;
	}
}
