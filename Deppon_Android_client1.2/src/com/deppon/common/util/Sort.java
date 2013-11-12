package com.deppon.common.util;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @功能描述：中文排序
 * @author ：赵本兵
 * @创建日期：2011-11-2
 */
public class Sort {
	static ArrayList<SortEntry> sortList = new ArrayList<SortEntry>();
	static class SortEntry{
		public String key;
	}
	@SuppressWarnings("unchecked")
	private static Comparator cmp = Collator.getInstance(Locale.CHINA);
	private static Comparator<SortEntry> comparator = new Comparator<SortEntry>(){
		@SuppressWarnings("unchecked")
		@Override
		public int compare(SortEntry object1, SortEntry object2) {
			return cmp.compare(object1.key,object2.key);
		}
	};
	public static List<String> sort(List<String> str){
		ArrayList<SortEntry> sortList = new ArrayList<SortEntry>();
		ArrayList<String> sortedList = new ArrayList<String>();
		for (int i = 0 ; i <str.size();i++) {
			if(i==0){
				sortedList.add(str.get(0));
				continue;
			}
			SortEntry entry = new SortEntry();
			entry.key = str.get(i) ;
			sortList.add(entry);
		}
		Collections.sort(sortList,comparator);
		for (SortEntry s : sortList) {
			sortedList.add(s.key);
		}
		return sortedList; 
	}
}
