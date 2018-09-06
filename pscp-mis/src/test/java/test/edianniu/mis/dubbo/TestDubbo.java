/**
 * 
 */
package test.edianniu.mis.dubbo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.company.AddressInfo;
import com.edianniu.pscp.mis.bean.company.AuditReqData;
import com.edianniu.pscp.mis.bean.company.AuditResult;
import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.mis.bean.company.CompanyCustomerInfo;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.company.CompanySaveOrAuthReqData;
import com.edianniu.pscp.mis.bean.company.CompanySaveOrAuthResult;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.pay.MemberRefundInfo;
import com.edianniu.pscp.mis.bean.pay.MemberRefundStatus;
import com.edianniu.pscp.mis.bean.pay.QueryRefundsReq;
import com.edianniu.pscp.mis.bean.pay.QueryRefundsResult;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationListReq;
import com.edianniu.pscp.mis.bean.user.invitation.CompanyInvitationListResult;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationListReq;
import com.edianniu.pscp.mis.bean.user.invitation.ElectricianInvitationListResult;
import com.edianniu.pscp.mis.service.dubbo.CompanyCustomerInfoService;
import com.edianniu.pscp.mis.service.dubbo.CompanyInfoService;
import com.edianniu.pscp.mis.service.dubbo.PayInfoService;
import com.edianniu.pscp.mis.service.dubbo.UserInvitationInfoService;
import com.edianniu.pscp.mis.service.dubbo.WalletInfoService;
import com.google.common.collect.Lists;

/**
 * @author cyl
 * 
 */
public class TestDubbo {
	public static void testWalletInfoService(ApplicationContext ctx){
		WalletInfoService walletInfoService=(WalletInfoService)ctx.getBean("walletInfoService");
		Result result=walletInfoService.refundSocialWorkOrderDeposit("SGD3376064988024540");
		System.out.println(JSON.toJSONString(result));
	}
	
	public static void testPayInfoService(ApplicationContext ctx){
		PayInfoService payInfoService = (PayInfoService) ctx
				.getBean("payInfoService");
		QueryRefundsReq queryRefundsReq=new QueryRefundsReq();
		queryRefundsReq.setLimit(10);
		queryRefundsReq.setStatus(MemberRefundStatus.PROCESSING.getValue());
		QueryRefundsResult result=payInfoService.queryRefunds(queryRefundsReq);
		System.out.println(JSON.toJSONString(result));
		for(MemberRefundInfo refundInfo:result.getRefunds()){
			Result result1=payInfoService.checkRefundStatus(refundInfo.getId(), -1L);
			System.out.println("checkRefundStatus-"+refundInfo.getId()+":"+JSON.toJSONString(result1));
			Result result2=payInfoService.retryRefund(refundInfo.getId(), -1L);
			System.out.println("retryRefund-"+refundInfo.getId()+":"+JSON.toJSONString(result2));
		}
	}
	
	public static void testUserInvitation(ApplicationContext ctx){
		UserInvitationInfoService userInvitationInfoService = (UserInvitationInfoService) ctx
				.getBean("userInvitationInfoService");
		CompanyInvitationListReq companyInvitationListReq=new CompanyInvitationListReq();
		companyInvitationListReq.setCompanyId(1045L);
		companyInvitationListReq.setOffset(0);
		companyInvitationListReq.setPageSize(10);
		CompanyInvitationListResult companyListResult=userInvitationInfoService.list(companyInvitationListReq);
		System.out.println("companyListResult:"+JSON.toJSONString(companyListResult));
		ElectricianInvitationListReq electricianInvitationListReq=new ElectricianInvitationListReq();
		electricianInvitationListReq.setCompanyId(1047L);
		electricianInvitationListReq.setOffset(0);
		electricianInvitationListReq.setPageSize(10);
		ElectricianInvitationListResult electricianListResult=userInvitationInfoService.list(electricianInvitationListReq);
		System.out.println("electricianListResult:"+JSON.toJSONString(electricianListResult));
	}
	
	public static void testCompanyAudit(ApplicationContext ctx) {
		CompanyInfoService companyInfoService = (CompanyInfoService) ctx
				.getBean("companyInfoService");
		AuditReqData auditReqData = new AuditReqData();
		auditReqData.setAuthStatus(CompanyAuthStatus.SUCCESS.getValue());
		auditReqData.setCompanyId(1047L);
		auditReqData.setOpUser("admin");
		auditReqData.setRemark("审核成功");
		AuditResult result = companyInfoService.audit(auditReqData);
		if (result.isSuccess()) {
			System.out.println(JSON.toJSONString(result));
		} else {
			System.err.println(JSON.toJSONString(result));
		}
	}

