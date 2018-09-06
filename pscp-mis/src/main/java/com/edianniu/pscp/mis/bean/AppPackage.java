/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午5:33:30 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * 应用包名
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午5:33:30
 * @version V1.0
 */
public enum AppPackage {
	IOS_ELECTRICIAN_APP_PKG("com.edianniu.PSCP-Electrician", "电工APP-IOS版本"), IOS_FACILITATOR_APP_PKG(
			"com.edianniu.PSCP-Facilitator", "服务商APP-IOS版本"), IOS_CUSTOMER_APP_PKG(
			"com.edianniu.PSCP-Customer", "客户APP-IOS版本"), ANDROID_ELECTRICIAN_APP_PKG(
			"com.edianniu.pscp.electrician", "电工APP-ANDROID版本"), ANDROID_FACILITATOR_APP_PKG(
			"com.edianniu.pscp.facilitator", "服务商APP-ANDROID版本"), ANDROID_CUSTOMER_APP_PKG(
			"com.edianniu.pscp.customer", "客户APP-ANDROID版本"), PORTAL_APP_PKG(
			"com.edianniu.pscp.protal", "门户PC"), RENTER_APP_PKG(
			"com.edianniu.pscp.renter", "租客H5");
	AppPackage(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	private String value;
	private String desc;
	/**
	 * 是否WapApp
	 * @return
	 */
	public static boolean isRenterWapApp(String appPkg){//WapApp
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(appPkg.equals(RENTER_APP_PKG.getValue())){
			return true;
		}
		return false;
	}
	/**
	 * 是否WebApp
	 * @return
	 */
	public static boolean isWebApp(String appPkg){//webAPP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(appPkg.equals(PORTAL_APP_PKG.getValue())){
			return true;
		}
		return false;
	}
	/**
	 * 是否电工APP
	 * @return
	 */
	public static boolean isElectricianApp(String appPkg){//电工APP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(appPkg.equals(ANDROID_ELECTRICIAN_APP_PKG.getValue())||appPkg.equals(IOS_ELECTRICIAN_APP_PKG.getValue())){
			return true;
		}
		return false;
	}
	/**
	 * 是否服务商APP
	 * @return
	 */
	public static boolean isFacilitatorApp(String appPkg){//服务商APP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(appPkg.equals(ANDROID_FACILITATOR_APP_PKG.getValue())||appPkg.equals(IOS_FACILITATOR_APP_PKG.getValue())){
			return true;
		}
		return false;
	}
	/**
	 * 是否客户APP
	 * @return
	 */
	public static boolean isCustomerApp(String appPkg){//客户APP
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		if(appPkg.equals(ANDROID_CUSTOMER_APP_PKG.getValue())||appPkg.equals(IOS_CUSTOMER_APP_PKG.getValue())){
			return true;
		}
		return false;
	}
	/**
	 * 是否存在
	 * @param appPkg
	 * @return
	 */
	public static boolean include(String appPkg){
		if(StringUtils.isBlank(appPkg)){
			return false;
		}
		for(AppPackage appPackage:AppPackage.values()){
			if(appPackage.getValue().equals(appPackage.getValue())){
				return true;
			}
		}
		return false;
	}
	public String getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
}
