package com.edianniu.pscp.message.getuiclient.service;

import com.edianniu.pscp.message.getuiclient.domain.GeTuiClient;

/**
 * @author cyl
 */
public interface GeTuiClientService {
    public GeTuiClient getClientId(Long uid);

    public boolean bindClient(GeTuiClient geTuiClient);
}
