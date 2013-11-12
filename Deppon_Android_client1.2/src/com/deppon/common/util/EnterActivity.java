package com.deppon.common.util;

import java.text.AttributedCharacterIterator.Attribute;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class EnterActivity extends Activity{
		// 写一个广播的内部类，当收到动作时，结束activity
		private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				close();
				unregisterReceiver(this); // 这句话必须要写要不会报错，不写虽然能关闭，会报一堆错
			}
		};

		@Override
		public void onResume() {
			super.onResume();

			// 在当前的activity中注册广播
			IntentFilter filter = new IntentFilter();
			filter.addAction(Attribute.LANGUAGE.toString());
			registerReceiver(this.broadcastReceiver, filter); // 注册
		}

		/**
		 * 关闭
		 */
		public void close() {
			Intent intent = new Intent();
			intent.setAction(Attribute.LANGUAGE.toString()); // 说明动作
			sendBroadcast(intent);// 该函数用于发送广播
			finish();
		}
	}