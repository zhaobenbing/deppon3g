package com.deppon.client.ui;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.TrackInfo;
import com.deppon.common.util.Exit;
/**
 * @功能描述：货物跟踪
 * @author 赵本兵
 * @创建日期：2011-10-17
 */
public class P12_GoodsTrackResult_Act extends Activity{
	//运单状态
	private TextView wayBillNumber,status,statusDiscript;
	//基本信息
	private TextView arrivecity,traType,takeWay,goodsName,goodsCount,goodsWeight,goodsVolume;
	//到达信息
	private TextView takeName,takePerson,takePhone,takeAddress;
	//跟踪信息
	private Button btn_detail;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(com.deppon.R.layout.p12_goodstrack_result);
 		setTitle("跟踪信息");
 		//全屏
 		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		controller = ClientController.getController(this);
 		context = controller.getContext();
 		initView();
 		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
 		TrackInfo tr = (TrackInfo) context.getBusinessData("TrackBasicInfo");
 		String wayBillNum = (String) context.getBusinessData("WayBillNumber");
 		wayBillNumber.setText(wayBillNum);
 		//运单状态
 		status.setText(tr.getStatus());
 		statusDiscript.setText(tr.getStatusDescription());
 		//基本信息
 		arrivecity.setText(tr.getArrivecity());
 		traType.setText(tr.getTraType());
 		takeWay.setText(tr.getTakeWay());
 		goodsName.setText(tr.getGoodsName());
 		goodsCount.setText(tr.getGoodsCount()+"件");
 		goodsWeight.setText(tr.getGoodsWeight()+"公斤");
 		goodsVolume.setText(tr.getGoodsVolume()+"方");
 		//到达信息
 		takeName.setText(tr.getTakeName());
 		takePerson.setText(tr.getTakePerson());
 		takePhone.setText(tr.getTakePhone());
 		takeAddress.setText(tr.getTakeAddress());
 		
 		btn_detail.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(P12_GoodsTrackResult_Act.this,P13_TrackInfoAct.class);
				startActivity(intent);
			}
 		});
	}
	private void initView() {
		wayBillNumber = (TextView)findViewById(com.deppon.R.id.p12_str_ydnumber);
		status = (TextView)findViewById(com.deppon.R.id.p12_str_status);
		statusDiscript = (TextView)findViewById(com.deppon.R.id.p12_str_descript);
		
		arrivecity = (TextView)findViewById(com.deppon.R.id.p12_str_endcity);
		traType = (TextView)findViewById(com.deppon.R.id.p12_str_trackway);
		takeWay = (TextView)findViewById(com.deppon.R.id.p12_str_takeway);
		goodsName = (TextView)findViewById(com.deppon.R.id.p12_str_goodsname);
		goodsCount = (TextView)findViewById(com.deppon.R.id.p12_str_goodscount);
		goodsWeight = (TextView)findViewById(com.deppon.R.id.p12_str_goodsweight);
		goodsVolume = (TextView)findViewById(com.deppon.R.id.p12_str_goodsvolume);
		//到达信息
		takeName = (TextView)findViewById(com.deppon.R.id.p12_str_takename);
		takePerson = (TextView)findViewById(com.deppon.R.id.p12_str_takeperson);
		takePhone = (TextView)findViewById(com.deppon.R.id.p12_str_takephone);
		takeAddress = (TextView)findViewById(com.deppon.R.id.p12_str_takeaddress);
		//detail info button
		btn_detail = (Button)findViewById(com.deppon.R.id.p12_btn_trackinfo);
	}
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
		if(itemId==0){
			Intent intent = new Intent(P12_GoodsTrackResult_Act.this,TabWidget.class);
			startActivity(intent);
		}
		if(itemId==1){
			controller.goBack();;
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
