package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;

public class TabBussiness extends Activity {
	//声明ClientController
	private ClientController controller;
	private ClientContext context;
 	private ListView lv;
 	private SharedPreferences sp ;
  	private ArrayList<Map<String, ?>> list = new ArrayList<Map<String,?>>();
 	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.deppon.R.layout.listview);
		controller = ClientController.getController(this);
		context = controller.getContext();
		sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
 		lv = (ListView)findViewById(com.deppon.R.id.lv);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("image",com.deppon.R.drawable.now_order);
		map.put("introduce",getString(com.deppon.R.string.now_order));
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("image",com.deppon.R.drawable.order_manage);
		map1.put("introduce",getString(com.deppon.R.string.order_manage));
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("image",com.deppon.R.drawable.online_pay);
		map2.put("introduce",getString(com.deppon.R.string.online_pay));
		
		list.add(map);
		list.add(map1);
		list.add(map2);
 		
		ListAdapter adapter = new SimpleAdapter(this, list, com.deppon.R.layout.produce_service,
				new String[]{"image","introduce"}, new int[]{com.deppon.R.id.image,com.deppon.R.id.text});
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
 			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
 				 String email = sp.getString("email", "");
 			    String pwd = sp.getString("Pwd", "");
				System.out.println("email===:"+email+"pwd===:"+pwd);
 				if(position == 0){					
 					if((email==null||"".equals(email))&&(pwd==null||"".equals(pwd))){	
   						Toast.makeText(TabBussiness.this, "您还没有登录，请登录", Toast.LENGTH_LONG).show();
 						Intent intent = new Intent(TabBussiness.this,P02_LoginAct.class);
 	 					startActivity(intent);
  					}else{
  						Intent intent = new Intent(TabBussiness.this,P05_NowOrderAct.class);
	 	 				startActivity(intent);
  					}
  				}
 				if(position == 1) {
 					if((email==null||"".equals(email))&&(pwd==null||"".equals(pwd))){
 						Toast.makeText(TabBussiness.this, "您还没有登录，请登录", Toast.LENGTH_LONG).show();
 						Intent intent = new Intent(TabBussiness.this,P02_LoginAct.class);
 						context.addBusinessData("ordMagFlag","ordMagFlag");
 	 					startActivity(intent);
 					}else{
 						Intent intent = new Intent(TabBussiness.this,P20_OrdMagUIAct.class);
 	 					startActivity(intent);
 					}
 				}
			}
		});
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