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

public class P16_Air_TranAct extends Activity{
	private TextView content;
	private TextView line1;
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
		setContentView(com.deppon.R.layout.p16_jzky_gjq);
		setTitle("精准空运");
		// 全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 获得controller
		controller = ClientController.getController(P16_Air_TranAct.this);
		// 单利context
		context = controller.getContext();
		content = (TextView)findViewById(com.deppon.R.id.p100_content_tv);
		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
		content.setText("精准空运，当日到达");
		txt1 = (TextView)findViewById(com.deppon.R.id.p100_txt1_tv);
 		txt1.setText("承载您对德邦空运优势的信任，助力您的货物在当日24：00内送达");
		line1 = (TextView)findViewById(com.deppon.R.id.p100_line1_tv);
		line1.setText("----------------------------------------------------------------");
		
		txt2 = (TextView)findViewById(com.deppon.R.id.p100_txt2_tv);
		txt2.setText("航空代理");
		txt3 = (TextView)findViewById(com.deppon.R.id.p100_txt3_tv);
		txt3.setText("	我司拥有南航、国航、深航、厦航、东航、山航、海航等多家航空公司的代理权，在全国47个大中城市设有空运代理点，与全国所有机场保持良好的合作关系，连续八年荣获中国南方航空货运销售金奖，连续五，是华南地区最具实力的空运代理企业之一。");
	
		txt4 = (TextView)findViewById(com.deppon.R.id.p100_txt4_tv);
		txt4.setText("精准空运");
		txt5 = (TextView)findViewById(com.deppon.R.id.p100_txt5_tv);
		txt5.setText("	代理国内各大航空公司的空运物流服务，确立战略合作伙伴关系，常年包舱订舱，走货渠道多样，辐射范围覆盖全国。与国航、南航、深航等多家航空公司签订包舱协议，签署北京、上海、哈尔滨、成都、武汉、青岛、西安等20多个目的站，尤以东北、西北等片区实现北京通单中转，北京中班、上海早班等最具优势。所有包舱航班价格优惠，时效保证。");
		
		txt6 = (TextView)findViewById(com.deppon.R.id.p100_txt6_tv);
		txt6.setText("精准时效");
		txt7 = (TextView)findViewById(com.deppon.R.id.p100_txt7_tv);
		txt7.setText("	空运以其快速、安全、便捷的特点，造就空运精准时效，大大缩短货物运转周期，提高物品流通速度，降低了客户库存的风险。                                             ★以开单预配航班为计算日，保证货物在24小时	内送达目的站机场。                                              ★航班落地后2小时可提货。                                              ★客户13点前发货，可走当天19点后的航班，实	现当天发货当天到。                                              ★全国47家空运代理，准时派送。");
		
		txt8 = (TextView)findViewById(com.deppon.R.id.p100_txt8_tv);
		txt8.setText("精准服务");
		txt9 = (TextView)findViewById(com.deppon.R.id.p100_txt9_tv);
		txt9.setText("	德邦空运，承载您的信任，以标准化和人性化真诚为您服务。从发货到目的站提货，全程把控，实现贴心一条龙精准服务。                                              ★专人专线航班配载，航班跟踪。                                              ★从收货到提货全程监控，实时信息反馈。                                              ★多种转货渠道，实现灵活走货。                                              ★遇到不可预知原因影响航班延时等异常，及时	通知发货方，并改配最早航班");
	
		txt10 = (TextView)findViewById(com.deppon.R.id.p100_txt10_tv);
		txt10.setText("	欲了解更多信息（如价格），请致电全国服务热线：400-830-5555 ");
		
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
