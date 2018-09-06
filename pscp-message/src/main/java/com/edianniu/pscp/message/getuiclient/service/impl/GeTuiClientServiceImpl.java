package com.edianniu.pscp.message.getuiclient.service.impl;

import com.edianniu.pscp.message.getuiclient.service.GeTuiClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.message.getuiclient.dao.GeTuiClientDao;
import com.edianniu.pscp.message.getuiclient.domain.GeTuiClient;
import com.edianniu.pscp.message.getuiclient.domain.GeTuiClientDetail;

@Service
@Repository("geTuiClientService")
public class GeTuiClientServiceImpl implements GeTuiClientService {
    private static final Logger logger = LoggerFactory
            .getLogger(GeTuiClientServiceImpl.class);
    @Autowired
    private GeTuiClientDao geTuiClientDao;

    @Override
    public GeTuiClient getClientId(Long uid) {
        return geTuiClientDao.getClientIdByUid(uid);
    }
    
    @Override
    @Transactional
    public boolean bindClient(GeTuiClient geTuiClient) {
        try {
            GeTuiClient client = geTuiClientDao.getByUid(geTuiClient.getUid());
            if (client == null) {
                geTuiClientDao.save(geTuiClient);
            } else {
                geTuiClient.setId(client.getId());
                geTuiClientDao.update(geTuiClient);
            }
            GeTuiClientDetail geTuiClientDetail = new GeTuiClientDetail();
            BeanUtils.copyProperties(geTuiClient, geTuiClientDetail);
            geTuiClientDao.saveDetail(geTuiClientDetail);
            return true;
        } catch (Exception e) {
            logger.error("bindClient:{}", e);
        }

        return false;
    }

}
