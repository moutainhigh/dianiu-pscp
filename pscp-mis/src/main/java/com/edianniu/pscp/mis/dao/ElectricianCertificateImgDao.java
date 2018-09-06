package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.ElectricianCertificateImg;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ElectricianCertificateImgDao {
	
	public List<ElectricianCertificateImg> getCertificateImgsByElectricianId(Long electricianId);
	
	public void saveElectricianCertificateImg(ElectricianCertificateImg bean);
	
	public void deleteCertificateImg(Long id);
	
	public ElectricianCertificateImg getCertificateImgByElectricianIdAndFileId
	(@Param("electricianId")Long electricianId,@Param("fileId")String fileId);

    List<Map<String,Object>> queryListMap(Long electricianId);
}
