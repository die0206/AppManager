package com.adams.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.adams.appmanager.R;
import com.matti.idev.common.request.RequestCallBack;
import com.matti.idev.common.request.RequestResopnse;
import com.matti.idev.common.request.ResultItem;
import com.matti.idev.common.request.http.HttpRequestHelper;
import com.matti.idev.common.request.http.HttpRequestParamHelper;

public class MySelfMessage extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentManager fm = getSupportFragmentManager();
		if (fm.findFragmentById(android.R.id.content) == null) {
			MymessageListFragment list = new MymessageListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	public static class MymessageListFragment extends SherlockFragment {

		int uid = 72;
		ListView myselef;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			myselef = (ListView) getSherlockActivity().findViewById(R.id.myselef);
			HttpRequestHelper.getDatas(112,
					HttpRequestParamHelper.getMySelf(uid), myself);
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.my_self_message, container, false);

		}

		RequestCallBack<ResultItem> myself = new RequestCallBack<ResultItem>() {

			@Override
			public void onScuess(RequestResopnse<ResultItem> response) {
				List<Map<String, String>> mymap = new ArrayList<Map<String, String>>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", response.getResults().getString("username"));
				map.put("telephone", response.getResults().getString("telephone"));
				map.put("address", response.getResults().getString("address"));
				map.put("idcard", response.getResults().getString("idcard"));
				map.put("department",response.getResults().getString("department"));
				map.put("code", response.getResults().getString("code"));
				System.out.println(mymap.size());
				mymap.add(map);

				SimpleAdapter sm = new SimpleAdapter(getSherlockActivity(),
						mymap, R.layout.item_message,
						new String[] {"username", "telephone", "address", 
					                   "idcard","department", "code" },
						new int[] {
								   R.id.usename, R.id.telephone, R.id.address,
									R.id.idcard, R.id.deparement, R.id.code });
				myselef.setAdapter(sm);
				
			}
               
			 
			@Override
			public void onError(RequestResopnse<ResultItem> response) {
				System.out.println("获取用户信息失败");
			}

			@Override
			public void onComplete(RequestResopnse<ResultItem> response) {

			}
		};
	}
}
