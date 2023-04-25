package com.lru.datatypes;

import java.util.ArrayList;
import java.util.List;

public class PageNodeList<T> {
	
	private PageNode<T> head;
	
	private PageNode<T> tail;
	
	public PageNodeList() {
		
		this.head = this.tail = null;
	}
	
	public void add(PageNode<T> page) {
		
		if (this.head == null && this.tail == null) {

			this.head = this.tail = page;

		} else {
			
			tail.setNext(page);
			tail.getNext().setPrev(tail);
			tail = page;

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
			
			tail = tail.getPrev();
			tail.setNext(null);
			
			newNode.setNext(head);
			head.setPrev(newNode);
			
			head = newNode;

		} else {

			this.head = this.tail = newNode;
		}
		
		final String key2BEvicted = node2BEvicted.getKey();
		node2BEvicted = null;
		return key2BEvicted;

	}
	
	public void rearrange(final PageNode<T> accessedNode) {
		
		if (!accessedNode.equals(this.head)) {
			
			accessedNode.getPrev().setNext(accessedNode.getNext());
			
			if (accessedNode.getNext() != null) 
				accessedNode.getNext().setPrev(accessedNode.getPrev());
			
			accessedNode.setNext(this.head);
			this.head.setPrev(accessedNode);
			
			this.head = accessedNode;
			
		}
		
	}
	
	public List<String> getKeys() {

		List<String> keys = new ArrayList<>();

		PageNode<T> tmp = this.getFront();

		while (tmp != null) {
			keys.add(tmp.getKey());
			tmp = tmp.getNext();

		}

		return keys;

	}
	

}
