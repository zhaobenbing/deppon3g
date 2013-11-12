package com.deppon.client.ui;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;

public class P19_CardAirhAct extends Activity{
	private TextView content;
	private TextView line1;
	private TextView line2;
	private TextView line3;
	private TextView line4;
	private TextView line5;
	private TextView txt1;
	private TextView txt2;
	private TextView txt3;
	private TextView txt4;
	private TextView txt5;
	private TextView txt6;
	private TextView txt7;
	private TextView txt8;
	private TextView txt9;
	private TextView txt10;
	
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
 	private ClientContext context;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.deppon.R.layout.p19_jzkh_gjq);
		setTitle("精准卡航");
		// 全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 获得controller
		controller = ClientController.getController(P19_CardAirhAct.this);
		// 单利context
		context = controller.getContext();
		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
		content = (TextView)findViewById(com.deppon.R.id.p103_content_tv);
		content.setText("精准卡航,限时送达");
		txt1 = (TextView)findViewById(com.deppon.R.id.p103_txt1_tv);
		txt1.setText("承载您对德邦运输时效的信任,助力您的货物快速送达");
		line1 = (TextView)findViewById(com.deppon.R.id.p103_line1_tv);
		line1.setText("----------------------------------------------------------------");
		
		txt2 = (TextView)findViewById(com.deppon.R.id.p103_txt2_tv);
		txt2.setText("快速");
		txt3 = (TextView)findViewById(com.deppon.R.id.p103_txt3_tv);
		txt3.setText("全部采用进口VOLVO/SCANIA等全封闭厢式卡车，良好的刹车性能给驾驶员提供了一个安全的开车环境,以最优的线路为您的货物运输和派送保证您的货物在第一时间内到达");
		line2 = (TextView)findViewById(com.deppon.R.id.p103_line2_tv);
		line2.setText("----------------------------------------------------------------");
		
		txt4 = (TextView)findViewById(com.deppon.R.id.p103_txt4_tv);
		txt4.setText("安全");
		txt5 = (TextView)findViewById(com.deppon.R.id.p103_txt5_tv);
		txt5.setText("车辆全部采用进口VOLVO/SCANIA等全封闭厢式卡车，（车辆自身严密电子系统控制，给车辆带来全方位的保护,GPS全球定位，短信、电话、网络实现全程货物跟踪，使您随时随地掌握货物在途信息");
		line3 = (TextView)findViewById(com.deppon.R.id.p103_line3_tv);
		line3.setText("----------------------------------------------------------------");
		
		txt6 = (TextView)findViewById(com.deppon.R.id.p103_txt6_tv);
		txt6.setText("实惠");
		txt7 = (TextView)findViewById(com.deppon.R.id.p103_txt7_tv);
		txt7.setText("空运速度，汽运价格,安全快速，限时到达");
		line4 = (TextView)findViewById(com.deppon.R.id.p103_line4_tv);
		line4.setText("----------------------------------------------------------------");
		
		txt8 = (TextView)findViewById(com.deppon.R.id.p103_txt8_tv);
		txt8.setText("网络");
		txt9 = (TextView)findViewById(com.deppon.R.id.p103_txt9_tv);
		txt9.setText("迅速通达全国188个城市，新线路持续增加中，承载您对德邦运输时效的信任,助力您的货物快速送达");
		line5 = (TextView)findViewById(com.deppon.R.id.p103_line5_tv);
		line5.setText("----------------------------------------------------------------");
		
		txt10 = (TextView)findViewById(com.deppon.R.id.p103_txt10_tv);
		txt10.setText("欲了解更多信息（如价格），请致电全国服务热线：400-830-5555");
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
		if(itemId == 2){
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
