package com.project.geocode.cache;

import com.project.geocode.dto.LocationDTO;

/*
 * A cache interface to implement different types of caches
 * 
 * */
public interface CacheInterface {

	public void setDataInCache(String key, LocationDTO value);

	public Object getDataFromCache(String key);
	
	public Boolean haskey(String key) ;
}
