package test.edianniu.mis.tcp.user;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.LoginRequest;
import com.edianniu.pscp.mis.bean.response.user.LoginResponse;

public class Login extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(Login.class);
//	@Test
	public void electricianApp() throws IOException {

		LoginRequest request = new LoginRequest();
		request.setLoginName("15961141110");//1043--企业电工 ;1034 13666688430--社会电工；13666688420 1201 66167131 
		request.setPasswd("111111");//25359721(1043)   65788394（1034），26227159（1095）
		request.setAppPkg("com.edianniu.pscp.electrician");
		LoginResponse resp=this.sendRequest(request, 1002001, LoginResponse.class);//1060(服务商) 82757554 
		log.debug("resp:{}",resp);

	}
	//@Test
	public void facilitatorApp() throws IOException {

		LoginRequest request = new LoginRequest();
		request.setLoginName("13666688421");//1043--企业电工 ;1034 13666688430--社会电工；13666688420 1095 
		request.setPasswd("123456");//25359721(1043)   65788394（1034），26227159（1095）
		request.setAppPkg("com.edianniu.pscp.facilitator");
		LoginResponse resp=this.sendRequest(request, 1002001, LoginResponse.class);//1060(服务商) 82757554 
		log.debug("resp:{}",resp);

	}
	@Test
	public void customerApp() throws IOException {

		LoginRequest request = new LoginRequest();
		request.setLoginName("17733333333");//1043--企业电工 ;1034 13666688430--社会电工；13666688420 1095
		request.setPasswd("123456");//25359721(1043)   65788394（1034），26227159（1095）
		request.setAppPkg("com.edianniu.pscp.customer");
		LoginResponse resp=this.sendRequest(request, 1002001, LoginResponse.class);//1060(服务商) 82757554 
		log.debug("resp:{}",resp);

	}

	

}
