package com.lru.datatypes;

public class PageNode<T> {
	
	private String k;
	
	private T data;
	
	private PageNode<T> prev;
	
	private PageNode<T> next;
	
	public PageNode(String k, T data) {
		
		this.k = k;
		this.data = data;
	}
	
	public T getData() {
		return this.data;
	}
	
	
	public void setData(T data) {
		this.data = data;
		
	}
	
	public void setNext(PageNode<T> next ) {
		this.next = next;
	}
	
	public void setPrev(PageNode<T> prev) {
		this.prev = prev;
	}
	
	public PageNode<T> getPrev() {
		return this.prev;
	}
	
	public PageNode<T> getNext() {
		return this.next;
	}
	
	public String getKey() {
		return this.k;
	}
	
	@Override
	public String toString() {
		
		return this.data.toString();
		
	}

}
