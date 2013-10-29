package com.matti.idev.common.request.http;

import java.util.HashMap;
import java.util.Map;
import com.matti.idev.common.type.HttpMethod;
import com.matti.idev.common.type.HttpRequestType;
import com.matti.idev.common.util.NewsConstants;

/**
 * 生成参数的helper 每个方法必须写注释
 * 
 * @author matti
 * 
 */

public class HttpRequestParamHelper {

	private static HttpRequestParams getCommonParams(HttpRequestType type) {

		HttpRequestParams requestParams = new HttpRequestParams();
		requestParams.setUrl(NewsConstants.BASE_URL3 + type.getUrlPath());
		Map<String, Object> params = new HashMap<String, Object>();
		requestParams.setParams(params);
		return requestParams;
	}

	/**
	 * 获取客户端信息请求
	 * 
	 * @return
	 */
	public static HttpRequestParams getMoiveList(String str) {
		HttpRequestParams requestParams = getCommonParams(HttpRequestType.GETMOIVE);

		requestParams.getParams().put("queryAll", str);
		requestParams.setMethod(HttpMethod.GET);
		return requestParams;
	}

	public static HttpRequestParams getLogs(String enmae, String epwd) {
		HttpRequestParams requestParams = getCommonParams(HttpRequestType.GETMOIVE);

		requestParams.getParams().put("username", enmae);
		requestParams.getParams().put("password", epwd);

		requestParams.setMethod(HttpMethod.GET);
		return requestParams;
	}

	/**
	 * 获取网络热门
	 * 
	 * @param uid
	 * @return
	 */
	public static HttpRequestParams getHot(int uid) {
		HttpRequestParams requestParams = getCommonParams(HttpRequestType.SHOWUSER);
		requestParams.getParams().put("uid", uid);
		requestParams.setMethod(HttpMethod.GET);
		return requestParams;
	}

	/**
	 * 获取网络的软件的详细信息
	 * 
	 * @param pid
	 * @return
	 */
	public static HttpRequestParams getdetail(int uid, String pid) {
		HttpRequestParams requestParams = getCommonParams(HttpRequestType.DETAUK);
		requestParams.getParams().put("uid", uid);
		requestParams.getParams().put("pid", pid);
		requestParams.setMethod(HttpMethod.GET);
		return requestParams;
	}

	/**
	 * 获取用户
	 * @param uid
	 * @return
	 */
	public static HttpRequestParams getMySelf(int uid) {
		HttpRequestParams requestParams = getCommonParams(HttpRequestType.MYSELF);
		requestParams.getParams().put("uid", uid);
		requestParams.setMethod(HttpMethod.GET);
		return requestParams;
	};

}
