package com.adams.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.adams.appmanager.DetailMessage;
import com.adams.appmanager.R;
import com.adams.fragment.LoaderCustomSupport.AppListFragment;
import com.matti.idev.common.request.RequestCallBack;
import com.matti.idev.common.request.RequestResopnse;
import com.matti.idev.common.request.ResultItem;
import com.matti.idev.common.request.http.HttpRequestHelper;
import com.matti.idev.common.request.http.HttpRequestParamHelper;
import com.matti.idev.common.view.adapter.ItemViewHandler;
import com.matti.idev.common.view.adapter.ResultItemAdapter;

public class UserDetails extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fm = getSupportFragmentManager();

		if (fm.findFragmentById(android.R.id.content) == null) {
			AppListFragment list = new AppListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	public static class Newhost extends SherlockListFragment {

		String mCurFilter;
		String[] data = {};
		int uid = 78;
		View listview = null;
		 List<ResultItem> listResultItem ; 
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			setEmptyText("this is my listFragme");
			setHasOptionsMenu(true);
			listview = getListView();
			HttpRequestHelper.getDatas(112, HttpRequestParamHelper.getHot(uid), call);
			
		}

		@Override
		public void onListItemClick(ListView listView, View view, int position, long id) {
			super.onListItemClick(listView, view, position, id);
		
			System.out.println("AAAAAAAAAAAA"+listResultItem.get(position).getValue("appid"));
			Object strob = listResultItem.get(position).getValue("appid");
			String pid = String.valueOf(strob);
		    System.out.println(pid);
			
			Intent i = new Intent(getActivity(), DetailMessage.class);
			Bundle  bd = new Bundle();
			bd.putString("pid", pid);
			i.putExtra("strpid", bd);
			startActivity(i);
			
		}

		RequestCallBack<ResultItem> call = new RequestCallBack<ResultItem>() {
			
			@Override
			public void onComplete(RequestResopnse<ResultItem> response) {

			}

			@Override
			public void onScuess(RequestResopnse<ResultItem> response) {
			
				List<ResultItem> reuslts = response.getResults().getItems("list");
				listResultItem = new ArrayList<ResultItem>();
				System.out.println("sssssssssss"+reuslts);
				for(ResultItem ri : reuslts){
					System.out.println("============="+ri);
					listResultItem.add(ri);
					
				}
			
				
				ResultItemAdapter rs = new ResultItemAdapter(listview, reuslts,
						R.layout.item_shows, new String[] { "iconurlm",
								"category", "appname" }, new int[] {
								R.id.showimge, R.id.showcategory,
								R.id.showappname }, new ItemViewHandler() {

							@Override
							public void handleView(View convertView,
									Object obj, int positon) {

							}
						});
				setListAdapter(rs);
			}

			@Override
			public void onError(RequestResopnse<ResultItem> response) {
				System.out.println("µÇÂ¼Ê§°Ü");
			}
		};
	}

}
