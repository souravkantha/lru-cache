package com.lru.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.lru.exceptions.EmptyCacheException;
import com.lru.exceptions.PageNotFoundException;

public class VanillaLRUCacheTest {
	
	VanillaLRUCache<String> cache = null;
	
	VanillaLRUCache<String> loadWriteCache = new VanillaLRUCache<>(3);
	
	VanillaLRUCache<String> loadReadCache = new VanillaLRUCache<>(3);

	@Test
	public void testCacheSize() {
		
		cache = new VanillaLRUCache<>(2);

		assertEquals(2, cache.getCacheSize());
	}
	
	@Test
	public void testCacheSizeZero() {
		
		assertThrows(AssertionError.class, () -> cache = new VanillaLRUCache<>(0));
	}
	
	@Test
	public void testClearCache() {
		
		cache = new VanillaLRUCache<>(5);
		
		cache.clear();

		assertEquals(0, cache.getCacheKeyCount());
	}
	

	@Test
	public void testAddElement() {
		
		cache = new VanillaLRUCache<>(5);
		
		cache.set("foo", "bar");

		assertEquals("bar", cache.get("foo"));
	
	}
	
	@Test
	public void testAddElements() {
		
		cache = new VanillaLRUCache<>(3);
		
		cache.set("foo1", "bar1");
		cache.set("foo2", "bar2");
		cache.set("foo3", "bar3");
		
		List<String> keys = cache.getKeys();
		
		assertEquals(3, keys.size());
		
		assertEquals("foo1", keys.get(0));
		assertEquals("foo2", keys.get(1));
		assertEquals("foo3", keys.get(2));

	}
	
	@Test
	public void testAddElementsAndEviction() {
		
		
		
		loadWriteCache.set("foo1", "bar1");
		loadWriteCache.set("foo2", "bar2");
		loadWriteCache.set("foo3", "bar3");
		
		loadWriteCache.set("foo4", "bar4");
		
		List<String> keys = loadWriteCache.getKeys();
		
		assertEquals(3, keys.size());
		
		assertEquals("foo4", keys.get(0));
		assertEquals("foo1", keys.get(1));
		assertEquals("foo2", keys.get(2));
		
		loadWriteCache.set("foo5", "bar5");
		
		keys = loadWriteCache.getKeys();
		
		assertEquals(3, keys.size());
		
		assertEquals("foo5", keys.get(0));
		assertEquals("foo4", keys.get(1));
		assertEquals("foo1", keys.get(2));

	}
	
	@Test
	public void testEviction() {
		
		cache = new VanillaLRUCache<>(1);
		
		cache.set("foo1", "bar1");
		cache.set("foo2", "bar2");
		cache.set("foo3", "bar3");
		
		List<String> keys = cache.getKeys();
		
		assertNotEquals(3, keys.size());
		
		assertEquals("foo3", keys.get(0));
		
	}
	
	@Test
	public void testGetElement() {
		
		loadReadCache.set("foo1", "bar1");
		loadReadCache.set("foo2", "bar2");
		loadReadCache.set("foo3", "bar3");
		
		String val = loadReadCache.get("foo1");
		
		assertEquals("bar1", val);
		
		loadReadCache.get("foo2"); // rearrange should happen and foo2 should be in front
		
		List<String> keys = loadReadCache.getKeys();
		
		assertEquals("foo2", keys.get(0));
		assertEquals("foo1", keys.get(1));
		assertEquals("foo3", keys.get(2));
		
		loadReadCache.get("foo3"); // rearrange should happen and foo3 should be in front
		
		keys = loadReadCache.getKeys();
		
		assertEquals("foo3", keys.get(0));
		assertEquals("foo2", keys.get(1));
		assertEquals("foo1", keys.get(2));

	}
	
	@Test
	public void testUnknownCacheKey() {
		
		cache = new VanillaLRUCache<>(1);
		
		assertThrows(PageNotFoundException.class,  () -> { cache.get("unknown"); });
	}
	
	@Test
	public void testEmptyCacheGetKeys() {
		
		cache = new VanillaLRUCache<>(1);
		
		cache.set("foo1", "bar1");
		
		List<String> keys = cache.getKeys();
		
		assertEquals(1, keys.size());
		
		cache.clear();
		
		
		assertThrows(EmptyCacheException.class,  () -> cache.getKeys());
	}
	

}
