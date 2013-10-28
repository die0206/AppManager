package com.matti.idev.common.request;

import java.io.Serializable;

/**
 * 请求返回的实体
 * @author matti
 *
 * @param <T>
 */
public class RequestResopnse<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8053107140256040319L;

	private int what;
	
	private String errorCode;
	
	private String errorMsg;;
	
	private T results;

	public int getWhat() {
		return what;
	}

	public void setWhat(int what) {
		this.what = what;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public T getResults() {
		return results;
	}

	public void setResults(T results) {
		this.results = results;
	}
	
	public boolean isError(){
		return (errorCode != null && errorCode.length()>0) || (errorMsg != null && errorMsg.length()>0 );
	}
	
	

}
