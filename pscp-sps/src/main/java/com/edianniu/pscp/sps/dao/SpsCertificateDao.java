package com.edianniu.pscp.sps.dao;

import com.edianniu.pscp.sps.domain.Certificate;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:42
 */
public interface SpsCertificateDao extends BaseDao<Certificate> {

    List<Certificate> queryAllList();

    List<Map<String, Object>> queryMapAllCertificate();
}
