package com.deppon.common.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.deppon.client.service.ClientController;

public class Exit {
	//声明Controller,Context
	private ClientController controller;
	private Context context;
	public Exit(Context context) {
		this.context = context;
		controller = ClientController.getController();
		exit();
 	}
	public Exit(Context context ,Activity activity){
		this.context = context;
		this.controller = ClientController.getController(activity);
		exit();
 	}
	private void exit() {
 		Builder builder = new AlertDialog.Builder(context);
 		builder.setTitle("退出窗口提示");
 		builder.setMessage("确定退出吗");
 		builder.setPositiveButton("确定", new OnClickListener(){
 			@Override
			public void onClick(DialogInterface dialog, int which) {
 				controller.exit();
			}
  		});
 		builder.setNegativeButton("取消", null);
 		builder.create().show();
	}
	
}
