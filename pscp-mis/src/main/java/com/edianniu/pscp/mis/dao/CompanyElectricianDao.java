package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.bean.history.vo.FacilitatorHistoryVO;
import com.edianniu.pscp.mis.bean.query.history.FacilitatorHistoryQuery;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationInfo;
import com.edianniu.pscp.mis.domain.CompanyElectrician;
import com.edianniu.pscp.mis.query.ElectricianInvitationQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CompanyElectricianDao
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月28日 上午10:49:00
 */
public interface CompanyElectricianDao extends BaseDao<CompanyElectrician> {

    CompanyElectrician getById(Long id);

    CompanyElectrician getByMobileAndCompanyId(@Param("mobile") String mobile,
                                               @Param("companyId") Long companyId);
    CompanyElectrician getByMemberIdAndCompanyId(@Param("memberId") Long memberId,
            @Param("companyId") Long companyId);

    int updateMemberId(@Param("id") Long id, @Param("memberId") Long memberId, @Param("modifiedUser") String modifiedUser);

    List<ElectricianInvitationInfo> queryElectricianInvitationInfoList(
            ElectricianInvitationQuery electricianInvitationQuery);

    int queryElectricianInvitationInfoCount(ElectricianInvitationQuery electricianInvitationQuery);

    int queryFacilitatorHistoryCount(FacilitatorHistoryQuery listQuery);

    List<FacilitatorHistoryVO> queryFacilitatorHistory(FacilitatorHistoryQuery listQuery);
}
