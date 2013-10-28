package com.matti.idev.common.view;

import com.matti.idev.common.util.AsyncImageLoader;
import com.matti.idev.common.util.AsyncImageLoader.ImageCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

public class ViewUtils {

	public static AsyncImageLoader imageLoader = new AsyncImageLoader();

	/** 隐藏键盘 */
	public static void hiddieKeywords(Context context, EditText editText) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/** 显示对应图片 */
	public static void showImage(final ImageView imageView, String imageUrl) {
		Drawable drawable =imageLoader.loadDrawable(imageUrl, "",
				new ImageCallback() {

					@Override
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl, String key) {
						if(imageView != null && imageDrawable != null){
							imageView.setImageDrawable(imageDrawable);
							imageView.setVisibility(View.VISIBLE);
						}
					}
				});
		if(drawable != null && imageView != null){
			imageView.setImageDrawable(drawable);
		}
	}
	
	public void setImageValue(ImageView imageView,Object obj){
		if(imageView != null && obj != null){
			try{
				if(obj instanceof Drawable){
						imageView.setImageDrawable((Drawable) obj);
					
				}else if(obj instanceof Bitmap){
						imageView.setImageBitmap((Bitmap) obj);
					
				}else if(obj instanceof Integer){
						imageView.setImageResource((Integer) obj);
					
				}else if(obj instanceof String){
					showImage(imageView, (String) obj);
				}
			}catch(Exception e){
			}
		}else if(obj == null){
			imageView.setVisibility(View.GONE);
		}
	}
	

	// 对于大图片回收内存
	public static void recycleImageViewResource(ImageView imageView) {
		if (imageView != null && imageView.getDrawable() != null) {
			BitmapDrawable bd = (BitmapDrawable) imageView.getDrawable();
			if (bd.getBitmap() != null && !bd.getBitmap().isRecycled()) {
				bd.getBitmap().recycle();
			}
			bd = null;
		}
		imageView = null;
	}
}
