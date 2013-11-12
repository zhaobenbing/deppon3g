package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.deppon.client.db.DatabaseHelper;
import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.Dept;
import com.deppon.common.beans.Province;
import com.deppon.common.beans.User;
import com.deppon.common.util.Exit;
import com.deppon.common.util.Sort;

/**
 * @功能：现在下单界面
 * @author Administrator：赵本兵
 * @创建时间：2011-9-22
 */
public class P05_NowOrderAct extends Activity {
	// 托运人
	private EditText p05_et_consignor;
	// 联系电话
	private EditText p05_et_phone;
	// 货物名称
	private EditText p05_et_goodsName;
	// 省份，城市,网点下拉列表
	private Spinner proSpinner, citySpinner, deptSpinner;
	// 网点信息
	private Button btn_deptInfo;
	private Button btn_sendOrder;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	private DatabaseHelper db;
	private ArrayAdapter<String> adapterPro, adapterCity, adapterDept;
	private List<String> fromP, fromC, fromD;
	// 基本URL
	public static String BASEURL = "";
	public static String SELECTEDPRO = "", SELECTEDCITY, SELECTDEPT;
	public static int SUCCESS = 200, FAIL = 500;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == SUCCESS) {
				Toast.makeText(P05_NowOrderAct.this, "下单成功", 2500).show();
			} else {
				Toast.makeText(P05_NowOrderAct.this, "下单失败", 2500).show();
			}
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.deppon.R.layout.p05_noworder);
		setTitle("现在下单");
		controller = ClientController.getController(this);
		context = controller.getContext();
		BASEURL = context.getSystemProperty("BASE_URL");
		// 全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
		init();
		// 获取当前用户信息
		User user = (User) context.getBusinessData("CurrentUser");
		if(user!=null){
			// 初始化托运人，联系电话
			p05_et_consignor.setText(user.getName());
			p05_et_consignor.setTextColor(Color.GRAY);
			p05_et_phone.setText(user.getTelephone());
			p05_et_phone.setTextColor(Color.GRAY);
		}
 		loadSpinnerProItem();
		// 每次初始化deptSpinner
		adapterDept = new ArrayAdapter<String>(P05_NowOrderAct.this,
				R.layout.simple_spinner_item, new String[] {"请选择营业网点"});
		deptSpinner.setAdapter(adapterDept);
		// 处理省份下拉列表框
		proSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Spinner sp = (Spinner) arg0;
				SELECTEDPRO = sp.getSelectedItem().toString().trim();
				loadSpinnerCityItem();
				adapterDept = new ArrayAdapter<String>(P05_NowOrderAct.this,
						R.layout.simple_spinner_item,
						new String[] { "请选择营业网点" });
				deptSpinner.setAdapter(adapterDept);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		// 处理城市下拉列表框
		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Spinner sp = (Spinner) arg0;
				SELECTEDCITY = sp.getSelectedItem().toString().trim();
				adapterDept = new ArrayAdapter<String>(P05_NowOrderAct.this,
						R.layout.simple_spinner_item,
						new String[] { "请选择营业网点" });
				deptSpinner.setAdapter(adapterDept);
				context.addBusinessData("cacheDepts", null);
				loadSpinnerDeptItem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// 处理营业网点下拉列表框
		deptSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
 			@Override
			public void onItemSelected(final AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Spinner sp = (Spinner) arg0;
				SELECTDEPT = sp.getSelectedItem().toString().trim();
				context.addBusinessData("requestDept", SELECTDEPT);
				Map<String, Dept> depts = (Map<String, Dept>) context.getBusinessData("cacheDepts");
				if (depts != null) {
					for (Dept d : depts.values()) {
						if (SELECTDEPT.equals(d.getDeptName())) {
							context.addBusinessData("OrderDeptId", d.getDeptid());
						}
					}
				}
				context.addBusinessData("deptInfo", null);
				controller.k0502();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		btn_deptInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String deptInfo = (String) context.getBusinessData("deptInfo");
				AlertDialog.Builder builder = new AlertDialog.Builder(
						P05_NowOrderAct.this);
				builder.setTitle("网点地址");
				if (deptInfo == null) {
					builder.setMessage("暂无该网点信息");
				} else {
					builder.setMessage(deptInfo);
				}
				builder.setNegativeButton("返回", null);
				builder.create().show();
			}
		});

		btn_sendOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String consignor = p05_et_consignor.getText().toString() .trim();
				final String phone = p05_et_phone.getText().toString().trim();
				final String goodsName = p05_et_goodsName.getText().toString() .trim();
				if (consignor == null || "".equals(consignor)) {
					Toast.makeText(P05_NowOrderAct.this, "请填写托运人", 2500).show();
					return;
				}
				if (phone == null || "".equals(phone)) {
					Toast.makeText(P05_NowOrderAct.this, "请填写联系电话", 2500)
							.show();
					return;
				}if (goodsName == null || "".equals(goodsName)) {
					Toast.makeText(P05_NowOrderAct.this, "请填写货物名称", 2500)
							.show();
					return;
				} 	if (SELECTEDPRO.equals("请选择省份")) {
					Toast.makeText(P05_NowOrderAct.this, "请选择省份", 2500).show();
					return;
				}
				if (SELECTDEPT.equals("请选择城市")) {
					Toast.makeText(P05_NowOrderAct.this, "请选择城市", 2500).show();
					return;
				}
				if (SELECTDEPT.equals("请选择营业网点")) {
					Toast.makeText(P05_NowOrderAct.this, "请选择营业网点", 2500).show();
					return;
				}else {
					new Thread() {
						@Override
						public void run() {
							context.addBusinessData("consignor", consignor);
							context.addBusinessData("phone", phone);
							context.addBusinessData("goodsName", goodsName);
							boolean bool = controller.k0503();
							if (bool) {
								Message msg = new Message();
								msg.what = SUCCESS;
								handler.sendMessage(msg);
							}
						}
					}.start();
					Intent intent = new Intent();
					intent.setClass(P05_NowOrderAct.this,
							P20_OrdMagUIAct.class);
					startActivity(intent);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void loadSpinnerDeptItem() {
		fromD = new ArrayList<String>();
		fromD.add("请选择营业网点");
		// 根据城市Id查找网点请求
		db = new DatabaseHelper(P05_NowOrderAct.this);
		db.getReadableDatabase();
		Cursor cursor = db.select("city", new String[] { "cityId" },
				"cityName= '" + SELECTEDCITY + "' ", null, null, null, null);
		// 获取城市Id
		try {
			if (cursor != null) {
				if(cursor.moveToNext()){
					System.out.println("==depts count=====" + cursor.getCount());
					String cityId = cursor.getString(cursor.getColumnIndex("cityId"));
					context.addBusinessData("cityId", cityId);
				}			
			}
			// 当选择城市时，加载网点下拉列表框
			controller.k0501();

			Map<String, Dept> depts = (Map<String, Dept>) context
					.getBusinessData("cacheDepts");
			if (depts != null) {
				for (Dept d : depts.values()) {
					fromD.add(d.getDeptName());
				}
			}
			adapterDept = new ArrayAdapter<String>(P05_NowOrderAct.this,
					R.layout.simple_spinner_item, fromD);
			deptSpinner.setAdapter(adapterDept);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}

	}

	@SuppressWarnings("unchecked")
	private void loadSpinnerCityItem() {
		db = new DatabaseHelper(P05_NowOrderAct.this);
		db.getReadableDatabase();
		String provinceId = "";
		// 当选择省份时，加载城市下拉列表框
		Map<String, Province> pros = (Map<String, Province>) context
				.getBusinessData("cacheProvinces");
		String selectedPro = SELECTEDPRO;
		if (pros != null) {
			for (Province p : pros.values()) {
				if (p.getProvinceName().equals(selectedPro)) {
					provinceId = p.getProvinceId();
				}
			}
		}
		if (provinceId != null || "".equals(provinceId)) {
			System.out.println("===SelectedProvinceId===" + provinceId);
			Cursor cursor = db.select("city", new String[] { "cityName" },
					"father ='" + provinceId + "' ", null, null, null, null);
			try {
				fromC = new ArrayList<String>();
				fromC.add("请选择城市");
				while (cursor.moveToNext()) {
					String cityName = cursor.getString(cursor
							.getColumnIndex("cityName"));
					fromC.add(cityName);
				}
				System.out.println("====City count form database====="
						+ cursor.getCount());
				adapterPro = new ArrayAdapter<String>(P05_NowOrderAct.this,
						R.layout.simple_spinner_item, Sort.sort(fromC));
				citySpinner.setAdapter(adapterPro);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cursor.close();
				db.close();
			}
		}
	}

	private void loadSpinnerProItem() {
		db = new DatabaseHelper(this);
		Cursor cursor = db.select("province",null,null, null, null, null, "findex");
		try {
			fromP = new ArrayList<String>();
			fromP.add("请选择省份");
			while (cursor.moveToNext()) {
				String provinceName = cursor.getString(cursor
						.getColumnIndex("provinceName"));
				fromP.add(provinceName);
			}
			System.out.println("====Province count form database====="
					+ cursor.getCount());
			adapterCity = new ArrayAdapter<String>(P05_NowOrderAct.this,
					R.layout.simple_spinner_item, fromP);
			proSpinner.setAdapter(adapterCity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
	}

	private void init() {
		btn_sendOrder = (Button) findViewById(com.deppon.R.id.p05_btn_sendOrder);
		p05_et_consignor = (EditText) findViewById(com.deppon.R.id.p05_et_consignor);
		p05_et_phone = (EditText) findViewById(com.deppon.R.id.p05_et_phone);
		p05_et_goodsName = (EditText) findViewById(com.deppon.R.id.p05_et_goodsName);
		btn_deptInfo = (Button) findViewById(com.deppon.R.id.p05_btn_deptInfo);

		proSpinner = (Spinner) findViewById(com.deppon.R.id.p05_sp_province);
		citySpinner = (Spinner) findViewById(com.deppon.R.id.p05_sp_city);
		deptSpinner = (Spinner) findViewById(com.deppon.R.id.p05_sp_dept);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, getString(com.deppon.R.string.menu_index));
		menu.add(0, 1, 1, getString(com.deppon.R.string.menu_ormanager));
		menu.add(0, 2, 2, getString(com.deppon.R.string.menu_back));
		menu.add(0, 3, 3, getString(com.deppon.R.string.menu_logout));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case 0:
			Intent intent = new Intent(this,TabWidget.class);
			startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent(this,P20_OrdMagUIAct.class);
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
