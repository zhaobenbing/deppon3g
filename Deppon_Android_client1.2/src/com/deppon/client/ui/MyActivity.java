package com.deppon.client.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

/**
 * @功能描述：自定义菜单
 * @author 赵本兵
 * @创建日期：2011-11-08
 */
public class MyActivity extends Activity{
 	private  View menuView;
	private  GridView toolbarGrid;
	private AlertDialog menuDialog;// menu菜单Dialog
	/** 底部菜单图片 **/
	public static int[] menu_toolbar_image_array = { com.deppon.R.drawable.controlbar_homepage,
		com.deppon.R.drawable.controlbar_backward_enable,
		com.deppon.R.drawable.controlbar_forward_enable, com.deppon.R.drawable.controlbar_window,
		com.deppon.R.drawable.controlbar_showtype_list };
	/** 底部菜单文字 **/
	public  static String[] menu_toolbar_name_array = { "首页", "后退", "前进", "创建", "菜单" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.deppon.R.layout.menu);
		// 创建AlertDialog
		menuDialog = new AlertDialog.Builder(this).create();
		menuDialog.setView(menuView);
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// 监听按键
					dialog.dismiss();
				return false;
			}
		});
		// 设置自定义menu菜单
		menuView = View.inflate(this, com.deppon.R.layout.gridview_menu, null);
		
		toolbarGrid = (GridView) findViewById(com.deppon.R.id.GridView_toolbar);
		toolbarGrid.setBackgroundResource(com.deppon.R.drawable.channelgallery_bg);// 设置背景
		toolbarGrid.setNumColumns(5);// 设置每行列数
		toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
		toolbarGrid.setVerticalSpacing(10);// 垂直间隔
		toolbarGrid.setHorizontalSpacing(10);// 水平间隔
		toolbarGrid.setAdapter(getMenuAdapter(this,menu_toolbar_name_array,
				menu_toolbar_image_array));// 设置菜单Adapter
	}
	
	/**
	 * 构造菜单Adapter
	 * 
	 * @param menuNameArray
	 *            名称
	 * @param imageResourceArray
	 *            图片
	 * @return SimpleAdapter
	 */
	private static SimpleAdapter getMenuAdapter(Context context,String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(context, data,
				com.deppon.R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { com.deppon.R.id.item_image, com.deppon.R.id.item_text });
		return simperAdapter;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("nenu");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	/**
	 * 拦截MENU
	 */
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (menuDialog == null) {
			menuDialog = new AlertDialog.Builder(this).setView(menuView).show();
		} else {
			menuDialog.show();
		}
		return false;// 返回为true 则显示系统menu
	}
}
