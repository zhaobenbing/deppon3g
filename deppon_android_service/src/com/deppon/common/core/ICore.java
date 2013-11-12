package com.deppon.common.core;

import java.io.Serializable;
import java.util.List;

public interface ICore <T extends Serializable,PK extends Serializable>{
	public void addnew(T bean);
	public void update(T bean);
	public void delete(T bean);
	public T load(PK id);
	public T get(PK id);
	public List<T> find(String hsql);
	public List<T> find(T bean);
	public List<T> find(T bean,String[] excludeProperty);
}
