package com.deppon.client.ui;

import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deppon.client.db.DatabaseHelper;
import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.City;
import com.deppon.common.beans.Province;
import com.deppon.common.beans.User;
/**
 * 欢迎界面（P01）
 * @author Administrator：赵本兵
 * @创建时间：2011-7-30
 */
public class P01_WelcomeAct extends Activity {
	//声明一个进度条
	private ProgressBar pb;
	//声明一个TextView
	private TextView progressText;
	//声明ClientController
	private ClientController controller;
	private ClientContext context;
	private DatabaseHelper db ;
	//声明Handler
	private Handler guiHandler;
	private SharedPreferences sp ;
	private boolean isFirst;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==200){
				setProgressBarIndeterminateVisibility(false);
			}
			else if(msg.what == 404){
				setProgressBarIndeterminateVisibility(false);
					AlertDialog.Builder builder = new AlertDialog.Builder(P01_WelcomeAct.this);
					builder.setTitle("糟糕了");
					builder.setMessage("服务器未开启或网络不可达");
 					builder.setPositiveButton("退出程序", new OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							pb.setProgress(0);
							context.addBusinessData("TimeOut", "");
							controller.exit();
						}
					});
					builder.create().show();
			}
		};
	};
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
 		setContentView(com.deppon.R.layout.p01_welcome);
 		setTitle("初始化...");
 		//全屏
 	//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		//获得controller实例
 		controller = ClientController.getController(P01_WelcomeAct.this);
 		context = controller.getContext();
  		guiHandler = new ClientGUIHandler();
  		sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
  		isFirst = sp.getBoolean("First", true);
  		LinkedList<Activity> link = (LinkedList<Activity>) context.getBusinessData("Activitys");
  		if(link==null){
  			link = new LinkedList<Activity>();
  			link.add(P01_WelcomeAct.this);
  			context.addBusinessData("Activitys",link );
  		}else{
  			link.add(P01_WelcomeAct.this);
  			context.addBusinessData("Activitys", link);
  		}
 		//初始化对应组件的实例
 		initView();
 		new Thread(){
 			@Override
 			public void run() {
 				setProgressBarIndeterminateVisibility(true);
 				Message msg = new Message();
 				if(isFirst){
 		 			try {
 		 				System.out.println("===first===");
 		 				/**
 		 				File file = new File("/mnt/sdcard/","cachecityId.txt");
 		 				if(!file.exists()){
 		 					file.createNewFile();
 		 				}**/
 						controller.init(guiHandler);
 						String timeout = (String) context.getBusinessData("TimeOut");
 						if((timeout!=null&&!"".equals(timeout))&&timeout.equals("TimeOut")){
 							System.out.println("==timeout=="+timeout);
 							msg.what = 404;
 							handler.sendMessage(msg);
 							return ;
 						}else{
 							msg.what=200;
 							handler.sendMessage(msg);
 						}
 						db = new DatabaseHelper(P01_WelcomeAct.this);
 						db.getWritableDatabase();
 						Map<String,Province> pros = (Map<String, Province>) context.getBusinessData("cacheProvinces");
 						for (Province p : pros.values()) {
 							db.insert(p);
 						}
 						Map<String,City> citys = (Map<String, City>) context.getBusinessData("cacheCitys");
 						for (City c : citys.values()) {
 							db.insert(c);
 						}
 						/**
 						String[] data = ParserData.parseKeyWords();
 						for (int i = 0; i < data.length; i++) {
							db.insert(new KeyWord(data[i]));
						}
						**/
 					   //获取编辑器,对文本进行编辑
 						Editor edit = sp.edit();
 						edit.putBoolean("First", false);
 						edit.commit();
 						//清除cacheCitys,cacheDepts
 						context.addBusinessData("cacheCitys", null);
 						context.addBusinessData("cacheDepts", null);
 					} catch (Exception e) {
 						e.printStackTrace();
 					}finally{
 						if(db!=null){
 							db.close();
 						}
  					}
 					//初始化完成，跳转到登陆界面
 					Intent intent = new Intent(P01_WelcomeAct.this,TabWidget.class);
 					try {
						finalize();
						System.gc();
						context.addBusinessData("TimeOut", null);
						context.addBusinessData("TimeOut", "");
					} catch (Throwable e) {
						e.printStackTrace();
					}
 					startActivity(intent);
 					//销毁当前的Activity
 					P01_WelcomeAct.this.finish();
 		 		}else{
 		 			try {
 		 				System.out.println("=== no first===");
 		 				//pb.setVisibility(View.INVISIBLE);
 		 				//progressText.setVisibility(View.INVISIBLE);
 		 				controller.init(guiHandler);
 						String timeout = (String) context.getBusinessData("TimeOut");
 						if((timeout!=null&&!"".equals(timeout))&&timeout.equals("TimeOut")){
 							System.out.println("==timeout=="+timeout);
 							msg.what = 404;
 							handler.sendMessage(msg);
 							return ;
 						}else{
 							msg.what=200;
 							handler.sendMessage(msg);
 						}
 						
 					   //获取编辑器,对文本进行编辑
 						Editor edit = sp.edit();
 						edit.putBoolean("First", false);
 						edit.commit();
 						} catch (Exception e) {
 							e.printStackTrace();
 						}
 						//初始化完成，跳转到主界面
 						Intent intent = new Intent(P01_WelcomeAct.this,TabWidget.class);
 						try {
							finalize();
							System.gc();
							context.addBusinessData("TimeOut", null);
							context.addBusinessData("TimeOut", "");
						} catch (Throwable e) {
							e.printStackTrace();
						}
 						startActivity(intent);
 						//销毁当前的Activity
 						P01_WelcomeAct.this.finish();
 		 		}
 				//boolean isFirstLogin = sp.getBoolean("FirstLogin", true);
 				//if(!isFirstLogin){
 					//初始化用户信息
	 		  		User user = new User();
	 		  		user.setEmail(sp.getString("email", ""));
	 		  		user.setPassword(sp.getString("Pwd", ""));
	 		  		user.setId(sp.getInt("id", 0));
	 		  		user.setTelephone(sp.getString("telephone", ""));
	 		  		user.setAddress(sp.getString("address", ""));
	 		  		user.setMobilephone(sp.getString("mobilephone", ""));
	 		  		user.setName(sp.getString("name", ""));
	 		  	    context.addBusinessData("CurrentUser", user);
 				//}
  			} 			
 		}.start();
 	}
	private void initView() {
		pb = (ProgressBar)findViewById(com.deppon.R.id.p01_progress);
		progressText = (TextView)findViewById(com.deppon.R.id.p01_progress_text);
	}
	/**
	 * 设置进度条的值
	 * @param value
	 */
	public void setProgressValue(int value){
		pb.setProgress(value);
	}
	/**
	 * 获得进度条的值
	 * @return
	 */
	public int getProgressValue(){
		return pb.getProgress();
	}
	public void setProgressText(String text){
		progressText.setText(text);
	}
	public String getProgressText(){
		return (String)progressText.getText();
	}
	private class ClientGUIHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
 			super.handleMessage(msg);
 			int value = msg.getData().getInt("Value");
  			String text = msg.getData().getString("Text");
  			P01_WelcomeAct.this.setProgressValue(value);
 			P01_WelcomeAct.this.setProgressText(text);
		}
	}
}
