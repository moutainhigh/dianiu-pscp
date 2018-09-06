package com.edianniu.pscp.portal.controller.sys;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.edianniu.pscp.portal.utils.PasswordUtil;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 登录相关
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 上午10:40:10
 */
@Controller
public class LoginController {
	@Autowired
	private Producer producer;
	
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password, String anchor, String captcha, HttpServletRequest request)throws IOException {
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}
		
		
		try{
			Subject subject = ShiroUtils.getSubject();
			//sha256加密 new Sha256Hash(password).toHex();
			password = PasswordUtil.encode(password);
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error(e.getMessage());
		}catch (LockedAccountException e) {
			return R.error(e.getMessage());
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
		String redirectUrl="main.html";
		SavedRequest saveRequest=WebUtils.getSavedRequest(request);
		if(saveRequest!=null){
			String requestUri=saveRequest.getRequestURI();
			String queryString=saveRequest.getQueryString();
			if(StringUtils.isNoneBlank(requestUri)){
				requestUri=requestUri.substring(1, requestUri.length());
			}
			if(requestUri.endsWith(".html")){
				if(requestUri.equals("main.html")){
					redirectUrl=redirectUrl+(StringUtils.isNoneBlank(queryString)?"?"+queryString:"")+anchor;
				}
				else{
					redirectUrl=redirectUrl+(StringUtils.isNoneBlank(queryString)?"?"+queryString:"")+"#"+requestUri;
				}
			}
			
		}
		return R.ok().put("redirectUrl", redirectUrl);
	}
	
	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:index.html";
	}
	
}
