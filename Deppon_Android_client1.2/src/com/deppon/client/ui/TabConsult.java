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

public class TabConsult extends Activity {
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
		map.put("image",com.deppon.R.drawable.consult);
		map.put("introduce",getString(com.deppon.R.string.consult));
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("image",com.deppon.R.drawable.proposal);
		map1.put("introduce",getString(com.deppon.R.string.proposal));
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("image",com.deppon.R.drawable.complain);
		map2.put("introduce",getString(com.deppon.R.string.complain));
		
		list.add(map);
		list.add(map1);
		list.add(map2);
		ListAdapter adapter = new SimpleAdapter(this, list, com.deppon.R.layout.produce_service,
				new String[]{"image","introduce"}, new int[]{com.deppon.R.id.image,com.deppon.R.id.text});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					Intent intent1 = new Intent(TabConsult.this,P21_ConsultAct.class);
					startActivity(intent1);
					break;
				case 1:
					Intent intent2 = new Intent(TabConsult.this,P22_AdviceAct.class);
					startActivity(intent2);
					break;
				case 2:
					Intent intent3 = new Intent(TabConsult.this,P23_ComplainAct.class);
					startActivity(intent3);
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