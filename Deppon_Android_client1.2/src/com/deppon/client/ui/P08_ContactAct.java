package com.deppon.client.ui;

import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.Exit;

public class P08_ContactAct extends Activity {
	private WebView webView;
	private Handler handler = new Handler();
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
 		setContentView(com.deppon.R.layout.p08_contact);
 		controller = ClientController.getController(P08_ContactAct.this);
 		context = controller.getContext();
 		BASEURL = context.getSystemProperty("BASE_URL");
 		//全屏
 		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
 		webView = (WebView)findViewById(com.deppon.R.id.p08_webview);
 		WebSettings webSettings = webView.getSettings();
 		//设置浏览器开启javascript
 		webSettings.setJavaScriptEnabled(true);
 		//设置可以访问文件
 		webSettings.setAllowFileAccess(true);
 		//设置支持缩放
 		webSettings.setBuiltInZoomControls(true);
 		webView.loadUrl(BASEURL+context.getSystemProperty("CONTACT"));
 		//设置WebViewClient
 		webView.setWebViewClient(new WebViewClient(){
 			@Override
 			public boolean shouldOverrideUrlLoading(WebView view, String url) {
 				view.loadUrl(url);
  				return true;
 			}
 			@Override
 			public void onPageFinished(WebView view, String url) {
  				super.onPageFinished(view, url);
 			}
 			@Override
 			public void onPageStarted(WebView view, String url, Bitmap favicon) {
  				super.onPageStarted(view, url, favicon);
 			}
 		});
 		//设置WebChromeClient
 		webView.setWebChromeClient(new WebChromeClient(){
 			//处理javascript中的alert
 			@Override
 			public boolean onJsAlert(WebView view, String url, String message,
 					final JsResult result) {
 				Builder builder = new Builder(P08_ContactAct.this);
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
 			//设置应用程序中的title
 			@Override
 			public void onReceivedTitle(WebView view, String title) {
 				P08_ContactAct.this.setTitle(title);
  				super.onReceivedTitle(view, title);
 			};
 			
 		});
 		//添加插件
 		webView.addJavascriptInterface(new Object(){
 			@SuppressWarnings("unused")
			public void showAddress(){
 				handler.post(new Runnable(){
 					@Override
					public void run() {
 						webView.loadUrl("javascript:showAddress()");
					}
 				});
 			};
 			//搜索联系人
 			@SuppressWarnings("unused")
			public void searchContact(){
 				handler.post(new Runnable(){
 					@Override
					public void run() {
 						webView.loadUrl(BASEURL+context.getSystemProperty("SEARCHCONTACT"));
					}
  				});
 			};
 			//开始搜索
 			@SuppressWarnings("unused")
			public void startSearch(){
 				handler.post(new Runnable(){
 					@Override
 					public void run() {
  						webView.loadUrl("javascript:startSearch()");
 					}
 				});
 			};
 			//所有联系人
 			@SuppressWarnings("unused")
			public void allContact(){
 				handler.post(new Runnable(){
 					@Override
 					public void run() {
  						webView.loadUrl(BASEURL+context.getSystemProperty("ALLCONTACT"));
 					}
 				});
 			};
 			//显示所有联系人
 			@SuppressWarnings("unused")
			public void showAllContact(){
 				handler.post(new Runnable(){
 					@Override
 					public void run() {
  						webView.loadUrl("javascript:showAllContact()");
 					}
 				});
 			}
 		}, "contact");
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
			Intent intent = new Intent(P08_ContactAct.this,TabWidget.class);
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
