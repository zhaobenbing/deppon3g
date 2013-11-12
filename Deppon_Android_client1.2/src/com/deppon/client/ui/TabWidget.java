package com.deppon.client.ui;

import java.util.LinkedList;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;
 
public class TabWidget extends TabActivity {
	private ClientController controller;
	private ClientContext context;
	private TabHost tabHost;
	//获取RelativeLayout
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		//自定义title
 		requestWindowFeature(Window.FEATURE_NO_TITLE);
 		setContentView(com.deppon.R.layout.main);
 		//全屏
 	//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		controller = ClientController.getController(this);
 		context = controller.getContext();
 		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
 		
  		//获取tabHost
 		tabHost = getTabHost();
 		//每个选项卡空间
 		TabHost.TabSpec spec ;
 		//选项卡首界面
 		Intent intent = new Intent();
 		intent.setClass(this,TabSelfService.class);
 		
 		//自助服务1
 		RelativeLayout r3 = getRelativeLayout(0, 1);
  		ImageView image2 = (ImageView)r3.getChildAt(0);
 		image2.setBackgroundResource(com.deppon.R.drawable.self_service);
 		TextView tvTab3 = (TextView)r3.getChildAt(1);
 		tvTab3.setText(getString(com.deppon.R.string.self_service));//你也可以拿到ImageView设置该Tab的图片Icon
 		spec = tabHost.newTabSpec("self_service").setIndicator(r3).setContent(intent);    
 		tabHost.addTab(spec);   
 		
 		//营业厅2
 		RelativeLayout r2 = getRelativeLayout(0, 1);
		TextView tvTab2 = (TextView)r2.getChildAt(1);
		tvTab2.setText(getString(com.deppon.R.string.bussiness_hall));//你也可以拿到ImageView设置该Tab的图片Icon
		ImageView image1 = (ImageView)r2.getChildAt(0);
		image1.setBackgroundResource(com.deppon.R.drawable.bussiness_hall);
		intent = new Intent().setClass(this, TabBussiness.class);   
		spec = tabHost.newTabSpec("bussiness_hall").setIndicator(r2).setContent(intent);    
		tabHost.addTab(spec);    
 		
		//产品业务3
		RelativeLayout rl = getRelativeLayout(0, 1);
		TextView tvTab1 = (TextView)rl.getChildAt(1);
		tvTab1.setText(getString(com.deppon.R.string.produce_bussiness));//你也可以拿到ImageView设置该Tab的图片Icon
		ImageView image = (ImageView)rl.getChildAt(0);
		image.setBackgroundResource(com.deppon.R.drawable.produce);
		intent = new Intent().setClass(this, TabProduce.class);   
		spec = tabHost.newTabSpec("produce").setIndicator(rl).setContent(intent);    
		tabHost.addTab(spec);   
 		
 		//系统管理界面4
 		RelativeLayout r4 = getRelativeLayout(0, 1);
		TextView tvTab4 = (TextView)r4.getChildAt(1);
		tvTab4.setText(getString(com.deppon.R.string.system_manage));//你也可以拿到ImageView设置该Tab的图片Icon
		ImageView image3 = (ImageView)r4.getChildAt(0);
		image3.setBackgroundResource(com.deppon.R.drawable.system);
		intent = new Intent().setClass(this, TabSysManager.class);   
		spec = tabHost.newTabSpec("system").setIndicator(r4).setContent(intent);    
		tabHost.addTab(spec);   
		
		//咨询建议5
		RelativeLayout r5 = getRelativeLayout(0, 1);
		ImageView image4 = (ImageView)r5.getChildAt(0);
		image4.setBackgroundResource(com.deppon.R.drawable.advice);
		TextView tvTab5 = (TextView)r5.getChildAt(1);
		tvTab5.setText(getString(com.deppon.R.string.consult_advice));//你也可以拿到ImageView设置该Tab的图片Icon
		intent = new Intent().setClass(this, TabConsult.class);   
		spec = tabHost.newTabSpec("advice").setIndicator(r5).setContent(intent);    
		tabHost.addTab(spec); 
		
		final android.widget.TabWidget tabWidget = tabHost.getTabWidget();
		//去掉系统默认的色白的底角  
		tabWidget.setStripEnabled(false);
		tabHost.setOnTabChangedListener(new OnTabChangeListener(){
			@Override
			public void onTabChanged(String tabId) {
				//当点击tab选项卡的时候，更改当前的背景  
				for (int i = 0; i < tabWidget.getChildCount(); i++) {
					View vvv = tabWidget.getChildAt(i);
					if (tabHost.getCurrentTab() == i) {
						vvv.setBackgroundColor(Color.GRAY);
					}else{
						vvv.setBackgroundResource(com.deppon.R.drawable.channelgallery_bg);
					}  
				} 
			}
		});
		
	}
	public RelativeLayout getRelativeLayout(int linearLayoutchildAt,int tabWidgetChildAt) {
		LinearLayout layout = (LinearLayout)tabHost.getChildAt(linearLayoutchildAt);
		View tw = layout.getChildAt(tabWidgetChildAt);	
		RelativeLayout rl = (RelativeLayout)LayoutInflater.from(this).inflate(com.deppon.R.layout.tab_indicator, (ViewGroup) tw, false);
		return rl;	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, getString(com.deppon.R.string.menu_order));
		menu.add(0, 1, 1, getString(com.deppon.R.string.menu_ormanager));
		menu.add(0, 2, 2, getString(com.deppon.R.string.menu_track));
		menu.add(0, 3, 3, getString(com.deppon.R.string.menu_deptsearch));
		menu.add(0, 4, 4, getString(com.deppon.R.string.menu_pricesearch));
		menu.add(0, 5, 5, getString(com.deppon.R.string.menu_logout));
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch(itemId){
		case 0:
			Intent intent0 = new Intent(this,P05_NowOrderAct.class);
			startActivity(intent0);
			break;
		case 1:
			Intent intent1 = new Intent(this,P06_OrderManageAct.class);
			startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(this,P11_GoodsTrackAct.class);
			startActivity(intent2);
			break;
		case 3:
			Intent intent3 = new Intent(this,P10_DeptSearchAct.class);
			startActivity(intent3);
			break;
		case 4:
			Intent intent4 = new Intent(this,P14_PriceSearchAct.class);
			startActivity(intent4);
			break;
		case 5:
			new Exit(this);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			if(event.getRepeatCount()==0){
				new Exit(this);
				return false;
			}
			controller.goBack();
			return false;
		}
		return false;
	}
}
