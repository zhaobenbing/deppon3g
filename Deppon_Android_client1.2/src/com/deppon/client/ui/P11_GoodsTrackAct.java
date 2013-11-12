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
import android.widget.EditText;
import android.widget.Toast;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;
/**
 * @功能描述：货物跟踪
 * @author 赵本兵
 * @创建日期：2011-10-17
 */
public class P11_GoodsTrackAct extends Activity{
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	private Button btn_track;
	private EditText wayBillNumber ;
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(com.deppon.R.layout.p11_goodstrack);
 		setTitle("货物跟踪");
 		//全屏
 		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		controller = ClientController.getController(this);
 		context = controller.getContext();
 		initView();
 		btn_track.setOnClickListener(new OnClickListener(){
 			@Override
			public void onClick(View v) {
 				String waybill = wayBillNumber.getText().toString().trim();
 				if(waybill==null||waybill.length()<7){
 					Toast.makeText(P11_GoodsTrackAct.this, "请输入正确的运单号", 2500).show();
 					return ;
 				}else{
 					context.addBusinessData("WayBillNumber", waybill);
 					if(controller.k1002()){
 	 					Intent intent = new Intent(P11_GoodsTrackAct.this,P12_GoodsTrackResult_Act.class);
 	 	 				startActivity(intent);
 	 				}else{
 	 					Toast.makeText(P11_GoodsTrackAct.this, "您输入的运单号不存在", 2500).show();
 	 				}
 				}
 			}
  		});
	}
	@SuppressWarnings("unchecked")
	private void initView() {
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
		btn_track = (Button)findViewById(com.deppon.R.id.p11_btn_b1101);
		wayBillNumber = (EditText)findViewById(com.deppon.R.id.p11_et_goodstrack);
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
			Intent intent = new Intent(P11_GoodsTrackAct.this,TabWidget.class);
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
