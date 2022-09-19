package com.project.geocode.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.project.geocode.dto.LocationDTO;

@Component
public class CustomInMemoryCache implements CacheInterface {
	static Logger LOGGER = LoggerFactory.getLogger(CustomInMemoryCache.class);
	
	/*A singleton class with synchronised initialisation 
	 *to make sure there is single object shared across threads 
	 * */
	private static CustomInMemoryCache singletonCache;

	private Map<String, Object> cache;

	private CustomInMemoryCache() {
		cache = new ConcurrentHashMap<>();
	}

	public static CustomInMemoryCache getInMemoryCache() {
		if (singletonCache == null) {
			synchronized (CustomInMemoryCache.class) {
				if (singletonCache == null) {
					singletonCache = new CustomInMemoryCache();
				}
			}
		}
		return singletonCache;
	}

	@Override
	public void setDataInCache(String location, LocationDTO value) {
		if (this.cache == null) {
			LOGGER.info("Cache is not initialized !!");
			return;
		}
		this.cache.put(location, value);
	}

	@Override
	public Object getDataFromCache(String key) {
		if (this.cache == null) {
			LOGGER.info("Cache is not initialized !!");
			return null;
		}
		return this.cache.get(key);
	}

	@Override
	public Boolean haskey(String key) {
		return cache.containsKey(key);
	}

}
