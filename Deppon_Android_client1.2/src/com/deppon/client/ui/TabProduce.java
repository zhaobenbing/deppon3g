package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class TabProduce extends Activity {
	private ClientController controller ;
	private ClientContext context;
	private ListView lv;
	private ArrayList<Map<String, ?>> list = new ArrayList<Map<String,?>>();
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		controller = ClientController.getController(this);
		context = controller.getContext();
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
		setContentView(com.deppon.R.layout.listview);
		lv = (ListView)findViewById(com.deppon.R.id.lv);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("image",com.deppon.R.drawable.index_jzky);
		map.put("introduce",getString(com.deppon.R.string.airport_transport));
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("image",com.deppon.R.drawable.index_jzqy);
		map1.put("introduce",getString(com.deppon.R.string.truck_transport));
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("image",com.deppon.R.drawable.index_jzcy);
		map2.put("introduce",getString(com.deppon.R.string.city_transport));
		
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("image",com.deppon.R.drawable.index_jzkh);
		map3.put("introduce",getString(com.deppon.R.string.boat_transport));
		list.add(map);
		list.add(map1);
		list.add(map2);
		list.add(map3);
		
		ListAdapter adapter = new SimpleAdapter(this, list,com.deppon.R.layout.produce_service,
				new String[]{"image","introduce"}, new int[]{com.deppon.R.id.image,com.deppon.R.id.text});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					Intent intent1 = new Intent(TabProduce.this,P16_Air_TranAct.class);
					startActivity(intent1);
					break;
				case 1:
					Intent intent2 = new Intent(TabProduce.this,P17_CarTranAct.class);
					startActivity(intent2);
					break;
				case 2:
					Intent intent3 = new Intent(TabProduce.this,P18_CityTranAct.class);
					startActivity(intent3);
					break;
				case 3:
					Intent intent4 = new Intent(TabProduce.this,P19_CardAirhAct.class);
					startActivity(intent4);
					break;
				default:
					break;
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