package com.matti.idev.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import android.graphics.drawable.Drawable;

/**
 * 
 * @author matti
 * @description
 *
 */
public class ImageFetchFactory {
	
	private static final String CACHE_FORMAT = ".img";
	
	private ImageFetchFactory(){
		
	}
	
	/**对外获取图片的方法*/
	public static Drawable getImage(String url){
		Drawable drawable = null;
		if(!BeanUtils.isEmpty(url)){
			//md5算法比较慢放在外部统一处理
			String cacheName = BeanUtils.md532(url);
			//尝试读取缓存
			drawable = getCacheImage(url,cacheName);
			if(drawable == null){
				//使用策略进行网络下载
				drawable = download(url,cacheName);
			}
		}
		return drawable;
	}
	
	/**下载图片*/
	protected static Drawable download(String url,String cacheName){
		Drawable drawable = null;
		String startS = ""; 
		//分割出对应的服务器
		try{
			startS = url.substring(0, 15);
		}catch(Exception e){
			startS = String.valueOf(url.length() % 8);
		}
		//向同一个服务器开启的线程数 进行分割
		int poolCounts = 3;
		String poolNumber = String.valueOf(System.currentTimeMillis() % poolCounts);
		synchronized (startS+poolNumber) {
			drawable = getNetDrawable(url,cacheName);
		}
		return drawable;
	}
	
	/**尝试从缓存中获取图片数据*/
	protected static Drawable getCacheImage(String url,String cacheName){
		Drawable d = null;
		String filePath = CommonUtils.getImageCachePath()+File.separator+cacheName+CACHE_FORMAT;
		if(BeanUtils.isFileExist(filePath)){
			d = getLocalDrawable(filePath);
		}
		return d;
	}
	
	/**将图片文件转换成具体的图片数据*/
	public static Drawable getLocalDrawable(String filePath){
		Drawable d = null;
		synchronized (filePath) {
			InputStream i = null;
			try{
				i = new FileInputStream(filePath);
				d = Drawable.createFromStream(i, "src");
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				BeanUtils.close(i);
			}
		}
		return d;
	}
	
	/**防止多个线程操作同一个图片*/
	protected static Drawable getNetDrawable(String url,String cacheName){
		synchronized (url) {
			Drawable drawable = getCacheImage(url,cacheName);
			if(drawable == null){
				//真是的下载图片
				drawable = getNetDownloadDrawable(url,cacheName);
			}
			return drawable;
		}
	}
	
	/**获取网络图片*/
	private static Drawable getNetDownloadDrawable(String url,String cacheName){
		synchronized (url) {
			Drawable drawable = null;
			URL m;
			InputStream i = null;
			try {
				m = new URL(url);
				i = (InputStream) m.getContent();
				if(!BeanUtils.isEmpty(cacheName) && i!=null){
					//存放的缓存地址
					String filePath = CommonUtils.getImageCachePath()+File.separator+cacheName+CACHE_FORMAT;
					//文件进行存储
					BeanUtils.saveFile(i, filePath);
					//再重文件中获取
					drawable = getLocalDrawable(filePath);
				}else{
					drawable = Drawable.createFromStream(i, "src");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				BeanUtils.close(i);
			}
			return drawable;
		}
	}
	
	public static void release(){
		BeanUtils.deleteFile(CommonUtils.getImageCachePath());
	}
}
