/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:06:57
 * @version V1.0
 */
package com.edianniu.pscp.mis.service.impl;

import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianWorkStatus;
import com.edianniu.pscp.mis.bean.user.UserInfo;
import com.edianniu.pscp.mis.commons.Constants;
import com.edianniu.pscp.mis.dao.*;
import com.edianniu.pscp.mis.domain.*;
import com.edianniu.pscp.mis.service.ElectricianService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 下午3:06:57
 */
@Service
@Repository("electricianService")
public class DefaultElectricianService implements ElectricianService {

    @Autowired
    @Qualifier("electricianDao")
    private ElectricianDao electricianDao;
    @Autowired
    @Qualifier("certificateDao")
    private CertificateDao certificateDao;
    @Autowired
    @Qualifier("electricianCertificateDao")
    private ElectricianCertificateDao electricianCertificateDao;
    @Autowired
    @Qualifier("electricianWorkStatusLogDao")
    private ElectricianWorkStatusLogDao electricianWorkStatusLogDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberRoleDao memberRoleDao;
    @Autowired
    private ElectricianCertificateImgDao electricianCertificateImgDao;
    @Autowired
    private CompanyDao companyDao;

    @Override
    public ElectricianInfo getInfoByUid(Long uid) {
        Electrician bean = electricianDao.getByUid(uid);
        if (null == bean) {
        	ElectricianInfo info = new ElectricianInfo(); 
        	return info;
		}
        List<ElectricianCertificateImg> certificateImgs = electricianCertificateImgDao.getCertificateImgsByElectricianId(bean.getId());
        List<CertificateImgInfo> list = new ArrayList<CertificateImgInfo>();
        if (!certificateImgs.isEmpty() && certificateImgs.size() > 0) {
            for (ElectricianCertificateImg img : certificateImgs) {
                CertificateImgInfo imgInfo = new CertificateImgInfo();
                imgInfo.setFileId(img.getFileId());
                imgInfo.setOrderNum(img.getOrderNum());
                list.add(imgInfo);
            }
        }
        ElectricianInfo info = new ElectricianInfo();
        BeanUtils.copyProperties(bean, info);
        info.setCertificateImgs(list);
        info.setAuthStatus(bean.getStatus());
        if (bean.getCompanyId() == 0L) {
            info.setType(1);
        } else {
            Company company = companyDao.getById(info.getCompanyId());
            if (company != null) {
                info.setCompanyName(company.getName());
            }

            info.setType(2);
        }

        return info;
    }

    @Override
    public void updateWorkStatus(Long uid, Integer workStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("workStatus", workStatus);
        map.put("modifiedTime", new Date());
        map.put("modifiedUser", "系统");
        electricianDao.updateWorkTimeAndStatus(map);

    }

    @Override
    @Transactional
    public void updateWorkStatus(Electrician electrician, Integer workStatus) {
        updateWorkStatus(electrician.getMemberId(), workStatus);
        if (workStatus == ElectricianWorkStatus.ON_LINE.getValue()) {
            ElectricianWorkStatusLog electricianWorkStatusLog = new ElectricianWorkStatusLog();
            electricianWorkStatusLog.setElectricianId(electrician.getMemberId());
            electricianWorkStatusLog.setOnlineTime(new Date());
            electricianWorkStatusLog.setStatus(workStatus);
            electricianWorkStatusLogDao.save(electricianWorkStatusLog);
        } else if (workStatus == ElectricianWorkStatus.OFF_LINE.getValue()) {
            ElectricianWorkStatusLog electricianWorkStatusLog = electricianWorkStatusLogDao.getLatestByUid(electrician.getMemberId());
            if (electricianWorkStatusLog != null &&
                    electricianWorkStatusLog.getStatus() == ElectricianWorkStatus.ON_LINE.getValue()) {
                Date offlineTime = new Date();
                electricianWorkStatusLog.setOfflineTime(offlineTime);
                electricianWorkStatusLog.setStatus(workStatus);
                electricianWorkStatusLogDao.update(electricianWorkStatusLog);
                long workTime = (electrician.getWorkTime() == null ? 0L : electrician.getWorkTime()) +
                        offlineTime.getTime() - electricianWorkStatusLog.getOnlineTime().getTime();
                updateWorkTime(electrician.getMemberId(), workTime);
            }
        }
    }

    @Override
    public void updateWorkTime(Long uid, Long workTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("workTime", workTime);
        map.put("modifiedTime", new Date());
        map.put("modifiedUser", "系统");
        electricianDao.updateWorkTimeAndStatus(map);
    }

