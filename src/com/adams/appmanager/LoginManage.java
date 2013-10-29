package com.adams.appmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.matti.idev.common.request.RequestCallBack;
import com.matti.idev.common.request.RequestResopnse;
import com.matti.idev.common.request.ResultItem;
import com.matti.idev.common.request.http.HttpRequestHelper;
import com.matti.idev.common.request.http.HttpRequestParamHelper;

public class LoginManage extends Activity {
	EditText pwd, name;
	Button btn;
	String epwd, enmae;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		name = (EditText) findViewById(R.id.ename);
		pwd = (EditText) findViewById(R.id.epwd);
		pwd.setText("21232f297a57a5a743894a0e4a801fc3");
		btn = (Button) findViewById(R.id.ebtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				enmae = name.getText().toString().trim();
				epwd = pwd.getText().toString().trim();
				HttpRequestHelper.getDatas(111,
						HttpRequestParamHelper.getLogs(enmae, epwd), callBack);
			}
		});

	}

	RequestCallBack<ResultItem> callBack = new RequestCallBack<ResultItem>() {

		@Override
		public void onComplete(RequestResopnse<ResultItem> response) {

		}

		@Override
		public void onScuess(RequestResopnse<ResultItem> response) {
			Object str = response.getResults().getValue("uid");
			System.out.println("----------" + str);

			if (Integer.parseInt(str.toString().trim()) == 0) {
				System.out.println("µÇÂ¼Ê§°Ü");

			} else {
				Intent i = new Intent(LoginManage.this, MainActivity.class);
				startActivity(i);
			}
		}

		@Override
		public void onError(RequestResopnse<ResultItem> response) {
			System.out.println("µÇÂ¼Ê§°Ü");
		}
	};
}