	public static void testCompanySaveOrAuth(ApplicationContext ctx) {
		CompanyInfoService companyInfoService = (CompanyInfoService) ctx
				.getBean("companyInfoService");
		CompanySaveOrAuthReqData req = new CompanySaveOrAuthReqData();
		req.setActionType("auth");
		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.setName("测试电牛科技有限公司1");
		companyInfo.setWebsite("");
		companyInfo.setOrganizationCodeImg("organizationCodeImg1");
		List<CertificateImgInfo> certificateImages = Lists.newArrayList();
		CertificateImgInfo imgInfo = new CertificateImgInfo();
		imgInfo.setFileId("F1000000000004");
		imgInfo.setOrderNum(1);
		certificateImages.add(imgInfo);
		imgInfo = new CertificateImgInfo();
		imgInfo.setFileId("F1000000000003");
		imgInfo.setOrderNum(2);
		certificateImages.add(imgInfo);

		companyInfo.setCertificateImages(certificateImages);
		companyInfo.setBusinessLicence("businessLicence1");
		companyInfo.setBusinessLicenceImg("businessLicenceImg1");
		AddressInfo addressInfo=new AddressInfo();
		addressInfo.setProvinceId(1L);
		addressInfo.setCityId(1L);
		addressInfo.setAreaId(6L);
		addressInfo.setAddress("东信通讯科技园");
		companyInfo.setAddressInfo(addressInfo);
		companyInfo.setApplicationLetterFid("applicationLetterFid1");
		companyInfo.setContacts("联系人2");

		companyInfo.setMobile("13666688421");
		companyInfo.setPhone("0571-88373007");
		companyInfo.setOrganizationCodeImg("organizationCodeImg1");
		companyInfo.setUserName("申请人2");
		companyInfo.setIdCardBackImg("idCardBackImg");
		companyInfo.setIdCardFrontImg("idCardFrontImg");
		companyInfo.setIdCardNo("331021198309052817");
		req.setCompanyInfo(companyInfo);
		req.setUid(1184L);
		CompanySaveOrAuthResult result = companyInfoService.saveOrAuth(req);

		if (result.isSuccess()) {
			System.out.println(JSON.toJSONString(result));
		} else {
			System.err.println(JSON.toJSONString(result));
		}
	}
	
	public static void testGetCompanyCustomer(ApplicationContext ctx){
		CompanyCustomerInfoService companyCustomerInfoService = 
				(CompanyCustomerInfoService) ctx.getBean("companyCustomerInfoService");
		/*Long companyId = 1088L;
		Long memberId = null;
		List<CompanyCustomer> list = companyCustomerInfoService.getInfo(memberId, companyId);*/
		CompanyCustomerInfo byId = companyCustomerInfoService.getById(1111L);
		String jsonString = JSON.toJSONString(byId);
		System.out.println(jsonString);
	}
	
	
	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:Test.xml");
		testGetCompanyCustomer(ctx);
		
		/*
		 * AreaInfoService
		 * areaInfoService=(AreaInfoService)ctx.getBean("areaInfoService");
		 * List<ProvinceInfo> list= areaInfoService.getProvinceInfos();
		 * System.out.println(list); List<CityInfo> citys=
		 * areaInfoService.getCityInfos(2L); System.out.println(citys);
		 */

		/*
		 * AuditReqData auditReqData=new AuditReqData(); AuditResult
		 * auditResult=companyInfoService.audit(auditReqData);
		 * System.out.println(JSON.toJSONString(auditResult));
		 */
		/*
		 * ElectricianInfoService
		 * electricianInfoService=(ElectricianInfoService)
		 * ctx.getBean("electricianInfoService");
		 * com.edianniu.pscp.mis.bean.electrician.AuditReqData auditReqData2=new
		 * com.edianniu.pscp.mis.bean.electrician.AuditReqData();
		 * auditReqData2.setElectricianId(1067L);
		 * auditReqData2.setAuthStatus(2); List<ElectricianCertificateInfo>
		 * certificates=new ArrayList<>(); ElectricianCertificateInfo ecInfo=new
		 * ElectricianCertificateInfo(); ecInfo.setCertificateId(1000L);
		 * ecInfo.setElectricianId(1067L); ecInfo.setName("");
		 * ecInfo.setOrderNum(0); ecInfo.setRemark(""); ecInfo.setStatus(0);
		 * certificates.add(ecInfo); ecInfo=new ElectricianCertificateInfo();
		 * ecInfo.setCertificateId(1006L); ecInfo.setElectricianId(1067L);
		 * ecInfo.setName(""); ecInfo.setOrderNum(0); ecInfo.setRemark("");
		 * ecInfo.setStatus(0); certificates.add(ecInfo); ecInfo=new
		 * ElectricianCertificateInfo(); ecInfo.setCertificateId(1000L);
		 * ecInfo.setElectricianId(1067L); ecInfo.setName("");
		 * ecInfo.setOrderNum(0); ecInfo.setRemark(""); ecInfo.setStatus(0);
		 * certificates.add(ecInfo);
		 * auditReqData2.setCertificates(certificates);
		 * auditReqData2.setOpUser("admin"); auditReqData2.setRemark("");
		 * com.edianniu.pscp.mis.bean.electrician.AuditResult
		 * auditResult2=electricianInfoService.audit(auditReqData2);
		 * System.out.println(JSON.toJSONString(auditResult2));
		 */

	}

	private static byte[] getBytes(File file) throws IOException {

		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;
		byte[] buffer = new byte[1024 * 8];
		Random ran = new Random();

		String name = "F:/bank1/" + ran.nextInt(100000);

		try {
			in = new FileInputStream(file);

			out = new FileOutputStream(name);

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return buffer;

	}

}
