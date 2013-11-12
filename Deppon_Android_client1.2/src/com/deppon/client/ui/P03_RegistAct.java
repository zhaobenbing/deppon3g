package com.deppon.client.ui;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.User;
import com.deppon.common.util.Exit;
/**
 * 注册界面
 * @author Administrator：赵本兵
 * @创建时间：2011-8-1
 */
public class P03_RegistAct extends Activity {
	//声明EditView控件
	private EditText  registEmail;
	private EditText registPhone;
	private EditText password;
	private EditText repwd;
	private Button registButton;
	private Button loginoutButton;
	//声明控制器ClientController
	private ClientController controller;
	//声明客户端上下文
	private ClientContext context;
	public static final int REGIST_FAIL = 500;
	public static final int REGIST_SUEECSS = 200;
	private ProgressDialog dialog;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
 			super.handleMessage(msg);
 			switch (msg.what) {
			case REGIST_SUEECSS:
				Toast.makeText(P03_RegistAct.this, "恭喜您，注册成功,请登陆", Toast.LENGTH_LONG).show();
				break;
			case REGIST_FAIL:
				Toast.makeText(P03_RegistAct.this, "该Email已被注册,请重新填写一个Email", Toast.LENGTH_LONG).show();
				break;
			}
		}
	};
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(com.deppon.R.layout.p03_regist);
 		setTitle("注册");
 		//全屏
 		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		//获取controller
 		controller = ClientController.getController(P03_RegistAct.this);
   		//通过controller得到context
 		context = controller.getContext();
 		LinkedList<Activity> link = (LinkedList<Activity>)context.getBusinessData("Activitys");
		link.add(this);
 		//初始化控件
 		initView();
 		//对注册按钮添加监听器
 		registButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = registEmail.getText().toString().trim();
				String phone = registPhone.getText().toString().trim();
				String pwd = password.getText().toString().trim();
				String rep = repwd.getText().toString().trim();
				//正则表达式，判断是否为email
				String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"; 
				Pattern regex = Pattern.compile(check); 
				Matcher matcher = regex.matcher(email.trim()); 
				boolean isMatched = matcher.matches(); 
				if(!isMatched){ 
					Toast.makeText(P03_RegistAct.this, "请输入正确的email", 2500).show();
					return;
				}
				//判断是否为电话号码
				String ch = "^1[358]\\d{9}$";
				regex = Pattern.compile(ch);
				matcher = regex.matcher(phone.trim());
				boolean isphone = matcher.matches();
				if(!isphone) {
					Toast.makeText(P03_RegistAct.this, "请输入正确的phone", 2500).show();
					return ;
				}
				if(pwd ==null || "".equals(pwd)) {
					Toast.makeText(P03_RegistAct.this, "请输入密码", 2500).show();
					return ;
				}
				if(!pwd.equals(rep)) {
					Toast.makeText(P03_RegistAct.this, "两次输入的秘密不一致，请重新输入", 2500).show();
					return ;
				}else {
					dialog = ProgressDialog.show(P03_RegistAct.this, "请等待...","正在注册...",true);
					User user = new User();
					user.setEmail(email);
					user.setTelephone(phone);
					user.setPassword(pwd);
					context.addBusinessData("Email", email);
					context.addBusinessData("Phone", phone);
					context.addBusinessData("Password", pwd);
					context.addBusinessData("RegistUser", user);
					new Thread(){
						@Override
						public void run() {
							 try {
								 Message msg = new Message();
								if(controller.k0301()){
									 msg.what = REGIST_SUEECSS;
									 handler.sendMessage(msg);
								 }else{
									 msg.what = REGIST_FAIL;
									 handler.sendMessage(msg);
								 }
							} catch (Exception e) {
 								e.printStackTrace();
							}finally{
								dialog.dismiss();
							}
						}
					}.start();
				}
			}
		});
 		//对退出按钮添加监听事件
 		loginoutButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				controller.k0302();
			}
		});
	}
	private void initView() {
		registEmail = (EditText)findViewById(com.deppon.R.id.p03_email);
		registPhone = (EditText)findViewById(com.deppon.R.id.p03_phone);
		password = (EditText)findViewById(com.deppon.R.id.p03_password);
		repwd = (EditText)findViewById(com.deppon.R.id.p03_repassword);
		registButton = (Button)findViewById(com.deppon.R.id.p03_button_regist);
		loginoutButton = (Button)findViewById(com.deppon.R.id.p03_button_logout);
	}
	//菜单menu
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
		if(itemId == 0) {
			Intent intent = new Intent(this,TabWidget.class);
			startActivity(intent);			
		}
		if(itemId == 1) {
			controller.goBack();;
		}
		if(itemId==2){
			new Exit(P03_RegistAct.this);
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
