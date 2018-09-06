package com.edianniu.pscp.mis.dao;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.mis.domain.Company;

/**
 * ClassName: CompanyDao
 * Author: tandingbo
 * CreateTime: 2017-04-16 15:06
 */
public interface CompanyDao {
    /**
     * 根据主键ID获取企业信息
     *
     * @param id
     * @return
     */
    Company getById(Long id);

    /**
     * 根据会员ID获取企业信息
     *
     * @param memberId
     * @return
     */
    Company getByMemberId(Long memberId);
    
    /**
     * 根据企业名字查询
     *
     * @param company
     * @return
     */
    public Company getByName(String name);
    /**
     * 查询数量
     * @param name
     * @return
     */
    public int getCountByName(@Param("name")String name,@Param("statuss")int statuss[]);
    /**
     * 保存
     * @param company
     */
    public void save(Company company);
    /**
     * 更新
     * @param company
     */
    public void update(Company company);
}
