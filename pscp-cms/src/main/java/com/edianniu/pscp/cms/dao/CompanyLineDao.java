package com.edianniu.pscp.cms.dao;

import com.edianniu.pscp.cms.entity.CompanyLineEntity;

public interface CompanyLineDao extends BaseDao<CompanyLineEntity>{

	int getCountChildLines(Long id);

}
