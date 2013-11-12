package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.deppon.client.db.DatabaseHelper;
import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.Dept;
import com.deppon.common.beans.Province;
import com.deppon.common.util.Exit;
import com.deppon.common.util.Sort;
/**
 * @功能描述：网点查询UI
 * @author :赵本兵
 * @创建日期：2011-10-14
 */
public class P10_DeptSearchAct extends Activity {
	private ListView lv ;
	private Spinner proSpinner,citySpinner;
	//private EditText et_searchDept ;
	private AutoCompleteTextView et_searchDept;
	private Button btn_search;
	private DatabaseHelper db;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	private ArrayAdapter<String> adapterPro,adapterCity;
	private 	List<String> fromP,fromC;
	public static String SELECTEDPRO = null, SELECTEDCITY = null ,SELECTDEPT = null;
	public static String BEFORE=null;
	//private 	PrintWriter pw;
	//private BufferedReader br ;
	private Map<String,String> convert = null;
 	private ArrayList<Map<String, ?>> list = new ArrayList<Map<String,?>>();
 	
 	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(com.deppon.R.layout.p10_selfservice_deptsearch);
 		setTitle("网点查询");
 		//全屏
 		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
 		controller = ClientController.getController(this);
		context = controller.getContext();
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
		init();
 		//加载省份
 		initProvinces();
 		// 处理省份下拉列表框
 		proSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
 				new Thread(){
 					@Override
 					public void run() {
 						Spinner sp = (Spinner)arg0;
 						SELECTEDPRO = sp.getSelectedItem().toString().trim();
  					}
 				}.start();
   				loadSpinnerCityItem();
 				//清除list
   				if(list != null){
 					list.clear();
 					lv.setAdapter(null);
 					System.gc();
   				}
 				 et_searchDept.setText("");
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
 				new Thread(){
 					@Override
 					public void run() {
 						Spinner sp = (Spinner)arg0;
 						SELECTEDCITY = sp.getSelectedItem().toString().trim();
  					}
 				}.start();
				//清除list
 				if(list != null){
 					list.clear();
 					lv.setAdapter(null);
					System.gc();
   				}
 				 et_searchDept.setText("");
  				Cursor cursor = db.select("city", new String[] { "cityId" },
						"cityName ='" + SELECTEDCITY + "' ", null, null, null, null);
				try {		
					if(cursor.moveToNext()) {
						String cityId = cursor.getString(cursor.getColumnIndex("cityId"));
						context.addBusinessData("searchDeptCityId",cityId);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					cursor.close();
					db.close();
				}
 			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
 			}
		});
 		/**
 		//搜索提示
 		//关联关键字
 		String[] autoString = null ;
 		int count = 0;
 		Cursor cursor = db.select("keywords", new String[] { "keyword" },
				"keyword like' %" + et_searchDept + "%' ", null, null, null, null);
		try {		
			autoString = new String[cursor.getCount()];
			while(cursor.moveToNext()) {
				String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
				autoString[count] = keyword;
				count++;
 			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
 		ArrayAdapter<String> adapter = new ArrayAdapter<String>(P10_DeptSearchAct.this, android.R.layout.simple_dropdown_item_1line, autoString);
 		et_searchDept.setAdapter(adapter);
 		**/
 		//处理查询按钮
 		btn_search.setOnClickListener(new OnClickListener(){
 			@Override
			public void onClick(View v) {
 				new Thread(){
 					@Override
 					public void run() {
 						BEFORE = et_searchDept.getText().toString().trim();
  					}
 				}.start();
  				String searchDept = et_searchDept.getText().toString().trim();
 				if(SELECTEDPRO.equals("请选择省份")&&SELECTEDCITY.equals("请选择城市")&&"".equals(searchDept)){
 					Toast.makeText(P10_DeptSearchAct.this, "请输入关键字或选择省份,城市查询", 2500).show();
 					return ;
  				}else if(!SELECTEDPRO.equals("请选择省份")&&SELECTEDCITY.equals("请选择城市")){
  					if((BEFORE==null||"".equals(searchDept))){
  						Toast.makeText(P10_DeptSearchAct.this, "请继续输入条件查询", 2500).show();
  	 					return ;
  	    			}
  					if((BEFORE.equals(searchDept))){
  						Toast.makeText(P10_DeptSearchAct.this, "请重新查询", 2500).show();
  	 					return ;
  	    			}
  				}
 				//初始化工作
 				if(list != null){
 					list.clear();
 					lv.setAdapter(null);
					System.gc();
   				}
  				context.addBusinessData("searchDept",searchDept);
  				db = new DatabaseHelper(P10_DeptSearchAct.this);
				db.getReadableDatabase();
				Map<String,String> map = null;
  				try {
  					/**
  					pw = new PrintWriter(new OutputStreamWriter(
  							new FileOutputStream("/mnt/sdcard/cachecityId.txt",true),"utf-8"));
  					File file = new File("/mnt/sdcard/cachecityId.txt");
  					br = new BufferedReader(new InputStreamReader(new FileInputStream(file)),2*1024);
  					String str = "";
  					map = new HashMap<String,String>();
  					while((str=br.readLine())!=null){
  						map.put(str.trim().toString(), str.trim().toString());
  					}
  					String cityId = (String) context.getBusinessData("searchDeptCityId");
  					if(cityId!=null&&!map.containsValue(cityId)){
  						pw.println(cityId);
  					}
  					pw.flush();
  					pw.close();
					br.close();
					**/
  					map = new HashMap<String,String>();
  					String cityId = (String) context.getBusinessData("searchDeptCityId");
  					String str = "";
				    Cursor cursor = db.select("cityIds", new String[] { "cityId"},
							"cityId ='" + cityId + "' ", null, null, null, null);
					while(cursor.moveToNext()) {
							str = cursor.getString(cursor.getColumnIndex("cityId"));
							map.put(str.trim().toString(), str.trim().toString());
					}
					if(cityId!=null&&!map.containsValue(cityId)){
  						db.insert(cityId);
  					}
					cursor.close(); 
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					db.close();
				}
				//关键字查询
				if(BEFORE!=null&&!"".equals(BEFORE)){
					System.out.println("=======keywords");
					// 否则向服务器发送网点查询请求
					controller.k1001();
					final Map<String, Dept> depts = (Map<String, Dept>) context.getBusinessData("searchDeptResult");
					if (depts == null || depts.size() == 0) {
						Builder builder = new AlertDialog.Builder(P10_DeptSearchAct.this);
						builder.setTitle("抱歉");
						builder.setMessage("您查询的网点还没有添加或输入的关键字不精确,请重新查询");
						builder.setNegativeButton("返回", null);
						builder.create().show();
						return;
					}
					if (depts != null) {
						for (Dept d : depts.values()) {
							convert = new HashMap<String, String>();
							convert.put("deptName", d.getDeptName());
							list.add(convert);
						}
					}
					db.close();
					ListAdapter adapter = new SimpleAdapter(P10_DeptSearchAct.this,list, com.deppon.R.layout.p10_listlayout,
							new String[] { "deptName" },
							new int[] { com.deppon.R.id.text });
					lv.setAdapter(adapter);
					return ;
				}else{//非关键字查询
					// 如果已经选择过，则从数据库读取网点名称
					String cityid = (String) context.getBusinessData("searchDeptCityId");
					if (cityid != null && map.containsValue(cityid)) {
						System.out.println("=======from sqlpite3");
						Cursor cursor = db.select("dept",new String[] { "deptName" }, "cityId ='" + cityid
								+ "' ", null, null, null, null);
						System.out.println("==========>existed=depts size=>"+ cursor.getCount());
						while (cursor.moveToNext()) {
							String deptName = cursor.getString(cursor.getColumnIndex("deptName"));
							convert = new HashMap<String, String>();
							convert.put("deptName", deptName);
							list.add(convert);
						}
						cursor.close();
						db.close();
						ListAdapter adapter = new SimpleAdapter(P10_DeptSearchAct.this,list, com.deppon.R.layout.p10_listlayout,
								new String[] { "deptName" },
								new int[] { com.deppon.R.id.text });
						lv.setAdapter(adapter);
						return;
					} else {
						System.out.println("=======province city  not keywords");
						// 否则向服务器发送网点查询请求
						controller.k1001();
						final Map<String, Dept> depts = (Map<String, Dept>) context.getBusinessData("searchDeptResult");
						if (depts == null || depts.size() == 0) {
							Builder builder = new AlertDialog.Builder(P10_DeptSearchAct.this);
							builder.setTitle("抱歉");
							builder.setMessage("您查询的网点还没有添加或输入的关键字不精确,请重新查询");
							builder.setNegativeButton("返回", null);
							builder.create().show();
							return;
						}
						if (depts != null) {
							// 插入数据库
							for (Dept d : depts.values()) {
								db.insert(d);
								convert = new HashMap<String, String>();
								convert.put("deptName", d.getDeptName());
								list.add(convert);
							}
						}
						db.close();
					}
					ListAdapter adapter = new SimpleAdapter(P10_DeptSearchAct.this,list, com.deppon.R.layout.p10_listlayout,
							new String[] { "deptName" },
							new int[] { com.deppon.R.id.text });
					lv.setAdapter(adapter);
				}
				
			}
  		});
 		lv.setOnItemClickListener(new OnItemClickListener(){
 				@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				 SELECTDEPT = arg0.getItemAtPosition(arg2).toString().trim().split("=")[1].toString();
				 SELECTDEPT = SELECTDEPT.replace("}", "").trim();
 				 String deptInfo = "",address = "" ,phone = "";
				 Map<String,Dept> map = (Map<String, Dept>) context.getBusinessData("searchDeptResult");
				
				 if(map != null){
 					for (Dept d : map.values()) {
 	 					if(d.getDeptName().equals(SELECTDEPT)){
 	 						address = d.getDeptAddress();
 	 						phone = d.getDeptPhone();
 	 					}
 					}
	 			}
				
			  Cursor cursor = db.select("dept", new String[] { "deptAddress" ,"deptPhone"},
					"deptName ='" + SELECTDEPT + "' ", null, null, null, null);
				if(cursor.moveToNext()) {
					address = cursor.getString(cursor.getColumnIndex("deptAddress"));
					phone = cursor.getString(cursor.getColumnIndex("deptPhone"));
				}
				cursor.close(); 	
				Builder builder = new AlertDialog.Builder(P10_DeptSearchAct.this); 
				builder.setTitle("网点信息");
				if(deptInfo==null||((address==null||"".equals(address))&&(phone==null)||"".equals(phone))){
					builder.setMessage("暂无该网点信息");
				}else{
					deptInfo = "地址："+address+"\n"+"电话："+phone;
					builder.setMessage(deptInfo);
 				}
				builder.setNegativeButton("返回", null);
				builder.create().show();
			}
	 	});
	}
	@SuppressWarnings("unchecked")
	protected void loadSpinnerCityItem() {
		db = new DatabaseHelper(P10_DeptSearchAct.this);
		db.getReadableDatabase();
		String provinceId = "";
		// 当选择省份时，加载城市下拉列表框
		Map<String, Province> pros = (Map<String, Province>) context.getBusinessData("cacheProvinces");
		String selectedPro = SELECTEDPRO;
		if(pros != null){
			for (Province p : pros.values()) {
				if (p.getProvinceName().equals(selectedPro)) {
					provinceId = p.getProvinceId();
				}
			}
		}
 		if (provinceId != null || "".equals(provinceId)) {
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
				adapterCity = new ArrayAdapter<String>(
						P10_DeptSearchAct.this, R.layout.simple_spinner_item,
						Sort.sort(fromC));
				citySpinner.setAdapter(adapterCity);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cursor.close();
				db.close();
			}
		}
	}
	
	private void initProvinces() {
		db = new DatabaseHelper(this);
		Cursor cursor = db.select("province",null,null, null, null, null, "findex");
		try {
			fromP = new ArrayList<String>();
			fromP.add("请选择省份");
			while (cursor.moveToNext()) {
				String provinceName = cursor.getString(cursor.getColumnIndex("provinceName"));
				fromP.add(provinceName);
			}
			System.out.println("====Province count form database====="+ cursor.getCount());
		    adapterPro = new ArrayAdapter<String>(P10_DeptSearchAct.this, R.layout.simple_spinner_item, fromP);
			proSpinner.setAdapter(adapterPro);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
	}
	private void init() {
		proSpinner = (Spinner)findViewById(com.deppon.R.id.p10_sp_province);
		citySpinner =(Spinner)findViewById(com.deppon.R.id.p10_sp_city);
		et_searchDept = (AutoCompleteTextView)findViewById(com.deppon.R.id.p10_et_deptname);
		btn_search = (Button)findViewById(com.deppon.R.id.p10_btn_b1001);
		lv = (ListView)findViewById(com.deppon.R.id.lv);
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
			Intent intent = new Intent(P10_DeptSearchAct.this,TabWidget.class);
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
