package com.umk.andx3.demo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.umk.andx3.demo.gridheader.GridHeaderViewActivity;
import com.umk.andx3.demo.x3list.X3ListViewActivity;

public class MainActivity extends Activity {

	public static Context instance = null;
	
	Button btn_x3list_view;
	Button btn_gridheader_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;
		initView();
	}

	private void initView() {

		btn_x3list_view = (Button) findViewById(R.id.btn_x3list);
		btn_gridheader_view = (Button) findViewById(R.id.btn_gridheader);
		
		btn_x3list_view.setOnClickListener(btnX3ListClickListener);
		btn_gridheader_view.setOnClickListener(btnGridHeaderClickListener);
	}
	
	OnClickListener btnX3ListClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			display(instance, "List");
			Log.e("x3", "List");
			Intent intent = new Intent(instance, X3ListViewActivity.class);
			startActivity(intent);
		}
	};
	
	OnClickListener btnGridHeaderClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			display(instance, "Grid");
			Log.e("x3", "Grid");
			Intent intent = new Intent(instance, GridHeaderViewActivity.class);
			startActivity(intent);
		}
	};
	
	
	
	public static void display(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

}