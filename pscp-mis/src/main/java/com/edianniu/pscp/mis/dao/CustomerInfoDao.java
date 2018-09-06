/**
 * 
 */
package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.Company;

/**
 * @author cyl
 *
 */
public interface CustomerInfoDao {
	
	
	public Company getById(Long id);
	
	public Company getByMemberId(Long memberId);
	
}
