package com.matti.idev.common.view.adapter;

import java.util.List;

import com.matti.idev.common.util.AsyncImageLoader;
import com.matti.idev.common.util.BeanUtils;
import com.matti.idev.common.util.AsyncImageLoader.ImageCallback;
import com.matti.idev.common.view.ViewUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 公用 adapter
 * @author matti
 *
 */
public class ResultItemAdapter extends BaseAdapter {

	private Context context;
	protected List<?> items;
	private int layoutId;
	private String[] keys;
	protected int[] textid;
	private View listView;
	private AsyncImageLoader imageLoader;
	private ItemViewHandler itemViewHandler;
	private int[] itemColors;
	

	public ResultItemAdapter(View listView, List<?> reuslts, int layoutId,
			String[] keys, int[] txtids,ItemViewHandler itemViewHandler) {
		this.context = listView.getContext();
		this.listView = listView;
		this.items = reuslts;
		this.layoutId = layoutId;
		this.keys = keys;
		this.textid = txtids;
		this.itemViewHandler = itemViewHandler;
		this.imageLoader = ViewUtils.imageLoader;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			// 没有生成使用的布局
			convertView = LayoutInflater.from(context).inflate(layoutId, null);
			// 创建Holder绑定子元素
			viewHolder = new ViewHolder();
			if (textid != null && textid.length > 0) {
				viewHolder.resultViews = new View[textid.length];
				for (int i = 0; i < textid.length; i++) {
					viewHolder.resultViews[i] = convertView.findViewById(textid[i]);
				}
			}
			convertView.setTag(viewHolder);
		} else {
			// 直接从Holder获取绑定的对象
			viewHolder = (ViewHolder) convertView.getTag();
		}
		buildViewContext(position, convertView, viewHolder);
		if(itemColors != null ){
			convertView.setBackgroundColor(context.getResources().getColor(itemColors[position%itemColors.length]));
		}
		return convertView;
	}

	/** 具体的视图处理 */
	protected void buildViewContext(int position, View convertView,ViewHolder viewHolder) {
		try {
			// 只对有效数据进行操作
			if (items != null && position < items.size()) {
				if (textid != null && textid.length > 0 && keys != null
						&& keys.length > 0) {
					for (int i = 0; i < textid.length; i++) {
						// 获取对应的View
						View view = viewHolder.resultViews[i];
						if (view != null) {
							// 获取对应的值
							Object obj = BeanUtils.getFieldValue(
									getItem(position), keys[i]);
							// 将值设置对视图里
							setViewValue(view, obj, keys[i], position);
						}
					}
				}
				 if(itemViewHandler != null){
					 itemViewHandler.handleView(convertView,getItem(position),position);
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setViewValue(View view, Object obj, String key,
			final int position) {
		if (view instanceof TextView && obj instanceof String) {
			((TextView) view).setText((String) obj);

		} else if (view instanceof ImageView) {
			ImageView tempImage = (ImageView) view;
			tempImage.setTag(key + position);
			if (obj != null) {
				if (obj instanceof Integer || obj.getClass() == int.class) {
					// 使用资源绑定图形
					tempImage.setImageResource((Integer) obj);
				} else if (obj instanceof String) {
					// 使用网络请求绑定ImageView
					String url = "http://172.25.43.16:8080/guoxin/gxupload"+obj.toString();
					// 查找的KEY
					final String tagKey = key + url + position;
					tempImage.setTag(tagKey);
					// 开启网络请求获取图片
					Drawable drawables = null;
					if (!BeanUtils.isEmpty(url) && URLUtil.isHttpUrl(url)) {
						// 开启网络请求获取图片
						drawables = imageLoader.loadDrawable(url, key,
								new ImageCallback() {

									@Override
									public void imageLoaded(
											Drawable imageDrawable,
											String imageUrl, String key) {
										if (listView != null) {
											ImageView imageViewByTag = (ImageView) listView
													.findViewWithTag(tagKey);
											if (imageViewByTag != null) {
												setImageValue(imageViewByTag,
														imageDrawable);
											}
										}
									}
								});
					}
					// 图片展示
					setImageValue(tempImage, drawables);
				}
			} else {
				// 图片展示
				setImageValue(tempImage, null);
			}
		}
	}

	private void setImageValue(ImageView imageView, Drawable drawable) {
		if (imageView != null && drawable != null) {
			try {
				imageView.setImageDrawable(drawable);
				imageView.setVisibility(View.VISIBLE);
			} catch (Exception e) {
			}
		} else if (drawable == null) {
			imageView.setVisibility(View.GONE);
		}
	}

	public class ViewHolder {
		public View resultViews[];
	}

	public void setItemColors(int[] itemColors) {
		this.itemColors = itemColors;
	}
	
}
