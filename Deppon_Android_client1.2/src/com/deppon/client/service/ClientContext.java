package com.deppon.client.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import com.deppon.common.beans.Branch;

/**
 * 该类用来缓存客户端数据，对外部提供访问这些数据的方法。
 * 
 * @author Administrator：赵本兵 @ 创建时间：2011-7-29
 */
public class ClientContext {
	// 配置文件
	private Properties properties;

	/**
	 * 缓存营业网点数据
	 */
	private Map<Integer, Branch> branchCache;

	/**
	 * 缓存运行时业务数据
	 */
	private Map<String, Object> businessData;

	public ClientContext() {
		branchCache = new Hashtable<Integer, Branch>();
		businessData = new HashMap<String, Object>();
	}

	public void addBranch(Branch branch) {
		branchCache.put(branch.getId(), branch);
	}

	public void setBranches(Map<Integer, Branch> branches) {
		branchCache.clear();
		branchCache.putAll(branches);
	}

	public Object getBusinessData(String name) {
		return businessData.get(name);
	}

	public void addBusinessData(String name, Object data) {
		businessData.put(name, data);
	}

	public Map<Integer, Branch> getBranchs() {
		return branchCache;
	}

	public Map<Integer, Branch> getBranchCache() {
		return branchCache;
	}

	public void setBranchCache(Map<Integer, Branch> branchCache) {
		this.branchCache = branchCache;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	//获取属性对应的value
	public String getSystemProperty(String name){
		return properties.getProperty(name);
	}
 }
