package com.edianniu.pscp.portal.security;

import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.portal.entity.CompanyEntity;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.CompanyService;
import com.edianniu.pscp.portal.service.SysUserService;
import com.edianniu.pscp.portal.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 服务商是否认证通过拦截器
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月5日 下午3:23:42
 */
public class CompanyAuthFilter extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory
            .getLogger(CompanyAuthFilter.class);


    private String companyNotAuthUrl = "/notify/companyNotAuth.html";
    private String companyAuthingUrl = "/notify/companyAuthing.html";
    private String companyAuthFailUrl = "/notify/companyAuthFail.html";
    private String companyAuthAgainUrl = "/notify/companyAuthAgain.html";

    private List<String> noInteceptorUrls;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private SysUserService sysUserService;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (ShiroUtils.isLogin()) {// 已经登录
            if (!isNoInteceptorUrl(request)) {
                String uri = request.getRequestURI();
                logger.info("request uri:{}", uri);
                SysUserEntity user = sysUserService.getByUid(ShiroUtils.getUserId());
                if (user.isCustomer()) {
                	if(!ShiroUtils.getUserEntity().isCustomer()){
                        // 客户资质审核通过时(仅一次)，用数据库新的数据更新缓存当前登录用户信息
                        BeanUtils.copyProperties(user,ShiroUtils.getUserEntity());
                    }
				}
                if (user.isFacilitator()) {
                    if(!ShiroUtils.getUserEntity().isFacilitator()){
                        // 服务商资质审核通过时(仅一次)，用数据库新的数据更新缓存当前登录用户信息
                        BeanUtils.copyProperties(user,ShiroUtils.getUserEntity());
                    }
                    // 如果是服务商，认证过期 TODO 考虑年审的企业
                    CompanyEntity company = companyService
                            .getCompanyByCompanyId(user.getCompanyId());
                    if (company == null) {
                        String url = request.getContextPath() + companyNotAuthUrl;
                        response.sendRedirect(url);
                        return false;
                    }
                    if (company.getStatus() == CompanyAuthStatus.NOTAUTH.getValue()) {
                        String url = request.getContextPath() + companyAuthingUrl;
                        response.sendRedirect(url);
                        return false;
                    }
                    if (company.getStatus() == CompanyAuthStatus.AUTHING.getValue()) {
                        String url = request.getContextPath() + companyAuthingUrl;
                        response.sendRedirect(url);
                        return false;
                    }
                    if (company.getStatus() == CompanyAuthStatus.FAIL.getValue()) {
                        String url = request.getContextPath() + companyAuthFailUrl;
                        response.sendRedirect(url);
                        return false;
                    }
                }
                if (user.isNormalMember()) {
                    // 非服务商-普通用户
                    String redirectUrl = "";
                    CompanyEntity company = companyService.getSimpleCompanyByUid(ShiroUtils.getUserId());//此方法不会返回空
                    if (company == null) {
                        redirectUrl = this.companyNotAuthUrl;
                        response.sendRedirect(request.getContextPath()
                                + redirectUrl);
                        return false;
                    }
                    if (company.getStatus() == CompanyAuthStatus.NOTAUTH
                            .getValue()) {
                        redirectUrl = this.companyNotAuthUrl;

                    } else if (company.getStatus() == CompanyAuthStatus.FAIL
                            .getValue()) {
                        redirectUrl = this.companyAuthFailUrl;

                    } else if (company.getStatus() == CompanyAuthStatus.AUTHING
                            .getValue()) {
                        redirectUrl = this.companyAuthingUrl;

                    }
                    if (StringUtils.isNotBlank(redirectUrl)) {
                        response.sendRedirect(request.getContextPath()
                                + redirectUrl);
                        return false;
                    }
                }
            }
        }
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    private boolean isNoInteceptorUrl(HttpServletRequest request) {
        boolean isNoInterceptorUrl = false;
        String uri = request.getRequestURI();
        String path = request.getContextPath();
        uri = uri.replace(path, "");
        for (String regexp : noInteceptorUrls) {
            if (regexp.endsWith(".html")) {
                if (regexp.equals(uri)) {
                    isNoInterceptorUrl = true;
                    break;
                }
            } else {
                Matcher matcher = Pattern.compile(regexp).matcher(uri);
                if (matcher.find()) {
                    isNoInterceptorUrl = true;
                    break;
                }
            }

        }
        return isNoInterceptorUrl;
    }

    public List<String> getNoInteceptorUrls() {
        return noInteceptorUrls;
    }

    public void setNoInteceptorUrls(String urls) {
        this.noInteceptorUrls = new ArrayList<String>();
        for (String url : urls.split(",")) {
            this.noInteceptorUrls.add(url.trim());
        }
        this.noInteceptorUrls.add(companyAuthingUrl);
        this.noInteceptorUrls.add(companyNotAuthUrl);
        this.noInteceptorUrls.add(companyAuthFailUrl);
        this.noInteceptorUrls.add(companyAuthAgainUrl);
    }


}
