package com.deppon.client.ui;

import java.util.LinkedList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.User;
import com.deppon.common.util.Exit;

/**
 * 该Activity用于登录
 * 
 * @author ：赵本兵
 * @创建时间：2011-7-30
 */
public class P02_LoginAct extends Activity {
	// 声明登录编辑组件
	private EditText loginEmail;
	// 登录密码
	private EditText loginPassword;
	// 声明登录,注册，退出按钮
	private Button k0201, k0202, k0203;
	// 声明ClientController
	private ClientController controller;
	// 声明ClientContext
	private ClientContext context;
	private SharedPreferences sp;
	private ProgressDialog m_dDialog;
  	public static int LOGIN_FAIL = 500,SECCUSS=200;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == LOGIN_FAIL) {
				Toast.makeText(P02_LoginAct.this, "用户名或密码错误", 2500).show();
			}
			if(msg.what == SECCUSS){
				//boolean isFirstLogin = sp.getBoolean("FirstLogin", true);
				//if(isFirstLogin){
					User user = (User) context.getBusinessData("CurrentUser");
					Editor edit = sp.edit();
					String email = (String)context.getBusinessData("email");
					String Pwd = (String)context.getBusinessData("Pwd");
					edit.putString("email", email);
					edit.putString("Pwd", Pwd);
					edit.putInt("id", user.getId());
					edit.putString("telephone", user.getTelephone());
					edit.putString("address",user.getAddress());
					edit.putString("city", user.getCity());
					edit.putString("province", user.getProvince());
					edit.putString("mobilephone", user.getMobilephone());
					edit.putString("name", user.getName());
					edit.putString("loginName", user.getLoginName());
					edit.putString("sex", user.getSex());
					edit.putBoolean("FirstLogin", false);
					edit.commit();
				//}
			}
		}
	};
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(com.deppon.R.layout.p02_login);
		setTitle("登录/注册");
		sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		// 全屏
	//	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 获得controller
		controller = ClientController.getController(P02_LoginAct.this);
		// 单利context
		context = controller.getContext();
		
 		initView();
 		
 		// TODO 以下代码编写日期:2011-8-1
		// 对登录按钮添加监听事件
		k0201.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// User user = (User) context.getBusinessData("CurrentUser");
	 			// String loginemail = null;
		 		// if(user!=null&&user.getEmail()!=null){
		 		// 	loginemail = user.getEmail();
		 		// }
				// 获取登录email
				final String email = loginEmail.getText().toString().trim();
				// 获取登录密码
				final String pwd = loginPassword.getText().toString().trim();
				// 把获取的email和pwd缓存运行时业务数据
				if ("".equals(email) || email == null) {
					Toast.makeText(P02_LoginAct.this, "请输入正确的用户名", 2500).show();
					return;
				}
				if ("".equals(pwd) || pwd == null) {
					Toast.makeText(P02_LoginAct.this, "请输入正确的密码", 2500).show();
					return;
				} 
				/**
				if(loginemail != null&&!"".equals(loginemail)){
					Toast.makeText(P02_LoginAct.this, "您已经登录", 2500).show();
					Intent intent = new Intent(P02_LoginAct.this,P05_NowOrderAct.class);
					startActivity(intent);
					return;
				}**/
				else {			
					context.addBusinessData("email", email);
					context.addBusinessData("Pwd", pwd);			 
					m_dDialog = ProgressDialog.show(P02_LoginAct.this,
							"请等待...", "正在为你登录...", true);
					new Thread() {
						@Override
						public void run() {
							try {
								Message message = new Message();
								if (!controller.k0201()) {
									message.what = LOGIN_FAIL;
								}else{
									message.what = SECCUSS;
								}
								handler.sendMessage(message);
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								m_dDialog.dismiss();
							}
						}
					}.start();
				}
			}
		});
		// 对注册按钮添加监听事件
		k0202.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 跳转到注册界面
				controller.k0202();
			}
		});
		// 对退出按钮添加监听事件
		k0203.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 退出应用
				controller.k0203();
			}
		});
	}

	/**
	 * 初始化组件
	 */
	@SuppressWarnings("unchecked")
	private void initView() {
		((LinkedList<Activity>)context.getBusinessData("Activitys")).add(this);
 		loginEmail = (EditText) findViewById(com.deppon.R.id.p02_user_name_et);
		loginPassword = (EditText) findViewById(com.deppon.R.id.p02_password_et);
		k0201 = (Button) findViewById(com.deppon.R.id.p02_k0201);
		k0202 = (Button) findViewById(com.deppon.R.id.p02_k0202);
		k0203 = (Button) findViewById(com.deppon.R.id.p02_k0203);
	}

	// menu 菜单
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
			Intent intent = new Intent(this, TabWidget.class);
			startActivity(intent);		
		}
		if (itemId == 1) {
			controller.goBack();
		}
		if(itemId==2){
			new Exit(P02_LoginAct.this);
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
