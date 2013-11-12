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

public class P18_CityTranAct extends Activity{
	private TextView content;
	private TextView line1;
	private TextView line2;
	private TextView txt1;
	private TextView txt2;
	private TextView txt3;
	private TextView txt4;

	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
 	private ClientContext context;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.deppon.R.layout.p18_jzcy_gjq);
		setTitle("精准城运");
		// 全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 获得controller
		controller = ClientController.getController(P18_CityTranAct.this);
		// 单利context
		context = controller.getContext();
		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
		content = (TextView)findViewById(com.deppon.R.id.p102_content_tv);
		content.setText("精准城运，准点送达");
		txt1 = (TextView)findViewById(com.deppon.R.id.p102_txt1_tv);
		txt1.setText("承载您对德邦运输承诺的信任，助力您的货物准点送达 ");
		line1 = (TextView)findViewById(com.deppon.R.id.p102_line1_tv);
		line1.setText("----------------------------------------------------------------");
		
		txt2 = (TextView)findViewById(com.deppon.R.id.p102_txt2_tv);
		txt2.setText("	实现珠三角、长三角、京津塘、山东、辽宁、川渝区域城市之间快速送达。 准点到达，准点派送限时未到，最高按运费3倍赔付 ");

		line2 = (TextView)findViewById(com.deppon.R.id.p102_line2_tv);
		line2.setText("----------------------------------------------------------------");
		
		txt3 = (TextView)findViewById(com.deppon.R.id.p102_txt3_tv);
		txt3.setText("    珠三角区域内往返                                                                                         广州、深圳、东莞、佛山、中山、江门、珠海、惠州、清远、肇庆                                                                                        	 京津塘区域内往返                                                                                          北京、天津、廊坊、燕郊、石家庄  辽宁区域内往返 沈阳、大连、鞍山、抚顺                                                                                          	 山东区域内往返                                                                                          青岛、即墨、诸城、济南、威海、潍坊、烟台、胶州、德州、淄博、泰安                                                                                           	长三角区域内往返                                                                                          上海、苏州、太仓、吴江、昆山、常熟、江阴、张家港、无锡、常州、杭州、嘉兴、湖州、绍兴、义乌、宁波、余姚、慈溪                                                                                           	川渝区域内往返                                                                                          成都、绵阳、泸州、德阳、乐山、重庆");
	
		txt4 = (TextView)findViewById(com.deppon.R.id.p102_txt4_tv);
		txt4.setText("以上线路时效仅供参考，欲了解更多信息（如价格），致电全国服务热线：400-830-5555 ");
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
