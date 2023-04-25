package com.lru.impl;

import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.domain.TestMappings;
import org.jsmart.zerocode.jupiter.extension.ParallelLoadExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@LoadWith("load-runner.properties")
@TestMapping(testClass = VanillaLRUCacheTest.class, testMethod = "testAddElementsAndEviction")
@ExtendWith({ParallelLoadExtension.class})
public class LRUCacheLoadTest {
	
    @Test
    @DisplayName("testing parallel load")
    @TestMappings({@TestMapping(testClass = VanillaLRUCacheTest.class, testMethod = "testAddElementsAndEviction"),
    	@TestMapping(testClass = VanillaLRUCacheTest.class, testMethod = "testGetElement")})
    public void testLoadForAddAndEvictCache() {
    	
    }


}
