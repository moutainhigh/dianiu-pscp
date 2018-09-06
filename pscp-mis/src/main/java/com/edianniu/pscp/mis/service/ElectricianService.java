/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:23:25
 * @version V1.0
 */
package com.edianniu.pscp.mis.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.domain.Electrician;
import com.edianniu.pscp.mis.domain.ElectricianCertificate;
import com.edianniu.pscp.mis.domain.ElectricianWorkStatusLog;
import com.edianniu.pscp.mis.domain.Member;

/**
 * 电工服务类
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:23:25
 */
public interface ElectricianService {
    /**
     * 是否电工用户
     * 1）社会电工
     * 2）普通会员电工审核中
     *
     * @param uid
     * @return
     */
    public boolean isElectricianMember(Long uid);

    /**
     * 获取电工信息
     *
     * @param uid
     * @return
     */
    public ElectricianInfo getInfoByUid(Long uid);

    /**
     * 更新电工上线/下线状态
     *
     * @param uid
     * @param workStatus
     */
    public void updateWorkStatus(Long uid, Integer workStatus);

    /**
     * 更新电工上线/下线状态
     *
     * @param electrician
     * @param workStatus
     */
    public void updateWorkStatus(Electrician electrician, Integer workStatus);

    /**
     * 更新电工工作时间
     *
     * @param uid
     * @param workTime
     */
    public void updateWorkTime(Long uid, Long workTime);

    /**
     * 获取最新的电工工作状态日志
     *
     * @param uid
     * @return
     */
    public ElectricianWorkStatusLog getLatestWorkStatusLogByUid(Long uid);

    /**
     * 电工资料修改
     *
     * @param electrician
     */
    public void update(Electrician electrician, List<CertificateImgInfo> imgs);

    /**
     * 电工资料新增
     *
     * @param electrician
     * @param imgs
     */
    public void save(Electrician electrician, List<CertificateImgInfo> imgs);

    /**
     * 电工资料新增
     *
     * @param electrician
     */
    public void save(Electrician electrician);

    /**
     * 电工绑定企业
     *
     * @param member
     * @param electrician
     * @param imgs
     */
    public void bindCompany(Member member, Electrician electrician, List<CertificateImgInfo> imgs);

    /**
     * 电工解绑企业
     *
     * @param member
     * @param electrician
     * @param imgs
     */
    public void unBindCompany(Member member, Electrician electrician);

    /**
     * 社会电工审核成功
     *
     * @param electrician
     * @param userInfo
     * @param electricianCertificates
     */
    public void auditSuccess(Electrician electrician, UserInfo userInfo, List<ElectricianCertificate> electricianCertificates);

    /**
     * 电工审核失败
     *
     * @param electrician
     */
    public void auditFail(Electrician electrician);

    /**
     * 获取电工信息
     *
     * @param uid
     * @return
     */
    public Electrician getByUid(Long uid);

    /**
     * 获取电工信息
     *
     * @param id
     * @return
     */
    public Electrician getById(Long id);

    /**
     * 获取电工证书信息
     *
     * @param electricianId
     * @return
     */
    public List<ElectricianCertificate> getCertificatesByElectricianId(Long electricianId);

    /**
     * 是否存在证书
     *
     * @param certificateId
     * @return
     */
    public boolean existCertificate(Long certificateId);
}
