package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;

public class TabSelfService extends Activity {
	private ClientController controller ;
	private ClientContext context;
	private ListView lv;
	private ArrayList<Map<String, ?>> list = new ArrayList<Map<String,?>>();
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(com.deppon.R.layout.listview);
		controller = ClientController.getController(this);
		context = controller.getContext();
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
		lv = (ListView)findViewById(com.deppon.R.id.lv);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("image",com.deppon.R.drawable.goods_track);
		map.put("introduce",getString(com.deppon.R.string.goods_track));
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("image",com.deppon.R.drawable.branch_query);
		map1.put("introduce",getString(com.deppon.R.string.branch_query));
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("image",com.deppon.R.drawable.price_query);
		map2.put("introduce",getString(com.deppon.R.string.price_query));
		
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("image",com.deppon.R.drawable.embargo_query);
		map3.put("introduce",getString(com.deppon.R.string.embargo_query));
		
		list.add(map);
		list.add(map1);
		list.add(map2);
		list.add(map3);
		
		ListAdapter adapter = new SimpleAdapter(this, list, com.deppon.R.layout.produce_service,
				new String[]{"image","introduce"}, new int[]{com.deppon.R.id.image,com.deppon.R.id.text});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){
 			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
 				switch (arg2) {
				case 0:
					Intent intent1 = new Intent(TabSelfService.this,P11_GoodsTrackAct.class);
					startActivity(intent1);
					break;
				case 1:
					Intent intent2 = new Intent(TabSelfService.this,P10_DeptSearchAct.class);
					startActivity(intent2);
					break;
				case 2:
					 Intent intent3 = new Intent(TabSelfService.this,P14_PriceSearchAct.class);
					 startActivity(intent3);
					break;
				case 3:
					 
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