package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.Certificate;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CertificateDao extends BaseDao<Certificate>{
	List<Certificate> getCertificateListByIds(@Param("ids")Long ids[]);
	
	List<Certificate> getAllCertificateList();

    List<Certificate> queryAllList();
}
