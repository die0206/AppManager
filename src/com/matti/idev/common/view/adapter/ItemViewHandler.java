package com.matti.idev.common.view.adapter;

import android.view.View;

/**
 * 用来单独处理每个viewContent
 * @author matti
 *
 */
public interface ItemViewHandler {
	
	public void handleView(View convertView,Object obj,int positon);

}
