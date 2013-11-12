package com.deppon.client.ui;

import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.User;
import com.deppon.common.util.Exit;
 

public class P07_PersonDatum extends Activity {
	//声明WebView
	private WebView webView;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	//基本URL
	public static String BASEURL ="";
	public String message = "" ;
	@SuppressWarnings("unused")
	private SharedPreferences sp;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==200){
				Builder builder = new AlertDialog.Builder(P07_PersonDatum.this);
				builder.setTitle("基本信息");
				builder.setMessage("恭喜您，保存基本信息成功,下次登录将生效");
				builder.setPositiveButton("现在登录", new OnClickListener(){
 					@Override
					public void onClick(DialogInterface dialog, int which) {
 						Intent intent = new Intent(P07_PersonDatum.this,P02_LoginAct.class);
 						clearSaveInfo();
 						startActivity(intent);
 						P07_PersonDatum.this.finish();
					}
				});
				builder.setNegativeButton("取消", null);
				builder.create().show();
			}else{
				Toast.makeText(P07_PersonDatum.this, "系统错误，请稍后重试", 2500).show();
			}
		}
		private void clearSaveInfo() {
			context.addBusinessData("CurrentUser", null);
			context.addBusinessData("modifyPwdFlag", null);
			context.addBusinessData("contactFlag", null);
			context.addBusinessData("personDatumFlag", null);
			context.addBusinessData("ordMagFlag", null);
			context.addBusinessData("email", null);
			context.addBusinessData("Pwd", null);
		}
	};
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(com.deppon.R.layout.p07_systemmanager);
 		controller = ClientController.getController(P07_PersonDatum.this);
 		context = controller.getContext();
 		sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
 		BASEURL=context.getSystemProperty("BASE_URL");
 		//全屏
 	//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
 		webView = (WebView)findViewById(com.deppon.R.id.p07_webview);
 		WebSettings webSettings = webView.getSettings();
 		//设置浏览器开启javascript
 		webSettings.setJavaScriptEnabled(true);
 		//设置可以访问文件
 		webSettings.setAllowFileAccess(true);
 		//设置支持绽放
 		webSettings.setBuiltInZoomControls(true);
  		webView.loadUrl(BASEURL+context.getSystemProperty("PERSONDATUM"));
  		//设置WebChromeClient
 		webView.setWebChromeClient(new WebChromeClient(){
  			//处理javascript中的alert
 			@Override
 			public boolean onJsAlert(WebView view, String url, String message,
 					  final JsResult result) {
 				Builder builder = new Builder(P07_PersonDatum.this);
 				builder.setTitle("提示对话框");
 				builder.setMessage(message);
 				builder.setPositiveButton("确定", new OnClickListener(){
 					@Override
					public void onClick(DialogInterface dialog, int which) {
 						result.confirm();
					}
 				});
 				builder.setCancelable(false);
 				builder.create().show();
  				return true;
 			};
 			//设置应用程序title
 			@Override
 			public void onReceivedTitle(WebView view, String title) {
 				setTitle(title);
 				super.onReceivedTitle(view, title);
 			}
 			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message,final JsResult result) {
 				Builder builder = new Builder(P07_PersonDatum.this);
 				builder.setTitle("带选择的对话框");
 				builder.setMessage(message);
 				builder.setPositiveButton("确定", new OnClickListener(){
 					@Override
					public void onClick(DialogInterface dialog, int which) {
 						result.confirm();
					}});
 				builder.setNegativeButton("取消", new OnClickListener(){
 					@Override
					public void onClick(DialogInterface dialog, int which) {
 						result.cancel();
					}});
 				builder.setCancelable(false);
 				builder.create().show();
 				return true;
 			};
 		});
 		
 	
 		//添加插件
		webView.addJavascriptInterface(new Object() {
			@SuppressWarnings("unused")
			public void show() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						webView.loadUrl("javascript:show()");
					}
				});
			};

			@SuppressWarnings("unused")
			public void initInfo() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						User user = (User) context.getBusinessData("CurrentUser");
						if(user!=null){
							String mobile = user.getMobilephone();
							String json = "";
							if(mobile!=null){
								json = user.getName() + "#"+ user.getTelephone()+"#"+mobile;
							}else{
								json = user.getName() + "#"+ user.getTelephone()+"#";
							}
							webView.loadUrl("javascript:initInfo('" + json + "')");
						}
					}
				});
			};

			@SuppressWarnings("unused")
			public void resultInfo(String messa) {
				message = messa;
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (!message.equals("false")) {
							String[] arr = message.split("#");
							User c = (User) context.getBusinessData("CurrentUser");
							User user = new User();
							user.setName(arr[0]);
							user.setTelephone(arr[1]);
							if (!arr[2].equals("without")) {
								user.setMobilephone(arr[2]);
							}
							user.setId(c.getId());
							user.setProvince(arr[3]);
							user.setCity(arr[4]);
							user.setAddress(arr[5]);
							context.addBusinessData("PersonDatum", user);
							Message msg = new Message();
							if (controller.k0701()) {
								msg.what = 200;
								//Editor edit = sp.edit();
 								//edit.putBoolean("FirstLogin", true);
 								//edit.commit();
							} else {
								msg.what = 500;
							}
							handler.handleMessage(msg);
						}
					}
				});
			}
		}, "system");
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
			Intent intent = new Intent(P07_PersonDatum.this,TabWidget.class);
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
  }
