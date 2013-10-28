package com.adams.utils;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.adams.adapter.TabsAdapter;
import com.adams.appmanager.R;

public class CreateTabsTool {

	private static TabHost mTabHost;

	public static TabHost getmTabHost() {
		return mTabHost;
	}

	public static void setmTabHost(TabHost mTabHost) {
		CreateTabsTool.mTabHost = mTabHost;
	}

	public static ViewPager getmViewPager() {
		return mViewPager;
	}

	public static void setmViewPager(ViewPager mViewPager) {
		CreateTabsTool.mViewPager = mViewPager;
	}

	public static TabsAdapter getmTabsAdapter() {
		return mTabsAdapter;
	}

	public static void setmTabsAdapter(TabsAdapter mTabsAdapter) {
		CreateTabsTool.mTabsAdapter = mTabsAdapter;
	}

	private static ViewPager mViewPager;
	private static TabsAdapter mTabsAdapter;

	public static void createFragmentTabs(FragmentActivity activity,
			Bundle savedInstanceState, String[] title, List<Class> list) {
		mTabHost = (TabHost) activity.findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager) activity.findViewById(R.id.pager);

		mTabsAdapter = new TabsAdapter(activity, mTabHost, mViewPager);

		for (int i = 0; i < list.size(); i++) {
			mTabsAdapter.addTab(
					mTabHost.newTabSpec(title[i]).setIndicator(title[i]),
					list.get(i), null);
		}

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}
}
