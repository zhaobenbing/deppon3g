package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;
/**
 * @功能描述：跟踪信息
 * @author 赵本兵
 * @创建日期：2011-10-17
 */
public class P13_TrackInfoAct extends Activity {
	private ListView lv ;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	private ArrayList<Map<String, ?>> list;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(com.deppon.R.layout.p13_trackinfo);
 		setTitle("跟踪信息");
 		//全屏
 		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		controller = ClientController.getController(this);
 		context = controller.getContext();
 		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
 		initView();
 		list = (ArrayList<Map<String, ?>>) context.getBusinessData("TrackDetail");
 		if(list == null){
 			Toast.makeText(P13_TrackInfoAct.this,"正在为您安排发货，请您耐心等待!", 2500).show();
 		}else{
 			ListAdapter adapter = new SimpleAdapter(P13_TrackInfoAct.this, list, 
 					com.deppon.R.layout.p13_listlayout, 
 					new String[]{"trackDate","trackType","trackInfo"},
 					new int[]{com.deppon.R.id.p13_str_trackdate,com.deppon.R.id.p13_str_tracktype,
 					com.deppon.R.id.p13_str_trackinfo});
 			lv.setAdapter(adapter);
  		}
	}
	private void initView() {
		lv = (ListView)findViewById(com.deppon.R.id.p13_lv);
 	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, getString(com.deppon.R.string.menu_index));
		menu.add(0, 1, 1, getString(com.deppon.R.string.menu_back));
		menu.add(0, 2, 2, getString(com.deppon.R.string.menu_logout));
		menu.add(0, 3, 3, "重新查询");
 		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case 0:
			Intent intent = new Intent(P13_TrackInfoAct.this,TabWidget.class);
			startActivity(intent);
			break;
		case 1:
			controller.goBack();
			break;
		case 2:
			new Exit(this);
			break;
		case 3:
			Intent intent4 = new Intent(P13_TrackInfoAct.this,P11_GoodsTrackAct.class);
			startActivity(intent4);
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
