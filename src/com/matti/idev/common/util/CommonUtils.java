package com.matti.idev.common.util;

import java.io.File;

import com.matti.idev.app.MSysApplication;

import android.os.Environment;


/**
 * 
 * @author matti
 *
 */
public class CommonUtils {

	private static String getCachePath(String name) {
		String path = "";
		if (hasSdcard()) {
			path = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + "Android/data/"
					+ MSysApplication.getMPackageName() + "/download/";
		} else {
			path = getDataPath();
		}
		BeanUtils.checkFileExist(path);
		path = path + File.separator + name + File.separator;
		BeanUtils.checkFileExist(path);
		return path;
	}

	/** 图片缓存目录 */
	public static String getImageCachePath() {
		return getCachePath("cache");
	}

	private static String getDataPath() {
		String path = "";
		path = "/data/data/" + MSysApplication.getMPackageName() + "/config/";
		BeanUtils.checkFileExist(path);
		return path;
	}

	public static boolean hasSdcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

}
