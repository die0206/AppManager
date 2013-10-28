package com.matti.idev.common.request.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.matti.idev.common.request.RequestCallBack;
import com.matti.idev.common.request.RequestResopnse;
import com.matti.idev.common.request.ResultItem;
import com.matti.idev.common.type.CommonError;
import com.matti.idev.common.type.HttpMethod;
import com.matti.idev.common.util.NewsConfig;

import android.os.AsyncTask;
import android.util.Log;

/**
 * 请求线程
 * @author matti
 *
 */
public class HttpAsyncTask extends AsyncTask<Void, Void, RequestResopnse<ResultItem>> {
	
	private HttpRequestParams param;
	
	private RequestCallBack<ResultItem> callBack;
	
	private int what = 0;
	
	public HttpAsyncTask(int what,HttpRequestParams param,RequestCallBack<ResultItem> callBack){
		this.param = param;
		this.callBack = callBack;
		this.what = what;
	}

	@Override
	protected RequestResopnse<ResultItem> doInBackground(Void... params) {
		RequestResopnse<ResultItem> resopnse = new RequestResopnse<ResultItem>();
		resopnse.setWhat(what);
		// 先封装一个 JSON 对象
		try {
			//使用默认的HttpClient
			HttpClient client = new DefaultHttpClient();
			//设置连接超时时间
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000*60);
			//设置读取内容连接超时时间
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000*60);
			//根据提交的方式创建HttpRequestBase
			HttpRequestBase requestBase = param.getMethod() == HttpMethod.GET ? HttpRequestHelper.buildHttGet(param) : HttpRequestHelper.buildHttPost(param);
		    //执行HTTP请求
		    HttpResponse httpResponse = client.execute(requestBase);
		    if(httpResponse.getStatusLine().getStatusCode() == 200){
		    	String reslutStr = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
		    	if(NewsConfig.showLog){
		    		Log.i("reslut", reslutStr);
		    	}
		    	ResultItem result = HttpRequestHelper.processJson(reslutStr);
		    	resopnse.setResults(result);
		    }else{
		    	resopnse.setErrorCode(CommonError.NETWORK_ERROR.name());
		    }
				
		} catch (Exception e) {
			resopnse.setErrorCode(CommonError.REQUEST_ERROR.name());
			e.printStackTrace();
		} 
		
		return resopnse;
	}
	
	@Override
	protected void onPostExecute(RequestResopnse<ResultItem> response) {
		super.onPostExecute(response);
		if(callBack != null && response!= null){
			
			if(response.isError() ){
				callBack.onError(response);
			}else{
				callBack.onScuess(response);
			}
			
			callBack.onComplete(response);
		}
	}


}