    @Override
    @Transactional
    public void save(Electrician electrician,
                     List<CertificateImgInfo> imgs) {
        save(electrician);
        for (CertificateImgInfo img : imgs) {
            ElectricianCertificateImg electricianCertificateImg = new ElectricianCertificateImg();
            electricianCertificateImg.setFileId(img.getFileId());
            electricianCertificateImg.setOrderNum(img.getOrderNum());
            electricianCertificateImg.setElectricianId(electrician.getId());
            electricianCertificateImg.setStatus(0);
            electricianCertificateImgDao.saveElectricianCertificateImg(electricianCertificateImg);
        }

    }

    @Override
    @Transactional
    public void save(Electrician electrician) {
        electricianDao.save(electrician);

    }

    @Override
    @Transactional
    public void update(Electrician electrician, List<CertificateImgInfo> imgs) {
        //现获取所有认证图片
        List<ElectricianCertificateImg> list = electricianCertificateImgDao.getCertificateImgsByElectricianId(electrician.getId());
        Map<String, Long> map = new HashMap<String, Long>();
        for (ElectricianCertificateImg img : list) {
            map.put(img.getFileId(), img.getId());
        }
        for (CertificateImgInfo img : imgs) {
            ElectricianCertificateImg bean = electricianCertificateImgDao.
                    getCertificateImgByElectricianIdAndFileId(electrician.getId(), img.getFileId());
            //没有记录就新增
            if (bean == null) {
                bean = new ElectricianCertificateImg();
                bean.setFileId(img.getFileId());
                bean.setOrderNum(img.getOrderNum());
                bean.setElectricianId(electrician.getId());
                bean.setStatus(0);
                electricianCertificateImgDao.saveElectricianCertificateImg(bean);
            } else {
                //有记录就移除
                map.remove(bean.getFileId());
            }
        }
        //余下全是被删除的认证图片
        for (Entry<String, Long> set : map.entrySet()) {
            Long id = set.getValue();
            electricianCertificateImgDao.deleteCertificateImg(id);
        }
        electricianDao.update(electrician);
    }

    @Override
    public Electrician getByUid(Long uid) {
        return electricianDao.getByUid(uid);
    }

    @Override
    public List<ElectricianCertificate> getCertificatesByElectricianId(
            Long electricianId) {
        return electricianCertificateDao.getCertificatesByElectricianId(electricianId);
    }

    @Override
    public ElectricianWorkStatusLog getLatestWorkStatusLogByUid(Long uid) {
        return electricianWorkStatusLogDao.getLatestByUid(uid);
    }

    @Override
    public Electrician getById(Long id) {

        return electricianDao.get(id);
    }

    @Override
    @Transactional
    public void bindCompany(Member member, Electrician electrician,
                            List<CertificateImgInfo> imgs) {
        if (electrician.getId() == null) {
            save(electrician, imgs);
        } else {
            update(electrician, imgs);
        }
        memberDao.updateUser(member);
    }

    @Override
    @Transactional
    public void unBindCompany(Member member, Electrician electrician) {
        electricianDao.update(electrician);
        memberDao.updateUser(member);
    }

    @Transactional
    @Override
    public void auditSuccess(Electrician electrician, UserInfo userInfo,
                             List<ElectricianCertificate> electricianCertificates) {
        electricianDao.update(electrician);
        Member member = new Member();
        member.setUid(userInfo.getUid());
        member.setAge(userInfo.getAge());
        member.setIsElectrician(userInfo.getIsElectrician());
        member.setSex(userInfo.getSex());
        memberDao.updateUser(member);
        for (ElectricianCertificate electricianCertificate : electricianCertificates) {
            electricianCertificateDao.save(electricianCertificate);
        }
        //删除服务商角色ID
        memberRoleDao.deleteByUidAndRoleId(userInfo.getUid(), Constants.FACILITATOR_ROLE_ID);

    }

    @Transactional
    @Override
    public void auditFail(Electrician electrician) {
        electricianDao.update(electrician);
    }

    @Override
    public boolean isElectricianMember(Long uid) {
        Electrician electrician = getByUid(uid);
        if (electrician == null) {
            return false;
        }
        if (electrician.getStatus() == ElectricianAuthStatus.AUTHING.getValue() ||
                electrician.getStatus() == ElectricianAuthStatus.SUCCESS.getValue()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existCertificate(Long certificateId) {
        Certificate certificate = certificateDao.get(certificateId);
        if (certificate != null) {
            return true;
        }
        return false;
    }

}
