package com.edianniu.pscp.portal.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class WebUtils {
    public static ThreadLocal<Exception> exLocal = new ThreadLocal<Exception>();


    public static SessionInfo getSessionInfo() {
        HttpSession session = getSession();
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        return sessionInfo;
    }

    public static HttpSession getSession() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return session;

    }
    public static HttpServletRequest getHttpServletRequest(){
    	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	 return request;
    }
    public static void clearSessionInfo(){
    	 HttpSession session = getSession();
         if(session!=null){
        	 /*
         	  RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
              if(attributes!=null){
            	  
            	  attributes.removeAttribute(AbstractCasFilter.CONST_CAS_ASSERTION, RequestAttributes.SCOPE_SESSION);
              }
              */
             session.removeAttribute("sessionInfo");
         }
    }
    public static void setSessionInfo(SessionInfo sessionInfo) {

        HttpSession session = getSession();
        if(sessionInfo!=null){
        	/*
        	RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        	if(attributes!=null){
        		  Assertion assertion=new AssertionImpl(sessionInfo.getLoginName());
                  attributes.setAttribute(AbstractCasFilter.CONST_CAS_ASSERTION, assertion,RequestAttributes.SCOPE_SESSION);
        	}
        	*/
            session.setAttribute("sessionInfo", sessionInfo);
        }
        

    }

    /**
     * 获取当前访问路径.
     * @return
     */
    public static String getRequestUri() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUri = request.getRequestURI();
       
        //String queryString=request.getQueryString();
        //String path = request.getContextPath();
        return requestUri;
    }
    /**
     * 获取query String
     * @return
     */
    public static String getQueryString(){
    	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	 String queryString=request.getQueryString();
    	 return queryString;
    }


    /**
     * 检测是否是ajax请求<br>
     * <p/>
     * <b>date: <b> Sep 17, 2013 1:44:50 PM
     *
     * @return boolean
     * @version 1.0
     * @author using using@qq.com
     */
    public static boolean isAjax() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String isAjaxReq = request.getHeader("X-Requested-With");
        if (isAjaxReq != null) {
            // ajax请求
            return true;
        } else {
            // 普通网络请求
            return false;
        }
    }
    
    /**
     * 获取当前访问请求所在页面.
     * @return
     */
    public static String getRequestPage() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String referer=request.getHeader("referer");     
        return referer;
    }
}
