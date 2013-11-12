package com.deppon.client.ui;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;
/**
 * @功能描述：建议UI
 * @author :赵本兵
 * @创建日期：2011-11-3
 */
public class P22_AdviceAct extends Activity {
	@SuppressWarnings("unused")
	private EditText et ;
 	private Button btn ;
	private TextView tv;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.deppon.R.layout.p22_advice);
		setTitle("建议");
		//全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		controller = ClientController.getController(this);
		context = controller.getContext();
		initView();
		tv.setOnLongClickListener(new OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				String phone = tv.getText().toString().trim().split(":")[1].trim();
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
				startActivity(intent);
				return false;
			}
		});
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(P22_AdviceAct.this, "感谢您的留言", Toast.LENGTH_LONG).show();
			}
		});
	}
	@SuppressWarnings("unchecked")
	private void initView() {
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
		et = (EditText)findViewById(com.deppon.R.id.p22_consult_et);
		btn = (Button)findViewById(com.deppon.R.id.p22_consult_btn);
		tv = (TextView)findViewById(com.deppon.R.id.p22_consult_tv);
	}
	// menu 菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, getString(com.deppon.R.string.menu_index));
		menu.add(0, 1, 1, getString(com.deppon.R.string.menu_back));
		menu.add(0, 2, 2, getString(com.deppon.R.string.menu_logout));
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == 0) {
			Intent intent = new Intent(this, TabWidget.class);
			startActivity(intent);		
		}
		if (itemId == 1) {
			controller.goBack();
		}
		if(itemId==2){
			new Exit(this);
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			controller.goBack();
			return false;
		}
		return false;
	}
}
