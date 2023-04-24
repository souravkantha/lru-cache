package com.lru.interfaces;

import java.util.List;

import com.lru.exceptions.EmptyCacheException;
import com.lru.exceptions.PageNotFoundException;

public interface AbstractCache<T> {
	
	public void set(String key, T val);
	
	public T get(String key) throws PageNotFoundException;
	
	public void clear();
	
	public List<String> getKeys() throws EmptyCacheException;

}
