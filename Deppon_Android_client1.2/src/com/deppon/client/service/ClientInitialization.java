package com.deppon.client.service;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ClientInitialization {
	private ClientContext context;
	private Activity activity;
	private Handler handler;
 	private int[] processValue={5,10,15,20,40,50,65,78,85,100};
 	private String[] processText={"正在比较版本号....",
			"正在加载配置信息..." ,
			"正在加载网点信息....",
			"正在更新业务数据....",
			"正在加载配置信息..." ,
			"正在加载网点信息....",
			"正在更新业务数据....",
			"正在加载配置信息..." ,
			"正在加载网点信息....",
			"正在更新业务数据...."
			};
	public ClientInitialization(ClientContext context,Activity activity,Handler handler){
		this.context=context;
		this.activity=activity;
		this.handler=handler;
	}
	
	/**
	 * 客户端程序初始化方法
	 */
	public void init(){
		for (int i = 0; i < processValue.length; i++) {
			switch(i){
			case 0 :
				sendMessage(i);
				//比较程序版本
				compareProgramVersion();
			case 1:
				sendMessage(i);
			case 2:
				sendMessage(i);
				//配置
				loadClientConfig();
			case 3:
				sendMessage(i);
			case 4:
				//加载网点信息
				sendMessage(i);
			case 5:
				sendMessage(i);
			case 6:
				sendMessage(i);
			case 7:
				sendMessage(i);
			case 8:
				sendMessage(i);
				//更新业务数据
		 		updateBusinessData();
			case 9:
				sendMessage(i);
				break ;
			}
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//发送消息 使 进度条的数据 于页面显示动态的变动
	public void sendMessage(int index){
		Message mess = handler.obtainMessage();
		Bundle bund = new Bundle();
		bund.putString("Text", processText[index]);
		bund.putInt("Value",processValue[index]);
		mess.setData(bund);
		handler.sendMessage(mess);
		
	}
	
	public void loadClientConfig(){
		//context.get
	}
	/**
	 * 比较程序版本是否一致
	 */
	private void compareProgramVersion(){
		
	}
	/**
	 * 更新业务数据
	 */
 	private void updateBusinessData(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
	}

	public ClientContext getContext() {
		return context;
	}

	public void setContext(ClientContext context) {
		this.context = context;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
 	
}
