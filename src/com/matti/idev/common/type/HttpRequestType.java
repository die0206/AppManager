package com.matti.idev.common.type;

/**
 * 所有的请求类型  都要写注释 
 * @author matti
 *
 */
public enum HttpRequestType {
	
	
	INIT(""),			//首页初始化信息
	VERSION("/app/v1/701"),
	GETMOIVE("/login.action"),//首页初始化信息
	SHOWUSER("/userapplist.action"),
	DETAUK("/appdetail.action"),
	MYSELF("/userinfo.action");
	
	
	
	private String urlPath;
	
	private HttpRequestType(String urlPath){
		this.urlPath = urlPath;
	}

	public String getUrlPath() {
		return urlPath;
	}
	
	

}
