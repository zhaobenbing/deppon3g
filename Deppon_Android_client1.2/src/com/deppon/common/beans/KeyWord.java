package com.deppon.common.beans;
/**
 * @功能描述：关键字
 * @author 赵本兵
 * @创建日期：2011-10-31
 */
public class KeyWord {
		private int id;
		private String keyword;
		public KeyWord() {
 		}
		
		public KeyWord(String keyword) {
 			this.keyword = keyword;
		}
 		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getKeyword() {
			return keyword;
		}
		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
		
}
