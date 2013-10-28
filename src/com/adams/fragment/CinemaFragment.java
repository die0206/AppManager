package com.adams.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import com.adams.appmanager.R;

public class CinemaFragment extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentManager fm = getSupportFragmentManager();

		if (fm.findFragmentById(android.R.id.content) == null) {
			CinemaListFragment list = new CinemaListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	public static class CinemaListFragment extends SherlockFragment {

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_tab2, container, false);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			// 设置标题栏显示菜单
			setHasOptionsMenu(true);
		}

		/**
		 * 创建菜单
		 */
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

			SubMenu subMenu = menu.addSubMenu("Action Item");
			subMenu.add("Sample");
			subMenu.add("Menu");
			subMenu.add("Items");

			MenuItem subMenuItem = subMenu.getItem();
			subMenuItem.setTitle("请选择");
			subMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
					| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

			menu.add("Refresh")
					.setIcon(R.drawable.navigation_refresh)
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_ALWAYS
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}

	}
}
