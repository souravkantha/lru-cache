package com.lru.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lru.constants.ErrorCodes;
import com.lru.datatypes.PageNode;
import com.lru.exceptions.EmptyCacheException;
import com.lru.exceptions.PageNotFoundException;
import com.lru.interfaces.AbstractCache;

public class VanillaLRUCache<T> implements AbstractCache<T> {
	
	private Integer cacheSize;
	
	private Integer cacheKeyCount;
	
	private Map<String, PageNode<T>> cacheMap;
	
	private PageNode<T> head;
	
	private PageNode<T> tail;
	
	
	public VanillaLRUCache(Integer cacheSize) {
		
		assert(cacheSize > 0);
		
		this.cacheSize = cacheSize;
		cacheKeyCount = 0;
		this.cacheMap = new HashMap<>(this.cacheSize);
		head = tail = null;
		
	}

	@Override
	public void set(String key, T val) {
		
		if (this.cacheMap.containsKey(key)) {
			
			PageNode<T> node2Update = this.cacheMap.get(key);
			node2Update.setData(val);
			return;
		}
		
			PageNode<T> page = new PageNode<>(key,val);
			
			if(this.head == null && this.tail == null) {
				
				this.head = this.tail = page;
				this.head.setNext(null);
				this.head.setPrev(null);
				this.tail.setNext(null);
				this.tail.setPrev(null);
				
			} else {
				
				tail = page;
				tail.setPrev(head);
				tail.setNext(null);
				head.setPrev(null);
				head.setNext(tail);
				
			}
			
			if (this.cacheKeyCount + 1 <= this.cacheSize ) {
				
				this.cacheKeyCount++;
				
			} else {
				
				removeLastNodeAndAddNewNode(page);
				
			}
			
			this.cacheMap.put(key, page);
	}

	@Override
	public T get(String key) throws PageNotFoundException {
		
		if (!this.cacheMap.containsKey(key)) 
			 throw new PageNotFoundException(ErrorCodes.ERR001, "Page Not found in cache");
		
		return this.cacheMap.get(key).getData();
		
	}

	@Override
	public void clear() {
		
		this.cacheMap.clear();
		this.head = this.tail = null;
		this.cacheKeyCount = 0;
		
	}

	// Return all keys according to the order of linked list else we could have returned the Hashmap's key set
	@Override
	public List<String> getKeys() throws EmptyCacheException {
	
		if ((head = tail) == null) 
			 throw new EmptyCacheException(ErrorCodes.ERR002, "Cache is empty");
		
		List<String> keys = new ArrayList<>();
		
		PageNode<T> tmp = this.head;
		
		while (tmp != null) {
			keys.add(tmp.getKey());
			tmp = tmp.getNext();
			
		}
		
		return keys;
		
	}
	
	private void removeLastNodeAndAddNewNode(PageNode<T> newNode) {
		
		PageNode<T> oldTail = tail;

		if(this.cacheKeyCount > 1) {
			
			tail.getPrev().setNext(null);
			
			newNode.setPrev(null);
			newNode.setNext(head);
			
			head.setPrev(newNode);
			
		} else {
			
			head = tail = newNode;
		}
		

		this.cacheMap.remove(oldTail.getKey());
		oldTail = null;
		
	}
	
	

}
