package com.lru.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lru.constants.ErrorCodes;
import com.lru.datatypes.PageNode;
import com.lru.datatypes.PageNodeList;
import com.lru.exceptions.EmptyCacheException;
import com.lru.exceptions.PageNotFoundException;
import com.lru.interfaces.AbstractCache;

public class VanillaLRUCache<T> implements AbstractCache<T> {
	
	private Integer cacheSize;
	
	private Integer cacheKeyCount;
	
	private Map<String, PageNode<T>> cacheMap;
	
	private PageNodeList<T> pageNodeList;
	
	public VanillaLRUCache(Integer cacheSize) {
		
		assert(cacheSize > 0);
		
		this.cacheSize = cacheSize;
		cacheKeyCount = 0;
		this.cacheMap = new HashMap<>(this.cacheSize);
		this.pageNodeList = new PageNodeList<>();
		
		
	}

	@Override
	public void set(String key, T val) {
		
		if (this.cacheMap.containsKey(key)) {
			
			PageNode<T> node2Update = this.cacheMap.get(key);
			node2Update.setData(val);
			return;
		}
		
			PageNode<T> page = new PageNode<>(key,val);
			
			if (this.cacheKeyCount + 1 <= this.cacheSize) {
				
				this.pageNodeList.add(page);

				this.cacheKeyCount++;

			} else {

				final String key2BEvicted = 
						this.pageNodeList.removeLastNodeAndAddNewNode(page, this.cacheKeyCount);
				this.cacheMap.remove(key2BEvicted);
				
			}
			
			this.cacheMap.put(key, page);
	}

	@Override
	public T get(String key) throws PageNotFoundException {
		
		if (!this.cacheMap.containsKey(key)) 
			 throw new PageNotFoundException(ErrorCodes.ERR001, "Page Not found in cache");
		
		PageNode<T> currNode = this.cacheMap.get(key);
		
		this.pageNodeList.rearrange(currNode);
		
		return this.cacheMap.get(key).getData();
		
	}

	@Override
	public void clear() {
		
		this.cacheMap.clear();
		this.pageNodeList.clear();
		this.cacheKeyCount = 0;
		
	}

	// Return all keys according to the order of linked list else we could have returned the Hashmap's key set
	@Override
	public List<String> getKeys() throws EmptyCacheException {
	
		if (this.pageNodeList.isEmpty()) 
			 throw new EmptyCacheException(ErrorCodes.ERR002, "Cache is empty");
		
		return this.pageNodeList.getKeys();
		
	}
	
	public Integer getCacheSize() {
		
		return this.cacheSize;
	}
	
	public Integer getCacheKeyCount() {
		
		return this.cacheKeyCount;
	}
	

}
