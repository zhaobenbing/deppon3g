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

public class TabSysManager extends Activity {
	private ListView lv;
	private ArrayList<Map<String, ?>> list = new ArrayList<Map<String,?>>();
	private ClientController controller;
	private ClientContext context;
	private SharedPreferences sp ;
 	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.deppon.R.layout.listview);
		//全屏
	//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		lv = (ListView)findViewById(com.deppon.R.id.lv);
		controller = ClientController.getController(this);
		context = controller.getContext();
		sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("image",com.deppon.R.drawable.login_or_regist);
		map.put("introduce",getString(com.deppon.R.string.login_or_regist));
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("image",com.deppon.R.drawable.modify_datum);
		map1.put("introduce",getString(com.deppon.R.string.modify_datum));
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("image",com.deppon.R.drawable.contact_manage);
		map2.put("introduce",getString(com.deppon.R.string.contact_manage));
		
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("image",com.deppon.R.drawable.modify_password);
		map3.put("introduce",getString(com.deppon.R.string.modify_password));
		
		list.add(map);
		list.add(map1);
		list.add(map2);
 		list.add(map3);
		ListAdapter adapter = new SimpleAdapter(this, list, com.deppon.R.layout.produce_service,
				new String[]{"image","introduce"}, new int[]{com.deppon.R.id.image,com.deppon.R.id.text});
		lv.setAdapter(adapter);
 		lv.setOnItemClickListener(new OnItemClickListener() {
 			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
 			  /**
 				User user = (User) context.getBusinessData("CurrentUser");
 			    String email ="";
 			    String pwd = "";
 			    if(user!=null){
 			    	email =user.getEmail();
 			        pwd = user.getPassword();
 			    }
 			    **/
 			   String email = sp.getString("email", "");
			    String pwd = sp.getString("Pwd", "");
 				if(position==0){
 					/**
 					System.out.println("===unloginemail----->"+email);
 					if(email != null&&!"".equals(email)){
 						Toast.makeText(TabSysManager.this, "您已经登录", 2500).show();
 						Intent intent = new Intent(TabSysManager.this,P05_NowOrderAct.class);
 						startActivity(intent);
 						return;
 					}**/
 					Intent intent = new Intent(TabSysManager.this,P02_LoginAct.class);
 					startActivity(intent);
 				}
 				if(position==1){
 					if((email==null||"".equals(email))&&(pwd==null||"".equals(pwd))){	
   						Toast.makeText(TabSysManager.this, "您还没有登录，请登录", Toast.LENGTH_LONG).show();
 						Intent intent = new Intent(TabSysManager.this,P02_LoginAct.class);
 						context.addBusinessData("personDatumFlag","personDatumFlag");
 	 					startActivity(intent);
  					}else{
  						Intent intent = new Intent(TabSysManager.this,P07_PersonDatum.class);
 						startActivity(intent);
  					}
 				}
 				if(position==2){
 					if((email==null||"".equals(email))&&(pwd==null||"".equals(pwd))){	
   						Toast.makeText(TabSysManager.this, "您还没有登录，请登录", Toast.LENGTH_LONG).show();
 						Intent intent = new Intent(TabSysManager.this,P02_LoginAct.class);
 						context.addBusinessData("contactFlag","contactFlag");
 						startActivity(intent);
  					}else{
  						Intent intent = new Intent(TabSysManager.this,P08_ContactAct.class);
 						startActivity(intent);
 					}
 				}
 				if(position==3){					
 					if((email==null||"".equals(email))&&(pwd==null||"".equals(pwd))){	
   						Toast.makeText(TabSysManager.this, "您还没有登录，请登录", Toast.LENGTH_LONG).show();
 						Intent intent = new Intent(TabSysManager.this,P02_LoginAct.class);
 	 					context.addBusinessData("modifyPwdFlag","modifyPwdFlag");
 	 					startActivity(intent);
  					}else{
  						Intent intent = new Intent(TabSysManager.this,P09_ModifyPwdAct.class);
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