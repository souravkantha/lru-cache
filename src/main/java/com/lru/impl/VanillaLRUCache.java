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
		this.head = this.tail = null;
		
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
				
				if (this.head == null && this.tail == null) {

					this.head = this.tail = page;

				} else {

					tail.setNext(page);
					tail.getNext().setPrev(tail);
					tail = page;

				}

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
	
		if (this.head == null && this.tail == null) 
			 throw new EmptyCacheException(ErrorCodes.ERR002, "Cache is empty");
		
		List<String> keys = new ArrayList<>();
		
		PageNode<T> tmp = this.head;
		
		while (tmp != null) {
			keys.add(tmp.getKey());
			tmp = tmp.getNext();
			
		}
		
		return keys;
		
	}
	
	public Integer getCacheSize() {
		
		return this.cacheSize;
	}
	
	public Integer getCacheKeyCount() {
		
		return this.cacheKeyCount;
	}
	
	private void removeLastNodeAndAddNewNode(PageNode<T> newNode) {

		PageNode<T> oldTail = tail;

		if (this.cacheKeyCount > 1) {
			
			tail = tail.getPrev();
			tail.setNext(null);
			
			newNode.setNext(head);
			head.setPrev(newNode);
			
			head = newNode;

		} else {

			this.head = this.tail = newNode;
		}

		this.cacheMap.remove(oldTail.getKey());
		oldTail = null;

	}
	

}
