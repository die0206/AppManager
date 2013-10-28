package com.matti.idev.common.request;

/**
 * 请求calback
 * @author matti
 *
 * @param <T>
 */
public interface RequestCallBack<T> {
	
	
	public void onComplete(RequestResopnse<T> response);//无论成功还是失败都回掉
	
	public void onScuess(RequestResopnse<T> response);//成功时回调
	
	public void onError(RequestResopnse<T> response);//失败时回调

}
