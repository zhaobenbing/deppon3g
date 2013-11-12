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
import com.deppon.common.util.Exit;

/**
 * @描述:系统管理 修改密码
 * @author Administrator:赵本兵
 * @创建时间：2011-9-27
 */
public class P09_ModifyPwdAct extends Activity {
	private WebView webView;
 	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	//基本URL
	public static String BASEURL ="";
	public String msg = "" ;
	@SuppressWarnings("unused")
	private SharedPreferences sp;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==200){
				Builder builder = new AlertDialog.Builder(P09_ModifyPwdAct.this);
				builder.setTitle("修改密码");
				builder.setMessage("恭喜您，修改密码成功");
				builder.setPositiveButton("现在登录", new OnClickListener(){
 					@Override
					public void onClick(DialogInterface dialog, int which) {
 						clearSaveInfo();
 						Intent intent = new Intent(P09_ModifyPwdAct.this,P02_LoginAct.class);
 						startActivity(intent);
 						P09_ModifyPwdAct.this.finish();
					}
				});
				builder.setNegativeButton("取消", null);
				builder.create().show();
			}
			if(msg.what==500){
				Toast.makeText(P09_ModifyPwdAct.this, "很遗憾,密码修改失败,请重新登陆", 2500).show();
				clearSaveInfo();
				Intent intent = new Intent(P09_ModifyPwdAct.this,P02_LoginAct.class);
				startActivity(intent);
			}
		};
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
		setContentView(com.deppon.R.layout.p09_modifypwd);
		controller = ClientController.getController(P09_ModifyPwdAct.this);
 		context = controller.getContext();
 		sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
 		BASEURL = context.getSystemProperty("BASE_URL");
		// 全屏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
		webView = (WebView)findViewById(com.deppon.R.id.p09_webview);
		WebSettings webSettings = webView.getSettings();
		//设置支持javascript
		webSettings.setJavaScriptEnabled(true);
		//设置支持 文件访问
		webSettings.setAllowFileAccess(true);
		//设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		//加载URL
		webView.loadUrl(BASEURL+context.getSystemProperty("MODIFYPASSWORD"));
		//设置webChromeClient
		webView.setWebChromeClient(new WebChromeClient(){
			//处理javascript中的alert
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
 				Builder builder = new Builder(P09_ModifyPwdAct.this);
				builder.setTitle("修改密码?");
				builder.setMessage(message);
 				builder.setPositiveButton("确定",new OnClickListener(){
 					@Override
					public void onClick(DialogInterface dialog, int which) {
 						result.confirm();
					}
 				});
				builder.setCancelable(false);
				builder.create().show();
				return true;
			};
  			//设置应用程序的title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				P09_ModifyPwdAct.this.setTitle(title);
				super.onReceivedTitle(view, title);
			};
		});
 		
 		//添加插件
 		webView.addJavascriptInterface(new Object(){
 			@SuppressWarnings("unused")
			public void showInfo(String message){
				msg = message;
				handler.post(new Runnable(){
 					@Override
					public void run() {
  						if(!msg.equals("false")){
 							String[] str = msg.split(",");
 							String oldpwd = str[0];
 							String newpwd = str[1];
 							context.addBusinessData("oldpwd", oldpwd);
 							context.addBusinessData("newpwd", newpwd);
 							Message msg = new Message();
 							if(controller.k0901()){
 								msg.what = 200;
 							//	Editor edit = sp.edit();
 							//	edit.putBoolean("FirstLogin", true);
 								//edit.commit();
 							}else{
 								msg.what = 500;
 							}
 							handler.sendMessage(msg);
 						}
  					}
 				});
			}			
		}, "modifypassword");
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
			Intent intent = new Intent(P09_ModifyPwdAct.this,TabWidget.class);
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
