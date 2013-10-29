package com.matti.idev.common.request.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.matti.idev.common.request.RequestCallBack;
import com.matti.idev.common.request.ResultItem;
import com.matti.idev.common.util.BeanUtils;
import com.matti.idev.common.util.NewsConfig;

import android.util.Log;
import android.widget.EditText;

/**
 * http请求帮助类 
 * @author matti
 *
 */
public class HttpRequestHelper {
	
	public static void getDatas(int what,HttpRequestParams param,RequestCallBack<ResultItem> callBack){
		if(param != null){
			new HttpAsyncTask(what, param, callBack).execute();
		}
	}
	
	/** 处理Json格式的数据返回 */
	public static ResultItem processJson(String context) {
	ResultItem item = new ResultItem();
	try {
	JSONObject jsonObj = null;
	if (context != null && context.trim().startsWith("[")) {
	jsonObj = new JSONObject();
	jsonObj.put("list", new JSONArray(context));
	} else {
	jsonObj = new JSONObject(context);
	}
	// 转换为统一的ResultItem
	item = BeanUtils.convertJSONObject(jsonObj);
	// 主要那个用于监听用户色session 或者 个别需要处理的错误，或者统一的错误
	} catch (Exception e) {
	e.printStackTrace();
	}
	return item;
	}
	
	
	/**拼装 URL*/
	public static  HttpGet buildHttGet(HttpRequestParams httpRequestParams){
		
    	//参数信息
  		StringBuffer argsUrl = new StringBuffer();
	    
	    if(httpRequestParams.getParams() != null ){
			List<NameValuePair> params = new ArrayList<NameValuePair>(httpRequestParams.getParams().size());
			Map<String,Object> paramMap = httpRequestParams.getParams();
			for(String key:httpRequestParams.getParams().keySet()){
				Object obj = paramMap.get(key);
				params.add(new BasicNameValuePair(key,obj==null ? null : obj.toString()));  
				try {
					argsUrl.append((argsUrl.length()!=0 ? "&" : "")+key+"="+URLEncoder.encode(obj==null ? "" : obj.toString(),HTTP.UTF_8));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	    //获取请求的地址并且拼加参数
	    String url = httpRequestParams.getUrl();
	    //最终请求的URL
	    url = BeanUtils.urlAppend(url, argsUrl.toString());
	    System.out.println("========"+url);
	    HttpGet get = new HttpGet(url);
	    if(NewsConfig.showLog){
	    	Log.i("get", url);
	    }
	    return get;
	}
	
	
	public static HttpPost buildHttPost(HttpRequestParams httpRequestParams)throws UnsupportedEncodingException{
		HttpPost post = new HttpPost(httpRequestParams.getUrl());
		if(httpRequestParams.getParams() != null ){
			List<NameValuePair> params = new ArrayList<NameValuePair>(httpRequestParams.getParams().size());
			Map<String,Object> paramMap = httpRequestParams.getParams();
			for(String key:httpRequestParams.getParams().keySet()){
				Object obj = paramMap.get(key);
				params.add(new BasicNameValuePair(key,obj==null ? null : obj.toString()));   
			}
			try {
				post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				throw e;
			} 
		}
		if(NewsConfig.showLog){
	    	Log.i("post url", httpRequestParams.getUrl());
	    	try {
				Log.i("post param", EntityUtils.toString(post.getEntity()));
			} catch (Exception e) {
				e.printStackTrace();
			} 
	    }
		return post;
	}




	
}

