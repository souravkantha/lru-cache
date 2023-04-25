package com.lru.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class PageNodeList<T> {
	
	private PageNode<T> head;
	
	private PageNode<T> tail;
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	
	public PageNodeList() {
		
		this.head = this.tail = null;
	}
	
	public void add(PageNode<T> page) {
		
		if (this.head == null && this.tail == null) {

			this.head = this.tail = page;

		} else {
			lock.writeLock().lock();
			try {
				tail.setNext(page);
				tail.getNext().setPrev(tail);
				tail = page;
			} finally {
				lock.writeLock().unlock();
			}
			

		}
		
	}
	
	public void clear() {
		
		this.head = this.tail = null;
	}
	
	public boolean isEmpty() {
		
		return (this.head == null && this.tail == null);
	}
	
	public PageNode<T> getFront() {
		
		PageNode<T> tmpHead = head;
		return tmpHead;
		
	}
	
	public PageNode<T> getRear() {
		
		PageNode<T> tmpTail = tail;
		return tmpTail;
		
	}
	
	public String removeLastNodeAndAddNewNode(PageNode<T> newNode, Integer cacheKeyCount) {

		PageNode<T> node2BEvicted = this.tail;

		if (cacheKeyCount > 1) {
			
			lock.writeLock().lock();
			try {
			
				tail = tail.getPrev();
				tail.setNext(null);
				
				newNode.setNext(head);
				head.setPrev(newNode);
				
				head = newNode;
			
			} finally {
				lock.writeLock().unlock();
			}

		} else {

			this.head = this.tail = newNode;
		}
		
		final String key2BEvicted = node2BEvicted.getKey();
		node2BEvicted = null;
		return key2BEvicted;

	}
	
	public void rearrange(final PageNode<T> accessedNode) {
		
		if (!accessedNode.equals(this.head)) {
			
			lock.writeLock().lock();
			try {
			
				accessedNode.getPrev().setNext(accessedNode.getNext());
				
				if (accessedNode.getNext() != null) 
					accessedNode.getNext().setPrev(accessedNode.getPrev());
				
				accessedNode.setNext(this.head);
				this.head.setPrev(accessedNode);
				
				this.head = accessedNode;
			} finally {
				lock.writeLock().unlock();
			}
			
		}
		
	}
	
	public List<String> getKeys() {

		List<String> keys = new ArrayList<>();

		PageNode<T> tmp = this.getFront();

		lock.readLock().lock();
		
		try {
		
			while (tmp != null) {
				keys.add(tmp.getKey());
				tmp = tmp.getNext();
	
			}
		} finally {
			lock.readLock().unlock();
		}

		return keys;

	}
	

}
