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

public class P17_CarTranAct extends Activity{
	private TextView content;
	private TextView line1;
	private TextView line2;
	private TextView txt1;
	private TextView txt2;
	private TextView txt3;
	private TextView txt4;
	@SuppressWarnings("unused")
	private TextView txt5;

	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
 	private ClientContext context;
	
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.deppon.R.layout.p17_jzqy_gjq);
		setTitle("精准汽运");
		// 全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 获得controller
		controller = ClientController.getController(P17_CarTranAct.this);
		// 单利context
		context = controller.getContext();
		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
		content = (TextView)findViewById(com.deppon.R.id.p101_content_tv);
		content.setText("精准汽运，无处不达");
		txt1 = (TextView)findViewById(com.deppon.R.id.p101_txt1_tv);
		txt1.setText("承载您对德邦运输网络的信任，助力您的货物无处不达  ");
		line1 = (TextView)findViewById(com.deppon.R.id.p101_line1_tv);
		line1.setText("----------------------------------------------------------------");
		
		txt2 = (TextView)findViewById(com.deppon.R.id.p101_txt2_tv);
		txt2.setText(" ★网络横贯东西、纵穿南北、遍布全中国★每日准点发车，专线通达全国★全国各地无处不达，运输线路持续优化 ");

		line2 = (TextView)findViewById(com.deppon.R.id.p101_line2_tv);
		line2.setText("----------------------------------------------------------------");
		
		txt3 = (TextView)findViewById(com.deppon.R.id.p101_txt3_tv);
		txt3.setText("专线直达城市如下：                                                        华南                                                 		               广州、深圳、东莞、佛山、中山、江门、珠海、惠州、汕头、湛江、清远、肇庆、韶关、河源、 潮州、阳江、揭阳、南宁                                                       华东                                            		      	          上海、杭州、宁波、嘉兴、绍兴、余姚、义乌、湖州、慈溪 南京、苏州、张家港、吴江、太仓、无锡、江阴、昆山、常熟、南通、常州 济南、青岛、诸城、威海、潍坊、淄博、烟台、即墨 合肥、南昌、厦门、福州、泉州、莆田、晋江                                      			                  		   华北东北                             		                              北京、天津、石家庄、廊坊、燕郊、保定、霸州、唐山、太原 、 沈阳、鞍山、大连、长春、哈尔滨                                  		                 西部                           	    		       		                   成都、绵阳、泸州、德阳、乐山、重庆、昆明、西安、西宁");
		
		txt4 = (TextView)findViewById(com.deppon.R.id.p101_txt4_tv);
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
