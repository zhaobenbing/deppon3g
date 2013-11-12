package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.deppon.client.db.DatabaseHelper;
import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.PriceBean;
import com.deppon.common.beans.Province;
import com.deppon.common.util.Exit;
import com.deppon.common.util.Sort;

/**
 * @功能描述：价格查询
 * @author ：赵本兵
 * @创建日期：2011-10-19
 */
public class P14_PriceSearchAct extends Activity {
	// 发货地下拉列表
	private Spinner sp_send_pro, sp_send_city;
	// 目的地下拉列表
	private Spinner sp_aim_pro, sp_aim_city;
	// 运输类型
	private Spinner sp_tratype;
	// 查询按钮
	private Button btn_beginsearch;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	private DatabaseHelper db;
	private ArrayAdapter<String> adapterPros, adapterCitys, adapterProa,
			adapterCitya, adapterTratype;
	private List<String> listPros, listCitys, listProa, listCitya, listTratype;
	public static String SELECTEDPROS, SELECTEDPROA, SELECTEDCITYS,
			SELECTEDCITYA, SELECTEDTRTYPE;
	public static int CHOOSEED = 301;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int sendMonth = 0, arrMonth = 0, sendDay = 0, arrDay = 0;
			if (msg.what == CHOOSEED) {
				Calendar c = Calendar.getInstance(Locale.CHINA);
				// 获取返回的价格时效信息
				final PriceBean pb = (PriceBean) context
						.getBusinessData("PriceSearchInfo");
				sendMonth = c.get(Calendar.MONTH) + 1;
				arrMonth = c.get(Calendar.MONTH) + 1;
				sendDay = (c.get(Calendar.DAY_OF_MONTH) + pb.getSendDates());
				arrDay = (c.get(Calendar.DAY_OF_MONTH) + pb.getArrDates());
				switch (c.get(Calendar.MONTH) + 1) {
				// 31天
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					if (sendDay > 31) {
						sendDay = sendDay - 31;
						sendMonth++;
						if (sendMonth > 12) {
							sendMonth = sendMonth - 12;
						}
					}
					if (arrDay > 31) {
						arrDay = arrDay - 31;
						arrMonth++;
						if (arrMonth > 12) {
							arrMonth = arrMonth - 12;
						}
					}
					break;
				// 30天
				case 4:
				case 6:
				case 9:
				case 11:
					if (sendDay > 30) {
						sendDay = sendDay - 30;
						sendMonth++;
					}
					if (arrDay > 30) {
						arrDay = arrDay - 30;
						arrMonth++;
					}
					break;
				case 2:
					// 判断是否为润年
					boolean isleap = ((GregorianCalendar) c).isLeapYear(c
							.get(Calendar.YEAR));
					// 润年29天
					if (isleap) {
						if (sendDay > 29) {
							sendDay = sendDay - 29;
							sendMonth++;
						}
						if (arrDay > 29) {
							arrDay = arrDay - 29;
							arrMonth++;
						}
					} else {
						if (sendDay > 28) {
							sendDay = sendDay - 28;
							sendMonth++;
						}
						if (arrDay > 28) {
							arrDay = arrDay - 28;
							arrMonth++;
						}
					}
				}
				String str1 = pb.getArrTimePoint() == null ? "" : pb.getArrTimePoint();
				String str2 =  pb.getSendTimePoint() == null ? "" : pb.getSendTimePoint();
				String arrData = arrMonth + "月" + arrDay + "日"+ str2+ "前";
 				String sendData = sendMonth + "月" + sendDay + "日"+str1;
				context.addBusinessData("sendData", arrData);
				context.addBusinessData("arrData", sendData);
 			}
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.deppon.R.layout.p14_pricesearch);
		setTitle("价格时效查询");
		// 全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initView();
		controller = ClientController.getController(this);
		context = controller.getContext();
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
		// 加载发货，目的省份,
		loadSpinnerProItem();
		// 加载运输类型
		loadTraType();
		// 处理发货省份下拉列表框
		sp_send_pro.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Spinner sp = (Spinner) arg0;
				SELECTEDPROS = sp.getSelectedItem().toString().trim();
				sp_tratype.setSelection(0);
				loadCitySItem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// 处理目的省份下拉列表框
		sp_aim_pro.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Spinner sp = (Spinner) arg0;
				SELECTEDPROA = sp.getSelectedItem().toString().trim();
				sp_tratype.setSelection(0);
				loadCityAItem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// 处理发货城市下拉列表框
		sp_send_city.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spinner sp = (Spinner) arg0;
				SELECTEDCITYS = sp.getSelectedItem().toString().trim();
				sp_tratype.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// 处理目的地城市下拉列表框
		sp_aim_city.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spinner sp = (Spinner) arg0;
				SELECTEDCITYA = sp.getSelectedItem().toString().trim();
				sp_tratype.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// 处理运输类型
		sp_tratype.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spinner sp = (Spinner) arg0;
				SELECTEDTRTYPE = sp.getSelectedItem().toString().trim();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// 对查询按钮进行监听
		btn_beginsearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SELECTEDPROS.equals("请选择省")) {
					Toast.makeText(P14_PriceSearchAct.this, "请选择发货地省份", 2500)
							.show();
					return;
				}
				if (SELECTEDCITYS.equals("请选择城市")) {
					Toast.makeText(P14_PriceSearchAct.this, "请选择发货地城市", 2500)
							.show();
					return;
				}
				if (SELECTEDPROA.equals("请选择省")) {
					Toast.makeText(P14_PriceSearchAct.this, "请选择目的地省份", 2500)
							.show();
					return;
				}
				if (SELECTEDCITYA.equals("请选择城市")) {
					Toast.makeText(P14_PriceSearchAct.this, "请选择目的地城市", 2500)
							.show();
					return;
				}
				if (SELECTEDTRTYPE.equals("请选择运输类型")) {
					Toast.makeText(P14_PriceSearchAct.this, "请选择运输类型", 2500)
							.show();
					return;
				} else {
					db = new DatabaseHelper(P14_PriceSearchAct.this);
					db.getReadableDatabase();
					// 获取发货城市Id
					Cursor cursor = db.select("city",
							new String[] { "oldCityId" }, "oldCityName= '"
									+ SELECTEDCITYS + "' ", null, null, null,
							null);
					try {
						if (cursor != null) {
							cursor.moveToNext();
							String cityId = cursor.getString(cursor
									.getColumnIndex("oldCityId"));
							context.addBusinessData("startCityId", cityId);
						}
						cursor = db.select("city",
								new String[] { "oldCityId" }, "oldCityName= '"
										+ SELECTEDCITYA + "' ", null, null,
								null, null);
						if (cursor != null) {
							cursor.moveToNext();
							String cityId = cursor.getString(cursor
									.getColumnIndex("oldCityId"));
							context.addBusinessData("endCityId", cityId);
						}
						context.addBusinessData("tranportType", SELECTEDTRTYPE);
						boolean bool = controller.k1401();

						if (bool) {

							if (SELECTEDTRTYPE.equals("精准卡航")
									|| SELECTEDTRTYPE.equals("精准城运")) {
								Message msg = new Message();
								msg.what = CHOOSEED;
								handler.sendMessage(msg);
							}
							Intent intent = new Intent(P14_PriceSearchAct.this,
									P15_PriceSearchResultAct.class);
							startActivity(intent);
						} else {
							Toast.makeText(P14_PriceSearchAct.this,
									"暂无此线路的价格时效信息，请重新查询", 2500).show();
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if(cursor!=null){
							cursor.close();
 						}
					}
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	protected void loadCityAItem() {
		db = new DatabaseHelper(P14_PriceSearchAct.this);
		db.getReadableDatabase();
		String provinceId = "";
		// 当选择省份时，加载城市下拉列表框
		Map<String, Province> pros = (Map<String, Province>) context
				.getBusinessData("cacheProvinces");
		String selectedPro = SELECTEDPROA;
		if (pros != null) {
			for (Province p : pros.values()) {
				if (p.getProvinceOldName().equals(selectedPro)) {
					provinceId = p.getProvinceOldId();
					System.out.println("==send provinceId=="
							+ p.getProvinceOldId());
				}
			}
		}

		if (provinceId != null || "".equals(provinceId)) {
			Cursor cursor = db.select("city", new String[] { "oldCityName" },
					"oldFather ='" + provinceId + "' ", null, null, null, "oldCityName");
			try {
				listCitya = new ArrayList<String>();
				listCitya.add("请选择城市");
				while (cursor.moveToNext()) {
					String cityName = cursor.getString(cursor
							.getColumnIndex("oldCityName"));
					listCitya.add(cityName);
				}
				adapterCitya = new ArrayAdapter<String>(
						P14_PriceSearchAct.this, R.layout.simple_spinner_item,
						Sort.sort(listCitya));
				sp_aim_city.setAdapter(adapterCitya);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cursor.close();
				db.close();
			}
		}
	}
	//加载城市
	@SuppressWarnings("unchecked")
	protected void loadCitySItem() {
		db = new DatabaseHelper(P14_PriceSearchAct.this);
		db.getReadableDatabase();
		String provinceId = "";
		// 当选择省份时，加载城市下拉列表框
		Map<String, Province> pros = (Map<String, Province>) context
				.getBusinessData("cacheProvinces");
		String selectedPro = SELECTEDPROS;
		if (pros != null) {
			for (Province p : pros.values()) {
				if (p.getProvinceOldName().equals(selectedPro)) {
					provinceId = p.getProvinceOldId();
				}
			}
		}
		if (provinceId != null || "".equals(provinceId)) {
			Cursor cursor = db.select("city", new String[] { "oldCityName" },
					"oldFather ='" + provinceId + "' ", null, null, null, "oldCityName");
			try {
				listCitys = new ArrayList<String>();
				listCitys.add("请选择城市");
				while (cursor.moveToNext()) {
					String cityName = cursor.getString(cursor
							.getColumnIndex("oldCityName"));
					listCitys.add(cityName);
				}
				System.out.println("====City count form database====="
						+ cursor.getCount());
				adapterCitys = new ArrayAdapter<String>(
						P14_PriceSearchAct.this, R.layout.simple_spinner_item,
						Sort.sort(listCitys));
				sp_send_city.setAdapter(adapterCitys);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cursor.close();
				db.close();
			}
		}
	}
	//加载运输类型
	private void loadTraType() {
		listTratype = new ArrayList<String>();
		listTratype.add("请选择运输类型");
		String[] transports = getResources().getStringArray(
				com.deppon.R.array.transportType);
		for (int i = 0; i < transports.length; i++) {
			listTratype.add(transports[i]);
		}
		adapterTratype = new ArrayAdapter<String>(P14_PriceSearchAct.this,
				R.layout.simple_spinner_item, listTratype);
		sp_tratype.setAdapter(adapterTratype);
	}
	//加载省份
	private void loadSpinnerProItem() {
		db = new DatabaseHelper(this);
		Cursor cursor = db.select("province",null,null, null, null, null, "findex");
		try {
			listPros = new ArrayList<String>();
			listProa = new ArrayList<String>();
			listPros.add("请选择省");
			listProa.add("请选择省");
			while (cursor.moveToNext()) {
				String provinceName = cursor.getString(cursor
						.getColumnIndex("oldProvinceName"));
				listPros.add(provinceName);
				listProa.add(provinceName);
			}
			// 发货adapter
			adapterPros = new ArrayAdapter<String>(P14_PriceSearchAct.this,
					R.layout.simple_spinner_item, listPros);
			sp_send_pro.setAdapter(adapterPros);
			// 目的adapter
			adapterProa = new ArrayAdapter<String>(P14_PriceSearchAct.this,
					R.layout.simple_spinner_item, listProa);
			sp_aim_pro.setAdapter(adapterProa);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
	}

	private void initView() {
		sp_send_pro = (Spinner) findViewById(com.deppon.R.id.p14_sp_pro_send);
		sp_send_city = (Spinner) findViewById(com.deppon.R.id.p14_sp_city_send);
		sp_aim_pro = (Spinner) findViewById(com.deppon.R.id.p14_sp_pro_aim);
		sp_aim_city = (Spinner) findViewById(com.deppon.R.id.p14_sp_city_aim);
		sp_tratype = (Spinner) findViewById(com.deppon.R.id.p14_sp_tratype);

		btn_beginsearch = (Button) findViewById(com.deppon.R.id.p14_btn_beginsearch);
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
		if (itemId == 0) {
			Intent intent = new Intent(P14_PriceSearchAct.this, TabWidget.class);
			startActivity(intent);
		}
		if (itemId == 1) {
			controller.goBack();;
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
