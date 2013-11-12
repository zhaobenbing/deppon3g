package com.deppon.client.ui;

import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;

public class P06_OrderManageAct extends Activity {
	private WebView webView;
    private Handler mHandler = new Handler();  
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	//基本URL
	public static String BASEURL ="";
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(com.deppon.R.layout.p06_ordermanager);
 		setTitle("订单管理");
 		controller = ClientController.getController(this);
 		context = controller.getContext();
 		BASEURL = context.getSystemProperty("BASE_URL");
 		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
 		webView = (WebView)this.findViewById(com.deppon.R.id.p06_webview);
 		//设置浏览器开启javascript
 		webView.getSettings().setJavaScriptEnabled(true);
 		//设置支持访问文件
 		webView.getSettings().setAllowFileAccess(true);
 		//设置支持缩放
 		webView.getSettings().setBuiltInZoomControls(true);
 		//添加插件
 		webView.addJavascriptInterface(new JSOrderClass(), "order");
 		//webView.loadUrl("file:///android_asset/orderManger.html");
 		webView.loadUrl(BASEURL+context.getSystemProperty("ORDERMANAGER"));
 		//webView.loadUrl("http://192.168.14.104:8092/deppon_android_service/index.jsp");
 		//设置WebChromeClient
 		webView.setWebChromeClient(new WebChromeClient(){
 			//处理Javascript中的alert
 			@Override
 			public boolean onJsAlert(WebView view, String url, String message,
 					final JsResult result) {
 				//构建一个Buider来显示网页中的对话框
 				Builder builder = new Builder(P06_OrderManageAct.this);
 				builder.setTitle("提示对话框");
 				builder.setMessage(message);
 				builder.setPositiveButton(android.R.string.ok,new OnClickListener(){
 					@Override
					public void onClick(DialogInterface dialog, int which) {
 						//点击确定按钮之后，继续执行网页中的操作
 						result.confirm();
					}
  				} );
 				builder.setCancelable(false);
 				builder.create().show();
  				return true;
 			 }
 		  });
 		 webView.setWebViewClient(new WebViewClient(){
 			 @Override
 			public boolean shouldOverrideUrlLoading(WebView view, String url) {
 				 view.loadUrl(url);
  				return true;
 			}
  		 });
	}
	public class JSOrderClass{
		//跳转到订单界面
		public void addOrder(){
			Intent intent = new Intent(P06_OrderManageAct.this,P05_NowOrderAct.class);
			startActivity(intent);
		}
		//详情
		public void Detail(){
			webView.loadUrl(BASEURL+context.getSystemProperty("ORDERDETAIL"));
		}
		//返回
		public void goback(){
			//Intent intent = new Intent(P06_OrderManageAct.this,P06_OrderManageAct.class);
			//startActivity(intent);
			if(webView.canGoBack()){
				webView.goBack();
			}
		}
		//test
		public void test(){
			 mHandler.post(new Runnable() {  
				 public void run() {  
					 webView.loadUrl("javascript:alertshow()");
				}  
			 });
		}
		//返回上一级
		public void back(){
			P06_OrderManageAct.this.finish();
		}
		//首页
		public void goindex(){
			Intent intent = new Intent(P06_OrderManageAct.this,TabWidget.class);
			startActivity(intent);
		}
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
		if(itemId == 0){
			Intent intent = new Intent(P06_OrderManageAct.this,TabWidget.class);
			startActivity(intent);
		}
		if(itemId == 1){
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
