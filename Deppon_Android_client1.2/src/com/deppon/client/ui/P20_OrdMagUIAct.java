package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.Order;
import com.deppon.common.util.Exit;

public class P20_OrdMagUIAct extends Activity {
	private Spinner sp;
	private Button btn;
	private ListView lv;
 	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	private String[] string ;
	public static String SELECTED;
	private Map<String,String> m = null;
	private ArrayList<Map<String, ?>> list = new ArrayList<Map<String,?>>();
	private Map<String,Order> map;
	//private  Button btn_ok;
	//private  Button btn_cancle;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 200){
				Toast.makeText(P20_OrdMagUIAct.this, "该订单已被取消", 2500).show();
			}
		}
	};
	//基本URL
 	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(com.deppon.R.layout.p20_listman);
 		setTitle("订单管理");
 		controller = ClientController.getController(this);
 		context = controller.getContext();
  		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
  		init();
  		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
  		string = getResources().getStringArray(com.deppon.R.array.ordersearch);
 		ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,string);
 		sp.setAdapter(ad);
 		sp.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
 				Spinner sp = (Spinner)arg0;
 				SELECTED = sp.getSelectedItem().toString().trim();
 			}
 			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
 			}
 			
 		});
 		//初始化最近一笔订单
 		if(controller.k2004()){
			    map = (Map<String, Order>) context.getBusinessData("LastOrders");
				if(map==null){
					Toast.makeText(P20_OrdMagUIAct.this, "您还没有下过单，现在去下单吧", 2500).show();
					Intent intent = new Intent(P20_OrdMagUIAct.this,P05_NowOrderAct.class);
					startActivity(intent);
					return ;
				}
				for (Order or : map.values()) {
					m = new HashMap<String, String>();
					m.put("orderNumber",or.getOrderNumber());
					Date date = or.getOrderDate();
					String year = 1900+date.getYear()+"";
					String month = (date.getMonth()+1)+"";
					String day = date.getDate()+"";
					String hour = date.getHours()+"";
					String minutes = date.getMinutes()+"";
					String seconds = date.getSeconds()+"";
					String tempString = year+"-"+month+"-"+day+" " +hour+":"+minutes+":"+seconds;
			  		m.put("orderDate",tempString);
			  		m.put("goodsName", or.getGoodsName());
			  		list.add(m);
			}
				ListAdapter adapter = new SimpleAdapter(P20_OrdMagUIAct.this, list, com.deppon.R.layout.p20_orderman_text, 
		  				new String[]{"orderNumber","orderDate","goodsName"}, new int[]{com.deppon.R.id.p20_ordernumber,com.deppon.R.id.p20_orderdate,
		  				com.deppon.R.id.p20_goodsname}) ;
		  		lv.setAdapter(adapter);
 		sp.setSelection(3);
  		btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					// 每次重新获取数据
					if (list != null && list.size() > 0) {
						list.clear();
					}
					if (SELECTED.equals(string[0])) {
						list.clear();
						Toast.makeText(P20_OrdMagUIAct.this, "请选择查询订单类型", 2500)
								.show();
						return;
					}
					if (SELECTED.equals(string[1])) {
						if (controller.k2001()) {
							map = (Map<String, Order>) context
									.getBusinessData("AllOrders");
							if (map == null) {
								Toast.makeText(P20_OrdMagUIAct.this,
										"您还没有下过单，现在去下单吧", 2500).show();
								Intent intent = new Intent(
										P20_OrdMagUIAct.this,
										P05_NowOrderAct.class);
								startActivity(intent);
								return;
							}
							for (Order or : map.values()) {
								m = new HashMap<String, String>();
								m.put("orderNumber", or.getOrderNumber());
								String parsedate = parseDate(or.getOrderDate());
								m.put("orderDate", parsedate);
								m.put("goodsName", or.getGoodsName());
								list.add(m);
							}
							ListAdapter adapter = new SimpleAdapter(
									P20_OrdMagUIAct.this, list,
									com.deppon.R.layout.p20_orderman_text,
									new String[] { "orderNumber", "orderDate",
											"goodsName" }, new int[] {
											com.deppon.R.id.p20_ordernumber,
											com.deppon.R.id.p20_orderdate,
											com.deppon.R.id.p20_goodsname });
							lv.setAdapter(adapter);
							Toast.makeText(P20_OrdMagUIAct.this,
									"您总共下了" + map.size() + "笔单", 2500).show();
						}
						return;
					}
					if (SELECTED.equals(string[2])) {
						if (controller.k2001()) {
							map = (Map<String, Order>) context
									.getBusinessData("AllOrders");
							if (map == null) {
								Toast.makeText(P20_OrdMagUIAct.this,
										"您还没有下过单，现在去下单吧", 2500).show();
								Intent intent = new Intent(
										P20_OrdMagUIAct.this,
										P05_NowOrderAct.class);
								startActivity(intent);
								return;
							}
							for (Order or : map.values()) {
								m = new HashMap<String, String>();
								m.put("orderNumber", or.getOrderNumber());
								String parsedate = parseDate(or.getOrderDate());
								m.put("orderDate", parsedate);
								m.put("goodsName", or.getGoodsName());
								list.add(m);
							}
							ListAdapter adapter = new SimpleAdapter(
									P20_OrdMagUIAct.this, list,
									com.deppon.R.layout.p20_orderman_text,
									new String[] { "orderNumber", "orderDate",
											"goodsName" }, new int[] {
											com.deppon.R.id.p20_ordernumber,
											com.deppon.R.id.p20_orderdate,
											com.deppon.R.id.p20_goodsname });
							lv.setAdapter(adapter);
							Toast.makeText(P20_OrdMagUIAct.this,
									"您最近一周" + map.size() + "笔订单", 2500).show();
						}
						return;
					}
					if (SELECTED.equals(string[3])) {
						if (controller.k2004()) {
							map = (Map<String, Order>) context
									.getBusinessData("LastOrders");
							if (map == null) {
								Toast.makeText(P20_OrdMagUIAct.this,
										"您还没有下过单，现在去下单吧", 2500).show();
								Intent intent = new Intent(
										P20_OrdMagUIAct.this,
										P05_NowOrderAct.class);
								startActivity(intent);
								return;
							}
							for (Order or : map.values()) {
								m = new HashMap<String, String>();
								m.put("orderNumber", or.getOrderNumber());
								String parsedate = parseDate(or.getOrderDate());
								m.put("orderDate", parsedate);
								m.put("goodsName", or.getGoodsName());
								list.add(m);
							}
							ListAdapter adapter = new SimpleAdapter(
									P20_OrdMagUIAct.this, list,
									com.deppon.R.layout.p20_orderman_text,
									new String[] { "orderNumber", "orderDate",
											"goodsName" }, new int[] {
											com.deppon.R.id.p20_ordernumber,
											com.deppon.R.id.p20_orderdate,
											com.deppon.R.id.p20_goodsname });
							lv.setAdapter(adapter);
						}
						return;
					}
					if (SELECTED.equals(string[4])) {
						if (controller.k2003()) {
							map = (Map<String, Order>) context
									.getBusinessData("TreatedOrders");
							if (map == null) {
								Toast.makeText(P20_OrdMagUIAct.this,
										"您还没有下过单，现在去下单吧", 2500).show();
								Intent intent = new Intent(
										P20_OrdMagUIAct.this,
										P05_NowOrderAct.class);
								startActivity(intent);
								return;
							}
							for (Order or : map.values()) {
								m = new HashMap<String, String>();
								m.put("orderNumber", or.getOrderNumber());
								String parsedate = parseDate(or.getOrderDate());
								m.put("orderDate", parsedate);
								m.put("goodsName", or.getGoodsName());
								list.add(m);
							}
							ListAdapter adapter = new SimpleAdapter(
									P20_OrdMagUIAct.this, list,
									com.deppon.R.layout.p20_orderman_text,
									new String[] { "orderNumber", "orderDate",
											"goodsName" }, new int[] {
											com.deppon.R.id.p20_ordernumber,
											com.deppon.R.id.p20_orderdate,
											com.deppon.R.id.p20_goodsname });
							lv.setAdapter(adapter);
							Toast.makeText(P20_OrdMagUIAct.this,
									"您有" + map.size() + "笔订单已受理", 2500).show();
						}
						return;
					}
					if (SELECTED.equals(string[5])) {
						if (controller.k2002()) {
							map = (Map<String, Order>) context
									.getBusinessData("UntreatedOrders");
							if (map == null) {
								Toast.makeText(P20_OrdMagUIAct.this,
										"您还没有下过单，现在去下单吧", 2500).show();
								Intent intent = new Intent(
										P20_OrdMagUIAct.this,
										P05_NowOrderAct.class);
								startActivity(intent);
								return;
							}
							for (Order or : map.values()) {
								m = new HashMap<String, String>();
								m.put("orderNumber", or.getOrderNumber());
								String parsedate = parseDate(or.getOrderDate());
								m.put("orderDate", parsedate);
								m.put("goodsName", or.getGoodsName());
								list.add(m);
							}
							ListAdapter adapter = new SimpleAdapter(
									P20_OrdMagUIAct.this, list,
									com.deppon.R.layout.p20_orderman_text,
									new String[] { "orderNumber", "orderDate",
											"goodsName" }, new int[] {
											com.deppon.R.id.p20_ordernumber,
											com.deppon.R.id.p20_orderdate,
											com.deppon.R.id.p20_goodsname });
							lv.setAdapter(adapter);
							Toast.makeText(P20_OrdMagUIAct.this,
									"您还有" + map.size() + "笔单未受理,请耐心等待", 2500)
									.show();
						}
						return;
					}
					if (SELECTED.equals(string[6])) {
						if (controller.k2006()) {
							map = (Map<String, Order>) context
									.getBusinessData("FindCancledOrders");
							if (map == null) {
								Toast.makeText(P20_OrdMagUIAct.this,
										"您还没有下过单，现在去下单吧", 2500).show();
								Intent intent = new Intent(
										P20_OrdMagUIAct.this,
										P05_NowOrderAct.class);
								startActivity(intent);
								return;
							}
							for (Order or : map.values()) {
								m = new HashMap<String, String>();
								m.put("orderNumber", or.getOrderNumber());
								String parsedate = parseDate(or.getOrderDate());
								m.put("orderDate", parsedate);
								m.put("goodsName", or.getGoodsName());
								list.add(m);
							}
							ListAdapter adapter = new SimpleAdapter(
									P20_OrdMagUIAct.this, list,
									com.deppon.R.layout.p20_orderman_text,
									new String[] { "orderNumber", "orderDate",
											"goodsName" }, new int[] {
											com.deppon.R.id.p20_ordernumber,
											com.deppon.R.id.p20_orderdate,
											com.deppon.R.id.p20_goodsname });
							lv.setAdapter(adapter);
							Toast.makeText(P20_OrdMagUIAct.this,
									"您已经取消了" + map.size() + "笔订单", 2500).show();
						}
						return;
					}
				}

				private String parseDate(Date orderDate) {
					Date date = orderDate;
					String year = 1900 + date.getYear() + "";
					String month = (date.getMonth() + 1) + "";
					String day = date.getDate() + "";
					String hour = date.getHours() + "";
					String minutes = date.getMinutes() + "";
					String seconds = date.getSeconds() + "";
					String tempString = year + "-" + month + "-" + day + " "
							+ hour + ":" + minutes + ":" + seconds;
					return tempString;
				}
			});
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if (string[5].equals(SELECTED)) {
						HashMap<String, String> map = (HashMap<String, String>) arg0
								.getItemAtPosition(arg2);
						context.addBusinessData("updateOrderNum", map
								.get("orderNumber"));
						Builder builder = new AlertDialog.Builder(
								P20_OrdMagUIAct.this);
						builder.setTitle("友情提示");
						builder.setMessage("您要做什么?");
						builder.setPositiveButton("取消该订单", new MyListerner());
						builder.setNegativeButton("等待受理", null);
						builder.create().show();
					}
				}

				class MyListerner implements DialogInterface.OnClickListener {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Thread() {
							@Override
							public void run() {
								if (controller.k2005()) {
									Message msg = new Message();
									msg.what = 200;
									handler.sendMessage(msg);
								}
							}
						}.start();
					}
				}
			});
		}
	}
	private void init() {
		lv = (ListView)findViewById(com.deppon.R.id.p20_lv);
		sp = (Spinner)findViewById(com.deppon.R.id.p20_sp);
		btn = (Button)findViewById(com.deppon.R.id.p20_btn);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, getString(com.deppon.R.string.menu_index));
		menu.add(0, 1, 1, getString(com.deppon.R.string.menu_order));
		menu.add(0, 2, 2, getString(com.deppon.R.string.menu_back));
		menu.add(0, 3, 3, getString(com.deppon.R.string.menu_logout));
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case 0:
			Intent intent = new Intent(P20_OrdMagUIAct.this,TabWidget.class);
			startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent(P20_OrdMagUIAct.this,P05_NowOrderAct.class);
			startActivity(intent1);
			break;
		case 2:
			controller.goBack();
			break;
		case 3:
			new Exit(this);
			break;
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
