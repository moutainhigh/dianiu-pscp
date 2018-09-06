package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.ElectricianCertificate;

import java.util.List;
import java.util.Map;

public interface ElectricianCertificateDao {
	
	public List<ElectricianCertificate> getCertificatesByElectricianId(Long electricianId);
	
	public void save(ElectricianCertificate electricianCertificate);
	
	public void delete(Long id);

    List<Map<String,Object>> queryListMap(Long electricianId);
}
