package test.edianniu.mis.tcp.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.request.company.CompanySaveOrAuthRequest;
import com.edianniu.pscp.mis.bean.response.company.CompanySaveOrAuthResponse;

public class CompanySaveOrAuth extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(CompanySaveOrAuth.class);


	@Test
	public void test() throws IOException {

		CompanySaveOrAuthRequest request = new CompanySaveOrAuthRequest();
		request.setUid(1276L);
		
		request.setToken("85195837");
		request.setAddress("擦擦擦");
		request.setApplicationLetterFid("img1/M01/00/0C/wKgB-1n4Gm-AAvZhAAa7a62MwzE764.jpg");
		
		request.setBusinessLicence("325446888");
		request.setBusinessLicenceImg("img1/M00/00/0C/wKgB-1n4GhOAF5TrAALMSoKWyh4534.jpg");
		
		request.setContacts("啊啊啊啊");
		request.setEmail("1575287113@qq.com");
		request.setLegalPerson("杜牧");
		request.setMobile("13666688420");
		request.setName("测试题");
		request.setType(1);
		request.setWebsite("擦擦擦");
		request.setUserName("13666677004");
		request.setProvinceId(2L);
		request.setCityId(2L);
		request.setAreaId(20L);
		request.setPhone("057188742563");
		request.setIdCardBackImg("img1/M01/00/05/bwERxVaNI8KAc3ijAAEy9JRV1Bg694..jpg");
		request.setIdCardFrontImg("img1/M00/00/0C/wKgB-1n4GmmATVqRAAEkb8-cNr0274.jpg");
		request.setOrganizationCodeImg("img1/M01/00/0C/wKgB-1n4GhiAfCEnAAXkSLWodbo358.jpg");
		List<CertificateImgInfo>list=new ArrayList<CertificateImgInfo>();
		CertificateImgInfo img1=new CertificateImgInfo();
		img1.setFileId("img1/M00/00/0C/wKgB-1n4GiCAelJTAAXkSLWodbo533.jpg");
		img1.setOrderNum(0);
		list.add(img1);
		CertificateImgInfo img2=new CertificateImgInfo();
		img2.setFileId("img1/M01/00/0C/wKgB-1n4GiaAHbSLAAZJv3hk53c907.jpg");
		img2.setOrderNum(1);
		list.add(img2);
		CertificateImgInfo img3=new CertificateImgInfo();
		img3.setFileId("img1/M00/00/0C/wKgB-1n4Gi6AMJMUAAZCUdIavQY789.jpg");
		img3.setOrderNum(2);
		list.add(img3);
		request.setCertificateImages(list);
		request.setIdCardNo("420983198909197330");
		request.setUserName("陈炎林");
		request.setActionType("auth");
		request.setAppPkg("com.edianniu.pscp.facilitator");
		CompanySaveOrAuthResponse resp=this.sendRequest(request, 1002010, CompanySaveOrAuthResponse.class);
		log.info("resp:{}",resp);

	}

}
