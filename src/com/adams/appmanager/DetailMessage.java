package com.adams.appmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.matti.idev.common.request.RequestCallBack;
import com.matti.idev.common.request.RequestResopnse;
import com.matti.idev.common.request.ResultItem;
import com.matti.idev.common.request.http.HttpRequestHelper;
import com.matti.idev.common.request.http.HttpRequestParamHelper;
import com.matti.idev.common.util.AsyncImageLoader;
import com.matti.idev.common.util.AsyncImageLoader.ImageCallback;
import com.matti.idev.common.util.NewsConstants;

public class DetailMessage extends Activity {

	String name, synopsis;
	TextView topview, lowview;
	String url = null;
	String urls[] = null;
	AsyncImageLoader asyncimgloder;
	private LinearLayout myGallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_message);
		int uid = 72;

		Intent i = getIntent();
		Bundle b = i.getBundleExtra("strpid");
		String pid = b.getString("pid");

		topview = (TextView) findViewById(R.id.toptitle);
		lowview = (TextView) findViewById(R.id.lowtitle);
		myGallery = (LinearLayout) findViewById(R.id.mygallery);

		HttpRequestHelper.getDatas(113,
				HttpRequestParamHelper.getdetail(uid, pid), detail);

	}

	RequestCallBack<ResultItem> detail = new RequestCallBack<ResultItem>() {

		@Override
		public void onScuess(RequestResopnse<ResultItem> response) {
			asyncimgloder = new AsyncImageLoader();
			name = (String) response.getResults().getValue("author");
			synopsis = (String) response.getResults().getValue("introducetext");

			StringBuilder str = new StringBuilder(response.getResults()
					.getValue("introduceimgs").toString());
			str.deleteCharAt(str.length() - 1);
			str.deleteCharAt(0);
			urls = str.toString().split(",");

			for (int i = 0; i < urls.length - 1; i++) {
				url = NewsConstants.DEATIL + urls[i].trim();
				System.out.println(url);
				asyncimgloder.loadDrawable(url, "detail", new ImageCallback() {
					@Override
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl, String key) {
						ImageView imageView = new ImageView(
								getApplicationContext());
						imageView.setLayoutParams(new LayoutParams(200, 200));
						imageView.setImageDrawable(imageDrawable);
						myGallery.addView(imageView);
					}
				});

			}
			topview.setText(name);
			lowview.setText(synopsis);

		}

		@Override
		public void onError(RequestResopnse<ResultItem> response) {
			System.out.println("返回失败");

		}

		@Override
		public void onComplete(RequestResopnse<ResultItem> response) {

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, Menu.FIRST + 1, 0, "分享");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
        switch(item.getItemId())//得到被点击的item的itemId
        {
        case Menu.FIRST + 1:
        	System.out.println("===========================ssssssss");
            break;
       
            
        }
        return true;
	}

}
