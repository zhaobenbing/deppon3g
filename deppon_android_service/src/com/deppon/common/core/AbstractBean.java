package com.deppon.common.core;

import java.io.Serializable;

public abstract class AbstractBean implements Serializable{
	private static final long serialVersionUID = 5289230958685597866L;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
