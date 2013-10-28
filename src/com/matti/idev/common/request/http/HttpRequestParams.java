package com.matti.idev.common.request.http;

import java.util.Map;

import com.matti.idev.common.type.HttpMethod;

/**
 * 
 * @author matii
 * 请求的全部参数
 */
public class HttpRequestParams {
	
	private Map<String,Object> params; //
	
	private String url;
	
	private HttpMethod method;

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	
	
}
