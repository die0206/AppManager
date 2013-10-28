package com.matti.idev.app;

import java.util.HashMap;
import java.util.Map;

import com.matti.idev.common.request.ResultItem;
import com.matti.idev.common.type.HttpRequestType;

import android.app.Application;
import android.content.Context;

/**
 * application
 * @author matti
 *
 */
public class MSysApplication extends Application {
	
	private static String packageName;
	private static Context mContext;
	
	
	public static Map<String ,ResultItem> sourceMap = new HashMap<String,ResultItem>();//用来缓存不会变化的请求数据
	static{
		sourceMap.put(HttpRequestType.VERSION.name(), null);//TODO add 
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		packageName = getApplicationContext().getPackageName();
		mContext = getApplicationContext();
	}
	
	public static String getMPackageName(){
		return packageName;
	}

	public static Context getmContext() {
		return mContext;
	}

}
