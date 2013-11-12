package com.deppon.client.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.deppon.common.beans.City;
import com.deppon.common.beans.Dept;
import com.deppon.common.beans.KeyWord;
import com.deppon.common.beans.Province;

/**
 * 手机数据库:保存客户端数据
 * 创建数据库，创建表 
 * @author Administrator：赵本兵
 * @创建时间：2011-7-29
 * @修改：省份新增字段 index,oldProvinceId，oldProvinceName
 *  城市新增字段 oldCityId，oldCityName，oldFather
 *  部门新增字段oldProvinceId，oldCityId
 *  @修改日期：2011-10-21
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	private SQLiteDatabase db;
	 
	public DatabaseHelper(Context context){
		//创建数据库：dp_3g_db
		super(context,"dp_3g_db",null,1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建网点表
		String drop_dept = " drop table if exists dept;";
		String create_dept = " create table dept(_id integer primary key autoincrement, " +
				"deptId text,deptName text ,deptAddress text ,deptPhone text ,oldProvinceId text,oldCityId text" +
				"provinceId text,cityId text,isStart text);";
		//创建省份表
		String drop_province = "drop table if exists province;";
		String create_province = " create table province(_id integer primary key autoincrement, " +
				"provinceId text ,provinceName text,findex text,oldProvinceId text,oldProvinceName text);";
		//创建城市表
		String drop_city = "drop table if exists city;";
		String create_city = "create table city(_id integer primary key autoincrement ," +
				"cityId text,cityName text,father text,oldCityId text,oldCityName text,oldFather text);";
		
		//创建关键字表
		String drop_keywords = "drop table if exists keywords;";
		String create_keywords = "create table keywords(_id integer primary key autoincrement ," +
				"keyword text);";
		//保存城市id
		String drop_cityIds = "drop table if exists cityIds;";
		String create_cityIds = "create table cityIds(_id integer primary key autoincrement ," +
				"cityId text);";
		
		
		db.execSQL(drop_cityIds);
		db.execSQL(create_cityIds);
		Log.i("DB", "------create table cityIds------");
		
		db.execSQL(drop_dept);
		db.execSQL(create_dept);
		Log.i("DB", "------create table dept------");
		
		db.execSQL(drop_province);
		db.execSQL(create_province);
		Log.i("DB", "------drop table province------");
		Log.i("DB", "------create table province------");
		
		db.execSQL(drop_city);
		db.execSQL(create_city);
		Log.i("DB", "------create table city------");	
		
		db.execSQL(drop_keywords);
		db.execSQL(create_keywords);
		Log.i("DB", "------create table keywords------");
 	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	public Cursor select (String tableName){
 		return select(tableName,null,null,null,null,null,null);
	}
	public Cursor select(String tableName, String[] columns, String selection, String [] selectionArgs,
			String groupby, String having, String orderby){
		//Cursor 类似于List<Map<String,Object>>
		db=this.getReadableDatabase();
		Cursor cursor=db.query(tableName, columns, selection, selectionArgs, groupby, having, orderby);
 		Log.i("LOG","====select " +tableName+"===");
		return cursor;
	}
	/**
	 * 插入i数据
	 */
	public void insert(Province pro){
		db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("provinceId", pro.getProvinceId());
		cv.put("provinceName", pro.getProvinceName());
		cv.put("findex", pro.getFindex());
		cv.put("oldProvinceId", pro.getProvinceOldId());
		cv.put("oldProvinceName", pro.getProvinceOldName());
 		db.insert("province", null, cv);
 		Log.i("LOG","====insert into Province===");
		db.close();
	}
	public void insert(City city){
		db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("cityId",city.getCityId());
		cv.put("cityName", city.getCityName());
		cv.put("father", city.getFather());
		cv.put("oldCityId",city.getCityOldId());
		cv.put("oldCityName", city.getCityOldName());
		cv.put("oldFather", city.getOldFather());
		db.insert("city", null, cv);
		Log.i("InsertProvince","====insert into City===");
		db.close();
	}
	public void insert(Dept dept){
		db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("deptId", dept.getDeptid());
		cv.put("deptAddress", dept.getDeptAddress());
		cv.put("deptPhone", dept.getDeptPhone());
		//cv.put("provinceId", dept.getProvinceId());
		//cv.put("oldProvinceId", dept.getOldProvinceId());
		//cv.put("oldCityId", dept.getOldCityId());
		cv.put("cityId", dept.getCityId());
		//cv.put("isStart", dept.getIsStart());
		cv.put("deptName", dept.getDeptName());
		db.insert("dept", null, cv);
		Log.i("LOG","====insert into Dept===");
		db.close();
	}
	//关键字
	public void insert(KeyWord keyword){
		db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("keyId", keyword.getId());
		cv.put("keyword", keyword.getKeyword());
 		db.insert("keywords", null, cv);
		Log.i("LOG","====insert into keywords===");
		db.close();
	}
	//cityIds
	public void insert(String cityid){
		db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("cityId",cityid);
 		db.insert("cityIds", null, cv);
 		Log.i("LOG","====insert into cityIds===");
		db.close();
	}
}
