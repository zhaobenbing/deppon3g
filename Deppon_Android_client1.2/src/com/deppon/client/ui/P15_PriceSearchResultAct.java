package com.deppon.client.ui;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.PriceBean;
import com.deppon.common.util.Exit;

/**
 * @功能描述：价格查询
 * @author ：赵本兵
 * @创建日期：2011-10-19
 */
public class P15_PriceSearchResultAct extends Activity {
	private LinearLayout linear_take, linear_send, linear_worktime;
	//空运LinearLayout
	private LinearLayout linear_monring,linear_non,linear_night,linear_schedule;
	private TextView tv_morning,tv_non,tv_night,tv_schedule;
	private TextView tv_title_sendtime, tv_worktime;
	// 发货日期
	//private DatePicker picker;
	private TextView tv_wgoods, tv_lgoods, tv_minprice, tv_takegoods,
			tv_sendgoods;
	private Button choosedate;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	private Calendar c;
	public static final int CHANAGED = 100;
	public static String INITARR,INITSEND;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
 			if(msg.what==CHANAGED){
 				Bundle bundle = msg.getData();
 				// 预计总部可提货时间
 				tv_takegoods.setText(bundle.getInt("sendMonth") + "月"+(bundle.getInt("sendDay"))+ "日 "+bundle.getString("sendTimePoint"));
				// 预计总部可派送时间
				tv_sendgoods.setText(bundle.getInt("arrMonth") + "月"+ (bundle.getInt("arrDay"))+"日 "+ bundle.getString("arrTimePoint"));
 			}
		}
	};
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.deppon.R.layout.p15_pricesearchresult);
		setTitle("价格时效查询结果");
		// 全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);	
		controller = ClientController.getController(this);
		context = controller.getContext();
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
		initView();
		// 运输类型
		String str = (String) context.getBusinessData("tranportType");
		// 获取返回的价格时效信息
		final PriceBean pb = (PriceBean) context.getBusinessData("PriceSearchInfo");
		if (str.equals("精准卡航") || str.equals("精准城运")) {
			// 设置日期控件，linear_take，linear_send,tv_title_sendtime可见
			//picker.setVisibility(View.VISIBLE);
			linear_take.setVisibility(View.VISIBLE);
			linear_send.setVisibility(View.VISIBLE);
			tv_title_sendtime.setVisibility(View.VISIBLE);
			choosedate.setVisibility(View.VISIBLE);
			c = Calendar.getInstance(Locale.CHINA);
			
			choosedate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new DatePickerDialog(P15_PriceSearchResultAct.this,new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view,final int year, final int monthOfYear,final int dayOfMonth) {
								System.out.println("=====now date===="+year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
								int sendMonth = monthOfYear+1,arrMonth = monthOfYear+1;
								int sendDay = (dayOfMonth+ pb.getSendDates());
								int arrDay = (dayOfMonth+ pb.getArrDates());
								switch(monthOfYear+1){
								//31天
								case 1:
								case 3:
								case 5:
								case 7:
								case 8:
								case 10:
								case 12:
									if(sendDay>31){
										sendDay = sendDay -31;
										sendMonth ++;
										if(sendMonth>12){
											sendMonth = sendMonth - 12 ;
										}
									}
									if(arrDay>31){
										arrDay = arrDay - 31;
										arrMonth ++;
										if(arrMonth>12){
											arrMonth = arrMonth - 12 ;
										}
									}
									break;
								//30天
								case 4:
								case 6:
								case 9:
								case 11:
									if(sendDay>30){
										sendDay = sendDay -30;
										sendMonth ++;
									}
									if(arrDay>30){
										arrDay = arrDay - 30;
										arrMonth ++;				
									}
									break;
								case 2:
									//判断是否为润年
									boolean isleap = ((GregorianCalendar)c).isLeapYear(year);
									//润年29天
									if(isleap){
										if(sendDay>29){
											sendDay = sendDay -29;
											sendMonth ++;											
										}
										if(arrDay>29){
											arrDay = arrDay - 29;
											arrMonth ++;											
										}
									}else{
										if(sendDay>28){
											sendDay = sendDay -28;
											sendMonth ++;											
										}
										if(arrDay>28){
											arrDay = arrDay - 28;
											arrMonth ++;											
										}
									}
								}								
								Message msg = new Message();
								msg.what = CHANAGED;
								Bundle bundle = new Bundle();
								bundle.putInt("arrMonth", arrMonth);
								bundle.putInt("sendMonth", sendMonth);
 								bundle.putInt("arrDay", arrDay);
								bundle.putInt("sendDay",sendDay);
								bundle.putString("sendTimePoint", pb.getArrTimePoint() == null ? "" : pb.getArrTimePoint());
								bundle.putString("arrTimePoint", pb.getSendTimePoint() == null ? "": pb.getSendTimePoint()+"前");
								msg.setData(bundle);
								handler.sendMessage(msg);
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
				}
			});
			// 初始化数据
			// 重货
			tv_wgoods.setText(pb.getWeightPrice() + "元/公斤");
			// 轻货
			tv_lgoods.setText(pb.getLightPrice() + "元/立方");
			// 最低一票
			tv_minprice.setText(pb.getMinPrice() + "元");			 		
			String senddata = (String) context.getBusinessData("sendData");
			String arrdata = (String) context.getBusinessData("arrData");
			// 预计总部可提货时间
			tv_takegoods.setText(arrdata);
 			// 预计总部可派送时间
			tv_sendgoods.setText(senddata);					
 		} else if(str.equals("精准空运")){
 			linear_monring.setVisibility(View.VISIBLE);
 			linear_non.setVisibility(View.VISIBLE);
 			linear_night.setVisibility(View.VISIBLE);
 			linear_schedule.setVisibility(View.VISIBLE);
 			tv_morning.setText(pb.getMorning()+"元/公斤");
 			tv_non.setText(pb.getNoon()+"元/公斤");
 			tv_night.setText(pb.getNight()+"元/公斤");
 			tv_schedule.setText(pb.getSchedule()+"   (提示：若班期为1234567表示周一到周日均有出发航班)");
 		}else {
			// 设置linear_worktime可见
			linear_worktime.setVisibility(View.VISIBLE);
			// 初始化数据
			// 重货
			tv_wgoods.setText(pb.getWeightPrice() + "元/公斤");
			// 轻货
			tv_lgoods.setText(pb.getLightPrice() + "元/立方");
			// 最低一票
			tv_minprice.setText(pb.getMinPrice() + "元");
			// 运行时效
			tv_worktime.setText(pb.getWorkTime());
		}
	}

	private void initView() {
		//picker = (DatePicker) findViewById(com.deppon.R.id.p15_senddate);
		tv_wgoods = (TextView) findViewById(com.deppon.R.id.p15_str_wgoods);
		tv_lgoods = (TextView) findViewById(com.deppon.R.id.p15_str_lgoods);
		tv_minprice = (TextView) findViewById(com.deppon.R.id.p15_str_minprice);
		tv_takegoods = (TextView) findViewById(com.deppon.R.id.p15_str_takegoods);
		tv_sendgoods = (TextView) findViewById(com.deppon.R.id.p15_str_sendgoods);
		tv_worktime = (TextView) findViewById(com.deppon.R.id.p15_str_worktime);
		tv_title_sendtime = (TextView) findViewById(com.deppon.R.id.p15_tv_sendtime);

		choosedate = (Button) findViewById(com.deppon.R.id.p15_choosedate);

		linear_take = (LinearLayout) findViewById(com.deppon.R.id.p15_linear_taketime);
		linear_send = (LinearLayout) findViewById(com.deppon.R.id.p15_linear_sendtime);
		linear_worktime = (LinearLayout) findViewById(com.deppon.R.id.p15_linear_worktime);
		
		linear_monring = (LinearLayout) findViewById(com.deppon.R.id.p15_layout_morning);
		linear_non = (LinearLayout) findViewById(com.deppon.R.id.p15_layout_non);
		linear_night = (LinearLayout) findViewById(com.deppon.R.id.p15_layout_night);
		linear_schedule = (LinearLayout) findViewById(com.deppon.R.id.p15_layout_schedule);
 		tv_morning = (TextView) findViewById(com.deppon.R.id.p15_str_morning);
		tv_non = (TextView) findViewById(com.deppon.R.id.p15_str_non);
		tv_night = (TextView) findViewById(com.deppon.R.id.p15_str_night);
		tv_schedule = (TextView) findViewById(com.deppon.R.id.p15_str_schedule);
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
			Intent intent = new Intent(P15_PriceSearchResultAct.this,
					TabWidget.class);
			startActivity(intent);
		}
		if (itemId == 1) {
			controller.goBack();
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
