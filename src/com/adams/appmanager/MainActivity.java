package com.adams.appmanager;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.adams.appmanager.R;
import com.adams.fragment.LoaderCustomSupport;
import com.adams.fragment.Tab1Fragment;
import com.adams.fragment.CinemaFragment;
import com.adams.fragment.UserDetails;
import com.adams.utils.CreateTabsTool;

import android.os.Bundle;

public class MainActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_tabs_pager);

		ArrayList<Class> fragments = new ArrayList<Class>();
		fragments.add(UserDetails.Newhost.class);
		fragments.add(CinemaFragment.CinemaListFragment.class);
		fragments.add(LoaderCustomSupport.AppListFragment.class);

		CreateTabsTool.createFragmentTabs(this, savedInstanceState,
				new String[] { "热门", "管理", "我的" }, fragments);

		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", CreateTabsTool.getmTabHost()
				.getCurrentTabTag());
	}
}
