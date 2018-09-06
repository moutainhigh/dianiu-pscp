/**
 *
 */
package com.edianniu.pscp.message.push.service;

import com.edianniu.pscp.message.commons.Result;
import com.edianniu.pscp.message.push.domain.PushMessage;

import java.util.List;

/**
 * @author cyl
 */
public interface PushService {
    /**
     * 推送消息
     *
     * @param clientId
     * @param message
     * @return
     */
    public Result pushMessageToSingle(PushMessage message,String appType,String clientId);

    /**
     * 推送消息
     *
     * @param message
     * @return
     */
    public Result pushMessageToList(PushMessage message,String appType, List<String> clientIds);
    /**
     * 推送消息
     * @param message
     * @param appType
     * @return
     */
    public Result pushMessageToApp(PushMessage message,String appType);
    
    public void push(String appType,String clientId);
}
