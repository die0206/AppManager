package com.matti.idev.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.matti.idev.app.MSysApplication;
import com.matti.idev.common.request.ResultItem;

import android.database.Cursor;

/**
 * 各种跟bean有关的公用方法  其他公用方法写在 CommonUtils里
 * @author matti
 *
 */
public class BeanUtils {
	
	/**
	 * 通用资源的关闭操作
	 * @param closeable
	 */
	public static void close(Closeable closeable){
		if(closeable!=null){
			try{
				closeable.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 反射对象
	 * @param className
	 * @return
	 */
	@SuppressWarnings("all")
	public static Object loadClass(String className){
		Object obj = null;
		try {
			Class classs = Class.forName(className);
			obj = classs.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("加载【"+className+"】失败!");
		}
		return obj;
	}
	
	
	/**
	 * 判断数据是否为空 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("all")
	public static boolean isEmpty(Object obj){
		boolean flag = true;
		if(obj!=null){
			if(obj instanceof String){
				flag = (obj.toString().trim().length() == 0);
				
			}else if(obj instanceof Collection<?>){
				flag = ((Collection)obj).size() == 0;
				
			}else if(obj instanceof Map){
				flag = ((Map)obj).size() == 0;
				
			}else if(obj instanceof ResultItem){
				flag = ((ResultItem)obj).getValues().size() == 0;
				
			}else if(obj instanceof Object[]){
				flag = ((Object[])obj).length == 0;
				
			}else{
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 日期转换
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 字符串转换日期
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date parseDate(String dateStr,String formatStr){
		Date result = null;
		try{
			if(dateStr.length() < formatStr.length()){
				dateStr = "0"+dateStr;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			result = sdf.parse(dateStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 文件保存
	 * @param message
	 */
	public static void saveFile(InputStream inps,String filePath){
		OutputStream out = null;
		try{
			out =  new FileOutputStream(filePath);
			byte[] buffer = new byte[2048];  
		    int len = -1;  
		    while ((len = inps.read(buffer)) != -1) {  
		    	out.write(buffer, 0, len);  
		    }
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(inps);
			close(out);
		}
	}
	
	/**
	 * 文件保存
	 * @param fileContent
	 */
	public static void saveFile(String fileContent,String filePath){
		if(!BeanUtils.isEmpty(fileContent)){
			saveFile(fileContent.getBytes(),filePath);
		}
	}
	
	/**解析文件名称*/
	public static String parseFileName(String url){
		String fileName = "";
		int lastSplit = url.lastIndexOf("/");
		int lastSplit1 = url.lastIndexOf("\\");
		lastSplit = lastSplit > lastSplit1 ? lastSplit : lastSplit1;
		fileName = url.substring(lastSplit+1);
		return fileName;
	}
	
	/**
	 * 文件保存
	 * @param byes
	 */
	public static void saveFile(byte[] byes,String filePath){
		OutputStream output = null;
		try{
			output = new FileOutputStream(filePath);
			output.write(byes);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(output);
		}
	}
	
	
	/**
	 * 获取对象的属性
	 * @param obj
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getFieldValue(Object obj,String key){
		if(obj!=null){
			if(obj instanceof Map<?, ?>){
				return ((Map<String,Object>)obj).get(key);
			}else if(obj instanceof ResultItem){
				return ((ResultItem)obj).getValue(key);
			}else if(obj instanceof String){
				return obj;
			}
		}
		return null;
	}
	
	/**获取文件路径*/
	public static String getFileContext(String filePath){
		String message = "";
		try {
			message = getFileContext(new FileInputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	/**获取文件内容*/
	public static String getFileContext(InputStream input){
		String mesage = "";
		ByteArrayOutputStream outStrem = null;
		try{
			outStrem = new ByteArrayOutputStream();
			int i = 0;
			while((i=input.read())!=-1){
				outStrem.write(i);
			}
			mesage = new String(outStrem.toByteArray());
			return mesage;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("");
		}finally{
			try {
				input.close();
				outStrem.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * URl的拼接
	 * @param url
	 * @param parts
	 * @return
	 */
	public static String urlAppend(String url,String parts){
		url = url.trim();
		if(!BeanUtils.isEmpty(parts)){
			if(url.indexOf("?") < 0){
				url += "?";
			}else{
				url += "&";
			}
		}
		return url+parts;
	}
	
	/**
	 * 验证文件路径是否存在，不存在则进行创建操作
	 * @param filePath
	 */
	public static void checkFileExist(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**判断文件是否存在*/
	public static boolean isFileExist(String filePath){
		boolean hasFile = false;
		try{
			File file = new File(filePath);
			hasFile = file.exists();
		}catch(Exception e){
			e.printStackTrace();
		}
		return hasFile;
	}
	
	/**删除文件*/
	public static void deleteFile(String filePath){
		File file = new File(filePath);
		try{
			if(file.isFile()){
				if(isFileExist(filePath)){
					file.delete();
				}
			}else if(file.isDirectory()){
				File[] tempFiles = file.listFiles();
				if(!BeanUtils.isEmpty(tempFiles)){
					for(File tempFile : tempFiles){
						tempFile.delete();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 将数据库的查询内容转换为具体的数据内容
	 * @param cursor
	 * @return
	 */
	public static List<ResultItem> convertCursor(Cursor cursor){
		List<ResultItem> results = new ArrayList<ResultItem>();
		if(cursor!=null && cursor.getCount() > 0){
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
				int size = cursor.getColumnCount();
				ResultItem item = new ResultItem();
				for(int i = 0;i <size;i++){
					item.addValue(cursor.getColumnName(i),cursor.getString(i));
				}
				results.add(item);
			}
		}
		return results;
	}
	
//	/**集合排序*/
//	public static void sort(List<? extends ResultItem> datas,ResultItemComparer comparer){
//		try{
//			Collections.sort(datas, comparer);
//		}catch(Exception e){
//		}
//	}
	
	/**根据流获取文本内容*/
	public static String getContent(InputStream in,String encode){
		StringBuffer mesage = new StringBuffer();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(in, encode));
			int i = 0;
			while((i = reader.read()) !=-1){
				mesage.append((char)i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BeanUtils.close(in);
		}
		String data = mesage.toString();
		int firstIndex = data.indexOf("{");
		return firstIndex<=0?data:data.substring(firstIndex);
	}
	
	/**将JsonObject转换成ResultItem*/
	@SuppressWarnings("unchecked")
	public static ResultItem convertJSONObject(JSONObject jsonObj){
		ResultItem resultItem = new ResultItem();
	    if(jsonObj!=null){
	    	//遍历所有的KEY值
    	    Iterator<String> keys = jsonObj.keys();
            while(keys.hasNext()){
                String key = keys.next();
                try{
                	//获取具体对象
                	Object obj = jsonObj.get(key);
                	if(obj!=null){
                		if(obj instanceof JSONObject){
                			//添加属性(递归添加)
                			resultItem.addValue(key,convertJSONObject((JSONObject)obj));
                			
                		}else if(obj instanceof JSONArray){
                			//列表对象
                			List<Object> listItems = new ArrayList<Object>();
                			//将JSONArray足个解析
                			JSONArray tempArray = (JSONArray) obj;
                			for(int i = 0; i < tempArray.length() ; i++){
                				Object itempObj = tempArray.get(i); 
                				if(itempObj instanceof JSONObject){
                					//递归添加
                					listItems.add(convertJSONObject(tempArray.getJSONObject(i)));
                				}else {
                					listItems.add(itempObj);
                				}
                			}
                			resultItem.addValue(key,listItems);
                			
                		}else{
                			resultItem.addValue(key,obj.toString());
                		}
                	}
                }catch(Exception e){
                	e.printStackTrace();
                }
            }
	    }
        return resultItem;
	}
	
	/**根据json对象生成ResultItem对象*/
	public static ResultItem getResultItemByJson(String context){
		ResultItem item = new ResultItem();
		try {
			//生成jsonObject
			JSONObject jsonObj = new JSONObject(context);
			//转换为统一的ResultItem
			item = BeanUtils.convertJSONObject(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	

	/**自定义的getBtyes*/
	protected static byte[] getBytes(String message) {
		byte[] values = new byte[message.length()];
		for (int i = 0; i < message.length(); i++) {
			values[i] = (byte) message.charAt(i);
		}
		return values;
	}

	public static String getDataPath(String dataName){
		String path = "";
		//暂时所有数据不放sdcard上
		path = "/data/data/"+MSysApplication.getMPackageName()+"/config/"+dataName;
		BeanUtils.checkFileExist(path);
		return path;
	}
	
	/**保存序列对象*/
	public static synchronized void saveObject(Object obj,String path){
		ObjectOutputStream ost = null;
		try{
			ost = new ObjectOutputStream(new FileOutputStream(path));
			ost.writeObject(obj);
			ost.flush();
		}catch(Exception e){
		}finally{
			close(ost);
		}
	}
	
	/**异步的方式进行存储*/
	public static void aysncSaveObject(final Object obj,final String path){
		new Thread(){
			public void run(){
				saveObject(obj, path);
			}
		}.start();
	}
	
	/**获取对象*/
	public static Object getSaveObject(String path){
		ObjectInputStream ost = null;
		Object obj = null;
		try{
			ost = new ObjectInputStream(new FileInputStream(path));
			obj = ost.readObject();
		}catch(Exception e){
//			System.out.println("加载用户信息出错.........");
			deleteFile(path);
		}finally{
			close(ost);
		}
		return obj;
	}
	
	/***切割字符串*/
	public static String shortString(String content,int length){
		if(!BeanUtils.isEmpty(content) && content.length() > length){
			content = content.substring(0, 8)+"...";
		}
		return content;
	}
	
	/***
	 * 生成32位的MD5加密方式
	 * @param str
	 * @return
	 */
	public static String md532(String source){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
        try {
	         byte[] strTemp = source.getBytes();
	         //使用MD5创建MessageDigest对象
	         MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	         mdTemp.update(strTemp);
	         byte[] md = mdTemp.digest();
	         int j = md.length;
	         char str[] = new char[j * 2];
	         int k = 0;
	         for (int i = 0; i < j; i++) {
	        	 byte b = md[i];
	          	//将没个数(int)b进行双字节加密
	          	str[k++] = hexDigits[b >> 4 & 0xf];
	          	str[k++] = hexDigits[b & 0xf];
	         }
	         return new String(str);
        }catch (Exception e) {
        	return null;
        }
	}
	
	/** 执行Linux命令，并返回执行结果。 */
	public static String exec(String[] args) {
	     String result = "";
	     ProcessBuilder processBuilder = new ProcessBuilder(args);
	     Process process = null;
	     InputStream errIs = null;
	     InputStream inIs = null;
	     try {
	         ByteArrayOutputStream baos = new ByteArrayOutputStream();
	         int read = -1;
	         process = processBuilder.start();
	         errIs = process.getErrorStream();
	         while ((read = errIs.read()) != -1) {
	             baos.write(read);
	         }
	         baos.write('\n');
	         inIs = process.getInputStream();
	         while ((read = inIs.read()) != -1) {
	             baos.write(read);
	         }
	         byte[] data = baos.toByteArray();
	         result = new String(data);
	     } catch (Exception e) {
	         e.printStackTrace();
	     } finally {
	    	 BeanUtils.close(errIs);
	    	 BeanUtils.close(inIs);
	         if (process != null) {
	             process.destroy();
	         }
	     }
	     return result;
	 }
	
	
	/**将XML 字符解析**/
	public static String xmlCharDeCode(String context){
		if(!BeanUtils.isEmpty(context)){
			context = context.replace("&lt;","<");
			context = context.replace("&gt;",">");
			context = context.replace("&apos;","'");
			context = context.replace("&quot;","\"");
			context = context.replace("&amp;","&");
		}
		return context;
	}
	
	public static String getString(String...strings){
		StringBuffer sb = new StringBuffer();
		if(strings != null){
			for(String str:strings){
				if(!isEmpty(str)){
					sb.append(str);
				}
			}
		}
		return sb.toString();
	}
}
